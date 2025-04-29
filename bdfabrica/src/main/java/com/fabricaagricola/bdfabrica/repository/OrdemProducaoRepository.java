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
import com.fabricaagricola.bdfabrica.model.OrdemProducao;

@Repository
public class OrdemProducaoRepository {
	private final DataSource dataSource;

	@Autowired
	public OrdemProducaoRepository(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	// Inserir
	public OrdemProducao save(OrdemProducao ordemProducao) {
		String sql = "INSERT INTO OrdemProducao (produto_fabricado, quantidade_produto, data_inicio, data_final, descricao, id_dependente, id_requisitado) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
		try (Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			stmt.setString(1, ordemProducao.getProdutoFabricado());
			stmt.setInt(2, ordemProducao.getQuantidadeProduto());
			stmt.setDate(3, Date.valueOf(ordemProducao.getDataInicio()));
			stmt.setDate(4, Date.valueOf(ordemProducao.getDataFinal()));
			stmt.setString(5, ordemProducao.getDescricao());
			if (ordemProducao.getIdOrdemDependente() != null) {
				stmt.setInt(6, ordemProducao.getIdOrdemDependente());
			} else {
				stmt.setNull(6, java.sql.Types.INTEGER);
			}
			if (ordemProducao.getIdOrdemRequisitada() != null) {
				stmt.setInt(7, ordemProducao.getIdOrdemRequisitada());
			} else {
				stmt.setNull(7, java.sql.Types.INTEGER);
			}
			stmt.executeUpdate();
			try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					ordemProducao.setIdOrdem(generatedKeys.getInt(1));
				}
			}
			return ordemProducao;
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao salvar Ordem de Produção", e);
		}
	}

	// Buscar Ordem de Produção por ID (resolvendo os relacionamentos)
	public Optional<OrdemProducao> findById(int id) {
		String sql = "SELECT * FROM OrdemProducao WHERE id_ordem = ?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, id);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					OrdemProducao ordemProducao = mapResultSet(rs);
					return Optional.of(ordemProducao);
				}
			}
			return Optional.empty();
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao buscar ordem produção por id", e);
		}
	}

	// Listar todas
	public List<OrdemProducao> findAll() {
		String sql = "SELECT * FROM OrdemProducao";
		List<OrdemProducao> lista = new ArrayList<>();
		try (Connection conn = dataSource.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				lista.add(mapResultSet(rs));
			}
			return lista;
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao listar todas as ordens de produção", e);
		}
	}

	// atualizar
	public OrdemProducao update(OrdemProducao ordemProducao) {
		String sql = "UPDATE OrdemProducao SET id_dependente = ?, id_requisitado = ?, produto_fabricado = ?, "
				+ "quantidade_produto = ?, data_final = ?, descricao = ? WHERE id_ordem = ?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			if (ordemProducao.getIdOrdemDependente() != null) {
				stmt.setInt(1, ordemProducao.getIdOrdemDependente());
			} else {
				stmt.setNull(1, java.sql.Types.INTEGER);
			}
			if (ordemProducao.getIdOrdemRequisitada() != null) {
				stmt.setInt(2, ordemProducao.getIdOrdemRequisitada());
			} else {
				stmt.setNull(2, java.sql.Types.INTEGER);
			}
			stmt.setString(3, ordemProducao.getProdutoFabricado());
			stmt.setInt(4, ordemProducao.getQuantidadeProduto());
			stmt.setDate(5, Date.valueOf(ordemProducao.getDataFinal()));
			stmt.setString(6, ordemProducao.getDescricao());
			stmt.setInt(7, ordemProducao.getIdOrdem());
			stmt.executeUpdate();
			return ordemProducao;
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao atualizar ordem de produção", e);
		}
	}

	// deletar
	public void delete(int id) {
		String limparDependencias = "UPDATE OrdemProducao SET id_dependente = NULL WHERE id_dependente = ?";
		String limparRequisitadas = "UPDATE OrdemProducao SET id_requisitado = NULL WHERE id_requisitado = ?";
		String sql = "DELETE FROM OrdemProducao WHERE id_ordem = ?";

		try (Connection conn = dataSource.getConnection()) {
			conn.setAutoCommit(false);

			try (PreparedStatement stmt1 = conn.prepareStatement(limparDependencias);
					PreparedStatement stmt2 = conn.prepareStatement(limparRequisitadas);
					PreparedStatement stmt3 = conn.prepareStatement(sql)) {
				// Limpa ordens que dependem da ordem a ser deletada
				stmt1.setInt(1, id);
				stmt1.executeUpdate();

				// Limpa ordens que requisitam a ordem a ser deletada
				stmt2.setInt(1, id);
				stmt2.executeUpdate();

				// Agora sim deleta
				stmt3.setInt(1, id);
				stmt3.executeUpdate();

				conn.commit();
			} catch (SQLException e) {
				conn.rollback();
				throw new RuntimeException("Erro ao deletar ordem de produção", e);
			}
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao deletar ordem de produção", e);
		}
	}

	private OrdemProducao mapResultSet(ResultSet rs) throws SQLException {
		OrdemProducao ordem = new OrdemProducao();
		ordem.setIdOrdem(rs.getInt("id_ordem"));
		ordem.setProdutoFabricado(rs.getString("produto_fabricado"));
		ordem.setQuantidadeProduto(rs.getInt("quantidade_produto"));
		ordem.setDataInicio(rs.getDate("data_inicio").toLocalDate());
		ordem.setDataFinal(rs.getDate("data_final").toLocalDate());
		ordem.setDescricao(rs.getString("descricao"));

		int idDep = rs.getInt("id_dependente");
		if (!rs.wasNull()) {
			ordem.setIdOrdemDependente(idDep);
		}

		int idReq = rs.getInt("id_requisitado");
		if (!rs.wasNull()) {
			ordem.setIdOrdemRequisitada(idReq);
		}

		return ordem;
	}
}