package others;

/**
 * 求double的int次方
 *
 * @author humingk
 */
public class power {
    // ------------------------------------------------------------------------------------------
    // start
    // 1.测试用例方法

    public void test() {
        for (int i = -10; i <= 10; i++) {
            for (double j = -99.99; j <= 99.99; j += 11.11) {
                System.out.println(j + "^" + i + " => " + solution(j, i));
            }
        }
    }

    // 2.算法题方法

    /**
     * 若exp为1，则:循环1：奇数1=> result=base^1, base=base^2,exp=0
     * 若exp为3，则:循环1：奇数3=> result=base^1, base=base^2,exp=1;循环2: 奇数1=> result=base^3,base=base^3,exp=0
     * 若exp为5，则:循环1：奇数5=> result=base^1, base=base^2,exp=3...
     *
     * @param base
     * @param exponent
     * @return
     */
    public double solution(double base, int exponent) {
        int exp;
        if (exponent < 0) {
            exp = -exponent;
        } else {
            exp = exponent;
        }
        double result = 1.0;
        while (exp != 0) {
            // 若为奇数，即末尾为1，则1&1为1，不等于0
            if ((exp & 1) != 0) {
                result *= base;
            }
            base *= base;
            exp >>= 1;
        }
        // 是否求倒数
        return exponent < 0 ? 1.0 / result : result;
    }

    // end
    // ------------------------------------------------------------------------------------------

    public static void main(String[] args) {
        new power().test();
    }
}