package tirgus.app.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tirgus.net.ClientMarket;

import java.io.IOException;

public class ClientApplication extends Application {

    private static ClientMarket market;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/client/client.fxml"));
        primaryStage.setTitle("Tirgus Client");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static ClientMarket getMarket()
    {
        return market;
    }

    public static void main(String[] args) throws IOException
    {
        final String host = args[0];
        final int port = Integer.valueOf(args[1]);

        //set market type
        market = new ClientMarket(host, port);
        launch(args);
    }
}
