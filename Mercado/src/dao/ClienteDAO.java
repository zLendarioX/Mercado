
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connectionFactory.ConnectionDatabase;
import model.Cliente;

public class ClienteDAO {

    // create - Criar/ inserir inforações no banco
    // read - Ler informações do banco
    // update - Atualizar informações do banco
    // delete - Apagar informações do banco
    // search - Pesquisar e Ler informações do banco


    public void create(Cliente cliente) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("INSERT INTO Cliente values(?, ?, ?, ?, ?, ?, ?)");
            stmt.setString(1, cliente.getNomeCliente());
            stmt.setString(2, cliente.getCpfCliente());
            stmt.setString(3, cliente.getDataNasc());
            stmt.setString(4, cliente.getTelefone());
            stmt.setString(5, cliente.getEmail());
            stmt.setString(6, cliente.getEndereco());
            stmt.setString(7, cliente.getGenero());

            stmt.execute();
            System.out.println("Cliente cadastrado!");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException("Erro ao cadastrar!", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt);
        }

    }


    public ArrayList<Cliente> read() {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Cliente> clientes = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM Cliente");
            rs = stmt.executeQuery();

            while(rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setIdCliente(rs.getString("idCliente"));
                cliente.setNomeCliente(rs.getString("nomeCliente"));
                cliente.setCpfCliente(rs.getString("cpfCliente"));
                cliente.setDataNasc(rs.getString("dataNasc"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setEmail(rs.getString("email"));
                cliente.setEndereco(rs.getString("endereco"));
                cliente.setGenero(rs.getString("genero"));

                clientes.add(cliente);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException("Erro ao ler informações!", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt, rs);
        }        
        return clientes;
    }



    public void update(Cliente cliente) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE Cliente SET nomeCliente = ?, cpfCliente = ?, "
                    + "dataNasc = ?, telefone = ?, email = ?, endereco = ?, genero = ?"
                    + "where cpfCliente = ?");
            stmt.setString(1, cliente.getNomeCliente());
            stmt.setString(2, cliente.getCpfCliente());
            stmt.setString(3, cliente.getDataNasc());
            stmt.setString(4, cliente.getTelefone());
            stmt.setString(5, cliente.getEmail());
            stmt.setString(6, cliente.getEndereco());
            stmt.setString(7, cliente.getGenero());
            stmt.setString(8, cliente.getCpfCliente());

            stmt.executeUpdate();
            System.out.println("Cliente atualizado!");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException("Erro ao atualizar!", e);
        }finally {
            ConnectionDatabase.closeConnection(con, stmt);
        }    
    }


    public void delete(String cpf) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("DELETE FROM Cliente where cpfCliente = ?");
            stmt.setString(1, cpf);

            stmt.execute();
            System.out.println("Cliente excluido!");

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException("Erro ao excluir!", e);
        }finally {
            ConnectionDatabase.closeConnection(con, stmt);
        }    
    }
    public ArrayList<Cliente> seach(String pesquisa) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Cliente> clientes = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM Cliente where nomeCliente like ? or cpfCliente like ?");
            stmt.setString(1, "%"+pesquisa+"%");
            stmt.setString(2, "%"+pesquisa+"%");
            rs = stmt.executeQuery();

            while(rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setIdCliente(rs.getString("idCliente"));
                cliente.setNomeCliente(rs.getString("nomeCliente"));
                cliente.setCpfCliente(rs.getString("cpfCliente"));
                cliente.setDataNasc(rs.getString("dataNasc"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setEmail(rs.getString("email"));
                cliente.setEndereco(rs.getString("endereco"));
                cliente.setGenero(rs.getString("genero"));

                clientes.add(cliente);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException("Erro ao ler informações!", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt);
        }        
        return clientes;
    }
}