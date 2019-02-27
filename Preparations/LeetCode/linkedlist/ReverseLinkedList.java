package linkedlist;

import java.util.ArrayDeque;
import java.util.Deque;

public class ReverseLinkedList {

    public static void main(String[] args) {
        ReverseLinkedList rll = new ReverseLinkedList();
        ListNode root = new ListNode(1);
        root.next = new ListNode(2);
        root.next.next = new ListNode(3);
        root.next.next.next = new ListNode(4);
        root.next.next.next.next = new ListNode(5);
        ListNode res = rll.reverseList3(root);
        while (res != null) {
            System.out.println(res.val);
            res = res.next;
        }
    }

    /**
     * A weak try with stack
     */
    public ListNode reverseList3(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        Deque<ListNode> stack = new ArrayDeque<>();

        while (head != null) {
            stack.push(head);
            head = head.next;
        } // head == null
        head = new ListNode(0);
        ListNode curr = head;

        while(!stack.isEmpty()) {
            ListNode temp = stack.pop();
            curr.next = temp;
            curr = curr.next;
        } // stack is empty but curr.next is not null
        curr.next = null; // VERY FVCKING IMPORTANT DUDE
        return head.next;
    }

    /**
     * Iterator
     */
    public ListNode reverseList(ListNode curr) {
        if (curr == null || curr.next == null) {
            return curr;
        }
        ListNode prev = null;
        while (curr != null) {
            ListNode temp = curr.next; // keep next node
            curr.next = prev; // curr points to (old)prev
            prev = curr; // renew prev to curr
            curr = temp; // renew curr to (old)curr.next
        } // curr == null, prev = (old) curr
        return prev;
    }

    /**
     * Recursion
     */
    public ListNode reverseList2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        return reverseListRec(head, null);
    }

    /**
     * null    1 ->     2 ->    3 ->    null
     * newNext curr     oldNext
     * null <- 1        2 ->    3 ->    null
     *         newNext  curr    oldNext
     * null <- 1     <- 2       3 ->    null
     *                  newNext curr    oldNext
     */
    private ListNode reverseListRec(ListNode curr, ListNode newNext) {
        if (curr == null) {
            return newNext; // at end, return last valid node
        }
        ListNode oldNext = curr.next;
        curr.next = newNext;
        return reverseListRec(oldNext, curr);
    }
}
