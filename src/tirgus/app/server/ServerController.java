package tirgus.app.server;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tirgus.app.control.ProductsTable;
import tirgus.app.server.AddToStockControl;
import tirgus.model.Product;

import java.io.IOException;

public class ServerController
{
    @FXML
    private ProductsTable productsTable;

    @FXML
    private TextField searchField;

    public void onAddNewProduct(ActionEvent actionEvent) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("view/addNewProduct.fxml"));
        Stage stage = new Stage();
        stage.setTitle("New Product");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void onAddToStock(ActionEvent actionEvent) throws IOException
    {
        if(productsTable.getSelectedProduct() == null)
        {
            //TODO: Alertbox!!
            return;
        }
        Stage stage = new Stage();
        stage.setTitle("Add To Stock");
        stage.setScene(new Scene(new AddToStockControl(productsTable.getSelectedProduct())));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

    }
}
