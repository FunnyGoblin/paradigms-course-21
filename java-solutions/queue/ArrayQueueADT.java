package queue;

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

import java.util.Arrays;
import java.util.Objects;

public class ArrayQueueADT {
    private int size = 0, head = 0;
    private Object[] elements = new Object[2];

    public static ArrayQueueADT create() {
        return new ArrayQueueADT();
    }


    private static int getTail(ArrayQueueADT deq){
        Objects.requireNonNull(deq);
        return (deq.head + deq.size) % deq.elements.length;
    }

    private static void ensureCapacity(ArrayQueueADT deq) {
        Objects.requireNonNull(deq);
        int n = deq.elements.length;
        if (n == deq.size) {
            Object[] nw = Arrays.copyOfRange(deq.elements, deq.head, deq.elements.length * 2);
            System.arraycopy(deq.elements, 0, nw, deq.elements.length - deq.head, deq.head);
            deq.head = 0;
            deq.elements = nw;
        }
    }

    //Pred: x != null && deq != null
    //Post:  n = n' + 1 && a[n] == x && forall i = 1..n': a[i] == a[i]'
    public static void enqueue(ArrayQueueADT deq, Object x) {
        Objects.requireNonNull(deq);
        Objects.requireNonNull(x);
        ensureCapacity(deq);
        deq.elements[getTail(deq)] = x;
        deq.size++;
    }

    //Pred: x != null && deq != null
    //Post: n == n' + 1 && a[1] == x && forall i = 2..n a[i] == a[i - 1]'
    public static void push(ArrayQueueADT deq, Object x) {
        Objects.requireNonNull(deq);
        Objects.requireNonNull(x);
        ensureCapacity(deq);
        --deq.head;
        if (deq.head < 0) {
            deq.head = deq.elements.length - 1;
        }
        deq.elements[deq.head] = x;
        deq.size++;
    }

    //Pred: n > 0 && deq != null
    //Post: R == a[1] && Immutable
    public static Object element(ArrayQueueADT deq) {
        Objects.requireNonNull(deq);
        assert deq.size > 0;
        return deq.elements[deq.head];
    }

    //Pred: n > 0 && deq != null
    //Post: R == a[n] && Immutable
    public static Object peek(ArrayQueueADT deq) {
        Objects.requireNonNull(deq);
        assert deq.size > 0;
        return deq.elements[(getTail(deq) - 1 + deq.elements.length) % deq.elements.length];
    }

    //Pred: n > 0 && deq != null
    //Post:  n == n' - 1 && forall i = 1..n: a[i] == a[i+1]' && R == a[1]'
    public static Object dequeue(ArrayQueueADT deq) {
        Objects.requireNonNull(deq);
        assert deq.size > 0;
        Object r = deq.elements[deq.head];
        deq.elements[deq.head++] = null;
        if (deq.head == deq.elements.length) {
            deq.head = 0;
        }
        deq.size--;
        return r;
    }

    //Pred: n > 0 && deq != null
    //Post: n == n' - 1 && forall i = 1..n: a[i] == a[i]' && R == a[n']'
    public static Object remove(ArrayQueueADT deq) {
        Objects.requireNonNull(deq);
        assert deq.size > 0;
        deq.size--;
        Object r = deq.elements[getTail(deq)];
        deq.elements[getTail(deq)] = null;
        return r;
    }

    //Pred: deq != null
    //Post: R == n && Immutable
    public static int size(ArrayQueueADT deq) {
        Objects.requireNonNull(deq);
        return deq.size;
    }

    //Pred: deq != null
    //Post: R == [n > 0] && Immutable
    public static boolean isEmpty(ArrayQueueADT deq) {
        Objects.requireNonNull(deq);
        return deq.size == 0;
    }

    //Pred: deq != null
    //Post: n == 0
    public static void clear(ArrayQueueADT deq) {
        Arrays.fill(deq.elements, null);
        deq.size = deq.head = 0;
    }
}
