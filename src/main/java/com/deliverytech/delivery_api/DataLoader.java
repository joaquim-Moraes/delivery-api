package com.deliverytech.delivery_api;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.deliverytech.delivery_api.Entity.Cliente;
import com.deliverytech.delivery_api.Entity.ItemPedido;
import com.deliverytech.delivery_api.Entity.Pedido;
import com.deliverytech.delivery_api.Entity.Produto;
import com.deliverytech.delivery_api.Entity.Restaurant;
import com.deliverytech.delivery_api.Repository.ClienteRepository;
import com.deliverytech.delivery_api.Repository.PedidoRepository;
import com.deliverytech.delivery_api.Repository.ProdutoRepository;
import com.deliverytech.delivery_api.Repository.RestauranteRepository;

@Component
public class DataLoader implements CommandLineRunner {
    
    private final ClienteRepository clienteRepository;
    
    private final PedidoRepository pedidoRepository;
    
    private final RestauranteRepository restauranteRepository;
    
    private final ProdutoRepository produtoRepository;
    
    public DataLoader(ClienteRepository clienteRepository, 
                      PedidoRepository pedidoRepository, 
                      RestauranteRepository restauranteRepository, 
                      ProdutoRepository produtoRepository) {
        this.clienteRepository = clienteRepository;
        this.pedidoRepository = pedidoRepository;
        this.restauranteRepository = restauranteRepository;
        this.produtoRepository = produtoRepository;
    }
    
    
    @Override
    @Transactional
    @SuppressWarnings("null")
    public void run(String... args) throws Exception {
        
        // Limpar dados para garantir um teste limpo (opcional)
        pedidoRepository.deleteAll();
        produtoRepository.deleteAll();
        restauranteRepository.deleteAll();
        clienteRepository.deleteAll();
        
        System.out.println("\n--- 💾 2.1 INSERÇÃO DE DADOS DE TESTE ---");
        
        // --- ENTIDADES PRINCIPAIS ---

        // 1. Inserir 3 clientes diferentes
        Cliente c1 = new Cliente("Ana Silva", "ana.silva@teste.com", true);
        Cliente c2 = new Cliente("Bruno Mendes", "bruno.mendes@teste.com", false);
        Cliente c3 = new Cliente("Carla Rocha", "carla.rocha@teste.com", true);
        clienteRepository.saveAll(Arrays.asList(c1, c2, c3));
        System.out.println("Clientes inseridos: 3");

        // 2. Inserir 2 restaurantes de categorias distintas
        Restaurant r1 = new Restaurant("O Rei do Hambúrguer", "Lanches",true, 10);
        Restaurant r2 = new Restaurant("Tempero Caseiro", "Comida",true,8);
        restauranteRepository.saveAll(Arrays.asList(r1, r2));
        System.out.println("Restaurantes inseridos: 2");

        // 3. Inserir 5 produtos variados
        Produto p1 = new Produto("Lache", "Lanche", true,25.00, r1);
        Produto p2 = new Produto("Acompanhamento", "Acompanhamento", true,15.00, r1);
        Produto p3 = new Produto("Bebida", "Bebida", false,6.00, r1);
        Produto p4 = new Produto("Comida", "Comida", true,38.00, r2);
        Produto p5 = new Produto("Bebida", "Bebida", true,12.00, r2);
        produtoRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5));
        System.out.println("Produtos inseridos: 5");

        // 4. Inserir 2 pedidos com itens
        // Pedido 1: Cliente Ana (r1)
        Pedido ped1 = new Pedido(c1);
        ped1.addItem(new ItemPedido(ped1, p1, 1)); // 1 Cheeseburger
        ped1.addItem(new ItemPedido(ped1, p3, 2)); // 2 Refrigerantes
        pedidoRepository.save(ped1);
        
        // Pedido 2: Cliente Bruno (r2)
        Pedido ped2 = new Pedido(c2);
        ped2.addItem(new ItemPedido(ped2, p4, 1)); // 1 Feijoada
        ped2.addItem(new ItemPedido(ped2, p5, 1)); // 1 Suco
        pedidoRepository.save(ped2);

        System.out.println("Pedidos inseridos: 2");
        System.out.println("----------------------------------------\n");

        // --- VALIDAÇÃO DAS CONSULTAS ---

        System.out.println("--- 🔎 2.2 VALIDAÇÃO DAS CONSULTAS E RELACIONAMENTOS ---");
        
        // 1. Testar consultas derivadas (Exemplos)

        // 1.1 Buscar cliente por e-mail (Derivada)
        System.out.println("\n*** 1.1 Busca Cliente por Email: ***");
        clienteRepository.findByEmail("ana.silva@teste.com").ifPresent(c -> 
           System.out.println("✓ Cliente encontrado: " + c.getNome()));

        // 1.2 Buscar restaurantes por categoria (Derivada)
        System.out.println("\n*** 1.2 Restaurantes por Categoria (FAST_FOOD): ***");
        List<Restaurant> fastFood = restauranteRepository.findByCategoria("FAST_FOOD");
        fastFood.forEach(r -> System.out.println("✓ Restaurante: " + r.getNome()));
        

        // 2. Verificar relacionamentos entre entidades
        
        // 2.1 Verificar Pedidos de um Cliente (Relacionamento Cliente -> Pedido)
        System.out.println("\n*** 2.1 Pedidos do Cliente 'Ana Silva': ***");
        Cliente ana = clienteRepository.findByEmail("ana.silva@teste.com").get(); // Busca o cliente novamente
        List<Pedido> pedidosDaAna = pedidoRepository.findByCliente(ana);
        
        if (!pedidosDaAna.isEmpty()) {
            System.out.println("✓ Pedido #" + pedidosDaAna.get(0).getId() + " encontrado.");
        } else {
            System.out.println("✗ Nenhum pedido encontrado para Ana.");
        }
        
        // 2.2 Verificar Itens de um Pedido (Relacionamento Pedido -> ItemPedido -> Produto)
        System.out.println("\n*** 2.2 Itens do Pedido #" + ped1.getId() + ": ***");
        Pedido pedidoRecuperado = pedidoRepository.findById(ped1.getId()).orElse(null);
        
        if (pedidoRecuperado != null && !pedidoRecuperado.getItens().isEmpty()) {
            System.out.println("✓ Pedido recuperado com " + pedidoRecuperado.getItens().size() + " itens:");
        } else {
             System.out.println("✗ Pedido não encontrado ou sem itens.");
        }
        
        // 2.3 Verificar Produtos de um Restaurante (Relacionamento Restaurante -> Produto)
        System.out.println("\n*** 2.3 Produtos do Restaurante 'Tempero Caseiro': ***");
        List<Produto> produtosR2 = produtoRepository.findByRestaurante(r2);
        produtosR2.forEach(p -> System.out.println("✓ Produto: " + p.getCategoria()));
        
        System.out.println("\n--- VALIDAÇÃO CONCLUÍDA ---");
    }


}
