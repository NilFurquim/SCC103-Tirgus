package tirgus.collection;

import java.util.HashMap;

public class BidiMap<K, V> extends HashMap<K, V>
{
    private HashMap<V, K> inverseMap;

    public BidiMap()
    {
        inverseMap = new HashMap<>();
    }

    @Override
    public V put(K key, V value)
    {
        inverseMap.put(value, key);
        return super.put(key, value);
    }

    public K findKey(V value)
    {
        return inverseMap.get(value);
    }

    public V findValue(K key)
    {
        return get(key);
    }
}
