package tirgus.net;

import java.util.HashMap;

public class TirgusMessageMapper
{
    private static TirgusMessageMapper _instance = new TirgusMessageMapper();

    public static TirgusMessageMapper instance()
    {
        return _instance;
    }


    private TirgusMessageMapper()
    {
        map = new HashMap<>();
    }

    private HashMap<String, Class<?>> map;

    public HashMap<String, Class<?>> getMap()
    {
        return map;
    }
}
