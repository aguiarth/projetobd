package com.fabricaagricola.bdfabrica.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fabricaagricola.bdfabrica.repository.DashboardRepository.DashboardResumoDTO;
import com.fabricaagricola.bdfabrica.repository.DashboardRepository.ContasResumoDTO;
import com.fabricaagricola.bdfabrica.repository.DashboardRepository.LucroMensalDTO;
import com.fabricaagricola.bdfabrica.service.DashboardService;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;
    
    @Autowired
    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/pedidos-resumo")
    public ResponseEntity<DashboardResumoDTO> getPedidosResumo() {
        DashboardResumoDTO resumo = dashboardService.getResumoGeralPedidos();
        return ResponseEntity.ok(resumo);
    }
    
    @GetMapping("/contas-resumo")
    public ResponseEntity<ContasResumoDTO> getResumoContas() {
    	ContasResumoDTO resumo = dashboardService.getResumoContas();
        return ResponseEntity.ok(resumo);
    }
    
    @GetMapping("/clientes-total")
    public ResponseEntity<Integer> getTotalClientes() {
        int totalClientes = dashboardService.getTotalClientes();
        return ResponseEntity.ok(totalClientes);
    }
    
    @GetMapping("/lucro-mensal-historico")
    public ResponseEntity<List<LucroMensalDTO>> getLucroMensalHistorico(
            @RequestParam(defaultValue = "12") int ultimosMeses) {
        List<LucroMensalDTO> historico = dashboardService.getLucroMensalHistorico(ultimosMeses);
        return ResponseEntity.ok(historico);
    }
    
    @GetMapping("/lotes-registrados-total")
    public ResponseEntity<Integer> getTotalLotesRegistrados() {
        int totalLotes = dashboardService.getTotalLotesRegistrados();
        return ResponseEntity.ok(totalLotes);
    }
    
    @GetMapping("/lotes-custo-total")
    public ResponseEntity<BigDecimal> getValorTotalCustoLotes() {
        BigDecimal totalCusto = dashboardService.getValorTotalCustoLotes();
        return ResponseEntity.ok(totalCusto);
    }
}
