---
layout: post
title : 典型编程题笔记(4)-树(Java)
categories : algorithms
description : 
keywords :
---

- 典型的编程题，包括测试
- [github源代码地址](https://github.com/humingk/humingk.github.io/tree/master/source_code/offer/tree)
- 若未标明，默认的测试平台为：[牛客网](https://www.nowcoder.com)

---
### 二叉树模板工具类

- 生成不同类型的二叉树
- 测试不同用例的二叉树

后续完整代码只包括 test() 和 solution() 两个方法

**第3~9题**开始使用此模板

```java
package tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author humingk
 */
public class BinaryTreeBase {

    // ------------------------------------------------------------------------------------------
    // start

    // 1.测试用例方法

    public void test() {
        List<TreeNode> treeNodes = createTreeNodes();

    }

    // 2.算法题方法

    public TreeNode solution(TreeNode root) {


        return null;
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
//        showLevelOrder(treeNodes.get(0));

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
//        showLevelOrder(treeNodes.get(1));

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
//        showLevelOrder(treeNodes.get(2));

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
//        showLevelOrder(treeNodes.get(3));

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
        // 找出当前子二叉树的根节点在In中的位置 rootInIn
        int rootInIn = -1;
        for (int i = startIn; i <= endIn; i++) {
            if (pre[startPre] == in[i]) {
                rootInIn = i;
                break;
            }
        }
        // 创建新节点
        TreeNode rootTemp = null;
        // 新节点左子二叉树的总节点数
        int leftLength = rootInIn - startIn;
        // 新节点右子二叉树的总节点数
        int rightLength = endIn - rootInIn;

        // 新节点不为空
        if (rootInIn != -1) {
            rootTemp = new TreeNode(in[rootInIn]);
            // 新节点左子树不为空
            if (leftLength != 0) {
                rootTemp.left = createBinaryTreeSon(pre, in, startPre + 1, startPre + leftLength, startIn, startIn + leftLength - 1);
                rootTemp.left.parent = rootTemp;
            }
            // 新节点右子树不为空
            if (rightLength != 0) {
                rootTemp.right = createBinaryTreeSon(pre, in, endPre - rightLength + 1, endPre, endIn - rightLength + 1, endIn);
                rootTemp.right.parent = rootTemp;
            }
        }
        return rootTemp;
    }

    public static void main(String[] args) {
        new BinaryTreeBase().test();
    }
}
```

---

### 一、重建二叉树

[OJ](https://www.nowcoder.com/practice/8a19cbe657394eeaac2f6ea9b0f6fcf6?tpId=13&tqId=11157&tPage=1&rp=1&ru=%2Fta%2Fcoding-interviews&qru=%2Fta%2Fcoding-interviews%2Fquestion-ranking)

输入某二叉树的前序遍历和中序遍历的结果，请重建出该二叉树。假设输入的前序遍历和中序遍历的结果中都不含重复的数字。例如输入前序遍历序列{1,2,4,7,3,5,6,8}和中序遍历序列{4,7,2,1,5,3,8,6}，则重建二叉树并返回。

#### 解法1+测试 递归

前序遍历的第一个值就是根节点的值，对应的中序遍历，左边部分是左子树，右边部分是右子树，采用递归方式

```java
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
```

#### 运行结果

```java
1，完全二叉树
4 6 8 10 12 14 16 
2，非完全二叉树
4 7 2 1 5 3 8 6 
3，没有右子节点的二叉树
5 4 3 2 1 
4，没有左子节点的二叉树
1 2 3 4 5 
5，只有一个节点的二叉树
1 
```



---

### 二、二叉树的下一节点

[OJ](https://www.nowcoder.com/practice/9023a0c988684a53960365b889ceaf5e?tpId=13&tqId=11210&tPage=3&rp=1&ru=%2Fta%2Fcoding-interviews&qru=%2Fta%2Fcoding-interviews%2Fquestion-ranking)

给定一个二叉树和其中的一个节点，请找出中序遍历顺序的下一个节点并且返回。注意，树中的节点不仅包含左右子节点，同时包含指向父节点的指针。

#### 解法+测试

分三种情况，当前节点的下一个节点为：

- 当前节点有右子树，当前节点的下一个节点为右子树中的中序遍历第一个节点
- 当前节点为某节点的左子节点，当前节点的下一个节点为当前节点的父节点
- 当前节点为某节点的右子节点，当前节点为当前左子树中序遍历最后一个节点，当前节点的下一个节点为当前左子树的根节点

```java
    // ------------------------------------------------------------------------------------------
    // start
    /*
    相对模板类改变的地方：
        1.添加了parent节点

     */

    // 1.测试用例方法

    public void helpSolution(TreeNode treeNode, FindNextNodeInBinaryTree f) {
        if (treeNode != null) {
            if(treeNode.left!=null){
                helpSolution(treeNode.left,f);
            }
            TreeNode treeNodeTemp=f.solution(treeNode);
            if(treeNodeTemp!=null){
                System.out.print(treeNode.value + "->" + treeNodeTemp.value + " ");
            }
            else {
                System.out.print(treeNode.value + "->"+"null"+" ");
            }
            if(treeNode.right!=null){
                helpSolution(treeNode.right,f);
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
        helpSolution(binaryTreeBase.root,binaryTreeBase);

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
```

#### 运行结果

```java
1，完全二叉树
4 6 8 10 12 14 16 
4->6 6->8 8->10 10->12 12->14 14->16 16->null 
2，非完全二叉树
4 7 2 1 5 3 8 6 
4->7 7->2 2->1 1->5 5->3 3->8 8->6 6->null 
3，没有右子节点的二叉树
5 4 3 2 1 
5->4 4->3 3->2 2->1 1->null 
4，没有左子节点的二叉树
1 2 3 4 5 
1->2 2->3 3->4 4->5 5->null 
5，只有一个节点的二叉树
1 
1->null 
6，没有节点的二叉树

```

---



### 三、树的子结构

[OJ](https://www.nowcoder.com/practice/6e196c44c7004d15b1610b9afca8bd88?tpId=13&tqId=11170&tPage=1&rp=1&ru=%2Fta%2Fcoding-interviews&qru=%2Fta%2Fcoding-interviews%2Fquestion-ranking)

输入两棵二叉树A，B，判断B是不是A的子结构。（ps：我们约定空树不是任意一个树的子结构）

#### 解法1+测试 递归

先比较根节点，再用递归比较子树

```java
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
    }

    // 2.算法题方法

    public boolean HashSubtree(TreeNode root1, TreeNode root2) {
        boolean result = false;
        if (root1 != null && root2 != null) {
            // 当前节点作为根节点开始比较
            if (root1.val == root2.val) {
                result = isSub(root1, root2);
            }
            // 当前节点的左节点作为根节点开始比较
            else if (!result) {
                result = isSub(root1.left, root2);
            }
            // 当前节点的右节点作为根节点开始比较
            else if (!result) {
                result = isSub(root1.right, root2);
            }
        }
        return result;
    }

    /**
     * 递归判断是否相同
     *
     * @param root1
     * @param root2
     * @return
     */
    private boolean isSub(TreeNode root1, TreeNode root2) {
        // root2遍历到叶节点，为null
        if (root2 == null) {
            return true;
        }
        // root2此节点不为null，root1此节点却为null，不匹配
        if (root1 == null) {
            return false;
        }
        // 此节点不匹配
        if (root1.val != root2.val) {
            return false;
        }
        // 递归
        else {
            return isSub(root1.left, root2.left) && isSub(root1.right, root2.right);
        }
    }

    // end
    // ------------------------------------------------------------------------------------------
```

#### 运行结果

```java
0，完全二叉树
InOrder: 4 6 8 10 12 14 16 
1，非完全二叉树
InOrder: 4 7 2 1 5 3 8 6 
2，没有右子节点的二叉树
InOrder: 5 4 3 2 1 
3，没有左子节点的二叉树
InOrder: 1 2 3 4 5 
4，只有一个节点的二叉树
InOrder: 1 
5，没有节点的二叉树
InOrder: 
========================
6.子结构1
InOrder: 4 6 8 
7.子结构2
InOrder: 5 3 6 
8.子结构3
InOrder: 5 3 8 6 
===================
InOrder: 4 6 8 10 12 14 16 
InOrder: 5 3 8 6 
false
InOrder: 5 3 6 
false
InOrder: 4 6 8 
true
----------------------------
InOrder: 4 7 2 1 5 3 8 6 
InOrder: 5 3 8 6 
true
InOrder: 5 3 6 
true
InOrder: 4 6 8 
false
----------------------------
InOrder: 5 4 3 2 1 
InOrder: 5 3 8 6 
false
InOrder: 5 3 6 
false
InOrder: 4 6 8 
false
----------------------------
InOrder: 1 2 3 4 5 
InOrder: 5 3 8 6 
false
InOrder: 5 3 6 
false
InOrder: 4 6 8 
false
----------------------------
InOrder: 1 
InOrder: 5 3 8 6 
false
InOrder: 5 3 6 
false
InOrder: 4 6 8 
false
----------------------------
InOrder: 
InOrder: 5 3 8 6 
false
InOrder: 5 3 6 
false
InOrder: 4 6 8 
false
----------------------------
```

#### 解法2+测试 与、或短路递归

思路与解法1一致，但采用或、与运算短路的特性进行递归，大大简化代码

```java
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
```



------



### 四、镜像二叉树

[OJ](https://www.nowcoder.com/practice/564f4c26aa584921bc75623e48ca3011?tpId=13&tqId=11171&tPage=1&rp=1&ru=%2Fta%2Fcoding-interviews&qru=%2Fta%2Fcoding-interviews%2Fquestion-ranking)

操作给定的二叉树，将其变换为源二叉树的镜像。

```
二叉树的镜像定义：源二叉树 
    	    8
    	   /  \
    	  6   10
    	 / \  / \
    	5  7 9 11
    	镜像二叉树
    	    8
    	   /  \
    	  10   6
    	 / \  / \
    	11 9 7  5
```

#### 解法+测试 递归

递归交换左右子节点

```java
    // ------------------------------------------------------------------------------------------
    // start

    // 1.测试用例方法

    public void test() {
        List<TreeNode> treeNodes = createTreeNodes();
        for (int i = 0; i < treeNodes.size(); i++) {
            showInOrder(treeNodes.get(i));
            showInOrder(Mirror(treeNodes.get(i)));
            System.out.println("------------------------");
        }

    }

    // 2.算法题方法

    public TreeNode Mirror(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode tempNode = root.right;
        root.right = root.left;
        root.left = tempNode;
        Mirror(root.left);
        Mirror(root.right);
        return root;
    }

    // end
    // ------------------------------------------------------------------------------------------
```

#### 运行结果

```java
0，完全二叉树
InOrder: 4 6 8 10 12 14 16 
1，非完全二叉树
InOrder: 4 7 2 1 5 3 8 6 
2，没有右子节点的二叉树
InOrder: 5 4 3 2 1 
3，没有左子节点的二叉树
InOrder: 1 2 3 4 5 
4，只有一个节点的二叉树
InOrder: 1 
5，没有节点的二叉树
InOrder: 
========================
InOrder: 4 6 8 10 12 14 16 
InOrder: 16 14 12 10 8 6 4 
------------------------
InOrder: 4 7 2 1 5 3 8 6 
InOrder: 6 8 3 5 1 2 7 4 
------------------------
InOrder: 5 4 3 2 1 
InOrder: 1 2 3 4 5 
------------------------
InOrder: 1 2 3 4 5 
InOrder: 5 4 3 2 1 
------------------------
InOrder: 1 
InOrder: 1 
------------------------
InOrder: 
InOrder: 
------------------------
```

------



### 五、对称二叉树

[OJ](https://www.nowcoder.com/practice/ff05d44dfdb04e1d83bdbdab320efbcb?tpId=13&tqId=11211&tPage=3&rp=1&ru=%2Fta%2Fcoding-interviews&qru=%2Fta%2Fcoding-interviews%2Fquestion-ranking)

请实现一个函数，用来判断一颗二叉树是不是对称的。注意，如果一个二叉树同此二叉树的镜像是同样的，定义其为对称的。

#### 解法+测试 遍历

不同的两种遍历，值相同

- 前序遍历

  先访问根节点，再访问左节点，再访问右节点

- 后序遍历

  先访问根节点，再访问右节点，再访问左节点

```java
    // ------------------------------------------------------------------------------------------
    // start

    // 1.测试用例方法

    public void test() {
        List<TreeNode> treeNodes = createTreeNodes();
        treeNodes.add(createBinaryTree(new int[]{8, 6, 5, 7, 6, 7, 5}, new int[]{5, 6, 7, 8, 7, 6, 5}));
        /**
         *      8
         *    6    6
         *   5 7  7 5
         */
        for (int i = 0; i < treeNodes.size(); i++) {
            showInOrder(treeNodes.get(i));
            System.out.println(solution(treeNodes.get(i)));
            System.out.println("-----------------");
        }
    }

    // 2.算法题方法

    public boolean solution(TreeNode pRoot) {
        return isSymmetry(pRoot, pRoot);
    }

    /**
     * 不同遍历顺序
     *
     * @param pRoot1
     * @param pRoot2
     * @return
     */
    private boolean isSymmetry(TreeNode pRoot1, TreeNode pRoot2) {
        if (pRoot1 == null && pRoot2 == null) {
            return true;
        } else if (pRoot1 == null || pRoot2 == null) {
            return false;
        } else if (pRoot1.val != pRoot2.val) {
            return false;
        } else {
            return isSymmetry(pRoot1.left, pRoot2.right) && isSymmetry(pRoot1.right, pRoot2.left);
        }
    }

    // end
    // ------------------------------------------------------------------------------------------
```

#### 运行结果

```java
0，完全二叉树
InOrder: 4 6 8 10 12 14 16 
1，非完全二叉树
InOrder: 4 7 2 1 5 3 8 6 
2，没有右子节点的二叉树
InOrder: 5 4 3 2 1 
3，没有左子节点的二叉树
InOrder: 1 2 3 4 5 
4，只有一个节点的二叉树
InOrder: 1 
5，没有节点的二叉树
InOrder: 
========================
InOrder: 4 6 8 10 12 14 16 
false
-----------------
InOrder: 4 7 2 1 5 3 8 6 
false
-----------------
InOrder: 5 4 3 2 1 
false
-----------------
InOrder: 1 2 3 4 5 
false
-----------------
InOrder: 1 
true
-----------------
InOrder: 
true
-----------------
InOrder: 5 6 7 8 7 6 5 
true
-----------------
```

------



### 六、从上到下打印二叉树

[OJ](https://www.nowcoder.com/practice/7fe2212963db4790b57431d9ed259701?tpId=13&tqId=11175&tPage=2&rp=1&ru=%2Fta%2Fcoding-interviews&qru=%2Fta%2Fcoding-interviews%2Fquestion-ranking)

从上往下打印出二叉树的每个节点，同层节点从左至右打印。

![之字形打印](../img/alg/z_tree_print.png)

#### 解法1 +测试 打印一行

打印成一行

用队列依次添加当前节点的左节点和右节点，循环条件是队列不为空

```java
    // ------------------------------------------------------------------------------------------
    // start

    // 1.测试用例方法

    public void test() {
        List<TreeNode> treeNodes = createTreeNodes();
        for (int i = 0; i < treeNodes.size(); i++) {
            showInOrder(treeNodes.get(i));
            ArrayList<Integer> result = solution(treeNodes.get(i));
            for (int j = 0; j < result.size(); j++) {
                System.out.print(result.get(j) + " ");
            }
            System.out.println();
            System.out.println("-------------------------------------");
        }

    }

    // 2.算法题方法

    public ArrayList<Integer> solution(TreeNode root) {
        ArrayList<Integer> result = new ArrayList<>();
        ArrayList<TreeNode> queue = new ArrayList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.remove(0);
            if (node != null) {
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
                result.add(node.val);
            }
        }
        return result;
    }

    // end
    // ------------------------------------------------------------------------------------------
```

#### 运行结果

```java
0，完全二叉树
InOrder: 4 6 8 10 12 14 16 
1，非完全二叉树
InOrder: 4 7 2 1 5 3 8 6 
2，没有右子节点的二叉树
InOrder: 5 4 3 2 1 
3，没有左子节点的二叉树
InOrder: 1 2 3 4 5 
4，只有一个节点的二叉树
InOrder: 1 
5，没有节点的二叉树
InOrder: 
========================
InOrder: 4 6 8 10 12 14 16 
10 6 14 4 8 12 16 
-------------------------------------
InOrder: 4 7 2 1 5 3 8 6 
1 2 3 4 5 6 7 8 
-------------------------------------
InOrder: 5 4 3 2 1 
1 2 3 4 5 
-------------------------------------
InOrder: 1 2 3 4 5 
1 2 3 4 5 
-------------------------------------
InOrder: 1 
1 
-------------------------------------
InOrder: 

-------------------------------------
```



#### 解法2 +测试 打印分行

暂无OJ

分别计数队列中当前行的个数和下一行的个数

```java
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
    }

    // end
    // ------------------------------------------------------------------------------------------
```

#### 运行结果2

```java
0，完全二叉树
InOrder: 4 6 8 10 12 14 16 
1，非完全二叉树
InOrder: 4 7 2 1 5 3 8 6 
2，没有右子节点的二叉树
InOrder: 5 4 3 2 1 
3，没有左子节点的二叉树
InOrder: 1 2 3 4 5 
4，只有一个节点的二叉树
InOrder: 1 
5，没有节点的二叉树
InOrder: 
========================
InOrder: 4 6 8 10 12 14 16 
10 
6 14 
4 8 12 16 
-------------------------------------
InOrder: 4 7 2 1 5 3 8 6 
1 
2 3 
4 5 6 
7 8 
-------------------------------------
InOrder: 5 4 3 2 1 
1 
2 
3 
4 
5 
-------------------------------------
InOrder: 1 2 3 4 5 
1 
2 
3 
4 
5 
-------------------------------------
InOrder: 1 
1 
-------------------------------------
InOrder: 

-------------------------------------
```

#### 解法3+测试 打印之字行

暂无OJ

用两个栈来分别保存奇数行和偶数行的节点

其中，打印偶数行的时候，保存下一行(奇数行)子结点，先保存右节点，再保存左节点

奇数行相反

如下图所示:

```java
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
```

#### 运行结果3

```java
0，完全二叉树
InOrder: 4 6 8 10 12 14 16 
1，非完全二叉树
InOrder: 4 7 2 1 5 3 8 6 
2，没有右子节点的二叉树
InOrder: 5 4 3 2 1 
3，没有左子节点的二叉树
InOrder: 1 2 3 4 5 
4，只有一个节点的二叉树
InOrder: 1 
5，没有节点的二叉树
InOrder: 
========================
InOrder: 4 6 8 10 12 14 16 
10 
14 6 
4 8 12 16 
-------------------------------------
InOrder: 4 7 2 1 5 3 8 6 
1 
3 2 
4 5 6 
8 7 
-------------------------------------
InOrder: 5 4 3 2 1 
1 
2 
3 
4 
5 
-------------------------------------
InOrder: 1 2 3 4 5 
1 
2 
3 
4 
5 
-------------------------------------
InOrder: 1 
1 
-------------------------------------
InOrder: 
-------------------------------------
```



------



### 七、二叉搜索树的后序遍历序列

[OJ](https://www.nowcoder.com/practice/a861533d45854474ac791d90e447bafd?tpId=13&tqId=11176&tPage=2&rp=1&ru=%2Fta%2Fcoding-interviews&qru=%2Fta%2Fcoding-interviews%2Fquestion-ranking)

输入一个整数数组，判断该数组是不是某二叉搜索树的后序遍历的结果。如果是则输出Yes,否则输出No。假设输入的数组的任意两个数字都互不相同。

#### 解法+测试 递归二分



递归

在后序遍历中，最后一位为树的根结点，前面可分为两部分：

- 左子树，都比根结点小
- 右子树，不能有比根结点小的数，如果有返回false

```java
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
```

#### 运行结果

```java
1 2 3 4 5  ==> is post order ?
true
5 4 3 2 1  ==> is post order ?
true
4 8 6 12 16 14 10  ==> is post order ?
true
7 4 2 5 8 6 3 1  ==> is post order ?
false
8 6 4  ==> is post order ?
true
7 4 6 5  ==> is post order ?
false
4 6 7 5  ==> is post order ?
true
 ==> is post order ?
false
```

------



### 八、二叉树中和为某一值的路径

[OJ](https://www.nowcoder.com/practice/b736e784e3e34731af99065031301bca?tpId=13&tqId=11177&tPage=2&rp=1&ru=%2Fta%2Fcoding-interviews&qru=%2Fta%2Fcoding-interviews%2Fquestion-ranking)

输入一颗二叉树的根节点和一个整数，打印出二叉树中结点值的和为输入整数的所有路径。路径定义为从树的根结点开始往下一直到叶结点所经过的结点形成一条路径。(注意: 在返回值的list中，数组长度大的数组靠前)

#### 解法+测试 递归(DFS)

前序遍历递归访问二叉树

在叶节点的时候判断路径的和值是否满足要求

每一次递归的结尾都要删除当前节点以及当前路径和值减去当前节点

```java
    // ------------------------------------------------------------------------------------------
    // start

    // 1.测试用例方法

    public void test() {
        List<TreeNode> treeNodes = createTreeNodes();
        treeNodes.add(createBinaryTree(new int[]{10, 5, 4, 7, 12}, new int[]{4, 5, 7, 10, 12}));
        System.out.println("6.多条相同路径二叉树");
        showLevelOrder(treeNodes.get(6));

        for (int i = 0; i < treeNodes.size(); i++) {
            showInOrder(treeNodes.get(i));
            System.out.println("target:22 ");
            showArraylists(solution(treeNodes.get(i), 22));
            System.out.println("target:24 ");
            showArraylists(solution(treeNodes.get(i), 24));
            System.out.println("target:0 ");
            showArraylists(solution(treeNodes.get(i), 0));
            System.out.println("---------------------------------");
        }


    }

    private void showArraylists(ArrayList<ArrayList<Integer>> arrayLists) {
        if (arrayLists == null) {
            return;
        }
        for (int i = 0; i < arrayLists.size(); i++) {
            for (int j = 0; j < arrayLists.get(i).size(); j++) {
                System.out.print(arrayLists.get(i).get(j) + " -> ");
            }
            System.out.println();
        }
    }

    // 2.算法题方法

    public ArrayList<ArrayList<Integer>> solution(TreeNode root, int target) {
        if (root == null) {
            if (target == 0) {
                return new ArrayList<>();
            }
            return null;
        }
        ArrayList<ArrayList<Integer>> paths = new ArrayList<>();
        ArrayList<Integer> path = new ArrayList<>();
        recursion(root, target, 0, path, paths);
        return paths;
    }

    private void recursion(TreeNode root, int targetSum, int currentSum, ArrayList<Integer> path, ArrayList<ArrayList<Integer>> paths) {
        currentSum += root.val;
        path.add(root.val);
        if (root.left == null && root.right == null) {
            if (currentSum == targetSum) {
                // 保存当前符合条件的路径 
                paths.add(new ArrayList<>());
                for (int i = 0; i < path.size(); i++) {
                    paths.get(paths.size() - 1).add(path.get(i));
                }
            }
        }
        // 前序遍历
        if (root.left != null) {
            recursion(root.left, targetSum, currentSum, path, paths);
        }
        if (root.right != null) {
            recursion(root.right, targetSum, currentSum, path, paths);
        }
        // 在每一次调用完left right的时候，删除当前节点
        path.remove(path.size() - 1);
        currentSum -= root.val;
    }

    // end
    // ------------------------------------------------------------------------------------------
```

#### 运行结果

```java
0，完全二叉树
PreOrder: 10 6 4 8 14 12 16 
InOrder: 4 6 8 10 12 14 16 
PostOrder: 4 8 6 12 16 14 10 
1，非完全二叉树
PreOrder: 1 2 4 7 3 5 6 8 
InOrder: 4 7 2 1 5 3 8 6 
PostOrder: 7 4 2 5 8 6 3 1 
2，没有右子节点的二叉树
PreOrder: 1 2 3 4 5 
InOrder: 5 4 3 2 1 
PostOrder: 5 4 3 2 1 
3，没有左子节点的二叉树
PreOrder: 1 2 3 4 5 
InOrder: 1 2 3 4 5 
PostOrder: 5 4 3 2 1 
4，只有一个节点的二叉树
InOrder: 1 
5，没有节点的二叉树
InOrder: 
========================
6.多条相同路径二叉树
levelOrder: 
10 
5 12 
4 7 
------------------
InOrder: 4 6 8 10 12 14 16 
target:22 
target:24 
10 -> 6 -> 8 -> 
target:0 
---------------------------------
InOrder: 4 7 2 1 5 3 8 6 
target:22 
target:24 
target:0 
---------------------------------
InOrder: 5 4 3 2 1 
target:22 
target:24 
target:0 
---------------------------------
InOrder: 1 2 3 4 5 
target:22 
target:24 
target:0 
---------------------------------
InOrder: 1 
target:22 
target:24 
target:0 
---------------------------------
InOrder: 
target:22 
target:24 
target:0 
---------------------------------
InOrder: 4 5 7 10 12 
target:22 
10 -> 5 -> 7 -> 
10 -> 12 -> 
target:24 
target:0 
---------------------------------
```

------



### 九、二叉搜索树与双向链表

[OJ](https://www.nowcoder.com/practice/947f6eb80d944a84850b0538bf0ec3a5?tpId=13&tqId=11179&tPage=2&rp=1&ru=%2Fta%2Fcoding-interviews&qru=%2Fta%2Fcoding-interviews%2Fquestion-ranking)

输入一棵二叉搜索树，将该二叉搜索树转换成一个排序的双向链表。要求不能创建任何新的结点，只能调整树中结点指针的指向。

#### 解法1+测试 中序遍历递归

中序遍历也可以用循环实现而不用递归实现

```java
    // ------------------------------------------------------------------------------------------
    // start

    // 1.测试用例方法

    public void test() {
        List<TreeNode> treeNodes = createTreeNodes();
        showInOrder(treeNodes.get(0));
        showDoubleList(solution(treeNodes.get(0)));
        System.out.println("-----------------------------");

        showInOrder(treeNodes.get(3));
        showDoubleList(solution(treeNodes.get(3)));
        System.out.println("-----------------------------");

        treeNodes.add(createBinaryTree(new int[]{5, 4, 3, 2, 1}, new int[]{1, 2, 3, 4, 5}));
        showInOrder(treeNodes.get(6));
        showDoubleList(solution(treeNodes.get(6)));
        System.out.println("-----------------------------");

        treeNodes.add(createBinaryTree(new int[]{10, 6, 4, 14, 12}, new int[]{4, 6, 10, 12, 14}));
        showInOrder(treeNodes.get(7));
        showDoubleList(solution(treeNodes.get(7)));
        System.out.println("-----------------------------");

    }

    private void showDoubleList(TreeNode head) {
        System.out.println("double list:");
        TreeNode last = null;
        while (head != null) {
            System.out.print(head.val + " -> ");
            if (head.right == null) {
                last = head;
            }
            head = head.right;
        }
        System.out.println();
        while (last != null) {
            System.out.print(last.val + " -> ");
            last = last.left;
        }
        System.out.println();
    }

    // 2.算法题方法

    /**
     * 已经转换好的双向链表的最后一个节点
     */
    private TreeNode listNode;

    public TreeNode solution(TreeNode pRootOfTree) {
        if (pRootOfTree == null) {
            return null;
        }
        // 中序遍历递归
        listNode = null;
        recursion(pRootOfTree);
        while (pRootOfTree.left != null) {
            pRootOfTree = pRootOfTree.left;
        }
        return pRootOfTree;
    }

    /**
     * 中序遍历递归
     *
     * @param root
     */
    private void recursion(TreeNode root) {
        if (root.left != null) {
            recursion(root.left);
        }
        // 头结点
        if (listNode == null) {
            listNode = root;
        }
        // 树指针转换为链表指针
        else {
            listNode.right = root;
            root.left = listNode;
            listNode = listNode.right;
        }
        if (root.right != null) {
            recursion(root.right);
        }
    }

    // end
    // ------------------------------------------------------------------------------------------
```

#### 运行结果

```java
0，完全二叉树
PreOrder: 10 6 4 8 14 12 16 
InOrder: 4 6 8 10 12 14 16 
PostOrder: 4 8 6 12 16 14 10 
1，非完全二叉树
PreOrder: 1 2 4 7 3 5 6 8 
InOrder: 4 7 2 1 5 3 8 6 
PostOrder: 7 4 2 5 8 6 3 1 
2，没有右子节点的二叉树
PreOrder: 1 2 3 4 5 
InOrder: 5 4 3 2 1 
PostOrder: 5 4 3 2 1 
3，没有左子节点的二叉树
PreOrder: 1 2 3 4 5 
InOrder: 1 2 3 4 5 
PostOrder: 5 4 3 2 1 
4，只有一个节点的二叉树
InOrder: 1 
5，没有节点的二叉树
InOrder: 
========================
InOrder: 4 6 8 10 12 14 16 
double list:
4 -> 6 -> 8 -> 10 -> 12 -> 14 -> 16 -> 
16 -> 14 -> 12 -> 10 -> 8 -> 6 -> 4 -> 
-----------------------------
InOrder: 1 2 3 4 5 
double list:
1 -> 2 -> 3 -> 4 -> 5 -> 
5 -> 4 -> 3 -> 2 -> 1 -> 
-----------------------------
InOrder: 1 2 3 4 5 
double list:
1 -> 2 -> 3 -> 4 -> 5 -> 
5 -> 4 -> 3 -> 2 -> 1 -> 
-----------------------------
InOrder: 4 6 10 12 14 
double list:
4 -> 6 -> 10 -> 12 -> 14 -> 
14 -> 12 -> 10 -> 6 -> 4 -> 
-----------------------------
```

------

#### 解法2 递归构造

分别将左右子树构造成双链表，再连起来

```java
/**
public class TreeNode {
    int val = 0;
    TreeNode left = null;
    TreeNode right = null;

    public TreeNode(int val) {
        this.val = val;

    }

}
*/
public class Solution {
    public TreeNode Convert(TreeNode pRootOfTree) {
        if(pRootOfTree==null){
            return null;
        }
        if(pRootOfTree.left==null&&pRootOfTree.right==null){
            
            return pRootOfTree;
        }
        // 将左子树构成双链表
        TreeNode left=Convert(pRootOfTree.left);
        // 左双链表末尾
        TreeNode lastNode=left;
        while(lastNode!=null&&lastNode.right!=null){
            lastNode=lastNode.right;
        }
        // 左双链表末尾接上当前根结点
        if(left!=null){
            pRootOfTree.left=lastNode;
            lastNode.right=pRootOfTree;
        }
        // 将右子树构成双链表
        TreeNode right=Convert(pRootOfTree.right);
        // 右双链表开头
        TreeNode firstNode=right;
        while(firstNode!=null&&firstNode.left!=null){
            firstNode=firstNode.left;
        }
        // 当前根结点接上右双链表开头
        if(right!=null){
            pRootOfTree.right=firstNode;
            firstNode.left=pRootOfTree;
        }
        return left==null?pRootOfTree:left;
    }
}

```

### 十、二叉树序列化

[OJ](https://www.nowcoder.com/practice/cf7e25aa97c04cc1a68c8f040e71fb84?tpId=13&tqId=11214&tPage=4&rp=1&ru=%2Fta%2Fcoding-interviews&qru=%2Fta%2Fcoding-interviews%2Fquestion-ranking)

请实现两个函数，分别用来序列化和反序列化二叉树

#### 解法1 先序遍历递归

```java
/*
public class TreeNode {
    int val = 0;
    TreeNode left = null;
    TreeNode right = null;

    public TreeNode(int val) {
        this.val = val;

    }

}
*/
public class Solution {
    String Serialize(TreeNode root) {
        StringBuffer result=new StringBuffer();
        if(root==null){
            result.append("#,");
        }else{
            result.append(root.val+",");
            result.append(Serialize(root.left));
            result.append(Serialize(root.right));
        }
        return result.toString();
  }
    String[] strs;
    int index=-1;
    TreeNode Deserialize(String str) {
        if(str==null){
            return null;
        }
        index++;
        if(index==0){
            strs=str.split(",");
        }
        TreeNode root=null;
        if(!strs[index].equals("#")){
            root = new TreeNode(Integer.valueOf(strs[index]));
            root.left=Deserialize("");
            root.right=Deserialize("");
        }
        return root;
        
  }
}
```

------

### 十二、二叉搜索树的第K个节点

[OJ](https://www.nowcoder.com/practice/ef068f602dde4d28aab2b210e859150a?tpId=13&tqId=11215&tPage=4&rp=1&ru=%2Fta%2Fcoding-interviews&qru=%2Fta%2Fcoding-interviews%2Fquestion-ranking)

给定一棵二叉搜索树，请找出其中的第k小的结点。例如， （5，3，7，2，4，6，8）    中，按结点数值大小顺序第三小结点的值为4。



#### 解法1 中序遍历递归

二叉搜索树按照中序遍历的顺序即为从小到大的顺序

```java
/*
public class TreeNode {
    int val = 0;
    TreeNode left = null;
    TreeNode right = null;

    public TreeNode(int val) {
        this.val = val;

    }

}
*/
public class Solution {
    // 计数器
    public int count=0;
    TreeNode KthNode(TreeNode pRoot, int k)
    {
        if(pRoot!=null){
            // 向左递归
            TreeNode target=KthNode(pRoot.left,k);
            if(target!=null){
                return target;
            }
            // 找到第k个节点
            if(++count==k){
                return pRoot;
            }
            // 向右递归
            target=KthNode(pRoot.right,k);
            if(target!=null){
                return target;
            }
        }
        return null;
    }


}
```

------



### 十三、二叉树的深度

[OJ](https://www.nowcoder.com/practice/435fb86331474282a3499955f0a41e8b?tpId=13&tqId=11191&tPage=2&rp=1&ru=%2Fta%2Fcoding-interviews&qru=%2Fta%2Fcoding-interviews%2Fquestion-ranking)

输入一棵二叉树，求该树的深度。从根结点到叶结点依次经过的结点（含根、叶结点）形成树的一条路径，最长路径的长度为树的深度。



#### 解法1 递归

求以当前节点为根节点的树的深度，也就是求当前左子树和当前右子树的最深深度+1

```java
/**
public class TreeNode {
    int val = 0;
    TreeNode left = null;
    TreeNode right = null;

    public TreeNode(int val) {
        this.val = val;

    }

}
*/
public class Solution {
    public int TreeDepth(TreeNode root) {
        if(root==null){
            return 0;
        }else{
            return Math.max(TreeDepth(root.left),TreeDepth(root.right))+1;
        }
    }
}
```

------

### 十四、平衡二叉树

[OJ](https://www.nowcoder.com/practice/8b3b95850edb4115918ecebdf1b4d222?tpId=13&tqId=11192&tPage=2&rp=1&ru=%2Fta%2Fcoding-interviews&qru=%2Fta%2Fcoding-interviews%2Fquestion-ranking)

输入一棵二叉树，判断该二叉树是否是平衡二叉树。

#### 解法1 从上到下深度差比较

递归遍历并且每次对比左右节点的深度差(二次递归)是否大于1

缺点：

每一次遍历到某个节点，都要再次遍历这个节点的左右子树

```java
public class Solution {
    public boolean IsBalanced_Solution(TreeNode root) {
        if(root==null){
            return true;
        }
        int left=TreeDepth(root.left);
        int right=TreeDepth(root.right);
        if(left-right<-1||left-right>1){
            return false;
        }
        return IsBalanced_Solution(root.left) && IsBalanced_Solution(root.right);
    }
    private int TreeDepth(TreeNode root) {
        if(root==null){
            return 0;
        }else{
            int left=TreeDepth(root.left);
            int right=TreeDepth(root.right);
            return Math.max(left,right)+1;
        }
    }
}
```

------

#### 解法2 后序遍历深度差比较

递归后序遍历的同时比较左右子树的深度差是否大于1

后序遍历，遍历根节点的时候，已经遍历左右子树

比较左右子树的深度差的时候，向上传递深度

```java
public class Solution {
    public boolean IsBalanced_Solution(TreeNode root) {
        if(root==null){
            return true;
        }
        if(recursion(root)==-1){
            return false;
        }else{
            return true;
        }
    }
    private int recursion(TreeNode root){
        if(root==null){
            return 0;
        }
        int left=recursion(root.left);
        // 左子树已经不平衡
        if(left==-1){
            return -1;
        }
        int right=recursion(root.right);
        // 右子树已经不平衡
        if(right==-1){
            return -1;
        }
        // 左右子树分别平衡，但当前根节点对应的树不平衡
        if(left-right<-1||left-right>1){
            return -1;
        }else{
            return Math.max(left,right)+1;
        }
    }
}
```

------

### 十五、 二叉搜索树的最低公共祖先

[OJ](https://leetcode-cn.com/problems/lowest-common-ancestor-of-a-binary-search-tree)

给定一个二叉搜索树, 找到该树中两个指定节点的最近公共祖先。

百度百科中最近公共祖先的定义为：“对于有根树 T 的两个结点 p、q，最近公共祖先表示为一个结点 x，满足 x 是 p、q 的祖先且 x 的深度尽可能大（一个节点也可以是它自己的祖先）。”

例如，给定如下二叉搜索树:  root = [6,2,8,0,4,7,9,null,null,3,5] 

示例 1:

输入: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 8
输出: 6 
解释: 节点 2 和节点 8 的最近公共祖先是 6。
示例 2:

输入: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 4
输出: 2
解释: 节点 2 和节点 4 的最近公共祖先是 2, 因为根据定义最近公共祖先节点可以为节点本身。


说明:

所有节点的值都是唯一的。
p、q 为不同节点且均存在于给定的二叉搜索树中。

#### 解法1 从上到下递归

从上到下递归，第一个位于p、q之间的节点就是最低公共祖先

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return null;
        }
        // 标记p q大小
        boolean flag = q.val > p.val;
        // true: root < p < q
        if (flag ? root.val < p.val : root.val < q.val) {
            return lowestCommonAncestor(root.right, p, q);
        }
        // true: root > q > p
        else if (flag ? root.val > q.val : root.val > p.val) {
            return lowestCommonAncestor(root.left, p, q);
        } 
        // true: p < root < q
        else {
            return root;
        }
    }
}
```

------

### 十六、普通二叉树的最低公共祖先

[OJ](https://leetcode-cn.com/problems/lowest-common-ancestor-of-a-binary-tree/submissions/)

给定一个二叉树, 找到该树中两个指定节点的最近公共祖先。

百度百科中最近公共祖先的定义为：“对于有根树 T 的两个结点 p、q，最近公共祖先表示为一个结点 x，满足 x 是 p、q 的祖先且 x 的深度尽可能大（一个节点也可以是它自己的祖先）。”

例如，给定如下二叉树:  root = [3,5,1,6,2,0,8,null,null,7,4] 

示例 1:

输入: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
输出: 3
解释: 节点 5 和节点 1 的最近公共祖先是节点 3。
示例 2:

输入: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 4
输出: 5
解释: 节点 5 和节点 4 的最近公共祖先是节点 5。因为根据定义最近公共祖先节点可以为节点本身。


说明:

所有节点的值都是唯一的。
p、q 为不同节点且均存在于给定的二叉树中。

#### 解法1 从下到上递归

和上一题 [十五、 二叉搜索树的最低公共祖先](https://humingk.github.io/algorithms-program_tree/#十五-二叉搜索树的最低公共祖先)  类似，即：

从上到下递归，第一个位于p、q之间的节点就是最低公共祖先



但这里不是二叉搜索树，不能通过比较当前节点、p、q的大小来判断当前节点是否在p、q之间

因此从下到上传递当前节点左右子树是否分别有p、q的信息，即：

- 若当前节点的左/右子树中有p/q，则当前节点的左/右节点为非null
- 若当前节点的左/右子树中没有p/q，则当前节点的左/右节点为null

这样就可以判断某个节点是否是公共祖先

而从下到上，则第一个公共祖先为最低公共祖先

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // 如果遇到了p、q，则向上传递p、q
        // 除了传递p、q,没有p、q的路径上向上传递的是null
        if(root==null || root.val==p.val || root.val==q.val){
            return root;
        }
        // 左子树递归
        TreeNode left=lowestCommonAncestor(root.left,p,q);
        // 右子树递归
        TreeNode right=lowestCommonAncestor(root.right,p,q);
        if(left==null){
            return right;
        }else{
            if(right==null){
                return left;
            }else{
                // 当前节点左右都同时收到了传递而来的p、q（即left和right都为非null）
                return root;
            }
        }
    }
}
```



#### 解法2 路径比较

用两个栈分别存储从根节点到相应节点的路径

再比较路径，最后一个相同的节点（队列的话）

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 * int val;
 * TreeNode left;
 * TreeNode right;
 * TreeNode(int x) { val = x; }
 * }
 */

import java.util.Stack;

class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return null;
        }
        Stack<TreeNode> pStack = new Stack<>();
        Stack<TreeNode> qStack = new Stack<>();
        findPath(root, p, pStack);
        findPath(root, q, qStack);
        int pSize = pStack.size();
        int qSize = qStack.size();
        // 保留最小长度
        for (int i = 0; i < Math.abs(pSize - qSize); i++) {
            if (pSize > qSize) {
                pStack.pop();
            } else {
                qStack.pop();
            }
        }
        // 找出最近的公共元素
        while (pStack.peek().val != qStack.peek().val) {
            pStack.pop();
            qStack.pop();
        }
        return pStack.peek();
    }

    private TreeNode findPath(TreeNode root, TreeNode x, Stack<TreeNode> xStack) {
        if (root == null) {
            return null;
        }
        xStack.push(root);
        // 到达终点
        if (x.val == root.val) {
            return root;
        }
        TreeNode left = findPath(root.left, x,xStack);
        TreeNode right = findPath(root.right, x,xStack);
        // 此路不通，后退
        if (left == null && right == null) {
            xStack.pop();
            return null;
        }
        return root;
    }
}	
```





### 十七、二叉树最大宽度

[OJ](https://leetcode-cn.com/problems/maximum-width-of-binary-tree)

给定一个二叉树，编写一个函数来获取这个树的最大宽度。树的宽度是所有层中的最大宽度。这个二叉树与满二叉树（full binary tree）结构相同，但一些节点为空。

每一层的宽度被定义为两个端点（该层最左和最右的非空节点，两端点间的null节点也计入长度）之间的长度。

示例 1:

输入: 

           1
         /   \
        3     2
       / \     \  
      5   3     9 

输出: 4
解释: 最大值出现在树的第 3 层，宽度为 4 (5,3,null,9)。
示例 2:

输入: 

          1
         /  
        3    
       / \       
      5   3     

输出: 2
解释: 最大值出现在树的第 3 层，宽度为 2 (5,3)。
示例 3:

输入: 

          1
         / \
        3   2 
       /        
      5      

输出: 2
解释: 最大值出现在树的第 2 层，宽度为 2 (3,2)。
示例 4:

输入: 

          1
         / \
        3   2
       /     \  
      5       9 
     /         \
    6           7
输出: 8
解释: 最大值出现在树的第 4 层，宽度为 8 (6,null,null,null,null,null,null,7)。
注意: 答案在32位有符号整数的表示范围

#### 解法1 层次遍历

用队列保存当前行的节点：

两个队列分别保存当前行和下一行的节点，通过1-flag更换当前行

用链表保存当前行节点在满二叉树的位置，用于计算当前行的宽度

```java
import java.util.LinkedList;
import java.util.Queue;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 * int val;
 * TreeNode left;
 * TreeNode right;
 * TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public int widthOfBinaryTree(TreeNode root) {
        if (root == null) {
            return 0;
        }
        // 保存当前行的节点队列，flag；下一行的节点队列，1-flag
        Queue<TreeNode>[] queue = new LinkedList[2];
        queue[0] = new LinkedList<>();
        queue[1] = new LinkedList<>();
        // 保存当前行的节点在满二叉树中的位置，用于计算当前行宽度
        LinkedList<Integer> list = new LinkedList<>();
        int flag = 0;
        queue[flag].offer(root);
        list.add(1);
        int max = 1;
        TreeNode node;
        int nowIndex;
        // 遍历当前行
        while (!queue[flag].isEmpty()) {
            // 当前节点
            node = queue[flag].poll();
            // 当前节点在满二叉树中的位置
            nowIndex = list.removeFirst();
            if (node.left != null) {
                queue[1 - flag].offer(node.left);
                list.add(nowIndex * 2);
            }
            if (node.right != null) {
                queue[1 - flag].offer(node.right);
                list.add(nowIndex * 2 + 1);
            }
            // 当前行已遍历完
            if (queue[flag].isEmpty()) {
                if (!list.isEmpty()) {
                    // 更新最大宽度，与当前行的宽度比较
                    max = Math.max(max, list.getLast() - list.getFirst() + 1);
                }
                // 切换到下一行
                flag = 1 - flag;
            }
        }
        return max;
    }
}
```

------

#### 解法2 DFS遍历

遍历每一行的时候，只需要记住当前行最左节点在满二叉树中的位置即可，因为：

当前行宽度=当前节点位置-当前行最左节点位置+1

因此，用一个链表保存每一行最左节点在满二叉树中的位置

```java
import java.util.ArrayList;
import java.util.List;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 * int val;
 * TreeNode left;
 * TreeNode right;
 * TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public int widthOfBinaryTree(TreeNode root) {
        if (root == null) {
            return 0;
        }
        // 保存每一层最左节点其在满二叉树中的位置
        List<Integer> list = new ArrayList<>();
        dfs(root, 1, 1, list);
        return max;
    }

    public int max = 0;

    /**
     * @param root  当前节点
     * @param level 当前行数
     * @param index 当前节点在满二叉树中的位置
     * @param list  保存每一层最左节点其在满二叉树中的位置
     */
    public void dfs(TreeNode root, int level, int index, List<Integer> list) {
        if (root == null) {
            return;
        }
        // 行数大于list长度，说明到了新的一行
        if (level > list.size()) {
            list.add(index);
        }
        // 当前行宽度=当前节点位置-当前行最左节点位置+1
        max = Math.max(max, index - list.get(level - 1) + 1);
        dfs(root.left, level + 1, index * 2, list);
        dfs(root.right, level + 1, index * 2 + 1, list);
    }
}
```

------

### 十八、最小高度数

[OJ](https://leetcode-cn.com/problems/minimum-height-trees)

对于一个具有树特征的无向图，我们可选择任何一个节点作为根。图因此可以成为树，在所有可能的树中，具有最小高度的树被称为最小高度树。给出这样的一个图，写出一个函数找到所有的最小高度树并返回他们的根节点。

格式

该图包含 n 个节点，标记为 0 到 n - 1。给定数字 n 和一个无向边 edges 列表（每一个边都是一对标签）。

你可以假设没有重复的边会出现在 edges 中。由于所有的边都是无向边， [0, 1]和 [1, 0] 是相同的，因此不会同时出现在 edges 里。

示例 1:

输入: n = 4, edges = [[1, 0], [1, 2], [1, 3]]

        0
        |
        1
       / \
      2   3 

输出: [1]
示例 2:

输入: n = 6, edges = [[0, 3], [1, 3], [2, 3], [4, 3], [5, 4]]

     0  1  2
      \ | /
        3
        |
        4
        |
        5 

输出: [3, 4]
说明:

 根据树的定义，树是一个无向图，其中任何两个顶点只通过一条路径连接。 换句话说，一个任何没有简单环路的连通图都是一棵树。
树的高度是指根节点和叶子节点之间最长向下路径上边的数量。

#### 解法1 遍历求所有树的高度(超时)

遍历图所有的节点，分别求以当前节点为根节点的树的高度

超时

且注意题中已指明，该图包含 `n` 个节点，标记为 `0` 到 `n - 1`，故不需要使用HashMap

PS：

其中，求一个树的高度有下列三种方法：

- 广度优先搜索，用队列实现层次遍历
- 深度优先搜索，递归求各子树的高度最大值
- 深度优先搜索，用栈实现前序/后序遍历，最大的栈长度即为树的高度

```java
import java.util.*;

class Solution {
    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        List<Integer> result = new ArrayList<>();
        if (n == 1) {
            List<Integer> r = new ArrayList<>();
            r.add(0);
            return r;
        } else if (n <= 0 || edges == null || edges.length == 0) {
            return result;
        }
        // 存储图的节点的邻接表
        // list 第一个存储以当前节点为根节点的树的高度
        // list 第二个存储计算以当前节点为根节点的树的高度时候是否遍历过此节点
        HashMap<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < edges.length; i++) {
            for (int j = 0; j < 2; j++) {
                int a, b;
                if (j == 0) {
                    a = edges[i][0];
                    b = edges[i][1];
                } else {
                    a = edges[i][1];
                    b = edges[i][0];
                }
                if (map.containsKey(a)) {
                    map.get(a).add(b);
                } else {
                    List<Integer> list = new LinkedList<>();
                    list.add(0);
                    list.add(0);
                    list.add(b);
                    map.put(a, list);
                }
            }
        }
        // 广度遍历列表
        Queue<Integer> queue = new LinkedList<>();
        // 当前节点的邻接表
        List<Integer> ne;
        // 分别求以不同key为根节点的树的高度
        for (Integer key : map.keySet()) {
            int depth = 0;
            queue.add(key);
            // 已遍历的节点的标记重置
            for (Integer k : map.keySet()) {
                map.get(k).set(1, 0);
            }
            map.get(key).set(1, 1);
            // 广度优先
            while (!queue.isEmpty()) {
                depth++;
                // 当前层的节点个数
                int size = queue.size();
                // 遍历当前层的节点
                for (int i = 0; i < size; i++) {
                    int now = queue.poll();
                    ne = map.get(now);
                    // 广度队列添加当前节点的邻接节点
                    for (int j = 2; j < ne.size(); j++) {
                        // 当前节点未遍历过
                        if (map.get(ne.get(j)).get(1) == 0) {
                            queue.add(ne.get(j));
                            map.get(ne.get(j)).set(1, 1);
                        }
                    }
                }
            }
            map.get(key).set(0, depth);
        }
        // 最小高度
        int min = Integer.MAX_VALUE;
        for (Integer key : map.keySet()) {
            if (min > map.get(key).get(0)) {
                min = map.get(key).get(0);
            }
        }
        // 最小高度的树的根节点
        for (Integer key : map.keySet()) {
            if (map.get(key).get(0) == min) {
                result.add(key);
            }
        }
        return result;
    }

}
```

------

#### 解法2 去除叶节点

既然要求高度最小的树的根节点，那么逐渐删除当前无向图的叶子节点，最后始终会剩下一个或者两个节点，即为高度最小的树的根节点

```java
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class Solution {
    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        List<Integer> result = new ArrayList<>();
        if (n == 1) {
            result.add(0);
            return result;
        } else if (n <= 0 || edges == null || edges.length == 0) {
            return result;
        }
        // 无向图边关系
        boolean[][] graph = new boolean[n][n];
        // 标记已经遍历
        boolean[] visited = new boolean[n];
        // 当前节点的边数
        int[] nums = new int[n];
        // 初始化无向图
        for (int i = 0; i < edges.length; i++) {
            graph[edges[i][0]][edges[i][1]] = true;
            graph[edges[i][1]][edges[i][0]] = true;
            nums[edges[i][0]]++;
            nums[edges[i][1]]++;
        }
        // 当前的叶子节点队列
        Queue<Integer> leaves = new LinkedList<>();
        // 删除叶子节点
        while (n > 2) {
            // 当前边数为1的节点为叶子节点
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] == 1) {
                    leaves.offer(i);
                }
            }
            // 删除当前叶子节点列表
            while (!leaves.isEmpty()) {
                int leaf = leaves.poll();
                nums[leaf]--;
                n--;
                visited[leaf] = true;
                // 更新与当前叶子节点相连的节点
                for (int i = 0; i < graph[leaf].length; i++) {
                    if (graph[leaf][i]) {
                        graph[i][leaf] = false;
                        graph[leaf][i] = false;
                        nums[i]--;
                    }
                }
            }
        }
        // 最后剩下的一个或两个节点
        for (int i = 0; i < visited.length; i++) {
            if (!visited[i]) {
                result.add(i);
            }
        }
        return result;
    }
}
```

------

### 十九、已知中序和后序求前序遍历

eg，输入中序遍历和后序遍历：

dgbaechf

gbdehfca

输出：

adbgcefh

#### 解法1 递归

与前面第一题，根据前序遍历和中序遍历重建二叉树差不多

PS:

赛码OJ通过率仅有83%，失败测试用例暂时未知！



```java
import java.util.Scanner;

/**
 * @author humingk
 */
public class Main {

    public String recursion(String in, String back, int inStart, int inEnd, int backStart, int backEnd) {
        if (inStart > inEnd) {
            return "";
        }
        if (inStart == inEnd) {
            return String.valueOf(in.charAt(inStart));
        }
        char root = back.charAt(backEnd);
        int inMiddle = inStart;
        while (in.charAt(inMiddle) != root) {
            inMiddle++;
        }
        return root + recursion(in, back, inStart, (inStart + 1 == inMiddle) ? inStart : inMiddle - inStart - 1, backStart, backStart + inMiddle - inStart - 1) + recursion(in, back, inMiddle + 1, inEnd, backStart + inMiddle - inStart, backEnd - 1);
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String in = scanner.nextLine().trim();
        String back = scanner.nextLine().trim();
        String s = new Main().recursion(in, back, 0, in.length() - 1, 0, back.length() - 1);
        System.out.println(s);
    }
}

```

------

### 二十、



#### 解法1

```java

```

------

#### 解法2

```java

```

------

#### 解法3

```java

```

------

### 二十一、





#### 解法1

```java

```

------

#### 解法2

```java

```

------

#### 解法3

```java

```

------

