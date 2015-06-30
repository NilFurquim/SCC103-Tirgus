package tirgus.app.server;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import tirgus.model.Product;
import tirgus.model.Sale;
import tirgus.model.User;
import tirgus.net.ServerMarket;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


/**
 * Domain specific PDF printer
 */
public class PDFPrinter {

    private final int headerWidth = 20;
    private final int headerHeight = -50;

    private final int lineSpacing = -35;
    private final int columnSpacing = 150;

    /**
     * Print a list of sales to a single page
     * @param file
     * @param sales
     * @throws IOException
     */
    public void printSales(File file, List<Sale> sales) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDPage.PAGE_SIZE_A4);
        document.addPage(page);

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
        for (Sale sale : sales) {
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
