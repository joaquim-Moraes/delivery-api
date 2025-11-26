package com.deliverytech.delivery_api.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deliverytech.delivery_api.DTO.Request.ItemPedidoDTO;
import com.deliverytech.delivery_api.DTO.Request.PedidoDTO;
import com.deliverytech.delivery_api.Entity.Cliente;
import com.deliverytech.delivery_api.Entity.ItemPedido;
import com.deliverytech.delivery_api.Entity.Pedido;
import com.deliverytech.delivery_api.Entity.Pedido.StatusPedido;
import com.deliverytech.delivery_api.Entity.Produto;
import com.deliverytech.delivery_api.Exception.BusinessException;
import com.deliverytech.delivery_api.Exception.EntityNotFoundException;
import com.deliverytech.delivery_api.Repository.ClienteRepository;
import com.deliverytech.delivery_api.Repository.PedidoRepository;
import com.deliverytech.delivery_api.Repository.ProdutoRepository;

@Service
@SuppressWarnings("null")
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    private static final double VALOR_MINIMO_PEDIDO = 10.0;

    /**
     * Cria um novo pedido com transação complexa.
     * Valida cliente, itens, calcula total e define status inicial.
     * 
     * REGRAS DE NEGÓCIO:
     * 1. Cliente deve existir e estar ativo
     * 2. Todos os produtos devem existir e estar disponíveis
     * 3. Pedido deve ter pelo menos um item
     * 4. Total do pedido deve ser >= R$ 10.00
     * 5. Todos os itens devem ter quantidade > 0 e preço > 0
     * 
     * @param clienteId ID do cliente
     * @param pedido Entidade Pedido com itens associados
     * @return Pedido criado com id gerado
     * @throws EntityNotFoundException se cliente ou produtos não existem
     * @throws BusinessException se regras de negócio são violadas
     */
    @Transactional
    public Pedido criarPedido(Long clienteId, Pedido pedido) {
        // 1. Validar ID do cliente
        validarId(clienteId);

        // 2. Buscar e validar cliente
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new EntityNotFoundException("Cliente", clienteId));

        // 3. Validar cliente está ativo
        if (!cliente.isAtivo()) {
            throw new BusinessException("CLIENTE_INATIVO", 
                    "Não é possível criar pedido para cliente inativo. Cliente ID: " + clienteId);
        }

        // 4. Validar itens do pedido
        validarItensPedido(pedido);

        // 5. Validar e processar itens (verificar disponibilidade)
        validarDisponibilidadeProdutos(pedido.getItens());

        // 6. Calcular total do pedido
        double totalPedido = calcularTotalPedido(pedido.getItens());
        
        // 7. Validar valor mínimo
        if (totalPedido < VALOR_MINIMO_PEDIDO) {
            throw new BusinessException("VALOR_MINIMO_VIOLADO",
                    "Valor mínimo do pedido é R$ " + VALOR_MINIMO_PEDIDO + ". Total: R$ " + totalPedido);
        }

        // 8. Configurar dados do pedido
        pedido.setCliente(cliente);
        pedido.setDatePedido(LocalDate.now());
        pedido.setStatus(StatusPedido.PENDENTE);

        // 9. Salvar pedido (e itens em cascata)
        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        // 10. Log de auditoria
        registrarAuditoria("PEDIDO_CRIADO", "Pedido criado com ID: " + pedidoSalvo.getId() + 
                          ", Cliente: " + cliente.getNome() + ", Total: R$ " + totalPedido);

        return pedidoSalvo;
    }

    /**
     * Cria pedido a partir de PedidoDTO. Converte os ItemPedidoDTO em ItemPedido,
     * valida disponibilidade e utiliza o preço do produto como preço unitário.
     * Em seguida delega para o método criarPedido(Long, Pedido).
     */
    @Transactional
    public Pedido criarPedidoDTO(PedidoDTO pedidoDTO) {
        if (pedidoDTO == null) {
            throw new BusinessException("PEDIDO_INVALIDO", "PedidoDTO não pode ser nulo");
        }

        Long clienteId = pedidoDTO.getClienteId();
        validarId(clienteId);

        // Validar cliente existe e está ativo (reuse will happen in criarPedido but we check early)
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new EntityNotFoundException("Cliente", clienteId));
        if (!cliente.isAtivo()) {
            throw new BusinessException("CLIENTE_INATIVO", "Não é possível criar pedido para cliente inativo. Cliente ID: " + clienteId);
        }

        // Converter e validar itens
        List<ItemPedido> itens = new ArrayList<>();
        for (ItemPedidoDTO itemDTO : pedidoDTO.getItens()) {
            if (itemDTO == null || itemDTO.getProdutoId() == null) {
                throw new BusinessException("PRODUTO_INVALIDO", "Item do pedido deve conter produtoId");
            }

            Produto produto = produtoRepository.findById(itemDTO.getProdutoId())
                    .orElseThrow(() -> new EntityNotFoundException("Produto", itemDTO.getProdutoId()));

            if (!produto.isDisponibilidade()) {
                throw new BusinessException("PRODUTO_INDISPONIVEL", "Produto '" + produto.getNome() + "' está indisponível");
            }

            if (itemDTO.getQuantidade() <= 0) {
                throw new BusinessException("QUANTIDADE_INVALIDA", "Quantidade deve ser maior que zero para o produto: " + produto.getNome());
            }

            ItemPedido item = new ItemPedido();
            item.setProduto(produto);
            item.setQuantidade(itemDTO.getQuantidade());
            // Use o preço atual do produto para evitar manipulação pelo cliente
            item.setPrecoUnitario(produto.getPreco());

            itens.add(item);
        }

        // Construir pedido e delegar
        Pedido pedido = new Pedido();
        pedido.setItens(itens);

        return criarPedido(clienteId, pedido);
    }

    /**
     * Busca pedido por ID com carregamento de itens.
     * 
     * @param id ID do pedido
     * @return Pedido encontrado com seus itens carregados
     * @throws IllegalArgumentException se pedido não existe
     */
    @Transactional(readOnly = true)
    public Pedido buscarPedidoPorId(Long id) {
        validarId(id);

        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Pedido com ID " + id + " não encontrado."));

        // Força carregamento da coleção lazy dentro da transação
        if (pedido.getItens() != null) {
            pedido.getItens().size();
        }

        return pedido;
    }

    /**
     * Busca histórico completo de pedidos de um cliente.
     * 
     * @param clienteId ID do cliente
     * @return Lista de pedidos do cliente ordenada por data (mais recentes primeiro)
     * @throws IllegalArgumentException se cliente não existe
     */
    @Transactional(readOnly = true)
    public List<Pedido> buscarPedidosPorCliente(Long clienteId) {
        validarId(clienteId);

        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Cliente com ID " + clienteId + " não encontrado."));

        List<Pedido> pedidos = pedidoRepository.findByClienteId(clienteId);
        
        // Carrega items para cada pedido
        pedidos.forEach(p -> {
            if (p.getItens() != null) {
                p.getItens().size();
            }
        });

        // Ordena por data decrescente (mais recentes primeiro)
        return pedidos.stream()
                .sorted((p1, p2) -> p2.getDatePedido().compareTo(p1.getDatePedido()))
                .toList();
    }

    /**
     * Atualiza status do pedido com validação de transições de estado.
     * Transições válidas:
     * - PENDENTE → EM_ANDAMENTO ou CANCELADO
     * - EM_ANDAMENTO → ENTREGUE ou CANCELADO
     * - ENTREGUE → (nenhuma)
     * - CANCELADO → (nenhuma)
     * 
     * @param pedidoId ID do pedido
     * @param novoStatus Novo status desejado
     * @return Pedido com status atualizado
     * @throws IllegalArgumentException se transição inválida ou pedido não existe
     */
    @Transactional
    public Pedido atualizarStatusPedido(Long pedidoId, StatusPedido novoStatus) {
        validarId(pedidoId);

        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Pedido com ID " + pedidoId + " não encontrado."));

        StatusPedido statusAtual = pedido.getStatus();

        if (statusAtual == novoStatus) {
            throw new IllegalArgumentException(
                    "Pedido já está no status: " + statusAtual);
        }

        validarTransicaoStatus(statusAtual, novoStatus);

        pedido.setStatus(novoStatus);
        return pedidoRepository.save(pedido);
    }

    /**
     * Calcula o total preciso de um pedido baseado em seus itens.
     * Fórmula: Σ(preço_unitário × quantidade) por item
     * 
     * @param itens Lista de itens do pedido
     * @return Total do pedido arredondado para 2 casas decimais
     * @throws IllegalArgumentException se itens inválidos
     */
    public double calcularTotalPedido(List<ItemPedido> itens) {
        if (itens == null || itens.isEmpty()) {
            return 0.0;
        }

        double total = 0.0;

        for (ItemPedido item : itens) {
            validarItem(item);

            double subtotal = item.getPrecoUnitario() * item.getQuantidade();
            total += subtotal;
        }

        // Arredonda para 2 casas decimais
        return Math.round(total * 100.0) / 100.0;
    }

    /**
     * Cancela um pedido se o status permitir.
     * Apenas pedidos nos status PENDENTE ou EM_ANDAMENTO podem ser cancelados.
     * 
     * @param pedidoId ID do pedido
     * @return Pedido cancelado
     * @throws IllegalArgumentException se pedido não pode ser cancelado
     */
    @Transactional
    public Pedido cancelarPedido(Long pedidoId) {
        validarId(pedidoId);

        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Pedido com ID " + pedidoId + " não encontrado."));

        StatusPedido statusAtual = pedido.getStatus();

        if (statusAtual == StatusPedido.ENTREGUE) {
            throw new IllegalArgumentException(
                    "Não é possível cancelar um pedido já entregue.");
        }

        if (statusAtual == StatusPedido.CANCELADO) {
            throw new IllegalArgumentException(
                    "Pedido já está cancelado.");
        }

        pedido.setStatus(StatusPedido.CANCELADO);
        return pedidoRepository.save(pedido);
    }

    /**
     * Lista pedidos por status específico.
     * 
     * @param status Status a filtrar
     * @return Lista de pedidos com o status informado
     */
    @Transactional(readOnly = true)
    public List<Pedido> listarPedidosPorStatus(StatusPedido status) {
        if (status == null) {
            throw new IllegalArgumentException("Status não pode ser nulo.");
        }
        return pedidoRepository.findByStatus(status);
    }

    /**
     * Lista pedidos em um período de datas.
     * 
     * @param inicio Data inicial (inclusive)
     * @param fim Data final (inclusive)
     * @return Lista de pedidos no período
     * @throws IllegalArgumentException se datas inválidas
     */
    @Transactional(readOnly = true)
    public List<Pedido> listarPedidosPorPeriodo(LocalDate inicio, LocalDate fim) {
        validarPeriodo(inicio, fim);
        return pedidoRepository.findByDatePedidoBetween(inicio, fim);
    }

    /**
     * Lista pedidos com múltiplos filtros (status, período).
     * 
     * @param status Status (opcional)
     * @param dataInicio Data inicial (opcional)
     * @param dataFim Data final (opcional)
     * @return Lista de pedidos filtrada
     */
    @Transactional(readOnly = true)
    public List<Pedido> listarComFiltros(StatusPedido status, LocalDate dataInicio, LocalDate dataFim) {
        if (status != null && dataInicio != null && dataFim != null) {
            validarPeriodo(dataInicio, dataFim);
            List<Pedido> porStatus = pedidoRepository.findByStatus(status);
            return porStatus.stream()
                    .filter(p -> !p.getDatePedido().isBefore(dataInicio) 
                            && !p.getDatePedido().isAfter(dataFim))
                    .toList();
        } else if (status != null) {
            return pedidoRepository.findByStatus(status);
        } else if (dataInicio != null && dataFim != null) {
            validarPeriodo(dataInicio, dataFim);
            return pedidoRepository.findByDatePedidoBetween(dataInicio, dataFim);
        }
        return pedidoRepository.findAll();
    }

    /**
     * Gera relatório de pedidos em um período com status específico.
     * 
     * @param inicio Data inicial
     * @param fim Data final
     * @param status Status a filtrar (em formato string)
     * @return Lista de pedidos do relatório
     */
    @Transactional(readOnly = true)
    public List<Pedido> gerarRelatorio(LocalDate inicio, LocalDate fim, String status) {
        validarPeriodo(inicio, fim);

        List<Pedido> pedidos = pedidoRepository.findByDatePedidoBetween(inicio, fim);

        if (status != null && !status.isBlank()) {
            try {
                StatusPedido statusEnum = StatusPedido.valueOf(status.toUpperCase());
                return pedidos.stream()
                        .filter(p -> p.getStatus() == statusEnum)
                        .toList();
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(
                        "Status inválido: " + status + ". Valores válidos: PENDENTE, EM_ANDAMENTO, ENTREGUE, CANCELADO");
            }
        }

        return pedidos;
    }

    /**
     * Alias para atualizarStatusPedido (compatibilidade com chamadas antigas).
     */
    public Pedido alterarStatus(Long pedidoId, StatusPedido novoStatus) {
        return atualizarStatusPedido(pedidoId, novoStatus);
    }

    /**
     * Lista pedidos de um restaurante.
     * 
     * @param restauranteId ID do restaurante
     * @return Lista de pedidos associados
     */
    @Transactional(readOnly = true)
    public List<Pedido> listarPedidosRestaurante(Long restauranteId) {
        validarId(restauranteId);
        return pedidoRepository.findByRestauranteId(restauranteId);
    }

    // ==================== VALIDAÇÕES ====================

    /**
     * Valida transição entre estados de pedido.
     * Garante que apenas transições válidas sejam permitidas.
     */
    private void validarTransicaoStatus(StatusPedido statusAtual, StatusPedido novoStatus) {
        boolean transicaoValida = false;

        switch (statusAtual) {
            case PENDENTE:
                // De PENDENTE pode ir para EM_ANDAMENTO ou CANCELADO
                transicaoValida = (novoStatus == StatusPedido.EM_ANDAMENTO 
                                || novoStatus == StatusPedido.CANCELADO);
                break;

            case EM_ANDAMENTO:
                // De EM_ANDAMENTO pode ir para ENTREGUE ou CANCELADO
                transicaoValida = (novoStatus == StatusPedido.ENTREGUE 
                                || novoStatus == StatusPedido.CANCELADO);
                break;

            case ENTREGUE:
                // De ENTREGUE não pode mudar
                transicaoValida = false;
                break;

            case CANCELADO:
                // De CANCELADO não pode mudar
                transicaoValida = false;
                break;
        }

        if (!transicaoValida) {
            throw new IllegalArgumentException(
                    "Transição inválida: " + statusAtual + " → " + novoStatus);
        }
    }

    /**
     * Valida disponibilidade de todos os produtos do pedido.
     * Verifica se cada produto existe, está disponível e preço é válido.
     */
    private void validarDisponibilidadeProdutos(List<ItemPedido> itens) {
        for (ItemPedido item : itens) {
            if (item.getProduto() == null || item.getProduto().getId() == null) {
                throw new BusinessException("PRODUTO_INVALIDO",
                        "Item do pedido deve ter um produto associado");
            }

            Long produtoId = item.getProduto().getId();
            Produto produto = produtoRepository.findById(produtoId)
                    .orElseThrow(() -> new EntityNotFoundException("Produto", produtoId));

            // Validar disponibilidade
            if (!produto.isDisponibilidade()) {
                throw new BusinessException("PRODUTO_INDISPONIVEL",
                        "Produto '" + produto.getNome() + "' (ID: " + produtoId + ") está indisponível");
            }

            // Validar preço do item corresponde ao preço do produto
            if (Math.abs(item.getPrecoUnitario() - produto.getPreco()) > 0.01) {
                System.out.println("Aviso: Preço do item diferente do preço do produto. " +
                                 "Produto ID: " + produtoId + ", Item preço: " + item.getPrecoUnitario() +
                                 ", Produto preço: " + produto.getPreco());
            }
        }
    }

    /**
     * Registra auditoria de operações importantes.
     * Pode ser integrado com sistema de logs.
     */
    private void registrarAuditoria(String operacao, String detalhes) {
        // TODO: Integrar com sistema de auditoria/logs
        System.out.println("[AUDITORIA] " + operacao + ": " + detalhes);
    }

    /**
     * Valida itens do pedido.
     */
    private void validarItensPedido(Pedido pedido) {
        if (pedido.getItens() == null || pedido.getItens().isEmpty()) {
            throw new IllegalArgumentException(
                    "Pedido deve conter pelo menos um item.");
        }

        for (ItemPedido item : pedido.getItens()) {
            validarItem(item);
        }
    }

    /**
     * Valida um item individual do pedido.
     */
    private void validarItem(ItemPedido item) {
        if (item == null) {
            throw new IllegalArgumentException("Item do pedido não pode ser nulo.");
        }

        if (item.getQuantidade() <= 0) {
            throw new IllegalArgumentException(
                    "Quantidade do item deve ser maior que zero.");
        }

        if (item.getPrecoUnitario() <= 0) {
            throw new IllegalArgumentException(
                    "Preço unitário do item deve ser maior que zero.");
        }

        if (item.getProduto() == null) {
            throw new IllegalArgumentException(
                    "Item deve ter um produto associado.");
        }
    }

    /**
     * Valida período de datas.
     */
    private void validarPeriodo(LocalDate inicio, LocalDate fim) {
        if (inicio == null || fim == null) {
            throw new IllegalArgumentException(
                    "Data de início e fim são obrigatórias.");
        }

        if (inicio.isAfter(fim)) {
            throw new IllegalArgumentException(
                    "Data de início não pode ser posterior à data de fim.");
        }
    }

    /**
     * Valida ID.
     */
    private void validarId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "ID deve ser um número positivo.");
        }
    }
}