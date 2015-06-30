package tirgus.net;

import tirgus.net.message.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.util.Scanner;

/**
 * Client-Server connection
 */
public class TirgusConnection implements Runnable
{
    private Thread inputThread;
    private Socket socket;
    private Scanner scanner;
    private PrintWriter writer;
    private TirgusMessageCallback messageCallback;

    //TODO some queue and response ID for a multiple responses case
    private ResponseMessage lastResponse;

    /**
     * Constructor
     * @param socket
     * @param messageCallback
     * @throws IOException
     */
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

    /**
     * Send a message that doesnt expect answers
     * @param message
     */
    public void sendMessage(TirgusMessage message)
    {
        writer.println(message);
    }

    /**
     * Send a message that expects answers
     * @param message
     * @return
     */
    public ResponseMessage sendMessageAndWait(TirgusMessage message)
    {
        sendMessage(message);
        return waitForResponse();
    }

    /**
     * Send a message that expects a boolean answer
     * @param message
     * @return
     */
    public boolean sendMessageAndWaitBoolean(TirgusMessage message)
    {
        BooleanResponseMessage response = (BooleanResponseMessage) sendMessageAndWait(message);
        return response != null && response.successful();
    }

    /**
     * Wait for answer
     * @return
     */
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

    /**
     * Instantiate message from string
     * @param c
     * @param body
     * @return
     */
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