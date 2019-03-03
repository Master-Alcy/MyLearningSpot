package riskval.prepare;


//interface FunLinkedList<T> {
//    int getSize();
//
//    T get(int index);
//
//    void set(int index, T value);
//}
//
//class MyListNode<T> {
//    T val;
//    MyListNode prev;
//    MyListNode next;
//
//    MyListNode(T val) {
//        this.val = val;
//        prev = next = null;
//    }
//}

public class MyLinkedList {

    private class ListNode {
        int val;
        ListNode prev, next;

        ListNode(int val) {
            this.val = val;
            prev = next = null;
        }
    }

    private int size;
    private ListNode head;

    public MyLinkedList() {
        size = 0;
        head = new ListNode(0); // like dummy
    }


    public int getSize() {
        return size;
    }

    public void addLast(int val) {
        ListNode curr = head; // non-null

        while (curr.next != null) {
            curr = curr.next;
        } // curr.next == null;

        ListNode node = new ListNode(val);
        curr.next = node;
        node.prev = curr;
        // curr.prev is other node
        // node.next is null
        size++;
    }

    public void addFirst(int val) {
        ListNode oldFirst = head.next;
        ListNode newFirst = new ListNode(val);
        // head.prev is null
        head.next = newFirst;
        newFirst.prev = head;
        newFirst.next = oldFirst;
        if (oldFirst != null) {
            oldFirst.prev = newFirst;
            // oldFirst.next remains
        } // else oldFirst is null
        size++;
    }

    public int get(int index) {
        if (index < 0 || index >= size) {
            System.out.println("Error: Out of Bound");
            return 0;
        }
        ListNode curr = head.next;
        int count = 0;
        while (count < index) { // note size should ensure that curr is never null
            count ++;
            curr = curr.next;
        } // for index: 0 => break;
        return curr.val;
    }

    public String toString() {
        ListNode curr = head.next;
        String result = "";
        while(curr != null) {
            result += curr.val + " -> ";
            curr = curr.next;
        } // curr is null
        result += "null";
        return result;
    }

    public int remove(int index) {
        if (index < 0 || index >= size) { // index = 0 when size = 0 is error
            System.out.println("Error: Out of Bound");
            return 0;
        }
        ListNode curr = head.next; // note size and index makes sure this is valid
        int count = 0;
        while (count < index) {
            count ++;
            curr = curr.next;
        } // curr.next is null, curr is not null and not head
        ListNode oldBefore = curr.prev;
        ListNode oldAfter = curr.next;
        curr.prev = curr.next = null; // ready to be GCed
        oldBefore.next = oldAfter; // oldBefore.prev is ok
        if (oldAfter != null) {
            oldAfter.prev = oldBefore; // oldAfter.next is ok
        } // else it's null, then enough
        size--;
        return curr.val;
    }

    public static void main(String[] args) {
        MyLinkedList mll = new MyLinkedList();
        System.out.println("Check initial size: " + mll.getSize());
        mll.addFirst(1);
        mll.addFirst(2);
        mll.addFirst(3);
        mll.addFirst(4);
        System.out.println(mll.toString());
        mll.addLast(6);
        mll.addLast(7);
        mll.addLast(8);
        mll.addLast(9);
        System.out.println(mll.toString());
        System.out.println("Check final size: " + mll.getSize());
        System.out.println("Get 0, 7, 5: " + mll.get(0) + " " + mll.get(7) + " " + mll.get(5));
        System.out.println("Remove 0, 6: " + mll.remove(0) + " " + mll.remove(6));
        System.out.println("Size: " + mll.getSize());
        System.out.println(mll.toString());
    }
}
