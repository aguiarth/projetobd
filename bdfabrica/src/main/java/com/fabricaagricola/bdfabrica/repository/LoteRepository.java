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

import com.fabricaagricola.bdfabrica.model.Lote;

@Repository
public class LoteRepository {
	private final DataSource dataSource;

	@Autowired
	public LoteRepository(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	// Verificação de existência por tabela
	private boolean existsById(String tableName, String columnName, int id) {
		String sql = "SELECT 1 FROM " + tableName + " WHERE " + columnName + " = ?";

		try (Connection conn = dataSource.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, id);
			try (ResultSet rs = stmt.executeQuery()) {
				return rs.next();
			}
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao verificar existência de ID na tabela " + tableName, e);
		}
	}

	// Salvar
	public Lote save(Lote lote) {
	    if (!existsById("Estoque", "id_estoque", lote.getIdEstoque()) ||
	        !existsById("MateriaPrima", "id_materia_prima", lote.getIdMateriaPrima()) ||
	        !existsById("ProdutoAcabado", "id_produto", lote.getIdProduto())) {
	        throw new IllegalArgumentException("Erro: ID de Estoque, Produto ou Matéria-Prima não encontrado.");
	    }

	    if (!isCustoValido(lote.getCusto())) {
	        throw new IllegalArgumentException("Erro: custo inválido. Insira apenas números válidos (ex: 10.5 ou 10,50).");
	    }

	    String sql = "INSERT INTO Lote (codigo, id_estoque, id_materia_prima, id_produto, custo, descricao, quantidade, data_validade) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

	    try (Connection conn = dataSource.getConnection(); 
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        String codigoLote = "L" + LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE) +
	                            "-" + String.format("%04d", new Random().nextInt(10000));
	        lote.setCodigo(codigoLote);

	        stmt.setString(1, lote.getCodigo());
	        stmt.setInt(2, lote.getIdEstoque());
	        stmt.setInt(3, lote.getIdMateriaPrima());
	        stmt.setInt(4, lote.getIdProduto());
	        stmt.setString(5, lote.getCusto());
	        stmt.setString(6, lote.getDescricao());
	        stmt.setInt(7, lote.getQuantidade());
	        stmt.setDate(8, Date.valueOf(lote.getDataValidade()));

	        stmt.executeUpdate();

	        return lote;

	    } catch (SQLException e) {
	        throw new RuntimeException("Erro ao salvar o lote", e);
	    }
	}


	// Atualizar
	public Lote update(Lote lote) {
		if (!existsById("Estoque", "id_estoque", lote.getIdEstoque()) ||
			!existsById("MateriaPrima", "id_materia_prima", lote.getIdMateriaPrima()) ||
			!existsById("ProdutoAcabado", "id_produto", lote.getIdProduto())) {
			throw new IllegalArgumentException("Erro: ID de Estoque, Produto ou Matéria-Prima não encontrado.");
		}
		
		if (!isCustoValido(lote.getCusto())) {
	        throw new IllegalArgumentException("Erro: custo inválido. Insira apenas números válidos (ex: 10.5 ou 10,50).");
	    }

		String sql = "UPDATE Lote SET custo = ?, descricao = ?, quantidade = ?, data_validade = ? WHERE codigo = ?";

		try (Connection conn = dataSource.getConnection(); 
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, lote.getCusto());
			stmt.setString(2, lote.getDescricao());
			stmt.setInt(3, lote.getQuantidade());
			stmt.setDate(4, Date.valueOf(lote.getDataValidade()));
			stmt.setString(5, lote.getCodigo());

			stmt.executeUpdate();
			return lote;
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao atualizar lote", e);
		}
	}

	// Busca
	public Optional<Lote> findByNumero(String codigo) {
		String sql = "SELECT * FROM Lote WHERE codigo = ?";

		try (Connection conn = dataSource.getConnection(); 
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, codigo);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					Lote lote = new Lote(
							rs.getString("codigo"), 
							rs.getInt("id_estoque"), 
							rs.getInt("id_materia_prima"),
							rs.getInt("id_produto"), 
							rs.getString("custo"), 
							rs.getString("descricao"),
							rs.getInt("quantidade"), 
							rs.getDate("data_validade").toLocalDate()
					);
					return Optional.of(lote);
				}
			}

			return Optional.empty();

		} catch (SQLException e) {
			throw new RuntimeException("Erro ao buscar Lote por codigo", e);
		}
	}

	// Listar todos
	public List<Lote> findAll() {
		String sql = "SELECT * FROM Lote";
		List<Lote> loteList = new ArrayList<>();

		try (Connection conn = dataSource.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				Lote lote = new Lote(
						rs.getString("codigo"), 
						rs.getInt("id_estoque"), 
						rs.getInt("id_materia_prima"),
						rs.getInt("id_produto"), 
						rs.getString("custo"), 
						rs.getString("descricao"),
						rs.getInt("quantidade"), 
						rs.getDate("data_validade").toLocalDate()
				);
				loteList.add(lote);
			}
			return loteList;
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao listar todos os lotes", e);
		}
	}

	// Deletar
	public void delete(String codigo) {
		String sql = "DELETE FROM Lote WHERE codigo = ?";

		try (Connection conn = dataSource.getConnection(); 
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, codigo);
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao deletar lote", e);
		}
	}
	
	
	// MÉTODO AUXILIAR
	private boolean isCustoValido(String valor) {
	    if (valor == null || valor.trim().isEmpty()) return false;

	    try {
	        float f = Float.parseFloat(valor.replace(',', '.')); // Aceita vírgula como separador decimal, se desejar
	        return f > 0; // Se quiser garantir que seja positivo
	    } catch (NumberFormatException e) {
	        return false;
	    }
	}

}
