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
				cadastrarProduto();
				break;
			case 2:
				System.out.println("");
				System.out.println("Consultando Produtos...");
				System.out.println("");
				consultaProduto();
				break;
			case 3:
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
	 * @throws Exception
	 */
	public static void cadastrarProduto() throws Exception {

		System.out.println("");
		System.out.println("Informe a Categoria do PRODUTO: ");
		System.out.println("");
		System.out.println("(1) para CASAL");
		System.out.println("(2) para SOLTEIRO");
		System.out.println("(3) para PREMIUM-CASAL");
		System.out.println("(4) para PREMIUM-SOLTEIRO");
		System.out.println("(0) para SAIR");
		int categoria = Reader.readInt();

		if (categoria != 0 && categoria < 5) {
			System.out.println("");
			System.out.println("OBS: A UNIDADE DE MEDIDA EM !CENTIMETRO!");
			System.out.println("");

			System.out.println("Informe o NOME do PRODUTO: ");
			String nome = Reader.readString();
			System.out.println("");

			System.out.println("Informe ALTURA: ");
			double altura = Reader.readDouble();
			System.out.println("");

			System.out.println("Informe LARGURA: ");
			double largura = Reader.readDouble();
			System.out.println("");

			System.out.println("Informe COMPRIMENTO: ");
			double comprimento = Reader.readDouble();
			System.out.println("");

			System.out.println("Informe PRECO: ");
			double preco = Reader.readDouble();
			System.out.println("");

			System.out.println("Informe QUANTIDADE: ");
			int quantidade = Reader.readInt();
			System.out.println("");

			System.out.println("Confirme os Dados Cadastrados: ");
			switch (categoria) {
			case 1:
				System.out.println("Categoria | " + categoria + " | [CASAL]");
				break;
			case 2:
				System.out.println("Categoria | " + categoria + " | [SOLTEIRO]");
				break;
			case 3:
				System.out.println("Categoria | " + categoria + " | [PREMIUM-CASAL]");
				break;
			case 4:
				System.out.println("Categoria | " + categoria + " | [PREMIUM-SOLTEIRO]");
				break;
			default:
				break;
			}
			System.out.println("NOME: | " + nome + " |");
			System.out.println("ALTURA: | " + altura + "cm |");
			System.out.println("LARGURA: | " + largura + "cm |");
			System.out.println("COMPRIMENTO: | " + comprimento + "cm |");
			System.out.println("PRECO: | R$" + preco + " |");
			System.out.println("QUANTIDADE: | " + quantidade + " un |");
			System.out.println("");

			System.out.println("Confirma CADASTRO? (1) - SIM (2) - NAO");
			int confirmacao = Reader.readInt();

			if (confirmacao == 1) {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "system", "1234");

				PreparedStatement stmt = conn.prepareStatement(
						"INSERT INTO produto(proID,idCate,proNome,proAltura,proLargura,proCompr,proPreco,proQntd) VALUES(proSEQ.nextval,?,?,?,?,?,?,?)");

				stmt.setInt(1, categoria);
				stmt.setString(2, nome);
				stmt.setDouble(3, altura);
				stmt.setDouble(4, largura);
				stmt.setDouble(5, comprimento);
				stmt.setDouble(6, preco);
				stmt.setInt(7, quantidade);

				int rowAffected = stmt.executeUpdate();

				if (rowAffected == 0) {
					conn.rollback();
					return;
				}

				System.out.println("");
				consultaProduto();
				System.out.println("");

				conn.commit();
				stmt.close();
				conn.close();
			} else {
				System.out.println("");
				System.out.println("Retorne ao Menu Principal");
				System.out.println("");
			}
		} else {
			System.out.println("");
			System.out.println("Retorne ao Menu Principal");
			System.out.println("");
		}
	}

	public static void consultaProduto() throws ClassNotFoundException, SQLException {

		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "system", "1234");

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

	public static void alterarProduto() throws Exception {
		consultaProduto();
		
		System.out.println("Insira o ID do PRODUTO que deseja realizar modificacoes: ");
		int idAlterar = Reader.readInt();
		
		System.out.println("Deseja alterar qual registro?: ");
		System.out.println("");
		System.out.println("(1) - Categoria");
		System.out.println("(2) - Nome do PRODUTO");
		System.out.println("(3) - Altura");
		System.out.println("(4) - Largura");
		System.out.println("(5) - Comprimento");
		System.out.println("(6) - Preco");
		System.out.println("(7) - Quantidade");
		System.out.println("");
		
	}

	public static void consultaCategoria() {

	}

}
