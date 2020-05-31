package array;

/**
 * 二维数组是否包含某数
 *
 * @author humingk
 */
public class SearchNumberInDoubleArray {

    /**
     * @param array
     * @param target
     * @return true    有此数
     * false   无此数
     */
    public static boolean findNumberInDoubleArray(int[][] array, int target) {
        if(array==null || array.length==0 || array[0].length==0){
            return false;
        }
        int i = 0,j = array.length - 1;
        while (i < array.length && j >= 0) {
            if (array[i][j] == target) {
                return true;
            }
            // 第j列第i个数比 target 大，第j列下面的数均大于 target
            else if (array[i][j] > target) {
                // 在第i行中跳至第jTemp列的下一列
                j--;
                continue;
            }
            // 第j列第i个数比 target 小，target 可能是第j列下面的数
            else {
                // 在jTemp列中跳至第i行的下一行
                i++;
                continue;
            }
        }
        return false;
    }

    public static void test(int[][] array, int target) {
        if (findNumberInDoubleArray(array, target)) {
            System.out.println("有此数" + target);
        } else {
            System.out.println("无此数" + target);
        }
    }

    public static void main(String[] args) {
        System.out.println("测试该二维数组是否含有该整数");
        System.out.println("1.含有该整数");
        int[][] array1 = {{1, 2, 8, 9}, {2, 4, 9, 12}, {4, 7, 10, 13}, {6, 8, 11, 15}};
        int target1 = 10;
        test(array1, target1);

        System.out.println("2.不含有该整数");
        int[][] array2 = {{1, 2, 8, 9}, {2, 4, 9, 12}, {4, 7, 10, 13}, {6, 8, 11, 15}};
        int target2 = 20;
        test(array2, target2);

        System.out.println("3.该整数是最小的");
        int[][] array3 = {{1, 2, 8, 9}, {2, 4, 9, 12}, {4, 7, 10, 13}, {6, 8, 11, 15}};
        int target3 = 1;
        test(array3, target3);

        System.out.println("4.该整数是最大的");
        int[][] array4 = {{1, 2, 8, 9}, {2, 4, 9, 12}, {4, 7, 10, 13}, {6, 8, 11, 15}};
        int target4 = 15;
        test(array4, target4);

        System.out.println("5.该整数刚好比最小数小");
        int[][] array5 = {{1, 2, 8, 9}, {2, 4, 9, 12}, {4, 7, 10, 13}, {6, 8, 11, 15}};
        int target5 = 0;
        test(array5, target5);

        System.out.println("6.该整数刚好比最大数大");
        int[][] array6 = {{1, 2, 8, 9}, {2, 4, 9, 12}, {4, 7, 10, 13}, {6, 8, 11, 15}};
        int target6 = 16;
        test(array6, target6);

        System.out.println("7.无效输入");
        int[][] array7 = {{}};
        int target7 = 10;
        test(array7, target7);
    }
}