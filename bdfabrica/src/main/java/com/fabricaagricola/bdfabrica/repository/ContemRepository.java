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

import com.fabricaagricola.bdfabrica.model.Contem;

@Repository
public class ContemRepository {
	private final DataSource dataSource;

    @Autowired
    public ContemRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    
    // inserir
    public void save(Contem contem) {
    	if (!pedidoExiste(contem.getNumero())) {
            throw new IllegalArgumentException("número de pedido informado não existe.");
        }
    	
    	if (!produtoExiste(contem.getIdProduto())) {
            throw new IllegalArgumentException("id informado não existe.");
        }
    	
    	if (produtoJaVinculado(contem.getIdProduto())) {
            throw new IllegalArgumentException("Este produto já está vinculado a um pedido.");
        }
    	String sql = "INSERT INTO Contem (numero, id_produto) VALUES (?, ?)";
    	
    	try (Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

               stmt.setString(1, contem.getNumero());
               stmt.setInt(2, contem.getIdProduto());
               stmt.executeUpdate();

           } catch (SQLException e) {
               throw new RuntimeException("Erro ao salvar relação Contem", e);
           }
    }
    
    //conferir se pedido existe
    private boolean pedidoExiste(String numero) {
        String sql = "SELECT 1 FROM Pedido WHERE numero = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, numero);
            return stmt.executeQuery().next();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar existência do Pedido", e);
        }
    }
    
    // conferir se produto existe
    private boolean produtoExiste(int idProduto) {
        String sql = "SELECT 1 FROM ProdutoAcabado WHERE id_produto = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idProduto);
            return stmt.executeQuery().next();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar existência do produto", e);
        }
    }
    
    // conferir se produto já está vinculado a um pedido
    private boolean produtoJaVinculado(int idProduto) {
        String sql = "SELECT 1 FROM Contem WHERE id_produto = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idProduto);
            return stmt.executeQuery().next();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar existência da produto", e);
        }
    }
    
    // listar todos
    public List<Contem> findAll() {
        List<Contem> lista = new ArrayList<>();
        String sql = "SELECT numero, id_produto FROM Contem";

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Contem(
                        rs.getString("numero"),
                        rs.getInt("id_produto")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar relações Contem", e);
        }

        return lista;
    }
    
    // buscar produtos associados a um pedido
    public List<Contem> findByNumero(String numero) {
        List<Contem> lista = new ArrayList<>();
        String sql = "SELECT numero, id_produto FROM Contem WHERE numero = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, numero);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Contem(
                            rs.getString("numero"),
                            rs.getInt("id_produto")
                    ));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar por numero em Contem", e);
        }

        return lista;
    }
    
    // achar um relacionamento específico
    public Contem findByNumeroAndId(String numero, int idProduto) {
        String sql = "SELECT numero, id_produto FROM Contem WHERE numero = ? AND id_produto = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, numero);
            stmt.setInt(2, idProduto);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Contem(
                		rs.getString("numero"), 
                		rs.getInt("id_produto")
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar relacionamento Contem", e);
        }

        return null;
    }
    
    // deletar relacionamento
    public void delete(String numero, int idProduto) {
        String sql = "DELETE FROM Contem WHERE numero = ? AND id_produto = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, numero);
            stmt.setInt(2, idProduto);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar relação Contem", e);
        }
    }
}
