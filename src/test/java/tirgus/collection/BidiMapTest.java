package tirgus.collection;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test for bidirectional map
 */
public class BidiMapTest {

    @Test
    public void testPut() {
        BidiMap<String, Integer> map = new BidiMap<>();
        map.put("One", 1);
        map.put("Two", 2);
        map.put("Three", 3);
        assertEquals((Integer) 1, map.get("One"));
        assertEquals((Integer)2, map.get("Two"));
        assertEquals(null, map.get("invalid"));
    }

    @Test
    public void testFindValue()
    {
        BidiMap<String, Integer> map = new BidiMap<>();
        map.put("One", 1);
        map.put("Two", 2);
        map.put("Three", 3);
        assertEquals((Integer)1, map.findValue("One"));
        assertEquals((Integer)3, map.findValue("Three"));
        assertEquals(null, map.findValue("invalid"));
    }

    @Test
    public void testFindKey()
    {
        BidiMap<String, Integer> map = new BidiMap<>();
        map.put("One", 1);
        map.put("Two", 2);
        map.put("Three", 3);
        assertEquals("One", map.findKey(1));
        assertEquals("Three", map.findKey(3));
        assertEquals(null, map.findKey(-1));
    }
}