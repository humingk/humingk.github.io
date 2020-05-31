package others;


/**
 * 打印1到最大的n位数
 *
 * @author humingk
 */
public class PringNumber {
    // ------------------------------------------------------------------------------------------
    // start
    // 1.测试用例方法

    public void test() {
//        solution1(20);
        solution2(20);
    }

    // 2.算法题方法

    /**
     * 方法1：高精度++
     *
     * @param n
     */
    public void solution1(int n) {
        if (n <= 0) {
            return;
        }
        char[] s = new char[n];
        for (int i = 0; i < s.length - 2; i++) {
            // debug
            s[i] = '9';
        }
        s[s.length - 1] = '0';
        s[s.length - 2] = '0';
        while (!add(s)) {
            print(s);
        }
    }

    /**
     * 高精度++
     *
     * @param s
     * @return
     */
    private boolean add(char[] s) {
        // 是否进位
        boolean isOverflow = false;
        // 进位值
        int overflow = 0;
        for (int i = s.length - 1; i >= 0; i--) {
            // 当前位数值(加上进位值，默认为0)
            int now = s[i] - '0' + overflow;
            // 最末位++
            if (i == s.length - 1) {
                now++;
            }
            // 当前位没有产生进位
            if (now < 10) {
                s[i] = (char) ('0' + now);
                break;
            } else {
                // 最高位溢出
                if (i == 0) {
                    isOverflow = true;
                } else {
                    now -= 10;
                    overflow = 1;
                    s[i] = (char) ('0' + now);
                }
            }
        }
        return isOverflow;

    }

    /**
     * 方法2：数字递归排列
     *
     * @param n
     */
    public void solution2(int n) {
        if (n <= 0) {
            return;
        }
        char[] s = new char[n];
        for (int i = 0; i < s.length; i++) {
            // debug
            s[i] = '9';
        }

        // debug
        for (int i = 0; i < 10; i++) {
            s[i] = (char) (i + '0');
            recursion(s, 0);
        }
    }

    /**
     * 方法2用到的递归
     *
     * @param s
     * @param index
     */
    private void recursion(char[] s, int index) {
        if (index == s.length - 1) {
            print(s);
            return;
        }
        for (int i = 0; i < 10; i++) {
            s[index + 1] = (char) (i + '0');
            recursion(s, index + 1);
        }
    }

    /**
     * 打印
     *
     * @param s
     */
    private void print(char[] s) {
        boolean flag = true;
        for (int i = 0; i < s.length; i++) {
            if (flag && s[i] != '0') {
                flag = false;
            }
            if (!flag) {
                System.out.print(s[i]);
            }
        }
        System.out.println();
    }
    // end
    // ------------------------------------------------------------------------------------------

    public static void main(String[] args) {
        new PringNumber().test();
    }
}