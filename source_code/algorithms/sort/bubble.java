package sort;

/**
 * 冒泡排序
 *
 * @author humingk
 */
public class bubble {
    public static void sort(Comparable[] a) {
        // 标志位
        boolean flag;
        // 外循环
        for (int i = a.length - 1; i >= 0; i--) {
            flag = false;
            for (int j = 0; j < i; j++) {
                if (!less(a[j], a[j + 1])) {
                    exch(a, j, j + 1);
                    flag = true;
                }
            }
            // 只要有一次内循环没有交换，则说明已有序
            if (!flag) {
                return;
            }
        }
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
        String[] a = {"S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
        sort(a);
        assert isSorted(a);
        show(a);
    }
}