package tirgus.net;

import tirgus.model.Product;
import tirgus.serialization.CSVSerializer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class TirgusConnection implements Runnable
{
    private Socket socket;
    private Scanner scanner;
    private PrintWriter writer;

    public TirgusConnection(Socket socket) throws IOException
    {
        this.socket = socket;
        this.writer = new PrintWriter(socket.getOutputStream());
        this.scanner = new Scanner(socket.getInputStream());
        new Thread(this).start();
    }

    public void newProductMessage(Product product)
    {
        writer.print(new NewProductMessage(product));
        writer.flush();
    }

    @Override
    public void run()
    {
        while (scanner.hasNext())
        {
            String line = scanner.nextLine();
            System.out.println(line);
        }
    }
}

class NewProductMessage extends TirgusMessage
{
    static
    {
        setIdentifier("new-product");
    }

    public NewProductMessage(String body)
    {
        super(body);
    }

    public NewProductMessage(Product product)
    {
        super(CSVSerializer.write(product));
    }
}


