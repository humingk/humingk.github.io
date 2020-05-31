package others;

import static java.lang.Math.pow;

/**
 * 减绳子 动态规划 贪婪算法
 *
 * @author humingk
 */
public class CuttingRope<Value> {
    // ------------------------------------------------------------------------------------------
    // start
    // 1.测试用例方法

    public void test() {
        System.out.println("动态规划-贪婪");
        for (int i = 0; i < 100; i++) {
            System.out.println("长度为" + i + ":");
            System.out.println(solutionDynamic(i) + " - " + solutionGreedy(i));
        }
    }

    // 2.算法题方法

    /**
     * 动态规划
     *
     * @return
     */
    public long solutionDynamic(int length) {
        if (length <= 1) {
            return 0;
        } else if (length == 2) {
            return 1;
        } else if (length == 3) {
            return 2;
        }

        long[] f = new long[length + 1];
        f[1] = 1;
        f[2] = 2;
        f[3] = 3;

        long max = 0;
        for (int i = 4; i <= length; i++) {
            max = 0;
            for (int j = 1; j <= i / 2; j++) {
                // 总长度为i 第一段为j，第二段为i-j
                long temp = f[j] * f[i - j];
                if (max < temp) {
                    max = temp;
                }
            }
            f[i] = max;
        }
        return max;
    }

    /**
     * 贪婪算法
     *
     * @return
     */
    public long solutionGreedy(int length) {
        if (length <= 1) {
            return 0;
        } else if (length == 2) {
            return 1;
        } else if (length == 3) {
            return 2;
        }

        // 剪成长度为3的个数
        int countOf3 = length / 3;
        // 剪成长度为2的个数
        int countOf2 = 0;

        // 如果剩下的长度为1，则将最后的长度4剪成两个长度为2
        if (length - countOf3 * 3 == 1) {
            countOf3--;
            countOf2 = 2;
        }
        // 剩下的长度为2
        else if (length - countOf3 * 3 == 2) {
            countOf2 = 1;
        }
        return (long) (pow(3, countOf3) * (long) pow(2, countOf2));
    }
    // end
    // ------------------------------------------------------------------------------------------

    public static void main(String[] args) {
        new CuttingRope().test();
    }
}