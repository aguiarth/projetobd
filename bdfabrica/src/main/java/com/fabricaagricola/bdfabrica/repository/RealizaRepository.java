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

import com.fabricaagricola.bdfabrica.model.Realiza;

@Repository
public class RealizaRepository {
	private final DataSource dataSource;

    @Autowired
    public RealizaRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    
    // inserir
    public void save(Realiza realiza) {
    	if (!clienteExiste(realiza.getCnpj())) {
            throw new IllegalArgumentException("CNPJ informado não existe.");
        }
    	
    	if (!pedidoExiste(realiza.getNumero())) {
            throw new IllegalArgumentException("número de pedido informado não existe.");
        }
    	
    	if (pedidoJaVinculado(realiza.getNumero())) {
            throw new IllegalArgumentException("Este pedidio já está vinculado a um cliente.");
        }
    	
    	String sql = "INSERT INTO Realiza (cnpj, numero) VALUES (?, ?)";
    	
    	try (Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

               stmt.setString(1, realiza.getCnpj());
               stmt.setString(2, realiza.getNumero());
               stmt.executeUpdate();

           } catch (SQLException e) {
               throw new RuntimeException("Erro ao salvar relação Realiza", e);
           }
    }
    
    // conferir se cliente existe
    private boolean clienteExiste(String cnpj) {
        String sql = "SELECT 1 FROM Cliente WHERE cnpj = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cnpj);
            return stmt.executeQuery().next();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar existência do cliente", e);
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
    
    // conferir se o pedido já está vinculado a um cliente
    private boolean pedidoJaVinculado(String numero) {
        String sql = "SELECT 1 FROM Realiza WHERE numero = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, numero);
            return stmt.executeQuery().next();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar se pedido já está vinculado", e);
        }
    }
    
    // listar todos
    public List<Realiza> findAll() {
        List<Realiza> lista = new ArrayList<>();
        String sql = "SELECT cnpj, numero FROM Realiza";

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Realiza(
                        rs.getString("cnpj"),
                        rs.getString("numero")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar relações Realiza", e);
        }

        return lista;
    }
    
    
    // buscar pedido associados a um cnpj
    public List<Realiza> findByCnpj(String cnpj) {
        List<Realiza> lista = new ArrayList<>();
        String sql = "SELECT cnpj, numero FROM Realiza WHERE cnpj = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cnpj);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Realiza(
                            rs.getString("cnpj"),
                            rs.getString("numero")
                    ));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar por CNPJ em Realiza", e);
        }

        return lista;
    }
    
    // achar um relacionamento específico
    public Realiza findByCnpjAndNumero(String cnpj, String numero) {
        String sql = "SELECT cnpj, numero FROM Realiza WHERE cnpj = ? AND numero = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cnpj);
            stmt.setString(2, numero);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Realiza(
                		rs.getString("cnpj"), 
                		rs.getString("numero")
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar relacionamento Realiza", e);
        }

        return null;
    }
    
    // deletar relacionamento
    public void delete(String cnpj, String numero) {
        String sql = "DELETE FROM Realiza WHERE cnpj = ? AND numero = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cnpj);
            stmt.setString(2, numero);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar relação Realiza", e);
        }
    }
}
