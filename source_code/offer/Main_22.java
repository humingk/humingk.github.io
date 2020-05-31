import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * @author humingk
 */
public class Main_22 {
    public double[] solution(int start, int end, int num) {
        int sum = (num - 1) / 3;
        int plus = (num - 1) % 3;
        double k = 6.00 * sum;
        if (plus == 1) {
            k += 1.00;
        } else if (plus == 2) {
            k += 3.00;
        }
        k /= 2;
        double p = (end - start) / k;
        double[] result = new double[num];
        for (int i = 0; i < num; i++) {
            result[i] = start + p * i;
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int start = scanner.nextInt();
        int end = scanner.nextInt();
        int num = scanner.nextInt();
        if (num <= 1) {
            System.out.println(0);
            return;
        }
        DecimalFormat df = new DecimalFormat("0.00");
        if (start < end) {
            double[] result = new Main_22().solution(start, end, num);
            for (int i = 0; i < result.length - 1; i++) {
                System.out.print(df.format(result[i]) + " ");
            }
            System.out.print(df.format(result[result.length - 1]));
        } else {
            double[] result = new Main_22().solution(end, start, num);
            for (int i = result.length - 1; i > 0; i--) {
                System.out.print(df.format(result[i]) + " ");
            }
            System.out.print(df.format(result[0]));
        }
    }
}
