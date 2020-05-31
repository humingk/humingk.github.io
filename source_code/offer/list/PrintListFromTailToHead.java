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
