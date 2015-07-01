package tirgus.net.message;

import tirgus.collection.BidiMap;

import java.util.Map;

public class TirgusMessageMapper
{
    private static TirgusMessageMapper _instance = new TirgusMessageMapper();

    public static TirgusMessageMapper instance()
    {
        return _instance;
    }

    static
    {
        Map<String, Class<? extends TirgusMessage>> map = instance().getMap();
        map.put("response", ResponseMessage.class);
        map.put("bool-response", BooleanResponseMessage.class);
        map.put("new-product", NewProductMessage.class);
        map.put("new-user", NewUserMessage.class);
        map.put("request-salt", RequestSaltMessage.class);
        map.put("login", LoginMessage.class);
        map.put("buy", BuyMessage.class);
        map.put("quantity", QuantityMessage.class);
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
