package queue;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

public class ArrayQueue extends AbstractQueue {
    private int head;
    private Object[] elements;

    public ArrayQueue() {
        size = head = 0;
        elements = new Object[2];
    }

    public AbstractQueue getInstance() {
        return new ArrayQueue();
    }

    private void ensureCapacity() {
        int n = elements.length;
        if (n == size) {
            Object[] nw = Arrays.copyOfRange(elements, head, elements.length * 2);
            System.arraycopy(elements, 0, nw, elements.length - head, head);
            head = 0;
            elements = nw;
        }
    }

    public void enqueueImpl(Object x) {
        ensureCapacity();
        elements[getTail()] = x;
    }

    public void push(Object x) {
        Objects.requireNonNull(x);
        ensureCapacity();
        --head;
        if (head < 0) {
            head = elements.length - 1;
        }
        elements[head] = x;
        size++;
    }

    public Object element() {
        assert size > 0;
        return elements[head];
    }

    private int getTail() {
        return (head + size) % elements.length;
    }

    public Object dequeueImpl() {
        Object r = elements[head];
        elements[head++] = null;
        if (head == elements.length) {
            head = 0;
        }
        return r;
    }

    @Override
    public Iterator<Object> iterator() {
        return new Iterator<>() {
            private int pointer = 0;

            @Override
            public boolean hasNext() {
                return pointer < size;
            }

            @Override
            public Object next() {
                return elements[(head + pointer++) % elements.length];
            }
        };
    }
}
