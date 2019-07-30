import java.util.HashMap;
import java.util.Map;

// The cache is initialized with a positive capacity
// Do operations in O(1) time complexity
public class LRUCache {

    private int capacity;
    private DLinkedNode head;
    private DLinkedNode tail;
    private Map<Integer, DLinkedNode> cache;
    private int count;

    private class DLinkedNode {
        int value;
        int key;
        DLinkedNode prev;
        DLinkedNode next;
    }

    public LRUCache(int capacity) {
        cache = new HashMap<Integer, DLinkedNode>((int)((capacity + 1) / 0.75));
        this.capacity = capacity;
        head = new DLinkedNode();
        tail = new DLinkedNode();

        head.prev = null;
        head.next = tail;
        tail.next = null;
        tail.prev = head;
        count = 0;

    }

    private void addNodeToHead(DLinkedNode node) { // add node to first of the node
        node.next = head;
        node.prev = null;
        head.prev = node;
        head = node;
    }

    private void removeTailNode() { //remove the last node
        tail = tail.prev;
        tail.next = null;

    }

    private void removeNode(DLinkedNode node) { //remove particular node
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private int get(int key) {
        // Get the value (will always be positive) of the key if
        // the key exists in the cache, otherwise return -1.

        if (cache.containsKey(key)) {
            removeNode(cache.get(key));
            addNodeToHead(cache.get(key));
            return cache.get(key).value;
        } else {
            return -1; // not found key
        }
    }

    public void put(int key, int value) {
        //Set or insert the value if the key is not already present.
        // When the cache reached its capacity,it should invalidate the least recently
        // used item before inserting a new item.
        if (cache.containsKey(key)) {
            cache.get(key).value = value; // update value
            return;
        }

        count++;
        DLinkedNode newNode = new DLinkedNode();

        newNode.key = key;
        newNode.value = value;
        cache.put(key, newNode);
        addNodeToHead(newNode);

        if (count > capacity) {
            cache.remove(tail.key);
            removeTailNode();
            count--;
        }
    }

    public static void main(String[] args) {
        LRUCache cache = new LRUCache(2 /* capacity */);

        cache.put(1, 1);
        System.out.println(cache.get(1));
        cache.put(2, 2);
        System.out.println(cache.get(1));       // returns 1
        cache.put(3, 3);                        // evicts key 2
        cache.get(2);                           // returns -1 (not found)
        cache.put(4, 4);                        // evicts key 1
        cache.get(1);                           // returns -1 (not found)
        System.out.println(cache.get(3));       // returns 3
        System.out.println(cache.get(4));       // returns 4
    }
}