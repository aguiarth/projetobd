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

import com.fabricaagricola.bdfabrica.model.ProdutoAcabado;

@Repository
public class ProdutoAcabadoRepository {
	private final DataSource dataSource;

    @Autowired
    public ProdutoAcabadoRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    //inserir
    public ProdutoAcabado save(ProdutoAcabado produtoAcabado) {
    	String sql = "INSERT INTO ProdutoAcabado (descricao, data_finalizacao) VALUES (?, ?)";
    	
    	try (Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
    		
    		if (produtoAcabado.getDescricao() == null) {
    		    stmt.setNull(1, java.sql.Types.VARCHAR);
    		} else {
    		    stmt.setString(1, produtoAcabado.getDescricao());
    		}
    		stmt.setDate(2, Date.valueOf(produtoAcabado.getDataFinalizacao()));
    		
    		stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                	produtoAcabado.setIdProduto(generatedKeys.getInt(1));
                }
            }
            return produtoAcabado;
    	}catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar produto acabado", e);
        }
    }
    
    // buscar id
    public Optional<ProdutoAcabado> findById(int id){
    	String sql = "SELECT * FROM ProdutoAcabado WHERE id_produto = ?";
    	
    	try (Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)){
    		stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()){
            	if (rs.next()) {
            		ProdutoAcabado produtoAcabado = new ProdutoAcabado(
                        rs.getInt("id_produto"),
                        rs.getString("descricao"),
                        rs.getDate("data_finalizacao").toLocalDate()
                    );
                    return Optional.of(produtoAcabado);
                }
            }
            return Optional.empty();
    	}catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar produto acabado por id", e);
        }
    }
    
    //listar todos
    public List<ProdutoAcabado> findAll() {
        String sql = "SELECT * FROM ProdutoAcabado";
        List<ProdutoAcabado> produtoAcabadoList = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
            	ProdutoAcabado produtoAcabado = new ProdutoAcabado(
                        rs.getInt("id_produto"),
                        rs.getString("descricao"),
                        rs.getDate("data_finalizacao").toLocalDate()
                );
                produtoAcabadoList.add(produtoAcabado);
            }
            return produtoAcabadoList;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar todos os produtos acabados", e);
        }
    }
    
    // atualizar
    public ProdutoAcabado update(ProdutoAcabado produtoAcabado) {
        String sql = "UPDATE ProdutoAcabado SET descricao = ?, data_finalizacao = ? WHERE id_produto = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, produtoAcabado.getDescricao());
            stmt.setDate(2, Date.valueOf(produtoAcabado.getDataFinalizacao()));
            stmt.setInt(3, produtoAcabado.getIdProduto());

            stmt.executeUpdate();
            return produtoAcabado;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar produto acabado", e);
        }
    }
    
    // Deletar
    public void delete(int id) {
        String sql = "DELETE FROM ProdutoAcabado WHERE id_produto = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar produto", e);
        }
    }
}
