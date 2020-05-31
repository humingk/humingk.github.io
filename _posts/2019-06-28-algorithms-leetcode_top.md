---
layout: post
title : leetcode精选题笔记
categories : algorithms
description : 
keywords :
---



默认测试平台：https://leetcode-cn.com/problemset/hot-100/

---

###  一、两数之和

给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。

你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。

示例:

给定 nums = [2, 7, 11, 15], target = 9

因为 nums[0] + nums[1] = 2 + 7 = 9
所以返回 [0, 1]

#### 测试用例

1. 空数组
2. 空指针
3. 符合条件
4. 不符合条件

#### 解决方法

1. 两次循环，依次判断 ,O(n^2)+O(1)

------

2. 先存入hash表，再依次判断，O(n)+O(n)

------

3. 存入hash表的同时判断，O(n)+O(n)

```java
class Solution {
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> hashmap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int now = target - nums[i];
            if (hashmap.containsKey(now)) {
                return new int[]{i, hashmap.get(now)};
            }
            hashmap.put(nums[i],i);
        }
        return null;
    }
}
```

------



### 二、两数相加（链表）

给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。

如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。

您可以假设除了数字 0 之外，这两个数都不会以 0 开头。

示例：

输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
输出：7 -> 0 -> 8
原因：342 + 465 = 807

#### 测试用例

1. 符合条件
2. 两个空链表
3. 单个空链表
4. 链表长度不相等
5. 链表长度相等
6. 两个链表都为最后一位时候产生进位
7. 

#### 解决方法



1.  循环判断，O(n)+O(n)

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
    class Solution {
        public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
            ListNode result = new ListNode(-1);
            ListNode pNode = result;
            int carry = 0;
            // 可能都为最后一位时产生进位
            while (l1 != null || l2 != null ||(l1==null&&l2==null&&carry==1)) {
                int sum = 0;
                if (l1 != null) {
                    sum += l1.val;
                    l1=l1.next;
                }
                if (l2 != null) {
                    sum += l2.val;
                    l2=l2.next;
                }
                sum += carry;
                if (sum < 10) {
                    carry = 0;
                } else {
                    carry = 1;
                    sum -= 10;
                }
                pNode.next = new ListNode(sum);
                pNode = pNode.next;
            }
            return result.next;
        }
    }
```

------





### 三、无重复字符的最长字符子串

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



#### 测试用例

1. 符合条件
2. 不符合条件
3. 全是相同字符
4. 全是不同字符
5. 不重复字符子串重复出现

#### 解决方法



1. 循环判断所有可能的子字符串是否重复,，O(n^3)+O(n)

------

2. 设置一个i-j滑动窗口，用HashSet存储i-j中的元素，故i-j始终不包含重复元素

   j向后移，i暂时不动，若j遇到重复元素，则删掉前一个重复元素，i继续后移

   O(n)+O(n)

```java
class Solution {
    public int lengthOfLongestSubstring(String s) {
        Set<Character> hashSet=new HashSet<>();
        int i=0,j=0,max=0;
        int length=s.length();
        while(i<length && j<length){
            // 右移的j遇到重复元素
            if(hashSet.contains(s.charAt(j))){
                hashSet.remove(s.charAt(i++));
            }
            // 继续右移
            else{
                hashSet.add(s.charAt(j++));
                max=Math.max(max,j-i);
            }
        }
        return max;
    }
}
```

------

3. 滑动窗口优化，用HashMap存储i-j中的元素

   j右移，若遇到重复元素，跳过整个窗口

```java
class Solution {
    public int lengthOfLongestSubstring(String s) {
        Map<Character,Integer> map=new HashMap<>();
        int max=0;
        int length=s.length();	 
        for(int i=0,j=0;j<length;j++){
            char now=s.charAt(j);
            // j向右移遇到重复元素，跳过此窗口
            if(map.containsKey(now)){
                i=Math.max(i,map.get(now));
            }
            // 与方法二不同，此处j还没有++
            max=Math.max(max,j-i+1);
            // 此处j+1，i取第一个重复元素后面的元素
            map.put(now,j+1);
        }
        
        return max;
    }
}
```

------



### 四、



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



### 五、



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



### 六、



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



### 七、



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



### 八、



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



### 九、



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



### 十、



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



