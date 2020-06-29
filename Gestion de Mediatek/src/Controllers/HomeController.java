package Controllers;

import beans.Client;
import beans.Utilisateur;
import dao.Connexion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML
    public TextField login;
    @FXML
    public TextField loginPass;
static Utilisateur user;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML
    public void Login(ActionEvent event) throws IOException, SQLException {
        Connexion cnx = new Connexion();
        Connection connection = null;
        connection = cnx.getConnectionStatement();
        Statement stat = connection.createStatement();
        try {
            if(login.getText().isEmpty()||loginPass.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Le champ code doit etre rempli!");
                alert.show();
            }else{

                String query =  " SELECT * FROM Utilisateur WHERE login= '"+login.getText()+"'";
                stat = connection.prepareStatement(query);
                //  ((PreparedStatement) stat).setLong( 1, Long.parseLong( CodeClt.getText()) );
                ResultSet rs = ((PreparedStatement) stat).executeQuery();
                if (rs!=null){
                    while (rs.next()) {
                        Utilisateur u = new Utilisateur();
                        u.setStatus( rs.getString( 3 ) );
                       user = u;
                    }
                    Parent root = FXMLLoader.load(getClass().getResource("/fxml/Home.fxml"));
                    Scene login =new Scene(root);
                    Stage window= (Stage)((Node)event.getSource()).getScene().getWindow();
                    window.getIcons().add(new Image("/images/home.png"));
                    window.setScene(login);
                    window.show();
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText(null);
                    alert.setContentText("Vous n'ete pas autoriser a utiliser cette application!");
                    alert.show();
                }


            }

        }catch(Exception ex){
            ex.printStackTrace();
            //JOptionPane.showMessageDialog(null,e);
        }
        finally{
            if(null != stat){
                stat.close();}

            connection.close();
        }
    }

    @FXML
    public void Clients(ActionEvent event) throws IOException, SQLException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Client.fxml"));
        Scene login =new Scene(root);
        Stage window= (Stage)((Node)event.getSource()).getScene().getWindow();
        window.getIcons().add(new Image("/images/listerClient.png"));
        window.setScene(login);
        window.show();
    }
    @FXML
    public void Inventaire(ActionEvent event) throws IOException, SQLException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Inventaire des produits.fxml"));
        Scene login =new Scene(root);
        Stage window= (Stage)((Node)event.getSource()).getScene().getWindow();
        window.getIcons().add(new Image("/images/checkStk1.png"));
        window.setScene(login);
        window.show();
    }
    @FXML
    public void SAV(ActionEvent event) throws IOException, SQLException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/ServiceSAV.fxml"));
        Scene login =new Scene(root);
        Stage window= (Stage)((Node)event.getSource()).getScene().getWindow();
        window.getIcons().add(new Image("/images/checkStk1.png"));
        window.setScene(login);
        window.show();
    }
    @FXML
    public void Cmd_Fournisseurs(ActionEvent event) throws IOException, SQLException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Commandes_Fournisseurs.fxml"));
        Scene login =new Scene(root);
        Stage window= (Stage)((Node)event.getSource()).getScene().getWindow();
        window.getIcons().add(new Image("/images/checkStk1.png"));
        window.setScene(login);
        window.show();
    }
    @FXML
    public void Produits(ActionEvent event) throws IOException, SQLException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Produit.fxml"));
        Scene login =new Scene(root);
        Stage window= (Stage)((Node)event.getSource()).getScene().getWindow();
        window.getIcons().add(new Image("/images/checkStk1.png"));
        window.setScene(login);
        window.show();
    }
    @FXML
    public void Factures(ActionEvent event) throws IOException, SQLException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Facture.fxml"));
        Scene login =new Scene(root);
        Stage window= (Stage)((Node)event.getSource()).getScene().getWindow();
        window.getIcons().add(new Image("/images/checkStk1.png"));
        window.setScene(login);
        window.show();
    }

}
