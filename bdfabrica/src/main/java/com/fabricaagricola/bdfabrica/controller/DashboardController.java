package com.fabricaagricola.bdfabrica.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fabricaagricola.bdfabrica.repository.DashboardRepository.DashboardResumoDTO;
import com.fabricaagricola.bdfabrica.service.DashboardService;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/visao-geral")
    public DashboardResumoDTO getResumoGeral() {
        return dashboardService.obterResumoGeral();
    }
}
