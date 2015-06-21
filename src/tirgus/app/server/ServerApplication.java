package tirgus.app.server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import tirgus.model.Market;
import tirgus.net.ServerMarket;

import java.io.IOException;

public class ServerApplication extends Application
{

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view/server.fxml"));
        primaryStage.setTitle("Tirgus Server");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Export to CSV");
        alert.setHeaderText("Save your data");
//        alert.setContentText("Save your data");
        alert.showAndWait();
        Market.instance().stop();
        super.stop();
    }

    public static void main(String[] args) throws IOException
    {
        Market.setInstance(new ServerMarket(Integer.valueOf(args[0])));
        launch(args);
    }
}
