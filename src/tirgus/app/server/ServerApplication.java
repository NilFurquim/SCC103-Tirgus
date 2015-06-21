package tirgus.app.server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import tirgus.net.ServerMarket;

import java.io.IOException;

public class ServerApplication extends Application
{
    private static ServerMarket market;

    public static ServerMarket getMarket()
    {
        return market;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view/server.fxml"));
        primaryStage.setTitle("Tirgus Server");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) throws IOException
    {
        final int port = Integer.valueOf(args[0]);

        //set market type
        market = new ServerMarket(port);
        launch(args);
    }

    @Override
    public void stop() throws Exception
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Export to CSV");
        alert.setHeaderText("Save your data");
        alert.showAndWait();
        market.stop();
        super.stop();
    }
}
