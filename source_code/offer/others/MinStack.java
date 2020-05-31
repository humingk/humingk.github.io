package others;

import java.util.Stack;

/**
 * 包含min方法的栈
 *
 * @author humingk
 */
public class MinStack {
    // ------------------------------------------------------------------------------------------
    // start
    // 1.测试用例方法

    public void test() {
        for (int i = 10; i < 20; i++) {
            push(i);
            print();
        }
        for (int i = 10; i < 15; i++) {
            pop();
            print();
        }
        for (int i = 5; i < 10; i++) {
            push(i);
            print();
        }
        for (int i = 0; i < data.size(); i++) {
            pop();
        }
    }

    private void print() {
        System.out.print("data: ");
        printStack(data);
        System.out.print("dataMin: ");
        printStack(dataMin);
        System.out.println("min: " + min());
        System.out.println("------------------------");
    }

    private void printStack(Stack<Integer> test) {
        int length = test.size();
        int[] temp = new int[length];
        for (int i = 0; i < length; i++) {
            temp[i] = test.pop();
        }
        for (int i = length - 1; i >= 0; i--) {
            test.push(temp[i]);
            if (temp[i] != 0) {
                System.out.print(temp[i] + " ");
            }
        }
        System.out.println();
    }

    // 2.算法题方法

    private Stack<Integer> data = new Stack<>();
    private Stack<Integer> dataMin = new Stack<>();

    public void push(int node) {
        data.push(node);
        if (dataMin.isEmpty() || node < dataMin.peek()) {
            dataMin.push(node);
        } else {
            dataMin.push(dataMin.peek());
        }
    }

    public void pop() {
        if (!data.isEmpty() && !dataMin.isEmpty()) {
            data.pop();
            dataMin.pop();
        }
    }

    public int top() {
        if (!data.isEmpty()) {
            return data.peek();
        } else {
            return -1;
        }
    }

    public int min() {
        if (!dataMin.isEmpty()) {
            return dataMin.peek();
        } else {
            return -1;
        }
    }

    // end
    // ------------------------------------------------------------------------------------------

    public static void main(String[] args) {
        new MinStack().test();
    }
}