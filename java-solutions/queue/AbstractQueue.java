package queue;

import java.util.Objects;

public abstract class AbstractQueue implements Queue, Iterable<Object> {
    protected int size = 0;

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void enqueue(Object x) {
        enqueueImpl(Objects.requireNonNull(x));
        size++;
    }

    abstract void enqueueImpl(Object x);

    public Object dequeue() {
        assert size > 0;
        Object r = dequeueImpl();
        size--;
        return r;
    }

    abstract Object dequeueImpl();

    public abstract Object element();

    public abstract void clear();

    abstract AbstractQueue getInstance();

    public boolean contains(Object x) {
        boolean res = false;
        for (Object e : this) {
            if (e.equals(x)) {
                res = true;
            }
        }
        return res;
    }

    public boolean removeFirstOccurrence(Object x) {
        boolean res = false;
        AbstractQueue q = getInstance();
        for (Object e : this) {
            if (res || !e.equals(x)) {
                enqueue(x);
            } else {
                res = true;
            }
        }
        if (!res) {
            return false;
        }
        this.clear();
        for (Object e : q) {
            this.enqueue(e);
        }
        return true;
    }
}
