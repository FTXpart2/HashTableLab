import java.util.AbstractSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyHashSet<E> extends AbstractSet<E> {
    private Object[] elements;
    private int size;
    private static final int DEFAULT_CAPACITY = 16;

    public MyHashSet() {
        elements = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    @Override
    public boolean add(E e) {
        if (size >= elements.length) {
            resize();
        }
        if (contains(e)) {
            return false; // Element already exists
        }
        elements[size++] = e;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < size; i++) {
            if (elements[i].equals(o)) {
                elements[i] = elements[size - 1]; // Replace with last element
                elements[size - 1] = null; // Avoid loitering
                size--;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < size; i++) {
            if (elements[i].equals(o)) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                while (index < size && elements[index] == null) {
                    index++; // Skip null elements
                }
                return index < size;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return (E) elements[index++];
            }
        };
    }

    @Override
    public int size() {
        return size;
    }

    private void resize() {
        Object[] newElements = new Object[elements.length * 2];
        System.arraycopy(elements, 0, newElements, 0, size);
        elements = newElements;
    }
}
