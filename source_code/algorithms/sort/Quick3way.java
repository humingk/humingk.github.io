package sort;

import edu.princeton.cs.algs4.StdRandom;

public class Quick3way {
    public static void sort(Comparable[] a) {
        StdRandom.shuffle(a);
        show(a);
        sort(a, 0, a.length - 1);
    }

    /**
     * @param a
     * @param lo
     * @param hi
     */
    private static void sort(Comparable[] a, int lo, int hi) {
        if (lo >= hi) {
            return;
        }
        // i部分为中间部分，为与当前v相等的元素
        int lt = lo, gt = hi, i = lo + 1;
        Comparable v = a[lo];
        while (i <= gt) {
            // 当前元素小于v
            if (less(a[i], v)) {
                exch(a, lt++, i++);
            }
            // 当前元素大于v
            else if (less(v, a[i])) {
                exch(a, i, gt--);
            }
            // 当前元素与v相等
            else {
                i++;
            }
        }
        show(a);
        // 递归归并的时候跳过i部分
        sort(a, lo, lt - 1);
        sort(a, gt + 1, hi);
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
        String[] a = {"T", "T", "T", "T", "T", "D", "D", "S", "X", "X", "O", "O", "P", "P", "P", "P", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
        sort(a);
        assert isSorted(a) : "wrong";
        show(a);
    }
}
