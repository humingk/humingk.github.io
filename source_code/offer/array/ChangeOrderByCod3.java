package array;

/**
 * 调整数组顺序，奇数位于偶数前面
 * 类似插入排序，保证稳定性且空间复杂度为O(1)
 *
 * @author humingk
 */
public class ChangeOrderByCod3<Value> {
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
        int temp;
        // 从第二位开始遍历找奇数
        for (int i = 1; i < array.length; i++) {
            // 奇数
            if (isCondiction(array[i])) {
                // 暂存当前奇数
                temp = array[i];
                // 遍历i之前的所有偶数
                int j = i;
                while (j >= 1 && !isCondiction(array[j - 1])) {
                    // 将i之前所有的偶数后移一位
                    array[j] = array[j - 1];
                    j--;
                }
                // 当前奇数前移到所有偶数前面
                array[j] = temp;
            }
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
        new ChangeOrderByCod3().test();
    }
}