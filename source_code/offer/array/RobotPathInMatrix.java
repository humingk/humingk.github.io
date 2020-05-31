package array;

/**
 * 机器人在矩阵中移动,不能进入数位之和大于K的格子
 *
 * @author humingk
 */
public class RobotPathInMatrix<Value> {
    // ------------------------------------------------------------------------------------------
    // start
    // 1.测试用例方法

    public void test() {
        System.out.println("1.多行多列");
        int s1=solution(18, 100, 100);
        System.out.println();
        System.out.println(s1);
        System.out.println("2.一行多列");
        int s2=solution(18, 1, 100);
        System.out.println();
        System.out.println(s2);
        System.out.println("3.多行一列");
        int s3=solution(18, 100, 1);
        System.out.println();
        System.out.println(s3);
        System.out.println("4.一行一列");
        int s4=solution(18, 1, 1);
        System.out.println();
        System.out.println(s4);
        System.out.println("5.threshold 为负数");
        int s5=solution(-18, 10, 10);
        System.out.println();
        System.out.println(s5);
    }

    // 2.算法题方法

    /**
     * @param threshold 数位和最大值
     * @param rows
     * @param cols
     * @return
     */
    public int solution(int threshold, int rows, int cols) {
        if (rows == 0 || cols == 0 || threshold <= 0) {
            return 0;
        }
        /**
         * true 走过
         * false 没走过 (默认)
         */
        boolean[] flags = new boolean[rows * cols];
        return recall(threshold, rows, cols, 0, 0, flags);
    }

    /**
     * 回溯
     *
     * @param threshold
     * @param rows
     * @param cols
     * @param x
     * @param y
     * @param flags
     * @return
     */
    private int recall(int threshold, int rows, int cols, int x, int y, boolean[] flags) {
        // 从当前格子出发能到达的格子数
        int count = 0;
        // x与y的数位之和
        int sum = 0,xx=x,yy=y;
        while (xx > 0) {
            sum += xx % 10;
            xx /= 10;
        }
        while (yy > 0) {
            sum += yy % 10;
            yy /= 10;
        }
        // 当前格子不超范围，没有走过以及数位之和不超过threshold，即能经过此点
        if (x >= 0 && x < rows && y >= 0 && y < cols
                && !flags[x * cols + y]
                && sum <= threshold) {
            flags[x * cols + y] = true;
            System.out.print(x + "-" + y + " -> ");
            count = 1
                    + recall(threshold, rows, cols, x + 1, y, flags)
                    + recall(threshold, rows, cols, x, y + 1, flags)
                    + recall(threshold, rows, cols, x - 1, y, flags)
                    + recall(threshold, rows, cols, x, y - 1, flags);
        } else {
            System.out.print(" | ");
        }
        return count;
    }
    // end
    // ------------------------------------------------------------------------------------------

    public static void main(String[] args) {
        RobotPathInMatrix stringBase = new RobotPathInMatrix();
        stringBase.test();
    }
}