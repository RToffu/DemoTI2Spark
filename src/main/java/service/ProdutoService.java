package service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import dao.DAO;
import model.Produto;
import spark.Request;
import spark.Response;


public class ProdutoService {

	private static DAO dao  = new DAO();

	public ProdutoService() {
		dao.conectar();
	}

	public Object add(Request request, Response response) {
	//--> Parse das variaveis inseridas na pagina
		String name = request.queryParams("name");
		String category = request.queryParams("category");
		int quantidade = Integer.parseInt(request.queryParams("quantidade"));

		int id = dao.getMaxId() + 1;

		Produto produto = new Produto(id, name, category, quantidade);

		dao.inserirProduto(produto);

		response.status(201); // 201 Created
		return id;
	}

	public Object get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));
		
		Produto produto = dao.get(id);
		
		if (produto != null) {
    	    response.header("Content-Type", "application/xml");
    	    response.header("Content-Encoding", "UTF-8");

            return "<produto>\n" + 
            		"\t<id>" + produto.getId() + "</id>\n" +
            		"\t<descricao>" + produto.getDevice_name() + "</descricao>\n" +
            		"\t<preco>" + produto.getDevice_category() + "</preco>\n" +
            		"\t<quantidade>" + produto.getQnt() + "</quantidade>\n" +
            		"</produto>\n";
        } else {
            response.status(404); // 404 Not found
            return "Produto " + id + " não encontrado.";
        }

	}

	public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        
		Produto produto = (Produto) dao.get(id);
		produto.toString();
        if (produto != null) {
        	produto.setDevice_name(request.queryParams("name"));
        	produto.setDevice_category(request.queryParams("category"));
        	produto.setQnt(Integer.parseInt(request.queryParams("quantidade")));
        	

        	//dao.atualizarProduto(produto.getId());
        	
            return id;
        } else {
            response.status(404); // 404 Not found
            return "Produto não encontrado.";
        }

	}

	public Object remove(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));

        Produto produto = (Produto) dao.get(id);

        if (produto != null) {

            dao.excluirProduto(produto.getId());

            response.status(200); // success
        	return id;
        } else {
            response.status(404); // 404 Not found
            return "Produto não encontrado.";
        }
	}

	public Object getAll(Request request, Response response) {
		StringBuffer returnValue = new StringBuffer("<produtos type=\"array\">");
		for (Produto produto : dao.getProdutos()) {
			returnValue.append("\n<produto>\n" + 
            		"\t<id>" + produto.getId() + "</id>\n" +
            		"\t<name>" + produto.getDevice_name() + "</name>\n" +
            		"\t<category>" + produto.getDevice_category() + "</category>\n" +
            		"\t<quantidade>" + produto.getQnt() + "</quantidade>\n" +
            		"</produto>\n");
		}
		returnValue.append("</produtos>");
	    response.header("Content-Type", "application/xml");
	    response.header("Content-Encoding", "UTF-8");
		return returnValue.toString();
	}
}