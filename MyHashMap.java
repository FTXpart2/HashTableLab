public class MyHashMap<K, V> {
    private Object[] hashArray;
    private int size;
    private MyHashSet<K> keySet;

    @SuppressWarnings("unchecked")
    public MyHashMap() {
        this.hashArray = new Object[16]; // Initial capacity
        this.size = 0;
        this.keySet = new MyHashSet<>();
    }

    @SuppressWarnings("unchecked")
    public V put(K key, V value) {
        int index = getIndex(key);
        V previousValue = null;

        // Check for existing key
        if (hashArray[index] != null) {
            previousValue = ((Entry<K, V>) hashArray[index]).value;
        }

        // Add or update the entry
        hashArray[index] = new Entry<>(key, value);
        keySet.add(key);
        if (previousValue == null) {
            size++;
        }
        
        return previousValue;
    }

    @SuppressWarnings("unchecked")
    public V get(Object key) {
        int index = getIndex(key);
        if (hashArray[index] != null) {
            return ((Entry<K, V>) hashArray[index]).value;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public V remove(Object key) {
        int index = getIndex(key);
        if (hashArray[index] != null) {
            V value = ((Entry<K, V>) hashArray[index]).value;
            hashArray[index] = null;
            keySet.remove(key);
            size--;
            return value;
        }
        return null;
    }

    public int size() {
        return size;
    }

    public MyHashSet<K> keySet() {
        return keySet;
    }

    private int getIndex(Object key) {
        return key.hashCode() % hashArray.length;
    }
@SuppressWarnings("unchecked")
    private static class Entry<K, V> {
        K key;
        V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}

