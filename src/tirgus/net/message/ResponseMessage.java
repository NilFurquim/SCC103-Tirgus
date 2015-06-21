package tirgus.net.message;

public class ResponseMessage extends TirgusMessage
{

    public final Response response;

    public ResponseMessage(String body)
    {
        super(body);
        response = body.contains(Response.SUCCESS.text)
                ? Response.SUCCESS
                : Response.FAILURE;
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
