package tirgus.net;

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
    private TirgusMessageCallback messageCallback;

    //TODO some queue and response ID for a multiple responses case
    private ResponseMessage lastResponse;

    public TirgusConnection(Socket socket, TirgusMessageCallback messageCallback) throws IOException
    {
        this.socket = socket;
        this.writer = new PrintWriter(socket.getOutputStream(), true);
        this.scanner = new Scanner(socket.getInputStream());
        this.lastResponse = null;
        this.messageCallback = messageCallback;

        inputThread = new Thread(this);
        inputThread.start();
    }

    public void sendMessage(TirgusMessage message)
    {
        System.err.println("sent: " + message);
        writer.println(message);
    }

    public ResponseMessage sendMessageAndWait(TirgusMessage message)
    {
        sendMessage(message);
        return waitForResponse();
    }

    public boolean sendMessageAndWaitBoolean(TirgusMessage message)
    {
        BooleanResponseMessage response = (BooleanResponseMessage) sendMessageAndWait(message);
        return response != null && response.successful();
    }

    protected ResponseMessage waitForResponse()
    {
        final int timeout = 1000;
        final int step = 50;
        int timePast = 0;
        while (lastResponse == null && timePast < timeout)
        {
            try
            {
                Thread.sleep(step);
                timePast += step;
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        ResponseMessage response = lastResponse;
        lastResponse = null;

        return response;
    }

    @Override
    public void run()
    {
        while (scanner.hasNext())
        {
            final String identifier = scanner.next();
            final String body = scanner.nextLine().trim();

            Class<? extends TirgusMessage> c = TirgusMessageMapper.instance().getMap().findValue(identifier);
            TirgusMessage message = instantiateMessage(c, body);
            System.err.println("received: " + message);
            if (message instanceof ResponseMessage)
            {
                lastResponse = (ResponseMessage) message;
            } else
            {
                if (!messageCallback.callback(this, message))
                {
                    System.err.println("unhandled message: " + message);
                }
            }
        }
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