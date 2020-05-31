package search;

import edu.princeton.cs.algs4.Queue;

/**
 * @author humingk
 */
public class SequentialSearchSt<Key, Value> {
    private int n;
    private Node first;

    private class Node {
        private Key key;
        private Value value;
        private Node next;

        public Node(Key key, Value value, Node next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    public SequentialSearchSt() {

    }

    public Value get(Key key) {
        for (Node i = first; i != null; i = i.next) {
            if (key.equals(i.key)) {
                return i.value;
            }
        }
        return null;
    }

    public void put(Key key, Value value) {
        if (value == null) {
            delete(key);
            return;
        }
        // 修改value
        for (Node i = first; i != null; i = i.next) {
            if (key.equals(i.key)) {
                i.value = value;
                return;
            }
        }
        // 新添加
        first = new Node(key, value, first);
        n++;
        return;
    }

    public boolean contains(Key key) {
        return get(key) != null;
    }

    public void delete(Key key) {
        first = delete(key, first);
    }

    public Node delete(Key key, Node node) {
        if (key == null) {
            return null;
        }
        if (key.equals(node.key)) {
            n--;
            return node.next;
        }
        node.next = delete(key, node.next);
        return node;
    }

    public int size() {
        return n;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<Key>();
        for (Node i = first; i != null; i = i.next) {
            queue.enqueue(i.key);
        }
        return queue;
    }

    public static void main(String[] args) {
        String[] a = {"S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
        SequentialSearchSt<String, Integer> st = new SequentialSearchSt<String, Integer>();
        int i = 0;
        for (String s : a) {
            st.put(s, i);
            i++;
        }
        System.out.print("Key:  ");
        for (int j = 0; j < a.length; j++) {
            System.out.print(a[j] + " ");
        }
        System.out.println();
        System.out.print("Value:");
        for (int j = 0; j < a.length; j++) {
            System.out.print(j + " ");
        }
        System.out.println();

        System.out.println();
        st.delete("A");
        st.delete("E");
        System.out.println("delete A and E then:");
        for (String s : st.keys()) {
            System.out.print(st.get(s) + " ");
        }
        System.out.println();
    }
}