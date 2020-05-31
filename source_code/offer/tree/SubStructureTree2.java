package tree;

import java.util.LinkedList;
import java.util.List;

/**
 * 树的子结构
 *
 * @author humingk
 */
public class SubStructureTree2 {

    // ------------------------------------------------------------------------------------------
    // start

    // 1.测试用例方法

    public void test() {
        List<TreeNode> treeNodes = createTreeNodes();
        treeNodes.add(createBinaryTree(new int[]{6, 4, 8}, new int[]{4, 6, 8}));
        System.out.println("6.子结构1");
        /**
         *   6
         * 4   8
         */
        showInOrder(treeNodes.get(6));

        treeNodes.add(createBinaryTree(new int[]{3, 5, 6}, new int[]{5, 3, 6}));
        System.out.println("7.子结构2");
        /**
         *      3
         *  5      6
         */
        showInOrder(treeNodes.get(7));

        treeNodes.add(createBinaryTree(new int[]{3, 5, 6, 8}, new int[]{5, 3, 8, 6}));
        System.out.println("8.子结构3");
        /**
         *      3
         *  5      6
         *       8
         */
        showInOrder(treeNodes.get(8));

        System.out.println("===================");
        for (int i = 0; i < treeNodes.size() - 3; i++) {
            showInOrder(treeNodes.get(i));
            showInOrder(treeNodes.get(treeNodes.size() - 1));
            System.out.println(HashSubtree(treeNodes.get(i), treeNodes.get(treeNodes.size() - 1)));
            showInOrder(treeNodes.get(treeNodes.size() - 2));
            System.out.println(HashSubtree(treeNodes.get(i), treeNodes.get(treeNodes.size() - 2)));
            showInOrder(treeNodes.get(treeNodes.size() - 3));
            System.out.println(HashSubtree(treeNodes.get(i), treeNodes.get(treeNodes.size() - 3)));
            System.out.println("----------------------------");
        }

        System.out.println("测试用例-wrong -----------");
        treeNodes.add(createBinaryTree(new int[]{7, 8, 9, 2, 5}, new int[]{5, 2, 9, 8, 7}));
        System.out.println("父结构xx");
        showInOrder(treeNodes.get(9));
        treeNodes.add(createBinaryTree(new int[]{8, 9, 3}, new int[]{3, 9, 8}));
        System.out.println("子结构xx");
        showInOrder(treeNodes.get(10));
        System.out.println(HashSubtree(treeNodes.get(treeNodes.size() - 2), treeNodes.get(treeNodes.size() - 1)));
    }

    // 2.算法题方法

    public boolean HashSubtree(TreeNode root1, TreeNode root2) {
        if (root1 == null || root2 == null) {
            return false;
        }
        return isSub(root1, root2) || HashSubtree(root1.left, root2) || HashSubtree(root1.right, root2);
    }

    /**
     * 递归判断是否相同
     *
     * @param root1
     * @param root2
     * @return
     */
    private boolean isSub(TreeNode root1, TreeNode root2) {
        if (root2 == null) {
            return true;
        }
        if (root1 == null) {
            return false;
        }
        if (root1.val == root2.val) {
            return isSub(root1.left, root2.left) && isSub(root1.right, root2.right);
        } else {
            return false;
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
        new SubStructureTree2().test();
    }
}