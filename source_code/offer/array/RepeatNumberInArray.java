package array;

/**
 * 数组中重复的数字
 *
 * @author humingk
 */
public class RepeatNumberInArray {
    /**
     * 1.另建哈希表
     * 共n个数，在1～n-1之间
     *
     * @param numbers
     * @param length
     * @param duplication
     * @return
     */
    public boolean findRepeatNumberInArray1(int[] numbers, int length, int[] duplication) {
        if (numbers == null || length <= 0) {
            return false;
        }
        // 哈希表存放哈希表数组下标在numbers数组中出现的次数
        int[] counts = new int[length];
        for (int i = 0; i < length; i++) {
            if (numbers[i] < 0 || numbers[i] > length - 1) {
                return false;
            }
            // 标记次数
            counts[numbers[i]]++;
        }
        for (int i = 0; i < length; i++) {
            if (counts[numbers[i]] > 1) {
                duplication[0] = numbers[i];
                return true;
            }
        }
        return false;
    }

    /**
     * 2.自身哈希表
     * 共n个数，在0～n-1之间
     *
     * @param numbers
     * @param length
     * @param duplication
     * @return true    有重复
     * false   没有重复
     */
    public boolean findRepeatNumberInArray2(int[] numbers, int length, int[] duplication) {
        if (numbers == null || length <= 0) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (numbers[i] < 0 || numbers[i] > length - 1) {
                return false;
            }
        }
        int m;
        for (int i = 0; i < length; i++) {
            if (numbers[i] != i) {
                m = numbers[i];
                // 出现了重复的数字
                if (numbers[m] == m) {
                    duplication[0] = m;
                    return true;
                }
                // 交换
                else {
                    numbers[i] = numbers[m];
                    numbers[m] = m;
                }
            }
        }
        return false;
    }


    /**
     * 3.二分法
     * 共n+1个数
     * 位于1～n之间
     *
     * @param nums
     * @return
     */
    public int findRepeatNumberInArray3(int[] nums) {
        if (nums == null || nums.length <= 0) {
            return -1;
        }
        int start = 1, end = nums.length - 1, middle, count;
        while (start < end) {
            middle = (start + end) >> 1;
            // start-middle之间的数字（数组下标数字）在数组（数组的值）中出现的次数
            count = 0;
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] <= middle && nums[i] >= start) {
                    count++;
                }
            }
            // start-middle下标值在数组中出现次数大于start-middle的数字个数，说明在数组start-middle之间存在重复值
            if (count > (middle - start + 1)) {
                end = middle;
            }
            // 否则在数组middle+1 - end之间存在重复值
            else {
                start = middle + 1;
            }
        }
        return start;
    }

    public void test1(int[] numbers, int[] duplication) {
        if (findRepeatNumberInArray2(numbers, numbers.length, duplication)) {
            System.out.println(duplication[0]);
        } else {
            System.out.println("repeat none");
        }
    }

    public void test3(int[] numbers) {
        System.out.println(findRepeatNumberInArray3(numbers));
    }

    public static void main(String[] args) {
        RepeatNumberInArray r = new RepeatNumberInArray();
        System.out.println("哈希表2========");
        int[] numbersTwo1 = {3, 1, 2, 4, 7, 5, 6, 7, 0, 9};
        System.out.println("没有重复");
        r.test1(numbersTwo1, new int[numbersTwo1.length]);
        System.out.println("有一个重复");
        int[] numbersTwo2 = {3, 1, 2, 4, 7, 7, 6, 8, 0, 9};
        r.test1(numbersTwo2, new int[numbersTwo2.length]);
        System.out.println("无效输入");
        int[] numbersTwo3 = {};
        r.test1(numbersTwo3, new int[numbersTwo3.length]);
        System.out.println("包含 0~n-1 之外的数字");
        int[] numbersTwo4 = {3, 1, 2, 24, 7, 5, 13, 7, 0, 9};
        r.test1(numbersTwo4, new int[numbersTwo4.length]);

        System.out.println("二分法=========");
        System.out.println("有两个重复");
        int[] numbersThree1 = {3, 1, 2, 4, 7, 7, 6, 8, 3, 9, 5};
        r.test3(numbersThree1);
        System.out.println("有一个重复");
        int[] numbersThree2 = {3, 1, 2, 4, 7, 7, 6, 8, 10, 9, 5};
        r.test3(numbersThree2);
        System.out.println("无效输入");
        int[] numbersThree3 = {};
        r.test3(numbersThree3);

    }
}