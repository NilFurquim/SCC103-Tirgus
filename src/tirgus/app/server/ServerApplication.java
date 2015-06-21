package tirgus.app.server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import tirgus.model.Product;
import tirgus.model.User;
import tirgus.net.ServerMarket;
import tirgus.serialization.CSVSerializer;
import tirgus.serialization.OrderedSerializable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class ServerApplication extends Application
{
    private static ServerMarket market;

    public static ServerMarket getMarket()
    {
        return market;
    }

    private static <T extends OrderedSerializable> void closingExport(String filename, List<T> list)
    {
        try {
            FileOutputStream fw = new FileOutputStream(filename);

            for(T item : list)
            {
                CSVSerializer.write(item, fw);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static <T extends OrderedSerializable> void initialImport(String filename, Class<T> c, Consumer<List<T>> consumer)
    {
        File file = new File(filename);
        if(file.exists())
        {
            try {
                List<T> list = CSVSerializer.read(new FileInputStream(file), c);
                consumer.accept(list);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

        initialImport("Products.csv", Product.class, products -> market.getProducts().addAll(products));
        initialImport("Users.csv", User.class, users -> market.getUsers().addAll(users));
        launch(args);
    }


    @Override
    public void stop() throws Exception
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Export to CSV");
        alert.setHeaderText("Save your data");
        Optional<ButtonType> result =  alert.showAndWait();
        if(result.isPresent() && result.get().equals(ButtonType.OK))
        {
            closingExport("Products.csv", market.getProducts());
            closingExport("Users.csv", market.getUsers());
        }
        market.stop();
        super.stop();
    }
}
