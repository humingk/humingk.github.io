package list;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * 删除链表重复节点
 *
 * @author humingk
 */
public class DeleteRepeatNodeT<Value> {
    /**
     * 此链表头节点
     */
    public ListNode first;

    /**
     * 此链表结构
     */
    public class ListNode {
        Value val;
        ListNode next;

        public ListNode(Value val) {
            this.val = val;
            this.next = null;
        }

        public ListNode(Value val, ListNode next) {
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
    public ListNode createLinkList(Value[] values) {
        ListNode head = new ListNode((Value) "head", null);
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
        // 链表头节点数组
        List<ListNode> listNodes = new LinkedList<>();
        for (int i = 0; i < 20; i++) {
            // 当前的node值，后面的值都大于等于nowNodeVal，升序
            int nowNodeVal = 0;
            Integer[] integers = new Integer[i];
            for (int j = 0; j < i; j++) {
                nowNodeVal += new Random().nextInt(3);
                integers[j] = nowNodeVal;
            }
            ListNode head = createLinkList((Value[]) integers);
            listNodes.add(head);
            showLinkList(head);
        }
        listNodes.add(createLinkList((Value[]) new Integer[]{1, 1, 1, 1, 1, 1, 1}));
        listNodes.add(createLinkList((Value[]) new Integer[]{2, 1, 1, 1, 1, 1, 1}));
        listNodes.add(createLinkList((Value[]) new Integer[]{1, 1, 1, 1, 1, 1, 2}));
        listNodes.add(createLinkList((Value[]) new Integer[]{1, 1, 2, 2, 3, 3}));

        // 开始测试
        for (int i = 0; i < listNodes.size(); i++) {
            showLinkList(listNodes.get(i));
            System.out.println("delete repeat node...");
            showLinkList(solution(listNodes.get(i)));
            System.out.println("---------------------------");
        }
    }

    // 2.算法题方法

    public ListNode solution(ListNode pHead) {
        // 单链表有头指针的情况，本代码情况
        ListNode preNode = pHead;
        ListNode pNode = pHead.next;
        // 单链表没有有头指针的情况，部分算法题测试代码情况
//        ListNode head = new ListNode("head");


        return null;
    }

    // end
    // ------------------------------------------------------------------------------------------

    public static void main(String[] args) {
        new DeleteRepeatNodeT<Integer>().test();
    }
}