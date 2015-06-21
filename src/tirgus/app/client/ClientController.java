package tirgus.app.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tirgus.app.control.ProductsTable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable
{
    @FXML
    private Button buyButton;

    @FXML
    private Label userLabel;

    @FXML
    private ProductsTable productsTable;

    public void onAddNewUser(ActionEvent actionEvent) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("view/addNewUser.fxml"));
        Stage stage = new Stage();
        stage.setTitle("New User");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void onLogin(ActionEvent actionEvent) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("view/login.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Login");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void onBuyProduct(ActionEvent actionEvent) throws IOException
    {

        if (productsTable.getSelectedProduct() == null)
        {
            //TODO alert
            return;
        }

        Stage stage = new Stage();
        stage.setTitle("Buy product");
        stage.setScene(new Scene(new BuyProductControl(productsTable.getSelectedProduct())));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        productsTable.initData(ClientApplication.getMarket());
        ClientApplication.getMarket().currentUserProperty().addListener((observable, oldValue, newValue) -> {
            userLabel.setText(newValue != null ? newValue.getName() : "None");
        });

        productsTable.getProductsTable().getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> buyButton.setDisable(newValue == null));
    }
}
