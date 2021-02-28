package queue;

import java.util.Arrays;
import java.util.Objects;
/*
Model:
    [a1, a2, a3, ...an]
    n -- size of deque
    elements[head] == a[1] && elements[head + 1] == a[2] && ... && elements[tail - 1] == a[n]

Inv:
    n >= 0
    forall i = 1..n: a[i] != null

Immutable:
    n == n' && forall i = 1..n: a[i] = a'[i]
*/
public class ArrayQueueModule {
    // :NOTE: do you really need 3 fields? I don't think so. Get rid of 1 of them
    private static int size = 0, head = 0, tail = 0;  
    private static Object[] elements = new Object[2];

    private static void ensureCapacity(){
        int n = elements.length;
        if (n == size){
            elements = Arrays.copyOf(elements, 2 * n);
            // :NOTE: copy array by elements in cycle is slow. Improve it
            for(int i = 0; i < head; i++){
                elements[i + n] = elements[i];
                elements[i] = null;
            }
            tail = head + n;
        }
    }

    //Pred: x != null
    //Post : n = n' + 1 && a[n] == x && forall i = 1..n': a[i] == a[i]'
    public static void enqueue(Object x){
        Objects.requireNonNull(x);
        ensureCapacity();
        elements[tail++] = x;
        if(tail == elements.length){
            tail = 0;
        }
        size++;
    }

    //Pred: x != null
    //Post: n == n' + 1 && a[1] == x && forall i = 2..n a[i] == a[i - 1]'
    public static void push(Object x){
        Objects.requireNonNull(x);
        ensureCapacity();
        --head;
        if(head < 0){
            head = elements.length - 1;
        }
        elements[head] = x;
        size++;
    }

    //Pred: n > 0
    //Post :R == a[1] && Immutable
    public static Object element(){
        assert size > 0;
        return elements[head];
    }

    //Pred: n > 0
    //Post: R == a[n] && Immutable
    public static Object peek(){
        assert size > 0;
        return elements[(tail - 1 + elements.length) % elements.length];
    }

    //Pred: n > 0
    //Post : n == n' - 1 && forall i = 1..n: a[i] == a[i+1]' && R == a[1]'
    public static Object dequeue(){
        assert size > 0;
        Object r = elements[head];
        elements[head++] = null;
        if(head == elements.length){
            head = 0;
        }
        size--;
        return r;
    }

    //Pred: n > 0;
    //Post: n == n' - 1 && forall i = 1..n: a[i] == a[i]' && R == a[n']'
    public static Object remove(){
        assert size > 0;
        tail--;
        if(tail < 0){
            tail = elements.length - 1;
        }
        Object r = elements[tail];
        elements[tail] = null;
        size--;
        return r;
    }

    //Pred: true
    //Post :R == n && Immutable
    public static int size(){
        return size;
    }

    //Pred: true
    //Post :R == [n > 0] && Immutable
    public static boolean isEmpty(){
        return size == 0;
    }

    // :NOTE: post conditions is empty? Hard disc is formated
    //Pred: true
    //Post:
    public static void clear(){
        while (!isEmpty()){
            dequeue();
        }
    }
}
