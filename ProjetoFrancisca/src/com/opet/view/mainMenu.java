package com.opet.view;

import com.opet.model.Categoria;
import com.opet.model.Produto;
import com.opet.util.Reader;

public class mainMenu {

	public static void main(String[] args) throws Exception {
		Categoria categoria = new Categoria();
		Produto produto = new Produto();
		
		int opc = -1;
		while ((opc = menu()) != 0) {
			switch (opc) {
			case 1:
				System.out.println("Informe os dados do produto a ser CADASTRADO: ");
				produto.cadastrar();
				break;
			case 2:
				System.out.println("");
				System.out.println("Consultando Produtos...");
				System.out.println("");
				produto.consultar();
				break;
			case 3:
				System.out.println("");
				System.out.println("Alterando Produtos...");
				System.out.println("");
				produto.alterar();
				break;
			case 4:
				produto.excluir();
				break;
			case 5:
				System.out.println("Informe os dados da categoria a ser CADASTRADA: ");
				categoria.cadastrar();
				break;
			case 6:
				System.out.println("Consultando Categorias...");
				categoria.consultar();
				break;
			case 7:
				System.out.println("Informe o ID da Categoria que deseja alterar: ");
				categoria.alterar();
				break;
			case 8:
				categoria.excluir(categoria);
				break;
			case 0:
				System.out.println("Saindo...");
				break;
			default:
				System.out.println("Opcao invalida, verifique sua digitacao e tente novamente");
			}
		}

	}

	public static int menu() throws Exception {

		int opc = -1;
		System.out.println("1 - Cadastro novo produto");
		System.out.println("2 - Consulta produtos");
		System.out.println("3 - Alterar produtos");
		System.out.println("4 - Excluir produtos");
		System.out.println("5 - Cadastro nova categoria");
		System.out.println("6 - Consulta categorias");
		System.out.println("7 - Alterar cagetorias");
		System.out.println("8 - Excluir categorias");
		System.out.println("0 - Sair");

		opc = Reader.readInt();

		return opc;
	}
}
