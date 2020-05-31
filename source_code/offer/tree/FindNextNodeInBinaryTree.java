package tree;

/**
 * 二叉树工具类
 * - 用于生成不同类型的二叉树
 * - 用于测试不同类型的二叉树
 * <p>
 * Tips：
 * - 1.补充测试用例方法
 * - 2.补充算法题方法
 *
 * @author humingk
 */
public class FindNextNodeInBinaryTree<Value> {

    // ------------------------------------------------------------------------------------------
    // start
    /*
    相对模板类改变的地方：
        1.添加了parent节点

     */

    // 1.测试用例方法

    public void helpSolution(TreeNode treeNode, FindNextNodeInBinaryTree f) {
        if (treeNode != null) {
            if (treeNode.left != null) {
                helpSolution(treeNode.left, f);
            }
            TreeNode treeNodeTemp = f.solution(treeNode);
            if (treeNodeTemp != null) {
                System.out.print(treeNode.value + "->" + treeNodeTemp.value + " ");
            } else {
                System.out.print(treeNode.value + "->" + "null" + " ");
            }
            if (treeNode.right != null) {
                helpSolution(treeNode.right, f);
            }
        }
    }

    public void helpTest(String type, Integer[] pre, Integer[] in) {
        System.out.println(type);
        FindNextNodeInBinaryTree binaryTreeBase = new FindNextNodeInBinaryTree();
        binaryTreeBase.createBinaryTree(pre, in);
        binaryTreeBase.showInOrder(binaryTreeBase.root);
        System.out.println();

        // 测试
        helpSolution(binaryTreeBase.root, binaryTreeBase);

        System.out.println();
    }

    public void test() {
        helpTest("1，完全二叉树", new Integer[]{10, 6, 4, 8, 14, 12, 16}, new Integer[]{4, 6, 8, 10, 12, 14, 16});
        helpTest("2，非完全二叉树", new Integer[]{1, 2, 4, 7, 3, 5, 6, 8}, new Integer[]{4, 7, 2, 1, 5, 3, 8, 6});
        helpTest("3，没有右子节点的二叉树", new Integer[]{1, 2, 3, 4, 5}, new Integer[]{5, 4, 3, 2, 1});
        helpTest("4，没有左子节点的二叉树", new Integer[]{1, 2, 3, 4, 5}, new Integer[]{1, 2, 3, 4, 5});
        helpTest("5，只有一个节点的二叉树", new Integer[]{1}, new Integer[]{1});
        helpTest("6，没有节点的二叉树", new Integer[]{}, new Integer[]{});

    }
    // 2.算法题方法

    public TreeNode solution(TreeNode treeNode) {
        // 当前节点有右子树，当前节点的下一个节点为右子树中的中序遍历第一个节点
        if(treeNode.right!=null){
            treeNode=treeNode.right;
            while (treeNode.left!=null){
                treeNode=treeNode.left;
            }
            return treeNode;
        }
        // 当前节点为某节点的左子节点，当前节点的下一个节点为当前节点的父节点
        else if(treeNode.parent!=null && treeNode.parent.left==treeNode){
            return treeNode.parent;
        }
        // 当前节点为某节点的右子节点，当前节点为当前左子树中序遍历最后一个节点，当前节点的下一个节点为当前左子树的根节点
        else if(treeNode.parent!=null && treeNode.parent.right==treeNode){
            treeNode=treeNode.parent;
            while (treeNode!=null && treeNode.parent !=null){
                if(treeNode.parent.left==treeNode){
                    return treeNode.parent;
                }
                else {
                    treeNode=treeNode.parent;
                }
            }
        }
        return null;
    }

    // end
    // ------------------------------------------------------------------------------------------


    /**
     * 此二叉树根节点
     */
    public TreeNode root;

    /**
     * 二叉树结构
     */
    public class TreeNode {
        Value value;
        TreeNode left;
        TreeNode right;
        TreeNode parent;

        public TreeNode(Value value) {
            this.value = value;
        }
    }

    /**
     * 中序遍历打印二叉树
     *
     * @param nodeTree
     */
    public void showInOrder(TreeNode nodeTree) {
        if (nodeTree != null) {
            showInOrder(nodeTree.left);
            System.out.print(nodeTree.value + " ");
            showInOrder(nodeTree.right);
        }
    }

    /**
     * 构造二叉树
     *
     * @param pre 先序遍历数组
     * @param in  中序遍历数组
     * @return 二叉树根节点
     */
    public TreeNode createBinaryTree(Value[] pre, Value[] in) {
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
    private TreeNode createBinaryTreeSon(Value[] pre, Value[] in, int startPre, int endPre, int startIn, int endIn) {
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
        FindNextNodeInBinaryTree binaryTree = new FindNextNodeInBinaryTree();
        binaryTree.test();
    }
}