package array;

/**
 * 找出旋转数组中的最小值
 *
 * @author humingk
 */
public class FindMinOfRotateArray<Value> {
    // ------------------------------------------------------------------------------------------
    // start
    // 1.测试用例方法

    public void test() {
        System.out.println("1.没有重复数字:" + " 3,4,5,6,7,8,9,1,2");
        System.out.println(solution(new int[]{3, 4, 5, 6, 7, 8, 9, 1, 2}));
        System.out.println("1.2 没有重复数字且有序:" + " 1,2,3,4,5,6,7,8,9");
        System.out.println(solution(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}));
        System.out.println("2.有重复数字:" + " 3,4,4,6,7,7,9,1,1");
        System.out.println(solution(new int[]{3, 4, 4, 6, 7, 7, 9, 1, 1}));
        System.out.println("2.2 有重复数字且有序:" + " 1,1,2,3,4,4,6,6,6,9");
        System.out.println(solution(new int[]{1, 1, 2, 3, 4, 4, 6, 6, 6, 9}));
        System.out.println("3.只有一个数字:" + "1,1,1,1,1,1,1,1,1,1");
        System.out.println(solution(new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1}));
        System.out.println("4.输入为空:");
        System.out.println(solution(null));
    }

    // 2.算法题方法

    public int solution(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        int start = 0, end = array.length - 1, middle = (start+end) >> 1;
        while (start <= end) {
            // 有序数组
            if (array[start] < array[end]) {
                return array[start];
            }
            // 最小值
            else if (start == middle) {
                return array[middle + 1];
            }
            // 中间值位于左边递增数组，最小值在middle和end之间，不包括middle
            else if (array[middle] > array[start]) {
                start = middle;
            }
            // 中间值位于右边递增数组，最小值在start和middle之间，包括middle
            else if (array[middle] < array[start]) {
                end = middle;
            }
            // 中间值与start相等，最小值为start(middle)或者end
            else if (array[middle] == array[start]) {
                if(array[start]>array[end]){
                    return array[end];
                }else{
                    return array[start];
                }
            }
            middle = start + (end - start) / 2;
        }
        return 0;
    }
    // end
    // ------------------------------------------------------------------------------------------

    public static void main(String[] args) {
        FindMinOfRotateArray stringBase = new FindMinOfRotateArray();
        stringBase.test();
    }
}