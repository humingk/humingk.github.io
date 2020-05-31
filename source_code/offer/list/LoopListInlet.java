package list;

import java.util.LinkedList;
import java.util.List;

/**
 * 链表环的入口节点
 *
 * @author humingk
 */
public class LoopListInlet {
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
//        showLinkList(HashSubtree(listNodes.get(19)));
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

    public static void main(String[] args) {
        new LoopListInlet().test();
    }
}