package tirgus.app.server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tirgus.model.Market;
import tirgus.model.ServerMarket;

public class ServerAppplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view/server.fxml"));
        primaryStage.setTitle("Tirgus Server");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) throws InstantiationException, IllegalAccessException
    {
        Market.makeMarket(ServerMarket.class);
        launch(args);
    }
}
