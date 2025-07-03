package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connectionFactory.ConnectionDatabase;
import model.Cliente;
import model.Produto;

public class ProdutoDAO {
	public void create(Produto produto) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("INSERT INTO Produto values(?, ?, ?, ?, ?, ?, ?)");
            stmt.setString(1, produto.getNomeProduto());
            stmt.setString(2, produto.getCodBarra());
            stmt.setString(3, produto.getTipoUn());
            stmt.setString(4, produto.getPrecoUn());
            stmt.setString(5, produto.getEstoque());
            stmt.setString(6, produto.getDataFab());
            stmt.setString(7, produto.getDataVal());

            stmt.execute();
            System.out.println("Produto cadastrado!");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException("Erro ao cadastrar!", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt);
        }

    }
	public ArrayList<Produto> read(){
		Connection con = ConnectionDatabase.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null; 
		ArrayList<Produto> produtos = new ArrayList<>();
		
		try {
			stmt = con.prepareStatement("SELECT * FROM Produto;");
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				Produto produto = new Produto();
				produto.setIdProduto(rs.getString("idProduto"));
				produto.setNomeProduto(rs.getString("nomeProduto"));
				produto.setCodBarra(rs.getString("codBarra"));
				produto.setTipoUn(rs.getString("tipoUn"));
				produto.setPrecoUn(rs.getString("precoUn"));
				produto.setEstoque(rs.getString("estoque"));
				produto.setDataFab(rs.getString("dataFab"));
				produto.setDataVal(rs.getString("dataVal"));
				produtos.add(produto);
			}
		} catch (Exception e) {
			throw new RuntimeException("Erro ao consultar tabela");
		} finally {
			ConnectionDatabase.closeConnection(con, stmt);
		}
		 
		return produtos;
	}
	public void update(Produto produto) {
		Connection con = ConnectionDatabase.getConnection();
		PreparedStatement stmt = null;
		
		try {
			stmt = con.prepareStatement("UPDATE Produto SET nomeProduto = ?, codBarra = ?, tipoUn = ?, "
					+ "precoUn = ?, estoque = ?, dataFab = ?, dataVal = ? where codBarra = ?");
			stmt.setString(1, produto.getNomeProduto());
			stmt.setString(2, produto.getCodBarra());
			stmt.setString(3, produto.getTipoUn());
			stmt.setString(4, produto.getPrecoUn());
			stmt.setString(5, produto.getEstoque());
			stmt.setString(6, produto.getDataFab());
			stmt.setString(7, produto.getDataVal());
			stmt.setString(8, produto.getCodBarra());
			
			stmt.executeUpdate();
            System.out.println("Produto atualizado atualizado!");
		} catch (Exception e) {
			// TODO: handle exception
			 throw new RuntimeException("Erro ao atualizar!", e);
		} finally {
			ConnectionDatabase.closeConnection(con, stmt);
		}
	}
	
	public void delete(String idProduto) {
		Connection con = ConnectionDatabase.getConnection();
		PreparedStatement stmt = null; 
		try {
			stmt = con.prepareStatement("DELETE Produto WHERE idProduto = ?;");
			stmt.setString(1, idProduto);
			stmt.execute();
			System.out.print("\nProduto deletado!\n\n");
		} catch (Exception e) {
			// TODO: handle exception
			 throw new RuntimeException("Erro ao deletar!", e);
		} finally {
			ConnectionDatabase.closeConnection(con, stmt);
		}
	}
	 public ArrayList<Produto> seach(String pesquisa) {
	        Connection con = ConnectionDatabase.getConnection();
	        PreparedStatement stmt = null;
	        ResultSet rs = null;
	        ArrayList<Produto> produtos = new ArrayList<>();

	        try {
	            stmt = con.prepareStatement("SELECT * FROM Produto where nomeProduto like ? or codBarra like ?");
	            stmt.setString(1, "%"+pesquisa+"%");
	            stmt.setString(2, "%"+pesquisa+"%");
	            rs = stmt.executeQuery();
	    
            	while(rs.next()) {
 	            	Produto produto = new Produto();
 					produto.setIdProduto(rs.getString("idProduto"));
 					produto.setNomeProduto(rs.getString("nomeProduto"));
 					produto.setCodBarra(rs.getString("codBarra"));
 					produto.setTipoUn(rs.getString("tipoUn"));
 					produto.setPrecoUn(rs.getString("precoUn"));
 					produto.setEstoque(rs.getString("estoque"));
 					produto.setDataFab(rs.getString("dataFab"));
 					produto.setDataVal(rs.getString("dataVal"));
 					produtos.add(produto);
 	            } 
            
	        } catch (SQLException e) {
	            // TODO Auto-generated catch block
	            throw new RuntimeException("Erro ao ler informações!", e);
	        } finally {
	            ConnectionDatabase.closeConnection(con, stmt);
	        }        
	        return produtos;
	    }
}
