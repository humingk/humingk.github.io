package others;

/**
 *二进制数中1的个数
 *
 * @author humingk
 */
public class OneInBinary<Value> {
    // ------------------------------------------------------------------------------------------
    // start
    // 1.测试用例方法

    public void test() {
        for (int i = -256; i < 257; i++) {
            System.out.println(i+" => "+solution1(i)+" - "+solution2(i));
        }
    }

    // 2.算法题方法

    public int solution1(int n) {
        int count=0;
        int flag=1;
        // int 有32位，故循环32次
        while(flag!=0){
            if((n & flag)!=0){
                count++;
            }
            flag=flag<<1;

        }
        return count;

    }
    public int solution2(int n) {
        int count=0;
        while (n!=0){
            count++;
            n=(n-1)&n;
        }
        return count;
    }
    // end
    // ------------------------------------------------------------------------------------------

    public static void main(String[] args) {
        new OneInBinary().test();
    }
}