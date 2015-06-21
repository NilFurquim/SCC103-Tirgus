package tirgus.app.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tirgus.model.ClientMarket;
import tirgus.model.Market;

public class ClientApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view/client.fxml"));
        primaryStage.setTitle("Tirgus Client");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) throws InstantiationException, IllegalAccessException
    {
        Market.makeMarket(ClientMarket.class);
        launch(args);
    }
}
