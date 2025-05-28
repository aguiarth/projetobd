package com.fabricaagricola.bdfabrica.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fabricaagricola.bdfabrica.repository.DashboardRepository;
import com.fabricaagricola.bdfabrica.repository.DashboardRepository.DashboardResumoDTO;
import com.fabricaagricola.bdfabrica.repository.DashboardRepository.ContasResumoDTO;
import com.fabricaagricola.bdfabrica.repository.DashboardRepository.LucroMensalDTO;

@Service
public class DashboardService {

    private final DashboardRepository dashboardRepository;

    public DashboardService(DashboardRepository dashboardRepository) {
        this.dashboardRepository = dashboardRepository;
    }

    public DashboardResumoDTO getResumoGeralPedidos() {
        return dashboardRepository.getResumoGeralPedidos();
    }
    
    public ContasResumoDTO getResumoContas() {
        return dashboardRepository.getResumoContas();
    }
    
    public int getTotalClientes() {
        return dashboardRepository.getTotalClientes();
    }
    
    public int getTotalLotesRegistrados() {
        return dashboardRepository.getTotalLotesRegistrados();
    }
    
    public BigDecimal getValorTotalCustoLotes() {
        return dashboardRepository.getValorTotalCustoLotes();
    }
    
    public List<LucroMensalDTO> getLucroMensalHistorico(int ultimosMeses) {
        return dashboardRepository.getLucroMensalHistorico(ultimosMeses);
    }
}
