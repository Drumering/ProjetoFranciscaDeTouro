package com.opet.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.opet.conexao.Conexao;
import com.opet.model.Categoria;
import com.opet.model.Produto;
import com.opet.util.Reader;

public class ProdutoDAO {
	
	private static final String INSERT = "INSERT INTO produto(proID,idCate,proNome,proAltura,proLargura,"
			+ "proCompr,proPreco,proQntd) VALUES(proSEQ.nextval,?,?,?,?,?,?,?)";
	private static final String SELECT = "SELECT * FROM produto INNER JOIN "
			+ "categoria ON produto.idCate = categoria.idCate";

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
		System.out.println("(0) - Cancelar alteracao");
		System.out.println("");
		int alterRegistro = Reader.readInt();

		Connection conn = Conexao.getConexao();
		// alterCategoria
		// Execucao de Consulta para busca do nome do ID informado pelo USUARIO
		categoria.consultar();
		// Preparacao da Query SQL
		PreparedStatement stmt = conn.prepareStatement("SELECT proNome FROM produto WHERE proID =?");
		// Insere Variavel ID na primeira Posicao para Busca em WHERE
		stmt.setInt(1, idAlterar);

		// Executa query
		ResultSet rs = stmt.executeQuery();
		// Variavel para Armazenamento da busca
		String proNome = null;
		// Lopp para captura da consulta e armazenamento em variavel
		while (rs.next()) {
			proNome = rs.getString("proNome");
		}

		if (alterRegistro != 0 && alterRegistro < 8) {
			switch (alterRegistro) {
			case 1:
				// Impressao do Resultado e Solicitacao para Leitura de Nova Categoria para
				// produto pesquisado anteriormente
				System.out.println("Informe o ID da nova CATEGORIA do PRODUTO:  " + proNome);
				// Leitura da ID da NOVA Categoria do Produto
				int novaCategoria = Reader.readInt();
				// Preparacao da Query SQL
				PreparedStatement stmtCate = conn.prepareStatement("SELECT nomeCate FROM categoria WHERE idCate = ?");
				// Insere variavel na primeira posicao para WHERE
				stmtCate.setInt(1, novaCategoria);
				// Executa a query
				ResultSet rsCate = stmtCate.executeQuery();
				// Variavel para armazenamento do resultado da QUERY
				String nomeNovaCategoria = null;
				// Loop para captura da consulta e armazenamento em vari�vel e impressao de
				// resultado
				while (rsCate.next()) {
					nomeNovaCategoria = rsCate.getString("nomeCate");
					System.out.println(
							"Confirma alteracao da CATEGORIA do PRODUTO " + proNome + " para: " + nomeNovaCategoria);
				}
				// Solicitacao de confirmacao de acoes
				System.out.println("");
				System.out.println("(1) Sim - (2) Cancelar");
				System.out.println("");
				int confirmacaoCate = Reader.readInt();
				if (confirmacaoCate == 1 && confirmacaoCate < 3) {
					PreparedStatement stmtUpdateCate = conn
							.prepareStatement("UPDATE produto SET idCate = ? WHERE proID = ?");
					stmtUpdateCate.setInt(1, novaCategoria);
					stmtUpdateCate.setInt(2, idAlterar);

					conn.setAutoCommit(false);

					int rowAffected = stmtUpdateCate.executeUpdate();

					if (rowAffected == 0) {
						conn.rollback();
						return;
					} else {
						System.out.println("Alteracao Realizada com Sucesso!");
					}

					conn.commit();
					rsCate.close();
					stmtCate.close();
					stmtUpdateCate.close();
					// stmt.close();
					// rs.close();
					// conn.close();
				} else {
					System.out.println("Operacao Cancelada");
				}
				break;
			case 2:
				// alterNome
				// Impressao do Resultado e Solicitacao para Leitura de Nova Categoria para
				// produto pesquisado anteriormente
				System.out.println("Informe o novo NOME do PRODUTO:  " + proNome);
				// Leitura da ID da NOVO NOME do Produto
				String novoNome = Reader.readString();
				System.out.println("Confirma a alteracao do PROTUDO: " + proNome + " para " + novoNome + "?");
				// Solicitacao das Confirmacoes de Acoes
				System.out.println("");
				System.out.println("(1) Sim - (2) Cancelar");
				System.out.println("");
				int confirmacaoNome = Reader.readInt();
				if (confirmacaoNome == 1 && confirmacaoNome < 3) {
					PreparedStatement stmtUpdateNome = conn
							.prepareStatement("UPDATE produto SET proNome = ? WHERE proID = ?");
					stmtUpdateNome.setString(1, novoNome);
					stmtUpdateNome.setInt(2, idAlterar);

					conn.setAutoCommit(false);

					int rowAffected = stmtUpdateNome.executeUpdate();

					if (rowAffected == 0) {
						conn.rollback();
						return;
					} else {
						System.out.println("Alteracao Realizada com Sucesso!");
					}

					conn.commit();
					stmtUpdateNome.close();
				} else {
					System.out.println("Cancelando Operacao");
				}
				break;
			case 3:
				// alterAltura
				System.out.println("Lembre-se. Unidade de Medida em !cm!");
				System.out.println("Informe a nova ALTURA do PRODUTO: " + proNome);
				System.out.println("");
				int novaAltura = Reader.readInt();
				System.out.println(
						"Confirma a alteracao da ALTURA do PRODUTO: " + proNome + " para " + novaAltura + "cm?");
				System.out.println("");
				System.out.println("(1) Sim - (2) Cancelar");
				int confirmacaoAltura = Reader.readInt();
				if (confirmacaoAltura == 1 && confirmacaoAltura < 3) {
					PreparedStatement stmtUpdateAltura = conn
							.prepareStatement("UPDATE produto SET proAltura = ? WHERE proID = ?");
					stmtUpdateAltura.setInt(1, novaAltura);
					stmtUpdateAltura.setInt(2, idAlterar);

					conn.setAutoCommit(false);

					int rowAffected = stmtUpdateAltura.executeUpdate();

					if (rowAffected == 0) {
						conn.rollback();
						return;
					} else {
						System.out.println("Alteracao Realizada com Sucesso!");
					}

					conn.commit();
					stmtUpdateAltura.close();
				} else {
					System.out.println("Cancelando Operacao");
				}
				break;
			case 4:
				// alterLargura
				System.out.println("Lembre-se. Unidade de Medida em !cm!");
				System.out.println("Informe a nova LARGURA do PRODUTO: " + proNome);
				System.out.println("");
				int novaLargura = Reader.readInt();
				System.out.println(
						"Confirma alteacao da LARGURA do PRODUTO: " + proNome + " para " + novaLargura + "cm?");
				System.out.println("");
				System.out.println("(1) Sim - (2) Cancelar");
				int confirmacaoLargura = Reader.readInt();
				if (confirmacaoLargura == 1 && confirmacaoLargura < 3) {
					PreparedStatement stmtUpdateLargura = conn
							.prepareStatement("UPDATE produto SET proLargura = ? WHERE proID = ?");
					stmtUpdateLargura.setInt(1, novaLargura);
					stmtUpdateLargura.setInt(2, idAlterar);

					conn.setAutoCommit(false);

					int rowAffected = stmtUpdateLargura.executeUpdate();

					if (rowAffected == 0) {
						conn.rollback();
						return;
					} else {
						System.out.println("Alteracao Realizada com Sucesso!");
					}

					conn.commit();
					stmtUpdateLargura.close();
				} else {
					System.out.println("Cancelando Operacao");
				}

				break;
			case 5:
				// alterComprimento
				System.out.println("Lembre-se. Unidade de Medida em !cm!");
				System.out.println("Informe o novo COMPRIMENTO do PRODUTO: " + proNome);
				System.out.println("");
				int novoComprimento = Reader.readInt();
				System.out.println("Confirma alteracao do COMPRIMENTO do PRODUTO: " + proNome + " para "
						+ novoComprimento + "cm?");
				System.out.println("");
				System.out.println("(1) Sim - (2) Cancelar");
				int confirmacaoComprimento = Reader.readInt();
				if (confirmacaoComprimento == 1) {
					PreparedStatement stmtUpdateComprimento = conn
							.prepareStatement("UPDATE produto SET proCompr = ? WHERE proID = ?");
					stmtUpdateComprimento.setInt(1, novoComprimento);
					stmtUpdateComprimento.setInt(2, idAlterar);

					conn.setAutoCommit(false);

					int rowAffected = stmtUpdateComprimento.executeUpdate();

					if (rowAffected == 0) {
						conn.rollback();
						return;
					} else {
						System.out.println("Alteracao Realizada com Sucesso!");
					}

					conn.commit();
					stmtUpdateComprimento.close();
				} else {
					System.out.println("Cancelando Operacao");
				}
				break;
			case 6:
				// alterPreco
				System.out.println("Informe o novo PRECO do PRODUTO: " + proNome);
				System.out.println("");
				double novoPreco = Reader.readDouble();
				System.out.println("Confirma alteracao do PRECO do PRODUTO: " + proNome + " para R$" + novoPreco + "?");
				System.out.println("");
				System.out.println("(1) Sim - (2) Cancelar");
				int confirmacaoPreco = Reader.readInt();
				if (confirmacaoPreco == 1) {
					PreparedStatement stmtUpdatePreco = conn
							.prepareStatement("UPDATE produto SET proPreco = ? WHERE proID = ?");
					stmtUpdatePreco.setDouble(1, novoPreco);
					stmtUpdatePreco.setInt(2, idAlterar);

					conn.setAutoCommit(false);

					int rowAffected = stmtUpdatePreco.executeUpdate();

					if (rowAffected == 0) {
						conn.rollback();
						return;
					} else {
						System.out.println("Alteracao Realizada com Sucesso!");
					}

					conn.commit();
					stmtUpdatePreco.close();
				} else {
					System.out.println("Cancelando Operacao");
				}
				break;
			case 7:
				// alterQuantidade
				System.out.println("Informe a nova QUANTIDADE do PRODUTO: " + proNome);
				System.out.println("");
				int novaQnt = Reader.readInt();
				System.out
						.println("Confirma alteracao da QUANTIDADE do PRODUTO: " + proNome + " para " + novaQnt + "?");
				System.out.println("(1) Sim - (2) Cancelar");
				int confirmacaoQnt = Reader.readInt();
				if (confirmacaoQnt == 1) {
					PreparedStatement stmtUpdateQnt = conn
							.prepareStatement("UPDATE produto SET proQntd = ? WHERE proID = ?");
					stmtUpdateQnt.setInt(1, novaQnt);
					stmtUpdateQnt.setInt(2, idAlterar);

					conn.setAutoCommit(false);

					int rowAffected = stmtUpdateQnt.executeUpdate();

					if (rowAffected == 0) {
						conn.rollback();
						return;
					} else {
						System.out.println("Alteracao Realizada com Sucesso!");
					}

					conn.commit();
					stmtUpdateQnt.close();
					stmt.close();
					rs.close();
					conn.close();

				} else {
					System.out.println("Cancelando Operacao");
				}
				break;
			case 0:
				break;
			default:
				break;
			}
		} else if (alterRegistro >= 8) {
			System.out.println("Opcao Invalida, cancelando Operacao");
			System.out.println("");
		} else {
			System.out.println("Cancelando Operacao");
			System.out.println("");
		}
	}
	
	public void excluir(Produto produto) throws Exception {

		System.out.println("digite o ID produto gostaria de excluir:");
		System.out.println("");
		consultar();
		System.out.println("");
		int ID = Reader.readInt(); // inteiro id excluir
		Connection conn = Conexao.getConexao();

		PreparedStatement stmt = conn.prepareStatement("DELETE FROM produto WHERE proID = ?");

		stmt.setInt(1, ID);

		int rowAffected = stmt.executeUpdate();
		
		if(rowAffected == 0) {
			conn.rollback();
			return;
		}
		
		System.out.println("Produto Excluido");
		System.out.println("");
		
		conn.commit();
		stmt.close();
		conn.close();

	}

	public ArrayList<Produto> listar() {
		return null;
	}

}
