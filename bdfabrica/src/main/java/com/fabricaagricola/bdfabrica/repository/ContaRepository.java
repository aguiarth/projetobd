package com.fabricaagricola.bdfabrica.repository;

import com.fabricaagricola.bdfabrica.enums.StatusConta;
import com.fabricaagricola.bdfabrica.model.Conta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ContaRepository {

	private final DataSource dataSource;
	
	@Autowired
	public ContaRepository(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	// Inserir
	public Conta save(Conta conta) {
		String sql = "INSERT INTO Conta (id_financeiro, data_emissao, data_vencimento, valor_total, status) VALUES (?, ?, ?, ?, ?)";
		
		try (Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			stmt.setInt(1, conta.getIdFinanceiro());
			stmt.setDate(2, Date.valueOf(conta.getDataEmissao()));
			stmt.setDate(3, Date.valueOf(conta.getDataVencimento()));
			stmt.setFloat(4, conta.getValorTotal());
			stmt.setString(5, conta.getStatus().name());
			
			stmt.executeUpdate();
			
			try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					conta.setIdConta(generatedKeys.getInt(1));
				}
			}
			
			return conta;
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao salvar conta", e);
		}
	}
	
	// Buscar por ID
	public Optional<Conta> findById(int id){
		String sql = "SELECT * FROM Conta WHERE id_conta = ?";
		
		try (Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1,id);
			
			try (ResultSet rs = stmt.executeQuery()){
				if (rs.next()) {
					Conta conta = new Conta(
						rs.getInt("id_conta"),
						rs.getInt("id_financeiro"),
						rs.getDate("data_emissao").toLocalDate(),
						rs.getDate("data_vencimento").toLocalDate(),
						rs.getFloat("valor_total"),
						StatusConta.valueOf(rs.getString("status"))
					);
					return Optional.of(conta);
				}
			}
			return Optional.empty();
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao buscar Conta por id", e);
		}
	}
	
	// Listar all
	public List<Conta> findAll() {
		String sql = "SELECT * FROM Conta";
		List<Conta> contaList = new ArrayList<>();
		
		try (Connection conn = dataSource.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			
			while (rs.next()) {
				Conta conta = new Conta(
						rs.getInt("id_conta"),
						rs.getInt("id_financeiro"),
						rs.getDate("data_emissao").toLocalDate(),
						rs.getDate("data_vencimento").toLocalDate(),
						rs.getFloat("valor_total"),
						StatusConta.valueOf(rs.getString("status"))
				);
				contaList.add(conta);
			}
			return contaList;
			
		} catch (SQLException e) {
            throw new RuntimeException("Erro ao listar todos as contas", e);
        }
	}
	
	// Atualizar
	public Conta update(Conta conta) {
		String sql = "UPDATE Conta SET id_financeiro = ?, data_emissao = ?, data_vencimento = ?, valor_total = ?, status = ? WHERE id_conta = ?";
		
		try (Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)){
			stmt.setInt(1, conta.getIdFinanceiro());
			stmt.setDate(2, Date.valueOf(conta.getDataEmissao()));
			stmt.setDate(3, Date.valueOf(conta.getDataVencimento()));
			stmt.setFloat(4, conta.getValorTotal());
			stmt.setString(5, conta.getStatus().name());
			stmt.setInt(6, conta.getIdConta());
			
			int rowsUpdated = stmt.executeUpdate();
			if (rowsUpdated == 0) {
			    throw new RuntimeException("Conta com ID " + conta.getIdConta() + " não encontrada para atualização.");
			}
			return conta;
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao atualizar conta", e);
		}
	}
	
	// Deletar
	public void delete(int id) {
		String sql = "DELETE FROM Conta WHERE id_conta = ?";
		
		try (Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)){
			stmt.setInt(1, id);
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao deletar conta", e);
		}
	}
	
}

	
