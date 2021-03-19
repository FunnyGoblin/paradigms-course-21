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

    public boolean findX(Object x, boolean delete){
        int n = size, res = n;
        boolean deleted = !delete;
        for(int i = 0; i < n; ++i){
            if(x.equals(element())){
                res = i;
                if(deleted){
                   enqueue(element());
                }
                deleted = true;
            }
            else{
                enqueue(element());
            }
            dequeue();
        }
        return res < n;
    }

    public boolean contains(Object x) {
        return findX(x, false);
    }

    public boolean removeFirstOccurrence(Object x) {
        return findX(x, true);
    }
}
