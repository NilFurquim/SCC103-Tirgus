package tirgus.app.server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tirgus.model.Product;
import tirgus.model.Sale;
import tirgus.model.User;
import tirgus.net.ServerMarket;
import tirgus.serialization.CSVSerializer;
import tirgus.serialization.OrderedSerializable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.function.Consumer;

/**
 * Server application
 */
public class ServerApplication extends Application
{
    private static ServerMarket market;

    public static ServerMarket getMarket()
    {
        return market;
    }

    /**
     * Export csv data
     * @param filename
     * @param list
     * @param <T>
     */
    public static <T extends OrderedSerializable> void closingExport(String filename, List<T> list)
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

    /**
     * import csv data
     * @param filename
     * @param c
     * @param consumer
     * @param <T>
     */
    public static <T extends OrderedSerializable> void initialImport(String filename, Class<T> c, Consumer<List<T>> consumer)
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
        Parent root = FXMLLoader.load(getClass().getResource("/server/server.fxml"));
        primaryStage.setTitle("Tirgus Server");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> market.onCloseRequest());
    }

    public static void main(String[] args) throws IOException
    {
        final int port = Integer.valueOf(args[0]);

        //set market type
        market = new ServerMarket(port);

        initialImport("Products.csv", Product.class, products -> market.getProducts().addAll(products));
        initialImport("Users.csv", User.class, users -> market.getUsers().addAll(users));
        initialImport("Sales.csv", Sale.class, sales -> market.getSales().addAll(sales));
        launch(args);
    }


    @Override
    public void stop() throws Exception
    {
        market.stop();
        super.stop();
    }
}
