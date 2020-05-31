package search;

import edu.princeton.cs.algs4.Queue;

/**
 * @author humingk
 */
public class Bst<Key extends Comparable<Key>, Value> {
    private Node root;

    private class Node {
        private Key key;
        private Value value;
        private Node left, right;
        /**
         * 以该节点为根的子节点总数
         */
        private int n;

        public Node() {
        }

        public Node(Key key, Value value, int n) {
            this.key = key;
            this.value = value;
            this.n = n;
        }
    }

    public Bst() {
    }

    public Value get(Key key) {
        return get(root, key);
    }

    private Value get(Node x, Key key) {
        if (x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            return get(x.left, key);
        } else if (cmp > 0) {
            return get(x.right, key);
        } else {
            return x.value;
        }
    }

    public void put(Key key, Value value) {
        if (value == null) {
            delete(key);
            return;
        }
        root = put(root, key, value);
    }

    private Node put(Node x, Key key, Value value) {
        // 树是空树，返回一个根节点
        // key不存在，将key 和 value 作为新节点插入到该子树中
        if (x == null) {
            return new Node(key, value, 1);
        }
        int cmp = key.compareTo(x.key);
        // 小于当前节点 继续在左侧插入
        if (cmp < 0) {
            x.left = put(x.left, key, value);
        }
        // 大于当前节点 继续在右侧插入
        else if (cmp > 0) {
            x.right = put(x.right, key, value);
        }
        // 更新key对应的value新值
        else {
            x.value = value;
        }
        x.n = size(x.left) + size(x.right) + 1;
        return x;
    }

    public boolean isEmpty() {
        return size(root) == 0;
    }

    public Iterable<Key> keys() {
        return keys(min(), max());
    }

    public Iterable<Key> keys(Key lo, Key hi) {
        Queue<Key> queue = new Queue<Key>();
        keys(root, queue, lo, hi);
        return queue;
    }

    /**
     * 查找lo-hi范围内的key
     *
     * @param x
     * @param queue
     * @param lo
     * @param hi
     */
    private void keys(Node x, Queue<Key> queue, Key lo, Key hi) {
        if (x == null) {
            return;
        }
        int cmplo = lo.compareTo(x.key);
        int cmphi = hi.compareTo(x.key);
        // lo < x.key
        if (cmplo < 0) {
            keys(x.left, queue, lo, hi);
        }
        // lo <= x.key <= hi
        if (cmplo <= 0 && cmphi >= 0) {
            queue.enqueue(x.key);
        }
        // x.key < hi
        if (cmphi > 0) {
            keys(x.right, queue, lo, hi);
        }

    }

    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) {
            return 0;
        } else {
            return x.n;
        }
    }

    public Key min() {
        return min(root).key;
    }

    private Node min(Node x) {
        if (x.left == null) {
            return x;
        }
        return min(x.left);
    }

    public Key max() {
        return max(root).key;
    }

    private Node max(Node x) {
        if (x.right == null) {
            return x;
        }
        return max(x.right);
    }

    public Key floor(Key key) {
        Node x = floor(root, key);
        if (x == null) {
            return null;
        }
        return x.key;
    }

    /**
     * @param x
     * @param key
     * @return 小于等于key节点中的最大键
     */
    private Node floor(Node x, Key key) {
        if (x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        // key等于x，小于等于key的节点为x
        if (cmp == 0) {
            return x;
        }
        // key小于x, 那么floor一定在x的左子树中，向左递归
        if (cmp < 0) {
            return floor(x.left, key);
        }
        // key大于x, 那么floor可能在 "x的右子树中/x"，向右递归
        Node t = floor(x.right, key);
        // 当x右子树存在floor时，返回t
        if (t != null) {
            return t;
        }
        // 当x右子树不存在floor时，返回x
        else {
            return x;
        }
    }

    public Key ceiling(Key key) {
        Node x = ceiling(root, key);
        if (x == null) {
            return null;
        }
        return x.key;
    }

    /**
     * @param x
     * @param key
     * @return 大于等于key的最小键节点
     */
    private Node ceiling(Node x, Key key) {
        if (x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        // key等于x，大于等于key的节点为x
        if (cmp == 0) {
            return x;
        }
        // key小于x, 那么floor可能在 "x的左子树中/x"，向左递归
        if (cmp < 0) {
            Node t = ceiling(x.left, key);
            //key小于x，且x的左子数存在floor，返回t
            if (t != null) {
                return t;
            }
            //key小于x，且x的左子数不存在floor，返回x
            else {
                return x;
            }
        }
        //key大于x，那么floor一定在x的右子树中，向右递归
        return ceiling(x.right, key);
    }

    public Key select(int k) {
        return select(root, k).key;
    }

    /**
     * @param x
     * @param k
     * @return 返回排名为k的节点
     */
    private Node select(Node x, int k) {
        if (x == null) {
            return null;
        }
        int t = size(x.left);
        if (t > k) {
            return select(x.left, k);
        } else if (t < k) {
            return select(x.right, k);
        } else {
            return x;
        }
    }

    public int rank(Key key) {
        return rank(root, key);
    }

    /**
     * @param x
     * @param key
     * @return 给定键的排名
     */
    private int rank(Node x, Key key) {
        if (x == null) {
            return 0;
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            return rank(x.left, key);
        } else if (cmp > 0) {
            return rank(x.right, key) + rank(x.left, key) + 1;
        } else {
            return size(x.left);
        }
    }

    public void deleteMin() {
        root = deleteMin(root);
    }

    private Node deleteMin(Node x) {
        // 返回x的右节点,等同于：
        // 将x的上一节点指向x，改成x的上一节点指向x的右节点
        // 此时没有任何链接指向x，x会被当做垃圾回收
        if (x.left == null) {
            return x.right;
        }
        x.left = deleteMin(x.left);
        //计数器更新
        x.n = size(x.left) + size(x.right) + 1;
        return x;
    }

    public void deleteMax() {
        root = deleteMax(root);
    }

    private Node deleteMax(Node x) {
        if (x.right == null) {
            return x.left;
        }
        x.right = deleteMax(x.right);
        x.n = size(x.left) + size(x.right) + 1;
        return x;
    }

    public void delete(Key key) {
        root = delete(root, key);
    }

    public Node delete(Node x, Key key) {
        if (x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = delete(x.left, key);
        } else if (cmp > 0) {
            x.right = delete(x.right, key);
        } else {
            // 删除任意只有一个的左/右子节点
            if (x.left == null) {
                return x.right;
            }
            if (x.right == null) {
                return x.left;
            }
            // t为将要删除的节点
            Node t = x;
            // x为t的后继节点，即t的右子树中最小的节点
            // 删除t后，x将要顶替t
            x = min(t.right);
            // 删除掉t的右子树(R)中最小的节点x，右子树调整好后(R+)
            // 将要代替t的x的右子树指向R+
            x.right = deleteMin(t.right);
            // 将要代替t的x的左子树指向t原来的左子树
            x.left = t.left;
        }
        x.n = size(x.left) + size(x.right) + 1;
        return x;
    }

    public static void main(String[] args) {
        String[] a = {"S", "O", "R", "T", "E", "X", "A", "M", "P", "L"};
        Bst<String, Integer> st = new Bst<>();
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

        System.out.print("less than or equal to D：");
        System.out.println(st.floor("D"));
        System.out.print("greater or equal to D：");
        System.out.println(st.ceiling("D"));

        System.out.print("5st ：");
        System.out.println(st.select(4));

        System.out.print("E's ranking:");
        System.out.println(st.rank("E"));

        System.out.println("delete Max and Min then:");
        st.deleteMin();
        st.deleteMax();
        for (String s : st.keys()) {
            System.out.print(st.get(s) + " ");
        }
        System.out.println();

        st.delete("P");
        st.delete("E");
        System.out.println("delete P and E then:");
        for (String s : st.keys()) {
            System.out.print(st.get(s) + " ");
        }
        System.out.println();
    }
}