package array;

/**
 * 调整数组顺序，奇数位于偶数前面
 * 另建数组，先找奇数再找偶数，保证稳定性
 *
 * @author humingk
 */
public class ChangeOrderByCod2<Value> {
// ------------------------------------------------------------------------------------------
    // start
    // 1.测试用例方法

    private int[][] nums = {
            null,
            {},
            {5},
            {1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
            {1, 3, 5, 7, 9, 2, 4, 6, 8, 10},
            {2, 4, 6, 8, 10, 1, 3, 5, 7, 9},
            {3, 3, 3, 3, 3, 3, 3, 3, 3, 3},
            {8, 8, 8, 8, 8, 8, 8, 8, 8, 8}
    };

    public void test() {
        for (int i = 0; i < nums.length; i++) {
            solution(nums[i]);
            if (i == 0) {
                continue;
            }
            for (int j = 0; j < nums[i].length; j++) {
                System.out.print(nums[i][j] + " ");
            }
            System.out.println();
        }
    }

    // 2.算法题方法

    public void solution(int[] array) {
        if (array == null || array.length == 0) {
            return;
        }
        int[] aux = new int[array.length];
        int j = 0;
        for (int i = 0; i < array.length; i++) {
            if (isCondiction(array[i])) {
                aux[j++] = array[i];
            }
        }
        for (int i = 0; i < array.length; i++) {
            if (!isCondiction(array[i])) {
                aux[j++] = array[i];
            }
        }
        for (int i = 0; i < array.length; i++) {
            array[i] = aux[i];
        }
    }

    /**
     * 奇数返回true
     * 偶数返回false
     *
     * @param now
     * @return
     */
    private boolean isCondiction(int now) {
        return ((now & 1) == 1);
    }

    // end
    // ------------------------------------------------------------------------------------------

    public static void main(String[] args) {
        new ChangeOrderByCod2().test();
    }
}