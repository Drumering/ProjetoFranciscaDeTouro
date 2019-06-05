package com.opet.model;

import com.opet.model.dao.CategoriaDAO;

public class Categoria extends CategoriaDAO{
	
	private int id;
	private String nome;
	private String descricao;
	
	public Categoria() {}
	
	public Categoria(int id, String nome, String descricao) {
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public void cadastrar() throws Exception {
		super.cadastrar(this);
	}
	
	public void excluir() throws Exception {
		super.excluir(this);
	}
	
	public Categoria consultar() throws Exception {
		return super.consultar();
	}
}