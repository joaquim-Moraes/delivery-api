Joaquim Gomes de Moraes


📦 Delivery API
Sistema de gerenciamento de pedidos para a startup DeliveryTech, desenvolvido com Spring Boot e JPA. Este projeto simula uma aplicação de delivery com funcionalidades completas de cadastro, consulta e gerenciamento de clientes, restaurantes, produtos e pedidos.


🚀 Tecnologias Utilizadas

Java 21
Spring Boot
Spring Data JPA
H2 Database (para testes)
Maven
Postman(para testes de API)


📁 Estrutura do Projeto
delivery-api/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/deliverytech/delivery/
│   │   │       ├── controller/
│   │   │       ├── service/
│   │   │       ├── repository/
│   │   │       ├── entity/



📌 Funcionalidades
👥 Clientes

Cadastro de clientes
Consulta por ID e e-mail
Atualização e inativação

🍽️ Restaurantes
Cadastro e gerenciamento
Busca por nome e categoria
Controle de status ativo/inativo

🛒 Produtos
Cadastro por restaurante
Consulta por categoria e disponibilidade

📦 Pedidos
Criação de pedidos
Cálculo de valores
Rastreamento por status e cliente


🔗 Endpoints REST
Clientes
POST    /clientes
GET     /clientes
GET     /clientes/{id}
PUT     /clientes/{id}
DELETE  /clientes/{id}


Restaurantes
POST    /restaurantes
GET     /restaurantes
GET     /restaurantes/{id}
PUT     /restaurantes/{id}
DELETE  /restaurantes/{id}
GET     /restaurantes/categoria/{categoria}


Produtos
POST    /restaurantes/{id}/produtos
GET     /restaurantes/{id}/produtos
GET     /produtos/categoria/{categoria}


Pedidos
POST    /pedidos
GET     /pedidos
GET     /pedidos/cliente/{id}
PUT     /pedidos/{id}/status




🧪 Testes

Testes realizados via Postman 
Banco H2 acessível via console: http://localhost:8080/h2-console
Dados de exemplo incluídos para validação
Regras de negócio testadas: e-mail único, cálculos de pedido, status de entrega


📄 Como Executar

Clone o repositório:
git clone https://github.com/joaquim-Moraes/delivery-api.git



Navegue até o projeto:
cd delivery-api



Execute a aplicação:
./mvnw spring-boot:run



Acesse os endpoints via Postman ou navegador.


📚 Documentação Adicional

Todas as funcionalidades estão organizadas por pacotes:
controller: Camada REST
service: Regras de negócio
repository: Acesso a dados
entity: Modelos JPA
Anotações @Query utilizadas para consultas personalizadas


✅ Status do Projeto
✔️ Funcionalidades implementadas
✔️ Testes realizados
✔️ Documentação pronta para demonstração

