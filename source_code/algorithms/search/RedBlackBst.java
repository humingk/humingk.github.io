package search;

import edu.princeton.cs.algs4.Queue;

/**
 * @author humingk
 */
public class RedBlackBst<Key extends Comparable<Key>, Value> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;
    private Node root;

    private class Node {
        private Key key;
        private Value value;
        private Node left, right;
        /**
         * 红色：true
         * 黑色：false
         * 空链接为黑色
         */
        private boolean color;
        /**
         * 以该节点为根的子节点总数
         */
        private int n;

        public Node(Key key, Value value, boolean color, int n) {
            this.key = key;
            this.value = value;
            this.color = color;
            this.n = n;
        }
    }

    public RedBlackBst() {
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
        // 根节点总是黑色的，若由红色变为黑色，说明树高度加1
        root.color = BLACK;
    }

    private Node put(Node x, Key key, Value value) {
        // 树是空树，返回一个根节点
        // key不存在，将key 和 value 作为新节点插入到该子树中
        if (x == null) {
            return new Node(key, value, RED, 1);
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
        // 红色右链接 转化为 红色左链接
        if (!isRed(x.left) && isRed(x.right)) {
            x = rotateLeft(x);
        }
        // 连续两个红色左链接 转化为 红色左链接+红色右链接
        if (isRed(x.left) && isRed(x.left.left)) {
            x = rotateRight(x);
        }
        // 红色左链接+红色右链接 转化为 红色父节点链接
        if (isRed(x.left) && isRed(x.right)) {
            flipColors(x);
        }
        x.n = size(x.left) + size(x.right) + 1;
        return x;
    }

    private boolean isRed(Node x) {
        if (x == null) {
            return false;
        }
        return x.color == RED;
    }

    private boolean is23() {
        return is23(root);
    }

    /**
     * 检查是否存在：
     * 一个节点连接两个红色链接
     * 红色右链接
     *
     * @param x
     * @return
     */
    private boolean is23(Node x) {
        if (x == null) {
            return true;
        }
        if (isRed(x.right)) {
            return false;
        }
        if (x != root && isRed(x) && isRed(x.left)) {
            return false;
        }
        return is23(x.left) && is23(x.right);
    }

    private boolean isBalanced() {
        int black = 0;
        Node x = root;
        while (x != null) {
            if (!isRed(x)) {
                black++;
            }
            x = x.left;
        }
        return isBalanced(root, black);
    }

    /**
     * 检查从某一节点到所有空链接的路径上的黑链接数量是否相同
     *
     * @param x
     * @param black
     * @return
     */
    private boolean isBalanced(Node x, int black) {
        if (x == null) {
            return black == 0;
        }
        if (!isRed(x)) {
            black--;
        }
        return isBalanced(x.left, black) && isBalanced(x.right, black);
    }

    private boolean isBst() {
        return isBst(root, null, null);
    }

    /**
     * 判断是否是一个RedBlackBST
     *
     * @param x
     * @param min
     * @param max
     * @return
     */
    private boolean isBst(Node x, Key min, Key max) {
        if (x == null) {
            return true;
        }
        if (min != null && x.key.compareTo(min) <= 0) {
            return false;
        }
        if (max != null && x.key.compareTo(max) >= 0) {
            return false;
        }
        return isBst(x.left, null, x.key) && isBst(x.right, x.key, null) && isBalanced();
    }

    /**
     * 红色右链接左旋为红色左链接
     *
     * @param h 上面的节点
     * @return
     */
    private Node rotateLeft(Node h) {
        Node x = h.right;

        h.right = x.left;
        x.left = h;

        x.color = h.color;
        h.color = RED;

        x.n = h.n;
        h.n = size(h.left) + size(h.right) + 1;
        return x;
    }

    /**
     * 红色左链接右旋为红色右链接
     *
     * @param h 上面的节点
     * @return
     */
    private Node rotateRight(Node h) {
        Node x = h.left;

        h.left = x.right;
        x.right = h;

        x.color = h.color;
        h.color = RED;

        x.n = h.n;
        h.n = size(h.left) + size(h.right) + 1;
        return x;
    }

    /**
     * --- 根节点总是黑色的
     * 一个节点的左右链接都是红色
     * 节点转化为红链接
     * 节点的子节点转化为黑链接
     *
     * @param x
     */
    private void flipColors(Node x) {
        x.color = !x.color;
        x.left.color = !x.left.color;
        x.right.color = !x.right.color;
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

    /**
     * 节点x不能有：
     * 红色右链接
     * 连续两个红色左链接
     * 红色左链接+红色右链接
     *
     * @param x
     * @return
     */
    private Node balance(Node x) {
        // 红色右链接 转化为 红色左链接
        if (!isRed(x.left) && isRed(x.right)) {
            x = rotateLeft(x);
        }
        // 连续两个红色左链接 转化为 红色左链接+红色右链接
        if (isRed(x.left) && isRed(x.left.left)) {
            x = rotateRight(x);
        }
        // 红色左链接+红色右链接 转化为 红色父节点链接
        if (isRed(x.left) && isRed(x.right)) {
            flipColors(x);
        }
        x.n = size(x.left) + size(x.right) + 1;
        return x;
    }

    /**
     * x是红色节点，x.left 和 x.left.left 都是黑色节点
     * 将x.left 或 x.left的左子节点 变成红色节点
     *
     * @param x
     * @return
     */
    private Node moveRedLeft(Node x) {
        flipColors(x);
        if (isRed(x.right.left)) {
            x.right = rotateRight(x.right);
            x = rotateLeft(x);
            flipColors(x);
        }
        return x;
    }

    public void deleteMin() {
        //沿着左链接向下，并且确保当前节点不是二节点(可以是三节点和四节点)
        if (!isRed(root.left) && !isRed(root.right)) {
            root.color = RED;
        }
        root = deleteMin(root);
        if (!isEmpty()) {
            root.color = BLACK;
        }
    }

    private Node deleteMin(Node x) {
        // 返回x的右节点,等同于：
        // 将x的上一节点指向x，改成x的上一节点指向x的右节点
        // 此时没有任何链接指向x，x会被当做垃圾回收
        if (x.left == null) {
            return null;
        }
        // moveRedLeft()
        if (!isRed(x.left) && !isRed(x.left.left)) {
            x = moveRedLeft(x);
        }
        x.left = deleteMin(x.left);
        return balance(x);
    }

    /**
     * x为红色节点，x.right 和 x.right.left是黑色节点
     * 将x.right 或 x.right 的右子节点 变成红色
     *
     * @param x
     * @return
     */
    private Node moveRedRight(Node x) {
        flipColors(x);
        if (isRed(x.left.left)) {
            x = rotateRight(x);
            flipColors(x);
        }
        return x;
    }

    public void deleteMax() {
        if (!isRed(root.left) && !isRed(root.right)) {
            root.color = RED;
        }
        root = deleteMax(root);
        if (!isEmpty()) {
            root.color = BLACK;
        }
    }

    private Node deleteMax(Node x) {
        if (isRed(x.left)) {
            x = rotateRight(x);
        }
        if (x.right == null) {
            return null;
        }
        if (!isRed(x.right) && !isRed(x.right.left)) {
            x = moveRedRight(x);
        }
        x.right = deleteMax(x.right);
        return balance(x);
    }

    public void delete(Key key) {
        if (!isRed(root.left) && !isRed(root.right)) {
            root.color = RED;
        }
        root = delete(root, key);
        if (isEmpty()) {
            root.color = BLACK;
        }
    }

    private Node delete(Node h, Key key) {
        // 向左递归
        if (key.compareTo(h.key) < 0) {
            // x是红色节点，x.left 和 x.left.left 都是黑色节点
            // 将x.left 或 x.left的左子节点 变成红色节点
            if (!isRed(h.left) && !isRed(h.left.left)) {
                h = moveRedLeft(h);
            }
            h.left = delete(h.left, key);
        }
        // 向右递归
        else {
            if (isRed(h.left)) {
                h = rotateRight(h);
            }
            if (key.compareTo(h.key) == 0 && h.right == null) {
                return null;
            }
            // x为红色节点，x.right 和 x.right.left是黑色节点
            // 将x.right 或 x.right 的右子节点 变成红色
            if (!isRed(h.right) && !isRed(h.right.left)) {
                h = moveRedRight(h);
            }
            // 删除操作
            if (key.compareTo(h.key) == 0) {
                Node x = min(h.right);
                h.key = x.key;
                h.value = x.value;
                h.right = deleteMin(h.right);
            } else {
                h.right = delete(h.right, key);
            }
        }
        return balance(h);
    }

    public static void main(String[] args) {
        String[] a = {"S", "O", "R", "T", "E", "X", "A", "M", "P", "L"};
        RedBlackBst<String, Integer> st = new RedBlackBst<>();
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

        System.out.println("isBalanced?: " + st.isBalanced());
        System.out.println("isRedBlackBST?: " + st.isBst());

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