package sample;

import Controllers.InventairePrdController;
import dao.Connexion;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.*;

public class Main extends Application {
private double x, y;
    @Override
    public void start(Stage primaryStage) throws Exception{
      //  Parent root = FXMLLoader.load(getClass().getResource("/fxml/Inventaire des produits.fxml"));
       Parent root = FXMLLoader.load(getClass().getResource("/fxml/Home.fxml"));
      //  Parent root = FXMLLoader.load(getClass().getResource("/fxml/Commandes_Fournisseurs.fxml"));
        primaryStage.setTitle("Gestion de Mediatek");
        primaryStage.getIcons().add(new Image("/images/home.jpg"));
        primaryStage.setScene(new Scene(root));
      /*  //set stage borderless
        primaryStage.initStyle( StageStyle.UNDECORATED );
        //drag it here
        root.setOnMousePressed( event ->{
            x = event.getSceneX();
            y = event.getSceneY();
        } );
        root.setOnMousePressed( event ->{
            primaryStage.setX(event.getSceneX()-x);
            primaryStage.setY(event.getSceneY()-y);
        } );*/
        primaryStage.show();
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);

    /*    Parent root = FXMLLoader.load(getClass().getResource("/fxml/Facture.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
        primaryStage.setMinHeight(primaryStage.getMinHeight());
        primaryStage.setMinWidth(primaryStage.getMinWidth());*/

        //strat
   /*     double y = primaryStage.getHeight();
        double x = primaryStage.getWidth();
        System.out.println("x:"+x+"y"+y);
        primaryStage.widthProperty().addListener((obs , oldval ,newval) -> {
            double y_ = primaryStage.getHeight();
            double x_ = primaryStage.getWidth();
            if(x_ <= x && y_ <= y){
                primaryStage.setHeight(y);
                primaryStage.setWidth(x);
                System.out.println("x:"+x_+"y"+y_);
            }else{
                System.out.println("boom");
            }
        });
        primaryStage.heightProperty().addListener((obs , oldval ,newval) -> {
            double y_ = primaryStage.getHeight();
            double x_ = primaryStage.getWidth();
            if(x_ <= x && y_ <= y){
                primaryStage.setHeight(y);
                primaryStage.setWidth(x);
                System.out.println("x:"+x_+"y"+y_);
            }else{
                System.out.println("boom");
            }
        });*/
        //end

       /* Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();*/
      /*  InventairePrdController ip = new InventairePrdController();
        String var = ip.getEtat( 2 );
        System.out.println( var );*/
    }


    public static void main(String[] args) throws SQLException {
        launch( args );
        Connexion cnx = new Connexion();
        Connection conn = null;
        PreparedStatement pst=null;
        ResultSet rs=null;
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","hiba");
            String sql="select *from Client_m ";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                System.out.println(rs.getInt(1)+" "+rs.getString(2));
                conn.close();
            }
        }catch(Exception e){
            System.out.println(e);
        }

    }
}
