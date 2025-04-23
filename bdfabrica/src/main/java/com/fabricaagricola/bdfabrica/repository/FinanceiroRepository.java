package com.fabricaagricola.bdfabrica.repository;

import com.fabricaagricola.bdfabrica.model.Financeiro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class FinanceiroRepository {

    private final DataSource dataSource;

    @Autowired
    public FinanceiroRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Inserir
    public Financeiro save(Financeiro financeiro) {
        String sql = "INSERT INTO Financeiro (historico_lucro, historico_prejuizo, data_atualizacao) VALUES (?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setFloat(1, financeiro.getHistoricoLucro());
            stmt.setFloat(2, financeiro.getHistoricoPrejuizo());
            stmt.setDate(3, Date.valueOf(financeiro.getDataAtualizacao()));

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    financeiro.setIdFinanceiro(generatedKeys.getInt(1));
                }
            }
            return financeiro;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar financeiro", e);
        }
    }

    // Buscar por ID
    public Optional<Financeiro> findById(int id) {
        String sql = "SELECT * FROM Financeiro WHERE id_financeiro = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Financeiro financeiro = new Financeiro(
                        rs.getInt("id_financeiro"),
                        rs.getFloat("historico_lucro"),
                        rs.getFloat("historico_prejuizo"),
                        rs.getDate("data_atualizacao").toLocalDate()
                    );
                    return Optional.of(financeiro);
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar financeiro por id", e);
        }
    }

    // Listar todos
    public List<Financeiro> findAll() {
        String sql = "SELECT * FROM Financeiro";
        List<Financeiro> financeiroList = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Financeiro financeiro = new Financeiro(
                    rs.getInt("id_financeiro"),
                    rs.getFloat("historico_lucro"),
                    rs.getFloat("historico_prejuizo"),
                    rs.getDate("data_atualizacao").toLocalDate()
                );
                financeiroList.add(financeiro);
            }
            return financeiroList;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar todos os financeiros", e);
        }
    }

    // Atualizar
    public Financeiro update(Financeiro financeiro) {
        String sql = "UPDATE Financeiro SET historico_lucro = ?, historico_prejuizo = ?, data_atualizacao = ? WHERE id_financeiro = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setFloat(1, financeiro.getHistoricoLucro());
            stmt.setFloat(2, financeiro.getHistoricoPrejuizo());
            stmt.setDate(3, Date.valueOf(financeiro.getDataAtualizacao()));
            stmt.setInt(4, financeiro.getIdFinanceiro());

            stmt.executeUpdate();
            return financeiro;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar financeiro", e);
        }
    }

    // Deletar
    public void delete(int id) {
        String sql = "DELETE FROM Financeiro WHERE id_financeiro = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar financeiro", e);
        }
    }
}
