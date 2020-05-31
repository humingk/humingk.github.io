package tree;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * 之字形分行打印二叉树
 *
 * @author humingk
 */
public class UpToDownPrintTree3 {

    // ------------------------------------------------------------------------------------------
    // start

    // 1.测试用例方法

    public void test() {
        List<TreeNode> treeNodes = createTreeNodes();
        for (int i = 0; i < treeNodes.size(); i++) {
            showInOrder(treeNodes.get(i));
            solution(treeNodes.get(i));
            System.out.println("-------------------------------------");
        }

    }

    // 2.算法题方法

    public void solution(TreeNode root) {
        // 存储奇数行的节点
        // 先存右节点，再存左节点 因为下一行(偶数行)要倒打印
        Stack<TreeNode> stack1 = new Stack<>();
        // 存储偶数行的节点
        // 先存左节点，再存右节点 因为下一行(奇数行)要正打印
        Stack<TreeNode> stack2 = new Stack<>();
        stack1.push(root);
        // 当前行数
        int row = 1;
        // 当前行节点数
        int current = 1;
        // 下一行节点数
        int next = 0;
        while (!stack1.isEmpty() || !stack2.isEmpty()) {
            TreeNode node = null;
            // 当前打印行为奇数,当前行从stack1取值
            if ((row & 1) == 1) {
                node = stack1.pop();
                if (node != null) {
                    System.out.print(node.val + " ");
                    current--;
                    // 下一行存入stack2，先存左节点
                    if (node.left != null) {
                        stack2.push(node.left);
                        next++;
                    }
                    if (node.right != null) {
                        stack2.push(node.right);
                        next++;
                    }
                }
            }
            // 当前打印行为偶数，当前行从stack2取值
            else {
                node = stack2.pop();
                if (node != null) {
                    System.out.print(node.val + " ");
                    current--;
                    // 下一行存入stack1，先存右节点
                    if (node.right != null) {
                        stack1.push(node.right);
                        next++;
                    }
                    if (node.left != null) {
                        stack1.push(node.left);
                        next++;
                    }
                }
            }
            // 当前行打印完毕
            if (current == 0) {
                System.out.println();
                current = next;
                next = 0;
                row++;
            }
        }
    }

    // end
    // ------------------------------------------------------------------------------------------

    /**
     * 二叉树根节点
     */
    public TreeNode root;

    /**
     * 二叉树结构
     */
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode parent;

        public TreeNode(int val) {
            this.val = val;
        }
    }

    /**
     * 中序遍历打印二叉树
     *
     * @param nodeTree
     */
    public void showInOrder(TreeNode nodeTree) {
        System.out.print("InOrder: ");
        showInOrderR(nodeTree);
        System.out.println();
    }

    private void showInOrderR(TreeNode nodeTree) {
        if (nodeTree != null) {
            showInOrderR(nodeTree.left);
            System.out.print(nodeTree.val + " ");
            showInOrderR(nodeTree.right);
        }
    }

    /**
     * 测试用例生成
     *
     * @return
     */
    public List<TreeNode> createTreeNodes() {
        List<TreeNode> treeNodes = new LinkedList<>();
        treeNodes.add(createBinaryTree(new int[]{10, 6, 4, 8, 14, 12, 16}, new int[]{4, 6, 8, 10, 12, 14, 16}));
        System.out.println("0，完全二叉树");
        /**
         *      10
         *   6      14
         * 4  8   12  16
         */
        showInOrder(treeNodes.get(0));

        treeNodes.add(createBinaryTree(new int[]{1, 2, 4, 7, 3, 5, 6, 8}, new int[]{4, 7, 2, 1, 5, 3, 8, 6}));
        System.out.println("1，非完全二叉树");
        /**
         *          1
         *     2        3
         *  4        5      6
         *    7           8
         */
        showInOrder(treeNodes.get(1));

        treeNodes.add(createBinaryTree(new int[]{1, 2, 3, 4, 5}, new int[]{5, 4, 3, 2, 1}));
        System.out.println("2，没有右子节点的二叉树");
        /**
         *               1
         *            2
         *         3
         *      4
         *   5
         */
        showInOrder(treeNodes.get(2));

        treeNodes.add(createBinaryTree(new int[]{1, 2, 3, 4, 5}, new int[]{1, 2, 3, 4, 5}));
        System.out.println("3，没有左子节点的二叉树");
        /**
         *  1
         *    2
         *      3
         *        4
         *          5
         */
        showInOrder(treeNodes.get(3));

        treeNodes.add(createBinaryTree(new int[]{1}, new int[]{1}));
        System.out.println("4，只有一个节点的二叉树");
        showInOrder(treeNodes.get(4));

        treeNodes.add(createBinaryTree(new int[]{}, new int[]{}));
        System.out.println("5，没有节点的二叉树");
        showInOrder(treeNodes.get(5));

        System.out.println("========================");
        return treeNodes;
    }

    /**
     * 构造二叉树
     *
     * @param pre 先序遍历数组
     * @param in  中序遍历数组
     * @return 二叉树根节点
     */
    public TreeNode createBinaryTree(int[] pre, int[] in) {
        root = createBinaryTreeSon(pre, in, 0, pre.length - 1, 0, in.length - 1);
        return root;
    }

    /**
     * 递归构造二叉树
     *
     * @param pre
     * @param in
     * @param startPre
     * @param endPre
     * @param startIn
     * @param endIn
     * @return
     */
    private TreeNode createBinaryTreeSon(int[] pre, int[] in, int startPre, int endPre, int startIn, int endIn) {
        if (startPre > endPre || startIn > endIn) {
            return null;
        }
        // 前序列表的第一个值为当前的根节点
        TreeNode rootNow = new TreeNode(pre[startPre]);
        // 遍历中序列表
        for (int i = startIn; i <= endIn; i++) {
            if (in[i] == pre[startPre]) {
                //中序列表左边为当前的左子树
                rootNow.left = createBinaryTreeSon(pre, in, startPre + 1, startPre + i - startIn, startIn, i - 1);
                if (rootNow.left != null) {
                    rootNow.left.parent = rootNow;
                    //中序列表右边为当前的右子树
                    rootNow.right = createBinaryTreeSon(pre, in, startPre + i - startIn + 1, endPre, i + 1, endIn);
                    if (rootNow.right != null) {
                        rootNow.right.parent = rootNow;
                    }
                    break;
                }
            }
        }
        return rootNow;
    }

    public static void main(String[] args) {
        new UpToDownPrintTree3().test();
    }
}