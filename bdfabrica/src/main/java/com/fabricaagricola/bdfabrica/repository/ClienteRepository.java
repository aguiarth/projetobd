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
import com.fabricaagricola.bdfabrica.model.TelefoneCliente;

@Repository
public class ClienteRepository {
	
	private final DataSource dataSource;
	private final TelefoneClienteRepository telefoneClienteRepository;
	
	@Autowired
    public ClienteRepository(DataSource dataSource, TelefoneClienteRepository telefoneClienteRepository) {
        this.dataSource = dataSource;
        this.telefoneClienteRepository = telefoneClienteRepository;
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
            
         // SEEEE houver telefones, salva eles
            if (cliente.getTelefones() != null && !cliente.getTelefones().isEmpty()) {
                for (TelefoneCliente telefone : cliente.getTelefones()) {
                    telefone.setCnpj(cliente.getCnpjCliente());
                    telefoneClienteRepository.save(telefone);
                }
            }
            
            return cliente;
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao salvar cliente", e);
        }
    }
	
	// Buscar por CNPJ
	public Optional<Cliente> findByCnpj(String cnpj) {
	    String sqlCliente = "SELECT * FROM Cliente WHERE cnpj = ?";
	    String sqlTelefones = "SELECT * FROM Telefone_cliente WHERE cnpj = ?";
	    
	    try (Connection conn = dataSource.getConnection();
	         PreparedStatement stmtCliente = conn.prepareStatement(sqlCliente);
	         PreparedStatement stmtTelefones = conn.prepareStatement(sqlTelefones)) {

	        stmtCliente.setString(1, cnpj);
	        stmtTelefones.setString(1, cnpj);
	        
	        try (ResultSet rsCliente = stmtCliente.executeQuery()) {
	            if (rsCliente.next()) {
	                Cliente cliente = new Cliente(
	                    rsCliente.getString("cnpj"),
	                    rsCliente.getString("razao_social"),
	                    rsCliente.getString("rua"),
	                    rsCliente.getString("numero"),
	                    rsCliente.getString("cidade"),
	                    rsCliente.getString("cep"),
	                    rsCliente.getString("email"),
	                    new ArrayList<>()
	                );

	                try (ResultSet rsTelefones = stmtTelefones.executeQuery()) {
	                    while (rsTelefones.next()) {
	                        TelefoneCliente telefone = new TelefoneCliente();
	                        telefone.setCnpj(rsTelefones.getString("cnpj"));
	                        telefone.setTelefoneCliente(rsTelefones.getString("telefone_cliente"));
	                        cliente.getTelefones().add(telefone);
	                    }
	                }

	                return Optional.of(cliente);
	            }
	        }

	        return Optional.empty();
	    } catch (SQLException e) {
	        throw new RuntimeException("Erro ao buscar cliente por CNPJ", e);
	    }
	}

	
    
	public List<Cliente> findAll() {
	    String sqlCliente = "SELECT * FROM Cliente";
	    String sqlTelefones = "SELECT * FROM Telefone_cliente WHERE cnpj = ?";
	    
	    List<Cliente> clienteList = new ArrayList<>();
	    
	    try (Connection conn = dataSource.getConnection();
	         Statement stmtCliente = conn.createStatement()) {
	        
	        try (ResultSet rsCliente = stmtCliente.executeQuery(sqlCliente)) {
	            while (rsCliente.next()) {
	                Cliente cliente = new Cliente(
	                    rsCliente.getString("cnpj"),
	                    rsCliente.getString("razao_social"),
	                    rsCliente.getString("rua"),
	                    rsCliente.getString("numero"),
	                    rsCliente.getString("cidade"),
	                    rsCliente.getString("cep"),
	                    rsCliente.getString("email"),
	                    new ArrayList<>()
	                );
	                
	                try (PreparedStatement stmtTelefones = conn.prepareStatement(sqlTelefones)) {
	                    stmtTelefones.setString(1, cliente.getCnpjCliente());
	                    
	                    try (ResultSet rsTelefones = stmtTelefones.executeQuery()) {
	                        while (rsTelefones.next()) {
	                            TelefoneCliente telefone = new TelefoneCliente();
	                            telefone.setCnpj(rsTelefones.getString("cnpj"));
	                            telefone.setTelefoneCliente(rsTelefones.getString("telefone_cliente"));
	                            cliente.getTelefones().add(telefone);
	                        }
	                    }
	                }
	                
	                clienteList.add(cliente);
	            }
	        }

	        return clienteList;
	        
	    } catch (SQLException e) {
	        throw new RuntimeException("Erro ao listar todos os clientes", e);
	    }
	}

    
	public Cliente update(Cliente cliente) {
	    String sqlCliente = "UPDATE Cliente SET razao_social = ?, rua = ?, numero = ?, cidade = ?, cep = ?, email = ? WHERE cnpj = ?";
	    String sqlDeleteTelefones = "DELETE FROM Telefone_cliente WHERE cnpj = ?";
	    String sqlInsertTelefone = "INSERT INTO Telefone_cliente(cnpj, telefone_cliente) VALUES (?, ?)";

	    try (Connection conn = dataSource.getConnection();
	         PreparedStatement stmtCliente = conn.prepareStatement(sqlCliente)) {
	        
	        stmtCliente.setString(1, cliente.getRazaoSocial());
	        stmtCliente.setString(2, cliente.getRua());
	        stmtCliente.setString(3, cliente.getNumero());
	        stmtCliente.setString(4, cliente.getCidade());
	        stmtCliente.setString(5, cliente.getCep());
	        stmtCliente.setString(6, cliente.getEmail());
	        stmtCliente.setString(7, cliente.getCnpjCliente());

	        stmtCliente.executeUpdate();

	        try (PreparedStatement stmtDeleteTelefones = conn.prepareStatement(sqlDeleteTelefones)) {
	            stmtDeleteTelefones.setString(1, cliente.getCnpjCliente());
	            stmtDeleteTelefones.executeUpdate();
	        }

	        if (cliente.getTelefones() != null) {
	            try (PreparedStatement stmtInsertTelefone = conn.prepareStatement(sqlInsertTelefone)) {
	                for (TelefoneCliente telefone : cliente.getTelefones()) {
	                    stmtInsertTelefone.setString(1, cliente.getCnpjCliente());
	                    stmtInsertTelefone.setString(2, telefone.getTelefoneCliente());
	                    stmtInsertTelefone.addBatch();
	                }
	                stmtInsertTelefone.executeBatch();
	            }
	        }

	        return cliente;
	    } catch (SQLException e) {
	        throw new RuntimeException("Erro ao atualizar cliente", e);
	    }
	}

    
	public void delete(String cnpj) {
	    String sqlDeleteTelefones = "DELETE FROM Telefone_cliente WHERE cnpj = ?";
	    String sqlDeleteCliente = "DELETE FROM Cliente WHERE cnpj = ?";

	    try (Connection conn = dataSource.getConnection()) {
	        try (PreparedStatement stmtDeleteTelefones = conn.prepareStatement(sqlDeleteTelefones)) {
	            stmtDeleteTelefones.setString(1, cnpj);
	            stmtDeleteTelefones.executeUpdate();
	        }

	        try (PreparedStatement stmtDeleteCliente = conn.prepareStatement(sqlDeleteCliente)) {
	            stmtDeleteCliente.setString(1, cnpj);
	            stmtDeleteCliente.executeUpdate();
	        }

	    } catch (SQLException e) {
	        throw new RuntimeException("Erro ao deletar cliente", e);
	    }
	}

}
