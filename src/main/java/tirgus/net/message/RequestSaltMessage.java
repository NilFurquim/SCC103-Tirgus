package tirgus.net.message;

public class RequestSaltMessage extends TirgusMessage
{
    private String username;

    public RequestSaltMessage(String body)
    {
        super(body);
        this.username = body;
    }

    public String getUsername()
    {
        return username;
    }
}
