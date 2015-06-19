package tirgus.app.control;

import javafx.application.Platform;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import tirgus.model.Market;
import tirgus.model.Product;

import java.io.IOException;

public class ProductsTable extends BorderPane
{
    @FXML
    private TextField searchField;

    @FXML
    private TableView<Product> productsTable;

    @FXML
    private TableColumn<Product, String> nameCol;

    @FXML
    private TableColumn<Product, String> providerCol;

    @FXML
    private TableColumn<Product, Double> priceCol;

    @FXML
    private TableColumn<Product, Integer> quantityCol;

    @FXML
    private TableColumn<Product, Integer> validityCol;

    public ProductsTable() throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("productsTableView.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();

        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        providerCol.setCellValueFactory(new PropertyValueFactory<>("provider"));
        validityCol.setCellValueFactory(new PropertyValueFactory<>("validityDescription"));

        FilteredList<Product> filteredList = new FilteredList<>(Market.instance().getProducts());
        searchField.textProperty().addListener((observable, oldValue, newValue) ->
        {
            Platform.runLater(() -> filteredList.setPredicate(product -> {
                for (String part : newValue.split(" "))
                {
                    if (product.getName().startsWith(part))
                        return true;
                }

                return false;
            }));
        });
        productsTable.setItems(filteredList);
    }
}
