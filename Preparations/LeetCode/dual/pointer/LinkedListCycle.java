package dual.pointer;

public class LinkedListCycle {

    public static void main(String[] args) {
        LinkedListCycle llc = new LinkedListCycle();
        ListNode node1 = llc.new ListNode(3);
        ListNode node2 = llc.new ListNode(2);
        ListNode node3 = llc.new ListNode(0);
        ListNode node4 = llc.new ListNode(-4);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        //node4.next = node2;

        boolean res = llc.hasCycle(node1);
        System.out.println(res);
    }

    private class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        ListNode nodeSlow = head;
        ListNode nodeFast = head.next;
        while (nodeSlow != nodeFast) {
            if (nodeSlow == null || nodeFast == null)
                return false;
            nodeSlow = nodeSlow.next;
            nodeFast = nodeFast.next.next;
        }
        return true;
    }
}
