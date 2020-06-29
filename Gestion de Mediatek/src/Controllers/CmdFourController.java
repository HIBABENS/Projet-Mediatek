package Controllers;

import beans.*;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import dao.Connexion;
import dao.ProduitDao;
import dao.TypeDao;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class CmdFourController implements Initializable {
    @FXML
    public TextField CodePrd;
    @FXML
    public TextField qte;
    @FXML
    public TextField CodeFournisseur;
    @FXML
    public TableView<Journale> tabVJournale;
    @FXML
    public TableColumn<Journale, Date> date;
    @FXML
    public TableColumn<Journale, Integer> CodePrdJ;
    @FXML
    public TableColumn<Journale, Integer> QteStk;
    @FXML
    public TableView<Fournisseur> tabVF;
    @FXML
    public TableColumn<Fournisseur, Long> CodeFour;
    @FXML
    public TableColumn<Fournisseur, String> NomFour;
    @FXML
    public TableColumn<Fournisseur, String> TelF;
    @FXML
    public TableColumn<Fournisseur, String> AdresseF;
    @FXML
    public TableColumn<Fournisseur, String> TypeFour;
    @FXML
    public TableView<Commande_Fournisseurs > tabVCmds;
    @FXML
    public TableColumn<Commande_Fournisseurs, Long> CodeCmds;
    @FXML
    public TableColumn<Commande_Fournisseurs, Long> CodePrds;
    @FXML
    public TableColumn<Commande_Fournisseurs, Long> QuteCmds;
    @FXML
    public TableColumn<Commande_Fournisseurs, Long> CodeFrss;
    @FXML
    public Tab Journale;
    @FXML
    public Tab Fournisseurs;
    @FXML
    public Tab Commande;
    @FXML
    private TextField CodeTypePrd;
    @FXML
    private TextField restTypePrd;

  //  Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
      //  FXMLLoader loader = new FXMLLoader(  );

         //   AnchorPane anch1 = loader.load(getClass().getResource( "/fxml/Commandes_Fournisseurs.fxml" ));
            date.setCellValueFactory( new PropertyValueFactory<Journale, Date>("date_journale"  ) );
            System.out.println( date );
            CodePrdJ.setCellValueFactory( new PropertyValueFactory<Journale, Integer>("id_pro"  ) );
            QteStk.setCellValueFactory( new PropertyValueFactory<Journale, Integer>("stock"  ) );
            //load data
            tabVJournale.setItems( getPrduitsJ() );

            CodeFour.setCellValueFactory( new PropertyValueFactory<Fournisseur, Long>("id"  ) );
        System.out.println( CodeFour );
            NomFour.setCellValueFactory( new PropertyValueFactory<Fournisseur, String>("nom"  ) );
        System.out.println( NomFour );
            TypeFour.setCellValueFactory( new PropertyValueFactory<Fournisseur, String>("type_Fournissement"  ) );
        System.out.println( TypeFour );
            TelF.setCellValueFactory( new PropertyValueFactory<Fournisseur, String>("numTel"  ) );
        System.out.println( TelF );
            AdresseF.setCellValueFactory( new PropertyValueFactory<Fournisseur, String>("adresse"  ) );
        System.out.println( AdresseF );
            //load data
            tabVF.setItems( getFournisseurs() );

        CodeCmds.setCellValueFactory( new PropertyValueFactory<Commande_Fournisseurs, Long>("id_cmd"  ) );
        CodePrds.setCellValueFactory( new PropertyValueFactory<Commande_Fournisseurs, Long>("id_pro"  ) );
        QuteCmds.setCellValueFactory( new PropertyValueFactory<Commande_Fournisseurs, Long>("qte_cmd_frs"  ) );
        CodeFrss.setCellValueFactory( new PropertyValueFactory<Commande_Fournisseurs, Long>("id_Fours"  ) );
        //load data
        tabVCmds.setItems( getCommands() );

    }

    private ObservableList<Commande_Fournisseurs> getCommands() {
        Connexion cnx = new Connexion();
        Connection connection = null;
        connection = cnx.getConnectionStatement();
        PreparedStatement statement = null;
        Commande_Fournisseurs c = null;
        ObservableList<Commande_Fournisseurs> cmds = FXCollections.observableArrayList();
        try {
            String query = "SELECT * FROM Commande_Fournisseurs ";
            statement = connection.prepareStatement( query );
            int counter = 1;
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                c = new Commande_Fournisseurs();
                c.setId_cmd( rs.getLong( 1 ) );
                c.setId_pro( rs.getLong( 2 ) );
                c.setQte_cmd_frs( rs.getLong( 3 ) );
                c.setId_Fours( rs.getLong( 4 ) );
                cmds.add( c );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cmds;
    }

    @FXML
    private void Retour(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Home.fxml"));
        Scene login =new Scene(root);
        Stage window= (Stage)((Node)e.getSource()).getScene().getWindow();
        window.getIcons().add(new Image("/images/home.jpg"));
        window.setScene(login);
        window.show();
    }

 /*   @FXML
    private void Journale(ActionEvent e) throws IOException {

        date.setCellValueFactory( new PropertyValueFactory<Journale, Date>("date_journale"  ) );
        CodePrdJ.setCellValueFactory( new PropertyValueFactory<Journale, Integer>("id_pro"  ) );
        QteStk.setCellValueFactory( new PropertyValueFactory<Journale, Integer>("stock"  ) );
        //load data
        tabVJournale.setItems( getPrduitsJ() );

    }*/

    private ObservableList<Journale> getPrduitsJ() {
        Connexion cnx = new Connexion();
        Connection connection = null;
        connection = cnx.getConnectionStatement();
        PreparedStatement statement = null;
        Journale j = null;
        ObservableList<Journale> prds = FXCollections.observableArrayList();
        try {
            String query = "SELECT * FROM journale ";
            statement = connection.prepareStatement( query );
            int counter = 1;
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                j = new Journale();
                j.setDate_journale( rs.getDate( 3 ) );
                System.out.println( j.getDate_journale() );
                j.setId_pro( rs.getInt( 2 ) );
                j.setStock( rs.getInt( 4 ) );
                prds.add( j );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prds;
    }

   /* @FXML
    private void Fournisseurs(ObjectProperty<EventHandler<Event>> e) throws IOException {
        CodeFour.setCellValueFactory( new PropertyValueFactory<Fournisseur, Integer>("id"  ) );
        NomFour.setCellValueFactory( new PropertyValueFactory<Fournisseur, String>("nom"  ) );
        TypeFour.setCellValueFactory( new PropertyValueFactory<Fournisseur, String>("type_Fournissement"  ) );
        TelF.setCellValueFactory( new PropertyValueFactory<Fournisseur, String>("numTel"  ) );
        AdresseF.setCellValueFactory( new PropertyValueFactory<Fournisseur, String>("adresse"  ) );
        //load data
        tabVF.setItems( getFournisseurs() );
    }*/

    private ObservableList<Fournisseur> getFournisseurs() {

        Connexion cnx = new Connexion();
        Connection connection = null;
        connection = cnx.getConnectionStatement();
        PreparedStatement statement = null;
        Fournisseur f = null;
        ObservableList<Fournisseur> frns = FXCollections.observableArrayList();

        try {
            String query = "SELECT * FROM Fournisseur";

            statement = connection.prepareStatement( query );
            int counter = 1;
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {

                f = new Fournisseur();
                f.setId( rs.getLong( 1 ) );
                f.setNom( rs.getString( 2 ) );
                f.setNumTel( rs.getString( 3 ) );
                f.setAdresse( rs.getString( 4 ) );
                f.setType_Fournissement( rs.getString( 5 ) );
                frns.add( f );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return frns;

    }

    @FXML
    private void BTCmdFournisseur(ActionEvent e) throws IOException, SQLException {
        Connexion cnx = new Connexion();
        Connection connection = null;
        connection = cnx.getConnectionStatement();
        Statement stat = connection.createStatement();

        try {
          if(CodePrd.getText().isEmpty()||qte.getText().isEmpty()||CodeFournisseur.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Le champ code doit etre rempli!");
                alert.show();
            }else{

              stat.executeUpdate( " INSERT INTO Commande_Fournisseurs  (id_cmd, id_pro, qte_cmd_frs,id_Fours) " +
                      "VALUES ( cmd_fr_seq.nextval, '"+Long.parseLong(CodePrd.getText())+"', '"
                      +Long.parseLong(qte.getText())+"','"+Long.parseLong(CodeFournisseur.getText())+"')" );

              JOptionPane.showMessageDialog(null,"Commande effectué avec succé !");

                String query =  " DELETE FROM journale WHERE id_pro = '"+Integer.parseInt(CodePrd.getText())+"'";
                stat = connection.prepareStatement(query);
                //  ((PreparedStatement) stat).setLong( 1, Long.parseLong( CodeClt.getText()) );
                ((PreparedStatement) stat).executeQuery();
                
              JOptionPane.showMessageDialog(null,"Journalisation supprimée du journale");
                if(0 == JOptionPane.OK_OPTION){
                    Parent root = FXMLLoader.load(getClass().getResource("/fxml/Commandes_Fournisseurs.fxml"));
                    Scene inscription =new Scene(root);
                    Stage window= (Stage)((Node)e.getSource()).getScene().getWindow();
                    window.getIcons().add(new Image("/images/checkStk1.png"));
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
    private void search_produitType(ActionEvent e) throws IOException, SQLException{
        ProduitDao prd = new ProduitDao();
       Produit p;
        p = prd.getProduitById( Integer.parseInt( CodeTypePrd.getText()) );

        Integer rst1 =  p.getType_pro();

        TypeDao typeDao = new TypeDao();
        Type t;
        t = typeDao.getTypeById( rst1 );

        String rst2 = t.getLib_type();
        restTypePrd.setText( rst2 );

    }
    @FXML
    private void CmdFourPDF(ActionEvent e) throws IOException {

    }


}
