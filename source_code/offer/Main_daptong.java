import java.util.Scanner;

/**
 * @author humingk
 */
public class Main_daptong {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine().trim();
        String[] origin = line.split(" ");
        String[] result = new String[origin.length + 4];
        int l = origin.length;
        if (l >= 64) {
            l %= 64;
        }
        result[0] = Integer.toHexString(l + 128);
        result[1] = "10";
        result[2] = "F1";
        for (int i = 0; i < origin.length; i++) {
            result[3 + i] = origin[i];
        }
        int check1 = 0, check2 = 0;
        for (int i = 0; i < result.length - 1; i++) {
            check1 += Integer.parseInt(String.valueOf(result[i].charAt(0)), 16);
            if (check1 >= 16) {
                check1 -= 16;
            }
            check2 += Integer.parseInt(String.valueOf(result[i].charAt(1)), 16);
            if (check2 >= 16) {
                check2 -= 16;
            }
        }
        result[result.length - 1] = (Integer.toHexString(check1) + Integer.toHexString(check2)).toUpperCase();
        for (int i = 0; i < result.length - 1; i++) {
            System.out.print(result[i] + " ");
        }
        System.out.println(result[result.length - 1]);
    }
}
