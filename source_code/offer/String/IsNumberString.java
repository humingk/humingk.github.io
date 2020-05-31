package String;

/**
 * @author humingk
 */
public class IsNumberString {
    // ------------------------------------------------------------------------------------------
    // start
    // 1.测试用例方法

    public void test() {
        test2("Test1", "100", true);
        test2("Test2", "123.45e+6", true);
        test2("Test3", "+500", true);
        test2("Test4", "5e2", true);
        test2("Test5", "3.1416", true);
        test2("Test6", "600.", true);
        test2("Test7", "-.123", true);
        test2("Test8", "-1E-16", true);
        test2("Test9", "1.79769313486232E+308", true);

        test2("Test10", "12e", false);
        test2("Test11", "1a3.14", false);
        test2("Test12", "1+23", false);
        test2("Test13", "1.2.3", false);
        test2("Test14", "+-5", false);
        test2("Test15", "12e+5.4", false);
        test2("Test16", ".", false);
        test2("Test17", ".e1", false);
        test2("Test18", "e1", false);
        test2("Test19", "+.", false);
        test2("Test20", "", false);
        test2("Test21", null, false);
    }

    private void test2(String name, String str_string, boolean isNumber) {
        i = 0;
        char[] str;
        if (str_string == null) {
            str = null;
        } else {
            str = str_string.toCharArray();
        }
        System.out.println(str_string + " " + isNumber + " " + solution(str));
    }

    // 2.算法题方法

    private int i = 0;

    public boolean solution(char[] str) {
        if (str == null || str.length == 0) {
            return false;
        }
        // A[.[B][e|EC]] 中的 A
        boolean isNumber = isNum(str);
        // A[.[B][e|EC]] 中的 .
        if (i < str.length && str[i] == '.') {
            i++;
            // A[.[B][e|EC]] 中的 B 或 .[B][e|EC] 中的 B
            // eg: 符合要求的 .233 | 233. | 233.233
            isNumber = isUnsignedNum(str) || isNumber;
        }
        // A[.[B][e|EC]] 中的 e|E
        if (i < str.length && (str[i] == 'e' || str[i] == 'E')) {
            i++;
            // A[.[B][e|EC]] 中的 C
            // eg: 不符合要求的 e233 | .e233 | 233.e233 | 233e | 233e+ | 233e+233.233
            isNumber = isNumber && isNum(str);
        }
        return isNumber && i == str.length;
    }

    private boolean isNum(char[] str) {
        if (i < str.length && (str[i] == '+' || str[i] == '-')) {
            i++;
        }
        return isUnsignedNum(str);
    }

    private boolean isUnsignedNum(char[] str) {
        int start = i;
        while (i < str.length && str[i] >= '0' && str[i] <= '9') {
            i++;
        }
        return i > start;
    }

    // end
    // ------------------------------------------------------------------------------------------

    public static void main(String[] args) {
        new IsNumberString().test();
    }
}