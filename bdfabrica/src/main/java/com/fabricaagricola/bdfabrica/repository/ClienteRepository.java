package com.fabricaagricola.bdfabrica.repository;

import java.sql.Connection;
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

import com.fabricaagricola.bdfabrica.model.Cliente;

@Repository
public class ClienteRepository {
	
	private final DataSource dataSource;
	
	@Autowired
    public ClienteRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }
	
	public Cliente save(Cliente cliente) {
        String sql = "INSERT INTO Cliente(cnpj, razao_social, rua, numero, cidade, cep, email) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, cliente.getCnpjCliente());
            stmt.setString(2, cliente.getRazaoSocial());
            stmt.setString(3, cliente.getRua());
            stmt.setString(4, cliente.getNumero());
            stmt.setString(5, cliente.getCidade());
            stmt.setString(6, cliente.getCep());
            stmt.setString(7, cliente.getEmail());
            
            stmt.executeUpdate();
            return cliente;
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao salvar cliente", e);
        }
    }
	
	// Buscar por CNPJ
    public Optional<Cliente> findByCnpj(String cnpj) {
        String sql = "SELECT * FROM Cliente WHERE cnpj = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cnpj);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                	Cliente cliente = new Cliente(
                        rs.getString("cnpj"),
                        rs.getString("razao_social"),
                        rs.getString("rua"),
                        rs.getString("numero"),
                        rs.getString("cidade"),
                        rs.getString("cep"),
                        rs.getString("email")
                    );
                    return Optional.of(cliente);
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
        	throw new RuntimeException("Erro ao buscar financeiro por cnpj", e);
        }
    }
	
    
    // Listar todos
    public List<Cliente> findAll() {
    	String sql = "SELECT * FROM Cliente";
        List<Cliente> clienteList = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
        	Statement stmt = conn.createStatement();
               ResultSet rs = stmt.executeQuery(sql)) {

        	while (rs.next()) {
        		Cliente cliente = new Cliente(
                        rs.getString("cnpj"),
                        rs.getString("razao_social"),
                        rs.getString("rua"),
                        rs.getString("numero"),
                        rs.getString("cidade"),
                        rs.getString("cep"),
                        rs.getString("email")
                    );
        		clienteList.add(cliente);
            }
            return clienteList;

        } catch (SQLException e) {
        	throw new RuntimeException("Erro ao listar todos os clientes", e);
        }
    }
    
    // Atualizar
    public Cliente update(Cliente cliente) {
        String sql = "UPDATE Cliente SET razao_social = ?, rua = ?, numero = ?, cidade = ?, cep = ?, email = ? WHERE cnpj = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
        	stmt.setString(1, cliente.getRazaoSocial());
            stmt.setString(2, cliente.getRua());
            stmt.setString(3, cliente.getNumero());
            stmt.setString(4, cliente.getCidade());
            stmt.setString(5, cliente.getCep());
            stmt.setString(6, cliente.getEmail());
            stmt.setString(7, cliente.getCnpjCliente());

            stmt.executeUpdate();
            return cliente;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar cliente", e);
        }
    }
    
    // Deletar
    public void delete(String cnpj) {
        String sql = "DELETE FROM Cliente WHERE cnpj = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cnpj);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar cliente", e);
        }
    }
}
