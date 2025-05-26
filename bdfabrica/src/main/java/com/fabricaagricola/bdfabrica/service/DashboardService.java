package com.fabricaagricola.bdfabrica.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fabricaagricola.bdfabrica.repository.DashboardRepository;
import com.fabricaagricola.bdfabrica.repository.DashboardRepository.DashboardResumoDTO;

@Service
public class DashboardService {

    private final DashboardRepository dashboardRepository;

    public DashboardService(DashboardRepository dashboardRepository) {
        this.dashboardRepository = dashboardRepository;
    }

    @Transactional(readOnly = true)
    public DashboardResumoDTO obterResumoGeral() {
        return dashboardRepository.getResumoGeral();
    }
}
