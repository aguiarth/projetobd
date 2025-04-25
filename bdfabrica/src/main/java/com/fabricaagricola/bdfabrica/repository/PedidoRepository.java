package com.fabricaagricola.bdfabrica.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fabricaagricola.bdfabrica.enums.CondicoesPagamento;
import com.fabricaagricola.bdfabrica.enums.StatusGeral;
import com.fabricaagricola.bdfabrica.model.Pedido;

/*
	Status vai poder ser alterado, seguindo pelo enu;
	confirmar como isso afeta pedido;
*/

@Repository
public class PedidoRepository {
	private final DataSource dataSource;

    @Autowired
    public PedidoRepository(DataSource dataSource) {
    	this.dataSource = dataSource;
    }
    
 // Inserir
    public Pedido save(Pedido pedido) {
        String sql = "INSERT INTO Pedido (numero, data_emissao, valor_total, status, forma_pagamento) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String numeroPedido = "P" + LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE) +
                                  "-" + String.format("%04d", new Random().nextInt(10000));
            pedido.setNumero(numeroPedido);

            stmt.setString(1, pedido.getNumero());
            stmt.setDate(2, Date.valueOf(pedido.getDataEmissao()));
            stmt.setFloat(3, pedido.getValorTotal());
            stmt.setString(4, pedido.getStatus().name());

            if (pedido.getFormaPagamento() != null) {
                stmt.setString(5, pedido.getFormaPagamento().name());
            } else {
                throw new IllegalArgumentException("Forma de pagamento não pode ser nula.");
            }

            stmt.executeUpdate();

            return pedido;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar o pedido", e);
        }
    }

    
    // Busca
    public Optional<Pedido> findByNumero(String numero) {
        String sql = "SELECT * FROM Pedido WHERE numero = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, numero);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Pedido pedido = new Pedido(
                        rs.getString("numero"),
                        rs.getDate("data_emissao").toLocalDate(),
                        rs.getFloat("valor_total"),
                        StatusGeral.valueOf(rs.getString("status")),
                        CondicoesPagamento.valueOf(rs.getString("forma_pagamento"))
                    );
                    return Optional.of(pedido);
                }
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar Pedido por número", e);
        }
    }
    
    // listar todos
    public List<Pedido> findAll(){
    	String sql = "SELECT * FROM Pedido";
    	List<Pedido> pedidoList = new ArrayList<>();
    	
    	try (Connection conn = dataSource.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)){
    		
    		while (rs.next()) {
    			Pedido pedido = new Pedido(
    					rs.getString("numero"),
                        rs.getDate("data_emissao").toLocalDate(),
                        rs.getFloat("valor_total"),
                        StatusGeral.valueOf(rs.getString("status")),
                        CondicoesPagamento.valueOf(rs.getString("forma_pagamento"))
				);
    			pedidoList.add(pedido);
			}
			return pedidoList;
    	}catch (SQLException e) {
            throw new RuntimeException("Erro ao listar todos os pedidos", e);
        }
    }
    
    // atualizar
    // so utilizar o numeor e o status no postman para testar a atualizacao
    public Pedido update(Pedido pedido) {
    	String sql = "UPDATE Pedido SET status = ? WHERE numero = ?";
    	
    	try (Connection conn = dataSource.getConnection();
    	         PreparedStatement stmt = conn.prepareStatement(sql)) {

    	        stmt.setString(1, pedido.getStatus().name());
    	        stmt.setString(2, pedido.getNumero());

    	        int linhasAfetadas = stmt.executeUpdate();

    	        if (linhasAfetadas == 0) {
    	            throw new RuntimeException("Pedido com número " + pedido.getNumero() + " não encontrado para atualização.");
    	        }

    	        return pedido;

    	    } catch (SQLException e) {
    	        throw new RuntimeException("Erro ao atualizar Pedido", e);
    	    }
    }
    
    
    public void delete(String numero) {
    	String sql = "DELETE FROM Pedido WHERE numero = ?";
    	
    	try (Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)){
			stmt.setString(1, numero);
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao deletar pedido", e);
		}
	}
    
}
