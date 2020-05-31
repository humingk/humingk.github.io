---
layout: post
title : 典型编程题笔记(3)-字符串(Java)
categories : algorithms
description : 
keywords :
---

- 典型的编程题，包括测试
- [github源代码地址](https://github.com/humingk/humingk.github.io/tree/master/source_code/offer/string)
- 若未标明，默认的测试平台为：[牛客网](https://www.nowcoder.com)

---

### 字符串模板

第2-3题使用此模板:

```java
package String;

/**
 * @author humingk
 */
public class StringBase {
    // ------------------------------------------------------------------------------------------
    // start
    // 1.测试用例方法

    public void test() {

    }

    // 2.算法题方法

    public String solution() {


        return null;
    }

    // end
    // ------------------------------------------------------------------------------------------

    public static void main(String[] args) {
        new StringBase().test();
    }
}
```

---

### 一、替换空格

[OJ](https://www.nowcoder.com/practice/4060ac7e3e404ad1a894ef3e17650423?tpId=13&tqId=11155&tPage=1&rp=1&ru=%2Fta%2Fcoding-interviews&qru=%2Fta%2Fcoding-interviews%2Fquestion-ranking)

请实现一个函数，将一个字符串中的每个空格替换成“%20”。例如，当字符串为We Are Happy.则经过替换之后的字符串为We%20Are%20Happy。

#### 测试用例

1. 空格位于最前面
2. 空格位于中间
3. 空格位于最后面
4. 多个连续的空格
5. 没有空格
6. 输入为空字符串
7. 输入为空
8. 输入为连续的空格

#### 解法1 从前向后遍历后移 O(n^2)

循环替换，字符串后移 (后移次数过多，不推荐)

#### 解法2+测试 先算空格数 O(n)+O(n)

先得出空格数，确定新StringBuffer大小，再从后向前移动

```java
package string;

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
```

#### 运行结果

```
测试是否将空格替换为 %20 
1.空格位于最前面
%20stupidisasstupiddoes
2.空格位于中间
stupid%20is%20as%20stupid%20does
3.空格位于最后面
stupidisasstupiddoes%20
4.多个连续的空格
stupid%20%20is%20as%20%20stupid%20does
5.没有空格
stupidisasstupiddoes
6.输入为空字符串

7.输入为空

8.输入为连续的空格
%20%20%20
```

#### 解法3 String新建叠加 O(n)+O(N\*N/2)

利用string的+，遍历str，遇到空格+"%20",其它不变

java的String是final的，这里每+一次，都会建一个新的String，空间复杂度为O(N\*N/2)，不推荐

```java
public class Solution {
    public String replaceSpace(StringBuffer str) {
        if(str==null){
            return "";
        }
        String result="";
        for(int i=0;i<str.length();i++){
            if(str.charAt(i)==' '){
                result+="%20";
            }else{
                result+=str.charAt(i);
            }
        }
        return result;
    }
}
```

---

### 二、正则表达式匹配

[OJ](https://www.nowcoder.com/practice/45327ae22b7b413ea21df13ee7d6429c?tpId=13&tqId=11205&tPage=3&rp=1&ru=%2Fta%2Fcoding-interviews&qru=%2Fta%2Fcoding-interviews%2Fquestion-ranking)

请实现一个函数用来匹配包括'.'和'\*'的正则表达式。模式中的字符'.'表示任意一个字符，而'\*'表示它前面的字符可以出现任意次（包含0次）。 在本题中，匹配是指字符串的所有字符匹配整个模式。例如，字符串"aaa"与模式"a.a"和"ab\*ac\*a"匹配，但是与"aa.a"和"ab*a"均不匹配

#### 解法1+测试 双指针移位

两个指针分别指向字符串、模式，循环遍历两者，当两者都能遍历完说明能匹配

遍历到某个字符(包括"."),对于第二个字符是否为"\*",分两种情况AB：

- A - pattern第二个字符为\*,即"x\*"，对于此模式有没有匹配,分两种情况ab:

  - a - 当前匹配,对于\*取几次,有两种形式(1)(2):

    - (1) - \*取1次,  a... -> a\*...  str后移1位，pattern不变
  
    - (2) -  \*取0或2-n次，b... -> a\*...  str不变，pattern后移两位
  
    这里也有情况3=情况1+情况2的组合，\*取1-n次=\*取1次+\*取2-n次，即：
  
    aaab... -> a\*...  str后移n位，然后pattern也后移两位
  
  - b - 当前不匹配,\*只能取0次，只有一种形式(1):
  
    - (1) -  b... -> a\*...  str不变，pattern后移两位
  
  PS：
  
  这里的a-(2)情况和b-(1)情况虽然执行效果一样，但用处不同：
  
  b-(1)情况仅仅对应\* 取0次的情况，而a-(2)情况不仅包括\*取0次情况，还包括\*取1-n次的情况
  
- B -  pattern第二个字符不为\*,此时分两种情况:

  - a - 第一个字符不匹配， a... -> b...  ，返回false
  - b - 第一个字符匹配， a... -> a...  ，str后移一位，pattern后移一位

PS：

这里采用的是先判断第二个字符是否为"\*"再判断第一个字符是否相等，也可以反着来

PS：

因为在A-a中，有两种形式可选，故此处需要用递归来实现循环遍历

```java
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
```

#### 运行结果

```java
   true true
 .*  true true
 .  false false
 c*  true true
a .*  true true
a a.  false false
a   false false
a .  true true
a ab*  true true
a ab*a  false false
aa aa  true true
aa a*  true true
aa .*  true true
aa .  false false
ab .*  true true
ab .*  true true
aaa aa*  true true
aaa aa.a  false false
aaa a.a  true true
aaa .a  false false
aaa a*a  true true
aaa ab*a  false false
aaa ab*ac*a  true true
aaa ab*a*c*a  true true
aaa .*  true true
aab c*a*b  true true
aaca ab*a*c*a  true true
aaba ab*a*c*a  false false
bbbba .*a*a  true true
bcbbabab .*a*a  false false
```

#### 解法2 动态规划 O(m\*n)+O(m\*n)

设置dp[]\[\]保存str和pattern的匹配关系true or false，从后向前一一对应

有：

dp[i]\[j\]={dp[i+1]\[j\],dp[i]\[j+2\],dp[i+1]\[j+1\]}

dp[0]\[0\]即最终的结果

类似解法1，i指针从后向前遍历str，j指针从后向前遍历pattern，分两种情况AB：

- A - pattern当前字符为"\*",对于下一个pattern字符分两种情况ab：

  - a - 下一个pattern字符与当前str字符相等，即 ...a -> ...a* ,分两种情况(1)(2):

    - （1）-  \*匹配1次,i前移一个字符，j不变

      dp[i]\[j\]=dp[i+1]\[j\]

    - （2）-  \*匹配0次或2-n次,i不变，j前移两个字符

      dp[i]\[j\]=dp[i]\[j+2\]

  - b - 下一个pattern字符与当前str字符不相等，即 ...b -> ...a* ,仅一种情况:

    - （1）-  \*匹配0次,i不变，j前移两个字符

      dp[i]\[j\]=dp[i]\[j+2\]

- A - pattern当前字符不为为"\*",分两种情况ab:

  - a - 不匹配，即 ...b -> ...a ,i不变，j前移一个字符

    不匹配默认false

  - b - 匹配，即 ...a -> ...a ,i前移一个字符，j前移一个字符

    dp[i]\[j\]=dp[i+1]\[j+1\]

```java
public class Solution {
    public boolean match(char[] str, char[] pattern) {
        if (str == null || pattern == null) {
            return false;
        }
        // 存储str和pattern的对应关系,从后向前
        boolean[][] dp = new boolean[str.length + 1][pattern.length + 1];
        // 初始化，""和""匹配
        dp[str.length][pattern.length] = true;
        // 开始遍历，str从末尾""开始
        for (int i = str.length; i >= 0; i--) {
            // pattern从末尾最后一个字符开始
            for (int j = pattern.length - 1; j >= 0; j--) {
                // pattern当前字符后面的字符为*,j不能超范围
                if (j <= pattern.length - 2 && pattern[j + 1] == '*') {
                    // pattern当前字符与str当前字符相等，i不能超范围
                    if (i <= str.length - 1 && (pattern[j] == str[i] || pattern[j] == '.')) {
                        dp[i][j] = dp[i + 1][j] || dp[i][j + 2];
                    }
                    // pattern当前字符与str当前字符不相等
                    else {
                        dp[i][j] = dp[i][j + 2];
                    }
                }
                // pattern当前字符后面字符不为*
                else {
                    // pattern当前字符与str当前字符相等，i不能超范围
                    if (i <= str.length - 1 && (pattern[j] == str[i] || pattern[j] == '.')) {
                        dp[i][j] = dp[i + 1][j + 1];
                    }
                }
            }
        }
        return dp[0][0];
    }
}
```

---



### 三、表示数值的字符串

[OJ](https://www.nowcoder.com/practice/6f8c901d091949a5837e24bb82a731f2?tpId=13&tqId=11206&tPage=3&rp=1&ru=%2Fta%2Fcoding-interviews&qru=%2Fta%2Fcoding-interviews%2Fquestion-ranking)

请实现一个函数用来判断字符串是否表示数值（包括整数和小数）。例如，字符串"+100","5e2","-123","3.1416"和"-1E-16"都表示数值。 但是"12e","1a3.14","1.2.3","+-5"和"12e+4.3"都不是。

#### 解法1+测试 正则表达式判断

依次判断，A[.[B]\[e|EC\]] 或 .[B]\[e|EC\]
- A C 为正负整数串
- B为无符号整数串
- 可以没有A

```java
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
```

#### 运行结果

```java
100 true true
123.45e+6 true true
+500 true true
5e2 true true
3.1416 true true
600. true true
-.123 true true
-1E-16 true true
1.79769313486232E+308 true true
12e false false
1a3.14 false false
1+23 false false
1.2.3 false false
+-5 false false
12e+5.4 false false
. false false
.e1 false false
e1 false false
+. false false
 false false
null false false
```

------

### 四、字符串的字典序排列

[OJ](https://www.nowcoder.com/practice/fe6b651b66ae47d7acce78ffdd9a96c7?tpId=13&tqId=11180&tPage=2&rp=1&ru=%2Fta%2Fcoding-interviews&qru=%2Fta%2Fcoding-interviews%2Fquestion-ranking)
输入一个字符串,按字典序打印出该字符串中字符的所有排列。例如输入字符串abc,则打印出由字符a,b,c所能排列出来的所有字符串abc,acb,bac,bca,cab和cba。

输入描述:

输入一个字符串,长度不超过9(可能有字符重复),字符只包括大小写字母。

#### 解法1 递归求所有排列再排序成字典序

固定第一个，依次与后面的元素交换

后面的元素固定第一个，继续交换...

若abb，a与第一个b交换得：bab，第二个b与第一个b重复，a不与第二个b交换

即：轮到第二个b，需要与前面的a、b比较一下是否相同,相同的话跳过，以避免重复

eg：

abc，固定第一个a,并分别与后面交换,递归：

abc bac cba

对于abc,固定第二个b,递归:

abc acb (最后一级，添加到result)

对于bac,固定第二个a,递归:

bac bca (最后一级，添加到result)

对于cba,固定第二个b,递归:

cba cab (最后一级，添加到result)

```java
import java.util.ArrayList;
import java.util.Collections;

public class Solution {
    public ArrayList<String> Permutation(String str) {
        if (str == null || "".trim().equals(str)) {
            return result;
        }
        recursion(new StringBuffer().append(str), 0);
        // 排序成字典序
        Collections.sort(result);
        return result;
    }

    ArrayList<String> result = new ArrayList<>();

    private void recursion(StringBuffer str, int index) {
        // 固定元素到了最后一个，只有一种情况
        if (index == str.length()-1) {
            result.add(str.toString());
            return;
        }
        // 交换元素之前，本身
        recursion(str, index + 1);
        for (int i = index + 1; i < str.length(); i++) {
            StringBuffer s = new StringBuffer(str.toString());
            boolean flag = true;
            for (int j = index; j < i; j++) {
                // 判断当前需要交换的元素，有没有与前面重复
                if (str.charAt(i) == str.charAt(j)) {
                    flag = false;
                }
            }
            // 没有重复
            if (flag) {
                s.replace(index, index + 1, String.valueOf(str.charAt(i)));
                s.replace(i, i + 1, String.valueOf(str.charAt(index)));
                // 递归
                recursion(s, index + 1);
            }
        }
    }

}
```

------

#### 解法2 字典序排列算法

可以从最小的（字典序第一个）排列开始，循环求其在字典序列中的下一个排列

求字典序中的下一个排列，就是求字典序中第一个比其大的排列

eg：1234 -> 1243 -> 1324 -> 1342 ...

eg: 346987521在字典序中的下一个排列为3471256789

首先，将当期排列中从右到左的第一个变小的数字6与从右到左第一个比6大的数字7交换

即：347986521

再将目前序列中7后面的序列排成正序，即当期的倒序再进行倒序即可

即：347125689

```java
import java.util.ArrayList;
import java.util.Arrays;
public class Solution {
    public ArrayList<String> Permutation(String str) {
        ArrayList<String> result=new ArrayList<>();
        if(str==null||str.length()==0){
            return result;
        }
        int i,j;
        char[] c=str.toCharArray();
        Arrays.sort(c);
        result.add(String.valueOf(c));
        while(true){
            // 找到i,i为从右到左第一个变小的数
            i=c.length-2;
            while(i>=0&&c[i]>=c[i+1]){
                i--;
            }
            // 当前序列为最后一个字典序
            if(i<0){
                break;
            }
            // 找到j,j为从右到左第一个比i大的数
            j=c.length-1;
            while(j>i&&c[j]<=c[i]){
                j--;
            }
            // 交换i j
            swap(c,i,j);
            // 排序i后面的序列为正序，由于i后面为倒序，故再倒序即可
            reverse(c,i+1);
            // 此序列就是当前序列的下一个字典序
            result.add(String.valueOf(c));
        }
        return result;
    }
    public void swap(char[] c,int i,int j){
        char temp=c[i];
        c[i]=c[j];
        c[j]=temp;
    }
    public void reverse(char[] c,int start){
        for(int i=start;i<(start+c.length)/2;i++){
            swap(c,i,c.length-1-(i-start));
        }
    }
}
```

------



### 五、把数字翻译成字符串

[OJ](https://www.acwing.com/problem/content/description/55/)

给定一个数字，我们按照如下规则把它翻译为字符串：

0翻译成”a”，1翻译成”b”，……，11翻译成”l”，……，25翻译成”z”。

一个数字可能有多个翻译。例如12258有5种不同的翻译，它们分别是”bccfi”、”bwfi”、”bczi”、”mcfi”和”mzi”。

请编程实现一个函数用来计算一个数字有多少种不同的翻译方法。

```
输入："12258"

输出：5
```

#### 解法1 动态规划 O(n)+O(n)

- 从左到右递归

  f(i)为从第i个元素开始的数目，有

  f(i)=

  - f(i+1) ,当i和i+1不能拼接
  - f(i+1)+f(i+2),当i和i+1能拼接

但会求解重复子问题，因此通过循环实现从右到左递归:

辅助数组存放存储以当前元素为起始值的子字符串所对应的翻译方法总数

```java
class Solution {
    public int getTranslationCount(String s) {
        if (s == null || "".equals(s)) {
            return 0;
        }
        // 存储以当前元素为起始值的子字符串所对应的翻译方法总数
        int[] countList = new int[s.length() + 1];
        countList[s.length()] = 0;
        countList[s.length() - 1] = 1;
        for (int i = s.length() - 2; i >= 0; i--) {
            countList[i] = countList[i + 1];
            // 如果s[i]s[i+1]<26,能拼接
            if (s.charAt(i) == '1' || (s.charAt(i) == '2' && s.charAt(i + 1) < '6')) {
                countList[i] += countList[i + 2];
            }
        }
        return countList[0];
    }

    public static void main(String[] args) {
        System.out.println(new Solution().getTranslationCount("12258"));
    }
}
```

#### 解法2 动态规划-优化辅助空间 O(n)+O(1)

计算now（以now对应元素为起始值的子字符串所对应的翻译方法总数）的时候，只需要now右边的元素（pre对应的元素）和now右边右边的元素（prepre对应的元素）

```java
class Solution {
    public int getTranslationCount(String s) {
        if (s == null || "".equals(s.trim())) {
            return 0;
        }
        // pre,prepre,now均为当前元素为起始值的子字符串所对应翻译总数
        // pre初始化为以length-1即最后一位开头的子字符串翻译总数，prepre初始化为length即...
        int pre = 1, prepre = 1;
        // now为以length-2开头的子字符串翻译总数（考虑到了now与pre是否能拼接）,初始化为s仅一个数情况
        int now = 1;
        for (int i = s.length() - 2; i >= 0; i--) {
            now = pre;
            // 如果或s[i]s[i+1]<26,now与pre可以拼接
            if (s.charAt(i) == '1' || (s.charAt(i) == '2' && s.charAt(i + 1) < '6')) {
                // now与pre拼接后，再加上prepre的总数
                now += prepre;
            }
            // 向左移动
            prepre = pre;
            pre = now;
        }
        return now;
    }
}
```

------



### 六、第一个只出现一次的字符

[OJ](https://www.nowcoder.com/practice/1c82e8cf713b4bbeb2a5b31cf5b0417c?tpId=13&tqId=11187&tPage=2&rp=1&ru=%2Fta%2Fcoding-interviews&qru=%2Fta%2Fcoding-interviews%2Fquestion-ranking)

在一个字符串(0<=字符串长度<=10000，全部由字母组成)中找到第一个只出现一次的字符,并返回它的位置, 如果没有则返回 -1（需要区分大小写）.

#### 解法1 循环每个元素 O(n^2)



------

#### 解法2 自定义字母哈希表 O(N)+O(1)

ASCII码表中

65 - A

...

90 - Z

6个其他字符

97 - a

...

122 - z

```java
public class Solution {
    public int FirstNotRepeatingChar(String str) {
        if(str==null||"".equals(str)){
            return -1;
        }
        // 小写字母和大写字母之间还有6个其他符号 26+26+6
        int[] repeatList=new int[58];
        for(int i=0;i<str.length();i++){
            repeatList[str.charAt(i)-'A']++;
        }
        for(int i=0;i<str.length();i++){
            if(repeatList[str.charAt(i)-'A']==1){
                return i;
            }
        }
        return -1;
    }
}
```

------



### 七、字符流中第一个不重复的字符

[OJ](https://www.nowcoder.com/practice/00de97733b8e4f97a3fb5c680ee10720?tpId=13&tqId=11207&tPage=3&rp=1&ru=%2Fta%2Fcoding-interviews&qru=%2Fta%2Fcoding-interviews%2Fquestion-ranking)

请实现一个函数用来找出字符流中第一个只出现一次的字符。例如，当从字符流中只读出前两个字符"go"时，第一个只出现一次的字符是"g"。当从该字符流中读出前六个字符“google"时，第一个只出现一次的字符是"l"。

输出描述:

如果当前字符流没有存在出现一次的字符，返回#字符。

#### 解法1 自定义ASCII哈希表

ascill码第一个字符是空格' '

```java
import java.util.LinkedList;
import java.util.List;

public class Solution {
    //Insert one char from stringstream
    public void Insert(char ch) {
        stream.add(ch);
        repeatList[ch - ' ']++;
    }

    // 字符流
    private List<Character> stream = new LinkedList<>();
    // 字符出现次数
    private int[] repeatList = new int[256];

    //return the first appearence once char in current stringstream
    public char FirstAppearingOnce() {
        for (int i = 0; i < stream.size(); i++) {
            if (repeatList[stream.get(i) - ' '] == 1) {
                return stream.get(i);
            }
        }
        return '#';
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        s.Insert('a');
        System.out.println(s.FirstAppearingOnce());
        s.Insert('e');
        System.out.println(s.FirstAppearingOnce());
        s.Insert('a');
        System.out.println(s.FirstAppearingOnce());
        s.Insert('y');
        System.out.println(s.FirstAppearingOnce());
        s.Insert('e');
        System.out.println(s.FirstAppearingOnce());
        s.Insert('g');
        System.out.println(s.FirstAppearingOnce());
        s.Insert('a');
        System.out.println(s.FirstAppearingOnce());
    }
}
```

------

### 八、翻转字符串-翻转单词顺序

[OJ](https://www.nowcoder.com/practice/3194a4f4cf814f63919d0790578d51f3?tpId=13&tqId=11197&tPage=3&rp=1&ru=%2Fta%2Fcoding-interviews&qru=%2Fta%2Fcoding-interviews%2Fquestion-ranking)

牛客最近来了一个新员工Fish，每天早晨总是会拿着一本英文杂志，写些句子在本子上。同事Cat对Fish写的内容颇感兴趣，有一天他向Fish借来翻看，但却读不懂它的意思。例如，“student. a am I”。后来才意识到，这家伙原来把句子单词的顺序翻转了，正确的句子应该是“I am a student.”。Cat对一一的翻转这些单词顺序可不在行，你能帮助他么？

#### 解法1 栈存放单词起始值

eg:

"I am a student."

栈中依次压入单词起始值：

0 1 2 4 5 6 7 14

```java
import java.util.Stack;

public class Solution {
    public String ReverseSentence(String str) {
        if (str == null || "".equals(str.trim())) {
            return str;
        }
        Stack<Integer> stack = new Stack<>();
        int i = 0;
        while (i < str.length()) {
            stack.push(i++);
            while (i < str.length() - 1 && str.charAt(i) != ' ') {
                i++;
            }
            stack.push(i++);
        }
        i = 0;
        int j = 0;
        int count = 0;
        String result = "";
        while (!stack.isEmpty()) {
            j = stack.pop();
            i = stack.pop();
            result += str.substring(i, j + 1);
            if (count == 0) {
                result += " ";
            }
            count++;
        }
        return result.trim();
    }

    public static void main(String[] args) {
//        System.out.println(new Solution().ReverseSentence("I am a student."));
        System.out.println(new Solution().ReverseSentence("  "));
    }
}
```

------

#### 解法2 字符串数组存放单词

```java
public class Solution {
    public String ReverseSentence(String str) {
        if(str==null||"".equals(str.trim())){
            return str;
        }
        String [] wordList=str.split(" ");
        String result="";
        for(int i=wordList.length-1;i>=0;i--){
            result+=wordList[i]+" ";
        }
        return result.trim();   
    }
}
```

------

#### 解法3 翻转整体再翻转单词

先翻转整个字符串，再分别翻转每个单词

```java
public class Solution {
    public String ReverseSentence(String str) {
        if(str==null||str.length()==0||"".equals(str.trim())){
            return str;
        }
        char[] c=str.toCharArray();
        // 翻转所有字符串
        reverse(c,0,c.length-1);
        int start=0,end=0;
        int i=0;
        while(i<c.length){
            start=i;
            while(i<c.length&&c[i]!=' '){
                i++;
            }
            if(i==c.length-1){
                end=i;
            }else{
                end=i-1;
            }
            // 翻转所有单词
            reverse(c,start,end);
            i++;
        }
        return String.valueOf(c);
        
    }
    public void reverse(char[]c,int start,int end){
        char temp;
        while(start<end){
            temp=c[start];
            c[start]=c[end];
            c[end]=temp;
            start++;
            end--;
        }
    }
}
```

------

### 八、翻转字符串-左旋转字符串

[OJ](https://www.nowcoder.com/practice/12d959b108cb42b1ab72cef4d36af5ec?tpId=13&tqId=11196&tPage=3&rp=1&ru=%2Fta%2Fcoding-interviews&qru=%2Fta%2Fcoding-interviews%2Fquestion-ranking)

汇编语言中有一种移位指令叫做循环左移（ROL），现在有个简单的任务，就是用字符串模拟这个指令的运算结果。对于一个给定的字符序列S，请你把其循环左移K位后的序列输出。例如，字符序列S=”abcXYZdef”,要求输出循环左移3位后的结果，即“XYZdefabc”。是不是很简单？OK，搞定它！

#### 解法1 字符串拼接 Ｏ(n)+O(n)

利用java的String内置函数 substring(int start,int end)，start包括，end不包括

但Java的字符串拼接会用掉辅助空间

```java
public class Solution {
    public String LeftRotateString(String str,int n) {
        if(str==null||"".equals(str.trim())){
            return "";
        }
        return str.substring(n,str.length())+str.substring(0,n);
    }
}
```



#### 解法２ 翻转部分再翻转整体　O(n)+(1)

```java
public class Solution {
    public String LeftRotateString(String str,int n) {
        if(str==null||"".equals(str.trim())){
            return "";
        }
        char[] c=str.toCharArray();
        // 翻转前部分
        reverse(c,0,n-1);
        // 翻转后部分
        reverse(c,n,c.length-1);
        // 翻转整体
        reverse(c,0,c.length-1);
        return String.valueOf(c);
    }
    public void reverse(char[] c,int start,int end){
        char temp;
        while(start<end){
            temp=c[start];
            c[start]=c[end];
            c[end]=temp;
            start++;
            end--;
        }
    }
}
```



### 九、字符串转换成整数

[OJ](https://www.nowcoder.com/practice/1277c681251b4372bdef344468e4f26e?tpId=13&tqId=11202&tPage=3&rp=1&ru=%2Fta%2Fcoding-interviews&qru=%2Fta%2Fcoding-interviews%2Fquestion-ranking)

题目描述

将一个字符串转换成一个整数(实现Integer.valueOf(string)的功能，但是string不符合数字要求时返回0)，要求不能使用字符串转换整数的库函数。 数值为0或者字符串不是一个合法的数值则返回0。

输入描述:

```
输入一个字符串,包括数字字母符号,可以为空
```

#输出描述:

```
如果是合法的数值表达则返回该数字，否则返回0
```

示例1

输入

```
+2147483647
    1a33
```

输出

```
2147483647
    0
```

#### 测试用例

- 0x8000 000 ～0x7fff fff之间的32位int型整数
- 0x8000 000 ～0x7fff fff之外的非32位int型整数
- null
- 空串
- 包含+-的数
- 只有+-
- 除+-以外的非法字符

#### 解法1 多条件考虑

32位int型最大值为2147483647

```java
public class Solution {
    public int StrToInt(String str) {
        if(str==null||"".equals(str.trim())){
            return 0;
        }
        int result=0;
        boolean isPositive=true;
        for(int i=0;i<str.length();i++){
            char now = str.charAt(i);
            // 无符号数
            if(now-'0'<=9&&now-'0'>=0){
                // 溢出，32位int型最大值为2147483647
                // 原始值大于214748364 || 原始值等于214748364但最新值个位数大于7
                if(i>=9&&(result>(0x7fffffff/10)||(result==0x7fffffff&&now-'0'>7))){
                    return 0;
                }
                result=result*10+now-'0';
            }
            // 符号
            else if(now=='-'||now=='+'){
                // 符号位于最高位
                if(i==0){
                    // 符号位后面有数字
                    if(str.length()>1&&(str.charAt(i+1)-'0')<=9&&(str.charAt(i+1)-'0')>=0){
                        if(now=='-'){
                            isPositive=false;
                        }
                    }
                    // 符号后面不是数字
                    else{
                        return 0;
                    }
                }
                // 符号不位于最高位
                else{
                    return 0;
                }
            }
            // 包含非法字符
            else{
                return 0;
            }
        }
        if(!isPositive){
            result=0-result;
        }
        return result;
    }
}
```

------

### 十、索引处的解码字符串

[OJ](https://leetcode-cn.com/problems/decoded-string-at-index/)

给定一个编码字符串 S。为了找出解码字符串并将其写入磁带，从编码字符串中每次读取一个字符，并采取以下步骤：

如果所读的字符是字母，则将该字母写在磁带上。
如果所读的字符是数字（例如 d），则整个当前磁带总共会被重复写 d-1 次。
现在，对于给定的编码字符串 S 和索引 K，查找并返回解码字符串中的第 K 个字母。

 

示例 1：

输入：S = "leet2code3", K = 10
输出："o"
解释：
解码后的字符串为 "leetleetcodeleetleetcodeleetleetcode"。
字符串中的第 10 个字母是 "o"。
示例 2：

输入：S = "ha22", K = 5
输出："h"
解释：
解码后的字符串为 "hahahaha"。第 5 个字母是 "h"。
示例 3：

输入：S = "a2345678999999999999999", K = 1
输出："a"
解释：
解码后的字符串为 "a" 重复 8301530446056247680 次。第 1 个字母是 "a"。


提示：

2 <= S.length <= 100
S 只包含小写字母与数字 2 到 9 。
S 以字母开头。
1 <= K <= 10^9
解码后的字符串保证少于 2^63 个字母。

#### 解法1 保存解码字符串(超时)

将解码的字符串保存起来，再读取第K个元素

当遇到数字比较多的情况，会超出时间限制，甚至会内存溢出

```java
import java.util.LinkedList;
import java.util.List;

class Solution {
    public String decodeAtIndex(String S, int K) {
        if (S == null || "".equals(S.trim())) {
            return "";
        }
        // 保存解码后的字符串
        List<Character> list = new LinkedList<>();
        for (int i = 0; i < S.length(); i++) {
            // 第k个字符已求出
            if (list.size() >= K) {
                return list.get(K - 1).toString();
            }
            char now = S.charAt(i);
            int size = list.size();
            // 小写字母
            if (now >= 'a' && now <= 'z') {
                list.add(now);
            }
            // 数字
            else if (now >= '2' && now <= '9') {
                // 复制 now-1 次
                for (int k = 0; k < now - '1'; k++) {
                    // 复制
                    for (int j = 0; j < size; j++) {
                        list.add(list.get(j));
                    }
                }
            } else {
                return "";
            }
        }
        // 可能最后一个字符为数字
        return list.get(K - 1).toString();
    }

    public static void main(String[] args) {
        System.out.println(new Solution().decodeAtIndex("leet2code3", 10));
    }
}
```

------

#### 解法2 逆向求解 O(n)+O(1)

先求出编码后的字符串的长度，倒序遍历当前字符串S：

- 遇到数字说明字符串重复，删除重复字符串的长度
- 遇到字母则仅减去当前字符的长度

倒序遍历的同时，对K进行除余操作，当K==size的时候，说明遍历到目标字符

```java
	class Solution {
    public String decodeAtIndex(String S, int K) {
        if (S == null || "".equals(S.trim())) {
            return "";
        }
        // int可能会溢出
        long size = 0;
        // 计算解码后字符串的长度
        for (int i = 0; i < S.length(); i++) {
            char now = S.charAt(i);
            // 小写字母
            if (now <= 'z' && now >= 'a') {
                size++;
            }
            // 数字的话将前面部分的字符串重复
            else if (now <= '9' && now >= '0') {
                size *= now - '0';
            }
            // 违规字符
            else {
                return "";
            }
        }
        for (int i = S.length() - 1; i >= 0; i--) {
            // 如果某个单词长度为size，重复n次，总长度为size*n次
            // 若有size<K<size*n，则第K个元素相当于第K%size个元素
            K %= size;
            char now = S.charAt(i);
            // K==size的时候，K%size=0，已经逆向到求解字符处
            if (K == 0 && now <= 'z' && now >= 'a') {
                return String.valueOf(now);
            }
            // 遇到数字，删去重复字符
            if (now <= '9' && now >= '2') {
                size /= now - '0';
            } else {
                size--;
            }
        }
        return "";
    }
}
```

------



### 十一、最长不含重复字符的子字符串

[OJ](https://leetcode-cn.com/problems/longest-substring-without-repeating-characters)

给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。

示例 1:

输入: "abcabcbb"
输出: 3 
解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
示例 2:

输入: "bbbbb"
输出: 1
解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
示例 3:

输入: "pwwkew"
输出: 3
解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
     请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。

#### 解法1 暴力循环 O(n^3)

找出所有子字符串O(n^2)个，依次判断每个子字符串是否包括重复字符O(n)，共计O(n^3)

#### 解法2  滑动窗口+哈希集合 O(2n)+O(n)

维持一个滑动窗口i j：

- 如果下一个元素不在滑动窗口内重复，j右移

- 如果下一个元素在滑动窗口内重复，从哈希集合中移除滑动窗口最左边的元素，i右移

滑动窗口最大值即为最长子串长度

这里使用哈希集合来判断下一个元素是否在窗口内重复，重复的话i右移

其最坏情况是字符串均由同一个字符组成，每一次j右移后都出现重复，i需要右移，即每一个字符都要被i、j依次访问一次,O(2n)

```java
import java.util.HashSet;

class Solution {
    public int lengthOfLongestSubstring(String s) {
        if (s == null) {
            return 0;
        }
        int i = 0, j = 0, max = 0;
        // 保存当前滑动窗口中的值
        HashSet<Character> hashset = new HashSet<>();
        while (i < s.length() && j < s.length()) {
            // 下一个元素不在滑动窗口内重复，j右移
            if (!hashset.contains(s.charAt(j))) {
                hashset.add(s.charAt(j));
                max = Math.max(max, j - i + 1);
                j++;
            }
            // 下一个元素在滑动窗口内重复，i右移
            else {
                hashset.remove(s.charAt(i));
                i++;
            }
        }
        return max;
    }
}
```

#### 运行结果

时间和内存消耗比较大

```java
执行用时 :
37 ms
, 在所有 Java 提交中击败了
32.39%
的用户
内存消耗 :
39.9 MB
, 在所有 Java 提交中击败了
75.71%
的用户
```

#### 解法3  滑动窗口优化+哈希表 O(n)+O(n)

上一个解法中，如果下一个元素在滑动窗口内重复，i右移一位,其最坏情况是字符串均由同一个字符组成，每一次j右移后都出现重复，i需要右移，即每一个字符都要被i、j依次访问一次,O(2n)

我们可以采用哈希表存储当前滑动窗口中元素及其索引，这样不需要i每次只右移一位，而是i直接跳到滑动窗口中出现重复元素的位置后面

```java
import java.util.HashMap;

class Solution {
    public int lengthOfLongestSubstring(String s) {
        if (s == null) {
            return 0;
        }
        int i = 0, j = 0, max = 0;
        // 保存当前滑动窗口中的值及其下标
        HashMap<Character, Integer> hashMap = new HashMap<>();
        // j右移
        for (; j < s.length(); j++) {
            // 下一个元素在滑动窗口内重复，i跳转到重复元素的后面
            if (hashMap.containsKey(s.charAt(j))) {
                // 如果j对应的重复值，i早已跳过，不在滑动窗口内，则 其值+1 必定 <= i，此语句相当于没执行
                // 这样就不需要手动从hashmap中删除i跳过的元素
                i = Math.max(hashMap.get(s.charAt(j)) + 1, i);
            }
            max = Math.max(max, j - i + 1);
            hashMap.put(s.charAt(j), j);
        }
        return max;
    }

    public static void main(String[] args) {
        new Solution().lengthOfLongestSubstring("abba");
    }
}
```

#### 运行结果

相对于解法2，解法3的时间和内存均有提升

```java
执行用时 :
26 ms
, 在所有 Java 提交中击败了
64.96%
的用户
内存消耗 :
38.6 MB
, 在所有 Java 提交中击败了
87.33%
的用户
```

#### 解法4 滑动窗口优化+哈希表优化 O(n)+O(k)

由于题中仅仅用到了ASCII码，故可用int型数组来实现哈希表

- int [26] 用于字母 'a'-'z' 或 'A'-'Z'
- int[58] 用于 字符 'a'-'z'-...-'A'-'Z' （小写大写字母之间有6个其他字符）
- int [128] 用于ASCII码
- int [256] 用于扩展ASCII码

PS:

以上的解法中，哈希表中存储的都是元素在字符串中对应的下标值

而这里哈希表中存储的是元素在字符串中对应的下标值+1

因为此处的int型数组哈希表初始化的时候元素默认值就为0，这里避免冲突

```java
class Solution {
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        } else if ("".equals(s.trim())) {
            return 1;
        }
        int i = 0, j = 0, max = 0;
        // 保存当前滑动窗口中的值及其下标-1
        int[] hashAsc = new int[128];
        // j右移
        for (; j < s.length(); j++) {
            // 下一个元素在滑动窗口内重复，i跳转到重复元素的后面
            // 如果j对应的重复值，i早已跳过，不在滑动窗口内，则 其值+1 必定 <= i，此语句相当于没执行
            // 这样就不需要手动从hashmap中删除i跳过的元素
            i = Math.max(hashAsc[s.charAt(j)], i);
            max = Math.max(max, j - i + 1);
            hashAsc[s.charAt(j)] = j + 1;
        }
        return max;
    }
}
```

#### 运行结果

因为用到了int型数组的哈希表，时间和内存优化明显

```java
执行用时 :
8 ms
, 在所有 Java 提交中击败了
95.63%
的用户
内存消耗 :
37.5 MB
, 在所有 Java 提交中击败了
92.07%
的用户
```

#### 解法5 动态规划+哈希表 O(n)+O(n)

其实动态规划和优化后的滑动窗口方法几乎一样。。。

这里仍然用int型的数组作为哈希表

这里采用动态规划的递推：

以当前第i个字符结尾的子字符串的长度有两种情况:

- 此i字符未重复/此i字符上一次出现的位置不在此子字符串中:

  旧子字符串长度=i-1的子字符串长度++

- 此i字符上一次出现的位置在此子字符串中:

  新子字符串长度=i的位置-上一次出现的位置

f(n)=Max{上一子字符串长度++，新子字符串长度}

```java
class Solution {
    public int lengthOfLongestSubstring(String s) {
        if (s == null || "".equals(s)) {
            return 0;
        }
        // ASCII哈希表，存储字符在字符串中的下标值+1
        int[] hashAsc = new int[128];
        // 所有子字符串长度的最大值 当前子字符串长度
        int max = 0, now = 0;
        for (int i = 0; i < s.length(); i++) {
            // i对应的字符上一次出现的位置（字符串下标值+1）
            int pre = hashAsc[s.charAt(i)];
            // pre字符位于当前子字符串范围中
            // 上一次出现的位置(哈希表) >= 当前位置(下标值i+1) - 当前子字符串长度
            if (pre >= i + 1 - now) {
                now = i + 1 - pre;
            }
            // pre字符不位于当前子字符串范围内
            else {
                now++;
            }
            // 更新当前字符出现的位置
            hashAsc[s.charAt(i)] = i + 1;
            if (now > max) {
                max = now;
            }
        }
        return max;
    }
}
```

#### 运行结果

相比于滑动窗口，时间有一点点优化

```java
执行用时 :
6 ms
, 在所有 Java 提交中击败了
98.98%
的用户
内存消耗 :
38.1 MB
, 在所有 Java 提交中击败了
89.80%
的用户
```

------



### 十二、罗马数字转整数

[OJ](https://leetcode-cn.com/problems/roman-to-integer)

罗马数字包含以下七种字符: I， V， X， L，C，D 和 M。

字符          数值
I             1
V             5
X             10
L             50
C             100
D             500
M             1000
例如， 罗马数字 2 写做 II ，即为两个并列的 1。12 写做 XII ，即为 X + II 。 27 写做  XXVII, 即为 XX + V + II 。

通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做 IIII，而是 IV。数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。同样地，数字 9 表示为 IX。这个特殊的规则只适用于以下六种情况：

I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。 
C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
给定一个罗马数字，将其转换成整数。输入确保在 1 到 3999 的范围内。

示例 1:

输入: "III"
输出: 3
示例 2:

输入: "IV"
输出: 4
示例 3:

输入: "IX"
输出: 9
示例 4:

输入: "LVIII"
输出: 58
解释: L = 50, V= 5, III = 3.
示例 5:

输入: "MCMXCIV"
输出: 1994
解释: M = 1000, CM = 900, XC = 90, IV = 4.



#### 解法1 遍历+哈希表

```java
import java.util.HashMap;

class Solution {
    public int romanToInt(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        // 存储罗马哈希表
        HashMap<Character, Integer> hashmap = new HashMap<Character, Integer>() {
            {
                put('I', 1);
                put('V', 5);
                put('X', 10);
                put('L', 50);
                put('C', 100);
                put('D', 500);
                put('M', 1000);
            }
        };
        int result = 0, i = s.length() - 1;
        while (i > 0) {
            int now = hashmap.get(s.charAt(i));
            int pre = hashmap.get(s.charAt(i - 1));
            // 出现了4 40 400 900这样的组合
            if (pre < now) {
                result += now - pre;
                i -= 2;
            } else {
                result += now;
                i--;
            }
        }
        // 只剩下一位
        if (i == 0) {
            result += hashmap.get(s.charAt(0));
        }
        return result;
    }
}
```

------

### 十三、



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

