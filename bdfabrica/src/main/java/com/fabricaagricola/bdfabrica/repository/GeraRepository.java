package com.fabricaagricola.bdfabrica.repository;

import com.fabricaagricola.bdfabrica.model.Gera;
import com.fabricaagricola.bdfabrica.model.ProdutoAcabado;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GeraRepository {

    private final DataSource dataSource;

    @Autowired
    public GeraRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Inserir um novo relacionamento
    public void save(Gera gera) {
        String sql = "INSERT INTO Gera (id_ordem, id_produto) VALUES (?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, gera.getIdOrdem());
            stmt.setInt(2, gera.getIdProduto());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar relação Gera", e);
        }
    }

    // Buscar todos os relacionamentos
    public List<Gera> findAll() {
        List<Gera> lista = new ArrayList<>();
        String sql = "SELECT id_ordem, id_produto FROM Gera";

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Gera gera = new Gera(
                    rs.getInt("id_ordem"),
                    rs.getInt("id_produto")
                );
                lista.add(gera);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar relações Gera", e);
        }

        return lista;
    }
    
    // Buscar realcionamentos com mesmo idOrdem
    public List<Gera> findByOrdem(int idOrdem) {
    	List<Gera> lista = new ArrayList<>();
        String sql = "SELECT g.id_ordem, g.id_produto, p.descricao AS produto_descricao, p.data_finalizacao " +
                     "FROM Gera g " +
                     "JOIN ProdutoAcabado p ON g.id_produto = p.id_produto " +
                     "WHERE g.id_ordem = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idOrdem);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Gera gera = new Gera(
                    rs.getInt("id_ordem"),
                    rs.getInt("id_produto")
                );
                // Aqui, podemos mapear a descrição e data de finalização do produto (se desejar)
                ProdutoAcabado produto = new ProdutoAcabado();
                produto.setIdProduto(rs.getInt("id_produto"));
                produto.setDescricao(rs.getString("produto_descricao"));
                produto.setDataFinalizacao(rs.getDate("data_finalizacao").toLocalDate());

                gera.setProdutoAcabado(produto);
                lista.add(gera);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar produtos relacionados à ordem", e);
        }

        return lista;
    }
    
    // buscar relacionamento especidifoco
    public Gera findByOrdemAndProduto(int idOrdem, int idProduto) {
        String sql = "SELECT id_ordem, id_produto FROM Gera WHERE id_ordem = ? AND id_produto = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idOrdem);
            stmt.setInt(2, idProduto);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Gera(
                    rs.getInt("id_ordem"),
                    rs.getInt("id_produto")
                );
            } else {
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar relacionamento específico Gera", e);
        }
    }
    
    // Deletar uma relação específica
    public void delete(int idOrdem, int idProduto) {
        String sql = "DELETE FROM Gera WHERE id_ordem = ? AND id_produto = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idOrdem);
            stmt.setInt(2, idProduto);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar relação Gera", e);
        }
    }

}
