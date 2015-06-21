package tirgus.app.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tirgus.model.Product;

import java.io.IOException;

public class BuyProductControl extends GridPane
{
    @FXML
    public TextField productNameField;
    @FXML
    public TextField currentField;
    @FXML
    public TextField priceField;
    @FXML
    public Spinner<Integer> qtdSpinner;

    private Product product;

    public BuyProductControl(Product product) throws IOException
    {
        this.product = product;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/buyProduct.fxml"));
        loader.setController(this);
        loader.setRoot(this);
        loader.load();

        productNameField.setText(product.getName());
        currentField.setText(String.valueOf(product.getQuantity()));
        priceField.setText(String.valueOf(product.getPrice()));

        qtdSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, 1));
    }

    public void onBuy(ActionEvent event)
    {
        product.setQuantity(product.getQuantity() + qtdSpinner.getValue());

        if(ClientApplication.getMarket().buyProduct(product.getId(), qtdSpinner.getValue()))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("Purchase successful");
            alert.setHeaderText(qtdSpinner.getValue() + " item(s) of " + product.getName() + " bought successfully");
            alert.showAndWait();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("Purchase error");
            alert.setHeaderText("Error! Not capable of realizing purchase");
            alert.showAndWait();
        }
        ((Stage) productNameField.getScene().getWindow()).close();
    }


}
