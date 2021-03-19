package queue;

/*
Model:
    [a1, a2, a3, ...an]
    n -- size of deque

Inv:
    n >= 0
    forall i = 1..n: a[i] != null

Immutable:
    n == n' && forall i = 1..n: a[i] = a'[i]
*/


public interface Queue {
    //Pred: true
    //Post: R == n
    int size();

    //Pred: true
    //Post: R == (n == 0)
    boolean isEmpty();

    //Pred: x != null
    //Post: n = n' + 1 && a[n] == x && forall i = 1..n': a[i] == a[i]'
    void enqueue(Object x);

    //Pred: n > 0
    //Post: n == n' - 1 && forall i = 1..n: a[i] == a[i+1]' && R == a[1]'
    Object dequeue();

    //Pred: n > 0
    //Post: R == a[1] && Immutable
    Object element();

    //Pred: true
    //Post: n == 0
    void clear();

    //Pred: true
    //Post: Immutable && R == (exist i: a[i] == x)
    boolean contains(Object x);

    //Pred: true
    //Post: let I be the first position where a[I] == x
    // if I exist then R == true &&  n == n' - 1 && forall i = 1..I-1 a[i] == a'[i] && forall i = I..n a[i] == a'[i + 1]
    // else R == false && Immutable
    boolean removeFirstOccurrence(Object x);
}
