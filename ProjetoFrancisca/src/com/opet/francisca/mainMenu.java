package com.opet.francisca;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.opet.util.Reader;

//import sun.security.action.GetIntegerAction;

public class mainMenu {

	public static void main(String[] args) throws Exception {
		// Initialize Program
		int opc = -1;
		while ((opc = menu()) != 0) {
			switch (opc) {
			case 1:
				System.out.println("Informe os dados do produto a ser CADASTRADO: ");
				// consultaProduto();
				
				// cadastroProduto();
				break;
			case 2:
				System.out.println("");
				System.out.println("Consultando Produtos...");
				System.out.println("");
				consultaProduto();
				break;
			case 3:
				System.out.println("Informe o ID do produto que deseja alterar: ");
				// alterarProduto();
				break;
			case 4:
				System.out.println("Informe o ID do produto que deseja excluir: ");
				// excluirProduto();
				break;
			case 5:
				System.out.println("Informe os dados da categoria a ser CADASTRADA: ");
				// cadastroCategoria();
				break;
			case 6:
				System.out.println("Consultando Categorias...");
				// consultaCategoria();
				break;
			case 7:
				System.out.println("Informe o ID da Categoria que deseja alterar: ");
				// alterarCategoria();
				break;
			case 8:
				System.out.println("Informe o ID da Categoria que deseja excluir: ");
				// excluirCategoria();
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

	/**
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void cadastrarProduto() {
		System.out.println("Informe os dados para cadastro do NOVO produto: ");
		
	}

	public static void consultaProduto() throws ClassNotFoundException, SQLException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "system", "system");

		PreparedStatement stmt = conn.prepareStatement(
				"SELECT proID,proNome,nomeCate FROM produto INNER JOIN categoria ON produto.idCate = categoria.idCate");

		conn.setAutoCommit(false);

		ResultSet rs = stmt.executeQuery();

		int proID;
		String proNome;
		String nomeCate;

		while (rs.next()) {
			proID = rs.getInt("proID");
			proNome = rs.getString("proNome");
			nomeCate = rs.getString("nomeCate");
			System.out.println("ID do Produto: |" + proID + "| Nome do Produto: |" + proNome
					+ "| Nome da Categoria do Produto: |" + nomeCate + "|");
		}
		System.out.println("");
		System.out.println("Fim da Consulta");
		System.out.println("");

		rs.close();
		stmt.close();
		conn.close();
	}
	
	public static void consultaCategoria() {
		
	}

}
