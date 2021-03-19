package queue;

import java.util.Iterator;
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
        size--;
        return dequeueImpl();
    }

    abstract Object dequeueImpl();

    public abstract Object element();

    public void clear(){
        while (!isEmpty()){
            this.dequeue();
        }
    }

    abstract AbstractQueue getInstance();

    public int firstX(Object x){
        int i = 0;
        for (Object e: this) {
            if(e.equals(x)){
                return i;
            }
            i++;
        }
        return i;
    }

    public boolean contains(Object x) {
        return firstX(x) < size;
    }

    public boolean removeFirstOccurrence(Object x) {
        int pos = firstX(x);
        if (pos == size) {
            return false;
        }
        int n = size;
        for(int i = 0; i < n; i++){
            if(i != pos){
                enqueue(element());
            }
            dequeue();
        }
        return true;
    }
}
