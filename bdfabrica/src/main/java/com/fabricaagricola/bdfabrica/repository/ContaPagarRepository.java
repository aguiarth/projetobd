package com.fabricaagricola.bdfabrica.repository;

import com.fabricaagricola.bdfabrica.model.ContaPagar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ContaPagarRepository {

	private final DataSource dataSource;
	
	@Autowired
	public ContaPagarRepository(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	// Inserindo
	public ContaPagar save(ContaPagar contaPagar) {
		String sql = "INSERT INTO ContaPagar (id_conta, cnpj) VALUES (?, ?)";
		
		try (Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, contaPagar.getIdConta());
			stmt.setString(2, contaPagar.getCnpj());
			
			stmt.executeUpdate();
			
			return contaPagar;
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao salvar conta a pagar.", e);
		}
	}
	
	// Buscar
	public Optional<ContaPagar> findByPk(int idConta, String cnpj){
		String sql = "SELECT * FROM ContaPagar WHERE id_conta = ? AND cnpj = ?";
		
		try (Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)){
			stmt.setInt(1, idConta);
			stmt.setString(2, cnpj);
			
			try (ResultSet rs = stmt.executeQuery()){
				if (rs.next()) {
					ContaPagar contaPagar = new ContaPagar(
							rs.getInt("id_conta"),
							rs.getString("cnpj")
							);
					return Optional.of(contaPagar);
				}
			}
			return Optional.empty();
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao buscar conta a pagar.", e);
		}
	}
	
	// Listar all
	public List<ContaPagar> findAll(){
		String sql = "SELECT * FROM ContaPagar";
		List<ContaPagar> contaPagarList = new ArrayList<>();
		
		try (Connection conn = dataSource.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)){
			while (rs.next()) {
				ContaPagar contaPagar = new ContaPagar(
						rs.getInt("id_conta"),
						rs.getString("cnpj")
						);
				contaPagarList.add(contaPagar);
			}
			return contaPagarList;
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao listar contas a pagar.", e);
		}
	}
	
	// Deletar
	public void delete(int idConta, String cnpj) {
		String sql = "DELETE FROM ContaPagar WHERE id_conta = ? AND cnpj = ?";
		
		try (Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)){
			stmt.setInt(1, idConta);
			stmt.setString(2, cnpj);
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao deletar contas a pagar.", e);
		}
	}
	
	
	
}
