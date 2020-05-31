package search;

import edu.princeton.cs.algs4.Queue;

/**
 * @author humingk
 */
public class SepatateChainingHashSt<Key, Value> {
    private static final int HIGH_AVERAGE = 10;
    private static final int LOW_AVERAGE = 2;
    private static final int DOUBLE = 2;
    /**
     * 键值对总数
     */
    private int n;
    /**
     * 散列表大小
     */
    private int m;
    /**
     * 存放链表对象的数组
     */
    private search.SequentialSearchSt<Key, Value>[] st;

    public SepatateChainingHashSt() {
        this(997);
    }

    public SepatateChainingHashSt(int m) {
        this.m = m;
        st = (search.SequentialSearchSt<Key, Value>[]) new search.SequentialSearchSt[m];
        for (int i = 0; i < m; i++) {
            st[i] = new search.SequentialSearchSt();
        }
    }

    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % m;
    }

    public Value get(Key key) {
        return (Value) st[hash(key)].get(key);
    }

    public void put(Key key, Value value) {
        if (value == null) {
            delete(key);
            return;
        }
        // 如果存放链表对象的数组平均长度大于HIGH_AVERAGE,扩容
        if (n >= HIGH_AVERAGE * m) {
            resize(DOUBLE * m);
        }
        // 键值对总数递增
        if (!st[hash(key)].contains(key)) {
            n++;
        }
        st[hash(key)].put(key, value);

    }

    public void delete(Key key) {
        // 键值对数递减
        if (st[hash(key)].contains(key)) {
            n--;
        }
        st[hash(key)].delete(key);
        // 如果存放链表对象的数组平均长度小于LOW_AVERAGE,缩容
        if (n <= LOW_AVERAGE * m) {
            resize(m / DOUBLE);
        }
    }

    private Iterable<Key> keys() {
        Queue queue = new Queue<Key>();
        for (int i = 0; i < m; i++) {
            for (Key key : st[i].keys()) {
                queue.enqueue(key);
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
        SepatateChainingHashSt temp = new SepatateChainingHashSt<Key, Value>(chains);
        for (int i = 0; i < m; i++) {
            for (Key key : st[i].keys()) {
                temp.put(key, st[i].get(key));
            }
        }
        this.m = temp.m;
        this.n = temp.n;
        this.st = temp.st;
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
        SepatateChainingHashSt<String, Integer> st = new SepatateChainingHashSt<String, Integer>(5);
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

        System.out.println("orogin:");
        System.out.println("st     key:value");
        for (int j = 0; j < st.m; j++) {
            System.out.print(j + "      ");
            for (String string : st.st[j].keys()) {
                System.out.print(string + ":" + st.get(string) + " ");
            }
            System.out.println();
        }

        st.delete("P");
        st.delete("E");
        System.out.println("delete P and E then:");
        System.out.println("st     key:value");
        for (int j = 0; j < st.m; j++) {
            System.out.print(j + "      ");
            for (String string : st.st[j].keys()) {
                System.out.print(string + ":" + st.get(string) + " ");
            }
            System.out.println();
        }
    }
}
