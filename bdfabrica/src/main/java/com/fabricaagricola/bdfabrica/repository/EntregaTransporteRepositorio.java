package com.fabricaagricola.bdfabrica.repository;

import com.fabricaagricola.bdfabrica.model.EntregaTransporte;

import javax.sql.DataSource;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EntregaTransporteRepositorio {
    private final DataSource dataSource;

    @Autowired
    public EntregaTransporteRepositorio(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Salvar
    public EntregaTransporte save(EntregaTransporte entrega) {
        String sql = "INSERT INTO EntregaTransporte (numero_entrega, id_expedicao, data_prevista, data_saida, data_entrega) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String numeroEntrega = "E" + LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE) +
                                   "-" + String.format("%04d", new Random().nextInt(10000));
            entrega.setNumeroEntrega(numeroEntrega);

            stmt.setString(1, entrega.getNumeroEntrega());
            stmt.setInt(2, entrega.getIdExpedicao());
            stmt.setDate(3, (entrega.getDataPrevista() != null) ? Date.valueOf(entrega.getDataPrevista()) : null);
            stmt.setDate(4, (entrega.getDataSaida() != null) ? Date.valueOf(entrega.getDataSaida()) : null);
            stmt.setDate(5, (entrega.getDataEntrega() != null) ? Date.valueOf(entrega.getDataEntrega()) : null);

            stmt.executeUpdate();

            return entrega;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar a entrega", e);
        }
    }

    // Buscar por número_entrega
    public Optional<EntregaTransporte> findByNumero(String numeroEntrega) {
        String sql = "SELECT * FROM EntregaTransporte WHERE numero_entrega = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, numeroEntrega);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    EntregaTransporte entrega = new EntregaTransporte();
                    entrega.setNumeroEntrega(rs.getString("numero_entrega"));
                    entrega.setIdExpedicao(rs.getInt("id_expedicao"));
                    entrega.setDataPrevista(rs.getDate("data_prevista") != null ? rs.getDate("data_prevista").toLocalDate() : null);
                    entrega.setDataSaida(rs.getDate("data_saida") != null ? rs.getDate("data_saida").toLocalDate() : null);
                    entrega.setDataEntrega(rs.getDate("data_entrega") != null ? rs.getDate("data_entrega").toLocalDate() : null);
                    return Optional.of(entrega);
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar EntregaTransporte por número", e);
        }
    }

    // Listar todos
    public List<EntregaTransporte> findAll() {
        String sql = "SELECT * FROM EntregaTransporte";
        List<EntregaTransporte> entregas = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                EntregaTransporte entrega = new EntregaTransporte();
                entrega.setNumeroEntrega(rs.getString("numero_entrega"));
                entrega.setIdExpedicao(rs.getInt("id_expedicao"));
                entrega.setDataPrevista(rs.getDate("data_prevista") != null ? rs.getDate("data_prevista").toLocalDate() : null);
                entrega.setDataSaida(rs.getDate("data_saida") != null ? rs.getDate("data_saida").toLocalDate() : null);
                entrega.setDataEntrega(rs.getDate("data_entrega") != null ? rs.getDate("data_entrega").toLocalDate() : null);

                entregas.add(entrega);
            }
            return entregas;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar todas as EntregaTransportes", e);
        }
    }

    // Atualizar
    public EntregaTransporte update(EntregaTransporte entrega) {
        String sql = "UPDATE EntregaTransporte SET id_expedicao = ?, data_prevista = ?, data_saida = ?, data_entrega = ? WHERE numero_entrega = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, entrega.getIdExpedicao());
            stmt.setDate(2, entrega.getDataPrevista() != null ? Date.valueOf(entrega.getDataPrevista()) : null);
            stmt.setDate(3, entrega.getDataSaida() != null ? Date.valueOf(entrega.getDataSaida()) : null);
            stmt.setDate(4, entrega.getDataEntrega() != null ? Date.valueOf(entrega.getDataEntrega()) : null);
            stmt.setString(5, entrega.getNumeroEntrega());

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas == 0) {
                throw new RuntimeException("EntregaTransporte com número " + entrega.getNumeroEntrega() + " não encontrada para atualização.");
            }

            return entrega;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar EntregaTransporte", e);
        }
    }

    // Deletar
    public void delete(String numeroEntrega) {
        String sql = "DELETE FROM EntregaTransporte WHERE numero_entrega = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, numeroEntrega);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar EntregaTransporte", e);
        }
    }
}
