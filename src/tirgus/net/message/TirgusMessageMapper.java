package tirgus.net.message;

import tirgus.collection.BidiMap;

import java.util.Map;

public class TirgusMessageMapper
{
    private static TirgusMessageMapper _instance = new TirgusMessageMapper();

    static
    {
        Map<String, Class<? extends TirgusMessage>> map = instance().getMap();
        map.put("response", ResponseMessage.class);
        map.put("new-product", NewProductMessage.class);
        map.put("new-user", NewUserMessage.class);
    }

    public static TirgusMessageMapper instance()
    {
        return _instance;
    }

    private TirgusMessageMapper()
    {
        map = new BidiMap<>();
    }

    private BidiMap<String, Class<? extends TirgusMessage>> map;

    public BidiMap<String, Class<? extends TirgusMessage>> getMap()
    {
        return map;
    }
}
