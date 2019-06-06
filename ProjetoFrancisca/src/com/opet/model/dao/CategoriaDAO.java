package com.opet.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.opet.conexao.Conexao;
import com.opet.model.Categoria;

public class CategoriaDAO {
	
	private static final String SELECT_ALL = "SELECT * FROM categoria";
	private static final String SELECT_ONE = "SELECT * FROM categoria WHERE idCate = ?";
	private static final String INSERT = "INSERT INTO categoria (idCate,nomeCate,slugCate) "
			+ "VALUES (CateSEQ.nextval,?,?)";
	private static final String DELETE = "DELETE FROM categoria WHERE idCate = ?";
	
	public void cadastrar(Categoria categoria) throws Exception {
		String generatedID[] = {"idCate"};
		Connection conn = Conexao.getConexao();
		PreparedStatement stmt = conn.prepareStatement(INSERT, generatedID);

		stmt.setString(1, categoria.getNome());
		stmt.setString(2, categoria.getDescricao());
		
		conn.setAutoCommit(false);

		int rowAffected = stmt.executeUpdate();
		ResultSet rs = stmt.getGeneratedKeys();
		
		while(rs.next()) {
			int categoriaID = rs.getInt(1);
			categoria.setId(categoriaID);
		}
		
		rs.close();

		if (rowAffected == 0) {
			conn.rollback();
			return;
		}

		conn.commit();
		conn.close();
		stmt.close();		
	}

	public Categoria consultar(int id) throws Exception {
		
		Connection conn = Conexao.getConexao();
		PreparedStatement stmt = conn.prepareStatement(SELECT_ONE);
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();
		
		Categoria categoria = null;

		while (rs.next()) {
			categoria = new Categoria(rs.getInt("idCate"), rs.getString("nomeCate"), rs.getString("slugCate"));
		}

		rs.close();
		stmt.close();
		conn.close();
		return categoria;
	}
	
	public ArrayList<Categoria> listar() throws SQLException {
		
		Connection conn = Conexao.getConexao();
		PreparedStatement stmt = conn.prepareStatement(SELECT_ALL);
		ResultSet rs = stmt.executeQuery();
		
		ArrayList<Categoria> categorias = new ArrayList<>();
		while (rs.next()) {
			Categoria categoria = new Categoria(rs.getInt("idCate"), 
					rs.getString("nomeCate"), rs.getString("slugCate"));
			categorias.add(categoria);
		}

		rs.close();
		stmt.close();
		conn.close();
		return categorias;
	}
	
	public void excluir(Categoria categoria) throws Exception {
		
		Connection conn = Conexao.getConexao();
		PreparedStatement stmt = conn.prepareStatement(DELETE);

		stmt.setInt(1, categoria.getId());
		
		int rowAffected = stmt.executeUpdate();
		
		if(rowAffected == 0) {
			conn.rollback();
			return;
		}
		
		conn.commit();
		stmt.close();
		conn.close();
	}

	// TODO Alterar
	public void alterar(Categoria categoria) {
		
	}
}
