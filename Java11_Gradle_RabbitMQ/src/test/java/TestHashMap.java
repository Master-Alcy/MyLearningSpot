public class TestHashMap<K, V> {

    private class Node<K, V> {
        private K key;
        private V value;
        private Node next;

        public Node() {
            this(null, null);
        }

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    private Node<String, Integer>[] ndoeArr = new Node[0];

    public static void main(String[] args) {

    }
}
