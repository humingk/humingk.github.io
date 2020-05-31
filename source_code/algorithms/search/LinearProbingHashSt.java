package search;

import edu.princeton.cs.algs4.Queue;

/**
 * @author humingk
 */
public class LinearProbingHashSt<Key, Value> {
    private static final float HIGH_FULL = (float) 1 / (float) 2;
    private static final float LOW_FULL = (float) 1 / (float) 8;
    private static final int DOUBLE = 2;
    /**
     * 键值对总数
     */
    private int n;
    /**
     * 线性探测表大小
     */
    private int m;
    /**
     * 存放链表对象的数组
     */
    private Key[] keys;
    private Value[] values;

    public LinearProbingHashSt() {
        this(4);
    }

    public LinearProbingHashSt(int m) {
        this.m = m;
        this.n = 0;
        keys = (Key[]) new Object[m];
        values = (Value[]) new Object[m];
    }

    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % m;
    }

    public Value get(Key key) {
        for (int i = hash(key); keys[i] != null; i = (i + 1) % m) {
            if (key.equals(keys[i])) {
                return values[i];
            }
        }
        return null;
    }

    public void put(Key key, Value value) {
        if (value == null) {
            delete(key);
            return;
        }
        // 如果线性表长度占用百分比大于HIGH_FULL,扩容
        if (n >= HIGH_FULL * m) {
            resize(DOUBLE * m);
        }
        int i;
        for (i = hash(key); keys[i] != null; i = (i + 1) % m) {
            if (key.equals(keys[i])) {
                values[i] = value;
                return;
            }
        }
        keys[i] = key;
        values[i] = value;
        n++;
    }

    public void delete(Key key) {
        if (!contains(key)) {
            return;
        }
        int i = hash(key);
        while (!key.equals(keys[i])) {
            i = (i + 1) % m;
        }
        keys[i] = null;
        values[i] = null;

        // 刷新剩余key的hash值
        for (int j = (i + 1) % m; keys[j] != null; j = (j + 1) % m) {
            Key keyTemp = keys[j];
            Value valueTemp = values[j];
            keys[j] = null;
            values[j] = null;
            n--;
            put(keyTemp, valueTemp);
        }

        n--;
        // 如果线性表长度占用百分比大于HIGH_FULL,扩容
        if (n <= LOW_FULL * m) {
            resize(m / DOUBLE);
        }
    }

    private Iterable<Key> keys() {
        Queue queue = new Queue<Key>();
        for (int i = 0; i < m; i++) {
            if (keys[i] != null) {
                queue.enqueue(keys[i]);
            }
        }
        return queue;
    }

    /**
     * 调整存放链表对象数组的大小
     *
     * @param chains
     */
    private void resize(int chains) {
        LinearProbingHashSt<Key, Value> temp = new LinearProbingHashSt<Key, Value>(chains);
        for (int i = 0; i < m; i++) {
            if (keys[i] != null) {
                temp.put(keys[i], values[i]);
            }
        }
        this.m = temp.m;
        this.n = temp.n;
        this.keys = temp.keys;
        this.values = temp.values;
    }

    public boolean contains(Key key) {
        return get(key) != null;
    }

    public int size() {
        return n;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public static void main(String[] args) {
        String[] a = {"S", "O", "R", "T", "E", "X", "A", "M", "P", "L"};
        LinearProbingHashSt<String, Integer> st = new LinearProbingHashSt<String, Integer>(4);
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

        System.out.println("origin:");
        for (String string : st.keys()) {
            System.out.print(string + ":" + st.get(string) + " ");
        }
        System.out.println();
        st.delete("P");
        st.delete("E");
        System.out.println("delete P and E then:");
        for (String string : st.keys()) {
            System.out.print(string + ":" + st.get(string) + " ");
        }
        System.out.println();
    }
}