package com.fabricaagricola.bdfabrica.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fabricaagricola.bdfabrica.enums.TipoMovimentacao;
import com.fabricaagricola.bdfabrica.model.Estoque;

@Repository
public class EstoqueRepository {
	private final DataSource dataSource;
	
	@Autowired
	public EstoqueRepository(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	//Inserir 
	public Estoque save(Estoque estoque) {
		String sql = "INSERT INTO Estoque (tipo_movimentacao, data_movimentacao, hora_movimentacao) VALUES (?, ?, ?)";
		
		 try (Connection conn = dataSource.getConnection();
				 PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
			 	stmt.setString(1, estoque.getTipoMovimentacao().name());
	            stmt.setDate(2, Date.valueOf(estoque.getDataMovimentacao()));
	            stmt.setTime(3, Time.valueOf(estoque.getHoraMovimentacao()));

	            stmt.executeUpdate();

	            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
	                if (generatedKeys.next()) {
	                    estoque.setIdEstoque(generatedKeys.getInt(1));
	                }
	            }
	            return estoque;
		 }catch (SQLException e) {
				throw new RuntimeException("Erro ao salvar estoque", e);
			}
	}
	
	// buscar por id
	public Optional<Estoque> findById(int id){
		String sql = "SELECT * FROM Estoque WHERE id_estoque = ?";
		
		 try (Connection conn = dataSource.getConnection();
	             PreparedStatement stmt = conn.prepareStatement(sql)) {
	            stmt.setInt(1, id);

	            try (ResultSet rs = stmt.executeQuery()) {
	                if (rs.next()) {
	                	Estoque estoque = new Estoque(
	                        rs.getInt("id_estoque"),
	                        TipoMovimentacao.valueOf(rs.getString("tipo_movimentacao")),
	                        rs.getDate("data_movimentacao").toLocalDate(),
	                        rs.getTime("hora_movimentacao").toLocalTime()
	                    );
	                    return Optional.of(estoque);
	                }
	            }
	            return Optional.empty();
	        } catch (SQLException e) {
	            throw new RuntimeException("Erro ao buscar estoque por id", e);
	        }
	}
	
	// listar todos
	public List<Estoque> findAll(){
    	String sql = "SELECT * FROM Estoque";
    	List<Estoque> estoqueList = new ArrayList<>();
    	
    	try (Connection conn = dataSource.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)){
    		
    		while (rs.next()) {
    			Estoque estoque = new Estoque(
    					rs.getInt("id_estoque"),
                        TipoMovimentacao.valueOf(rs.getString("tipo_movimentacao")),
                        rs.getDate("data_movimentacao").toLocalDate(),
                        rs.getTime("hora_movimentacao").toLocalTime()
				);
    			estoqueList.add(estoque);
			}
			return estoqueList;
    	}catch (SQLException e) {
            throw new RuntimeException("Erro ao listar todos os estoques", e);
        }
    }
	
	// atualizar
	public Estoque update(Estoque estoque) {
	    String sql = "UPDATE Estoque SET tipo_movimentacao = ?, data_movimentacao = CURRENT_DATE, hora_movimentacao = CURRENT_TIME WHERE id_estoque = ?";
	    
	    try (Connection conn = dataSource.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setString(1, estoque.getTipoMovimentacao().name());
	        stmt.setInt(2, estoque.getIdEstoque());

	        int linhasAfetadas = stmt.executeUpdate();

	        if (linhasAfetadas == 0) {
	            throw new RuntimeException("Estoque com ID " + estoque.getIdEstoque() + " não encontrado para atualização.");
	        }

	        return estoque;

	    } catch (SQLException e) {
	        throw new RuntimeException("Erro ao atualizar Estoque", e);
	    }
	}
	
	// deletar
	public void delete(int id) {
        String sql = "DELETE FROM Estoque WHERE id_estoque = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar estoque", e);
        }
    }
}
