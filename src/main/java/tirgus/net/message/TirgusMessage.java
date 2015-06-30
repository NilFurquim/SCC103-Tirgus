package tirgus.net.message;

public abstract class TirgusMessage
{
    protected String body;

    protected String identifier;

    public TirgusMessage(String body)
    {
        identifier = TirgusMessageMapper.instance().getMap().findKey(getClass());
        this.body = body;
    }

    public String getBody()
    {
        return body;
    }

    public void setBody(String body)
    {
        this.body = body;
    }

    public String toString()
    {
        return identifier + " " + body;
    }

    public String getIdentifier()
    {
        return identifier;
    }
}
