package Controllers;

import beans.ObjV;
import beans.Produit;
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
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class InventairePrdController implements Initializable {
    @FXML
    public TableView<ObjV> tableViewInv;
    @FXML
    public TableColumn<ObjV, Integer> code;
    @FXML
    public TableColumn<ObjV, Integer> qte;
    @FXML
    public TableColumn<ObjV, String> libelle;
    @FXML
    public TableColumn<ObjV, Double> prix;
    @FXML
    public TableColumn<ObjV, String> etat;
    @FXML
    private TextField filterField;

    ObservableList<ObjV> dataList;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        search_produits() ;
     /*   //set up the columns on the table
        code.setCellValueFactory( new PropertyValueFactory<ObjV, Integer>("id_pro"  ) );
        System.out.println( code );
        libelle.setCellValueFactory( new PropertyValueFactory<ObjV, String>("lib_pro"  ) );
        prix.setCellValueFactory( new PropertyValueFactory<ObjV, Double>("prix_unit_pro"  ) );
        qte.setCellValueFactory( new PropertyValueFactory<ObjV, Integer>("qte_dispo"  ) );
        etat.setCellValueFactory( new PropertyValueFactory<ObjV, String>("fuction"  ) );
        code.setSortType(TableColumn.SortType.ASCENDING);
        //load data
        tableViewInv.setItems( getProduits() );*/
    }

    @FXML
    void search_produits() {
        code.setCellValueFactory( new PropertyValueFactory<ObjV, Integer>("id_pro"  ) );
        System.out.println( code );
        libelle.setCellValueFactory( new PropertyValueFactory<ObjV, String>("lib_pro"  ) );
        prix.setCellValueFactory( new PropertyValueFactory<ObjV, Double>("prix_unit_pro"  ) );
        qte.setCellValueFactory( new PropertyValueFactory<ObjV, Integer>("qte_dispo"  ) );
        etat.setCellValueFactory( new PropertyValueFactory<ObjV, String>("fuction"  ) );

        dataList = getProduits();
        tableViewInv.setItems(dataList);
        FilteredList<ObjV> filteredData = new FilteredList<>(dataList, b -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(objV -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (objV.getLib_pro().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches libelle
                } else if (objV.getFuction().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches demande
                }

                else
                    return false; // Does not match.
            });
        });
        SortedList<ObjV> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableViewInv.comparatorProperty());
        tableViewInv.setItems(sortedData);
    }


    //static List list = new ArrayList();
    private ObservableList<ObjV> getProduits() {
        Connexion cnx = new Connexion();
        Connection connection = null;
        connection = cnx.getConnectionStatement();
        PreparedStatement statement = null;
        ObjV o = null;

        ObservableList<ObjV> prds = FXCollections.observableArrayList();
        try {
            String query = "SELECT id_pro,lib_pro,prix_unit_pro,qte_dispo,function(id_pro) FROM PRODUIT";
            statement = connection.prepareStatement( query );
            int counter = 1;
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                o = new ObjV();
                o.setId_pro( rs.getInt( 1 ) );
                o.setLib_pro( rs.getString( 2 ) );
                o.setPrix_unit_pro(rs.getDouble( 3 )) ;
                o.setQte_dispo( rs.getInt( 4 ) );
                o.setFuction( rs.getString( 5 ) );
                prds.add( o );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prds;
    }

    @FXML
    private void Retour(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Home.fxml"));
        Scene login =new Scene(root);
        Stage window= (Stage)((Node)e.getSource()).getScene().getWindow();
        window.setScene(login);
        window.show();
    }

    public String getEtat(int id_produit) throws SQLException {
        Connexion cnx = new Connexion();
        Connection connection = null;
        CallableStatement cs = null;
        String var = "";
        try {
            connection = cnx.getConnectionStatement();

            CallableStatement call = connection.prepareCall( "{? = call function(?)}" );
            call.registerOutParameter( 1, Types.CHAR );
            call.setInt( 2, id_produit );
            call.execute();

           var =  call.getString( 1 );

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return var;
    }

}
