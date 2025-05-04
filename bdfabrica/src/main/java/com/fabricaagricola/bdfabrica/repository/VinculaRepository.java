package com.fabricaagricola.bdfabrica.repository;

import com.fabricaagricola.bdfabrica.model.Vincula;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class VinculaRepository {

    private final DataSource dataSource;

    @Autowired
    public VinculaRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Inserir novo relacionamento
    public void save(Vincula vincula) {
        if (!fornecedorExiste(vincula.getCnpj())) {
            throw new IllegalArgumentException("CNPJ informado não existe.");
        }
        if (!loteExiste(vincula.getCodigo())) {
            throw new IllegalArgumentException("Código de lote informado não existe.");
        }
        
        if (loteJaVinculado(vincula.getCodigo())) {
            throw new IllegalArgumentException("Este lote já está vinculado a um fornecedor.");
        }

        String sql = "INSERT INTO Vincula (cnpj, codigo) VALUES (?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, vincula.getCnpj());
            stmt.setString(2, vincula.getCodigo());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar relação Vincula", e);
        }
    }
    
    // conferir se fornecedor existe
    private boolean fornecedorExiste(String cnpj) {
        String sql = "SELECT 1 FROM Fornecedor WHERE cnpj = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cnpj);
            return stmt.executeQuery().next();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar existência do Fornecedor", e);
        }
    }
    
    //conferir se lote existe
    private boolean loteExiste(String codigo) {
        String sql = "SELECT 1 FROM Lote WHERE codigo = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codigo);
            return stmt.executeQuery().next();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar existência do Lote", e);
        }
    }
    
    // conferir se lote já está vinculado a um fornecedor
    private boolean loteJaVinculado(String codigo) {
        String sql = "SELECT 1 FROM Vincula WHERE codigo = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codigo);
            return stmt.executeQuery().next();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar se lote já está vinculado", e);
        }
    }

    
    // listar todos
    public List<Vincula> findAll() {
        List<Vincula> lista = new ArrayList<>();
        String sql = "SELECT cnpj, codigo FROM Vincula";

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Vincula(
                        rs.getString("cnpj"),
                        rs.getString("codigo")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar relações Vincula", e);
        }

        return lista;
    }
    
    // buscar lotes associados a um cnpj
    public List<Vincula> findByCnpj(String cnpj) {
        List<Vincula> lista = new ArrayList<>();
        String sql = "SELECT cnpj, codigo FROM Vincula WHERE cnpj = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cnpj);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Vincula(
                            rs.getString("cnpj"),
                            rs.getString("codigo")
                    ));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar por CNPJ em Vincula", e);
        }

        return lista;
    }
    
    // achar um relacionamento específico
    public Vincula findByCnpjAndCodigo(String cnpj, String codigo) {
        String sql = "SELECT cnpj, codigo FROM Vincula WHERE cnpj = ? AND codigo = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cnpj);
            stmt.setString(2, codigo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Vincula(rs.getString("cnpj"), rs.getString("codigo"));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar relacionamento Vincula", e);
        }

        return null;
    }

    // deletar relacionamento
    public void delete(String cnpj, String codigo) {
        String sql = "DELETE FROM Vincula WHERE cnpj = ? AND codigo = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cnpj);
            stmt.setString(2, codigo);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar relação Vincula", e);
        }
    }
}
