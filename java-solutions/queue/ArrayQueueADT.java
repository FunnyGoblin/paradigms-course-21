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
    private int size = 0, head = 0, tail = 0;
    private Object[] elements = new Object[2];

    public static ArrayQueueADT create() {
        return new ArrayQueueADT();
    }

    private static void ensureCapacity(ArrayQueueADT deq) {
        int n = deq.elements.length;
        if (n == deq.size) {
            deq.elements = Arrays.copyOf(deq.elements, 2 * n);
            for (int i = 0; i < deq.head; i++) {
                deq.elements[i + n] = deq.elements[i];
                deq.elements[i] = null;
            }
            deq.tail = deq.head + n;
        }
    }

    //Pred: x != null
    //Post : n = n' + 1 && a[n] == x && forall i = 1..n': a[i] == a[i]'
    public static void enqueue(ArrayQueueADT deq, Object x) {
        Objects.requireNonNull(x);
        ensureCapacity(deq);
        deq.elements[deq.tail++] = x;
        if (deq.tail == deq.elements.length) {
            deq.tail = 0;
        }
        deq.size++;
    }

    //Pred: x != null
    //Post: n == n' + 1 && a[1] == x && forall i = 2..n a[i] == a[i - 1]'
    public static void push(ArrayQueueADT deq, Object x) {
        Objects.requireNonNull(x);
        ensureCapacity(deq);
        --deq.head;
        if (deq.head < 0) {
            deq.head = deq.elements.length - 1;
        }
        deq.elements[deq.head] = x;
        deq.size++;
    }

    //Pred: n > 0
    //Post :R == a[1] && Immutable
    public static Object element(ArrayQueueADT deq) {
        assert deq.size > 0;
        return deq.elements[deq.head];
    }

    //Pred: n > 0
    //Post: R == a[n] && Immutable
    public static Object peek(ArrayQueueADT deq) {
        assert deq.size > 0;
        return deq.elements[(deq.tail - 1 + deq.elements.length) % deq.elements.length];
    }

    //Pred: n > 0
    //Post : n == n' - 1 && forall i = 1..n: a[i] == a[i+1]' && R == a[1]'
    public static Object dequeue(ArrayQueueADT deq) {
        assert deq.size > 0;
        Object r = deq.elements[deq.head];
        deq.elements[deq.head++] = null;
        if (deq.head == deq.elements.length) {
            deq.head = 0;
        }
        deq.size--;
        return r;
    }

    //Pred: n > 0;
    //Post: n == n' - 1 && forall i = 1..n: a[i] == a[i]' && R == a[n']'
    public static Object remove(ArrayQueueADT deq) {
        assert deq.size > 0;
        deq.tail--;
        if (deq.tail < 0) {
            deq.tail = deq.elements.length - 1;
        }
        Object r = deq.elements[deq.tail];
        deq.elements[deq.tail] = null;
        deq.size--;
        return r;
    }

    //Pred: true
    //Post :R == n && Immutable
    public static int size(ArrayQueueADT deq) {
        return deq.size;
    }

    //Pred: true
    //Post :R == [n > 0] && Immutable
    public static boolean isEmpty(ArrayQueueADT deq) {
        return deq.size == 0;
    }

    //Pred: true
    //Post:
    public static void clear(ArrayQueueADT deq) {
        while (!isEmpty(deq)) {
            dequeue(deq);
        }
    }
}
