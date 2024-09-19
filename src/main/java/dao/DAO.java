package dao;

import java.sql.*;
import java.util.*;
import model.Produto;

public class DAO {
//--> ATRIBUTOS
	private Connection conexao;

//--> CONTSTRUTOR
	public DAO() {
		conexao = null;
	}

	public boolean conectar() {
		// --> ATRIBUTOS
		String driverName = "org.postgresql.Driver";
		String serverName = "localhost";
		String mydatabase = "teste";
		int porta = 5432;
		String url = "jdbc:postgresql://" + serverName + ":" + porta + "/" + mydatabase;
		String username = "ti2cc";
		String password = "ti2cc";
		boolean status = false;

		try {
			Class.forName(driverName);
			conexao = DriverManager.getConnection(url, username, password);
			status = (conexao == null);
			System.out.println("Conexão efetuada com o postgres!");
		} catch (ClassNotFoundException e) {
			System.err.println("Conexão NÃO efetuada com o postgres -- Driver não encontrado -- " + e.getMessage());
		} catch (SQLException e) {
			System.err.println("Conexão NÃO efetuada com o postgres -- " + e.getMessage());
		}

		return status;
	}

	public boolean close() {
		boolean status = false;

		try {
			conexao.close();
			status = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return status;
	}

	public boolean inserirProduto(Produto produto) {
		boolean status = false;
		try {
			Statement st = conexao.createStatement();
			st.executeUpdate("INSERT INTO produto (id, device_name, device_category, qnt) " + "VALUES ("
					+ produto.getId() + ", '" + produto.getDevice_name() + "', '" + produto.getDevice_category()
					+ "', '" + produto.getQnt() + "');");
			st.close();
			status = true;
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}

	public Produto get (int id) {
		Produto produto;
		try {
		Statement st = conexao.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM produto WHERE id = " + id);
		rs.next();
		produto = new Produto(Integer.parseInt(rs.getString(1)));
		produto.setDevice_name(rs.getString(2));
		produto.setDevice_category(rs.getString(3));
		produto.setQnt(Integer.parseInt(rs.getString(4)));
		return produto;
		}catch (SQLException u) {
			throw new RuntimeException(u);
		}
	
	}

	public boolean atualizarProduto(int id, Scanner scan) {
		boolean status = false;
		Produto produto;
		try {
			int x = 0;
			Statement st = conexao.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM produto WHERE id = " + id);
			rs.next();
			produto = new Produto(Integer.parseInt(rs.getString(1)));
			produto.setDevice_name(rs.getString(2));
			produto.setDevice_category(rs.getString(3));
			produto.setQnt(Integer.parseInt(rs.getString(4)));
			System.out.println(produto.toString());

			do {
				System.out.printf("\n\n");
				System.out.println("1 - Alterar nome do device");
				System.out.println("2 - Alterar categoria do device");
				System.out.println("3 - Alterar quantidade");
				System.out.println();
				System.out.println("0 - Atualizar");
				System.out.print("Escolha uma opcao: ");
				x = scan.nextInt();
				scan.nextLine();

				switch (x) {
				case 0:
					break;
				case 1:
					String device_name = scan.nextLine();
					produto.setDevice_name(device_name);
					break;
				case 2:
					String device_category = scan.nextLine();
					produto.setDevice_category(device_category);
					break;
				case 3:
					int qnt = scan.nextInt();
					scan.nextLine();
					produto.setQnt(qnt);
					break;
				default:
					System.out.println("Opcao invalida");
					break;
				}
			} while (x != 0);

			String sql = "UPDATE produto SET device_name = '" + produto.getDevice_name() + "', device_category = '"
					+ produto.getDevice_category() + "', qnt = '" + produto.getQnt() + "'" + " WHERE id = "
					+ produto.getId();
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}

	public boolean excluirProduto(int id) {
		boolean status = false;
		try {
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM produto WHERE id = " + id);
			st.close();
			status = true;
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}

	public int getMaxId() {
		int id = 0;
		try {
			Statement st = conexao.createStatement();
			ResultSet rs = st.executeQuery("SELECT MAX(id) FROM produto");
			rs.next();
			id = Integer.parseInt(rs.getString(1));
		} catch (Exception e) {
			return 0;
		}
		return id;
	}

	public Produto[] getProdutos() {
		Produto[] produtos = null;

		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM produto");
			if (rs.next()) {
				rs.last();
				produtos = new Produto[rs.getRow()];
				rs.beforeFirst();

				for (int i = 0; rs.next(); i++) {
					produtos[i] = new Produto(rs.getInt("id"), rs.getString("device_name"),
							rs.getString("device_category"), rs.getInt("qnt"));
				}
			}
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return produtos;
	}

}
