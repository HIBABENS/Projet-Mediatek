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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SAVController implements Initializable {

    @FXML
    public TableView<ObjV> tableViewSAV;
    @FXML
    public TableColumn<ObjV, Long> code;
    @FXML
    public TableColumn<ObjV, String> nom;
    @FXML
    public TableColumn<ObjV, String> prenom;
    @FXML
    public TableColumn<ObjV, String> CA;
    @FXML
    public TableColumn<ObjV, String> categ;
    @FXML
    private TextField filterField;

    ObservableList<ObjV> dataList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        search_client();
      /*  //set up the columns on the table
        code.setCellValueFactory( new PropertyValueFactory<ObjV, Long>("Code_Clt"  ) );
        System.out.println( code );
        nom.setCellValueFactory( new PropertyValueFactory<ObjV, String>("nom"  ) );
        prenom.setCellValueFactory( new PropertyValueFactory<ObjV, String>("prenom"  ) );
        CA.setCellValueFactory( new PropertyValueFactory<ObjV, String>("Chiffre_affaire"  ) );
        categ.setCellValueFactory( new PropertyValueFactory<ObjV, String>("Categorie"  ) );
        //load data
        tableViewSAV.setItems( getClients() );*/
    }


    @FXML
    void search_client() {
        //set up the columns on the table
        code.setCellValueFactory( new PropertyValueFactory<ObjV, Long>("Code_Clt"  ) );
        System.out.println( code );
        nom.setCellValueFactory( new PropertyValueFactory<ObjV, String>("nom"  ) );
        prenom.setCellValueFactory( new PropertyValueFactory<ObjV, String>("prenom"  ) );
        CA.setCellValueFactory( new PropertyValueFactory<ObjV, String>("Chiffre_affaire"  ) );
        categ.setCellValueFactory( new PropertyValueFactory<ObjV, String>("Categorie"  ) );

        dataList = getClients() ;
        tableViewSAV.setItems(dataList);
        FilteredList<ObjV> filteredData = new FilteredList<>(dataList, b -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(client -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (client.getNom().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches Nom
                } else if (client.getPrenom().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches Prenom
                }else if (client.getChiffre_affaire().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches Chiffre_affaire
                }else if (client.getCategorie().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches Categorie
                }

                else
                    return false; // Does not match.
            });
        });
        SortedList<ObjV> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableViewSAV.comparatorProperty());
        tableViewSAV.setItems(sortedData);
    }

    @FXML
    private void Retour(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Home.fxml"));
        Scene login =new Scene(root);
        Stage window= (Stage)((Node)e.getSource()).getScene().getWindow();
        window.setScene(login);
        window.show();
    }

    private ObservableList<ObjV> getClients() {
        Connexion cnx = new Connexion();
        Connection connection = null;
        connection = cnx.getConnectionStatement();
        PreparedStatement statement = null;
        ObjV o = null;
        ObservableList<ObjV> clts = FXCollections.observableArrayList();
        try {
            String query = "SELECT * FROM v_chiffre_affaire ";
            statement = connection.prepareStatement( query );
            int counter = 1;
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                o = new ObjV();
                o.setCode_Clt( rs.getLong( 1 ) );
                o.setNom( rs.getString( 2 ) );
                o.setPrenom( rs.getString( 3 ) );
                o.setChiffre_affaire( rs.getString( 4 ) );
                o.setCategorie( rs.getString( 5 ) );
                clts.add( o );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clts;
    }

}
