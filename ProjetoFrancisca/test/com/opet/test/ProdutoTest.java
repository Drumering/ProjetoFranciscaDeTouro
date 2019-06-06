package com.opet.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.opet.model.Categoria;
import com.opet.model.Produto;

class ProdutoTest {
	
	@Test
	void testeCadastrarProduto() throws Exception {
		Categoria categoria = new Categoria(1, "Infantil", "algodao");
		Produto produto = new Produto(categoria, "Box", 1.1, 1.1, 1.1, 100, 2);
		produto.cadastrar();
		Produto produto2 = produto.consultar();
		
		assertEquals(produto2.getNome() ,produto.getNome());
		assertEquals(produto2.getQuantidade(), produto.getQuantidade());
	}
	
	@Test
	void testeConsultarProduto() throws Exception {
		Produto produto = new Produto();
		produto.setId(1);
		Produto produto2 = produto.consultar();
		
		assertEquals("Box" ,produto2.getNome());
		assertEquals(2, produto2.getQuantidade());
		
		assertEquals(1 ,produto2.getId());
		assertEquals(1, produto2.getCategoriaId());
	}
	
	@Test
	void testeExcluirProduto() throws Exception {
		Produto produto = new Produto();
		produto.setId(7);
		produto.excluir();
	}
	
	@Test
	void testeListarProdutos() {
		Produto produto = new Produto();
		ArrayList<Produto> produtos = produto.listar();
		
		assertTrue(produtos.size() > 0);
	}
}
