package com.fabricaagricola.bdfabrica.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fabricaagricola.bdfabrica.model.OrdemProducao;

@Repository
public class OrdemProducaoRepository {

    private final DataSource dataSource;

    @Autowired
    public OrdemProducaoRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Salvar Ordem de Produção
    public OrdemProducao save(OrdemProducao ordemProducao) {
        String sql = "INSERT INTO OrdemProducao (produto_fabricado, quantidade_produto, data_inicio, data_final, descricao, id_dependente, id_requisitado) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, ordemProducao.getProdutoFabricado());
            stmt.setInt(2, ordemProducao.getQuantidadeProduto());
            stmt.setDate(3, Date.valueOf(ordemProducao.getDataInicio()));
            stmt.setDate(4, Date.valueOf(ordemProducao.getDataFinal()));
            stmt.setString(5, ordemProducao.getDescricao());
            if (ordemProducao.getIdDependente() != null) {
                stmt.setInt(6, ordemProducao.getIdDependente());
            } else {
                stmt.setNull(6, java.sql.Types.INTEGER);
            }
            if (ordemProducao.getIdRequisitado() != null) {
                stmt.setInt(7, ordemProducao.getIdRequisitado());
            } else {
                stmt.setNull(7, java.sql.Types.INTEGER);
            }

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    ordemProducao.setIdOrdem(generatedKeys.getInt(1));
                }
            }

            return ordemProducao;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar Ordem de Produção", e);
        }
    }

    // Buscar Ordem de Produção por ID
    public Optional<OrdemProducao> findById(int id) {
        String sql = "SELECT * FROM OrdemProducao WHERE id_ordem = ?";

        try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    OrdemProducao ordemProducao = new OrdemProducao(
                        rs.getInt("id_ordem"),
                        rs.getInt("id_dependente"),
                        rs.getInt("id_requisitado"),
                        rs.getString("produto_fabricado"),
                        rs.getInt("quantidade_produto"),
                        rs.getDate("data_inicio").toLocalDate(),
                        rs.getDate("data_final").toLocalDate(),
                        rs.getString("descricao")
                    );
                    return Optional.of(ordemProducao);
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar ordem produção por id", e);
        }
    }

    // Listar todas as Ordens de Produção
    public List<OrdemProducao> findAll() {
        String sql = "SELECT * FROM OrdemProducao";
        List<OrdemProducao> ordemProducaoList = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                OrdemProducao ordemProducao = new OrdemProducao(
                    rs.getInt("id_ordem"),
                    rs.getInt("id_dependente"),
                    rs.getInt("id_requisitado"),
                    rs.getString("produto_fabricado"),
                    rs.getInt("quantidade_produto"),
                    rs.getDate("data_inicio").toLocalDate(),
                    rs.getDate("data_final").toLocalDate(),
                    rs.getString("descricao")
                );
                ordemProducaoList.add(ordemProducao);
            }
            return ordemProducaoList;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar todas as ordens de produção", e);
        }
    }

    // Atualizar Ordem de Produção
    public OrdemProducao update(OrdemProducao ordemProducao) {
        String sql = "UPDATE OrdemProducao SET id_dependente = ?, id_requisitado = ?, produto_fabricado = ?, "
                   + "quantidade_produto = ?, data_final = ?, descricao = ? WHERE id_ordem = ?";

        try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (ordemProducao.getIdDependente() != null) {
                stmt.setInt(1, ordemProducao.getIdDependente());
            } else {
                stmt.setNull(1, java.sql.Types.INTEGER);
            }
            if (ordemProducao.getIdRequisitado() != null) {
                stmt.setInt(2, ordemProducao.getIdRequisitado());
            } else {
                stmt.setNull(2, java.sql.Types.INTEGER);
            }

            stmt.setString(3, ordemProducao.getProdutoFabricado());
            stmt.setInt(4, ordemProducao.getQuantidadeProduto());
            stmt.setDate(5, Date.valueOf(ordemProducao.getDataFinal()));
            stmt.setString(6, ordemProducao.getDescricao());
            stmt.setInt(7, ordemProducao.getIdOrdem());

            stmt.executeUpdate();
            return ordemProducao;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar ordem de produção", e);
        }
    }

    // Deletar Ordem de Produção
    public void delete(int id) {
        String sql = "DELETE FROM OrdemProducao WHERE id_ordem = ?";

        try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar ordem de produção", e);
        }
    }
}
