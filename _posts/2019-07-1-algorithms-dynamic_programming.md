---
layout: post
title : 动态规划算法总结
categories : algorithms
description : 
keywords :
---





这里是动态规划算法的总结，包括一些具有代表性的相关题型，其中包括矩阵型、序列/双序列型、区间型、背包型等等，默认测试平台为[leetcode](https://leetcode-cn.com/problemset/all/)，部分测试平台为[牛客网剑指offer题单](https://www.nowcoder.com/ta/coding-interviews),还有一部分在[AcWing题库](https://www.acwing.com/problem/)上测试

---



## 动态规划类型

### 1. 贪心算法型

#### 动态规划算法与贪婪算法的区别

贪心算法是一种特殊的动态规划算法

对于一个动态规划问题，问题的最优解往往包含**重复**的子问题的最优解，动态规划就是为了消除重复的子问题

而贪婪算法由于每一次都**贪心**选取一个子问题，所以不会重复计算子问题的最优解

- [贪心算法型](https://humingk.github.io/algorithms-dynamic_programming/#1-%E8%B4%AA%E5%BF%83%E7%AE%97%E6%B3%95%E5%9E%8B)
  - [减绳子](https://humingk.github.io/algorithms-program_others/#三减绳子)  f(n)=Max(n的左边长度，n的右边长度)
  - [最长上升子序列](https://humingk.github.io/algorithms-dynamic_programming/#一最长上升子序列) f(n)=Max(不同上升子序列长度)
  - [课程表](https://humingk.github.io/algorithms-program_graph/#二课程表) 拓扑排序 f(n)=(选取邻接节点中最先没有前驱节点的节点)

---

### 2. 递推/划分型

比如在知道第i-1项的前提下，计算第i项

从前往后，依次递归，比如斐波那契的f(n)=f(n-1)+f(n-2)

利用循环依次计算并存入辅助空间，有时候只需要存储计算当前元素需要的历史元素,比如斐波那契

还有典型类型是求不同划分情况的最大值

- [递推/划分型](https://humingk.github.io/algorithms-dynamic_programming/#2-%E9%80%92%E6%8E%A8%E5%88%92%E5%88%86%E5%9E%8B)
  - [斐波那契数列](https://humingk.github.io/algorithms-program_others/#%E4%BA%8C%E6%96%90%E6%B3%A2%E9%82%A3%E5%A5%91%E6%95%B0%E5%88%97)  f(n)=f(n-1)+f(n-2)
  - [丑数](https://humingk.github.io/algorithms-program_others/#九丑数)  f(n)=Min(当前丑数x2,当前丑数x3,当前丑数x5)
  - [最长不含重复字符的子字符串](https://humingk.github.io/algorithms-program_array/#十六最长不含重复字符的子字符串) f(n)=Max(不同子字符串长度)
  - [把数字翻译成字符串](https://humingk.github.io/algorithms-program_string/#五把数字翻译成字符串) f(n)=[f(n+1),f(n+1)+f(n+2)]
  - [打家劫舍](https://humingk.github.io/algorithms-dynamic_programming/#%E4%B9%9D%E6%89%93%E5%AE%B6%E5%8A%AB%E8%88%8D) f(n)=max(f(n-1),f(n)+f(n-2))
  - [减绳子](https://humingk.github.io/algorithms-program_others/#三减绳子)  f(n)=Max(n的左边长度，n的右边长度)
  - [骰子的点数](https://humingk.github.io/algorithms-program_array/#二十六骰子的点数)  f(n,s)=f(n-1,s-1)+f(n-1,s-2)+...+f(n-1,s-6)
  - [圆圈中最后剩下的数](https://humingk.github.io/algorithms-program_list/#九圆圈中最后剩下的数)  f(n,m)=[0,(f(n-1,m)+m)%n]

---

### 3. 序列/划分型

主要针对序列/双序列的各种求解，包括不同划分情况

- [序列/划分型](https://humingk.github.io/algorithms-dynamic_programming/#3-%E5%BA%8F%E5%88%97%E5%88%92%E5%88%86%E5%9E%8B)
  - [最长上升子序列(LIS)](https://humingk.github.io/algorithms-dynamic_programming/#一最长上升子序列lis) f(n)=Max(不同上升子序列长度)
  - [最长公共子序列(LCS)](https://humingk.github.io/algorithms-dynamic_programming/#十一最长公共子序列lcs) f(n,m)=[f(n-1,m-1)+1,max(f(n-1,m),f(n,m-1))]
  - [最长公共子串(LSS)](https://humingk.github.io/algorithms-dynamic_programming/#十二最长公共子串lss)  f(n,m)=[f(n-1,m-1)+1,0]
  - [最长连续序列](https://humingk.github.io/algorithms-dynamic_programming/#二最长连续序列) f(n)=Max(子连续序列的长度)
  - [正则表达式匹配](https://humingk.github.io/algorithms-program_string/#二正则表达式匹配) dp[i]\[j\]={dp[i+1]\[j\],dp[i]\[j+2\],dp[i+1]\[j+1\]}

---

### 4. 区间/矩阵型

区间模型的状态表示一般为d(i,j)，表示区间[i, j]上的最优解，然后通过状态转移方程计算出[i+1, j]或者[i, j+1]上的最优解，逐步扩大区间的范围，最终求得[1, len]的最优解

主要以矩阵的形式给出

- [区间/矩阵型](https://humingk.github.io/algorithms-dynamic_programming/#4-%E5%8C%BA%E9%97%B4%E7%9F%A9%E9%98%B5%E5%9E%8B)
  - [礼物的最大价值](https://humingk.github.io/algorithms-program_array/#十五礼物的最大价值) f(n)=Max(f(n)+left,f(n)+up)
  - [连续子数组的最大和](https://humingk.github.io/algorithms-program_array/#十一连续子数组的最大和) f(n)=Max(子连续序列的和)
  - [机器人运动范围](https://humingk.github.io/algorithms-program_array/#六-机器人运动范围) f(n)=(1+左+右+上+下)
  - [完全平方数](https://humingk.github.io/algorithms-dynamic_programming/#%E5%8D%81%E5%AE%8C%E5%85%A8%E5%B9%B3%E6%96%B9%E6%95%B0) dp[i+j\*j]=Min(dp[i]+1,dp[i+j\*j])

---

### 5. 背包型

[背包问题九讲](https://www.kancloud.cn/kancloud/pack/70124)比较系统的讲解了不同类型的背包问题，包括解题思路

- [背包型](https://humingk.github.io/algorithms-dynamic_programming/#5-%E8%83%8C%E5%8C%85%E5%9E%8B)
  - 01背包问题
  - 分割等和子集-01背包
  - 一和零-01背包
  - 零钱兑换-完全背包
  - 目标和-背包

---
### 7. 其他类型

- [其他类型](https://humingk.github.io/algorithms-dynamic_programming/#7-%E5%85%B6%E4%BB%96%E7%B1%BB%E5%9E%8B)
  - 最长回文子串

### 基础数据结构算法

---



## 动态规划经典题型

### 一、最长上升子序列(LIS)

OJ:

[https://leetcode-cn.com/problems/longest-increasing-subsequence/](https://leetcode-cn.com/problems/longest-increasing-subsequence/)

给定一个无序的整数数组，找到其中最长上升子序列的长度。

示例:

输入: [10,9,2,5,3,7,101,18]
输出: 4 
解释: 最长的上升子序列是 [2,3,7,101]，它的长度是 4。
说明:

可能会有多种最长上升子序列的组合，你只需要输出对应的长度即可。
你算法的时间复杂度应该为 O(n2) 。
进阶: 

你能将算法的时间复杂度降低到 O(n log n) 吗?

#### 解法1 动态规划 O(n^2)+O(n)

辅助数组dp[]表示以以当前元素结尾的最长上升子序列长度

遍历每一个元素，都与前面的每一个元素对比，得出当前元素dp值的最大值

最后取dp[]最大值

```java
class Solution {
    public int lengthOfLIS(int[] nums) {
        if(nums==null||nums.length==0){
            return 0;
        }
        // 以当前元素结尾的最长上升子序列长度
        int []dp=new int[nums.length];
        for(int i=0;i<dp.length;i++){
            dp[i]=1;
        }
        // 状态转移
        for(int i=1;i<nums.length;i++){
            for(int j=0;j<i;j++){
                if(nums[i]>nums[j]){
                    dp[i]=Math.max(dp[i],1+dp[j]);
                }
            }
        }
        // 最长上升子序列的最大值
        int max=1;
        for(int i=0;i<dp.length;i++){
            max=Math.max(max,dp[i]);
        }
        return max;
    }
}
```

------

#### 解法2 贪心算法 O(NlogN)+O(N)

辅助数组greed表示遍历过程中当前最长上升子序列

每次遍历，考虑保持greed中的序列中值都为最小值，因为序列前面的值越小，序列后面能容纳的值就越多，序列就更可能更长(**贪婪算法**)

要想找到greed中应该更新为更小的值的位置，因为greed有序，故可以采用二分查找 O(logN)

```java
class Solution {
    public int lengthOfLIS(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        } else if (nums.length == 1) {
            return 1;
        }
        // 当前的最长上升子序列
        int[] greed = new int[nums.length];
        greed[0] = nums[0];
        // greed数组下标
        int j = 0;
        int left = 0, right = j + 1, middle = 0;
        for (int i = 1; i < nums.length; i++) {
            // greed有序，二分查找O(logN)
            left = 0;
            right = j + 1;
            while (left < right) {
                middle = (left + right) >> 1;
                // 右边找
                if (greed[middle] < nums[i]) {
                    left = middle + 1;
                }
                // 左边找
                else {
                    right = middle;
                }
            }
            // 此时greed中的left元素肯定大于当前nums元素，left左边为边界或者小于当前nums元素
            greed[left] = nums[i];
            // greed添加元素
            if (left == j + 1) {
                j++;
            }
        }
        return j + 1;
    }
}
```

------



### 二、最长连续序列

OJ：

[https://leetcode-cn.com/problems/longest-consecutive-sequence/](https://leetcode-cn.com/problems/longest-consecutive-sequence/)

给定一个未排序的整数数组，找出最长连续序列的长度。

要求算法的时间复杂度为 O(n)。

示例:

输入: [100, 4, 200, 1, 3, 2]
输出: 4
解释: 最长连续序列是 [1, 2, 3, 4]。它的长度为 4。




#### 解法1 动态规划(HashSet) O(n)+O(n)

HashSet的存取都是O(1)

将序列存储为hashset，遍历序列，如果当前元素i的前一个元素i-1不存在（通过hashset判断），则从当前元素开始为一个连续序列，i+1,i+2...直到该连续序列末尾

取所有连续序列长度的最大值

```java
import java.util.HashSet;
class Solution {
    public int longestConsecutive(int[] nums) {
        if(nums==null||nums.length==0){
            return 0;
        }
        HashSet<Integer> hashset=new HashSet<>();
        for(int num:nums){
            hashset.add(num);
        }
        // 当前连续序列长度
        int now=0;
        // 所有连续序列长度最大值
        int max=0;
        for(int num:nums){
            // 当前元素的上一个元素不存在
            // 当前元素为该连续序列起始值
            if(!hashset.contains(num-1)){
                now=1;
                while(hashset.contains(++num)){
                    now++;
                }
                // 更新max
                if(max<now){
                    max=now;
                }
            }
        }
        return max;
    }
}
```

------

#### 解法2 动态规划(HashSet) O(n)

HashMap的存取都是O(1)

HashMap中key表示序列中某个元素，value序列中某个元素所在的子连续序列的长度

遍历序列，当前元素i若存在前一个元素i-1或后一个元素i+1,则拼接两个元素所在子连续序列，更新value

取hashmap中value的最大值

------

### 三、01背包

有N件物品和一个容量为V的背包。第i件物品的费用是c[i]，价值是w[i]。求解将哪些物品装入背包可使价值总和最大（恰好装满）。

#### 解法 动态规划  dp\[i\]\[j\] = max{dp\[i-1\]\[j\],dp\[i-1\]\[j-weight\[i\]\]+worth\[i\]}

用dp\[i\]\[j\]表示前i(i<=N)件物品放入一个容量为j(j<=total)的包中，可以获得的最大价值

此时01背包分两种情况:

- 如果第i件物品不放入背包，则表示前i-1件物品放入容量为 total 背包，即：

  dp\[i][j]=dp\[i-1\]\[j\]

- 如果第i件物品放入背包，则表示前i-1件物品放入容量为 j-weight[i] 背包，再放入第i件物品,即：

  dp\[i\]\[j\]=dp\[i-1\]\[j-weight\[i\]\]+worth\[i\] 


状态转移方程为：

 dp\[i\]\[j\] = max{dp\[i-1\]\[j\],dp\[i-1\]\[j-weight\[i\]\]+worth\[i\]}


```java
/**
 * @author humingk
 */
public class test {
    public static void main(String[] args) {
        // 背包的最大承受重量->容量
        int total = 100;
        // 共有N件物品
        int N = 6;
        // 第i件物品的重量
        int[] weight = new int[]{0, 10, 20, 30, 40, 10, 50};
        // 第i件物品的价值
        int[] worth = new int[]{0, 200, 400, 500, 100, 300, 600};


        /**
         * dp[i][j]表示前i(i<=N)件物品放入一个容量为j(j<=total)的包中，可以获得的最大价值
         *
         * 此时01背包分两种情况：
         *  1. 如果第i件物品不放入背包，则表示前i-1件物品放入容量为 total 背包，即：
         *      dp[i][j]=dp[i-1][j]
         *  2. 如果第i件物品放入背包，则表示前i-1件物品放入容量为 total-weight[i] 背包，再放入第i件物品,即：
         *      dp[i][j]=dp[i-1][total-weight[i]]+worth[i]
         *
         * 即：
         *  dp[i][j] = max{dp[i-1][j],dp[i-1][total-weight[i]]+worth[i]}
         *
         */
        int[][] dp = new int[N ][total + 1];
        // 初始化
        for (int j = 0; j < total + 1; j++) {
            dp[0][j] = Integer.MIN_VALUE;
        }

        // 二维数组 01背包
        for (int i = 1; i < N + 1; i++) {
            for (int j = 1; j < total + 1; j++) {
                dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - weight[i]] + worth[i]);
            }
        }
    }
}
```

### 四、分割等和子集01背包

OJ：

[https://leetcode-cn.com/problems/partition-equal-subset-sum/](https://leetcode-cn.com/problems/partition-equal-subset-sum/)

给定一个只包含正整数的非空数组。是否可以将这个数组分割成两个子集，使得两个子集的元素和相等。

注意:

每个数组中的元素不会超过 100
数组的大小不会超过 200
示例 1:

输入: [1, 5, 11, 5]

输出: true

解释: 数组可以分割成 [1, 5, 5] 和 [11].


示例 2:

输入: [1, 2, 3, 5]

输出: false

解释: 数组不能分割成两个元素和相等的子集.




#### 解法1 动态规划 dp\[i\]\[j\]=dp\[i-1\]\[j\] or dp\[i-1\]\[j-num\[i\]\] (j>=num\[i\])

```java
class Solution {
    public boolean canPartition(int[] nums) {
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }
        // 和为奇数不可取
        if (sum % 2 == 1) {
            return false;
        }
        int target = sum / 2;
        /*
        dp[i][j]表示从第0~i之间的数选取出来的数的和为j，如果选到i则为true，没选到则为false
        两种情况：
            如果不选择i,则表示第0 ~ i-1之间选到的数的和为j
            如果选择i，则表示第0 ～ i-1之间选到的数的和为j-num[i],并且此处j>=num[i]

        dp[i][j]=dp[i-1][j] or dp[i-1][j-num[i]] (j>=num[i])
         */
        boolean[][] dp = new boolean[nums.length][target + 1];
        for (int i = 1; i < target + 1; i++) {
            if (nums[0] == i) {
                dp[0][i] = true;
            }
        }
        for (int i = 1; i < nums.length; i++) {
            for (int j = 0; j < target + 1; j++) {
                dp[i][j] = dp[i - 1][j];
                if (j >= nums[i]) {
                    dp[i][j] = dp[i - 1][j] || dp[i - 1][j - nums[i]];
                }
            }
        }
        return dp[nums.length - 1][target];
    }

    public static void main(String[] args) {
        new Solution().canPartition(new int[]{1, 2});
    }
}
```



------



### 四、分割子集和最小差01背包

分割一个数组为两个数组，使得两个数组的和的差的绝对值最小



#### 解法 动态规划 

dp\[i\]\[j\]=max{dp\[i-1\]\[j\],dp\[i-1\]\[j-array\[i-1\]\]+array\[i-1\]}

return sum - dp\[array.length\]\[sum/2\]*2

```java

/**
 * @author humingk
 */
public class Main {

    public static int solution(int[] array) {
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
        }
        int[][] dp = new int[array.length + 1][sum / 2 + 1];
        for (int i = 1; i <= array.length; i++) {
            for (int j = 1; j <= sum / 2; j++) {
                if (j >= array[i - 1]) {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - array[i - 1]] + array[i - 1]);
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        return sum - dp[array.length][sum / 2] * 2;
    }

    public static void main(String[] args) {
        // 2+5+6=13 4+9=13 => 0
        System.out.println(solution(new int[]{2, 4, 5, 6, 9}));
        // 1+0+1+2+4=8 7=7  => 1
        System.out.println(solution(new int[]{1, 0, 1, 7, 2, 4}));
        // 8+2=10 5+4=9 => 1
        System.out.println(solution(new int[]{5, 4, 8, 2}));

    }
}

```





### 五、一和零-01背包

OJ：

[https://leetcode-cn.com/problems/ones-and-zeroes/](https://leetcode-cn.com/problems/ones-and-zeroes/)



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





### 六、零钱兑换-完全背包

OJ：

[https://leetcode-cn.com/problems/coin-change/](https://leetcode-cn.com/problems/coin-change/)



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



### 七、目标和-背包

OJ：

[https://leetcode-cn.com/problems/target-sum/](https://leetcode-cn.com/problems/target-sum/)



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



### 八、最长回文子串

[OJ](https://leetcode-cn.com/problems/longest-palindromic-substring)

给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。

示例 1：

输入: "babad"
输出: "bab"
注意: "aba" 也是一个有效答案。
示例 2：

输入: "cbbd"
输出: "bb"





#### 解法1 动态规划 dp\[i\]\[j\]=dp\[i+1\]\[j-1\] && s\[i\]==s\[j\]

比如babab是否为回文串,取决于aba是否为回文串以及第一个字符b与最后一个字符b是否相等

```java
class Solution {
    public String longestPalindrome(String s) {
        int l = s.length();
        if (l < 2) {
            return s;
        }
        int start = 0, end = -1;
        // dp[i][j] 表示 s的第i-j子字符串 是否为回文串
        boolean[][] dp = new boolean[l][l];
        for (int i = l - 1; i >= 0; i--) {
            for (int j = i; j < l; j++) {
                // 一个字符 || 两个字符相等 || 三个及以上字符 dp[i][j]=dp[i+1][j-1] && s[i]==s[j]
                if (i == j || ((dp[i + 1][j - 1] || i + 1 == j) && s.charAt(i) == s.charAt(j))) {
                    dp[i][j] = true;
                    if (j - i + 1 > end - start + 1) {
                        start = i;
                        end = j;
                    }
                }
            }
        }
        return s.substring(start, end + 1);
    }
}
```

------



### 九、打家劫舍

OJ：

[https://leetcode-cn.com/problems/house-robber/](https://leetcode-cn.com/problems/house-robber/)

你是一个专业的小偷，计划偷窃沿街的房屋。每间房内都藏有一定的现金，影响你偷窃的唯一制约因素就是相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警。

给定一个代表每个房屋存放金额的非负整数数组，计算你在不触动警报装置的情况下，能够偷窃到的最高金额。

示例 1:

输入: [1,2,3,1]
输出: 4
解释: 偷窃 1 号房屋 (金额 = 1) ，然后偷窃 3 号房屋 (金额 = 3)。
     偷窃到的最高金额 = 1 + 3 = 4 。
示例 2:

输入: [2,7,9,3,1]
输出: 12
解释: 偷窃 1 号房屋 (金额 = 2), 偷窃 3 号房屋 (金额 = 9)，接着偷窃 5 号房屋 (金额 = 1)。
     偷窃到的最高金额 = 2 + 9 + 1 = 12 。



#### 解法1 动态规划 O(n)+O(1)

```java
class Solution {
    public int rob(int[] nums) {
        if(nums==null||nums.length==0){
            return 0;
        }else if(nums.length==1){
            return nums[0];
        }
        int prepre=nums[0],pre=Math.max(prepre,nums[1]);
        if(nums.length==2){
            return pre;
        }
        int now=0;
        // f(n)=max(f(n-1),f(n)+f(n-2))
        for(int i=2;i<nums.length;i++){
            now=Math.max(pre,prepre+nums[i]);
            prepre=pre;
            pre=now;
        }
        return now;
    }
}
```



------



### 十、完全平方数

OJ：

[https://leetcode-cn.com/problems/perfect-squares/](https://leetcode-cn.com/problems/perfect-squares/)



给定正整数 n，找到若干个完全平方数（比如 1, 4, 9, 16, ...）使得它们的和等于 n。你需要让组成和的完全平方数的个数最少。

示例 1:

输入: n = 12
输出: 3 
解释: 12 = 4 + 4 + 4.
示例 2:

输入: n = 13
输出: 2
解释: 13 = 4 + 9.




#### 解法1 动态规划 O(n*sqrt(n))+O(n)

所有的完全平方数的个数都可以看成1

所有的非完全平方数的个数都可以看成(比当前数[i+j\*j]小的某个完全平方数[j\*j]的个数)+(非完全平方数[i]的个数)

用于递推的状态转移方程为：dp[i+j\*j]=Min(dp[i]+1,dp[i+j\*j])

其中，可减少计算量，内循环j<=i

eg:

![](../img/alg/square.png)

PS：这里不能用贪心算法，因为这里需要找到个数最少的组合，如果每一次都贪心，比如12的时候采用3+3×3而不是8+2×2,个数反而更多了完全

```java
class Solution {
    public int numSquares(int n) {
       // 存储1-n的数对应的最少完全平方数个数
        int[] dp=new int[n+1];
        // 初始化以便求最小值
        for(int i=1;i<=n;i++){
            dp[i]=Integer.MAX_VALUE;   
        }
        // 初始化完全平方数本身
        for(int i=1;i*i<=n;i++){
            dp[i*i]=1;
        }
        for(int i=1;i<=n;i++){
            for(int j=1;j<=i&&i+j*j<=n;j++){
                // 所有的非完全平方数的个数都可以看成
                // (比当前数[i+j*j]小的某个完全平方数[j*j]的个数)+(非完全平方数[i]的个数)
                dp[i+j*j]=Math.min(dp[i]+1,dp[i+j*j]);
            }
        }
        return dp[n];
    }
}
```

------

#### 解法2 广度优先搜索(BFS)

转化为图的问题,图的节点包括当前值及从n走到当前节点的步数

要想找到个数最少的组合，那么从n开始对i\*i<=n的完全平方数i\*i进行广度优先遍历

当i*i==n的时候，n走到了0节点，由于是广度优先，此时的step+1即为最短路径

eg:

求13

广度优先遍历13的邻居节点，step=0

13-1\*1=12

13-2\*2=9

13-3\*3=4

广度优先遍历12的邻居节点，step=1

12-1\*1=11

12-2\*2=8

12-3\*3=3

广度优先遍历9的邻居节点，step=2

9-1\*1=8 (8已存在，不需要重复添加到遍历列表)

9-2\*2=5

9-3\*3=0 (走到了0节点，最短路径为step+1=3)



```java
import java.util.Queue;
import java.util.LinkedList;
class Solution {
    public class Node{
        public int val;
        public int step;
        public Node(int val,int step){
            this.val=val;
            this.step=step;
        }
    }
     public int numSquares(int n) {
         Node node = new Node(n,0);
         //保存广度优先搜索队列
         Queue<Node> queue = new LinkedList<>();
         queue.offer(node);
         // 标记是否访问此节点
         boolean[] visited = new boolean[n+1];
         // 遍历广度队列
         while(!queue.isEmpty()){
             Node temp=queue.poll();
             // 遍历当前节点的所有邻居节点
             for(int i=1;temp.val>=i*i;i++){
                 int now=temp.val-i*i;
                 // 最短路径
                 if(now==0){
                     return temp.step+1;
                 }
                 // 添加队列及标记
                 if(!visited[now]){
                     visited[now]=true;
                     queue.offer(new Node(now,temp.step+1));
                 }
             }
         }
         return -1;
   }
}
```

------



### 十一、最长公共子序列(LCS)

OJ：

[牛客网-最长公共子序列](https://www.nowcoder.com/practice/c996bbb77dd447d681ec6907ccfb488a?tpId=49&&tqId=29348&rp=1&ru=/activity/oj&qru=/ta/2016test/question-ranking)

对于两个字符串，请设计一个高效算法，求他们的最长公共子序列的长度，这里的最长公共子序列定义为有两个序列U1,U2,U3...Un和V1,V2,V3...Vn,其中Ui&ltUi+1，Vi&ltVi+1。且A[Ui] == B[Vi]。

给定两个字符串**A**和**B**，同时给定两个串的长度**n**和**m**，请返回最长公共子序列的长度。保证两串长度均小于等于300。

测试样例：

```
"1A2C3D4B56",10,"B1D23CA45B6A",12
返回：6
```



#### 解法1 动态规划

递推公式：

![](../img/alg/lcs2.png)

eg:

![](../img/alg/lcs.png)



```java
import java.util.*;

public class LCS {
    public int findLCS(String A, int n, String B, int m) {
        if(A==null||B==null||n==0||m==0){
            return 0;
        }
        // 用于存储A的0～i字符串与B的0～j字符串的最长公共序列个数
        int[][] dp =new int[n+1][m+1];
        for(int i=1;i<=n;i++){
            for(int j=1;j<=m;j++){
                // AB相应字符相等，当前子序列++
                if(A.charAt(i-1)==B.charAt(j-1)){
                    dp[i][j]=dp[i-1][j-1]+1;
                }
                // AB相应字符不相等，A或B右移
                else{
                    dp[i][j]=Math.max(dp[i-1][j],dp[i][j-1]);
                }
            }
        }
        return dp[n][m];
    }
}
```

------



### 十二、最长公共子串(LSS)

OJ：

[牛客网-最长公共子串](https://www.nowcoder.com/practice/02e7cc263f8a49e8b1e1dc9c116f7602?tpId=49&&tqId=29349&rp=1&ru=/activity/oj&qru=/ta/2016test/question-ranking)

对于两个字符串，请设计一个时间复杂度为O(m*n)的算法(这里的m和n为两串的长度)，求出两串的最长公共子串的长度。这里的最长公共子串的定义为两个序列U1,U2,..Un和V1,V2,...Vn，其中Ui + 1 == Ui+1,Vi + 1 == Vi+1，同时Ui == Vi。

给定两个字符串**A**和**B**，同时给定两串的长度**n**和**m**。

测试样例：

```
"1AB2345CD",9,"12345EF",7
返回：4
```



#### 解法1 动态规划

类似[十一、最长公共子序列(LCS)](https://humingk.github.io/algorithms-dynamic_programming/#十一最长公共子序列lcs)，不同的是：

- 这里公共子串应该为连续的序列，所以在遇到AB字符不相等的时候，应该重置公共子串而不是A或B右移

- 返回的最大值不是dp最后一个，而是dp中的最大值（所有公共子串长度的最大值）



```java
import java.util.*;

public class LongestSubstring {
    public int findLongest(String A, int n, String B, int m) {
        if(A==null||B==null||n==0||m==0){
            return 0;
        }
        // 用于存储以dp[i][j]结尾的当前公共子串长度
        int[][] dp =new int[n+1][m+1];
        // 公共子串长度最大值
        int max=0;
        for(int i=1;i<=n;i++){
            for(int j=1;j<=m;j++){
                // AB相应字符相等，当前子串++
                if(A.charAt(i-1)==B.charAt(j-1)){
                    dp[i][j]=dp[i-1][j-1]+1;
                }
                // AB相应字符不相等，当前子串重置
                else{
                    dp[i][j]=0;
                }
                // 更新max
                if(max<dp[i][j]){
                    max=dp[i][j];
                }
            }
        }
        return max;
    }
}
```

------



### 十三、固定预算买最少的东西

小米20190908的Java笔试题



小米之家有很多米粉喜欢的产品，产品种类很多，价格也不同。比如某签字笔1元，某充电宝79元，某电池1元，某电视1999元等

假设库存不限，小明去小米之家买东西，要用光N元预算的钱，请问他最少能买几件产品？

输入：

第1行为产品种类数

接下来的每行为每种产品的价格

最后一行为预算金额

输出：

能买到的最少的产品的件数，无法没有匹配的返回-1

样例输入：

```
2
500
1
1000
```

样例输出：

```
2
```

#### 解法1 动态规划

这道题要想不超时间，需要用动态规划来做

dp数组的下标表示当前的预算，对应的值表示当前预算所能买到的最少的产品数



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



### 十四、二维费用背包

类似题型：https://www.acwing.com/problem/content/description/8/

指定最大disk容量，最大memroy容量

每个进程占用disk，memory，以及能服务的用户数

问能服务的最多用户数

```
输入：
15 10 5,1,1000#2,3,3000#5,2,15000#10,4,16000
输出：
31000
```

#### 解法1 动态规划 dp\[i\]\[j\]\[k\] = max{dp\[i - 1\]\[j\]\[k\], dp\[i - 1\]\[j - x\]\[k - y\] + z}

二维费用背包

PS:

不要在已选择此进程的情况中，忘记加上此进程的用户数

```java
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class Main_vivo {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String inputStr = br.readLine();
        String[] input = inputStr.split(" ");
        int totalDisk = Integer.parseInt(input[0]);
        int totalMemory = Integer.parseInt(input[1]);
        List<Service> services = parseServices(input[2].split("#"));
        int output = solution(totalDisk, totalMemory, services);
        System.out.println(output);
    }

    private static int solution(int totalDisk, int totalMemory, List<Service> services) {

        /*
        dp[i][j][k] 表示前i个进程，分别占用disk=j和memory=k，此时的最大用户数
        分为两种情况：
            如果未选用此进程，则表示前i-1个进程占用了disk=j和memory=k
            如果选用了此进程，则表示前i-1个进程占用了 disk=j-当前进程需要的disk 和 memory=k-当前进程需要的memory,
                以及第i个进程占用了disk=当前进程需要的disk 和 memory=当前进程需要的memory
         */
        int[][][] dp = new int[services.size() + 1][totalDisk + 1][totalMemory + 1];
        for (int i = 2; i <= services.size(); i++) {
            for (int j = 1; j <= totalDisk; j++) {
                for (int k = 1; k <= totalMemory; k++) {
                    if (j >= services.get(i - 1).disk && k >= services.get(i - 1).memory) {
                        dp[i][j][k] = Math.max(dp[i - 1][j][k], dp[i - 1][j - services.get(i - 1).disk][k - services.get(i - 1).memory] + services.get(i - 1).users);
                    }
                    // 不能选择当前进程
                    else {
                        dp[i][j][k] = dp[i - 1][j][k];
                    }
                }
            }
        }
        return dp[services.size()][totalDisk][totalMemory];
    }

    private static List<Service> parseServices(String[] strArr) {
        if (strArr == null || strArr.length == 0) {
            return new ArrayList<Service>(0);
        }
        List<Service> services = new ArrayList<>(strArr.length);
        for (int i = 0; i < strArr.length; i++) {
            String[] serviceArr = strArr[i].split(",");
            int disk = Integer.parseInt(serviceArr[0]);
            int memory = Integer.parseInt(serviceArr[1]);
            int users = Integer.parseInt(serviceArr[2]);
            services.add(new Service(disk, memory, users));
        }
        return services;
    }

    static class Service {
        private int disk;

        private int memory;

        private int users;

        public Service(int disk, int memory, int users) {
            this.disk = disk;
            this.memory = memory;
            this.users = users;
        }

        public int getDisk() {
            return disk;
        }

        public void setDisk(int disk) {
            this.disk = disk;
        }

        public int getMemory() {
            return memory;
        }

        public void setMemory(int memory) {
            this.memory = memory;
        }

        public int getusers() {
            return users;
        }

        public void setusers(int users) {
            this.users = users;
        }
    }
}
```



------



### 十五、最长等差数列

一个数组中，最长的等差数列的元素个数

eg:

2 3 4 5 6 8 的最长等差数列为 2 3 4 5 6 长度为5



#### 解法1 dp\[i\]\[j\]=dp\[i-1\] [ array\[i\]-array\[j\] \] +1

dp\[i\]\[j\] 表示第以array\[i\]为结尾、以j为等差的等差数列的最大长度



```java
import java.util.Arrays;

/**
 * @author humingk
 */
public class Main {

    public static int Solution(int[] array) {
        Arrays.sort(array);
        int res = 1;
        // 等差值的长度
        int length = array[array.length - 1] - array[0];
        int[][] dp = new int[array.length][length + 1];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j <= length; j++) {
                dp[i][j] = 1;
            }
        }
        for (int i = 1; i < array.length; i++) {
            for (int j = i - 1; j >= 0; j--) {
                // 所有能得到当前值array[i]的进位值
                int diff = array[i] - array[j];
                // 对此进位值对应的数列个数+1
                dp[i][diff] = dp[j][diff] + 1;
                res = Math.max(res, dp[i][diff]);
            }
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(Solution(new int[]{3, 8, 4, 5, 6, 2}));
        System.out.println(Solution(new int[]{1, 2, 7, 10, 3, 4, 5, 6}));
    }
}

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



### 十七、



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



### 十八、



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



### 十九、



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

