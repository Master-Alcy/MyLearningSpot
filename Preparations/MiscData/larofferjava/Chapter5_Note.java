package larofferjava;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.PriorityQueue;
import java.util.Queue;

public class Chapter5_Note {

    public static void main(String[] args) {
        test1();
    }

    private static void test1() {
        Queue<Integer> pq = new PriorityQueue<>();
        pq.offer(1);
        pq.offer(2);
        pq.offer(3);

        System.out.println(pq.poll());
        System.out.println(pq.peek());

        Deque<Integer> dq = new ArrayDeque<>();
    }
}
