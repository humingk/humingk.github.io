package list;

import java.util.LinkedList;
import java.util.List;

/**
 * 反转链表
 *
 * @author humingk
 */
public class ReversalList {
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

    public static void main(String[] args) {
        new ReversalList().test();
    }
}