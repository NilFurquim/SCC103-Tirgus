package tirgus.app.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
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
        ClientApplication.getMarket().buyProduct(product.getId(), qtdSpinner.getValue());
        ((Stage) productNameField.getScene().getWindow()).close();
    }


}
