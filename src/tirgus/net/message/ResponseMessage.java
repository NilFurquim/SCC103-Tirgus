package tirgus.net.message;

public class ResponseMessage extends TirgusMessage
{
    public final Response response;

    public ResponseMessage(boolean successful)
    {
        this(successful ? Response.SUCCESS.text : Response.FAILURE.text);
    }

    public ResponseMessage(String body)
    {
        super(body);
        response = body.contains(Response.SUCCESS.text)
                ? Response.SUCCESS
                : Response.FAILURE;
    }

    public boolean successful()
    {
        return response == Response.SUCCESS;
    }

    public enum Response
    {
        SUCCESS("OK"),
        FAILURE("NAY");

        final String text;

        Response(String text)
        {
            this.text = text;
        }
    }
}
