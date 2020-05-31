import java.util.Scanner;

/*
1. 单位参考：壹、贰、叁、肆、伍、陆、柒、捌、玖、拾、佰、仟、万、亿、元、角、分、零、整；
2. 当数据为整数值或者小数部分为零时，需要添加XX元整。如：10表示为壹十元整， 200.00表示为贰百元整；

["200.00","201.15","1015","200001010200"]

["贰百元整", "贰百零壹元壹角伍分", "壹千零壹十伍元整", "贰千亿零壹百零壹万零贰百元整"]

 */


/**
 * @author humingk
 */
public class Main_meituan {

    public String getNum(char a) {
        switch (a) {
            case '1':
                return "壹";
            case '2':
                return "贰";
            case '3':
                return "叁";
            case '4':
                return "肆";
            case '5':
                return "伍";
            case '6':
                return "陆";
            case '7':
                return "柒";
            case '8':
                return "捌";
            case '9':
                return "玖";
            default:
                return "";
        }
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String lines = sc.nextLine();
        if (lines == null || "".equals(lines.trim())) {
            System.out.println("[]");
            return;
        }
        Main_meituan m = new Main_meituan();
        String[] nums = lines.split("\",\"");
        if (nums.length >= 2) {
            nums[0] = nums[0].split("\"")[1];
            nums[nums.length - 1] = nums[nums.length - 1].split("\"")[0];
        } else if (nums.length == 1) {
            nums[0] = nums[0].split("\"")[1];
        }
        String[] results = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            String[] now = nums[i].split(".");
            String left = "";
            String right = "";
            if (now.length == 2) {
                left = nums[i].split(".")[0];
                right = nums[i].split(".")[1];
            } else {
                left = nums[i];
            }
            String result = "";
            int l = left.length(), r = 1;
            if (left.charAt(left.length() - l) == '0') {
                l--;
            }
            while (l >= 1) {
                if (left.charAt(left.length() - l) == '0') {
                    result += "零";
                    l--;
                    while (l >= 1 && left.charAt(left.length() - l) == '0') {
                        l--;
                    }
                } else if (left.charAt(left.length() - 1) >= '1' && left.charAt(left.length() - 1) <= '9') {
                    result += m.getNum(left.charAt(left.length() - 1));
                    l--;
                } else if (l == 13) {
                    result += "万";
                    l--;
                } else if (l == 12 || l == 8 || l == 4) {
                    result += "仟";
                    l--;
                } else if (l == 11 || l == 7 || l == 3) {
                    result += "佰";
                    l--;
                } else if (l == 10 || l == 6 || l == 2) {
                    result += "拾";
                    l--;
                } else if (l == 9) {
                    result += "亿";
                    l--;
                } else if (l == 5) {
                    result += "万";
                    l--;
                } else if (l == 1) {
                    result += "元";
                    l--;
                }
            }
            results[i] = result;
        }
        for (int i = 0; i < results.length; i++) {
            System.out.println(results[i]);
        }


    }
}
