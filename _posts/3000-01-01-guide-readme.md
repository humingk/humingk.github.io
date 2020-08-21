---
layout: post
title : 博客导航

---

博客总目录，从这里快速找到你想看 （⊙ｏ⊙）

------

# 博客导航

博客地址:<https://humingk.github.io>

------

## 正则表达式相关

- [正则表达式基础](https://humingk.github.io/algorithms-regexp/)

  正则表达式就是那种，写的时候看文档，写完之后能用，过段时间看不懂，下一次写的时候又要看文档...这里总结复习一下基础语法及常用例子，也方便快速查阅

## SQL相关

- [豆瓣电影数据库设计及优化](https://humingk.github.io/mysql-douban_movie/)

  由于以前爬虫获取的豆瓣电影资料库数据库结构考虑不够，导致表之间出现了冗余，这里进行了一系列优化，一方面解决了冗余问题，另一方面也复习了数据库优化知识

- [MySQL修改默认存储路径](https://humingk.github.io/mysql-path/)

  mysql8.0 在ubuntu18上默认的存储路径为 /var/lib/mysql ，这里将其更换为 /home/data

  网上各种帖子不是少了权限就是少了AppArmor服务或socket属性，这里都包括了

- [MySQL 数据库设计注意事项](https://humingk.github.io/mysql-attention/)

  IMDB官方接口数据库是几个tsv文件合计几个G大小，导入MySQL中大概有十几个G，其中还存在冗余，这里将其转化为至少满足三范式的关系型数据库

- [IMDB官方接口数据库转化为满足三范式的关系型数据库](https://humingk.github.io/mysql-imdb/)

  根据《阿里巴巴Java开发手册》以及项目实践《豆瓣电影Plus数据库设计_v2.0》 ,这里总结一些关于数据库表设计相关的注意事项，特别是一些强制性的规约，我们为什么要那样做呢？

- [记一次误删数据库以及数据恢复 (MySQL-binlog-binlog2sql)](https://humingk.github.io/mysql-recovery_delete/)

  手速过快将某一个表的所有数据都删除了,这里使用[binlog2sql脚本](https://github.com/danfengcao/binlog2sql)通过[MySQL的binlog日志记录](https://dev.mysql.com/doc/refman/8.0/en/binary-log.html)来恢复数据

## Python相关

- [网易云音乐API分析](https://humingk.github.io/python-netease_music/)

  网易云音乐官方API有好几个，而且请求参数都用到了AES加密，我还是花了不少时间去折腾它，这里是一些简单总结，包括怎么去分析不同的API请求参数的加密过程，也比较全面的汇集了一些大佬们的精品解析贴

- [一种简易的多线程爬虫框架](https://humingk.github.io/python-spider/)

  之前写豆瓣电影爬虫的时候用过Github大佬[xianhu](https://github.com/xianhu)的[简易多线程爬虫框架](https://github.com/xianhu/PSpider),觉得很不错，结构精巧而实用，后来有时间于是就模仿着也实现了一个简化版本的，一方面学习一下python，另一方面能更加深刻地理解多线程思想

## Java相关

- [Map容器分析 (HashMap)](https://humingk.github.io/java-container_map)

  Java中的Map尤其是HashMap我们经常用到，JDK1.8也给HashMap加了不少新特性，这里总结了一些常用方法，包括部分源代码的简单分析，以及HashMap的一些工作原理的实现解析



以上 

---



## 算法相关(Java)

### 基础算法

#### [排序算法](https://humingk.github.io/algorithms-sort/)

排序算法是最基础的算法知识，程序员必掌握，这里主要参考了[算法第四版](https://algs4.cs.princeton.edu/home/)这本书，比较详细的讲解了各种排序算法的实现方式及区别

- [**常用排序算法区别分析**](https://humingk.github.io/algorithms-sort/#常用排序算法区别分析)
- [堆排序](https://humingk.github.io/algorithms-sort/#一堆排序)
- [快速排序](https://humingk.github.io/algorithms-sort/#二快速排序)
- [归并排序](https://humingk.github.io/algorithms-sort/#三归并排序)
- [希尔排序](https://humingk.github.io/algorithms-sort/#四希尔排序)
- [插入排序 ](https://humingk.github.io/algorithms-sort/#五插入排序)
- [选择排序](https://humingk.github.io/algorithms-sort/#六选择排序)
- [冒泡排序](https://humingk.github.io/algorithms-sort/#七冒泡排序)

常用排序算法中一些经典思想也被很多经典算法题的各种解法所采用，例如以下：

- 类似快速排序的**切分**思想：
  - [调整数组顺序，奇数位于前面](https://humingk.github.io/algorithms-program_array/#七调整数组顺序奇数位于前面) 类快速排序
  - [数组中出现次数超过一半的数字](https://humingk.github.io/algorithms-program_array/#九数组中出现次数超过一半的数字) 类快速排序
  - [最小的k个数](https://humingk.github.io/algorithms-program_array/#十最小的k个数) 类快速排序
- 类似归并排序的**归并**思想：
  - [合并两个有序的链表](https://humingk.github.io/algorithms-program_list/#六合并两个有序的链表) 类归并排序
  - [数组中的逆序对](https://humingk.github.io/algorithms-program_array/#十七数组中的逆序对) 类归并排序
  - [两个有序数组的中位数](https://humingk.github.io/algorithms-program_array/#三十一两个有序数组的中位数) 类归并排序 / 类归并排序优化
- 类似堆排序的**最大堆和最小堆**：
  - [最小的k个数](https://humingk.github.io/algorithms-program_array/#十最小的k个数) 构造最大堆
  - [数据流中的中位数](https://humingk.github.io/algorithms-program_array/#十二数据流中的中位数) 最大堆和最小堆
- 其他**变种**的排序算法：
  - [字符串的字典序排列](https://humingk.github.io/algorithms-program_string/#四字符串的字典序排列) 字典序排列算法
  - [把数组排成最小的数](https://humingk.github.io/algorithms-program_array/#十四把数组排成最小的数)  自定义比较规则的排序
  - [两数之和](https://humingk.github.io/algorithms-program_array/#三十两数之和) 类选择排序

#### [选择算法](https://humingk.github.io/algorithms-search/)

选择算法也是最基础的算法知识，这里主要参考了[算法第四版](https://algs4.cs.princeton.edu/home/)这本书，比较详细的讲解了各种选择算法的实现方式，其中包括二叉树、红黑树等基础数据结构的实现原理

- [顺序查找(无序链表)](https://humingk.github.io/algorithms-search/#顺序查找无序链表)
- [二分查找(有序数组)](https://humingk.github.io/algorithms-search/#二分查找有序数组)
- [二叉查找(二叉搜索树)](https://humingk.github.io/algorithms-search/#二叉树查找二叉查找树)
- [２-3查找(红黑树) ](https://humingk.github.io/algorithms-search/#2-3查找树红黑树)
- [ 拉链法查找(链表数组)](https://humingk.github.io/algorithms-search/#拉链法链表数组)
- [线性探测法查找(并行数组)](https://humingk.github.io/algorithms-search/#线性探测法并行数组)

常用选择算法中一些经典思想也被很多经典算法题的各种解法采用，例如以下：

- 类似二分查找中的**二分**思想：
  - [数组中重复的数字](https://humingk.github.io/algorithms-program_array/#二数组中重复的数字) 二分法
  - [旋转数组的最小值](https://humingk.github.io/algorithms-program_array/#四旋转数组的最小值) 二分法
  - [在排序数组中查找数字](https://humingk.github.io/algorithms-program_array/#%E5%8D%81%E5%85%AB%E6%95%B0%E5%AD%97%E5%9C%A8%E6%8E%92%E5%BA%8F%E6%95%B0%E7%BB%84%E4%B8%AD%E5%87%BA%E7%8E%B0%E7%9A%84%E6%AC%A1%E6%95%B0)
    - [数字在排序数组中出现的次数](https://humingk.github.io/algorithms-program_array/#十八数字在排序数组中出现的次数) 二分查找再左右遍历 / 二分查找数字始末位置 
    - [0到n-1中缺失的数字](https://humingk.github.io/algorithms-program_array/#十九0到n-1中缺失的数字) 二分查找
    - [数组中数值和下标相等的元素](https://humingk.github.io/algorithms-program_array/#二十数组中数值和下标相等的元素) 二分查找
    - [两个有序数组的中位数](https://humingk.github.io/algorithms-program_array/#三十一两个有序数组的中位数)  类二分查找
  
- 类似拉链法/线性探测法的**哈希表**思想：

  哈希表的插入和查询操作平均时间复杂度为O(1),最差情况(key总是碰撞)为O(n)

  而且哈希表的空间复杂度不一定为O(n)，可以用int型的位数、固定长度的整数、字母表等等，如下：

  - [数组中只出现一次的两个数字](https://humingk.github.io/algorithms-program_array/#二十一数组中只出现一次的两个数字) 哈希表  
  - [数组中唯一只出现一次的数字](https://humingk.github.io/algorithms-program_array/#二十二-数组中唯一只出现一次的数字) 哈希表
  - [数组中重复的数字](https://humingk.github.io/algorithms-program_array/#二数组中重复的数字) 另建哈希表  / 自身哈希表
  - [扑克牌顺子](https://humingk.github.io/algorithms-program_array/#二十七扑克牌顺子) 位数哈希表 
  - [复杂链表的复制](https://humingk.github.io/algorithms-program_list/#七复杂链表的复制) 递归+哈希表
  - [第一个只出现一次的字符](https://humingk.github.io/algorithms-program_string/#六第一个只出现一次的字符) 自定义字母哈希表
  - [字符流中第一个不重复的字符](https://humingk.github.io/algorithms-program_string/#七字符流中第一个不重复的字符) 自定义ASCII哈希表
  - [两数之和](https://humingk.github.io/algorithms-program_array/#三十两数之和) 哈希表 
  - [最长不含重复字符的子字符串](https://humingk.github.io/algorithms-program_string/#十一最长不含重复字符的子字符串) 滑动窗口+哈希集合 / 滑动窗口优化+哈希表 / 滑动窗口优化+哈希表优化 / 动态规划+哈希表 f(n)=Max{上一子字符串长度++，新子字符串长度}
  - [罗马数字转整数](https://humingk.github.io/algorithms-program_string/#十二罗马数字转整数) 遍历+哈希表

### [动态规划算法](https://humingk.github.io/algorithms-dynamic_programming)

这里是动态规划算法的总结，包括一些具有代表性的相关题型，其中包括矩阵型、序列/双序列型、区间型、背包型等等，默认测试平台为[leetcode](https://leetcode-cn.com/problemset/all/)，部分测试平台为[牛客网剑指offer题单](https://www.nowcoder.com/ta/coding-interviews),还有一部分在[AcWing题库](https://www.acwing.com/problem/)上测试

#### [贪心算法型](https://humingk.github.io/algorithms-dynamic_programming/#1-%E8%B4%AA%E5%BF%83%E7%AE%97%E6%B3%95%E5%9E%8B)

- [减绳子](https://humingk.github.io/algorithms-program_others/#三减绳子)  f(n)=Max{n的左边长度，n的右边长度}
- [最长上升子序列](https://humingk.github.io/algorithms-dynamic_programming/#一最长上升子序列) f(n)=Max{不同上升子序列长度}
- [课程表](https://humingk.github.io/algorithms-program_graph/#二课程表) 拓扑排序 - f(n)=(选取邻接节点中最先没有前驱节点的节点)[十二、罗马数字转整数](https://humingk.github.io/algorithms-program_string/#十二罗马数字转整数) 遍历+哈希表

#### [递推/划分型](https://humingk.github.io/algorithms-dynamic_programming/#2-%E9%80%92%E6%8E%A8%E5%88%92%E5%88%86%E5%9E%8B)

- [斐波那契数列](https://humingk.github.io/algorithms-program_others/#%E4%BA%8C%E6%96%90%E6%B3%A2%E9%82%A3%E5%A5%91%E6%95%B0%E5%88%97)  f(n)=f(n-1)+f(n-2)
- [丑数](https://humingk.github.io/algorithms-program_others/#九丑数)  f(n)=Min{当前丑数x2,当前丑数x3,当前丑数x5}
- [十一、最长不含重复字符的子字符串](https://humingk.github.io/algorithms-program_string/#十一最长不含重复字符的子字符串) f(n)=Max{上一子字符串长度++，新子字符串长度}
- [把数字翻译成字符串](https://humingk.github.io/algorithms-program_string/#五把数字翻译成字符串) f(n)=[f(n+1),f(n+1)+f(n+2)]
- [打家劫舍](https://humingk.github.io/algorithms-dynamic_programming/#%E4%B9%9D%E6%89%93%E5%AE%B6%E5%8A%AB%E8%88%8D) f(n)=max{f(n-1),f(n)+f(n-2)}
- [减绳子](https://humingk.github.io/algorithms-program_others/#三减绳子)  f(n)=Max{n的左边长度，n的右边长度}
- [骰子的点数](https://humingk.github.io/algorithms-program_array/#二十六骰子的点数)  f(n,s)=f(n-1,s-1)+f(n-1,s-2)+...+f(n-1,s-6)
- [圆圈中最后剩下的数](https://humingk.github.io/algorithms-program_list/#九圆圈中最后剩下的数)  f(n,m)=[0,(f(n-1,m)+m)%n]

#### [序列/划分型](https://humingk.github.io/algorithms-dynamic_programming/#3-%E5%BA%8F%E5%88%97%E5%88%92%E5%88%86%E5%9E%8B)

- [最长上升子序列(LIS)](https://humingk.github.io/algorithms-dynamic_programming/#一最长上升子序列lis) f(n)=Max{不同上升子序列长度}
- [最长公共子序列(LCS)](https://humingk.github.io/algorithms-dynamic_programming/#十一最长公共子序列lcs) f(n,m)=[f(n-1,m-1)+1,max(f(n-1,m),f(n,m-1))]
- [最长公共子串(LSS)](https://humingk.github.io/algorithms-dynamic_programming/#十二最长公共子串lss)  f(n,m)=[f(n-1,m-1)+1,0]
- [最长连续序列](https://humingk.github.io/algorithms-dynamic_programming/#二最长连续序列) f(n)=Max{子连续序列的长度}
- [正则表达式匹配](https://humingk.github.io/algorithms-program_string/#二正则表达式匹配) dp[i]\[j\]={dp[i+1]\[j\],dp[i]\[j+2\],dp[i+1]\[j+1\]}

#### [区间/矩阵型](https://humingk.github.io/algorithms-dynamic_programming/#4-%E5%8C%BA%E9%97%B4%E7%9F%A9%E9%98%B5%E5%9E%8B)

- [礼物的最大价值](https://humingk.github.io/algorithms-program_array/#十五礼物的最大价值) f(n)=Max{f(n)+left,f(n)+up}
- [连续子数组的最大和](https://humingk.github.io/algorithms-program_array/#十一连续子数组的最大和) f(n)=Max{上一个子序列和+当前元素值，当前元素值}
- [机器人运动范围](https://humingk.github.io/algorithms-program_array/#六机器人运动范围) f(n)=(1+左+右+上+下)
- [完全平方数](https://humingk.github.io/algorithms-dynamic_programming/#%E5%8D%81%E5%AE%8C%E5%85%A8%E5%B9%B3%E6%96%B9%E6%95%B0) dp[i+j\*j]=Min{dp[i]+1,dp[i+j\*j]}

#### [背包型](https://humingk.github.io/algorithms-dynamic_programming/#5-%E8%83%8C%E5%8C%85%E5%9E%8B)

- [01背包问题](https://humingk.github.io/algorithms-dynamic_programming/#三01背包) dp\[i\]\[j\] = max{dp\[i-1\]\[j\],dp\[i-1\]\[j-weight\[i\]\]+worth\[i\]}
- [分割等和子集-01背包](https://humingk.github.io/algorithms-dynamic_programming/#四分割等和子集01背包) dp\[i\]\[j\]=dp\[i-1\]\[j\] or dp\[i-1\]\[j-num\[i\]\] (j>=num\[i\])
- [分割子集和最小差01背包](https://humingk.github.io/algorithms-dynamic_programming/#四分割子集和最小差01背包) dp\[i\]\[j\]=max{dp\[i-1\]\[j\],dp\[i-1\]\[j-array\[i-1\]\]+array\[i-1\]}
- [二维费用背包](https://humingk.github.io/algorithms-dynamic_programming/#十四二维费用背包) dp\[i\]\[j\]\[k\] = max{dp\[i - 1\]\[j\]\[k\], dp\[i - 1\]\[j - x\]\[k - y\] + z}
- 一和零-01背包
- 零钱兑换-完全背包
- 目标和-背包

#### [其他类型](https://humingk.github.io/algorithms-dynamic_programming/#7-%E5%85%B6%E4%BB%96%E7%B1%BB%E5%9E%8B)

- [最长等差数列](https://humingk.github.io/algorithms-dynamic_programming/#十五最长等差数列) dp\[i\]\[j\]=dp\[i-1\] [ array\[i\]-array\[j\] \] +1
- 最长回文子串

### 基础数据结构算法

这里主要是[剑指offer第二版](https://github.com/zhedahht/CodingInterviewChinese2)上的经典算法例题(除图相关以外)，涵盖了各种基础数据结构的基本应用，其中大约有一半实现了自定义的测试用例方法,也包括一些不同的解法解析,主要的测试平台为[牛客网剑指offer题单](https://www.nowcoder.com/ta/coding-interviews)(默认OJ平台),还有一部分在[AcWing题库](https://www.acwing.com/problem/)以及[leetcode](https://leetcode-cn.com/problemset/all/)上测试(有标注)

#### [数组相关](https://humingk.github.io/algorithms-program_array/)

- 在排序数组中查找数字
  - [数字在排序数组中出现的次数](https://humingk.github.io/algorithms-program_array/#十八数字在排序数组中出现的次数) 循环 /二分查找再左右遍历 / 二分查找数字始末位置 
  - [0到n-1中缺失的数字](https://humingk.github.io/algorithms-program_array/#十九0到n-1中缺失的数字) 循环 / 和差 / 二分查找
  - [数组中数值和下标相等的元素](https://humingk.github.io/algorithms-program_array/#二十数组中数值和下标相等的元素) 循环 / 二分查找
- 数组中数字出现次数
  - [整数中1出现的次数](https://humingk.github.io/algorithms-program_array/#十三整数中1出现的次数) 循环判断累加 / 位数循环分情况累加 
  - [数组中只出现一次的两个数字](https://humingk.github.io/algorithms-program_array/#二十一数组中只出现一次的两个数字) 哈希表 / 数组位数异或 
  - [ 数组中唯一只出现一次的数字](https://humingk.github.io/algorithms-program_array/#二十二-数组中唯一只出现一次的数字) 哈希表 / 数组位数相加
  - [数组中出现次数超过一半的数字](https://humingk.github.io/algorithms-program_array/#九数组中出现次数超过一半的数字) 类快速排序 / 贪吃蛇吃果子
  - [数组中重复的数字](https://humingk.github.io/algorithms-program_array/#二数组中重复的数字) 另建哈希表  / 自身哈希表 / 二分法
- 和为s的数字
  - [和为s的两个数字](https://humingk.github.io/algorithms-program_array/#二十三和为s的两个数字) 双循环 / 前后遍历
  - [和为s的连续正数序列](https://humingk.github.io/algorithms-program_array/#二十四和为s的连续正数序列) 滑动窗口 / 滑动窗口优化版
- 数组排序问题
  - [最小的k个数](https://humingk.github.io/algorithms-program_array/#十最小的k个数) 先排序再取值  / 类快速排序 / 构造最大堆
  - [调整数组顺序，奇数位于前面](https://humingk.github.io/algorithms-program_array/#七调整数组顺序奇数位于前面) 类快速排序 / 另建数组 / 类插入排序 / 类冒泡排序 
  - [旋转数组的最小值](https://humingk.github.io/algorithms-program_array/#四旋转数组的最小值) 二分法 / 顺序查找
  - [数组中的逆序对](https://humingk.github.io/algorithms-program_array/#十七数组中的逆序对) 循环 / 类归并排序
  - [数据流中的中位数](https://humingk.github.io/algorithms-program_array/#十二数据流中的中位数) 无序数组 / 有序链表 / 二叉搜索树 / 平衡二叉搜索树 / 最大堆和最小堆
  - [两个有序数组的中位数](https://humingk.github.io/algorithms-program_array/#三十一两个有序数组的中位数) 类归并排序 / 类归并排序优化 / 类二分查找
  - 
- 数组元素组合问题
  - [把数组排成最小的数](https://humingk.github.io/algorithms-program_array/#十四把数组排成最小的数)  全排列再取最小值 / 自定义比较规则的排序
  - [连续子数组的最大和](https://humingk.github.io/algorithms-program_array/#十一连续子数组的最大和) 先求所有子数组的和 / 动态规划 Max{上一个子序列和+当前元素值,当前元素值}
- 二维数组问题
  - [矩阵中的路径](https://humingk.github.io/algorithms-program_array/#五矩阵中的路径) 回溯法递归
  - [机器人运动范围](https://humingk.github.io/algorithms-program_array/#六机器人运动范围) 回溯法递归
  - [顺时针打印矩阵](https://humingk.github.io/algorithms-program_array/#八顺时针打印矩阵) 向右旋转遍历 / 顺时针模拟
  - [礼物的最大价值](https://humingk.github.io/algorithms-program_array/#十五礼物的最大价值) BFS / 动态规划(二维、一维、无辅助空间) f(n)=Max{f(n)+left,f(n)+up}
  - [在二维数组中查找](https://humingk.github.io/algorithms-program_array/#三在二维数组中查找) 右上角开始查找
- 其他类型
  - [滑动窗口的最大值](https://humingk.github.io/algorithms-program_array/#二十五滑动窗口的最大值) 循环 / 双向队列保存最大值
  - [骰子的点数](https://humingk.github.io/algorithms-program_array/#二十六骰子的点数) 递归 / 动态规划(循环) f(n,s)=f(n-1,s-1)+f(n-1,s-2)+...+f(n-1,s-6)
  - [扑克牌顺子](https://humingk.github.io/algorithms-program_array/#二十七扑克牌顺子) 位数哈希表 
  - [构建乘积数组](https://humingk.github.io/algorithms-program_array/#二十八-构建乘积数组) 左右累乘
  - [逆波兰表达式求值](https://humingk.github.io/algorithms-program_array/#二十九逆波兰表达式求值) 栈
  - [两数之和](https://humingk.github.io/algorithms-program_array/#三十两数之和) 类选择排序  /  哈希表 
  - [最小差值](https://humingk.github.io/algorithms-program_array/#三十二最小差值) 取最大值最小值

#### [链表相关](https://humingk.github.io/algorithms-program_list/)

- 链表节点定位
  - [两个链表的第一个公共结点](https://humingk.github.io/algorithms-program_list/#八两个链表的第一个公共结点) 循环 / 双栈 / 双指针间隔距离差
  - [删除链表的节点](https://humingk.github.io/algorithms-program_list/#二删除链表的节点)  循环遍历 / 用删除节点的下一节点覆盖删除节点
  - [返回倒数第k个节点](https://humingk.github.io/algorithms-program_list/#三返回倒数第k个节点) 双指针间隔K同步移位
  - [链表中环的入口节点](https://humingk.github.io/algorithms-program_list/#四链表中环的入口节点) 快慢指针确定环，快慢指针计数确定环数，双指针间隔环数确定入口
- 其他类型
  - [从尾到头打印链表](https://humingk.github.io/algorithms-program_list/#一从尾到头打印链表) 栈 / 递归
  - [反转链表](https://humingk.github.io/algorithms-program_list/#五反转链表) 三个相邻指针改变链表方向并后移
  - [合并两个有序的链表](https://humingk.github.io/algorithms-program_list/#六合并两个有序的链表) 类归并排序
  - [复杂链表的复制](https://humingk.github.io/algorithms-program_list/#七复杂链表的复制) 递归+哈希表 / 复制新节点到旧节点后
  - [圆圈中最后剩下的数](https://humingk.github.io/algorithms-program_list/#九圆圈中最后剩下的数) 自定义链表环 / 动态规划 f(n,m)=[0,(f(n-1,m)+m)%n]
  - [两数相加](https://humingk.github.io/algorithms-program_list/#十两数相加) 模拟位数相加

#### [字符串相关](https://humingk.github.io/algorithms-program_string/)

- 字符串转换/替换
  - [字符串转换成整数](https://humingk.github.io/algorithms-program_string/#九字符串转换成整数) 多条件考虑:正负数、非法字符、符号位置、溢出
  - [把数字翻译成字符串](https://humingk.github.io/algorithms-program_string/#五把数字翻译成字符串) 动态规划 f(n)=[f(n+1),f(n+1)+f(n+2)]
  - [替换空格](https://humingk.github.io/algorithms-program_string/#一替换空格) StringBuffer+双指针后移位 
- 正则表达式
  - [正则表达式匹配](https://humingk.github.io/algorithms-program_string/#二正则表达式匹配) 双指针移位 / 动态规划 dp[i]\[j\]={dp[i+1]\[j\],dp[i]\[j+2\],dp[i+1]\[j+1\]}
  - [表示数值的字符串](https://humingk.github.io/algorithms-program_string/#三表示数值的字符串) 正则表达式判断 A[.[B]\[e\|EC\]] 
- 字符串翻转
  - [翻转单词顺序](https://humingk.github.io/algorithms-program_string/#八翻转字符串-翻转单词顺序) 栈存放单词起始值 / 字符串数组存放单词 / 翻转整体再翻转单词
  - [左旋转字符串](https://humingk.github.io/algorithms-program_string/#八翻转字符串-左旋转字符串) 字符串拼接 / 翻转部分再翻转整体
- 其他类型
  - [字符串的字典序排列](https://humingk.github.io/algorithms-program_string/#四字符串的字典序排列) 递归求所有排列再排序成字典序 / 字典序排列算法
  - [第一个只出现一次的字符](https://humingk.github.io/algorithms-program_string/#六第一个只出现一次的字符) 循环 / 自定义字母哈希表
  - [字符流中第一个不重复的字符](https://humingk.github.io/algorithms-program_string/#七字符流中第一个不重复的字符) 自定义ASCII哈希表
  - [索引处的解码字符串](https://humingk.github.io/algorithms-program_string/#十索引处的解码字符串) 保存解码字符串(超时) /  逆向求解
  - [最长不含重复字符的子字符串](https://humingk.github.io/algorithms-program_string/#十一最长不含重复字符的子字符串) 暴力循环 / 滑动窗口+哈希集合 / 滑动窗口优化+哈希表 / 滑动窗口优化+哈希表优化 / 动态规划+哈希表 f(n)=Max{上一子字符串长度++，新子字符串长度}
  - [罗马数字转整数](https://humingk.github.io/algorithms-program_string/#十二罗马数字转整数) 遍历+哈希表

#### [树相关](https://humingk.github.io/algorithms-program_tree/)

- [重建二叉树](https://humingk.github.io/algorithms-program_tree/#一重建二叉树) 递归
- [二叉树的下一节点](https://humingk.github.io/algorithms-program_tree/#二二叉树的下一节点) 分情况
- [树的子结构](https://humingk.github.io/algorithms-program_tree/#三树的子结构) 递归 / 与、或短路递归
- [镜像二叉树](https://humingk.github.io/algorithms-program_tree/#四镜像二叉树) 递归
- [对称二叉树](https://humingk.github.io/algorithms-program_tree/#五对称二叉树) 遍历
- [从上到下打印二叉树](https://humingk.github.io/algorithms-program_tree/#六从上到下打印二叉树) 打印一行 / 打印分行 / 打印之字行
- [二叉搜索树的后序遍历序列](https://humingk.github.io/algorithms-program_tree/#七二叉搜索树的后序遍历序列) 递归二分
- [二叉树中和为某一值的路径](https://humingk.github.io/algorithms-program_tree/#八二叉树中和为某一值的路径) 递归(DFS)
- [二叉搜索树与双向链表](https://humingk.github.io/algorithms-program_tree/#九二叉搜索树与双向链表) 中序遍历递归 / 递归构造
- [二叉树序列化](https://humingk.github.io/algorithms-program_tree/#十二叉树序列化) 先序遍历递归
- [二叉搜索树的第K个节点](https://humingk.github.io/algorithms-program_tree/#十二二叉搜索树的第k个节点) 中序遍历递归
- [二叉树的深度](https://humingk.github.io/algorithms-program_tree/#十三二叉树的深度) 递归
- [平衡二叉树](https://humingk.github.io/algorithms-program_tree/#十四平衡二叉树) 从上到下深度差比较 / 后序遍历深度差比较
- [二叉搜索树的最低公共祖先](https://humingk.github.io/algorithms-program_tree/#十五-二叉搜索树的最低公共祖先) 从上到下递归
- [普通二叉树的最低公共祖先](https://humingk.github.io/algorithms-program_tree/#十六普通二叉树的最低公共祖先) 从下到上递归 / 路径比较
- [二叉树最大宽度](https://humingk.github.io/algorithms-program_tree/#十七二叉树最大宽度) 层次遍历 / DFS遍历
- [最小高度数](https://humingk.github.io/algorithms-program_tree/#十八最小高度数) 遍历求所有树的高度(超时) / 去除叶节点
- [已知中序和后序求前序遍历](https://humingk.github.io/algorithms-program_tree/#十九已知中序和后序求前序遍历) 递归

#### [图相关](https://humingk.github.io/algorithms-program_graph/)

- [克隆图](https://humingk.github.io/algorithms-program_graph/#%E4%B8%80%E5%85%8B%E9%9A%86%E5%9B%BE) BFS / DFS
- [完全平方数](https://humingk.github.io/algorithms-dynamic_programming/#%E5%8D%81%E5%AE%8C%E5%85%A8%E5%B9%B3%E6%96%B9%E6%95%B0) BFS
- [课程表](https://humingk.github.io/algorithms-program_graph/#二课程表) DFS
- [单词接龙](https://humingk.github.io/algorithms-program_graph/#%E5%9B%9B%E5%8D%95%E8%AF%8D%E6%8E%A5%E9%BE%99) BFS /  双向BFS
- 岛屿数量
- [Floyd算法](https://humingk.github.io/algorithms-program_graph/#六floyd算法)
- [Dijkstra算法](https://humingk.github.io/algorithms-program_graph/#七dijkstra算法)

#### [其他相关](https://humingk.github.io/algorithms-program_others/)

- [用两个栈实现队列](https://humingk.github.io/algorithms-program_others/#一用两个栈实现队列)
- [斐波那契数列](https://humingk.github.io/algorithms-program_others/#二斐波那契数列)
- [减绳子](https://humingk.github.io/algorithms-program_others/#三减绳子)
- [二进制中1的个数](https://humingk.github.io/algorithms-program_others/#四二进制中1的个数) 将数右移比较 / 将1左移比较 / 先减1再与 / MIT HAKMEM / Hamming Weight
- [double数值的int次方](https://humingk.github.io/algorithms-program_others/#五double数值的int次方)
- [打印从1到最大的n位数](https://humingk.github.io/algorithms-program_others/#六打印从1到最大的n位数)
- [包含min的栈](https://humingk.github.io/algorithms-program_others/#七包含min的栈)
- [数字序列中某一位的数字](https://humingk.github.io/algorithms-program_others/#八数字序列中某一位的数字)
- [丑数](https://humingk.github.io/algorithms-program_others/#九丑数)
- [计算1+2+3+...+n](https://humingk.github.io/algorithms-program_others/#十计算1+2+3+...+n) 逻辑与的短路特性+递归  / 异常退出+递归 / 位运算乘法
- [整数反转](https://humingk.github.io/algorithms-program_others/#十一整数反转) 循环
- [ 不用加减乘除做加法](https://humingk.github.io/algorithms-program_others/#十二不用加减乘除做加法) 计算机的加法器原理
- [ 电话号码的字母组合](https://humingk.github.io/algorithms-program_others/#十二不用加减乘除做加法) 递归





---

谢谢阅读

欢迎  [Star it](https://github.com/humingk/humingk.github.io) / [Fork it](https://github.com/humingk/humingk.github.io) / [Follow me](https://github.com/humingk) / [New Issues](https://github.com/humingk/humingk.github.io/issues) / [Pull Requests](https://github.com/humingk/humingk.github.io/pulls)