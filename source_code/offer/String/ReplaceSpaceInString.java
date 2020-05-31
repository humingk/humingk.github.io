package String;

/**
 * 替换字符串中的空格
 *
 * @author humingk
 */
public class ReplaceSpaceInString {
    private final static char SPACE=' ';

    /**
     * 将空格替换为 %20
     *
     * @param str
     * @return
     */
    public static String replace(StringBuffer str) {
        if (str == null) {
            return null;
        }
        if (str.length() == 1) {
            if (str.charAt(0) == SPACE) {
                return "%20";
            } else {
                return str.toString();
            }
        }
        int numberOfSpace = 0;
        for (int i = 0; i <= str.length() - 1; i++) {
            if (str.charAt(i) == SPACE) {
                numberOfSpace++;
            }
        }
        str.setLength(str.length() + numberOfSpace * 2);
        int i = str.length() - 1;
        int j = str.length() - numberOfSpace * 2 - 1;
        while (i >= 0 && j >= 0) {
            if (str.charAt(j) == SPACE) {
                str.setCharAt(i, '0');
                str.setCharAt(i - 1, '2');
                str.setCharAt(i - 2, '%');
                i -= 3;
            } else {
                str.setCharAt(i, str.charAt(j));
                i--;
            }
            j--;
        }
        return str.toString();
    }

    public static void main(String[] args) {
        System.out.println("测试是否将空格替换为 %20 ");

        System.out.println("1.空格位于最前面");
        StringBuffer str1 = new StringBuffer(" stupidisasstupiddoes");
        System.out.println(replace(str1));

        System.out.println("2.空格位于中间");
        StringBuffer str2 = new StringBuffer("stupid is as stupid does");
        System.out.println(replace(str2));

        System.out.println("3.空格位于最后面");
        StringBuffer str3 = new StringBuffer("stupidisasstupiddoes ");
        System.out.println(replace(str3));

        System.out.println("4.多个连续的空格");
        StringBuffer str4 = new StringBuffer("stupid  is as  stupid does");
        System.out.println(replace(str4));

        System.out.println("5.没有空格");
        StringBuffer str5 = new StringBuffer("stupidisasstupiddoes");
        System.out.println(replace(str5));

        System.out.println("6.输入为空字符串");
        StringBuffer str6 = new StringBuffer("");
        System.out.println(replace(str6));

        System.out.println("7.输入为空");
        StringBuffer str7 = new StringBuffer();
        System.out.println(replace(str7));

        System.out.println("8.输入为连续的空格");
        StringBuffer str8 = new StringBuffer("   ");
        System.out.println(replace(str8));

    }
}