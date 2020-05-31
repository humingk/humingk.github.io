package array;

import java.util.ArrayList;

/**
 * 顺时针打印矩阵
 *
 * @author humingk
 */
public class ClockWisePrintArrayMatrix {
    // ------------------------------------------------------------------------------------------
    // start
    // 1.测试用例方法

    public void test() {
        int[][] test1 = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };
        printMatrix(test1);
        printArraylist(solution(test1));
        System.out.println("-----------------");

        int[][] test2 = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16},
                {17, 18, 19, 20}
        };
        printMatrix(test2);
        printArraylist(solution(test2));
        System.out.println("-----------------");

        int[][] test3 = {
                {1, 2, 3, 4}
        };
        printMatrix(test3);
        printArraylist(solution(test3));
        System.out.println("-----------------");

        int[][] test4 = {
                {1},
                {5},
                {9},
                {13}
        };
        printMatrix(test4);
        printArraylist(solution(test4));
        System.out.println("-----------------");

        int[][] test5 = {
                {13}
        };
        printMatrix(test5);
        printArraylist(solution(test5));
        System.out.println("-----------------");

        int[][] test6 = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16},
        };
        printMatrix(test6);
        printArraylist(solution(test6));
        System.out.println("-----------------");

        int[][] test7 = {
                {1, 2, 3, 4, 31},
                {5, 6, 7, 8, 32},
                {9, 10, 11, 12, 33},
                {13, 14, 15, 16, 34},
                {17, 18, 19, 20, 35}
        };
        printMatrix(test7);
        printArraylist(solution(test7));
        System.out.println("-----------------");
    }

    // 2.算法题方法

    public ArrayList<Integer> solution(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return null;
        }
        ArrayList<Integer> result = new ArrayList<>();
        int start = 0;
        while (start * 2 < matrix.length && start * 2 < matrix[0].length) {
            int endX = matrix[0].length - start - 1;
            int endY = matrix.length - start - 1;
            // 第一步 向右 条件: start < endX
            for (int i = start; i <= endX; i++) {
                result.add(matrix[start][i]);
            }
            // 第二步 向下 条件：start < endY
            for (int i = start + 1; i <= endY; i++) {
                result.add(matrix[i][endX]);
            }
            // 第三步 向左 条件: 已执行第二步
            // 如果没有执行第二步（向下），则说明当前只剩下一行，不需要再向左以避免重复
            if (start < endY) {
                for (int i = endX - 1; i >= start; i--) {
                    result.add(matrix[endY][i]);
                }
            }
            // 第四步 向上 条件：已执行第一步 and 第二步不止一次移动
            // 如果没有执行第一步（向右）， 则说明当前只有一列，不需要再向上以避免重复
            // 如果第二步（向下）没执行或只执行了一次的话，说明当前只有一列/两列，不需要再向上以避免重复
            if (start < endX && start < endY - 1) {
                for (int i = endY - 1; i >= start + 1; i--) {
                    result.add(matrix[i][start]);
                }
            }
            start++;
        }
        return result;
    }
    // end
    // ------------------------------------------------------------------------------------------

    private void printArray(int[] test) {
        for (int i = 0; i < test.length; i++) {
            System.out.print(test[i] + " ");
        }
        System.out.println();
    }

    private void printArraylist(ArrayList<Integer> test) {
        for (int i = 0; i < test.size(); i++) {
            System.out.print(test.get(i) + " ");
        }
        System.out.println();
    }

    private void printMatrix(int[][] test) {
        for (int i = 0; i < test.length; i++) {
            for (int j = 0; j < test[i].length; j++) {
                System.out.print(test[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        new ClockWisePrintArrayMatrix().test();
    }
}