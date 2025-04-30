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

import com.fabricaagricola.bdfabrica.model.MateriaPrima;

@Repository
public class MateriaPrimaRepository {
	private final DataSource dataSource;

	@Autowired
	public MateriaPrimaRepository(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	// Inserir
	public MateriaPrima save(MateriaPrima materiaPrima) {
		String sql = "INSERT INTO MateriaPrima (descricao, data_validade, custo_unitario, custo_total) VALUES (?, ?, ?, ?)";

		try (Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			stmt.setString(1, materiaPrima.getDescricao());
			stmt.setDate(2, Date.valueOf(materiaPrima.getDataValidade()));
			stmt.setFloat(3, materiaPrima.getCustoUnitario());
			stmt.setFloat(4, materiaPrima.getCustoTotal());

			stmt.executeUpdate();

			try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					materiaPrima.setIdMateriaPrima(generatedKeys.getInt(1));
				}
			}
			return materiaPrima;
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao salvar materia prima", e);
		}
	}

	// Buscar por ID
	public Optional<MateriaPrima> findById(int id) {
		String sql = "SELECT * FROM MateriaPrima WHERE id_materia_prima = ?";

		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, id);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					MateriaPrima materiaPrima = new MateriaPrima(
							rs.getInt("id_materia_prima"),
							rs.getString("descricao"), 
							rs.getDate("data_validade").toLocalDate(),
							rs.getFloat("custo_unitario"), 
							rs.getFloat("custo_total")
					);
					return Optional.of(materiaPrima);
				}
			}
			return Optional.empty();
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao buscar materia prima por id", e);
		}
	}

	// Listar all
	public List<MateriaPrima> findAll() {
		String sql = "SELECT * FROM MateriaPrima";
		List<MateriaPrima> materiaPrimaList = new ArrayList<>();

		try (Connection conn = dataSource.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				MateriaPrima materiaPrima = new MateriaPrima(
						rs.getInt("id_materia_prima"), 
						rs.getString("descricao"),
						rs.getDate("data_validade").toLocalDate(), 
						rs.getFloat("custo_unitario"),
						rs.getFloat("custo_total")
				);
				materiaPrimaList.add(materiaPrima);
			}
			return materiaPrimaList;

		} catch (SQLException e) {
			throw new RuntimeException("Erro ao listar todas as materias primas.", e);
		}
	}

	// Atualizar
	public MateriaPrima update(MateriaPrima materiaPrima) {
		String sql = "UPDATE MateriaPrima SET descricao = ?, custo_unitario = ?, custo_total = ? WHERE id_materia_prima = ?";

		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, materiaPrima.getDescricao());
			stmt.setFloat(2, materiaPrima.getCustoUnitario());
			stmt.setFloat(3, materiaPrima.getCustoTotal());
			stmt.setInt(4, materiaPrima.getIdMateriaPrima());

			int rowsUpdated = stmt.executeUpdate();
			if (rowsUpdated == 0) {
				throw new RuntimeException("Materia prima com id " + materiaPrima.getIdMateriaPrima()
						+ " não encontrado para atualização.");
			}
			return materiaPrima;
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao atualizar o materia prima.", e);
		}

	}

	// Deletar
	public void delete(int id) {
		String sql = "DELETE FROM MateriaPrima WHERE id_materia_prima = ?";

		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, id);
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao deletar materia prima", e);
		}
	}
}
