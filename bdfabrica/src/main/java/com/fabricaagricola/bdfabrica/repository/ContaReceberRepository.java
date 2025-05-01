package com.fabricaagricola.bdfabrica.repository;

import com.fabricaagricola.bdfabrica.model.ContaReceber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ContaReceberRepository {

	private final DataSource dataSource;
	
	@Autowired
	public ContaReceberRepository(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	// Inserindo
	public ContaReceber save(ContaReceber contaReceber) {
		String sql = "INSERT INTO ContaReceber (id_conta) VALUES (?)";
		
		try (Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)){
					
					stmt.setInt(1, contaReceber.getIdConta());
					stmt.executeUpdate();
										
					return contaReceber;
				} catch (SQLException e) {
					throw new RuntimeException("Erro ao salvar conta a receber", e);
				}
	}
	
	// Buscar por ID
	public Optional<ContaReceber> findById(int id){
		String sql = "SELECT * FROM ContaReceber WHERE id_conta = ?";
		
		try (Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, id);
			
			try (ResultSet rs = stmt.executeQuery()){
				if (rs.next()) {
					ContaReceber contaReceber = new ContaReceber(
							rs.getInt("id_conta")
					);
					return Optional.of(contaReceber);
				}
			}
			return Optional.empty();
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao buscar conta a receber por id", e);
		}
	}
	
	// Listar all
	public List<ContaReceber> findAll(){
		String sql = "SELECT * FROM ContaReceber";
		List<ContaReceber> contaReceberList = new ArrayList<>();
		
		try (Connection conn = dataSource.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				ContaReceber contaReceber = new ContaReceber(
						rs.getInt("id_conta")
				);
				contaReceberList.add(contaReceber);
			}
			return contaReceberList;
		} catch (SQLException e) {
            throw new RuntimeException("Erro ao listar todos as contas a receber", e);
        }
	}
	
	// Deletar
	public void delete(int id) {
		String sql = "DELETE FROM ContaReceber WHERE id_conta = ?";
		
		try (Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, id);
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao deletar conta a receber", e);
		}
	}
		
}
