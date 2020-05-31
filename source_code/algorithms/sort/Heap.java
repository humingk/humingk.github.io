package sort;

/**
 * 堆排序
 *
 * @author humingk
 */
public class Heap {
    public static void sort(Comparable[] a) {
        int N = a.length;
        //构造堆
        for (int k = N / 2; k >= 1; k--) {
            sink(a, k, N);
        }
        //下沉排序
        while (N > 1) {
            exch(a, 1, N--);
            sink(a, 1, N);
        }
    }

    /**
     * 下沉
     * 根节点>子节点
     *
     * @param a
     * @param k
     * @param N
     */
    private static void sink(Comparable[] a, int k, int N) {
        while (2 * k <= N) {
            int j = 2 * k;
            //左节点小于右节点，遍历到右节点
            if (j < N && less(a, j, j + 1)) {
                j++;
            }
            //根节点大于子节点
            if (!less(a, k, j)) {
                break;
            }
            //根节点小于子节点，交换
            exch(a, k, j);
            k = j;
        }
    }

    //如果v<w,返回true
    private static boolean less(Comparable[] a, int i, int j) {
        return a[i - 1].compareTo(a[j - 1]) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i - 1];
        a[i - 1] = a[j - 1];
        a[j - 1] = t;
    }

    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print((a[i] + " "));
        }
        System.out.println();
    }

    public static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++) {
            if (less(a, ++i, i)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        String[] a = {"S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
        sort(a);
        assert isSorted(a) : "wrong";
        show(a);
    }
}