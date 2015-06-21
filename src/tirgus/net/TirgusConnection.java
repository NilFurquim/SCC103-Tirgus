package tirgus.net;

import tirgus.model.Market;
import tirgus.model.Product;
import tirgus.model.User;
import tirgus.net.message.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.util.Scanner;

public class TirgusConnection implements Runnable
{
    private Thread inputThread;
    private Socket socket;
    private Scanner scanner;
    private PrintWriter writer;

    public TirgusConnection(Socket socket) throws IOException
    {
        this.socket = socket;
        this.writer = new PrintWriter(socket.getOutputStream(), true);
        this.scanner = new Scanner(socket.getInputStream());

        inputThread = new Thread(this);
        inputThread.start();
    }

    public void newProductMessage(Product product)
    {
        writer.println(new NewProductMessage(product));
    }

    public boolean newUserMessage(User user)
    {
        writer.println(new NewUserMessage(user));
//        waitForResponse();
        return false;
    }

    public ResponseMessage waitForResponse()
    {
        return null;
    }

    private TirgusMessage instantiateMessage(Class<? extends TirgusMessage> c, String body)
    {
        try
        {
            Constructor<? extends TirgusMessage> constructor = c.getConstructor(String.class);
            return constructor.newInstance(body);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void run()
    {
        while (scanner.hasNext())
        {
            final String identifier = scanner.next();
            final String body = scanner.nextLine().trim();

            Class<? extends TirgusMessage> c = TirgusMessageMapper.instance().getMap().findValue(identifier);
            loopCallback(instantiateMessage(c, body));
        }
    }

    private void loopCallback(TirgusMessage message)
    {

        if (message instanceof ResponseMessage)
        {
            //TODO response system
        } else if (message instanceof NewUserMessage)
        {
            NewUserMessage m = (NewUserMessage) message;
            Market.instance().newUser(m.getUser());
        } else if (message instanceof NewProductMessage)
        {
            NewProductMessage m = (NewProductMessage) message;
            Market.instance().newProduct(m.getProduct());
        }
    }

    public void stop()
    {
        try
        {
            inputThread.interrupt();
            socket.close();
            writer.close();
            scanner.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}



