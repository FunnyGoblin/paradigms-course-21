package queue;

// :NOTE: JAR with tests in repository

public class Main {

    public static void main(String[] args) {
        //ArrayQueueModule
        {
            System.out.println("ArrayQueueModule:\n");
            for (int i = 2; i < 5; i++) {
                ArrayQueueModule.enqueue(i * 7);
                ArrayQueueModule.push(-i * 7);
            }
            System.out.println(ArrayQueueModule.dequeue());
            System.out.println(ArrayQueueModule.remove());
            ArrayQueueModule.clear();
            System.out.println(ArrayQueueModule.isEmpty());
        }
        //ArrayQueueADT
        {
            System.out.println("ArrayQueueADT:\n");
            ArrayQueueADT q = ArrayQueueADT.create();
            for (int i = 0; i < 15; i++) {
                if (i % 2 == 0) {
                    ArrayQueueADT.enqueue(q, i * 5);
                } else {
                    ArrayQueueADT.push(q, i * 7);
                }
            }
            while (!ArrayQueueADT.isEmpty(q)) {
                System.out.print(ArrayQueueADT.dequeue(q));
                System.out.print(" ");
            }
            System.out.println();
        }
        //ArrayQueue
        {
            System.out.println("ArrayQueue:\n");
            ArrayQueue q = new ArrayQueue();
            for (int i = 0; i < 15; i++) {
                if (i % 2 == 0) {
                    q.enqueue(i * 11);
                } else {
                    q.push(i * 13);
                }
            }
            while (!q.isEmpty()) {
                System.out.print(q.dequeue());
                System.out.print(" ");
            }
        }
    }
}
