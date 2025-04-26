package com.fabricaagricola.bdfabrica.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fabricaagricola.bdfabrica.enums.StatusExpedicao;
import com.fabricaagricola.bdfabrica.model.Expedicao;

@Repository
public class ExpedicaoRepository {
	private final DataSource dataSource;
	
	@Autowired
	public ExpedicaoRepository(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	// criar
	public Expedicao save(Expedicao expedicao) {
		String sql = "INSERT INTO Expedicao (id_expedicao, data_expedicao, hora_expedicao, status) VALUES (?, ?, ?, ?)";
		
		try(Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
			stmt.setInt(1, expedicao.getIdExpedicao());
			stmt.setDate(2, Date.valueOf(expedicao.getDataExpedicao()));
			stmt.setTime(3, Time.valueOf(expedicao.getHoraExpedicao()));
			stmt.setString(4, expedicao.getStatus().name());
			
			stmt.executeUpdate();
			
			try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					expedicao.setIdExpedicao(generatedKeys.getInt(1));
				}
			}
			
			return expedicao;
		}catch (SQLException e) {
			throw new RuntimeException("Erro ao salvar chegada de pedido a expedição", e);
		}
	}
	
	//buscar por ID
	public Optional<Expedicao> findById(int id){
		String sql = "SELECT * FROM Expedicao WHERE id_expedicao = ?";
		
		try(Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)){
			stmt.setInt(1,id);
			
			try (ResultSet rs = stmt.executeQuery()){
				if (rs.next()) {
					Expedicao expedicao = new Expedicao(
						rs.getInt("id_expedicao"),
						rs.getDate("data_expedicao").toLocalDate(),
						rs.getTime("hora_expedicao").toLocalTime(),
						StatusExpedicao.valueOf(rs.getString("status"))
					);
					return Optional.of(expedicao);
				}
			}
			return Optional.empty();
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao buscar Conta por id", e);
		}
	}
	
	// listar all
	public List<Expedicao> findAll(){
		String sql = "SELECT * FROM Expedicao";
		List<Expedicao> expedicaoList = new ArrayList<>();
		
		try (Connection conn = dataSource.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)){
			while (rs.next()) {
				Expedicao expedicao = new Expedicao(
						rs.getInt("id_expedicao"),
						rs.getDate("data_expedicao").toLocalDate(),
						rs.getTime("hora_expedicao").toLocalTime(),
						StatusExpedicao.valueOf(rs.getString("status"))
				);
				expedicaoList.add(expedicao);
			}
			return expedicaoList;
		}catch (SQLException e) {
            throw new RuntimeException("Erro ao listar todos as expedicoes", e);
        }
	}
	
	// atualizar
	public Expedicao update(Expedicao expedicao) {
		String sql = "UPDATE Expedicao SET data_expedicao = ?, hora_expedicao = ?, status = ? WHERE id_expedicao = ?";
		
		try (Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)){
			stmt.setDate(1, Date.valueOf(expedicao.getDataExpedicao()));
			stmt.setTime(2, Time.valueOf(expedicao.getHoraExpedicao()));
			stmt.setString(3, expedicao.getStatus().name());
			stmt.setInt(4, expedicao.getIdExpedicao());
			
			int rowsUpdated = stmt.executeUpdate();
			if (rowsUpdated == 0) {
			    throw new RuntimeException("Expedicao com ID " + expedicao.getIdExpedicao() + " não encontrada para atualização.");
			}
			return expedicao;
		}catch (SQLException e) {
			throw new RuntimeException("Erro ao atualizar expedicao", e);
		}
	}
	
	// deletar
	public void delete(int id) {
		String sql = "DELETE FROM Expedicao WHERE id_expedicao = ?";
		
		try (Connection conn = dataSource.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)){
			stmt.setInt(1, id);
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao deletar expedicao", e);
		}
	}
}
