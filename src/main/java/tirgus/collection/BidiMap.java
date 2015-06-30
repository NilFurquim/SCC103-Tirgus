package tirgus.collection;

import java.util.HashMap;

/**
 * Bidirectional map (so incomplete I'm almost offended it is here)
 * @param <K>
 * @param <V>
 */
public class BidiMap<K, V> extends HashMap<K, V>
{
    private HashMap<V, K> inverseMap;

    /**
     * Constructor
     */
    public BidiMap()
    {
        inverseMap = new HashMap<>();
    }

    /**
     * associate a pair of objects
     * @param key
     * @param value
     * @return
     */
    @Override
    public V put(K key, V value)
    {
        inverseMap.put(value, key);
        return super.put(key, value);
    }

    /**
     * Find a for a given value
     * @param value
     * @return
     */
    public K findKey(V value)
    {
        return inverseMap.get(value);
    }

    /**
     * Find a value for a given key
     * @param key
     * @return
     */
    public V findValue(K key)
    {
        return get(key);
    }
}
