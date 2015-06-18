package tirgus.app.server;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tirgus.model.Product;

import java.net.URL;
import java.time.Period;
import java.util.Arrays;
import java.util.ResourceBundle;

public class AddNewProduct implements Initializable
{
    @FXML
    private TextField nameField;

    @FXML
    private TextField providerField;

    @FXML
    private Spinner<Double> priceSpinner;

    @FXML
    public Spinner<Integer> validityAmountSpinner;

    @FXML
    public Spinner<String> validityUnitSpinner;

    @FXML
    private Spinner<Integer> quantitySpinner;


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        priceSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.01, Double.MAX_VALUE, 19.99));
        validityAmountSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 1));
        validityUnitSpinner.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(
                        FXCollections.observableList(Arrays.asList("Day", "Week", "Month", "Year")))
        );
    }

    public void onRegister(ActionEvent actionEvent)
    {
        final String name = nameField.getText();
        final String provider = providerField.getText();
        final double price = priceSpinner.getValue();
        final int validityAmout = validityAmountSpinner.getValue();
        final Period validity;
        switch(validityUnitSpinner.getValue())
        {
            case "Day":
                validity = Period.of(0, 0, validityAmout);
                break;
            case "Week":
                validity = Period.of(0, 0, 7*validityAmout);
                break;
            case "Month":
                validity = Period.of(0, validityAmout, 0);
                break;
            case "Year":
                validity = Period.of(validityAmout, 0, 0);
                break;
            default:
                validity = Period.ofDays(0);
        }
        new Product(name, price, validity, provider);
        ((Stage) nameField.getScene().getWindow()).close();
    }
}
