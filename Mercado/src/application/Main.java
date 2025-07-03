package application;
	
import java.sql.Connection;
import java.util.ArrayList;

import com.sun.javafx.image.impl.ByteIndexed.Getter;

import connectionFactory.ConnectionDatabase;
import dao.ClienteDAO;
import dao.FuncionarioDAO;
import dao.ProdutoDAO;
import dao.VendaProdutoDAO;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Cliente;
import model.Funcionario;
import model.Produto;
import model.VendaProduto;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,1080,720);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {	
		//loadProduto();
		LoadFuncionarios();
		//LoadFClientes();
		//seachProduto("Macarrão");
		//LoadVendaProduto();
		//deleteVendaProduto();
		
		
	}
	static void deleteVendaProduto() {
		VendaProduto prod = new VendaProduto();
		VendaProdutoDAO DAO = new VendaProdutoDAO();

		DAO.delete("2");
		
		System.out.println("---------------------------------------------------------");
	}
	static void LoadVendaProduto() {
		VendaProduto prod = new VendaProduto();
		VendaProdutoDAO DAO = new VendaProdutoDAO();
		
		ArrayList<VendaProduto> prods = new ArrayList<>();
		prods  = DAO.read();
		System.out.println("---------------[ VendaProduto ]---------------------");
		for(int i = 0; i<prods.size(); i++) {
			prod = prods.get(i);
			System.out.printf("ID: %s IDVenda: %s IDProduto: %s quantidade: %s preco Total: %s\n",prod.getIdVendaProduto(), prod.getIdVenda(), prod.getIdProduto(), prod.getQuantidade(), prod.getValorTotal());
			
		}
		System.out.println("---------------------------------------------------------");
	}
	static void LoadFuncionarios() {
		Funcionario userID = new Funcionario();
		FuncionarioDAO DAO = new FuncionarioDAO();
		
		ArrayList<Funcionario> users = new ArrayList<>();
		users = DAO.read();
		for(int i = 0; i<users.size(); i++) {
			userID = users.get(i);

			System.out.printf("\nID: %s \nNome: %s \nCargo: %s \nSalario: %s \nemail: %s \nTelefone: %s\n",userID.getIdFuncionario(), userID.getNomeFuncionario(), userID.getCargo(), userID.getSalario(), userID.getEmail(), userID.getTelefone());
			
		}
		System.out.println("---------------------------------------------------------");
	}
	static void LoadFClientes() {
		Cliente userID = new Cliente();
		ClienteDAO DAO = new ClienteDAO();
		
		ArrayList<Cliente> users = new ArrayList<>();
		users = DAO.read();
		for(int i = 0; i<users.size(); i++) {
			userID = users.get(i);

			System.out.printf("\nID: %s \nNome: %s \nCPF: %s \nGenero: %s \nemail: %s \nTelefone: %s\n",userID.getIdCliente(), userID.getNomeCliente(), userID.getCpfCliente(), userID.getGenero(), userID.getEmail(), userID.getTelefone());
			System.out.println("---------------------------------------------------------");
		}
	}
	static void loadProduto() {
		Produto prod = new Produto();
		ProdutoDAO DAO = new ProdutoDAO();
		
		ArrayList<Produto> prods = new ArrayList<>();
		prods  = DAO.read();
		System.out.println("---------------[ LISTA DE PRODUTOS ]---------------------");
		for(int i = 0; i<prods.size(); i++) {
			prod = prods.get(i);
			System.out.printf("ID: %s Nome: %s Codigo de Barras: %s Validade: %s\n",prod.getIdProduto(), prod.getNomeProduto(), prod.getCodBarra(), prod.getDataVal());
			
		}
		System.out.println("---------------------------------------------------------");
	}
	static void seachProduto(String pesquisa) {
		Produto prod = new Produto();
		ProdutoDAO DAO = new ProdutoDAO();
		
		ArrayList<Produto> prods = new ArrayList<>();
		prods  = DAO.seach(pesquisa);
		for(int i = 0; i<prods.size(); i++) {
			prod = prods.get(i);
			System.out.printf("ID: %s Nome: %s Codigo de Barras: %s Validade: %s\n",prod.getIdProduto(), prod.getNomeProduto(), prod.getCodBarra(), prod.getDataVal());
			System.out.println("---------------------------------------------------------");
		}
	}
}