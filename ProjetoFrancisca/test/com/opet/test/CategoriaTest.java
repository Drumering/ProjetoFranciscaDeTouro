package com.opet.test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;

import com.opet.model.Categoria;

class CategoriaTest {
	
	@Test
	@Ignore
	void testeCadastrarCategoria() throws Exception {
		Categoria categoria = new Categoria("Adulto", "Algodao Egipcio");
		categoria.cadastrar();
		assertEquals("Adulto", categoria.getNome());
		assertEquals("Algodao Egipcio", categoria.getDescricao());
		
		Categoria categoria2 = categoria.consultar();
		assertEquals(categoria.getNome(), categoria2.getNome());
		assertEquals(categoria.getDescricao(), categoria2.getDescricao());
	}
	
	@Test
	@Ignore
	void testeExcluirCategoria() throws Exception {
		Categoria categoria = new Categoria(13, "Adulto", "Algodao Egipcio");
		categoria.excluir();
	}
	
	@Test
	void testeListarCategorias() {
		Categoria categoria = new Categoria();
		ArrayList<Categoria> categorias = categoria.listar();
		
		assertTrue(categorias.size() > 0);
	}

}
