package com.fabricaagricola.bdfabrica.repository;

import java.sql.Connection;
import java.sql.Date;
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

import com.fabricaagricola.bdfabrica.model.NotaFiscal;

@Repository
public class NotaFiscalRepository {
	private final DataSource dataSource;

	@Autowired
	public NotaFiscalRepository(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	// Inserir
	public NotaFiscal save(NotaFiscal notaFiscal) {
		String sql = "INSERT INTO NotaFiscal (valor_imposto, data_emissao_nota) VALUES (?, ?)";

		try (Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			stmt.setFloat(1, notaFiscal.getValorImposto());
			stmt.setDate(2, Date.valueOf(notaFiscal.getDataEmissaoNota()));

			stmt.executeUpdate();

			try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					notaFiscal.setChave(generatedKeys.getInt(1));
				}
			}
			return notaFiscal;
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao salvar nota fiscal", e);
		}
	}

	// Buscar por ID
	public Optional<NotaFiscal> findById(int id) {
		String sql = "SELECT * FROM NotaFiscal WHERE chave = ?";

		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, id);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					NotaFiscal notaFiscal = new NotaFiscal(rs.getInt("chave"), rs.getFloat("valor_imposto"),
							rs.getDate("data_emissao_nota").toLocalDate());
					return Optional.of(notaFiscal);
				}
			}
			return Optional.empty();
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao buscar nota fiscal pela chave", e);
		}
	}

	// Listar todos
	public List<NotaFiscal> findAll() {
		String sql = "SELECT * FROM NotaFiscal";
		List<NotaFiscal> notaFiscalList = new ArrayList<>();

		try (Connection conn = dataSource.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				NotaFiscal notaFiscal = new NotaFiscal(rs.getInt("chave"), rs.getFloat("valor_imposto"),
						rs.getDate("data_emissao_nota").toLocalDate());
				notaFiscalList.add(notaFiscal);
			}
			return notaFiscalList;
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao listar todas as notas fiscais", e);
		}
	}

	// Atualizar
	public NotaFiscal update(NotaFiscal notaFiscal) {
		String sql = "UPDATE NotaFiscal SET valor_imposto = ? WHERE chave = ?";

		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setFloat(1, notaFiscal.getValorImposto());
			stmt.setInt(2, notaFiscal.getChave());

			stmt.executeUpdate();
			return notaFiscal;
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao atualizar nosta Fiscal", e);
		}
	}

	// Deletar
	public void delete(int id) {
		String sql = "DELETE FROM NotaFiscal WHERE chave = ?";

		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, id);
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao deletar nota fiscal", e);
		}
	}
}
