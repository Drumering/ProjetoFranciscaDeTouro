package com.opet.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.opet.conexao.Conexao;
import com.opet.model.Categoria;
import com.opet.util.Reader;

public class CategoriaDAO {
	
	
	private static final String SELECT_ALL = "SELECT * FROM categoria";
	private static final String INSERT = "INSERT INTO categoria (idCate,nomeCate,slugCate,pillow) "
			+ "VALUES (CateSEQ.nextval,?,?)";
	private static final String DELETE = "DELETE FROM categoria WHERE idCate = ?";
	
	public void cadastrar(Categoria categoria) throws Exception {
		
		String nomeCategoria = Reader.readString();
		String slugCategoria = Reader.readString();

		System.out.println("Confirma cadastro da NOVA CATEGORIA: NOME: |" + nomeCategoria + "| SLUG: |" + slugCategoria + " |");
		
		Connection conn = Conexao.getConexao();
		PreparedStatement stmtCate = conn.prepareStatement(INSERT);

		stmtCate.setString(1, nomeCategoria);
		stmtCate.setString(2, slugCategoria);
		
		conn.setAutoCommit(false);

		int rowAffected = stmtCate.executeUpdate();

		if (rowAffected == 0) {
			conn.rollback();
			return;
		}

		conn.commit();
		conn.close();
		stmtCate.close();
	}

	public Categoria consultar() throws Exception {
		
		Connection conn = Conexao.getConexao();
		Categoria categoria = null;
		
		PreparedStatement stmt = conn.prepareStatement(SELECT_ALL);

		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			categoria = new Categoria(rs.getInt("idCate"), rs.getString("nomeCate"), rs.getString("slugCate"));
		}

		rs.close();
		stmt.close();
		conn.close();
		return categoria;
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

	public void alterar() {
		
	}
}
