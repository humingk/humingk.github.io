package tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 二叉搜索树的后序遍历序列
 *
 * @author humingk
 */
public class IsSearchTreePostOrder {

    // ------------------------------------------------------------------------------------------
    // start

    // 1.测试用例方法

    public void test() {
        ArrayList<int[]> list = new ArrayList<>();
        /**
         *  1
         *    2
         *      3
         *        4
         *          5
         */
        list.add(new int[]{1, 2, 3, 4, 5});
        /**
         *               1
         *            2
         *         3
         *      4
         *   5
         */
        list.add(new int[]{5, 4, 3, 2, 1});
        /**
         *      10
         *   6      14
         * 4  8   12  16
         */
        list.add(new int[]{4, 8, 6, 12, 16, 14, 10});
        /**
         *          1
         *     2        3
         *  4        5      6
         *    7           8
         */
        list.add(new int[]{7, 4, 2, 5, 8, 6, 3, 1});
        /**
         *      4
         *         6
         *           8
         */
        list.add(new int[]{8, 6, 4});
        list.add(new int[]{7, 4, 6, 5});
        list.add(new int[]{4, 6, 7, 5});
        list.add(new int[]{});

        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).length; j++) {
                System.out.print(list.get(i)[j] + " ");
            }
            System.out.println(" ==> is post order ?");
            System.out.println(VerifySquenceOfBST(list.get(i)));
        }
    }

    // 2.算法题方法

    public boolean VerifySquenceOfBST(int[] sequence) {
        if (sequence == null || sequence.length == 0) {
            return false;
        } else if (sequence.length == 1) {
            return true;
        }
        return recursion(sequence, 0, sequence.length - 1);
    }

    private boolean recursion(int[] sequence, int start, int end) {
        if (start >= end) {
            return true;
        }
        // 从左向右，找出比根结点小的树为根结点的左子树
        int left = start;
        while (left < end && sequence[left] <= sequence[end]) {
            left++;
        }
        // 判断根结点右子树中是否有小于根结点的值，返回false
        int right = left;
        while (right < end) {
            if (sequence[right] < sequence[end]) {
                return false;
            }
            right++;
        }
        // 递归
        return recursion(sequence, start, left - 1) && recursion(sequence, left, end - 1);
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
     * 前序遍历打印二叉树
     *
     * @param nodeTree
     */
    public void showPreOrder(TreeNode nodeTree) {
        System.out.print("PreOrder: ");
        showPreOrderR(nodeTree);
        System.out.println();
    }

    private void showPreOrderR(TreeNode nodeTree) {
        if (nodeTree != null) {
            System.out.print(nodeTree.val + " ");
            showPreOrderR(nodeTree.left);
            showPreOrderR(nodeTree.right);
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
     * 后序遍历打印二叉树
     *
     * @param nodeTree
     */
    public void showPostOrder(TreeNode nodeTree) {
        System.out.print("PostOrder: ");
        showPostOrderR(nodeTree);
        System.out.println();
    }

    private void showPostOrderR(TreeNode nodeTree) {
        if (nodeTree != null) {
            showPostOrderR(nodeTree.left);
            showPostOrderR(nodeTree.right);
            System.out.print(nodeTree.val + " ");
        }
    }

    /**
     * 按层次打印二叉树
     *
     * @param root
     */
    public void showLevelOrder(TreeNode root) {
        System.out.println("levelOrder: ");
        ArrayList<TreeNode> queue = new ArrayList<>();
        // 当前行节点数
        int current = 0;
        // 下一行节点数
        int next = 0;
        queue.add(root);
        current++;
        while (!queue.isEmpty()) {
            TreeNode node = queue.remove(0);
            current--;
            if (node != null) {
                if (node.left != null) {
                    queue.add(node.left);
                    next++;
                }
                if (node.right != null) {
                    queue.add(node.right);
                    next++;
                }
                System.out.print(node.val + " ");
            }
            if (current == 0) {
                System.out.println();
                current = next;
                next = 0;
            }
        }
        System.out.println("------------------");
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
        showPreOrder(treeNodes.get(0));
        showInOrder(treeNodes.get(0));
        showPostOrder(treeNodes.get(0));
        showLevelOrder(treeNodes.get(0));

        treeNodes.add(createBinaryTree(new int[]{1, 2, 4, 7, 3, 5, 6, 8}, new int[]{4, 7, 2, 1, 5, 3, 8, 6}));
        System.out.println("1，非完全二叉树");
        /**
         *          1
         *     2        3
         *  4        5      6
         *    7           8
         */
        showPreOrder(treeNodes.get(1));
        showInOrder(treeNodes.get(1));
        showPostOrder(treeNodes.get(1));
        showLevelOrder(treeNodes.get(1));

        treeNodes.add(createBinaryTree(new int[]{1, 2, 3, 4, 5}, new int[]{5, 4, 3, 2, 1}));
        System.out.println("2，没有右子节点的二叉树");
        /**
         *               1
         *            2
         *         3
         *      4
         *   5
         */
        showPreOrder(treeNodes.get(2));
        showInOrder(treeNodes.get(2));
        showPostOrder(treeNodes.get(2));
        showLevelOrder(treeNodes.get(2));

        treeNodes.add(createBinaryTree(new int[]{1, 2, 3, 4, 5}, new int[]{1, 2, 3, 4, 5}));
        System.out.println("3，没有左子节点的二叉树");
        /**
         *  1
         *    2
         *      3
         *        4
         *          5
         */
        showPreOrder(treeNodes.get(3));
        showInOrder(treeNodes.get(3));
        showPostOrder(treeNodes.get(3));
        showLevelOrder(treeNodes.get(3));

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
        new IsSearchTreePostOrder().test();
    }
}