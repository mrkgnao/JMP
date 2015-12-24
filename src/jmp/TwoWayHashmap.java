package jmp;

import java.util.HashMap;
import java.util.Map;

/**
 * An "obvious" two-way map for storing the instruction codes and their memory
 * representations.
 * I got this from <http://stackoverflow.com/a/3430209/1593359>, which you can 
 * understand from the "synchronized" in every method :P
 * @param <K> the key type
 * @param <V> the value type
 * 
 * TODO implement AbstractMap?
 */
public class TwoWayHashmap<K extends Object, V extends Object> {

    private final Map<K, V> valuesFromKeys = new HashMap<K, V>();
    private final Map<V, K> keysFromValues = new HashMap<V, K>();

    public synchronized void add(K key, V value) {
        valuesFromKeys.put(key, value);
        keysFromValues.put(value, key);
    }

    public synchronized V getValue(K key) {
        return valuesFromKeys.get(key);
    }

    public synchronized K getKey(V value) {
        return keysFromValues.get(value);
    }
}
