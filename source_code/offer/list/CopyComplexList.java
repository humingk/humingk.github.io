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
        // 复制每一个节点在当前节点后面
        while (pNode != null) {
            RandomListNode temp = new RandomListNode(pNode.label);
            temp.next = pNode.next;
            pNode.next = temp;
            pNode = temp.next;
        }
        // 复制每一个节点的random指针
        RandomListNode cNode = pHead.next;
        while (cNode != null) {
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