package Controllers;

import beans.Client;
import beans.ObjV;
import dao.Connexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CilentController implements Initializable {

    @FXML
    private VBox pnitems;
    @FXML
    public Button btChercherUnClt;
    @FXML
    public Button btAjoutClt;
    @FXML
    public Button btLister;
    @FXML
    public Button supprimer;
    @FXML
    public Button modifier;
    @FXML
    public Button ModifierUnClt;
    @FXML
    public TextField txNomClt;
    @FXML
    public TextField CodeClt;
    @FXML
    public TextField txtPrenomClt;
    @FXML
    public TextField txtModifNom;
    @FXML
    public TextField txtModifPrenom;
    @FXML
    public TextArea txtModifAdresse;
    @FXML
    public TextArea txClt;
    @FXML
    public TextField txtIdClt;
    @FXML
    public Label nomCh;
    @FXML
    public Label prenomCh;
    @FXML
    public Label adresseCh;
    @FXML
    public Label lbCodeItem;
    @FXML
    public Label lbNomItem;
    @FXML
    public Label lbPrenomItem;
    @FXML
    public Text lbAdresseItem;
    @FXML
    public TableView<Client> tableView;
    @FXML
    public TableColumn<Client, Long> Code;
    @FXML
    public TableColumn<Client, String> Nom;
    @FXML
    public TableColumn<Client, String> Prenom;
    @FXML
    public TableColumn<Client, String> Adresse;
    @FXML
    private TextField filterField;

    ObservableList<Client> dataList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       // search_client();
    /*  Node[] nodes = new Node[10];
      for(int i=0; i< nodes.length; i++){
          try {
              nodes[i] = FXMLLoader.load(getClass().getResource( "/fxml/item.fxml" )) ;
              pnitems.getChildren().add(nodes[i]);

          } catch (IOException e) {
              e.printStackTrace();
          }
      }*/
    //set up the columns on the table

    }
    @FXML
    private void chercher(ActionEvent e) throws IOException {
        search_client();
    }
    @FXML
    void search_client() {
        Code.setCellValueFactory( new PropertyValueFactory<Client, Long>("code_Clt"  ) );
        System.out.println( Code );
        Nom.setCellValueFactory( new PropertyValueFactory<Client, String>("nom_Clt"  ) );
        Prenom.setCellValueFactory( new PropertyValueFactory<Client, String>("prenom_Clt"  ) );
        Adresse.setCellValueFactory( new PropertyValueFactory<Client, String>("adresse_Clt"  ) );

        dataList = getClients() ;
        tableView.setItems(dataList);
        FilteredList<Client> filteredData = new FilteredList<>(dataList, b -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(client -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (client.getNom_Clt().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches Nom
                } else if (client.getPrenom_Clt().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches Prenom
                }else if (client.getAdresse_Clt().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches Adresse
                }

                else
                    return false; // Does not match.
            });
        });
        SortedList<Client> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);
    }

    private ObservableList<Client> getClients() {
        Connexion cnx = new Connexion();
        Connection connection = null;
        connection = cnx.getConnectionStatement();
        PreparedStatement statement = null;
        Client c = null;
        ObservableList<Client> clts = FXCollections.observableArrayList();
        try {
            String query = "SELECT * FROM Client_m ";
            statement = connection.prepareStatement( query );
            int counter = 1;
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                c = new Client();
                c.setCode_Clt( rs.getLong( 1 ) );
                c.setNom_Clt( rs.getString( 2 ) );
                c.setPrenom_Clt( rs.getString( 3 ) );
                c.setAdresse_Clt( rs.getString( 4 ) );
                clts.add( c );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clts;
    }
    @FXML
    private void Supprimer(ActionEvent e) throws IOException, SQLException {
        JOptionPane.showMessageDialog(null,"Voulez vous vraiment Supprimer ce Client?");
        if(0 == JOptionPane.OK_OPTION){
            Connexion cnx = new Connexion();
            Connection connection = null;
            connection = cnx.getConnectionStatement();
            Statement stat = connection.createStatement();
            try {
                if(CodeClt.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText(null);
                    alert.setContentText("Le champ code doit etre rempli!");
                    alert.show();
                }else{

                    String query =  " DELETE FROM Client_m WHERE Code_Clt= '"+Long.parseLong(CodeClt.getText())+"'";
                    stat = connection.prepareStatement(query);
                    //  ((PreparedStatement) stat).setLong( 1, Long.parseLong( CodeClt.getText()) );
                    ((PreparedStatement) stat).executeQuery();

                    if(0 == JOptionPane.OK_OPTION){
                        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Client.fxml"));
                        Scene inscription =new Scene(root);
                        Stage window= (Stage)((Node)e.getSource()).getScene().getWindow();
                        window.setScene(inscription);
                        window.show();
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
            JOptionPane.showMessageDialog(null,"Client supprimer");
        }
    }
    static Long varCodeModif;
    @FXML
    private void Modifier(ActionEvent e) throws IOException {
            if(CodeClt.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText(null);
                    alert.setContentText("Le champ code doit etre rempli!");
                    alert.show();
                }else{
                varCodeModif =Long.parseLong(CodeClt.getText());
                System.out.println(varCodeModif );
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/ModifierClient.fxml"));
                Scene scene = new Scene(root);
                Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
                window.setTitle("MODIFIER UN CLIENT");
                window.getIcons().add(new Image("/images/modifer.jpg"));
                window.setScene(scene);
                window.show();
            }
    }
    @FXML
    private void ModifClt(ActionEvent e) throws IOException, SQLException {
        Connexion cnx=new Connexion();
        Connection connection=null;
        connection=cnx.getConnectionStatement();
        Statement stat = connection.createStatement();
        try {
            if(txtModifNom.getText().isEmpty()||txtModifPrenom.getText().isEmpty() ||txtModifAdresse.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Tous les champs doivent être remplis!");
                alert.show();
            }else{
                System.out.println(txtModifNom.getText()  );
                System.out.println(txtModifPrenom.getText() );
                System.out.println(txtModifAdresse.getText() );
                System.out.println(varCodeModif);
                stat.executeQuery( " UPDATE Client_m SET  NOM_CLT= '"+txtModifNom.getText()+"' , PRENOM_CLT= '"+txtModifPrenom.getText()+"' , " +
                        "ADRESSE_CLT= '"+txtModifAdresse.getText()+"'  WHERE CODE_CLT= '"+varCodeModif.longValue()+"'");

                JOptionPane.showMessageDialog(null,"Client Modifié avec succée !");
                if(0 == JOptionPane.OK_OPTION){
                    Parent root = FXMLLoader.load(getClass().getResource("/fxml/Client.fxml"));
                    Scene inscription =new Scene(root);
                    Stage window= (Stage)((Node)e.getSource()).getScene().getWindow();
                    window.setScene(inscription);
                    window.show();
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
    private void Annuler(ActionEvent e) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Client.fxml"));
        Scene login =new Scene(root);
        Stage window= (Stage)((Node)e.getSource()).getScene().getWindow();
        window.setScene(login);
        window.show();
    }
    @FXML
    private void Retour(ActionEvent e) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Home.fxml"));
        Scene login =new Scene(root);
        Stage window= (Stage)((Node)e.getSource()).getScene().getWindow();
        window.getIcons().add(new Image("/images/home.jpg"));
        window.setScene(login);
        window.show();
    }
    @FXML
    public void AjouterUnClt(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/AjouterClient.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setTitle("AJOUTER UN CLIENT");
        window.getIcons().add(new Image("/images/ajouterClient.png"));
        window.setScene(scene);
        window.show();
    }
    @FXML
    public void ChercherUnClt(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/ChercherClient.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setTitle("CHERCHER UN CLIENT");
        window.getIcons().add(new Image("/images/chercherClient.png"));
        window.setScene(scene);
        window.show();
    }
    @FXML
    private void Ajouter(ActionEvent event) throws Exception {
        Connexion cnx=new Connexion();
        Connection connection=null;
        connection=cnx.getConnectionStatement();
        Statement stat = connection.createStatement();
        try {
            if(txNomClt.getText().isEmpty()||txtPrenomClt.getText().isEmpty() ||txClt.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Tous les champs doivent être remplis!");
                alert.show();
            }else{
                stat.executeUpdate( " INSERT INTO Client_m  (Code_Clt, Nom_Clt, Prenom_Clt,Adresse_Clt) " +
                        "VALUES ( clt_seq.nextval, '"+txNomClt.getText()+"', '"
                        +txtPrenomClt.getText()+"','"+txClt.getText()+"')" );

                JOptionPane.showMessageDialog(null,"Client ajouer !");
                if(0 == JOptionPane.OK_OPTION){
                    Parent root = FXMLLoader.load(getClass().getResource("/fxml/Client.fxml"));
                    Scene inscription =new Scene(root);
                    Stage window= (Stage)((Node)event.getSource()).getScene().getWindow();
                    window.setScene(inscription);
                    window.show();
                }
            }

        }catch(Exception e){
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null,e);
        }
        finally{
            if(null != stat){
                stat.close();}

            connection.close();
        }
    }

    @FXML
    private void Chercher(ActionEvent event) throws Exception {
        Connexion cnx = new Connexion();
        Connection connection = null;
        connection = cnx.getConnectionStatement();
        Statement stat = connection.createStatement();
        Client c = new Client();
        try {

            if (txtIdClt.getText().isEmpty()) {
                Alert alert = new Alert( Alert.AlertType.ERROR );
                alert.setTitle( "Erreur" );
                alert.setHeaderText( null );
                alert.setContentText( "Vous devez saisir le code rechercher!" );
                alert.show();
            }else{
                String query =  "SELECT * FROM Client_m WHERE Code_Clt = ?" ;
                stat = connection.prepareStatement(query);
                ((PreparedStatement) stat).setLong( 1, Long.parseLong( txtIdClt.getText().toString() ) );

                ResultSet rs = ((PreparedStatement) stat).executeQuery();
                if (rs.next()) {
                    c.setCode_Clt( rs.getLong( "Code_Clt" ) );
                    c.setNom_Clt( rs.getString( "Nom_Clt" ) );
                    c.setPrenom_Clt( rs.getString( "Prenom_Clt" ) );
                    c.setAdresse_Clt( rs.getString( "Adresse_Clt" ) );
                }
                nomCh.setText( c.getNom_Clt() );
                prenomCh.setText( c.getPrenom_Clt());
                adresseCh.setText( c.getAdresse_Clt() );
            }

        } catch (Exception e) {
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null,e);
        } finally {
            if (null != stat) {
                stat.close();
            }

            connection.close();
        }
    }

    public List<Client> getAllClients() throws SQLException,Exception {
        Connexion cnx = new Connexion();
        Connection connection = null;
        connection = cnx.getConnectionStatement();
        PreparedStatement statement = null;
        Client  c = null;
        List<Client> clients = new ArrayList<>();
        try {
            String query = "SELECT * FROM Client_m ";
            statement = connection.prepareStatement(query);
            int counter = 1;
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                c = new Client();
                c.setCode_Clt(rs.getLong(1));

                c.setNom_Clt(rs.getString(2));
                c.setPrenom_Clt(rs.getString(3));
                c.setAdresse_Clt(rs.getString(4));
                clients.add(c);
            }


            return clients;

        } catch (SQLException exception) {
            System.out.println("error de sql :"+exception.getMessage());
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

    @FXML
    private void ListerLesClts(ActionEvent event) throws Exception {

        Code.setCellValueFactory( new PropertyValueFactory<Client, Long>("code_Clt"  ) );
        System.out.println( Code );
        Nom.setCellValueFactory( new PropertyValueFactory<Client, String>("nom_Clt"  ) );
        Prenom.setCellValueFactory( new PropertyValueFactory<Client, String>("prenom_Clt"  ) );
        Adresse.setCellValueFactory( new PropertyValueFactory<Client, String>("adresse_Clt"  ) );
        //load data
        tableView.setItems( getClients() );

    }
}
