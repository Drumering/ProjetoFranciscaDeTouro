package com.opet.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.opet.conexao.Conexao;
import com.opet.model.Categoria;
import com.opet.model.Produto;

public class ProdutoDAO {
	
	private static final String INSERT = "INSERT INTO produto(proID,idCate,proNome,proAltura,proLargura,"
			+ "proCompr,proPreco,proQntd) VALUES(proSEQ.nextval,?,?,?,?,?,?,?)";
	private static final String SELECT = "SELECT * FROM produto INNER JOIN "
			+ "categoria ON produto.idCate = categoria.idCate";
	// TODO UPDATE TABLE PRODUTO VALUES
	private static final String UPDATE = "UPDATE produto SET idCate = ? WHERE proID = ?";
	private static final String DELETE = "DELETE FROM produto WHERE proID = ?";

	CategoriaDAO categoria = new CategoriaDAO();
	
	public void cadastrar(Produto produto) throws Exception {
		
		Connection conn = Conexao.getConexao();

		PreparedStatement stmt = conn.prepareStatement(INSERT);

		stmt.setInt(1, produto.getCategoria().getId());
		stmt.setString(2, produto.getNome());
		stmt.setDouble(3, produto.getAltura());
		stmt.setDouble(4, produto.getLargura());
		stmt.setDouble(5, produto.getComprimento());
		stmt.setDouble(6, produto.getPreco());
		stmt.setInt(7, produto.getQuantidade());

		int rowAffected = stmt.executeUpdate();

		if (rowAffected == 0) {
			conn.rollback();
			return;
		}
		conn.commit();
		stmt.close();
		conn.close();
	}
	
	public Produto consultar() throws ClassNotFoundException, SQLException {
		Produto produto = null;
		
		Connection conn = Conexao.getConexao();

		PreparedStatement stmt = conn.prepareStatement(SELECT);

		conn.setAutoCommit(false);

		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			Categoria categoria = new Categoria(rs.getInt("id"), rs.getString("catNome"), rs.getString("catDescricao"));
			produto = new Produto(categoria, rs.getString("proNome"), rs.getDouble("proAltura"), 
					rs.getDouble("proLargura"), rs.getDouble("proComprimento"), rs.getDouble("proPreco"), 
					rs.getInt("proQuantidade"));
		}
		rs.close();
		stmt.close();
		conn.close();
		return produto;
	}
	
	public void alterar(Produto produto) throws Exception {

		Connection conn = Conexao.getConexao();
		
		PreparedStatement stmt = conn.prepareStatement(UPDATE);
		
		stmt.setInt(1, produto.getCategoria().getId());
		stmt.setInt(2, produto.getId());

		conn.setAutoCommit(false);

		int rowAffected = stmt.executeUpdate();

		if (rowAffected == 0) {
			conn.rollback();
			return;
		} else {
			System.out.println("Alteracao Realizada com Sucesso!");
		}

		conn.commit();
		stmt.close();
	}
	
	public void excluir(Produto produto) throws Exception {
		
		Connection conn = Conexao.getConexao();

		PreparedStatement stmt = conn.prepareStatement(DELETE);

		stmt.setInt(1, produto.getId());

		int rowAffected = stmt.executeUpdate();
		
		if(rowAffected == 0) {
			conn.rollback();
			return;
		}
		conn.commit();
		stmt.close();
		conn.close();
	}

	public ArrayList<Produto> listar() {
		return null;
	}

}
