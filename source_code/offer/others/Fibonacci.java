package others;

/**
 *
 * @author humingk
 */
public class Fibonacci {
    /**
     * 递归
     *
     * @param n
     * @return
     */
    public int recursion(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        return recursion(n - 1) + recursion(n - 2);
    }

    /**
     * 向上调用
     *
     * @param n
     * @return
     */
    public int forUp(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        int FibOne=0;
        int FibTwo=1;
        int FibSum=0;
        for (int i = 2; i <=n ; i++) {
            FibSum=FibOne+FibTwo;
            FibOne=FibTwo;
            FibTwo=FibSum;
        }
        return FibSum;
    }

    /**
     * 递归解矩阵乘方
     *
     * @param n
     * @return
     */
    public int matrixPower(int n){
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        return helpPower(n)[0];
    }

    /**
     * 递归解矩阵乘方
     *
     * @param n
     * @return
     */
    public int[] helpPower(int n){
        if ((n-1)==1){
            return new int[]{1,1,1,0};
        }
        else if((n-1)==2){
            return power(new int[]{1,1,1,0},new int[]{1,1,1,0});
        }
        else if((n-1)%2==0){
            return power(helpPower(n/2+1),helpPower(n/2+1));
        }
        else{
            return power(helpPower(n-1),new int[]{1,1,1,0});
        }
    }

    /**
     * 矩阵乘法
     *
     * @param a
     * @param b
     * @return
     */
    public int[] power(int[] a,int[] b){
        return new int[]{a[0]*b[0]+a[1]*b[2],a[0]*b[1]+a[1]*b[3],a[2]*b[0]+a[3]*b[2],a[2]*b[1]+a[3]*b[3]};
    }

    public static void main(String[] args) {
        Fibonacci f=new Fibonacci();
        System.out.println("");
        for (int i = 0; i <100 ; i++) {
            System.out.println("一 -"+f.recursion(i));
            System.out.println("二 -"+f.forUp(i));
            System.out.println("三 -"+f.matrixPower(i));
        }
    }
}