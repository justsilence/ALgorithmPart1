/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */



import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int size;
    private Node begin;
    private Node end;

    private class Node {
        Item item;
        Node prev;
        Node next;
    }

    public Deque() {
        begin = null;
        end = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node node = new Node();
        node.item = item;
        node.prev = null;
        node.next = null;
        if (begin == null) {
            begin = node;
        } else {
            begin.prev = node;
            node.next = begin;
            begin = node;
        }
        if (end == null) {
            end = begin;
        }
        size++;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node node = new Node();
        node.item = item;
        node.prev = null;
        node.next = null;
        if (end == null) {
            end = node;
        } else {
            end.next = node;
            node.prev = end;
            end = node;
        }
        if (begin == null) {
            begin = end;
        }
        size++;
    }

    public Item removeFirst() {
        if (begin == null) {
            throw new NoSuchElementException();
        }
        Item item = begin.item;
        begin = begin.next;
        if (begin != null) {
            begin.prev = null;
        } else {
            end = null;
        }
        size--;
        return item;
    }

    public Item removeLast() {
        if (end == null) {
            throw new NoSuchElementException();
        }
        Item item = end.item;
        end = end.prev;
        if (end != null) {
            end.next = null;
        } else {
            begin = null;
        }
        size--;
        return item;
    }



    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }


    private class DequeIterator implements Iterator<Item> {

        private Node iter = begin;

        @Override
        public boolean hasNext() {
            return iter != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = iter.item;
            iter = iter.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }


    public static void main(String[] args) {

        Deque<Integer> deque = new Deque<>();
        deque.addFirst(1);
        System.out.println(deque.removeLast());
        deque.addLast(3);
        deque.addFirst(4);
        Iterator<Integer> iterator = deque.iterator();
        while (iterator.hasNext()) {
            System.out.print(iterator.next());
        }
        System.out.println(deque.size());
        System.out.println(deque.removeFirst());
        System.out.println(deque.removeLast());
        deque.addLast(8);
        System.out.println(deque.removeFirst());


    }
}
