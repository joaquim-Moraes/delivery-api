package com.deliverytech.delivery_api.Controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deliverytech.delivery_api.DTO.ClienteAtivo;
import com.deliverytech.delivery_api.DTO.PedidosPorPeriodo;
import com.deliverytech.delivery_api.DTO.ProdutoMaisVendido;
import com.deliverytech.delivery_api.DTO.RelatorioVendas;
import com.deliverytech.delivery_api.Service.RelatorioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/relatorios")
@Tag(name = "Relatórios", description = "Endpoints de relatórios analíticos e estatísticas da plataforma")
public class RelatorioController {

    @Autowired
    private RelatorioService relatorioService;


    @GetMapping("/vendas-por-restaurante")
    @Operation(summary = "Vendas por Restaurante", 
               description = "Retorna relatório de vendas agrupadas por restaurante em um período. " +
                           "Se nenhuma data for informada, usa os últimos 30 dias.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Relatório gerado com sucesso",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = RelatorioVendas.class))),
        @ApiResponse(responseCode = "400", description = "Parâmetros inválidos")
    })
    public List<RelatorioVendas> relatorioVendasPorRestaurante(
            @Parameter(description = "Data inicial do período (formato: YYYY-MM-DD). Padrão: 30 dias atrás", 
                      example = "2025-10-01")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            
            @Parameter(description = "Data final do período (formato: YYYY-MM-DD). Padrão: hoje", 
                      example = "2025-10-31")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        
        if (dataFim == null) {
            dataFim = LocalDate.now();
        }
        if (dataInicio == null) {
            dataInicio = dataFim.minusDays(30);
        }
        
        return relatorioService.relatorioVendasPorRestaurante(dataInicio, dataFim);
    }


    @GetMapping("/produtos-mais-vendidos")
    @Operation(summary = "Produtos Mais Vendidos", 
               description = "Retorna o Top 10 de produtos mais vendidos em um período. " +
                           "Classificação por quantidade de unidades vendidas.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Top 10 produtos gerado com sucesso",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = ProdutoMaisVendido.class))),
        @ApiResponse(responseCode = "400", description = "Parâmetros inválidos")
    })
    public List<ProdutoMaisVendido> relatorioProdutosMaisVendidos(
            @Parameter(description = "Data inicial do período (formato: YYYY-MM-DD). Padrão: 30 dias atrás", 
                      example = "2025-10-01")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            
            @Parameter(description = "Data final do período (formato: YYYY-MM-DD). Padrão: hoje", 
                      example = "2025-10-31")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        
        if (dataFim == null) {
            dataFim = LocalDate.now();
        }
        if (dataInicio == null) {
            dataInicio = dataFim.minusDays(30);
        }
        
        return relatorioService.relatorioProdutosMaisVendidos(dataInicio, dataFim);
    }


    @GetMapping("/clientes-ativos")
    @Operation(summary = "Clientes Mais Ativos", 
               description = "Retorna o Top 10 de clientes mais ativos em um período. " +
                           "Classificação por gasto total.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Top 10 clientes gerado com sucesso",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = ClienteAtivo.class))),
        @ApiResponse(responseCode = "400", description = "Parâmetros inválidos")
    })
    public List<ClienteAtivo> relatorioClientesMaisAtivos(
            @Parameter(description = "Data inicial do período (formato: YYYY-MM-DD). Padrão: 30 dias atrás", 
                      example = "2025-10-01")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            
            @Parameter(description = "Data final do período (formato: YYYY-MM-DD). Padrão: hoje", 
                      example = "2025-10-31")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        
        if (dataFim == null) {
            dataFim = LocalDate.now();
        }
        if (dataInicio == null) {
            dataInicio = dataFim.minusDays(30);
        }
        
        return relatorioService.relatorioClientesMaisAtivos(dataInicio, dataFim);
    }


    @GetMapping("/pedidos-por-periodo")
    @Operation(summary = "Pedidos por Período", 
               description = "Retorna análise de pedidos agrupados por dia. " +
                           "Inclui contagem de pedidos, valor de vendas e status dos pedidos.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Análise por período gerada com sucesso",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = PedidosPorPeriodo.class))),
        @ApiResponse(responseCode = "400", description = "Datas inválidas ou período inválido")
    })
    public List<PedidosPorPeriodo> relatorioPedidosPorPeriodo(
            @Parameter(description = "Data inicial do período (formato: YYYY-MM-DD). Obrigatória.", 
                      example = "2025-11-01", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            
            @Parameter(description = "Data final do período (formato: YYYY-MM-DD). Obrigatória.", 
                      example = "2025-11-30", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        
        if (dataInicio.isAfter(dataFim)) {
            throw new IllegalArgumentException("Data de início deve ser menor ou igual a data de fim");
        }
        
        return relatorioService.relatorioPedidosPorPeriodo(dataInicio, dataFim);
    }
}

