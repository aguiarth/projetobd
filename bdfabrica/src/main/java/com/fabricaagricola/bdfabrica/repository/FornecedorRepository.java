package com.fabricaagricola.bdfabrica.repository;

import com.fabricaagricola.bdfabrica.enums.CondicoesPagamento;
import com.fabricaagricola.bdfabrica.model.Fornecedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class FornecedorRepository {

	private final DataSource dataSource;
	
	@Autowired
	public FornecedorRepository(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	// Inserir
	public Fornecedor save(Fornecedor fornecedor) {
		String sql = "INSERT INTO Fornecedor (cnpj, razao_social, endereco, telefone, condicoes_pagamento) VALUES (?, ?, ?, ?, ?)";
		
		try (Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
			stmt.setString(1, fornecedor.getCnpj());
			stmt.setString(2, fornecedor.getRazaoSocial());
			stmt.setString(3, fornecedor.getEndereco());
			stmt.setString(4, fornecedor.getTelefone());
			stmt.setString(5, fornecedor.getCondicoesPagamento().name());
			
			stmt.executeUpdate();
			
			return fornecedor;

		} catch (SQLException e) {
			throw new RuntimeException("Erro ao salvar fornecedor", e);
		}
	}
	
	// Buscar por cnpj
	public Optional<Fornecedor> findByCnpj(String cnpj) {
		String sql = "SELECT * FROM Fornecedor WHERE cnpj = ?";
		
		try (Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, cnpj);
			
			try (ResultSet rs = stmt.executeQuery()){
				if (rs.next()) {
					Fornecedor fornecedor = new Fornecedor(
							rs.getString("cnpj"),
							rs.getString("razao_social"),
							rs.getString("endereco"),
							rs.getString("telefone"),
							CondicoesPagamento.valueOf(rs.getString("condicoes_pagamento"))
							);
					return Optional.of(fornecedor);
				}
			}
			return Optional.empty();
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao buscar Conta por cnpj.", e);
		}
	}
	
	// Listar all
	public List<Fornecedor> findAll() {
		String sql = "SELECT * FROM Fornecedor";
		List<Fornecedor> fornecedorList = new ArrayList<>();
		
		try (Connection conn = dataSource.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			
			while (rs.next()) {
				Fornecedor fornecedor = new Fornecedor(
						rs.getString("cnpj"),
						rs.getString("razao_social"),
						rs.getString("endereco"),
						rs.getString("telefone"),
						CondicoesPagamento.valueOf(rs.getString("condicoes_pagamento"))
				);
				fornecedorList.add(fornecedor);
			}
			return fornecedorList;
			
		} catch (SQLException e) {
            throw new RuntimeException("Erro ao listar todos os fornecedores.", e);
        }
	}
	
	// Atualizar 
	public Fornecedor update (Fornecedor fornecedor) {
		String sql = "UPDATE Fornecedor SET razao_social = ?, endereco = ?, telefone = ?, condicoes_pagamento = ? WHERE cnpj = ?";
		
		try (Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, fornecedor.getRazaoSocial());
			stmt.setString(2, fornecedor.getEndereco());
			stmt.setString(3, fornecedor.getTelefone());
			stmt.setString(4, fornecedor.getCondicoesPagamento().name());
			stmt.setString(5, fornecedor.getCnpj());
			
			int rowsUpdated = stmt.executeUpdate();
			if (rowsUpdated == 0) {
				throw new RuntimeException("Fornecedor com cnpj " + fornecedor.getCnpj() + " não encontrado para atualização.");
			}
			return fornecedor;
		} catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar o fornecedor.", e);
        }
		
	}
	
	// Deletar
	public void delete(String cnpj) {
		String sql = "DELETE FROM Fornecedor WHERE cnpj= ?";
		
		try(Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, cnpj);
			stmt.executeUpdate();
		} catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar o fornecedor", e);
        }
	}
	
}
