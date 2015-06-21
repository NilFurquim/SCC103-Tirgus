package tirgus.app.server;

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

public class AddToStockControl extends GridPane
{
    @FXML
    private TextField productNameField;

    @FXML
    private TextField currentField;

    @FXML
    private TextField newQuantityField;

    @FXML
    private Spinner<Integer> additionSpinner;

    private Product product;

    public AddToStockControl(Product selectedProduct) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/addToStock.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();

        product = selectedProduct;

        additionSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 1));
        additionSpinner.valueProperty().addListener((observable, oldValue, newValue) ->
        {
            newQuantityField.setText(String.valueOf(newValue + product.getQuantity()));
        });

        productNameField.setText(product.getName());
        currentField.setText(String.valueOf(product.getQuantity()));
        newQuantityField.setText(String.valueOf(product.getQuantity() + additionSpinner.getValue()));
    }

    public void onDone(ActionEvent actionEvent)
    {

        ServerApplication.getMarket().addToStock(product, additionSpinner.getValue());

        ((Stage) productNameField.getScene().getWindow()).close();
    }


}
