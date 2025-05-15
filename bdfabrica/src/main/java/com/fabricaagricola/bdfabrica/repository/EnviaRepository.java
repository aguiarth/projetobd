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

import com.fabricaagricola.bdfabrica.model.Envia;

@Repository
public class EnviaRepository {
	private final DataSource dataSource;

    @Autowired
    public EnviaRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    // inserir
    public void save(Envia envia) {
    	if (!pedidoExiste(envia.getNumero())) {
            throw new IllegalArgumentException("número de pedido informado não existe.");
        }
    	
    	if (!idExpedicaoExiste(envia.getIdExpedicao())) {
            throw new IllegalArgumentException("id informado não existe.");
        }
    	
    	if (pedidoJaVinculado(envia.getNumero())) {
            throw new IllegalArgumentException("Este pedidio já está vinculado a um cliente.");
        }
    	
    	String sql = "INSERT INTO Envia (numero, id_expedicao) VALUES (?, ?)";
    	
    	try (Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

               stmt.setString(1, envia.getNumero());
               stmt.setInt(2, envia.getIdExpedicao());
               stmt.executeUpdate();

           } catch (SQLException e) {
               throw new RuntimeException("Erro ao salvar relação Envia", e);
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
    
    // conferir se expedicao existe
    private boolean idExpedicaoExiste(int idExpedicao) {
        String sql = "SELECT 1 FROM Expedicao WHERE id_expedicao = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idExpedicao);
            return stmt.executeQuery().next();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar existência da expedição", e);
        }
    }
    
    // conferir se o pedido já está vinculado a uma expedicao
    private boolean pedidoJaVinculado(String numero) {
        String sql = "SELECT 1 FROM Envia WHERE numero = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, numero);
            return stmt.executeQuery().next();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar se pedido já está vinculado", e);
        }
    }
    
    // listar todos
    public List<Envia> findAll() {
        List<Envia> lista = new ArrayList<>();
        String sql = "SELECT numero, id_expedicao FROM Envia";

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Envia(
                        rs.getString("numero"),
                        rs.getInt("id_expedicao")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar relações Envia", e);
        }

        return lista;
    }
    
    // buscar pedidos associados a um idExpedicao
    public List<Envia> findById(int idExpedicao) {
        List<Envia> lista = new ArrayList<>();
        String sql = "SELECT numero, id_expedicao FROM Envia WHERE id_expedicao = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idExpedicao);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Envia(
                            rs.getString("numero"),
                            rs.getInt("id_expedicao")
                    ));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar por IdExpedicao em Envia", e);
        }

        return lista;
    }
    
    // achar um relacionamento específico
    public Envia findByIdAndNumero(int idExpedicao, String numero) {
        String sql = "SELECT numero, id_expedicao FROM Envia WHERE id_expedicao = ? AND numero = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idExpedicao);
            stmt.setString(2, numero);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Envia(
                    		rs.getString("numero"), 
                    		rs.getInt("id_expedicao")
                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar relacionamento Envia", e);
        }

        return null;
    }
    
    // deletar relacionamento
    public void delete(int idExpedicao, String numero) {
        String sql = "DELETE FROM Envia WHERE id_expedicao = ? AND numero = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

        	stmt.setInt(1, idExpedicao);
            stmt.setString(2, numero);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar relação Envia", e);
        }
    }
}
