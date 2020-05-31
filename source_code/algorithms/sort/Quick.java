package sort;

/**
 * 快速排序
 *
 * @author humingk
 */
public class Quick {
    public static void sort(Comparable[] a) {
//        StdRandom.shuffle(a);
        sort(a, 0, a.length - 1);
    }

    private static void sort(Comparable[] a, int lo, int hi) {
        if (lo >= hi) {
            return;
        }
        //j为切分值替换的位置
        int j = partition(a, lo, hi);
        sort(a, lo, j - 1);
        sort(a, j + 1, hi);
    }

    /**
     * 1. 交换法
     *
     * @param a
     * @param lo
     * @param hi
     * @return
     */
    private static int partition(Comparable[] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        Comparable v = a[lo];
        while (true) {
            // 从前向后，若当前元素大于v，则需要换到右边
            while (i < hi && less(a[++i], v)) ;
            // 从后向前,若当前元素小于v，则需要换到左边
            while (j > lo && less(v, a[--j])) ;
            if (i >= j) {
                break;
            }
            // ij交换
            exch(a, i, j);
        }
        //前后相遇处替换为 v
        exch(a, lo, j);
        return j;
    }

    //如果v<w,返回true
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print((a[i] + " "));
        }
        System.out.println();
    }

    public static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++) {
            if (less(a[i], a[i - 1])) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
//        String[] a = {"S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
        String[] a = {"1", "2", "3", "2", "2", "2", "5", "4", "2"};
        sort(a);
        assert isSorted(a) : "wrong";
        show(a);
    }
}