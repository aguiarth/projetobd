package com.fabricaagricola.bdfabrica.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fabricaagricola.bdfabrica.model.TelefoneCliente;

@Repository
public class TelefoneClienteRepository {

    private final DataSource dataSource;

    @Autowired
    public TelefoneClienteRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Salvar
    public void save(TelefoneCliente telefone) {
        String sql = "INSERT INTO Telefone_cliente(telefone_cliente, cnpj) VALUES (?, ?)";
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, telefone.getTelefoneCliente());
            stmt.setString(2, telefone.getCnpj());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao salvar telefone", e);
        }
    }

    // Atualizar
    public void update(TelefoneCliente telefone) {
        String sql = "UPDATE Telefone_cliente SET telefone_cliente = ? WHERE cnpj = ? AND telefone_cliente = ?";
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, telefone.getTelefoneCliente());
            stmt.setString(2, telefone.getCnpj());
            stmt.setString(3, telefone.getTelefoneCliente());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar telefone", e);
        }
    }

    // Deletar
    public void delete(String cnpj, String telefone) {
        String sql = "DELETE FROM Telefone_cliente WHERE cnpj = ? AND telefone_cliente = ?";
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, cnpj);
            stmt.setString(2, telefone);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao deletar telefone", e);
        }
    }
}
