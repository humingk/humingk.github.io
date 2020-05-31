package array;

import java.util.ArrayList;

/**
 * @author humingk
 */
public class ArrayBase {
    // ------------------------------------------------------------------------------------------
    // start
    // 1.测试用例方法

    public void test() {

    }

    // 2.算法题方法

    public String solution() {

        return null;
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
        new ArrayBase().test();
    }
}