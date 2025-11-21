package com.deliverytech.delivery_api.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Delivery Tech API")
                        .version("1.0.0")
                        .description("API de Gerenciamento de Pedidos e Restaurantes\n\n" +
                                "Documentação completa da API RESTful para gerenciar pedidos, produtos, restaurantes e clientes.")
                        .contact(new Contact()
                                .name("Delivery Tech Support")
                                .email("support@deliverytech.com")
                                .url("https://deliverytech.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                .addServersItem(new Server()
                        .url("http://localhost:8080")
                        .description("Servidor de Desenvolvimento"))
                .addServersItem(new Server()
                        .url("https://api.deliverytech.com")
                        .description("Servidor de Produção"));
    }
}
