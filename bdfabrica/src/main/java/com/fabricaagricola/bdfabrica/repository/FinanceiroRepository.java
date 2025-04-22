package com.fabricaagricola.bdfabrica.repository;

import com.fabricaagricola.bdfabrica.model.Financeiro;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FinanceiroRepository {

    private final String url = "jdbc:mysql://localhost:3307/bdfabrica";
    private final String user = "springboot_user";
    private final String password = "springboot_password";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public Financeiro save(Financeiro financeiro) throws SQLException {
        String sql = "INSERT INTO Financeiro (historico_lucro, historico_prejuizo, data_atualizacao) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setFloat(1, financeiro.getHistoricoLucro());
            stmt.setFloat(2, financeiro.getHistoricoPrejuizo());
            stmt.setDate(3, Date.valueOf(LocalDate.now()));
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    financeiro.setIdFinanceiro(rs.getInt(1));
                }
            }
        }
        return financeiro;
    }

    public List<Financeiro> findAll() throws SQLException {
        List<Financeiro> lista = new ArrayList<>();
        String sql = "SELECT * FROM Financeiro";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Financeiro f = new Financeiro();
                f.setIdFinanceiro(rs.getInt("id_financeiro"));
                f.setHistoricoLucro(rs.getFloat("historico_lucro"));
                f.setHistoricoPrejuizo(rs.getFloat("historico_prejuizo"));
                f.setDataAtualizacao(rs.getDate("data_atualizacao").toLocalDate());
                lista.add(f);
            }
        }
        return lista;
    }

    public Optional<Financeiro> findById(int id) throws SQLException {
        String sql = "SELECT * FROM Financeiro WHERE id_financeiro = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Financeiro f = new Financeiro();
                    f.setIdFinanceiro(rs.getInt("id_financeiro"));
                    f.setHistoricoLucro(rs.getFloat("historico_lucro"));
                    f.setHistoricoPrejuizo(rs.getFloat("historico_prejuizo"));
                    f.setDataAtualizacao(rs.getDate("data_atualizacao").toLocalDate());
                    return Optional.of(f);
                }
            }
        }
        return Optional.empty();
    }

    public boolean update(int id, Financeiro atualizado) throws SQLException {
        String sql = "UPDATE Financeiro SET historico_lucro = ?, historico_prejuizo = ?, data_atualizacao = ? WHERE id_financeiro = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setFloat(1, atualizado.getHistoricoLucro());
            stmt.setFloat(2, atualizado.getHistoricoPrejuizo());
            stmt.setDate(3, Date.valueOf(LocalDate.now()));
            stmt.setInt(4, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deleteById(int id) throws SQLException {
        String sql = "DELETE FROM Financeiro WHERE id_financeiro = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean existsById(int id) throws SQLException {
        String sql = "SELECT 1 FROM Financeiro WHERE id_financeiro = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }
}
