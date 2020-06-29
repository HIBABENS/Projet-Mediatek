package dao;
import beans.Client;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientDao {

    public Client getClientById(Long code_clt) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        Client client ;
        Connexion cnx = new Connexion();
        connection = cnx.getConnectionStatement();

        try {
            String query = "select * from client_m where code_Clt = ?";
            statement = connection.prepareStatement(query);
            int counter = 1;
            statement.setLong(counter++,code_clt);
            ResultSet rs = statement.executeQuery();


            rs.next();
            client = new Client();
            client.setCode_Clt(rs.getLong(1));
            client.setNom_Clt(rs.getString(2));
            client.setPrenom_Clt(rs.getString(3));
            client.setAdresse_Clt(rs.getString(4));

            return client;

        } catch (SQLException exception) {
            System.out.println("error de sql client find by id:" + exception.getMessage());
        } finally {
            if (statement != null) {
                statement.close();
            }

            if (connection != null) {
                connection.close();
            }
        }
        return null;
    }


    public List<Client> getAllClient() throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        Connexion cnx = new Connexion();
        connection = cnx.getConnectionStatement();

        Client client ;
        List<Client> clients = new ArrayList<>() ;

        try {

            String query = "select * from Client_m  ";
            statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();


            while(rs.next()){
                client = new Client();
                client.setCode_Clt(rs.getLong(1));
                client.setNom_Clt(rs.getString(2));
                client.setPrenom_Clt(rs.getString(3));

                client.setAdresse_Clt(rs.getString(4));
                clients.add(client);
            }
            return clients;

        } catch (SQLException exception) {
            System.out.println("error de sql get all:" + exception.getMessage());
        } finally {
            if (statement != null) {
                statement.close();
            }

            if (connection != null) {
                connection.close();
            }
        }
        return null;
    }
}