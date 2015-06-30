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
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import tirgus.app.control.ProductsTable;
import tirgus.model.Product;
import tirgus.model.Sale;
import tirgus.model.User;
import tirgus.net.ServerMarket;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ServerController implements Initializable {
    @FXML
    private ProductsTable productsTable;

    @FXML
    private TextField searchField;

    public void onAddNewProduct(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/server/addNewProduct.fxml"));
        Stage stage = new Stage();
        stage.setTitle("New Product");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productsTable.initData(ServerApplication.getMarket());
    }

    public void onPrintPdf(ActionEvent actionEvent) throws IOException {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF file", "*.pdf"));
        chooser.setInitialFileName("report.pdf");
        File file = chooser.showSaveDialog(null);

        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDPage.PAGE_SIZE_A4);
        document.addPage(page);


        final int headerWidth = 50;
        final int headerHeight = -100;

        final int lineSpacing = -35;
        final int columnSpacing = 150;

        ServerMarket market = ServerApplication.getMarket();

        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        contentStream.beginText();
        contentStream.moveTextPositionByAmount(0, PDPage.PAGE_SIZE_A4.getHeight());
        contentStream.moveTextPositionByAmount(headerWidth, headerHeight);
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);

        contentStream.drawString("User");

        contentStream.moveTextPositionByAmount(columnSpacing, 0);
        contentStream.drawString("Product");

        contentStream.moveTextPositionByAmount(columnSpacing, 0);
        contentStream.drawString("Quantity");

        contentStream.moveTextPositionByAmount(columnSpacing, 0);
        contentStream.drawString("Date");

        contentStream.moveTextPositionByAmount(-3 * columnSpacing, lineSpacing);

        contentStream.setFont(PDType1Font.HELVETICA, 16);
        for (Sale sale : market.getSales()) {
            final User user = market.getUsers().filtered(u -> u.getId() == sale.getUserId()).get(0);
            final Product product = market.getProducts().filtered(p -> p.getId() == sale.getProductId()).get(0);
            final int quantity = sale.getQuantity();
            final LocalDate date = sale.getDate();

            contentStream.drawString(user.getName());

            contentStream.moveTextPositionByAmount(columnSpacing, 0);
            contentStream.drawString(product.getName());

            contentStream.moveTextPositionByAmount(columnSpacing, 0);
            contentStream.drawString(Integer.toString(quantity));

            contentStream.moveTextPositionByAmount(columnSpacing, 0);
            contentStream.drawString(date.format(DateTimeFormatter.ISO_DATE));

            contentStream.moveTextPositionByAmount(-3 * columnSpacing, lineSpacing);
        }
        contentStream.close();
        try {
            document.save(file);
        } catch (Exception ignored) {

        }
        finally {
            document.close();
        }
    }
}
