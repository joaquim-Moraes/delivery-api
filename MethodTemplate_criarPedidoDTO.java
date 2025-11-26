// Método a ser adicionado na classe PedidoService após o método criarPedido()
// Este é um template - copie este método e adicione após o criarPedido()

/**
 * Cria pedido a partir de PedidoDTO com validação automática de preços.
 * Este método é o recomendado para uso nos controllers.
 * 
 * Diferente de criarPedido(Long, Pedido), este método:
 * - Aceita PedidoDTO com validação Bean Validation
 * - Preenche automaticamente precoUnitario dos itens
 * - Converte ItemPedidoDTO para ItemPedido
 * 
 * @param pedidoDTO DTO com clienteId e itens
 * @return Pedido criado com id gerado
 * @throws EntityNotFoundException se cliente ou produtos não existem
 * @throws BusinessException se regras de negócio são violadas
 */
@Transactional
public Pedido criarPedidoDTO(com.deliverytech.delivery_api.DTO.Request.PedidoDTO pedidoDTO) {
    // 1. Validar DTO
    if (pedidoDTO == null || pedidoDTO.getClienteId() == null || pedidoDTO.getItens() == null || pedidoDTO.getItens().isEmpty()) {
        throw new com.deliverytech.delivery_api.Exception.BusinessException("PEDIDO_INVALIDO", 
            "PedidoDTO deve conter clienteId e pelo menos um item");
    }

    // 2. Converter ItemPedidoDTO para ItemPedido
    List<ItemPedido> itens = new java.util.ArrayList<>();
    for (com.deliverytech.delivery_api.DTO.Request.ItemPedidoDTO itemDTO : pedidoDTO.getItens()) {
        ItemPedido item = new ItemPedido();
        
        // Buscar o produto para preencher os dados
        if (itemDTO.getProdutoId() == null) {
            throw new BusinessException("PRODUTO_INVALIDO", "ID do produto é obrigatório no item");
        }
        
        Produto produto = produtoRepository.findById(itemDTO.getProdutoId())
            .orElseThrow(() -> new EntityNotFoundException("Produto", itemDTO.getProdutoId()));
        
        // Validar disponibilidade
        if (!produto.isDisponibilidade()) {
            throw new BusinessException("PRODUTO_INDISPONIVEL",
                "Produto '" + produto.getNome() + "' está indisponível");
        }
        
        // Validar quantidade
        if (itemDTO.getQuantidade() <= 0) {
            throw new BusinessException("QUANTIDADE_INVALIDA",
                "Quantidade deve ser maior que zero para o produto: " + produto.getNome());
        }
        
        item.setProduto(produto);
        item.setQuantidade(itemDTO.getQuantidade());
        // 🔴 IMPORTANTE: Usar sempre o preço atual do produto
        item.setPrecoUnitario(produto.getPreco());
        
        itens.add(item);
    }

    // 3. Criar Pedido com os itens validados
    Pedido pedido = new Pedido();
    pedido.setItens(itens);
    
    // 4. Chamar o método original criarPedido
    return criarPedido(pedidoDTO.getClienteId(), pedido);
}
