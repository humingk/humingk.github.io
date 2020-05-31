package String;

/**
 * 正则表达式匹配
 *
 * @author humingk
 */
public class RegexString {
    // ------------------------------------------------------------------------------------------
    // start
    // 1.测试用例方法

    public void test() {
//        test2("test01", null, "", true);
//        test2("test01", "", null, true);
//        test2("test01", null, null, true);

        test2("test01", "", "", true);
        test2("test02", "", ".*", true);
        test2("test03", "", ".", false);
        test2("test04", "", "c*", true);
        test2("test05", "a", ".*", true);
        test2("test06", "a", "a.", false);
        test2("test07", "a", "", false);
        test2("test08", "a", ".", true);
        test2("test09", "a", "ab*", true);
        test2("test10", "a", "ab*a", false);
        test2("test11", "aa", "aa", true);
        test2("test12", "aa", "a*", true);
        test2("test13", "aa", ".*", true);
        test2("test14", "aa", ".", false);
        test2("test15", "ab", ".*", true);
        test2("test16", "ab", ".*", true);
        test2("test17", "aaa", "aa*", true);
        test2("test18", "aaa", "aa.a", false);
        test2("test19", "aaa", "a.a", true);
        test2("test20", "aaa", ".a", false);
        test2("test21", "aaa", "a*a", true);
        test2("test22", "aaa", "ab*a", false);
        test2("test23", "aaa", "ab*ac*a", true);
        test2("test24", "aaa", "ab*a*c*a", true);
        test2("test25", "aaa", ".*", true);
        test2("test26", "aab", "c*a*b", true);
        test2("test27", "aaca", "ab*a*c*a", true);
        test2("test28", "aaba", "ab*a*c*a", false);
        test2("test29", "bbbba", ".*a*a", true);
        test2("test30", "bcbbabab", ".*a*a", false);
    }

    private void test2(String name, String str_string, String pattern_string, boolean isMatch) {
        char[] str = str_string.toCharArray();
        char[] pattern = pattern_string.toCharArray();
        System.out.println(str_string + " " + pattern_string + "  " + isMatch + " " + solution(str, pattern));
    }

    // 2.算法题方法

    public boolean solution(char[] str, char[] pattern) {
        if (str == null || pattern == null) {
            return false;
        }
        return recursion(str, 0, pattern, 0);
    }

    private boolean recursion(char[] str, int i, char[] pattern, int j) {
        // 模式遍历完
        if (j == pattern.length) {
            // 若字符串也遍历完，返回true
            return i==str.length;
        }
        //A, pattern[j+1]='*'，遇到"x*"
        if (j < pattern.length - 1 && pattern[j + 1] == '*') {
            if (i != str.length && (str[i] == pattern[j] || pattern[j] == '.')) {
                // (1),str[i]=pattern[j],即： a... -> a*...
                return recursion(str, i + 1, pattern, j) ||
                        // *取0，a*不匹配任何字符,比如a... -> a*a*...
                        recursion(str, i, pattern, j + 2);
            }
            //(2), str[i]!=pattern[j],即： b... -> a*...
            else {
                return recursion(str, i, pattern, j + 2);
            }
        }
        //B, pattern[j+1]!='*',遇到"x",且第一个字符相等
        else if (i != str.length &&
                (str[i] == pattern[j] || pattern[j] == '.')) {
            return recursion(str, i + 1, pattern, j + 1);
        }
        return false;
    }

    // end
    // ------------------------------------------------------------------------------------------

    public static void main(String[] args) {
        new RegexString().test();
    }
}