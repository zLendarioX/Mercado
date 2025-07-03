package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connectionFactory.ConnectionDatabase;
import model.Funcionario;
import model.VendaProduto;

public class VendaProdutoDAO {
    public void create(VendaProduto venda) {
		Connection con = ConnectionDatabase.getConnection();
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement("INSERT INTO VendaProduto values(?, ?, ?, ?)");
            stmt.setString(1, venda.getIdVenda());
            stmt.setString(2, venda.getIdProduto());
            stmt.setString(3, venda.getQuantidade());
            stmt.setString(4, venda.getValorTotal());
 
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException("Erro ao Registrar a Venda!", e);
		} finally {
			ConnectionDatabase.closeConnection(con, stmt);
		}

    }
    public ArrayList<VendaProduto> read() {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<VendaProduto> IDs = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM VendaProduto");
            rs = stmt.executeQuery();

            while(rs.next()) {
            	VendaProduto ID = new VendaProduto();
            	ID.setIdVendaProduto(rs.getString("idVendaProduto"));
            	ID.setIdVenda(rs.getString("idVenda"));
            	ID.setIdProduto(rs.getString("idProduto"));
            	ID.setPrecoUn(rs.getString("precoUn"));
            	ID.setValorTotal(rs.getString("precoTotal"));
            	ID.setQuantidade(rs.getString("quantidade"));
                IDs.add(ID);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException("Erro ao ler informações!", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt, rs);
        }        
        return IDs;
    }
    public void update(VendaProduto ID) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE VendaProduto SET idVenda = ?, idProduto = ?, "
                    + "precoUn = ?, quantidade = ?, precoTotal = ? where idVendaProduto = ?");
            stmt.setString(1, ID.getIdVenda());
            stmt.setString(2, ID.getIdProduto());
            stmt.setString(3, ID.getPrecoUn());
            stmt.setString(4, ID.getQuantidade());
            stmt.setString(5, ID.getValorTotal());
            stmt.setString(6, ID.getIdVendaProduto());
            stmt.executeUpdate();
            System.out.println("Comando executado com exito!");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException("Erro ao atualizar!", e);
        }finally {
            ConnectionDatabase.closeConnection(con, stmt);
        }    
    }
    public void delete(String ID) {
    	Connection con = ConnectionDatabase.getConnection();
    	PreparedStatement stmt = null; 
    	
    	 ResultSet rs = null;
         ArrayList<VendaProduto> IDs = new ArrayList<>();
    	 try {
             stmt = con.prepareStatement("DELETE FROM VendaProduto where idVendaProduto = ?");
             stmt.setString(1, ID);

             stmt.execute();
             System.out.println("Comando executado com exito!");

         } catch (SQLException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
             throw new RuntimeException("Erro ao excluir!", e);
         }finally {
             ConnectionDatabase.closeConnection(con, stmt);
         }      
    }
    public ArrayList<VendaProduto> seach(String pesquisa) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<VendaProduto> IDs = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM VendaProduto where idVendaProduto like ?");
            stmt.setString(1, "%"+pesquisa+"%");
            rs = stmt.executeQuery();

            while(rs.next()) {
            	VendaProduto ID = new VendaProduto();
            	ID.setIdVendaProduto(rs.getString("idVendaProduto"));
            	ID.setIdVenda(rs.getString("idVenda"));
            	ID.setIdProduto(rs.getString("idProduto"));
            	ID.setPrecoUn(rs.getString("precoUn"));
            	ID.setValorTotal(rs.getString("precoTotal"));
            	ID.setQuantidade(rs.getString("quantidade"));
                IDs.add(ID);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException("Erro ao ler informações!", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt, rs);
        }        
        return IDs;
    }
}
