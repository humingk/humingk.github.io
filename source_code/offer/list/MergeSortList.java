package list;

import java.util.LinkedList;
import java.util.List;

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
public class MergeSortList {
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

    public static void main(String[] args) {
        new MergeSortList().test();
    }
}