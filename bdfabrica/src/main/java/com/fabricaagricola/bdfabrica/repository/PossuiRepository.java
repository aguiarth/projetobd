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

import com.fabricaagricola.bdfabrica.model.Possui;

@Repository
public class PossuiRepository {
	
	private final DataSource dataSource;
	
	@Autowired
    public PossuiRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }
	
	public void save(Possui possui) {
		if (!cnpjExiste(possui.getCnpj())) {
            throw new IllegalArgumentException("CNPJ informado não existe.");
        }
        if (!contaExiste(possui.getIdConta())) {
            throw new IllegalArgumentException("IdConta informado não existe.");
        }
        
		/*
		 * if (!relacionamentoJaExiste(possui.getCnpj(), possui.getIdConta())) { throw
		 * new IllegalArgumentException("Relacionamento já existe."); }
		 */

        
		String sql = "INSERT INTO Possui (cnpj, id_conta) VALUES (?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, possui.getCnpj());
            stmt.setInt(2, possui.getIdConta());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar relação Possui", e);
        }
	}
	
	// conferir se cliente existe
    private boolean cnpjExiste(String cnpj) {
        String sql = "SELECT 1 FROM Cliente WHERE cnpj = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cnpj);
            return stmt.executeQuery().next();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar existência do Cliente", e);
        }
    }
    
    //conferir se conta existe
    private boolean contaExiste(int idConta) {
        String sql = "SELECT 1 FROM ContaReceber WHERE id_conta = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idConta);
            return stmt.executeQuery().next();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar existência da Conta", e);
        }
    }
    
    
	/*
	 * private boolean relacionamentoJaExiste(String cnpj, int idConta) { String sql
	 * = "SELECT 1 FROM Possui WHERE cnpj = ? AND id_conta = ?"; try (Connection
	 * conn = dataSource.getConnection(); PreparedStatement stmt =
	 * conn.prepareStatement(sql)) { stmt.setString(1, cnpj); stmt.setInt(2,
	 * idConta); return stmt.executeQuery().next(); } catch (SQLException e) { throw
	 * new RuntimeException("Erro ao verificar existência do relacionamento.", e); }
	 * }
	 */

    
    // listar todos
    public List<Possui> findAll() {
        List<Possui> lista = new ArrayList<>();
        String sql = "SELECT cnpj, id_conta FROM Possui";

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Possui(
                        rs.getString("cnpj"),
                        rs.getInt("id_conta")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar relações Possui", e);
        }

        return lista;
    }
    
    // buscar contas associados a um cliente
    public List<Possui> findByCnpj(String cnpj) {
        List<Possui> lista = new ArrayList<>();
        String sql = "SELECT cnpj, id_conta FROM Possui WHERE cnpj = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cnpj);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Possui(
                            rs.getString("cnpj"),
                            rs.getInt("id_conta")
                    ));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar por CNPJ em Possui", e);
        }

        return lista;
    }
    
    // achar um relacionamento específico
    public Possui findByCnpjAndIdConta(String cnpj, int idConta) {
        String sql = "SELECT cnpj, id_conta FROM Possui WHERE cnpj = ? AND id_conta = ?";
        try (Connection conn = dataSource.getConnection();
        	     PreparedStatement stmt = conn.prepareStatement(sql)) {

        	    stmt.setString(1, cnpj);
        	    stmt.setInt(2, idConta);

        	    try (ResultSet rs = stmt.executeQuery()) {
        	        if (rs.next()) {
        	            return new Possui(
        	                rs.getString("cnpj"),
        	                rs.getInt("id_conta")
        	            );
        	        }
        	    }

        	} catch (SQLException e) {
        	    throw new RuntimeException("Erro ao buscar relacionamento Possui", e);
        	}

        return null;
    }
    
    // deletar relacionamento
    public void delete(String cnpj, int idConta) {
        String sql = "DELETE FROM Possui WHERE cnpj = ? AND id_conta = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cnpj);
            stmt.setInt(2, idConta);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar relação Possui", e);
        }
    }
}
