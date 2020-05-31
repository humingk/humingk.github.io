package search;

import edu.princeton.cs.algs4.Queue;

/**
 * @author humingk
 */
public class BinarySearchSt<Key extends Comparable<Key>, Value> {
    private static final int LOW = 4;
    private static final int DOUBLE = 2;
    private Key[] keys;
    private Value[] values;
    private int n = 0;

    public BinarySearchSt() {
        keys = (Key[]) new Comparable[2];
        values = (Value[]) new Object[2];
    }

    public Value get(Key key) {
        if (isEmpty()) {
            return null;
        }
        int i = rank(key);
        if (i < n && i >= 0 && keys[i].compareTo(key) == 0) {
            return values[i];
        }
        return null;
    }

    public void put(Key key, Value value) {
        if (value == null) {
            delete(key);
            return;
        }
        int i = rank(key);
        if (i < n && i >= 0 && keys[i].compareTo(key) == 0) {
            values[i] = value;
            return;
        }
        // keys扩容
        if (n == keys.length) {
            resize(2 * keys.length);
        }
        //比key大的（在key后面）的元素向后移一位
        for (int j = n; j > i; j--) {
            keys[j] = keys[j - 1];
            values[j] = values[j - 1];
        }
        keys[i] = key;
        values[i] = value;
        n++;
    }

    /**
     * 返回key在keys中的位置
     *
     * @param key
     * @return
     */
    public int rank(Key key) {
        int lo = 0, hi = n - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int cmp = key.compareTo(keys[mid]);
            if (cmp < 0) {
                hi = mid - 1;
            } else if (cmp > 0) {
                lo = mid + 1;
            } else {
                return mid;
            }
        }
        return lo;
    }

    public void delete(Key key) {
        if (isEmpty()) {
            return;
        }
        int i = rank(key);
        // 表中没有此元素
        if (i == n || keys[i].compareTo(key) != 0) {
            return;
        }
        // 比key大的元素向前移一位
        for (int j = i; j < n - 1; j++) {
            keys[j] = keys[j + 1];
            values[j] = values[j + 1];
        }
        n--;
        keys[n] = null;
        values[n] = null;
        // keys 减容
        if (n > 0 && n == keys.length / LOW) {
            resize(keys.length / DOUBLE);
        }
    }

    public int size() {
        return n;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public boolean contains(Key key) {
        return get(key) != null;
    }

    private void resize(int capacity) {
        assert capacity >= n;
        Key[] tempKeys = (Key[]) new Comparable[capacity];
        Value[] tempvalues = (Value[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            tempKeys[i] = keys[i];
            tempvalues[i] = values[i];
        }
        keys = tempKeys;
        values = tempvalues;
    }

    public Iterable<Key> keys() {
        return keys(keys[0], keys[n - 1]);
    }

    public Iterable<Key> keys(Key lo, Key hi) {
        Queue<Key> queue = new Queue<Key>();
        if (lo.compareTo(hi) > 0) {
            return queue;
        }
        for (int i = rank(lo); i < rank(hi); i++) {
            queue.enqueue(keys[i]);
        }
        if (contains(hi)) {
            queue.enqueue(keys[rank(hi)]);
        }
        return queue;
    }

    public static void main(String[] args) {
        String[] a = {"S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
        BinarySearchSt<String, Integer> st = new BinarySearchSt<String, Integer>();
        int i = 0;
        for (String s : a) {
            st.put(s, i);
            i++;
        }
        System.out.println("orign:");
        for (String s : st.keys()) {
            System.out.print(st.get(s) + " ");
        }
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