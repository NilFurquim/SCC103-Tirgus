package tirgus.net;

import java.lang.invoke.MethodHandles;

public abstract class TirgusMessage
{
    protected String body;

    protected static String identifier;

    static
    {
        identifier = null;
    }

    public TirgusMessage(String body)
    {
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

    protected static void setIdentifier(String identifier)
    {
        TirgusMessage.identifier = identifier;
        TirgusMessageMapper.instance().getMap().put(identifier,
                MethodHandles.lookup().lookupClass());
    }
}
