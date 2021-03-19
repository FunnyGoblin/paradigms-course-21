package queue;

public class Main {

    static void fill(AbstractQueue q) {
        for (int i = 0; i < 7; ++i) {
            q.enqueue(i * (5 - i % 2));
        }
    }

    static void dump(AbstractQueue q) {
        while (!q.isEmpty()) {
            System.out.print(q.dequeue());
            System.out.print(" ");
            System.out.print(q.contains(20));
            System.out.print("   ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        ArrayQueue q = new ArrayQueue();
        LinkedQueue q1 = new LinkedQueue();
        fill(q);
        fill(q1);
        q1.removeFirstOccurrence(20);
        q1.removeFirstOccurrence(20);
        dump(q);
        dump(q1);
    }
}
