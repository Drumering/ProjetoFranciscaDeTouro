package com.opet.model;

import java.sql.SQLException;
import java.util.ArrayList;

import com.opet.model.dao.ProdutoDAO;

public class Produto extends ProdutoDAO {
	
	private int id;
	private Categoria categoria;
	private String nome;
	private double altura;
	private double largura;
	private double comprimento;
	private double preco;
	private int quantidade;
	
	public Produto() {

	}

	public Produto(Categoria categoria, String nome, double altura, double largura, double comprimento, double preco,
			int quantidade) {
		this.categoria = categoria;
		this.nome = nome;
		this.altura = altura;
		this.largura = largura;
		this.comprimento = comprimento;
		this.preco = preco;
		this.quantidade = quantidade;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public double getAltura() {
		return altura;
	}
	public void setAltura(double altura) {
		this.altura = altura;
	}
	public double getLargura() {
		return largura;
	}
	public void setLargura(double largura) {
		this.largura = largura;
	}
	public double getComprimento() {
		return comprimento;
	}
	public void setComprimento(double comprimento) {
		this.comprimento = comprimento;
	}
	public double getPreco() {
		return preco;
	}
	public void setPreco(double preco) {
		this.preco = preco;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public void cadastrar() throws Exception {
		super.cadastrar(this);		
	}
	
	public void excluir() throws Exception {
		super.excluir(this);
	}
	
	public void alterar() throws Exception {
		super.alterar(this);
	}
	
	public Produto consultar() throws ClassNotFoundException, SQLException {
		return super.consultar();
	}
	
	public ArrayList<Produto> listar() {
		return super.listar();
	}
}
