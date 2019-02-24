package linkedlist;

public class ReverseLinkedList {

    public static void main(String[] args) {
        ReverseLinkedList rll = new ReverseLinkedList();
        ListNode root = rll.new ListNode(1);
        root.next = rll.new ListNode(2);
        root.next.next = rll.new ListNode(3);
        root.next.next.next = rll.new ListNode(4);
        root.next.next.next.next = rll.new ListNode(5);
        ListNode res = rll.reverseList(root);
        while (res != null) {
            System.out.println(res.val);
            res = res.next;
        }
    }

    private class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    /**
     * Iterator
     */
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode prev = null;
        while (head != null) {
            ListNode temp = head.next; // keep next node
            head.next = prev; // curr points to (old)prev
            prev = head; // renew prev to curr
            head = temp; // renew curr to (old)curr.next
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
