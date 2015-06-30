package tirgus.app.server;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tirgus.app.control.ProductsTable;
import tirgus.app.io.PDFPrinter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Server window controller
 */
public class ServerController implements Initializable {
    @FXML
    private ProductsTable productsTable;

    @FXML
    private TextField searchField;

    /**
     * Register product
     * @param actionEvent
     * @throws IOException
     */
    public void onAddNewProduct(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/server/addNewProduct.fxml"));
        Stage stage = new Stage();
        stage.setTitle("New Product");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    /**
     * Add to stock
     * @param actionEvent
     * @throws IOException
     */
    public void onAddToStock(ActionEvent actionEvent) throws IOException {
        if (productsTable.getSelectedProduct() == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("Information");
            alert.setHeaderText("Select a product");
            alert.showAndWait();
            return;
        }

        Stage stage = new Stage();
        stage.setTitle("Add To Stock");
        stage.setScene(new Scene(new AddToStockControl(productsTable.getSelectedProduct())));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    /**
     * Print PDF
     * @param actionEvent
     * @throws IOException
     */

    public void onPrintPdf(ActionEvent actionEvent) throws IOException {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF file", "*.pdf"));
        chooser.setInitialFileName("report.pdf");
        File file = chooser.showSaveDialog(null);
        PDFPrinter printer = new PDFPrinter();
        printer.printSales(file, ServerApplication.getMarket().getSales());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productsTable.initData(ServerApplication.getMarket());
    }
}
