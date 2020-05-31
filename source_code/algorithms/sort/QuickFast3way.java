package sort;

import edu.princeton.cs.algs4.StdRandom;

public class QuickFast3way {
    public static void sort(Comparable[] a) {
        StdRandom.shuffle(a);
        sort(a, 0, a.length - 1);
    }

    private static void insertionSort(Comparable[] a, int lo, int hi) {
        for (int i = lo; i <= hi; i++) {
            for (int j = i; j > lo && less(a[j], a[j - 1]); j--) {
                if (less(a[j], a[j - 1])) {
                    exch(a, j, j - 1);
                }
                // 若当前不需要交换，则说明j已经到了有序部分该有的位置
                else {
                    break;
                }
            }
        }
    }

    private static void sort(Comparable[] a, int lo, int hi) {
        int length = hi - lo + 1;
        if (length <= 4) {
            insertionSort(a, lo, hi);
            return;
        } else if (length <= 8) {
            int median = median3(a, lo, lo + length / 2, hi);
            exch(a, median, lo);
        } else {
            int eps = length / 8;
            int mid = lo + length / 2;
            int m1 = median3(a, lo, lo + eps, lo + eps + eps);
            int m2 = median3(a, mid - eps, mid, mid + eps);
            int m3 = median3(a, hi - eps - eps, hi - eps, hi);
            int ninther = median3(a, m1, m2, m3);
            exch(a, ninther, lo);
        }

        int i = lo, j = hi + 1;
        int p = lo, q = hi + 1;
        Comparable v = a[lo];
        while (true) {
            while (less(a[++i], v)) {
                if (i == hi) {
                    break;
                }
            }
            while (less(v, a[--j])) {
                if (j == lo) {
                    break;
                }
            }

            // pointers cross
            if (i == j && equal(a[i], v)) {
                exch(a, ++p, i);
            }
            if (i >= j) {
                break;
            }

            exch(a, i, j);
            if (equal(a[i], v)) {
                exch(a, ++p, i);
            }
            if (equal(a[j], v)) {
                exch(a, --q, j);
            }
        }


        i = j + 1;
        for (int k = lo; k <= p; k++) {
            exch(a, k, j--);
        }
        for (int k = hi; k >= q; k--) {
            exch(a, k, i++);
        }
        show(a);
        sort(a, lo, j);
        sort(a, i, hi);

    }


    //如果v<w,返回true
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static boolean equal(Comparable v, Comparable w) {
        return v.compareTo(w) == 0;
    }

    private static int median3(Comparable[] a, int i, int j, int k) {
        if ((less(a[i], a[j]) && less(a[j], a[k])) || less(a[k], a[j]) && less(a[j], a[i])) {
            return j;
        } else if ((less(a[j], a[i]) && less(a[i], a[k])) || less(a[k], a[i]) && less(a[i], a[j])) {
            return i;
        } else if ((less(a[i], a[k]) && less(a[k], a[j])) || less(a[j], a[k]) && less(a[k], a[i])) {
            return k;
        } else if (equal(a[i], a[j])) {
            return i;
        } else if (equal(a[i], a[k])) {
            return i;
        } else if (equal(a[j], a[k])) {
            return k;
        }
        //三个都相等
        else {
            return i;
        }
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
        String[] a = {"S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E", "T", "Q", "H", "P", "Z", "M", "L", "I", "F", "A", "U", "Y", "R", "B", "C", "X", "G", "D", "J"};
        sort(a);
        assert isSorted(a) : "wrong";
        show(a);
    }
}
