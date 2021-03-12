package queue;

import java.util.Iterator;

public class LinkedQueue extends AbstractQueue {
    private Node head, tail;

    private static class Node {
        private final Object value;
        private Node next;

        private Node(Object value, Node next) {
            this.value = value;
            this.next = next;
        }

        void setNext(Node next) {
            this.next = next;
        }
    }

    public AbstractQueue getInstance() {
        return new LinkedQueue();
    }

    public void enqueueImpl(Object x) {
        Node newTail = new Node(x, null);
        if (tail != null) {
            tail.setNext(newTail);
        }
        tail = newTail;
        if (head == null) {
            head = tail;
        }
    }

    public Object element() {
        assert size > 0;
        return head.value;
    }

    public Object dequeueImpl() {
        Object r = head.value;
        head = head.next;
        return r;
    }

    public void clear() {
        size = 0;
        head = tail = null;
    }

    @Override
    public Iterator<Object> iterator() {
        return new Iterator<>() {
            private Node pointer = head;

            @Override
            public boolean hasNext() {
                return pointer != null;
            }

            @Override
            public Object next() {
                Object r = pointer.value;
                pointer = pointer.next;
                return r;
            }
        };
    }
}
