package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connectionFactory.ConnectionDatabase;
import model.Venda;

public class VendaDAO {
	
	public void create(Venda venda) {
		Connection con = ConnectionDatabase.getConnection();
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement("INSERT INTO Venda values(?, ?, ?, ?, ?)");
            stmt.setString(1, venda.getIdCliente());
            stmt.setString(2, venda.getIdFuncionario());
            stmt.setString(3, venda.getPrecoTotal());
            stmt.setString(4, venda.getQuantTotal());
            stmt.setString(5, venda.getDataVenda());
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException("Erro ao Registrar a Venda!", e);
		} finally {
			ConnectionDatabase.closeConnection(con, stmt);
		}

    }
    public ArrayList<Venda> read() {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Venda> IDs = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM Venda");
            rs = stmt.executeQuery();

            while(rs.next()) {
            	Venda ID = new Venda();
            	ID.setIdVenda(rs.getString("idVenda"));
            	ID.setIdCliente(rs.getString("idCliente"));
            	ID.setIdFuncionario(rs.getString("idFuncionario"));
            	ID.setPrecoTotal(rs.getString("valorTotal"));
            	ID.setQuantTotal(rs.getString("quantidade"));
            	ID.setDataVenda(rs.getString("dataVenda"));
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
    public void update(Venda venda) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE Venda SET idCliente = ?, idFuncionario = ?, "
                    + "valorTotal = ?, quantTotal = ?, dataVenda = ? where idVenda = ?");
            stmt.setString(1, venda.getIdCliente());
            stmt.setString(2, venda.getIdFuncionario());
            stmt.setString(3, venda.getPrecoTotal());
            stmt.setString(4, venda.getQuantTotal());
            stmt.setString(5, venda.getDataVenda());
            stmt.setString(6, venda.getIdVenda());
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
         ArrayList<Venda> IDs = new ArrayList<>();
    	 try {
             stmt = con.prepareStatement("DELETE FROM Venda where idVenda = ?");
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
    public ArrayList<Venda> seach(String pesquisa) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Venda> IDs = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM Venda where idVenda like ?");
            stmt.setString(1, "%"+pesquisa+"%");
            rs = stmt.executeQuery();

            while(rs.next()) {
            	Venda ID = new Venda();
            	ID.setIdVenda(rs.getString("idVenda"));
            	ID.setIdCliente(rs.getString("idCliente"));
            	ID.setIdFuncionario(rs.getString("idFuncionario"));
            	ID.setPrecoTotal(rs.getString("valorTotal"));
            	ID.setQuantTotal(rs.getString("quantidade"));
            	ID.setDataVenda(rs.getString("dataVenda"));
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
