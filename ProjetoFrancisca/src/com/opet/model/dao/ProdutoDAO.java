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
	private static final String SELECT_ONE = "SELECT p.PROID id, p.IDCATE idcate, "
			+ "p.PRONOME nome, p.PROALTURA altura, p.PROLARGURA largura, p.PROCOMPR "
			+ "comprimento, p.PROPRECO preco, p.PROQNTD quantidade, c.NOMECATE "
			+ "categoria,c.SLUGCATE descricao FROM produto p INNER JOIN categoria "
			+ "c ON p.idCate = c.idCate WHERE p.proid = ?";
	private static final String SELECT_ALL = "SELECT p.PROID id, p.IDCATE idcate, "
			+ "p.PRONOME nome, p.PROALTURA altura, p.PROLARGURA largura, p.PROCOMPR "
			+ "comprimento, p.PROPRECO preco, p.PROQNTD quantidade, c.NOMECATE "
			+ "categoria,c.SLUGCATE descricao FROM produto p INNER JOIN categoria " + "c ON p.idCate = c.idCate";
	// TODO UPDATE TABLE PRODUTO VALUES
	private static final String UPDATE = "UPDATE produto SET idCate = ? WHERE proID = ?";
	private static final String DELETE = "DELETE FROM produto WHERE proID = ?";

	public void cadastrar(Produto produto) throws Exception {
		String generatedID[] = { "proID" };
		Connection conn = Conexao.getConexao();
		PreparedStatement stmt = conn.prepareStatement(INSERT, generatedID);

		stmt.setInt(1, produto.getCategoriaId());
		stmt.setString(2, produto.getNome());
		stmt.setDouble(3, produto.getAltura());
		stmt.setDouble(4, produto.getLargura());
		stmt.setDouble(5, produto.getComprimento());
		stmt.setDouble(6, produto.getPreco());
		stmt.setInt(7, produto.getQuantidade());

		int rowAffected = stmt.executeUpdate();
		ResultSet rs = stmt.getGeneratedKeys();

		while (rs.next()) {
			int produtoID = rs.getInt(1);
			produto.setId(produtoID);
		}

		if (rowAffected == 0) {
			conn.rollback();
			return;
		}
		conn.commit();
		stmt.close();
		conn.close();
	}

	public Produto consultar(int id) throws Exception {
		Produto produto = null;

		Connection conn = Conexao.getConexao();

		PreparedStatement stmt = conn.prepareStatement(SELECT_ONE);
		stmt.setInt(1, id);

		conn.setAutoCommit(false);

		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			produto = extrair(rs);
		}
		rs.close();
		stmt.close();
		conn.close();
		return produto;
	}

	public ArrayList<Produto> listar() throws SQLException {

		Connection conn = Conexao.getConexao();
		PreparedStatement stmt = conn.prepareStatement(SELECT_ALL);
		conn.setAutoCommit(false);
		ResultSet rs = stmt.executeQuery();

		ArrayList<Produto> produtos = new ArrayList<>();
		while (rs.next()) {
			Produto produto = extrair(rs);
			produtos.add(produto);
		}

		rs.close();
		stmt.close();
		conn.close();
		return produtos;
	}

	private Produto extrair(ResultSet rs) throws SQLException {
		Categoria categoria = new Categoria(rs.getInt("idcate"), rs.getString("categoria"), rs.getString("descricao"));
		Produto produto = new Produto(rs.getInt("id"), categoria, rs.getString("nome"), rs.getDouble("altura"),
				rs.getDouble("largura"), rs.getDouble("comprimento"), rs.getDouble("preco"), rs.getInt("quantidade"));
		return produto;
	}

	public void excluir(int id) throws Exception {

		Connection conn = Conexao.getConexao();

		PreparedStatement stmt = conn.prepareStatement(DELETE);

		stmt.setInt(1, id);

		int rowAffected = stmt.executeUpdate();

		if (rowAffected == 0) {
			conn.rollback();
			return;
		}
		conn.commit();
		stmt.close();
		conn.close();
	}

	public void alterar(Produto produto) throws Exception {

		Connection conn = Conexao.getConexao();

		PreparedStatement stmt = conn.prepareStatement(UPDATE);

		stmt.setInt(1, produto.getCategoriaId());
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

}
