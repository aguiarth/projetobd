package com.fabricaagricola.bdfabrica.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fabricaagricola.bdfabrica.model.Consome;

@Repository
public class ConsomeRepository {
    private final DataSource dataSource;

    @Autowired
    public ConsomeRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Inserir relação entre matéria-prima e ordem de produção
    public void save(Consome consome) {
        if (!materiaPrimaExiste(consome.getIdMateriaPrima())) {
            throw new IllegalArgumentException("Matéria-prima informada não existe.");
        }
        if (!ordemProducaoExiste(consome.getIdOrdem())) {
            throw new IllegalArgumentException("Ordem de produção informada não existe.");
        }
        if (materiaPrimaJaVinculada(consome.getIdMateriaPrima())) {
            throw new IllegalArgumentException("Esta matéria-prima já está vinculada a uma ordem de produção.");
        }

        String sql = "INSERT INTO Consome(id_materia_prima, id_ordem) VALUES (?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, consome.getIdMateriaPrima());
            stmt.setInt(2, consome.getIdOrdem());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar relação Consome.", e);
        }
    }

    // Verificar se a matéria-prima existe
    private boolean materiaPrimaExiste(int idMateriaPrima) {
        String sql = "SELECT 1 FROM MateriaPrima WHERE id_materia_prima = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idMateriaPrima);
            return stmt.executeQuery().next();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar existência de matéria-prima.", e);
        }
    }

    // Verificar se a ordem de produção existe
    private boolean ordemProducaoExiste(int idOrdem) {
        String sql = "SELECT 1 FROM OrdemProducao WHERE id_ordem = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idOrdem);
            return stmt.executeQuery().next();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar existência de ordem de produção.", e);
        }
    }

    // Verificar se a matéria-prima já está vinculada a alguma ordem
    private boolean materiaPrimaJaVinculada(int idMateriaPrima) {
        String sql = "SELECT 1 FROM Consome WHERE id_materia_prima = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idMateriaPrima);
            return stmt.executeQuery().next();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar vínculo da matéria-prima.", e);
        }
    }

    // Listar todas as relações
    public List<Consome> findAll() {
        List<Consome> lista = new ArrayList<>();
        String sql = "SELECT id_materia_prima, id_ordem FROM Consome";

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Consome consome = new Consome(
                    rs.getInt("id_materia_prima"),
                    rs.getInt("id_ordem")
                );
                lista.add(consome);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar relações Consome.", e);
        }

        return lista;
    }

    // Buscar relações por id da ordem
    public List<Consome> findByIdOrdem(int idOrdem) {
        List<Consome> lista = new ArrayList<>();
        String sql = "SELECT id_materia_prima, id_ordem FROM Consome WHERE id_ordem = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idOrdem);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Consome(
                        rs.getInt("id_materia_prima"),
                        rs.getInt("id_ordem")
                    ));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar por id_ordem em Consome.", e);
        }

        return lista;
    }

    // achar um relacionamento específico
    public Consome findByIdMateriaAndIdOrdem(int idOrdem, int idMateriaPrima) {
        String sql = "SELECT id_materia_prima, id_ordem FROM Consome WHERE id_ordem = ? AND id_materia_prima = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idOrdem);
            stmt.setInt(2, idMateriaPrima);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Consome(
                    rs.getInt("id_materia_prima"),
                    rs.getInt("id_ordem")
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar relacionamento Consome", e);
        }

        return null;
    }


    // Deletar relação
    public void delete(int idOrdem, int idMateriaPrima) {
        String sql = "DELETE FROM Consome WHERE id_ordem = ? AND id_materia_prima = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

        	stmt.setInt(1, idOrdem);
            stmt.setInt(2, idMateriaPrima);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar relação Consome.", e);
        }
    }
}
