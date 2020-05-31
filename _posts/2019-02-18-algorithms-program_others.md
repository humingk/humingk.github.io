---
layout: post
title : 典型编程题笔记(5)-其他(Java)
categories : algorithms
description : 
keywords :
---

- 典型的编程题，包括测试
- [github源代码地址](https://github.com/humingk/humingk.github.io/tree/master/source_code/offer/others)
- 若未标明，默认的测试平台为：[牛客网](https://www.nowcoder.com)

---

### 模板工具类:

```java
package others;

/**
 * @author humingk
 */
public class OtherBase {
    // ------------------------------------------------------------------------------------------
    // start
    // 1.测试用例方法

    public void test() {

    }

    // 2.算法题方法

    public void solution() {

    }
    // end
    // ------------------------------------------------------------------------------------------

    public static void main(String[] args) {
        new OtherBase().test();
    }
}
```



### 一、用两个栈实现队列

#### 解决方法

1. 栈1负责进，栈2负责出，栈2出的时候，若栈2为空，先pop栈1的所有值到栈2，再pop栈2；若栈2为非空，先pop栈2

#### 解法+测试

```java
package others;

import java.util.Stack;

public class TwoStackToQueue {
    Stack<Integer> stack1 = new Stack<Integer>();
    Stack<Integer> stack2 = new Stack<Integer>();

    public void push(int node) {
        stack1.push(node);
    }

    public int pop() {
        if(stack2.empty()){
            while (!stack1.empty()){
                stack2.push(stack1.pop());
            }
        }
        return stack2.pop();
    }
}
```

---

###  二、斐波那契数列

#### 解决方法

1. 从上而下递归，但需要计算很多重复的节点，导致非常慢,时间复杂度以n的指数递增

2. 从下而上循环计算，（斐波那契保存在一个数组中）,避免重复计算节点,O(n)+O(n)

3. 动态规划，（斐波那契仅仅保存在one，two，sum三个数中），O(n)+O(1)

4. 生成函数求解递推公式，O(1)

   函数如图所示:

   ![](../img/alg/fib4.png)

5. 矩阵乘法+矩阵快速幂，O(logN)

   其推导过程如图所示：

![](../img/alg/fib3.png)

其中对于矩阵快速幂的求解，有两种方法：

- 递归(**二分法**)求矩阵快速幂

  如图所示：

![](../img/alg/fib2.png)

- 循环(**二进制法**)求矩阵快速幂

  如图所示,本来需要156次计算，现在只需要（二进制个数8×二进制中1的个数4=32次）计算,矩阵也是如此

  ![](../img/alg/fib5.jpg)

#### 递归+从下而上+矩阵乘方之递归 O(logN)

```java
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
     * 向上调用（动态规划）
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
     *  递归解矩阵乘方
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
     * 递归解矩阵乘方,包含矩阵快速幂
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
```
#### 矩阵乘方之循环  O(logN)

{% raw %}

```java
public class Solution {
    // 循环求矩阵乘方 O（logN）
    public int Fibonacci(int n) {
        if (n == 0) {
            return 0;
        } else if (n == 1 || n == 2) {
            return 1;
        }
        int[][] a = new int[][]{{1, 1}, {1, 0}};
        a = quickPower(a, n - 2);
        return a[0][0] + a[0][1];
    }

    /**
     * 矩阵乘法
     *
     * @param a
     * @param b
     * @return
     */
    public int[][] multi(int[][] a, int[][] b) {
        if (a[0].length != b.length) {
            return null;
        }
        int length = b.length;
        int[][] result = new int[length][length];
        // result的行
        for (int i = 0; i < length; i++) {
            // result的列
            for (int j = 0; j < length; j++) {
                // a行元素×b列元素
                for (int k = 0; k < length; k++) {
                    result[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return result;
    }

    /**
     * 矩阵快速幂
     *
     * @param a
     * @param n
     * @return
     */
    public int[][] quickPower(int[][] a, int n) {
        if (n == 1) {
            return a;
        }
        int[][] result = new int[a.length][a.length];
        // 初始化result为单位矩阵
        for (int i = 0; i < result.length; i++) {
            result[i][i] = 1;
        }
        // 暂存矩阵的平方
        int[][] now = a;
        // n的二进制位数遍历
        for (int i = n; i != 0; i >>= 1) {
            // 当前二进制位为1
            if ((i & 1) == 1) {
                result = multi(result, now);
            }
            // 每一位都要暂存矩阵的平方，相当于a的2,4,8...次方，即a^2^n
            now = multi(now, now);
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(new Solution().Fibonacci(1));
        System.out.println(new Solution().Fibonacci(2));
        System.out.println(new Solution().Fibonacci(3));
        System.out.println(new Solution().Fibonacci(4));
        System.out.println(new Solution().Fibonacci(5));
        System.out.println(new Solution().Fibonacci(6));
        System.out.println(new Solution().Fibonacci(7));
        System.out.println(new Solution().Fibonacci(8));
        System.out.println(new Solution().Fibonacci(9));
        System.out.println(new Solution().Fibonacci(10));
    }
}
```

{% endraw %}

---

###  三、减绳子

给一根长度为n的绳子，请把绳子剪成m段（m , n ）都是正整数，（n>1&m>1）

每段绳子的长度为k[0],k[1],k[2],...,k[m]。请问k[0]*k[1]*k[2]*...*k[m]的最大值。

例如绳子是长度为8，我们把它剪成的长度分别为2,3,3的三段，此时得到的最大的乘积是18。

#### 解决方法

1. 动态规划算法

   将对大问题求最优解转化为对小问题求最优解

2. 贪婪算法

   首先，当n>1的时候，n都可以由任意个2和任意个3的和组成
   
   当 n>=5时，有：3(n-3) >= 2(n-2) >= n
   
   也就是说，将绳子剪成3和n-3的乘积比剪成2和n-2大的多，所以应该尽量剪成3和n-3(贪心算法)
   
   

#### 解法+测试

```java
    // ------------------------------------------------------------------------------------------
    // start
    // 1.测试用例方法

    public void test() {
        System.out.println("动态规划-贪婪");
        for (int i = 0; i < 100; i++) {
            System.out.println("长度为"+i+":");
            System.out.println(solutionDynamic(i) + " - " + solutionGreedy(i));
        }
    }

    // 2.算法题方法

    /**
     * 动态规划
     *
     * @return
     */
    public long solutionDynamic(int length) {
        if (length <= 1) {
            return 0;
        } else if (length == 2) {
            return 1;
        } else if (length == 3) {
            return 2;
        }

        long[] f = new long[length + 1];
        f[0] = 0;
        f[1] = 1;
        f[2] = 2;
        f[3] = 3;

        long max = 0;
        for (int i = 4; i <= length; i++) {
            max = 0;
            for (int j = 1; j <= i / 2; j++) {
                // 总长度为i 第一段为j，第二段为i-j
                long temp = f[j] * f[i - j];
                if (max < temp) {
                    max = temp;
                }
            }
            f[i]=max;
        }
        return max;
    }

    /**
     * 贪婪算法
     *
     * @return
     */
    public long solutionGreedy(int length) {
        if (length <= 1) {
            return 0;
        } else if (length == 2) {
            return 1;
        } else if (length == 3) {
            return 2;
        }

        // 剪成长度为3的个数
        int countOf3=length/3;
        // 剪成长度为2的个数
        int countOf2=0;

        // 如果剩下的长度为1，则将最后的长度4剪成两个长度为2
        if(length-countOf3*3==1){
            countOf3--;
            countOf2=2;
        }
        // 剩下的长度为2
        else if(length-countOf3*3==2){
            countOf2=1;
        }
        return (long)(pow(3,countOf3)*(long)pow(2,countOf2));
    }
    // end
    // ------------------------------------------------------------------------------------------
```

---

### 四、二进制中1的个数

[OJ](https://www.nowcoder.com/practice/8ee967e43c2c4ec193b040ea7fbb10b8?tpId=13&tqId=11164&tPage=1&rp=1&ru=%2Fta%2Fcoding-interviews&qru=%2Fta%2Fcoding-interviews%2Fquestion-ranking)

输入一个整数，输出该数二进制表示中1的个数。其中负数用补码表示。

#### 解法12+解法3+测试 将数右移比较 / 将1左移比较 / 先减1再与

1. 如果仅为正数，将二进制数依次右移>>，并与1做与运算&（对负数右移，左边会设为1，会陷入死循环）

2. 如果包含负数，将1依次左移<<，并与二进制数做与运算

3. n=(n-1)&n

   首先，将二进制数减一，然后，再与原二进制数做与运算

   比如 10100

   减一：10011，相与：10100&10011=10000

   减一导致的后面的0变为1，也会因为相与而消去

```java
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
```

#### 解法4 MIT HAKMEM算法

```java
public class Solution {
    public int NumberOf1(int n) {
        n = n - (((n >>> 1) & 0xdb6db6db) + ((n >>> 2) & 0x49249249));
        n = (n + (n >>> 3)) & 0xc71c71c7;
        return n < 0 ? ((n >>> 30) + ((n << 2) >>> 2) % 63) : n % 63;
    }
}
```

#### 解法5 Hamming Weight算法

```java
public class Solution {
    public int NumberOf1(int n) {
        n = n - ((n >>> 1) & 0x55555555);
        n = (n & 0x33333333) + ((n >>> 2) & 0x33333333);
        n = (n + (n >>> 4)) & 0x0F0F0F0F;
        return (n * 0x01010101) >>> 24;
    }
}
```



---

### 五、double数值的int次方

#### 解决方法

1. 指数分条件讨论，递归
   - 当指数为负，求其倒数
   - 递归求次方
2. 循环求次方
   - 若exp为1，则:
     - 循环1：奇数1=> result=base^1, base=base^2,exp=0
   - 若exp为3，则:
     - 循环1：奇数3=> result=base^1, base=base^2,exp=1
     - 循环2: 奇数1=> result=base^3,base=base^3,exp=0
   - 若exp为5，则:
     - 循环1：奇数5=> result=base^1, base=base^2,exp=3
     - ...

#### 测试用例

- double 正数 0 负数
- int 正数 0 负数

#### 解法+测试

```java
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
```

---

### 六、打印从1到最大的n位数

#### 解决方法

1. 高精度加法，再打印
2. 递归排列数字0-9

#### 解法+测试

```java
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
```

------

### 七、包含min的栈

#### 解决方法

- 建立一个辅助栈，专门存放最小值

#### 解法+测试

```java
    // ------------------------------------------------------------------------------------------
    // start
    // 1.测试用例方法

    public void test() {
        for (int i = 10; i < 20; i++) {
            push(i);
            print();
        }
        for (int i = 10; i < 15; i++) {
            pop();
            print();
        }
        for (int i = 5; i < 10; i++) {
            push(i);
            print();
        }
        for (int i = 0; i < data.size(); i++) {
            pop();
        }
    }

    private void print() {
        System.out.print("data: ");
        printStack(data);
        System.out.print("dataMin: ");
        printStack(dataMin);
        System.out.println("min: " + min());
        System.out.println("------------------------");
    }

    private void printStack(Stack<Integer> test) {
        int length = test.size();
        int[] temp = new int[length];
        for (int i = 0; i < length; i++) {
            temp[i] = test.pop();
        }
        for (int i = length - 1; i >= 0; i--) {
            test.push(temp[i]);
            if (temp[i] != 0) {
                System.out.print(temp[i] + " ");
            }
        }
        System.out.println();
    }

    // 2.算法题方法

    private Stack<Integer> data = new Stack<>();
    private Stack<Integer> dataMin = new Stack<>();

    public void push(int node) {
        data.push(node);
        if (dataMin.isEmpty() || node < dataMin.peek()) {
            dataMin.push(node);
        } else {
            dataMin.push(dataMin.peek());
        }
    }

    public void pop() {
        if (!data.isEmpty() && !dataMin.isEmpty()) {
            data.pop();
            dataMin.pop();
        }
    }

    public int top() {
        if (!data.isEmpty()) {
            return data.peek();
        } else {
            return -1;
        }
    }

    public int min() {
        if (!dataMin.isEmpty()) {
            return dataMin.peek();
        } else {
            return -1;
        }
    }

    // end
    // ------------------------------------------------------------------------------------------
```

#### 运行结果

```java
data: 10 
dataMin: 10 
min: 10
------------------------
data: 10 11 
dataMin: 10 10 
min: 10
------------------------
data: 10 11 12 
dataMin: 10 10 10 
min: 10
------------------------
data: 10 11 12 13 
dataMin: 10 10 10 10 
min: 10
------------------------
data: 10 11 12 13 14 
dataMin: 10 10 10 10 10 
min: 10
------------------------
data: 10 11 12 13 14 15 
dataMin: 10 10 10 10 10 10 
min: 10
------------------------
data: 10 11 12 13 14 15 16 
dataMin: 10 10 10 10 10 10 10 
min: 10
------------------------
data: 10 11 12 13 14 15 16 17 
dataMin: 10 10 10 10 10 10 10 10 
min: 10
------------------------
data: 10 11 12 13 14 15 16 17 18 
dataMin: 10 10 10 10 10 10 10 10 10 
min: 10
------------------------
data: 10 11 12 13 14 15 16 17 18 19 
dataMin: 10 10 10 10 10 10 10 10 10 10 
min: 10
------------------------
data: 10 11 12 13 14 15 16 17 18 
dataMin: 10 10 10 10 10 10 10 10 10 
min: 10
------------------------
data: 10 11 12 13 14 15 16 17 
dataMin: 10 10 10 10 10 10 10 10 
min: 10
------------------------
data: 10 11 12 13 14 15 16 
dataMin: 10 10 10 10 10 10 10 
min: 10
------------------------
data: 10 11 12 13 14 15 
dataMin: 10 10 10 10 10 10 
min: 10
------------------------
data: 10 11 12 13 14 
dataMin: 10 10 10 10 10 
min: 10
------------------------
data: 10 11 12 13 14 5 
dataMin: 10 10 10 10 10 5 
min: 5
------------------------
data: 10 11 12 13 14 5 6 
dataMin: 10 10 10 10 10 5 5 
min: 5
------------------------
data: 10 11 12 13 14 5 6 7 
dataMin: 10 10 10 10 10 5 5 5 
min: 5
------------------------
data: 10 11 12 13 14 5 6 7 8 
dataMin: 10 10 10 10 10 5 5 5 5 
min: 5
------------------------
data: 10 11 12 13 14 5 6 7 8 9 
dataMin: 10 10 10 10 10 5 5 5 5 5 
min: 5
------------------------
```



------



### 八、数字序列中某一位的数字

https://www.acwing.com/problem/content/description/52/



数字以0123456789101112131415…的格式序列化到一个字符序列中。

在这个序列中，第5位（从0开始计数）是5，第13位是1，第19位是4，等等。

请写一个函数求任意位对应的数字。

样例

```
输入：13

输出：1
```

#### 测试用例

- 0-9
- 10
- 190

#### 解法1 循环叠加位数

循环每一个数字，叠加每一个数字的位数

------

#### 解法2 按位数分情况叠加

- 0：

- 1～9：

  共9个数字，每数字占1位,共(10-1)*1=9位

- 10~99：

  共100个数字，每数字占2位，共(100-10)*2=180位

- 100～999：

  共1000个数字，每数字占3位，共(1000-100)*3=2700位

eg:

5253,在1000~9999中,前面已叠加 1+9+180+2700 =2890位，从1000开始第 5253-2890 =2363 位

第2363位所在数字为 1000+2363/4=1590 ,其中,第2363位在数字 1590 的第 4-2363%4-1=0位，即0



```java
class Solution {
    public int digitAtIndex(int n) {
        if (n < 10) {
            return n;
        }
        int i = 1;
        // 保存n-之前位数
        int k = n;
        // 用于判断是否过界
        int t = k - 10;
        while (t >= 0) {
            i++;
            k = t;
            // i位数包含的所有位数
            t -= (Math.pow(10, i) - Math.pow(10, i - 1)) * i;
        }
        // 当前位数所在数字
        int num = (int) (Math.pow(10, i - 1) + k / i);
        // 当前数字中第几位（个 十 百...）
        int InNum = i - k % i - 1;
        // 个
        if (InNum == 0) {
            return num % 10;
        } 
        // 十、百...
        else {
            return (int) ((num / (Math.pow(10, InNum))) % 10);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            System.out.println(new Solution().digitAtIndex(i));
        }
        for (int i = 170; i < 200; i++) {
            System.out.println(new Solution().digitAtIndex(i));
        }
    }
}
```

------

### 九、丑数

把只包含质因子2、3和5的数称作丑数（Ugly Number）。例如6、8都是丑数，但14不是，因为它包含质因子7。 习惯上我们把1当做是第一个丑数。求按从小到大的顺序的第N个丑数。



#### 解法1 循环数，判断数是否是丑数

对于一个数，循环判断，如果能被2,3,5中的一个数整除就整除，最终结果为1说明是丑数

计算量大，会超时

------

#### 解法2 动态规划

用数组保存排序好从小到大的丑数

每次都从×2，×3，×5中选取最小的作为下一个丑数

用T保存当前X的位置

```java
public class Solution {
    public int GetUglyNumber_Solution(int index) {
        if(index<=0){
            return 0;
        }
        int[] uglyList=new int[index];
        uglyList[0]=1;
        // 当前已有丑数最大值
        int max=1;
        // 乘以2,3,5得到的最小丑数
        int min2=2,min3=3,min5=5;
        // 在t之前的数，乘以2,3,5都会小于max;在t之后的数，乘以2,3,5都会大于max
        int t2=1,t3=1,t5=1;
        for(int i=1;i<index;i++){
            uglyList[i]=Math.min(Math.min(min2,min3),min5);
            // 计算大于max的min2，min3,min5
            max=uglyList[i];
            while(min2<=max){
                min2=uglyList[t2++]*2;
            }
            while(min3<=max){
                min3=uglyList[t3++]*3;
            }
            while(min5<=max){
                min5=uglyList[t5++]*5;
            }
        }
        return uglyList[index-1];
    }

    public static void main(String[] args) {
        System.out.println(new Solution().GetUglyNumber_Solution(5));
    }
}
```

------

### 十、计算1+2+3+...+n

求1+2+3+...+n，要求不能使用乘除法、for、while、if、else、switch、case等关键字及条件判断语句（A?B:C）。



#### 解法1 逻辑与的短路特性+递归 o(n)

boolean flag=A && B

B调用递归函数

- 如果A为true，则执行B
- 如果A为False，则不执行B

这里B中的>0可任意

```java
public class Solution {
    public int Sum_Solution(int n) {
        boolean flag = (n > 0) && ((n += Sum_Solution(n - 1)) > 0);
        return n;
    }
}
```

------

#### 解法2 异常退出+递归 O(n)

递归结束的条件为：

n=0,此时1/n会异常退出，返回0

```java
public class Solution {
    public int Sum_Solution(int n) {
        try {
            int flag = 1 / n;
            n += Sum_Solution(n - 1);
            return n;
        } catch (Exception e) {
            return 0;
        }
    }
}
```

------

#### 解法3 位运算乘法 O(logN)

利用递增数列公式：S(n)=n*(n+1)/2

其中的乘法，利用位运算

例如a\*b 可以拆解为:

- a为奇数：(a-1)\*b +b 
- a为偶数：(a/2)\*(b\*2)

直到a为0

eg: 

5\*6 = 4\*6+6 = 2\*12+6 = 1\*24+6 =0\*24+24+6=30

```java
public class Solution {
    public int Sum_Solution(int n) {
        // n*(n+1)/2
        return multi(n, n + 1) >> 1;
    }

    // a*b
    public int multi(int a, int b) {
        int result = 0;
        // a为奇数（仅一次） a*b = (a-1)*b +b 
        if((a&1)==1){
            result+=b;
        }
        a >>= 1;
        b <<= 1;
        // 若a不为0，此处a肯定为偶数，继续拆分
        // a*b = (a/2)*(b*2)
        if(a!=0){
            result += multi(a, b);
        }
        return result;
    }
}
```

------

### 十一、整数反转

[OJ](https://leetcode-cn.com/problems/reverse-integer)

给出一个 32 位的有符号整数，你需要将这个整数中每位上的数字进行反转。

示例 1:

输入: 123
输出: 321
 示例 2:

输入: -123
输出: -321
示例 3:

输入: 120
输出: 21
注意:

假设我们的环境只能存储得下 32 位的有符号整数，则其数值范围为 [−231,  231 − 1]。请根据这个假设，如果反转后整数溢出那么就返回 0。



#### 解法1 循环

```java
class Solution {
    public int reverse(int x) {
        int result = 0;
        while (x != 0) {
            // 判断下一步操作是否溢出
            // -2^31=-2147483648,2^31-1=2147483647
            // 没必要判断最后一位是否是小于-8或者大于7，因为参数中int的最大值附近值中第一位只能为1或2
            if (Math.abs(result) > Integer.MAX_VALUE / 10) {
                return 0;
            }
            result *= 10;
            result += x % 10;
            x /= 10;
        }
        return result;
    }
}
```

------



### 十二、不用加减乘除做加法

[OJ](https://www.nowcoder.com/practice/59ac416b4b944300b617d4f7f111b215?tpId=13&tqId=11201&tPage=3&rp=1&ru=%2Fta%2Fcoding-interviews&qru=%2Fta%2Fcoding-interviews%2Fquestion-ranking)

写一个函数，求两个整数之和，要求在函数体内不得使用+、-、*、/四则运算符号

#### 解法1 计算机的加法器原理

对于十进制，比如372+948不考虑进位相加得210，进位值分别为10,100,1000，加起来共1320

对于二进制：

- 不考虑进位相加：位的异或操作

  对每一位进行相加，0+0=0，1+1=0，0+1=1，1+0=1，和位的异或操作一样

- 进位：位相与再左移一位

  只有1+1会产生一个进位，可以转换为相与（都为1）后左移（进一位）

如果当前两数进位为0，即没有再同时出现两个1，即不需要进位

eg：5+7=12，即101+111=1100



第一步，101+111=010+1010

相加：101^111=010

进位：(101&111)<<1=1010

此时，num1=相加值=010，num2=进位值=1010



第二步，010+1010=1000+0100

相加：010^1010=1000

进位：(010&1010)<<1=0100

此时，num1=相加值=1000，num2=进位值=0100



第三步，1000+0100=1100+0000

相加：1000^0100=1100

进位：(1000&0100)<<1=0000

此时，num1=相加值=1100，num2=进位值=0000



递归：

```java
public class Solution {
    public int Add(int num1,int num2) {
        if(num2==0){
            return num1;
        }
        // 位异或，相同为0，不同为1，即不考虑进位的加法
        int sum=num1^num2;
        // 相与再左移，即求上一步不考虑进位的加法的进位值
        int carry=(num1&num2)<<1;
        return Add(sum,carry);
    }
}
```

循环：

```java
public class Solution {
    public int Add(int num1,int num2) {
        // 进位值不为0
        while(num2!=0){
            // 位异或，相同为0，不同为1，即不考虑进位的加法
            int sum=num1^num2;
            // 相与再左移，即求上一步不考虑进位的加法的进位值
            num2=(num1&num2)<<1;
            num1=sum;
        }
        return num1;
    }
}
```



### 十三、电话号码的字母组合

[OJ](https://leetcode-cn.com/problems/letter-combinations-of-a-phone-number/)

给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。

给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。



示例:

输入："23"
输出：["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"].
说明:
尽管上面的答案是按字典序排列的，但是你可以任意选择答案输出的顺序



#### 解法1 递归

```java
import java.util.*;

class Solution {
    HashMap<String, String> phone = new HashMap<String, String>() {{
        put("2", "abc");
        put("3", "def");
        put("4", "ghi");
        put("5", "jkl");
        put("6", "mno");
        put("7", "pqrs");
        put("8", "tuv");
        put("9", "wxyz");
    }};
    List<String> list = new ArrayList<>();

    public List<String> letterCombinations(String digits) {
        if (!"".equals(digits.trim())) {
            recursion("", digits);
        }
        return list;
    }

    public void recursion(String combination, String digits) {
        if ("".equals(digits)) {
            list.add(combination);
        } else {
            String str = phone.get(digits.substring(0, 1));
            for (int i = 0; i < str.length(); i++) {
                recursion(combination + str.charAt(i), digits.substring(1));
            }
        }
    }

    public static void main(String[] args) {
        List<String> result = new Solution().letterCombinations("23");
        for (int i = 0; i < result.size(); i++) {
            System.out.println(result.get(i));
        }
    }
}
```

------



### 十四、





#### 解法1

```java

```

------

#### 解法2

```java

```

------

#### 解法3

```java

```

------



### 十五、





#### 解法1

```java

```

------

#### 解法2

```java

```

------

#### 解法3

```java

```

------



### 十六、





#### 解法1

```java

```

------

#### 解法2

```java

```

------

#### 解法3

```java

```

------

