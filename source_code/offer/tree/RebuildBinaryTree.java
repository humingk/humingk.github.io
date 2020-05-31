package tree;

/**
 * @author humingk
 */
public class RebuildBinaryTree {
    TreeNode root;

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    /**
     * 重建二叉树
     *
     * @param pre
     * @param in
     * @return
     */
    public TreeNode rebuild(int[] pre, int[] in) {
        root = rebuildSon(pre, in, 0, pre.length - 1, 0, in.length - 1);
        return root;
    }

    /**
     * 重建二叉树子节点
     *
     * @param pre
     * @param in
     * @param startPre
     * @param endPre
     * @param startIn
     * @param endIn
     * @return
     */
    private TreeNode rebuildSon(int[] pre, int[] in, int startPre, int endPre, int startIn, int endIn) {
        if (startPre > endPre || startIn > endIn) {
            return null;
        }
        // 前序列表的第一个值为当前的根节点
        TreeNode rootNow = new TreeNode(pre[startPre]);
        // 遍历中序列表
        for (int i = startIn; i <= endIn; i++) {
            if (in[i] == pre[startPre]) {
                //中序列表左边为当前的左子树
                rootNow.left = rebuildSon(pre, in, startPre + 1, startPre + i - startIn, startIn, i - 1);
                //中序列表右边为当前的右子树
                rootNow.right = rebuildSon(pre, in, startPre + i - startIn + 1, endPre, i + 1, endIn);
                break;
            }
        }
        return rootNow;
    }

    public void show(TreeNode treeNode) {
        if (treeNode != null) {
            show(treeNode.left);
            System.out.print(treeNode.val + " ");
            show(treeNode.right);
        }
    }

    public static void main(String[] args) {
        System.out.println("1，完全二叉树");
        RebuildBinaryTree r1 = new RebuildBinaryTree();
        r1.rebuild(new int[]{10, 6, 4, 8, 14, 12, 16}, new int[]{4, 6, 8, 10, 12, 14, 16});
        r1.show(r1.root);
        System.out.println();

        System.out.println("2，非完全二叉树");
        RebuildBinaryTree r2 = new RebuildBinaryTree();
        r2.rebuild(new int[]{1, 2, 4, 7, 3, 5, 6, 8}, new int[]{4, 7, 2, 1, 5, 3, 8, 6});
        r2.show(r2.root);
        System.out.println();

        System.out.println("3，没有右子节点的二叉树");
        RebuildBinaryTree r3 = new RebuildBinaryTree();
        r3.rebuild(new int[]{1, 2, 3, 4, 5}, new int[]{5, 4, 3, 2, 1});
        r3.show(r3.root);
        System.out.println();

        System.out.println("4，没有左子节点的二叉树");
        RebuildBinaryTree r4 = new RebuildBinaryTree();
        r4.rebuild(new int[]{1, 2, 3, 4, 5}, new int[]{1, 2, 3, 4, 5});
        r4.show(r4.root);
        System.out.println();

        System.out.println("5，只有一个节点的二叉树");
        RebuildBinaryTree r5 = new RebuildBinaryTree();
        r5.rebuild(new int[]{1}, new int[]{1});
        r5.show(r5.root);
        System.out.println();

    }
}