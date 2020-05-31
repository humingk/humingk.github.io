---
layout: post
title : 典型编程题笔记(2)-链表(Java)
categories : algorithms
description : 
keywords :
---

- 典型的编程题，包括测试

- [github源代码地址](https://github.com/humingk/humingk.github.io/tree/master/source_code/offer/list)

- 若未标明，默认的测试平台为：[牛客网](https://www.nowcoder.com)

---

### 链表模板

此链表模板分两种情况：

- 默认单链表第一个节点为头指针，包含单链表的长度值，符合本代码情况
- 默认单链表第一个节点为头指针，符合部分算法题测试代码情况

分别在三个地方进行切换：

1. 测试用例生成链表头节点数组
2. 算法部分传入头节点
3. 算法部分返回链表头节点

PS：从第二题开始，第八题结束，采用此模板:

```java
package list;

import java.util.LinkedList;
import java.util.List;

/**
 * 此链表模板分两种情况：
 *
 * - 默认单链表第一个节点为头指针，包含单链表的长度值，符合本代码情况
 * - 默认单链表第一个节点为头指针，符合部分算法题测试代码情况
 *
 * 分别在三个地方进行切换：
 *
 * 1. 测试用例生成链表头节点数组
 * 2. 算法部分传入头节点
 * 3. 算法部分返回链表头节点
 *
 * @author humingk
 */
public class ListBase {
    /**
     * 此链表头节点
     */
    public ListNode first;

    /**
     * 此链表结构
     */
    public class ListNode {
        int val;
        ListNode next;

        public ListNode() {
            this.val = -1;
            this.next = null;
        }

        public ListNode(int val) {
            this.val = val;
            this.next = null;
        }

        public ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    /**
     * 创建链表,头指针存放链表长度
     *
     * @param values
     * @return
     */
    public ListNode createLinkList(int[] values) {
        ListNode head = new ListNode(values.length, null);
        for (int i = 0; i < values.length; i++) {
            ListNode node = new ListNode(values[i], null);
            node.next = head.next;
            head.next = node;
        }
        first = head;
        return head;
    }

    /**
     * 打印链表
     *
     * @param listNode
     */
    public void showLinkList(ListNode listNode) {
        while (listNode != null) {
            System.out.print(listNode.val + " ");
            if (listNode.next != null) {
                System.out.print("-> ");
            }
            listNode = listNode.next;
        }
        System.out.println();
    }
    // ------------------------------------------------------------------------------------------
    // start
    // 1.测试用例方法

    public void test() {
        /**
         * 测试用例创建单链表分两种情况:
         * 1. 默认单链表第一个节点为头指针，包含单链表的长度值，符合本代码情况
         * 2. 默认单链表第一个节点为头节点，符合部分算法题测试代码部分
         * 注意：
         *  若采用2，需将isFirst赋值为false
         */

        // 标记是否有头指针
        boolean isFirst = true;
        // 链表头节点数组
        List<ListNode> listNodes = new LinkedList<>();
        for (int i = 0; i < 20; i++) {
            int[] integers = new int[i];
            for (int j = 0; j < i; j++) {
                integers[j] = j;
            }

            // 1. 默认单链表第一个节点为头指针
            ListNode head = createLinkList(integers);

            // 2. 默认单链表第一个节点为头节点
//            ListNode head = createLinkList(integers).next;
//            isFirst = false;

            listNodes.add(head);
            showLinkList(head);
        }


        // 测试代码start -----------------------




        // 测试代码end -------------------------
    }

    // 2.算法题方法

    public ListNode solution(ListNode pHead) {
        /**
         * 算法部分分两种情况
         *
         * 1. 默认单链表第一个节点为头指针，包含单链表的长度值，符合本代码情况
         * 2. 默认单链表第一个节点为头节点，符合部分算法题测试代码部分
         * 注意：
         *
         * 若采用1，若对节点进行增删操作，头指针长度值对应增删
         * 若采用2，返回头节点应该是头指针的下一个节点,即：pHead.next
         *
         */

        if(pHead==null){
            return null;
        }
        // 2. 默认单链表第一个节点为头节点
//        ListNode first = new ListNode(-1);
//        first.next = pHead;
//        pHead = first;

        // 算法代码start -----------------------





        // 算法代码end ------------------------

        // 1. 默认单链表第一个节点为头指针
        return pHead;

        // 2. 默认单链表第一个节点为头节点
//        return pHead.next;
    }

    // end
    // ------------------------------------------------------------------------------------------

    public static void main(String[] args) {
        new ListBase().test();
    }
}
```

---

###  一、从尾到头打印链表

[OJ](https://www.nowcoder.com/practice/d0267f7f55b3412ba93bd35cfa8e8035?tpId=13&tqId=11156&tPage=1&rp=1&ru=/ta/coding-interviews&qru=/ta/coding-interviews/question-ranking)

输入一个链表，按链表值从尾到头的顺序返回一个ArrayList。

#### 测试用例

- 输入的链表有多个节点

- 输入的链表只有一个节点

- 输入的链表头节点为空

#### 解法+测试 栈 / 递归

- 遍历链表的时候压入栈，弹出栈的时候打印
- 递归遍历链表顺便打印,缺点：链表太长的话，函数调用层级越深，可能会导致函数调用栈溢出

```java
package list;

import java.util.ArrayList;
import java.util.Stack;

/**
 * @author humingk
 */
public class PrintListFromTailToHead {
    private ListNode first;
    ArrayList<Integer> arrayList1 = new ArrayList<>();
    ArrayList<Integer> arrayList2 = new ArrayList<>();
    Stack<Integer> stack = new Stack<>();

    public class ListNode {
        ListNode next;
        int value;

        ListNode(int value, ListNode next) {
            this.value = value;
            this.next = next;
        }
    }

    /**
     * 遍历链表的时候压入栈，弹出栈的时候打印
     *
     * @param listNode
     * @return
     */
    public ArrayList<Integer> printStack(ListNode listNode) {
        while (listNode != null) {
            stack.push(listNode.value);
            listNode = listNode.next;
        }
        while (!stack.isEmpty()) {
            arrayList1.add(stack.pop());
        }
        return arrayList1;
    }

    /**
     * 递归遍历链表顺便打印
     *
     * @param listNode
     * @return
     */
    public ArrayList<Integer> printRecursion(ListNode listNode) {
        if (listNode != null) {
            arrayList2 = printRecursion(listNode.next);
            arrayList2.add(listNode.value);
        }
        return arrayList2;
    }

    public void put(int[] values) {
        for (int i = 0; i < values.length; i++) {
            first = new ListNode(values[i],first);
        }
    }

    public void test(ArrayList<Integer> arrayList) {
        for (int i = 0; i < arrayList.size(); i++) {
            System.out.print(arrayList.get(i) + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println("1.输入的链表有多个节点");
        PrintListFromTailToHead p1 = new PrintListFromTailToHead();
        p1.put(new int[]{1, 2, 3, 4, 5});
        System.out.println("栈法");
        p1.test(p1.printStack(p1.first));
        System.out.println("递归");
        p1.test(p1.printRecursion(p1.first));

        System.out.println("2.输入的链表只有一个节点");
        PrintListFromTailToHead p2 = new PrintListFromTailToHead();
        p2.put(new int[]{1});
        System.out.println("栈法");
        p2.test(p2.printStack(p2.first));
        System.out.println("递归");
        p2.test(p2.printRecursion(p2.first));

        System.out.println("3.输入的链表头节点为空");
        PrintListFromTailToHead p3 = new PrintListFromTailToHead();
        p3.put(new int[]{});
        System.out.println("栈法");
        p3.test(p3.printStack(p3.first));
        System.out.println("递归");
        p3.test(p3.printRecursion(p3.first));
    }
}

```

#### 运行结果

```
1.输入的链表有多个节点
栈法
1 2 3 4 5 
递归
1 2 3 4 5 
2.输入的链表只有一个节点
栈法
1 
递归
1 
3.输入的链表头节点为空
栈法

递归

```



---

### 二、删除链表的节点

O(1)时间内

#### 解法1 循环遍历 O(n)

顺序遍历链表，将指向删除节点的指针指向下一个节点，时间复杂度 O(n)

#### 解法2+测试 用删除节点的下一节点覆盖删除节点 O(1)

指定删除节点，并用删除节点的下一个节点覆盖删除节点

若删除节点为尾节点，则采用顺序遍历方式

```java
package list;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * 删除节点
 *
 * @author humingk
 */
public class DeleteNode {
    /**
     * 此链表头节点
     */
    public ListNode first;

    /**
     * 此链表结构
     */
    public class ListNode {
        int val;
        ListNode next;

        public ListNode() {
            this.val = -1;
            this.next = null;
        }

        public ListNode(int val) {
            this.val = val;
            this.next = null;
        }

        public ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    /**
     * 创建链表,头指针存放链表长度
     *
     * @param values
     * @return
     */
    public ListNode createLinkList(int[] values) {
        ListNode head = new ListNode(values.length, null);
        for (int i = 0; i < values.length; i++) {
            ListNode node = new ListNode(values[i], null);
            node.next = head.next;
            head.next = node;
        }
        first = head;
        return head;
    }

    /**
     * 打印链表
     *
     * @param listNode
     */
    public void showLinkList(ListNode listNode) {
        while (listNode != null) {
            System.out.print(listNode.val + " ");
            if (listNode.next != null) {
                System.out.print("-> ");
            }
            listNode = listNode.next;
        }
        System.out.println();
    }
    // ------------------------------------------------------------------------------------------
    // start
    // 1.测试用例方法

    public void test() {
        /**
         * 测试用例创建单链表分两种情况:
         * 1. 默认单链表第一个节点为头指针，包含单链表的长度值，符合本代码情况
         * 2. 默认单链表第一个节点为头节点，符合部分算法题测试代码部分
         * 注意：
         *  若采用2，需将isFirst赋值为false
         */

        // 标记是否有头指针
        boolean isFirst = true;
        // 链表头节点数组
        List<ListNode> listNodes = new LinkedList<>();
        for (int i = 0; i < 20; i++) {
            int[] integers = new int[i];
            for (int j = 0; j < i; j++) {
                integers[j] = j;
            }

            // 1. 默认单链表第一个节点为头指针
            ListNode head = createLinkList(integers);

            // 2. 默认单链表第一个节点为头节点
//            ListNode head = createLinkList(integers).next;
//            isFirst = false;

            listNodes.add(head);
            showLinkList(head);
        }


        // 测试代码start -----------------------
        
        for (int i = 0; i < listNodes.size(); i++) {
            showLinkList(listNodes.get(i));
            System.out.println("=========================");
            // 可删除节点数
            int randomSum = i;
            for (int j = 0; j < i; j++) {
                // 删除任意节点
                int randomDelete = new Random().nextInt(randomSum);
                ListNode deleteNode = listNodes.get(i);
                if (isFirst) {
                    for (int k = -1; k < randomDelete; k++) {
                        deleteNode = deleteNode.next;
                    }
                } else {
                    for (int k = 0; k < randomDelete; k++) {
                        deleteNode = deleteNode.next;
                    }
                }
                randomSum--;
                System.out.println("delete " + deleteNode.val);
                showLinkList(solution(listNodes.get(i), deleteNode));
                System.out.println("-------------------------");
            }
        }


        // 测试代码end -------------------------
    }

    // 2.算法题方法

    /**
     * 删除节点
     *
     * @param pHead
     * @param deleteNode
     * @return
     */
    public ListNode solution(ListNode pHead, ListNode deleteNode) {
        /**
         * 算法部分分两种情况
         *
         * 1. 默认单链表第一个节点为头指针，包含单链表的长度值，符合本代码情况
         * 2. 默认单链表第一个节点为头节点，符合部分算法题测试代码部分
         * 注意：
         *
         * 若采用1，若对节点进行增删操作，头指针长度值对应增删
         * 若采用2，返回头节点应该是头指针的下一个节点,即：pHead.next
         *
         */

        // 2. 默认单链表第一个节点为头节点
//        ListNode first = new ListNode(-1);
//        first.next = pHead;
//        pHead = first;

        // 算法代码start -----------------------

        if (pHead == null || deleteNode == null) {
            return null;
        }
        // 只有一个节点,删除节点并删除头节点
        else if (pHead.next == deleteNode && deleteNode.next == null) {
            pHead.next = null;
            return null;
        }
        // 删除节点为尾节点，尾节点没有下一个节点，采用顺序遍历 O(n)
        else if (deleteNode.next == null) {
            ListNode pNode = pHead;
            while (pNode.next != deleteNode) {
                pNode = pNode.next;
            }
            pNode.next = null;
        }
        // 删除节点为中间节点 O(1)
        else {
            ListNode pNode = deleteNode.next;
            deleteNode.val = pNode.val;
            deleteNode.next = pNode.next;
            pNode.next = null;
        }

        // 算法代码end ------------------------

        // 1. 默认单链表第一个节点为头指针
        pHead.val--;
        return pHead;

        // 2. 默认单链表第一个节点为头节点
//        return pHead.next;
    }

    // end
    // ------------------------------------------------------------------------------------------

    public static void main(String[] args) {
        new DeleteNode().test();
    }
}
```

#### 运行结果

第一个节点为头指针的情况，头指针包含链表长度

```
-------------------------
11 -> 10 -> 9 -> 8 -> 7 -> 6 -> 5 -> 4 -> 3 -> 2 -> 1 -> 0 
=========================
delete 3
10 -> 10 -> 9 -> 8 -> 7 -> 6 -> 5 -> 4 -> 2 -> 1 -> 0 
-------------------------
delete 7
9 -> 10 -> 9 -> 8 -> 6 -> 5 -> 4 -> 2 -> 1 -> 0 
-------------------------
delete 1
8 -> 10 -> 9 -> 8 -> 6 -> 5 -> 4 -> 2 -> 0 
-------------------------
delete 0
7 -> 10 -> 9 -> 8 -> 6 -> 5 -> 4 -> 2 
-------------------------
delete 6
6 -> 10 -> 9 -> 8 -> 5 -> 4 -> 2 
-------------------------
delete 2
5 -> 10 -> 9 -> 8 -> 5 -> 4 
-------------------------
delete 8
4 -> 10 -> 9 -> 5 -> 4 
-------------------------
delete 5
3 -> 10 -> 9 -> 4 
-------------------------
delete 10
2 -> 9 -> 4 
-------------------------
delete 9
1 -> 4 
-------------------------
delete 4

-------------------------
```

第一个节点为头节点的情况

```
-------------------------
11 -> 10 -> 9 -> 8 -> 7 -> 6 -> 5 -> 4 -> 3 -> 2 -> 1 -> 0 
=========================
delete 4
11 -> 10 -> 9 -> 8 -> 7 -> 6 -> 5 -> 3 -> 2 -> 1 -> 0 
-------------------------
delete 2
11 -> 10 -> 9 -> 8 -> 7 -> 6 -> 5 -> 3 -> 1 -> 0 
-------------------------
delete 7
11 -> 10 -> 9 -> 8 -> 6 -> 5 -> 3 -> 1 -> 0 
-------------------------
delete 10
11 -> 9 -> 8 -> 6 -> 5 -> 3 -> 1 -> 0 
-------------------------
delete 6
11 -> 9 -> 8 -> 5 -> 3 -> 1 -> 0 
-------------------------
delete 1
11 -> 9 -> 8 -> 5 -> 3 -> 0 
-------------------------
delete 3
11 -> 9 -> 8 -> 5 -> 0 
-------------------------
delete 11
9 -> 8 -> 5 -> 0 
-------------------------
delete 0
9 -> 8 -> 5 
-------------------------
delete 8
9 -> 5 
-------------------------
delete 9
5 
-------------------------
delete 5

-------------------------
```

---

### 三、返回倒数第k个节点

[OJ](https://www.nowcoder.com/practice/529d3ae5a407492994ad2a246518148a?tpId=13&tqId=11167&tPage=1&rp=1&ru=%2Fta%2Fcoding-interviews&qru=%2Fta%2Fcoding-interviews%2Fquestion-ranking)

输入一个链表，输出该链表中倒数第k个节点。

#### 解法1+测试 双指针间隔K移位

两个指针,第一个指针先走k-1步，两个指针再同时走，当第一个指针到达末尾的时候，第二个指针到达倒数第k个节点处

```java
    // ------------------------------------------------------------------------------------------
    // start
    // 1.测试用例方法

    public void test() {
        /**
         * 测试用例创建单链表分两种情况:
         * 1. 默认单链表第一个节点为头指针，包含单链表的长度值，符合本代码情况
         * 2. 默认单链表第一个节点为头节点，符合部分算法题测试代码部分
         * 注意：
         *  若采用2，需将isFirst赋值为false
         */

        // 标记是否有头指针
        boolean isFirst = true;
        // 链表头节点数组
        List<ListNode> listNodes = new LinkedList<>();
        for (int i = 0; i < 20; i++) {
            int[] integers = new int[i];
            for (int j = 0; j < i; j++) {
                integers[j] = j;
            }

            // 1. 默认单链表第一个节点为头指针
//            ListNode head = createLinkList(integers);

            // 2. 默认单链表第一个节点为头节点
            ListNode head = createLinkList(integers).next;
            isFirst = false;

            listNodes.add(head);
            showLinkList(head);
        }


        // 测试代码start -----------------------

        for (int i = 1; i < listNodes.size(); i++) {
            showLinkList(listNodes.get(i));
            System.out.println(i / 2 + " between ");
            showLinkList(solution(listNodes.get(i), i / 2));
            System.out.println(i + " head ");
            showLinkList(solution(listNodes.get(i), i));
            System.out.println(1 + " last ");
            showLinkList(solution(listNodes.get(i), 1));
            System.out.println((i + 1) + " more ");
            showLinkList(solution(listNodes.get(i), i + 1));
            System.out.println(0 + " zero ");
            showLinkList(solution(listNodes.get(i), 0));
            System.out.println("------------------------------------------");
        }
        showLinkList(solution(null, 1));


        // 测试代码end -------------------------
    }

    // 2.算法题方法

    public ListNode solution(ListNode head, int k) {
        /**
         * 算法部分分两种情况
         *
         * 1. 默认单链表第一个节点为头指针，包含单链表的长度值，符合本代码情况
         * 2. 默认单链表第一个节点为头节点，符合部分算法题测试代码部分
         * 注意：
         *
         * 若采用1，若对节点进行增删操作，头指针长度值对应增删
         * 若采用2，返回头节点应该是头指针的下一个节点,即：pHead.next
         *
         */

        if (head == null || k == 0) {
            return null;
        }

        // 2. 默认单链表第一个节点为头节点
        ListNode first = new ListNode(-1);
        first.next = head;
        head = first;

        // 算法代码start -----------------------

        ListNode i = head;
        ListNode j = head;

        for (int l = 0; l < k - 1; l++) {
            j = j.next;
            if (j.next == null) {
                return null;
            }
        }
        // i和j始终相差k-1
        while (j.next != null) {
            j = j.next;
            i = i.next;
        }
        return i;


        // 算法代码end ------------------------

        // 1. 默认单链表第一个节点为头指针
//        return pHead;

        // 2. 默认单链表第一个节点为头节点
//        return head.next;
    }

    // end
    // ------------------------------------------------------------------------------------------
```

#### 运行结果

```java
------------------------------------------
14 -> 13 -> 12 -> 11 -> 10 -> 9 -> 8 -> 7 -> 6 -> 5 -> 4 -> 3 -> 2 -> 1 -> 0 
7 between 
6 -> 5 -> 4 -> 3 -> 2 -> 1 -> 0 
15 head 
14 -> 13 -> 12 -> 11 -> 10 -> 9 -> 8 -> 7 -> 6 -> 5 -> 4 -> 3 -> 2 -> 1 -> 0 
1 last 
0 
16 more 

0 zero 

------------------------------------------
```



------

### 四、链表中环的入口节点

[OJ](https://www.nowcoder.com/practice/253d2c59ec3e4bc68da16833f79a38e4?tpId=13&tqId=11208&tPage=3&rp=1&ru=%2Fta%2Fcoding-interviews&qru=%2Fta%2Fcoding-interviews%2Fquestion-ranking)

给一个链表，若其中包含环，请找出该链表的环的入口结点，否则，输出null。

#### 解法1+测试 快慢指针+双指针间隔K

方法1：

- 确定是否有环

  两个指针，快慢不同，若快指针追上慢指针，则有环

- 确定环的节点数n

  相遇指针处肯定为环内，可以计数再次回到相遇节点

- 确定入口节点

  头指针出发，P2 先走n步，P1P2再同时走，相遇处即为入口节点

  当P1P2相遇，此时P2绕了一圈刚好比P1多走n步，多走的n步就是链表中的环

```java
    // ------------------------------------------------------------------------------------------
    // start
    // 1.测试用例方法

    public void test() {
        /**
         * 测试用例创建单链表分两种情况:
         * 1. 默认单链表第一个节点为头指针，包含单链表的长度值，符合本代码情况
         * 2. 默认单链表第一个节点为头节点，符合部分算法题测试代码部分
         * 注意：
         *  若采用2，需将isFirst赋值为false
         */

        // 标记是否有头指针
        boolean isFirst = true;
        // 链表头节点数组
        List<ListNode> listNodes = new LinkedList<>();
        for (int i = 0; i < 20; i++) {
            int[] integers = new int[i];
            for (int j = 0; j < i; j++) {
                integers[j] = j;
            }

            // 1. 默认单链表第一个节点为头指针
//            ListNode head = createLinkList(integers);

            // 2. 默认单链表第一个节点为头节点
            ListNode head = createLinkList(integers).next;
            isFirst = false;

            listNodes.add(head);
            showLinkList(head);
        }


        // 测试代码start -----------------------
        showLinkList(listNodes.get(0));
        showLinkList(solution(listNodes.get(0)));
        System.out.println("----------------");
        System.out.println("null");
        showLinkList(solution(null));
        System.out.println("----------------");
        showLinkList(listNodes.get(19));
        ListNode start = listNodes.get(19);
        ListNode end = listNodes.get(19);
        for (int i = 0; i < 9; i++) {
            start = start.next;
        }
        for (int i = 0; i < 14; i++) {
            end = end.next;
        }
        System.out.println("start="+start.val);
        end.next = start;
        System.out.println("end="+end.val);
//        showLinkList(listNodes.get(19));
//        showLinkList(solution(listNodes.get(19)));
        System.out.println(solution(listNodes.get(19)).val);
        System.out.println("----------------");
        System.out.println(solution(listNodes.get(18)));
        // 测试代码end -------------------------
    }

    // 2.算法题方法

    public ListNode solution(ListNode pHead) {
        /**
         * 算法部分分两种情况
         *
         * 1. 默认单链表第一个节点为头指针，包含单链表的长度值，符合本代码情况
         * 2. 默认单链表第一个节点为头节点，符合部分算法题测试代码部分
         * 注意：
         *
         * 若采用1，若对节点进行增删操作，头指针长度值对应增删
         * 若采用2，返回头节点应该是头指针的下一个节点,即：pHead.next
         *
         */

        if (pHead == null || pHead.next == null) {
            return null;
        }

        // 2. 默认单链表第一个节点为头节点
        ListNode first = new ListNode(-1);
        first.next = pHead;
        pHead = first;

        // 算法代码start -----------------------

        // 确定是否有环
        // i走一步，j走两步
        ListNode meetNode = null;
        ListNode i = pHead;
        ListNode j = pHead;
        while (true) {
            if (i == j && i != pHead) {
                // i j相遇的节点
                meetNode = i;
                break;
            }
            if (i.next == null || j.next == null || j.next.next == null) {
                break;
            }
            i = i.next;
            j = j.next.next;
        }
        if (meetNode == null) {
            return null;
        }

        // 获得环的节点数
        int count = 1;
        j = j.next;
        while (i != j) {
            j = j.next;
            count++;
        }

        // 定位环的入口
        // j比i多走了count步
        i = pHead;
        j = pHead;
        for (int k = 0; k < count; k++) {
            j = j.next;
        }
        while (i != j) {
            i = i.next;
            j = j.next;
        }
        return i;


        // 算法代码end ------------------------

        // 1. 默认单链表第一个节点为头指针
//        return pHead;

        // 2. 默认单链表第一个节点为头节点
//        return pHead.next;
    }

    // end
    // ------------------------------------------------------------------------------------------
```

#### 运行结果

```java

----------------
null

----------------
18 -> 17 -> 16 -> 15 -> 14 -> 13 -> 12 -> 11 -> 10 -> 9 -> 8 -> 7 -> 6 -> 5 -> 4 -> 3 -> 2 -> 1 -> 0 
start=9
end=4
9
----------------
null
```



------

### 五、反转链表

[OJ](https://www.nowcoder.com/practice/75e878df47f24fdc9dc3e400ec6058ca?tpId=13&tqId=11168&tPage=1&rp=1&ru=%2Fta%2Fcoding-interviews&qru=%2Fta%2Fcoding-interviews%2Fquestion-ranking)

输入一个链表，反转链表后，输出新链表的表头。

#### 解法1+测试 三个相邻指针后移

三个指针从头结点开始依次后移，分为P1、P2、P3，原始链表为 p1 -> p2 -> p3

第一步：P2 -> P1,P1=P2

第二步：P2=P3，P3 -> P1(以前的P2)

第三步：P3=P3的下一个节点

可得到p1 <- p2 <- p1，循环执行以上三步

```java
    // ------------------------------------------------------------------------------------------
    // start
    // 1.测试用例方法

    public void test() {
        /**
         * 测试用例创建单链表分两种情况:
         * 1. 默认单链表第一个节点为头指针，包含单链表的长度值，符合本代码情况
         * 2. 默认单链表第一个节点为头节点，符合部分算法题测试代码部分
         * 注意：
         *  若采用2，需将isFirst赋值为false
         */

        // 标记是否有头指针
        boolean isFirst = true;
        // 链表头节点数组
        List<ListNode> listNodes = new LinkedList<>();
        for (int i = 0; i < 20; i++) {
            int[] integers = new int[i];
            for (int j = 0; j < i; j++) {
                integers[j] = j;
            }

            // 1. 默认单链表第一个节点为头指针
//            ListNode head = createLinkList(integers);

            // 2. 默认单链表第一个节点为头节点
            ListNode head = createLinkList(integers).next;
            isFirst = false;

            listNodes.add(head);
            showLinkList(head);
        }


        // 测试代码start -----------------------
        System.out.println("===================");
        for (int i = 0; i < listNodes.size(); i++) {
            showLinkList(listNodes.get(i));
            showLinkList(solution(listNodes.get(i)));
        }
        System.out.println();
        showLinkList(solution(null));
        showLinkList(listNodes.get(0));
        showLinkList(solution(listNodes.get(0)));


        // 测试代码end -------------------------
    }

    // 2.算法题方法

    public ListNode solution(ListNode head) {
        /**
         * 算法部分分两种情况
         *
         * 1. 默认单链表第一个节点为头指针，包含单链表的长度值，符合本代码情况
         * 2. 默认单链表第一个节点为头节点，符合部分算法题测试代码部分
         * 注意：
         *
         * 若采用1，若对节点进行增删操作，头指针长度值对应增删
         * 若采用2，返回头节点应该是头指针的下一个节点,即：pHead.next
         *
         */

        if (head == null) {
            return null;
        } else if (head.next == null) {
            return head;
        }
        // 2. 默认单链表第一个节点为头节点
        ListNode first = new ListNode(-1);
        first.next = head;
        head = first;

        // 算法代码start -----------------------
        ListNode pNode = head.next;
        ListNode newNode = null;
        ListNode oldNode = pNode.next;
        while (oldNode != null) {
            pNode.next = newNode;
            newNode = pNode;
            pNode = oldNode;
            if (oldNode.next == null) {
                oldNode.next = newNode;
                break;
            }
            oldNode = oldNode.next;
        }
        return oldNode;


        // 算法代码end ------------------------

        // 1. 默认单链表第一个节点为头指针
//        return pHead;

        // 2. 默认单链表第一个节点为头节点
//        return pHead.next;
    }

    // end
    // ------------------------------------------------------------------------------------------
```

#### 运行结果

```java
16 -> 15 -> 14 -> 13 -> 12 -> 11 -> 10 -> 9 -> 8 -> 7 -> 6 -> 5 -> 4 -> 3 -> 2 -> 1 -> 0 
0 -> 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8 -> 9 -> 10 -> 11 -> 12 -> 13 -> 14 -> 15 -> 16 
17 -> 16 -> 15 -> 14 -> 13 -> 12 -> 11 -> 10 -> 9 -> 8 -> 7 -> 6 -> 5 -> 4 -> 3 -> 2 -> 1 -> 0 
0 -> 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8 -> 9 -> 10 -> 11 -> 12 -> 13 -> 14 -> 15 -> 16 -> 17 
18 -> 17 -> 16 -> 15 -> 14 -> 13 -> 12 -> 11 -> 10 -> 9 -> 8 -> 7 -> 6 -> 5 -> 4 -> 3 -> 2 -> 1 -> 0 
0 -> 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8 -> 9 -> 10 -> 11 -> 12 -> 13 -> 14 -> 15 -> 16 -> 17 -> 18 
```

------



### 六、合并两个有序的链表

[OJ](https://www.nowcoder.com/practice/d8b6b4358f774294a89de2a6ac4d9337?tpId=13&tqId=11169&tPage=1&rp=1&ru=%2Fta%2Fcoding-interviews&qru=%2Fta%2Fcoding-interviews%2Fquestion-ranking)

输入两个单调递增的链表，输出两个链表合成后的链表，当然我们需要合成后的链表满足单调不减规则。

#### 解法1+测试 类归并排序

归并排序中的归并思想

```java
    // ------------------------------------------------------------------------------------------
    // start
    // 1.测试用例方法

    public void test() {
        /**
         * 测试用例创建单链表分两种情况:
         * 1. 默认单链表第一个节点为头指针，包含单链表的长度值，符合本代码情况
         * 2. 默认单链表第一个节点为头节点，符合部分算法题测试代码部分
         * 注意：
         *  若采用2，需将isFirst赋值为false
         */

        // 标记是否有头指针
        boolean isFirst = true;
        // 链表头节点数组
        List<ListNode> listNodes = new LinkedList<>();
        for (int i = 0; i < 20; i++) {
            int[] integers = new int[i];
            for (int j = 0; j < i; j++) {
                integers[j] = listNodes.size() - j;
            }

            // 1. 默认单链表第一个节点为头指针
//            ListNode head = createLinkList(integers);

            // 2. 默认单链表第一个节点为头节点
            ListNode head = createLinkList(integers).next;
            isFirst = false;

            listNodes.add(head);
            showLinkList(head);
        }


        // 测试代码start -----------------------
        System.out.println("---------------------");
        showLinkList(solution(null, null));
        System.out.println("---------------------");
        showLinkList(solution(null, listNodes.get(5)));
        System.out.println("---------------------");
        showLinkList(solution(listNodes.get(5), null));
        System.out.println("---------------------");
        ListNode one=new ListNode(1);
        ListNode two=new ListNode(1);
        showLinkList(solution(one,two));
        System.out.println("---------------------");
        showLinkList(listNodes.get(18));
        showLinkList(listNodes.get(8));
        showLinkList(solution(listNodes.get(18), listNodes.get(8)));

        // 测试代码end -------------------------
    }

    // 2.算法题方法

    public ListNode solution(ListNode list1, ListNode list2) {
        /**
         * 算法部分分两种情况
         *
         * 1. 默认单链表第一个节点为头指针，包含单链表的长度值，符合本代码情况
         * 2. 默认单链表第一个节点为头节点，符合部分算法题测试代码部分
         * 注意：
         *
         * 若采用1，若对节点进行增删操作，头指针长度值对应增删
         * 若采用2，返回头节点应该是头指针的下一个节点,即：pHead.next
         *
         */

//        if (pHead == null) {
//            return null;        showLinkList(listNodes.get(18));
//        }
        // 2. 默认单链表第一个节点为头节点
//        ListNode first = new ListNode(-1);
//        first.next = pHead;
//        pHead = first;

        // 算法代码start -----------------------
        if (list1 == null && list2 == null) {
            return null;
        } else if (list1 == null) {
            return list2;
        } else if (list2 == null) {
            return list1;
        }
        ListNode p1 = list1;
        ListNode p2 = list2;
        ListNode newHead = new ListNode(-1);
        ListNode pNode = newHead;
        while (true) {
            if (p1.val < p2.val) {
                pNode.next = p1;
                if (p1.next == null) {
                    pNode.next.next = p2;
                    break;
                } else {
                    p1 = p1.next;
                }
            } else {
                pNode.next = p2;
                if (p2.next == null) {
                    pNode.next.next = p1;
                    break;
                } else {
                    p2 = p2.next;
                }
            }
            pNode = pNode.next;
        }
        return newHead.next;

        // 算法代码end ------------------------

        // 1. 默认单链表第一个节点为头指针
//        return pHead;

        // 2. 默认单链表第一个节点为头节点
//        return pHead.next;
    }

    // end
    // ------------------------------------------------------------------------------------------
```



#### 运行结果

```java
---------------------

---------------------
1 -> 2 -> 3 -> 4 -> 5 
---------------------
1 -> 2 -> 3 -> 4 -> 5 
---------------------
1 -> 1 
---------------------
1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8 -> 9 -> 10 -> 11 -> 12 -> 13 -> 14 -> 15 -> 16 -> 17 -> 18 
1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8 
1 -> 1 -> 2 -> 2 -> 3 -> 3 -> 4 -> 4 -> 5 -> 5 -> 6 -> 6 -> 7 -> 7 -> 8 -> 8 -> 9 -> 10 -> 11 -> 12 -> 13 -> 14 -> 15 -> 16 -> 17 -> 18 
```



------



### 七、复杂链表的复制

[OJ](https://www.nowcoder.com/practice/f836b2c43afc4b35ad6adc41ec941dba?tpId=13&tqId=11178&tPage=2&rp=1&ru=%2Fta%2Fcoding-interviews&qru=%2Fta%2Fcoding-interviews%2Fquestion-ranking)

输入一个复杂链表（每个节点中有节点值，以及两个指针，一个指向下一个节点，另一个特殊指针指向任意一个节点），返回结果为复制后复杂链表的head。（注意，输出结果中请不要返回参数中的节点引用，否则判题程序会直接返回空）

#### 解法1 依次复制 O(n^2) + O(n)

复制旧链表的每一个旧节点的label，next到新链表

复制旧链表的每一个旧节点的random：

对于旧链表中的某个旧节点的random指向的节点，通过遍历旧链表找到其在链表中的位置，将新链表对应的新节点的random指向新链表中对应的位置

#### 解法2 递归+哈希表 O(n)+O(2n)

递归遍历旧链表的同时建立新链表

复制每一个节点的label，并且用哈希表存储每一个旧节点key及对应的新节点value

next由递归时候旧节点.next指定

random由旧节点.random所指向的旧节点通过hashmap找到新节点

```java
/*
public class RandomListNode {
    int label;
    RandomListNode next = null;
    RandomListNode random = null;

    RandomListNode(int label) {
        this.label = label;
    }
}
*/
import java.util.HashMap;
public class Solution {
    HashMap<RandomListNode,RandomListNode> hashMap=new HashMap<>();
    public RandomListNode Clone(RandomListNode pHead)
    {
        if(pHead==null){
            return null;
        }
        // 复制pHead节点 label
        RandomListNode copy = new RandomListNode(pHead.label);
        // 保存旧节点-新节点对应关系
        hashMap.put(pHead,copy);
        // 递归建立链表
        copy.next=Clone(pHead.next);
        // pHead节点的random指向的是旧链表中的节点,通过hashMap获取旧节点对应的新节点
        copy.random=hashMap.get(pHead.random);
        return copy;
    }
}
```

#### 解法3+测试 复制新节点到旧节点后 O(n)+O(n)

1. 遍历链表，old节点后面复制为new节点

   1 ->2 -> 3 则为 1-> 1 -> 2 -> 2 -> 3 -> 3

   old节点有random，new节点没有random

2. 复制每一个old节点的random到new节点

3. 分离新旧链表

```java
package list;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * 此链表模板分两种情况：
 * <p>
 * - 默认单链表第一个节点为头指针，包含单链表的长度值，符合本代码情况
 * - 默认单链表第一个节点为头指针，符合部分算法题测试代码情况
 * <p>
 * 分别在三个地方进行切换：
 * <p>
 * 1. 测试用例生成链表头节点数组
 * 2. 算法部分传入头节点
 * 3. 算法部分返回链表头节点
 *
 * @author humingk
 */
public class CopyComplexList {
    /**
     * 此链表头节点
     */
    public RandomListNode first;

    /**
     * 此链表结构
     */
    public class RandomListNode {
        int label;
        RandomListNode next;
        RandomListNode random;

        public RandomListNode() {
            this.label = -1;
            this.next = null;
            this.random = null;
        }

        public RandomListNode(int label) {
            this.label = label;
            this.next = null;
            this.random = null;
        }

        public RandomListNode(int label, RandomListNode next) {
            this.label = label;
            this.next = next;
            this.random = null;
        }

        public RandomListNode(int label, RandomListNode next, RandomListNode random) {
            this.label = label;
            this.next = next;
            this.random = random;
        }
    }

    /**
     * 创建链表,头指针存放链表长度
     *
     * @param labelues
     * @return
     */
    public RandomListNode createLinkList(int[] labelues) {
        RandomListNode head = new RandomListNode(labelues.length, null);
        for (int i = 0; i < labelues.length; i++) {
            RandomListNode node = new RandomListNode(labelues[i], null);
            node.next = head.next;
            head.next = node;
        }
        first = head;
        return head;
    }

    /**
     * 打印链表
     *
     * @param listNode
     */
    public void showLinkList(RandomListNode listNode) {
        while (listNode != null) {
            System.out.print(listNode.label + " ");
            if (listNode.random != null) {
                System.out.print("(" + listNode.random.label + ")");
            }
            if (listNode.next != null) {
                System.out.print("-> ");
            }
            listNode = listNode.next;
        }
        System.out.println();
    }
    // ------------------------------------------------------------------------------------------
    // start
    // 1.测试用例方法

    public void test() {
        /**
         * 测试用例创建单链表分两种情况:
         * 1. 默认单链表第一个节点为头指针，包含单链表的长度值，符合本代码情况
         * 2. 默认单链表第一个节点为头节点，符合部分算法题测试代码部分
         * 注意：
         *  若采用2，需将isFirst赋值为false
         */

        // 标记是否有头指针
        boolean isFirst = true;
        // 链表头节点数组
        List<RandomListNode> listNodes = new LinkedList<>();
        for (int i = 0; i < 20; i++) {
            int[] integers = new int[i];
            for (int j = 0; j < i; j++) {
                integers[j] = j;
            }

            // 1. 默认单链表第一个节点为头指针
//            RandomListNode head = createLinkList(integers);

            // 2. 默认单链表第一个节点为头节点
            RandomListNode head = createLinkList(integers).next;
            isFirst = false;

            listNodes.add(head);
            showLinkList(head);
        }
        // 添加random指针
        for (int j = 3; j < listNodes.size(); j++) {
            for (int i = 0; i < j; i++) {
                RandomListNode pNode = theNode(listNodes.get(j), i);
                pNode.random = randomNode(listNodes.get(j), j);
            }
        }
        for (int i = 0; i < listNodes.size(); i++) {
            showLinkList(listNodes.get(i));
        }

        // 测试代码start -----------------------
        for (int i = 0; i < listNodes.size(); i++) {
            System.out.println("origin:");
            showLinkList(listNodes.get(i));
            System.out.println("copy:");
            showLinkList(solution(listNodes.get(i)));
            System.out.println("------------------------------------------------------");
        }


        // 测试代码end -------------------------
    }

    private RandomListNode theNode(RandomListNode root, int the) {
        RandomListNode node = root;
        for (int i = 0; i < the && node.next != null; i++) {
            node = node.next;
        }
        return node;
    }

    /**
     * 节点添加随机random指针
     *
     * @param root
     * @param size
     * @return
     */
    private RandomListNode randomNode(RandomListNode root, int size) {
        int random = new Random().nextInt(size);
        RandomListNode node = root;
        int count = 0;
        while (node.next != null && count <= random) {
            node = node.next;
            count++;
        }
        return node;
    }

    // 2.算法题方法

    public RandomListNode solution(RandomListNode pHead) {
        /**
         * 算法部分分两种情况
         *
         * 1. 默认单链表第一个节点为头指针，包含单链表的长度值，符合本代码情况
         * 2. 默认单链表第一个节点为头节点，符合部分算法题测试代码部分
         * 注意：
         *
         * 若采用1，若对节点进行增删操作，头指针长度值对应增删
         * 若采用2，返回头节点应该是头指针的下一个节点,即：pHead.next
         *
         */

        if (pHead == null) {
            return null;
        }
        // 2. 默认单链表第一个节点为头节点
        RandomListNode first = new RandomListNode(-1);
        first.next = pHead;
        pHead = first;

        // 算法代码start -----------------------

        RandomListNode pNode = pHead.next;
        // 复制每一个节点new在当前节点old后面
        while (pNode != null) {
            RandomListNode temp = new RandomListNode(pNode.label);
            temp.next = pNode.next;
            pNode.next = temp;
            pNode = temp.next;
        }
        // 复制每一个节点的random指针
        RandomListNode cNode = pHead.next;
        while (cNode != null) {
            // 复制old上的random到new上
            cNode.next.random = cNode.random != null ? cNode.random.next : null;
            cNode = cNode.next.next;
        }
        // 分离新旧list
        RandomListNode sNode = pHead.next;
        RandomListNode newHead = sNode.next;
        while (sNode != null) {
            RandomListNode cloneNode = sNode.next;
            sNode.next = cloneNode.next;
            cloneNode.next = cloneNode.next != null ? cloneNode.next.next : null;
            sNode = sNode.next;
        }
        return newHead;

        // 算法代码end ------------------------

        // 1. 默认单链表第一个节点为头指针
//        return pHead;

        // 2. 默认单链表第一个节点为头节点
//        return pHead.next;
    }

    // end
    // ------------------------------------------------------------------------------------------

    public static void main(String[] args) {
        new CopyComplexList().test();
    }
}
```

#### 运行结果

```java
0 
1 -> 0 
2 -> 1 -> 0 
3 -> 2 -> 1 -> 0 
4 -> 3 -> 2 -> 1 -> 0 
5 -> 4 -> 3 -> 2 -> 1 -> 0 
6 -> 5 -> 4 -> 3 -> 2 -> 1 -> 0 
7 -> 6 -> 5 -> 4 -> 3 -> 2 -> 1 -> 0 
8 -> 7 -> 6 -> 5 -> 4 -> 3 -> 2 -> 1 -> 0 
9 -> 8 -> 7 -> 6 -> 5 -> 4 -> 3 -> 2 -> 1 -> 0 
10 -> 9 -> 8 -> 7 -> 6 -> 5 -> 4 -> 3 -> 2 -> 1 -> 0 
11 -> 10 -> 9 -> 8 -> 7 -> 6 -> 5 -> 4 -> 3 -> 2 -> 1 -> 0 
12 -> 11 -> 10 -> 9 -> 8 -> 7 -> 6 -> 5 -> 4 -> 3 -> 2 -> 1 -> 0 
13 -> 12 -> 11 -> 10 -> 9 -> 8 -> 7 -> 6 -> 5 -> 4 -> 3 -> 2 -> 1 -> 0 
14 -> 13 -> 12 -> 11 -> 10 -> 9 -> 8 -> 7 -> 6 -> 5 -> 4 -> 3 -> 2 -> 1 -> 0 
15 -> 14 -> 13 -> 12 -> 11 -> 10 -> 9 -> 8 -> 7 -> 6 -> 5 -> 4 -> 3 -> 2 -> 1 -> 0 
16 -> 15 -> 14 -> 13 -> 12 -> 11 -> 10 -> 9 -> 8 -> 7 -> 6 -> 5 -> 4 -> 3 -> 2 -> 1 -> 0 
17 -> 16 -> 15 -> 14 -> 13 -> 12 -> 11 -> 10 -> 9 -> 8 -> 7 -> 6 -> 5 -> 4 -> 3 -> 2 -> 1 -> 0 
18 -> 17 -> 16 -> 15 -> 14 -> 13 -> 12 -> 11 -> 10 -> 9 -> 8 -> 7 -> 6 -> 5 -> 4 -> 3 -> 2 -> 1 -> 0 

0 
1 -> 0 
2 (1)-> 1 (0)-> 0 (0)
3 (2)-> 2 (1)-> 1 (2)-> 0 (0)
4 (1)-> 3 (2)-> 2 (1)-> 1 (0)-> 0 (1)
5 (0)-> 4 (0)-> 3 (1)-> 2 (3)-> 1 (1)-> 0 (3)
6 (3)-> 5 (1)-> 4 (0)-> 3 (4)-> 2 (1)-> 1 (5)-> 0 (2)
7 (0)-> 6 (0)-> 5 (1)-> 4 (6)-> 3 (0)-> 2 (0)-> 1 (4)-> 0 (3)
8 (1)-> 7 (0)-> 6 (0)-> 5 (0)-> 4 (5)-> 3 (7)-> 2 (1)-> 1 (4)-> 0 (4)
9 (7)-> 8 (3)-> 7 (6)-> 6 (4)-> 5 (4)-> 4 (8)-> 3 (5)-> 2 (8)-> 1 (4)-> 0 (7)
10 (7)-> 9 (2)-> 8 (1)-> 7 (5)-> 6 (0)-> 5 (7)-> 4 (0)-> 3 (4)-> 2 (0)-> 1 (4)-> 0 (0)
11 (10)-> 10 (9)-> 9 (4)-> 8 (5)-> 7 (9)-> 6 (1)-> 5 (4)-> 4 (7)-> 3 (9)-> 2 (4)-> 1 (2)-> 0 (4)
12 (5)-> 11 (1)-> 10 (0)-> 9 (9)-> 8 (10)-> 7 (7)-> 6 (3)-> 5 (5)-> 4 (0)-> 3 (11)-> 2 (11)-> 1 (0)-> 0 (10)
13 (2)-> 12 (5)-> 11 (12)-> 10 (7)-> 9 (4)-> 8 (3)-> 7 (10)-> 6 (10)-> 5 (0)-> 4 (12)-> 3 (6)-> 2 (9)-> 1 (0)-> 0 (4)
14 (2)-> 13 (8)-> 12 (5)-> 11 (6)-> 10 (2)-> 9 (8)-> 8 (1)-> 7 (11)-> 6 (12)-> 5 (10)-> 4 (3)-> 3 (4)-> 2 (12)-> 1 (6)-> 0 (12)
15 (6)-> 14 (4)-> 13 (13)-> 12 (8)-> 11 (8)-> 10 (11)-> 9 (0)-> 8 (5)-> 7 (0)-> 6 (4)-> 5 (12)-> 4 (8)-> 3 (1)-> 2 (11)-> 1 (4)-> 0 (0)
16 (1)-> 15 (4)-> 14 (14)-> 13 (12)-> 12 (0)-> 11 (7)-> 10 (14)-> 9 (1)-> 8 (0)-> 7 (6)-> 6 (13)-> 5 (7)-> 4 (3)-> 3 (4)-> 2 (7)-> 1 (13)-> 0 (3)
17 (11)-> 16 (14)-> 15 (12)-> 14 (13)-> 13 (10)-> 12 (2)-> 11 (8)-> 10 (11)-> 9 (0)-> 8 (1)-> 7 (14)-> 6 (8)-> 5 (0)-> 4 (11)-> 3 (9)-> 2 (12)-> 1 (2)-> 0 (4)
18 (11)-> 17 (9)-> 16 (1)-> 15 (7)-> 14 (5)-> 13 (17)-> 12 (11)-> 11 (10)-> 10 (0)-> 9 (0)-> 8 (8)-> 7 (15)-> 6 (10)-> 5 (8)-> 4 (0)-> 3 (12)-> 2 (3)-> 1 (8)-> 0 (1)
origin:

copy:

------------------------------------------------------
origin:
0 
copy:
0 
------------------------------------------------------
origin:
1 -> 0 
copy:
1 -> 0 
------------------------------------------------------
origin:
2 (1)-> 1 (0)-> 0 (0)
copy:
2 (1)-> 1 (0)-> 0 (0)
------------------------------------------------------
origin:
3 (2)-> 2 (1)-> 1 (2)-> 0 (0)
copy:
3 (2)-> 2 (1)-> 1 (2)-> 0 (0)
------------------------------------------------------
origin:
4 (1)-> 3 (2)-> 2 (1)-> 1 (0)-> 0 (1)
copy:
4 (1)-> 3 (2)-> 2 (1)-> 1 (0)-> 0 (1)
------------------------------------------------------
origin:
5 (0)-> 4 (0)-> 3 (1)-> 2 (3)-> 1 (1)-> 0 (3)
copy:
5 (0)-> 4 (0)-> 3 (1)-> 2 (3)-> 1 (1)-> 0 (3)
------------------------------------------------------
origin:
6 (3)-> 5 (1)-> 4 (0)-> 3 (4)-> 2 (1)-> 1 (5)-> 0 (2)
copy:
6 (3)-> 5 (1)-> 4 (0)-> 3 (4)-> 2 (1)-> 1 (5)-> 0 (2)
------------------------------------------------------
origin:
7 (0)-> 6 (0)-> 5 (1)-> 4 (6)-> 3 (0)-> 2 (0)-> 1 (4)-> 0 (3)
copy:
7 (0)-> 6 (0)-> 5 (1)-> 4 (6)-> 3 (0)-> 2 (0)-> 1 (4)-> 0 (3)
------------------------------------------------------
origin:
8 (1)-> 7 (0)-> 6 (0)-> 5 (0)-> 4 (5)-> 3 (7)-> 2 (1)-> 1 (4)-> 0 (4)
copy:
8 (1)-> 7 (0)-> 6 (0)-> 5 (0)-> 4 (5)-> 3 (7)-> 2 (1)-> 1 (4)-> 0 (4)
------------------------------------------------------
origin:
9 (7)-> 8 (3)-> 7 (6)-> 6 (4)-> 5 (4)-> 4 (8)-> 3 (5)-> 2 (8)-> 1 (4)-> 0 (7)
copy:
9 (7)-> 8 (3)-> 7 (6)-> 6 (4)-> 5 (4)-> 4 (8)-> 3 (5)-> 2 (8)-> 1 (4)-> 0 (7)
------------------------------------------------------
origin:
10 (7)-> 9 (2)-> 8 (1)-> 7 (5)-> 6 (0)-> 5 (7)-> 4 (0)-> 3 (4)-> 2 (0)-> 1 (4)-> 0 (0)
copy:
10 (7)-> 9 (2)-> 8 (1)-> 7 (5)-> 6 (0)-> 5 (7)-> 4 (0)-> 3 (4)-> 2 (0)-> 1 (4)-> 0 (0)
------------------------------------------------------
origin:
11 (10)-> 10 (9)-> 9 (4)-> 8 (5)-> 7 (9)-> 6 (1)-> 5 (4)-> 4 (7)-> 3 (9)-> 2 (4)-> 1 (2)-> 0 (4)
copy:
11 (10)-> 10 (9)-> 9 (4)-> 8 (5)-> 7 (9)-> 6 (1)-> 5 (4)-> 4 (7)-> 3 (9)-> 2 (4)-> 1 (2)-> 0 (4)
------------------------------------------------------
origin:
12 (5)-> 11 (1)-> 10 (0)-> 9 (9)-> 8 (10)-> 7 (7)-> 6 (3)-> 5 (5)-> 4 (0)-> 3 (11)-> 2 (11)-> 1 (0)-> 0 (10)
copy:
12 (5)-> 11 (1)-> 10 (0)-> 9 (9)-> 8 (10)-> 7 (7)-> 6 (3)-> 5 (5)-> 4 (0)-> 3 (11)-> 2 (11)-> 1 (0)-> 0 (10)
------------------------------------------------------
origin:
13 (2)-> 12 (5)-> 11 (12)-> 10 (7)-> 9 (4)-> 8 (3)-> 7 (10)-> 6 (10)-> 5 (0)-> 4 (12)-> 3 (6)-> 2 (9)-> 1 (0)-> 0 (4)
copy:
13 (2)-> 12 (5)-> 11 (12)-> 10 (7)-> 9 (4)-> 8 (3)-> 7 (10)-> 6 (10)-> 5 (0)-> 4 (12)-> 3 (6)-> 2 (9)-> 1 (0)-> 0 (4)
------------------------------------------------------
origin:
14 (2)-> 13 (8)-> 12 (5)-> 11 (6)-> 10 (2)-> 9 (8)-> 8 (1)-> 7 (11)-> 6 (12)-> 5 (10)-> 4 (3)-> 3 (4)-> 2 (12)-> 1 (6)-> 0 (12)
copy:
14 (2)-> 13 (8)-> 12 (5)-> 11 (6)-> 10 (2)-> 9 (8)-> 8 (1)-> 7 (11)-> 6 (12)-> 5 (10)-> 4 (3)-> 3 (4)-> 2 (12)-> 1 (6)-> 0 (12)
------------------------------------------------------
origin:
15 (6)-> 14 (4)-> 13 (13)-> 12 (8)-> 11 (8)-> 10 (11)-> 9 (0)-> 8 (5)-> 7 (0)-> 6 (4)-> 5 (12)-> 4 (8)-> 3 (1)-> 2 (11)-> 1 (4)-> 0 (0)
copy:
15 (6)-> 14 (4)-> 13 (13)-> 12 (8)-> 11 (8)-> 10 (11)-> 9 (0)-> 8 (5)-> 7 (0)-> 6 (4)-> 5 (12)-> 4 (8)-> 3 (1)-> 2 (11)-> 1 (4)-> 0 (0)
------------------------------------------------------
origin:
16 (1)-> 15 (4)-> 14 (14)-> 13 (12)-> 12 (0)-> 11 (7)-> 10 (14)-> 9 (1)-> 8 (0)-> 7 (6)-> 6 (13)-> 5 (7)-> 4 (3)-> 3 (4)-> 2 (7)-> 1 (13)-> 0 (3)
copy:
16 (1)-> 15 (4)-> 14 (14)-> 13 (12)-> 12 (0)-> 11 (7)-> 10 (14)-> 9 (1)-> 8 (0)-> 7 (6)-> 6 (13)-> 5 (7)-> 4 (3)-> 3 (4)-> 2 (7)-> 1 (13)-> 0 (3)
------------------------------------------------------
origin:
17 (11)-> 16 (14)-> 15 (12)-> 14 (13)-> 13 (10)-> 12 (2)-> 11 (8)-> 10 (11)-> 9 (0)-> 8 (1)-> 7 (14)-> 6 (8)-> 5 (0)-> 4 (11)-> 3 (9)-> 2 (12)-> 1 (2)-> 0 (4)
copy:
17 (11)-> 16 (14)-> 15 (12)-> 14 (13)-> 13 (10)-> 12 (2)-> 11 (8)-> 10 (11)-> 9 (0)-> 8 (1)-> 7 (14)-> 6 (8)-> 5 (0)-> 4 (11)-> 3 (9)-> 2 (12)-> 1 (2)-> 0 (4)
------------------------------------------------------
origin:
18 (11)-> 17 (9)-> 16 (1)-> 15 (7)-> 14 (5)-> 13 (17)-> 12 (11)-> 11 (10)-> 10 (0)-> 9 (0)-> 8 (8)-> 7 (15)-> 6 (10)-> 5 (8)-> 4 (0)-> 3 (12)-> 2 (3)-> 1 (8)-> 0 (1)
copy:
18 (11)-> 17 (9)-> 16 (1)-> 15 (7)-> 14 (5)-> 13 (17)-> 12 (11)-> 11 (10)-> 10 (0)-> 9 (0)-> 8 (8)-> 7 (15)-> 6 (10)-> 5 (8)-> 4 (0)-> 3 (12)-> 2 (3)-> 1 (8)-> 0 (1)
------------------------------------------------------
```

------



### 八、两个链表的第一个公共结点

[OJ](https://www.nowcoder.com/practice/6ab1d9a29e88450685099d45c9e31e46?tpId=13&tqId=11189&tPage=2&rp=1&ru=%2Fta%2Fcoding-interviews&qru=%2Fta%2Fcoding-interviews%2Fquestion-ranking)

输入两个链表，找出它们的第一个公共结点。

#### 解法1 循环 O(n^2)+O(1)

循环链表1 的每一个节点的时候，循环链表2比较

------

#### 解法2 双栈 O(n)+O(2n)

用两个栈存放两个链表，倒序走进行比较

------

#### 解法3 双指针间隔距离差 O(n)+O(1)

先求长度差k，最长的链表先走k步，再同步遍历并比较

```java
/*
public class ListNode {
    int val;
    ListNode next = null;

    ListNode(int val) {
        this.val = val;
    }
}*/
public class Solution {
    public ListNode FindFirstCommonNode(ListNode pHead1, ListNode pHead2) {
        int l1=0,l2=0;
        ListNode pNode=pHead1;
        while(pNode!=null){
            pNode=pNode.next;
            l1++;
        }
        pNode=pHead2;
        while(pNode!=null){
            pNode=pNode.next;
            l2++;
        }
        // 保证l1<l2
        if(l1>l2){
            return F(pHead2,pHead1,l2,l1);
        }else{
            return F(pHead1,pHead2,l1,l2);
        }   
    }
    public ListNode F(ListNode pHead1,ListNode pHead2,int l1,int l2){
        ListNode pNode1=pHead1;
        ListNode pNode2=pHead2;
        int distance=l2-l1;
        // 链表2提前走distance步
        for(int i=0;i<distance;i++){
            pNode2=pNode2.next;
        }
        while(pNode1!=null&&pNode2!=null){
            if(pNode1.val==pNode2.val){
                return pNode1;
            }
            pNode1=pNode1.next;
            pNode2=pNode2.next;
        }
        return null;
    }
}
```

------



### 九、圆圈中最后剩下的数

[OJ](https://www.nowcoder.com/practice/f78a359491e64a50bce2d89cff857eb6?tpId=13&tqId=11199&tPage=3&rp=1&ru=%2Fta%2Fcoding-interviews&qru=%2Fta%2Fcoding-interviews%2Fquestion-ranking)

每年六一儿童节,牛客都会准备一些小礼物去看望孤儿院的小朋友,今年亦是如此。HF作为牛客的资深元老,自然也准备了一些小游戏。其中,有个游戏是这样的:首先,让小朋友们围成一个大圈。然后,他随机指定一个数m,让编号为0的小朋友开始报数。每次喊到m-1的那个小朋友要出列唱首歌,然后可以在礼品箱中任意的挑选礼物,并且不再回到圈中,从他的下一个小朋友开始,继续0...m-1报数....这样下去....直到剩下最后一个小朋友,可以不用表演,并且拿到牛客名贵的“名侦探柯南”典藏版(名额有限哦!!^_^)。请你试着想下,哪个小朋友会得到这份礼品呢？(注：小朋友的编号是从0到n-1)

#### 解法1 自定义链表环 O(m*n)+O(n)

```java
public class Solution {
    public int LastRemaining_Solution(int n, int m) {
        if(n<=0){
            return -1;
        }
        // 初始化链表环
        ListNode head = new ListNode(0);
        ListNode pNode = head;
        for (int i = 1; i < n; i++) {
            pNode.next = new ListNode(i);
            pNode = pNode.next;
        }
        pNode.next = head;
        // 循环删除第m个节点
        pNode = head;
        while (true) {
            // 待删除节点的上一个节点
            for (int i = 0; i < m - 2; i++) {
                pNode = pNode.next;
            }
            // 删除此节点的下一个节点
            pNode.next = pNode.next.next;
            pNode = pNode.next;
            // 只剩下一个节点
            if (pNode.val == pNode.next.val) {
                break;
            }
        }
        return pNode.val;
    }

    class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }
    }

    public static void main(String[] args) {
        System.out.println(new Solution().LastRemaining_Solution(5, 3));
    }
}
```

------

#### 解法2 动态规划 O(n)+O(1)

解法1每次都要删除一个节点，很多节点都可能会重复遍历

用f(n,m)表示n个数字中循环删除第m个数字后最后剩下的一个数字，有：

f(n,m)=

- n=1,0
- n>1,[f(n-1,m)+m]%n

可以从n=2开始循环算出n=3,4,5...时f(n,m)的值



公式推导eg: 

n=5,m=3，即：

0,1,2,3,4

第一次删除第k=m个数，即第k=(m-1)%n个数:

0,1,3,4

对应：

k-2,k-1,k+1,k+2

此时，3为新数组第一个数，则：

3,4,0,1 即：

K+1,K+2,K-2,K-1

p(i)为新数组的映射，表示新数组下标元素i对应的值，即：

p(i)=(i+k+1)%n，

eg：

i=0

i=1

i=2对应的k-2（0），原始数组位置为 2+2+1%5=0

i=3对应的k-1（1），原始数组位置为 3+2+1%5=1

由k=(m-1)%n,i代表旧数组,代入 p(i)=(i+k+1)%n 得：

f(n,m)=[f(n-1,m)+m]%n

```java
public class Solution {
    public int LastRemaining_Solution(int n, int m) {
        if (n <= 0) {
            return -1;
        } else if (n == 1) {
            return 0;
        }
        int result = 0;
        for (int i = 2; i <= n; i++) {
            result = (result + m) % i;
        }
        return result;
    }
}
```

------



### 十、两数相加

[OJ](https://leetcode-cn.com/problems/add-two-numbers)

给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。

如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。

您可以假设除了数字 0 之外，这两个数都不会以 0 开头。

示例：

输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
输出：7 -> 0 -> 8
原因：342 + 465 = 807

#### 解法 模拟位数相加 *O*(max(*m*,*n*))+*O*(max(*m*,*n*))

从最低位 即表头开始，逐位相加，并加上上一位相加的进位值0或1

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode head = new ListNode(-1);
        ListNode node = head;
        // 当前位的和
        int sum = 0;
        // 进位
        int carry = 0;
        // l1、l2没有遍历完或上一次相加有进位
        while (l1 != null || l2 != null || carry == 1) {
            if (l1 != null) {
                sum += l1.val;
                l1 = l1.next;
            }
            if (l2 != null) {
                sum += l2.val;
                l2 = l2.next;
            }
            // 加上一次相加的进位
            sum += carry;
            if (sum >= 10) {
                sum -= 10;
                carry = 1;
            } else {
                // 重置进位
                carry = 0;
            }
            ListNode p = new ListNode(sum);
            node.next = p;
            node = node.next;
            sum = 0;
        }
        return head.next;
    }
}
```

------





### 十一、





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





### 十二、





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

