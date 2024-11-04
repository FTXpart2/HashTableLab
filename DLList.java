import java.util.Iterator;
import java.util.NoSuchElementException;

public class DLList<E> implements Iterable<E> {
    private Node<E> head;
    private Node<E> tail;
    private int size;

    public DLList() {
        head = new Node<>(null);
        tail = new Node<>(null);
        head.setPrev(null);
        head.setNext(tail);
        tail.setPrev(head);
        tail.setNext(null);
        size = 0;
    }

    public boolean add(E item) {
        Node<E> newNode = new Node<>(item);
        Node<E> before = tail.prev();
        Node<E> after = tail;
        before.setNext(newNode);
        newNode.setPrev(before);
        newNode.setNext(after);
        after.setPrev(newNode);
        size++;
        return true;
    }

    public void add(int index, E item) {
        Node<E> newNode = new Node<>(item);
        Node<E> after = getNode(index);
        Node<E> before = getNode(index - 1);
        before.setNext(newNode);
        newNode.setPrev(before);
        newNode.setNext(after);
        after.setPrev(newNode);
        size++;
    }

    public E get(int x) {
        return getNode(x).get();
    }

    public E remove(int x) {
        E removed = getNode(x).get();
        // Adjust pointers based on the position of the node to be removed
        if (size == 1) {
            head.setNext(tail);
            tail.setPrev(head);
        } else if (x == 0) {
            Node<E> after = getNode(x + 1);
            head.setNext(after);
            after.setPrev(head);
        } else if (x == (size - 1)) {
            Node<E> before = getNode(x - 1);
            tail.setPrev(before);
            before.setNext(tail);
        } else {
            Node<E> before = getNode(x - 1);
            Node<E> after = getNode(x + 1);
            before.setNext(after);
            after.setPrev(before);
        }
        size--;
        return removed;
    }

    public boolean remove(Object o) {
        for (int i = 0; i < size; i++) {
            if (getNode(i).get().equals(o)) {
                remove(i);
                return true;
            }
        }
        return false;
    }

    public E set(int x, E y) {
        E replaced = getNode(x).get();
        getNode(x).set(y);
        return replaced;
    }

    public int size() {
        return size;
    }

    public String toString() {
        if (size == 0) {
            return "[]";
        }
        StringBuilder result = new StringBuilder("[" + head.next().get());
        for (int i = 1; i < size; i++) {
            result.append(", ").append(getNode(i).get());
        }
        result.append("]");
        return result.toString();
    }

    public void clear() {
        head = new Node<>(null);
        tail = new Node<>(null);
        head.setPrev(null);
        head.setNext(tail);
        tail.setPrev(head);
        tail.setNext(null);
        size = 0;
    }

    private Node<E> getNode(int index) {
        Node<E> current = head.next();
        for (int i = 0; i < index; i++) {
            current = current.next();
        }
        return current;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Node<E> current = head.next();

            @Override
            public boolean hasNext() {
                return current != tail;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("No more elements in the list.");
                }
                E data = current.get();
                current = current.next();
                return data;
            }
        };
    }

    // Node class for the doubly linked list
    private static class Node<E> {
        private E data;
        private Node<E> next;
        private Node<E> prev;

        public Node(E data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }

        public E get() {
            return data;
        }

        public void set(E data) {
            this.data = data;
        }

        public Node<E> next() {
            return next;
        }

        public void setNext(Node<E> next) {
            this.next = next;
        }

        public Node<E> prev() {
            return prev;
        }

        public void setPrev(Node<E> prev) {
            this.prev = prev;
        }
    }

    // Updated toArray method without arguments
    public MyImage[] toArray() {
        MyImage[] array = new MyImage[size];
        int index = 0;
        for (E item : this) {
            array[index++] = (MyImage) item; // Cast to MyImage
        }
        return array;
    }
}
