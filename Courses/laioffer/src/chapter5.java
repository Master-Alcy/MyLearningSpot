import java.util.*;

public class chapter5 {

    public static void main(String[] args) {
        test1();
    }

    private static void test1() {
        Queue<Integer> pq = new PriorityQueue<>(5);
        pq.offer(9);
        pq.offer(1);
        pq.offer(2);
        pq.offer(3);
        pq.offer(20);

        System.out.println(pq.toString()); // it's a heap
        while (!pq.isEmpty()) {
            System.out.println(pq.poll());
        }

        PriorityQueue<Integer> realPQ = new PriorityQueue<>(5, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });
        realPQ.offer(9);
        realPQ.offer(1);
        realPQ.offer(2);
        realPQ.offer(3);
        realPQ.offer(20);

        System.out.println(realPQ.toString()); // it's a heap
        while (!realPQ.isEmpty()) {
            System.out.println(realPQ.poll());
        }

        Deque<Integer> dq = new ArrayDeque<>();
    }

    private class myStack {
        ListNode head;
        int size;

        private class ListNode {
            int val;
            ListNode next;

            public ListNode(int val) {
                this.val = val;
            }
        }

        public myStack () {
            head = new ListNode(0);
            this.size = 0;
        }

        // ...
    }
}
