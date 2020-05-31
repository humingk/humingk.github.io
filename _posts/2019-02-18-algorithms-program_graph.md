---
layout: post
title : 典型编程题笔记(6)-图(Java)
categories : algorithms
description : 
keywords :

---



- 典型的关于图的编程题
- 若未标明，默认的测试平台为：[leetcode](https://leetcode-cn.com/tag/graph/)

------

深度优先搜索，广度优先搜索



### 一、克隆图

OJ：

[https://leetcode-cn.com/problems/clone-graph/](https://leetcode-cn.com/problems/clone-graph/)

给定无向连通图中一个节点的引用，返回该图的深拷贝克隆）。图中的每个节点都包含它的值 `val`（`Int`） 和其邻居的列表（`list[Node]`）。

输入：
{"$id":"1","neighbors":[{"$id":"2","neighbors":[{"$ref":"1"},{"$id":"3","neighbors":[{"$ref":"2"},{"$id":"4","neighbors":[{"$ref":"3"},{"$ref":"1"}],"val":4}],"val":3}],"val":2},{"$ref":"4"}],"val":1}

解释：
节点 1 的值是 1，它有两个邻居：节点 2 和 4 。
节点 2 的值是 2，它有两个邻居：节点 1 和 3 。
节点 3 的值是 3，它有两个邻居：节点 2 和 4 。
节点 4 的值是 4，它有两个邻居：节点 1 和 3 。

提示：

节点数介于 1 到 100 之间。
无向图是一个简单图，这意味着图中没有重复的边，也没有自环。
由于图是无向的，如果节点 p 是节点 q 的邻居，那么节点 q 也必须是节点 p 的邻居。
必须将给定节点的拷贝作为对克隆图的引用返回。



#### 解法1 广度优先搜索(BFS)

用队列（先进先出）保存广度搜索列表，遍历队列

```java
/*
// Definition for a Node.
class Node {
    public int val;
    public List<Node> neighbors;

    public Node() {}

    public Node(int _val,List<Node> _neighbors) {
        val = _val;
        neighbors = _neighbors;
    }
};
*/
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
class Solution {
    public Node cloneGraph(Node node) {
        if(node==null){
            return null;
        }
        // 新的克隆图返回节点
        Node clone=new Node(node.val,new ArrayList<>());
        // 存放广度遍历节点
        Queue<Node> queue=new LinkedList<>();
        queue.offer(node);
        // 存放旧节点列表对应的克隆值
        Map<Node,Node> copy =new HashMap<>();
        copy.put(node,clone);
        while(!queue.isEmpty()){
            Node temp=queue.poll();
            // 遍历当前节点的邻居节点列表
            for(Node nNode:temp.neighbors){
                // 此邻居节点未复制
                if(!copy.containsKey(nNode)){
                    // 复制当前节点
                    copy.put(nNode,new Node(nNode.val,new ArrayList<>()));
                    // 添加广度遍历列表
                    queue.offer(nNode);
                }
                // 复制当前节点的邻居节点列表
                copy.get(temp).neighbors.add(copy.get(nNode));
            }
        }
        return clone;
    }
}
```

------

#### 解法2 深度优先搜索(DFS)

递归访问当点节点的所有邻居节点

```java
/*
// Definition for a Node.
class Node {
    public int val;
    public List<Node> neighbors;

    public Node() {}

    public Node(int _val,List<Node> _neighbors) {
        val = _val;
        neighbors = _neighbors;
    }
};
*/
import java.util.HashMap;
class Solution {
    public Node cloneGraph(Node node) {
        if(node==null){
            return null;
        }
        // 存放节点对应的克隆值
        Map<Node,Node> copy=new HashMap<>();
        return dfs(node,copy);
    }
    public Node dfs(Node node,Map<Node,Node> copy){
        // 已遍历当前节点，返回当前节点的复制节点
        if(copy.containsKey(node)){
            return copy.get(node);
        }
        // 复制当前节点
        Node clone = new Node(node.val,new ArrayList<>());
        copy.put(node,clone);
        // 遍历当前节点的邻居列表
        for(Node nNode:node.neighbors){
            // 递归调用，复制当前节点的邻居节点列表
            clone.neighbors.add(dfs(nNode,copy));
        }
        return clone;
    }
}
```

------



### 二、课程表

OJ：

[https://leetcode-cn.com/problems/course-schedule/](https://leetcode-cn.com/problems/course-schedule/)

现在你总共有 n 门课需要选，记为 0 到 n-1。

在选修某些课程之前需要一些先修课程。 例如，想要学习课程 0 ，你需要先完成课程 1 ，我们用一个匹配来表示他们: [0,1]

给定课程总量以及它们的先决条件，判断是否可能完成所有课程的学习？

示例 1:

输入: 2, [[1,0]] 
输出: true
解释: 总共有 2 门课程。学习课程 1 之前，你需要完成课程 0。所以这是可能的。
示例 2:

输入: 2, [[1,0],[0,1]]
输出: false
解释: 总共有 2 门课程。学习课程 1 之前，你需要先完成课程 0；并且学习课程 0 之前，你还应先完成课程 1。这是不可能的。
说明:

输入的先决条件是由边缘列表表示的图形，而不是邻接矩阵。详情请参见图的表示法。
你可以假定输入的先决条件中没有重复的边。
提示:

这个问题相当于查找一个循环是否存在于有向图中。如果存在循环，则不存在拓扑排序，因此不可能选取所有课程进行学习。
通过 DFS 进行拓扑排序 - 一个关于Coursera的精彩视频教程（21分钟），介绍拓扑排序的基本概念。
拓扑排序也可以通过 BFS 完成。



#### 解法1 深度优先搜索(DFS) O(N+M)+O(N) N为节点数，M为边数

将课程关系转化为有向图，如果有向图出现了循环，则不存在拓扑排序，因此不可能选取所有课程进行学习

DFS进行拓扑排序：

对每一个节点都进行DFS，递归进行每一次DFS的过程中，如果访问到当前DFS已访问的节点，则说明出现了循环;如果访问到作为起始节点进行其他DFS的节点，则无须再访问

为了区分当前节点是当前DFS正在访问的节点还是作为起始节点进行其他DFS的已访问节点，用一个一维数组标记

        /**
         * 标记的当前节点状态
         * 0 未被访问
         * 1 已被当前起始节点的DFS访问（正在DFS访问中），若访问到本节点则出现了循环，返回false
         * 2 已被其他节点当作起始节点进行了DFS访问（已访问），访问到此节点无须再访问，返回true
         */


```java
import java.util.HashSet;

class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        if (numCourses <= 0) {
            return false;
        } else if (prerequisites.length == 0) {
            return true;
        }
        /**
         * 标记的当前节点状态
         * 0 未被访问
         * 1 已被当前起始节点的DFS访问（正在DFS访问中），若访问到本节点则出现了循环，返回false
         * 2 已被其他节点当作起始节点进行了DFS访问（已访问），访问到此节点无须再访问，返回true
         */
        int[] visited = new int[numCourses];
        // 邻接表存储图
        HashSet[] graph = new HashSet[numCourses];
        for (int i = 0; i < graph.length; i++) {
            graph[i] = new HashSet<>();
        }
        for(int i=0;i<prerequisites.length;i++){
            graph[prerequisites[i][0]].add(prerequisites[i][1]);
        }
        // 递归，深度优先搜索
        for(int i=0;i<graph.length;i++){
            // 出现循环
            if(!dfs(i,visited,graph)){
                return false;
            }
        }
        // 未出现循环
        return true;
    }

    /**
     * 有循环返回false
     *
     * @param now
     * @param visited
     * @param graph
     * @return
     */
    public boolean dfs(int now, int[] visited, HashSet<Integer>[] graph) {
        // 当前节点正在DFS中，出现循环
        if (visited[now] == 1) {
            return false;
        }
        // 当前节点已作为起始节点访问，不需要再访问
        else if (visited[now] == 2) {
            return true;
        }
        // 标记当前节点正在访问中
        visited[now] = 1;
        // 遍历当前节点的邻接表
        for (Integer neighbor : graph[now]) {
            // 出现循环
            if (!dfs(neighbor, visited, graph)) {
                return false;
            }
        }
        // 标记当前节点已访问
        visited[now] = 2;
        // 未发现循环
        return true;
    }
}
```

------

#### 解法2 拓扑排序 O(N+M)+O(N) N为节点数，M为边数

**拓扑排序**：

即有向无环图的所有顶点的线性队列，此队列满足所有顶点的先后顺序关系，可用于：

- 按先后顺序排序所有节点
- 确定有向图是否有循环

在这里：

这里不需要拓扑排序的顺序，只需要计数拓扑排序的个数count，如果能够按照拓扑排序遍历完所有节点，即没有循环

入度数组：

为了确认某个节点有没有前驱节点，用一个入度数组inDegree来保存所有节点的前驱节点个数

进行拓扑排序：

先将起始节点（没有前驱节点的节点）加入队列，遍历队列并计数，遍历队列的顺序就是拓扑排序的顺序

遍历到队列中的某个节点，需要将这个节点的所有邻接节点的入度减1（相当于删除这个节点），并且将所有邻接节点中没有前驱的节点加入队列中

如果能遍历完所有节点，并且呈拓扑排序，则说明没有循环

PS：

这里每一步都要先选取某个节点的邻接节点中最先没有前驱的节点到队列中（入度最先减为0的节点），其实就是**贪心算法**，满足“某一个节点指向的节点都在这个节点的后面”

```java
import java.util.Queue;
import java.util.LinkedList;

class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        if (numCourses <= 0) {
            return false;
        } else if (prerequisites.length == 0) {
            return true;
        }
        // 入度数组，即指向此节点的节点数
        int[] inDegree = new int[numCourses];
        for(int[] num:prerequisites){
            inDegree[num[0]]++;
        }
        // 存储待遍历队列
        Queue<Integer> queue=new LinkedList<>();
        // 先将所有起始节点加入队列（即入度为0的节点）
        for(int i=0;i<inDegree.length;i++){
            if(inDegree[i]==0){
                queue.offer(i);
            }
        }
        // 存储已遍历节点数，即拓扑排序个数
        int count=0;
        // 遍历队列
        while(!queue.isEmpty()){
            int now=queue.poll();
            count++;
            // 遍历此节点指向的节点列表
            for(int[] num:prerequisites){
                if(num[1]==now){
                    inDegree[num[0]]--;
                    // 没有节点指向当前邻接节点，即当前邻接节点的入度为0
                    if(inDegree[num[0]]==0){
                        queue.offer(num[0]);
                    }
                }
            }
        }
        return count==numCourses;
    }
}
```

------





### 四、单词接龙

OJ：

[https://leetcode-cn.com/problems/word-ladder/](https://leetcode-cn.com/problems/word-ladder/)

给定两个单词（beginWord 和 endWord）和一个字典，找到从 beginWord 到 endWord 的最短转换序列的长度。转换需遵循如下规则：

每次转换只能改变一个字母。
转换过程中的中间单词必须是字典中的单词。
说明:

如果不存在这样的转换序列，返回 0。
所有单词具有相同的长度。
所有单词只由小写字母组成。
字典中不存在重复的单词。
你可以假设 beginWord 和 endWord 是非空的，且二者不相同。
示例 1:

输入:
beginWord = "hit",
endWord = "cog",
wordList = ["hot","dot","dog","lot","log","cog"]

输出: 5

解释: 一个最短转换序列是 "hit" -> "hot" -> "dot" -> "dog" -> "cog",
     返回它的长度 5。
示例 2:

输入:
beginWord = "hit"
endWord = "cog"
wordList = ["hot","dot","dog","lot","log"]

输出: 0

解释: endWord "cog" 不在字典中，所以无法进行转换。



#### 解法1 广度优先搜索(BFS) O(M\*N)+ O(M\*N) M为单词个数，N为单词长度

广度优先搜索，每次变一个字母，最先到达目标单词即为最短路径

用队列保存BFS队列，用HashMap标记单词是否已访问

仅变一个字符：

对于hit变成hot，如果每一次都比较一下是否只差一个字符，计算量太大

考虑hit和hot都满足通配符h\*t，将所有单词的所有通配符作为key，单词作为value，用HashMap初始化存储

遍历到hit的时候，遍历hit的所有通配符 \*it,h\*t,hi\*所对应的所有单词，如果刚好为目标单词，则结束；如果未访问过此单词，则标记后加入BFS列表



```java
import javafx.util.Pair;

import java.util.*;

class Solution {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        // 存放通配符对应的单词列表，eg：h*t对应hit,hot
        Map<String, ArrayList<String>> hashmap = new HashMap<>();
        // 初始化hashmap
        for (String word : wordList) {
            for (int i = 0; i < beginWord.length(); i++) {
                // 通配符
                String key = word.substring(0, i) + "*" + word.substring(i + 1, beginWord.length());
                // 单词列表
                ArrayList<String> value = hashmap.getOrDefault(key, new ArrayList<>());
                // 添加当前单词
                value.add(word);
                hashmap.put(key, value);
            }
        }
        // BFS队列,单词+步数
        Queue<Pair<String, Integer>> queue = new LinkedList<>();
        queue.offer(new Pair(beginWord, 1));
        // 标记单词访问
        Map<String, Boolean> visited = new HashMap<>();
        // BFS遍历
        while (!queue.isEmpty()) {
            Pair<String, Integer> pair = queue.poll();
            String word = pair.getKey();
            int step = pair.getValue();
            // 遍历队列中每一个单词的n种通配符
            for (int i = 0; i < beginWord.length(); i++) {
                String key = word.substring(0, i) + "*" + word.substring(i + 1, beginWord.length());
                // 遍历当前通配符对应的单词列表
                for (String value : hashmap.getOrDefault(key, new ArrayList<String>())) {
                    // 到达目标单词
                    if (value.equals(endWord)) {
                        return step + 1;
                    }
                    // 未访问过此单词
                    if (!visited.containsKey(value)) {
                        // 标记访问此单词
                        visited.put(value, true);
                        // 添加到BFS队列
                        queue.offer(new Pair(value, step + 1));
                    }
                }
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        int t = new Solution().ladderLength("hit", "cog", Arrays.asList("hot", "dot", "lot", "dog", "log", "cog"));
        System.out.println(t);
    }
}
```

------

#### 解法2 双向广度优先搜索(2BFS)

和解法一不同的地方在于，这里分别从开始和末尾向中间进行BFS遍历，当某个单词被两个BFS都访问到，说明相遇，返回二者之和

PS：

特别注意的情况，双向的话需添加条件：若字典中不包含endWord，则直接返回0

由于在判断是否相遇的时候，需要直接返回最短路径，故把step也存储到visited标记中

```java
import javafx.util.Pair;

import java.util.*;

class Solution {
    public int wordLength=0;
    public Map<String, ArrayList<String>> hashmap=null;
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        if(!wordList.contains(endWord)){
            return 0;
        }
        wordLength=beginWord.length();
        // 存放通配符对应的单词列表，eg：h*t对应hit,hot
        hashmap = new HashMap<>();
        // 初始化hashmap
        for (String word : wordList) {
            for (int i = 0; i < wordLength; i++) {
                // 通配符
                String key = word.substring(0, i) + "*" + word.substring(i + 1, wordLength);
                // 单词列表
                ArrayList<String> value = hashmap.getOrDefault(key, new ArrayList<>());
                // 添加当前单词
                value.add(word);
                hashmap.put(key, value);
            }
        }
        // BFS队列,单词+步数
        Queue<Pair<String, Integer>> startQueue = new LinkedList<>();
        Queue<Pair<String, Integer>> endQueue = new LinkedList<>();
        startQueue.offer(new Pair(beginWord, 1));
        endQueue.offer(new Pair(endWord, 1));
        // 标记单词访问,单词+步数
        Map<String, Integer> startVisited = new HashMap<>();
        Map<String, Integer> endVisited = new HashMap<>();
        startVisited.put(beginWord,1);
        endVisited.put(endWord,1);
        // BFS遍历
        while (!startQueue.isEmpty()&& !endQueue.isEmpty( )) {
            // 从前向后
            int result=traverse(startQueue,startVisited,endVisited);
            if(result>-1){
                return result;
            }
            // 从后向前
            result=traverse(endQueue,endVisited,startVisited);
            if(result>-1){
                return result;
            }
        }
        return 0;
    }
    public int traverse(Queue<Pair<String,Integer>> queue,Map<String,Integer> nowVisited,Map<String,Integer> otherVisited){
        Pair<String, Integer> pair = queue.poll();
        String word = pair.getKey();
        int step = pair.getValue();
        // 遍历队列中每一个单词的n种通配符
        for (int i = 0; i < wordLength; i++) {
            String key = word.substring(0, i) + "*" + word.substring(i + 1, wordLength);
            // 遍历当前通配符对应的单词列表
            for (String value : hashmap.getOrDefault(key, new ArrayList<String>())) {
                // 另一BFS已经访问过此单词，前后BFS相遇
                if(otherVisited.containsKey(value)){
                    return step+otherVisited.get(value);
                }
                // 此BFS未访问过此单词
                if (!nowVisited.containsKey(value)) {
                    // 标记访问此单词
                    nowVisited.put(value, step+1);
                    // 添加到此BFS队列
                    queue.offer(new Pair(value, step + 1));
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int t = new Solution().ladderLength("hit", "cog", Arrays.asList("hot", "dot", "lot", "dog", "log", "cog"));
        System.out.println(t);
    }
}
```

------



### 五、岛屿数量

OJ：

[https://leetcode-cn.com/problems/number-of-islands/](https://leetcode-cn.com/problems/number-of-islands/)





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



### 六、Floyd算法

任意两点之间的最短距离

#### 测试用例

```
6
0 1 12 -1 -1 -1
-1 0 9 3 -1 -1
-1 -1 0 -1 5 -1
-1 -1 4 0 13 15
-1 -1 -1 -1 0 4
-1 -1 -1 -1 -1 0
```

#### 解法

```java
import java.util.Scanner;

/**
 * @author humingk
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[][] a = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                a[i][j] = scanner.nextInt();
                if (a[i][j] == -1) {
                    a[i][j] = Integer.MAX_VALUE;
                }
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(a[i][j] + " ");
            }
            System.out.println();
        }

        // Floyd算法
        // a[i][j]通过第k个节点中转
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    // a[i][k] 和 a[k][j]能走通 且 中转距离更短
                    if (a[i][k] != Integer.MAX_VALUE && a[k][j] != Integer.MAX_VALUE && a[i][k] + a[k][j] < a[i][j]) {
                        a[i][j] = a[i][k] + a[k][j];
                    }
                }
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(a[i][j] + " ");
            }
            System.out.println();
        }
    }
}

```

#### 运行结果

```java
6
0 1 12 -1 -1 -1
-1 0 9 3 -1 -1
-1 -1 0 -1 5 -1
-1 -1 4 0 13 15
-1 -1 -1 -1 0 4
-1 -1 -1 -1 -1 0
    
0 1 12 2147483647 2147483647 2147483647 
2147483647 0 9 3 2147483647 2147483647 
2147483647 2147483647 0 2147483647 5 2147483647 
2147483647 2147483647 4 0 13 15 
2147483647 2147483647 2147483647 2147483647 0 4 
2147483647 2147483647 2147483647 2147483647 2147483647 0 
    
0 1 8 4 13 17 
2147483647 0 7 3 12 16 
2147483647 2147483647 0 2147483647 5 9 
2147483647 2147483647 4 0 9 13 
2147483647 2147483647 2147483647 2147483647 0 4 
2147483647 2147483647 2147483647 2147483647 2147483647 0 
```

------



### 七、Dijkstra算法

某一点到任意点的距离

#### 测试用例

```
6
0 1 12 -1 -1 -1
-1 0 9 3 -1 -1
-1 -1 0 -1 5 -1
-1 -1 4 0 13 15
-1 -1 -1 -1 0 4
-1 -1 -1 -1 -1 0
```

#### 解法

```java
import java.util.Scanner;

/**
 * @author humingk
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[][] a = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                a[i][j] = scanner.nextInt();
                if (a[i][j] == -1) {
                    a[i][j] = Integer.MAX_VALUE;
                }
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(a[i][j] + " ");
            }
            System.out.println();
        }

        // Dijkstra算法
        int start = 0;
        // 第start个节点到其他节点的最短距离
        int[] distance = new int[n];
        for (int i = 0; i < n; i++) {
            distance[i] = a[start][i];
        }

        System.out.println();
        for (int i = 0; i < n; i++) {
            System.out.print(distance[i] + " ");
        }

        // 某个节点的最短距离是否已求出 true -> 已求出
        boolean[] flag = new boolean[n];
        flag[start] = true;

        for (int i = 0; i < n - 1; i++) {
            // min为距离start最近的节点 且 min之前未被找到
            int min = start;
            int minValue = Integer.MAX_VALUE;
            for (int j = 0; j < n; j++) {
                if (!flag[j] && distance[j] < minValue) {
                    min = j;
                    minValue = distance[j];
                }
            }
            flag[min] = true;

            for (int j = 0; j < n; j++) {
                // a[min][j]能走通 且 a-j 通过 a-min-j 中转距离更短
                if (a[min][j] != Integer.MAX_VALUE && distance[j] > distance[min] + a[min][j]) {
                    distance[j] = distance[min] + a[min][j];
                }
            }
        }

        System.out.println();
        for (int i = 0; i < n; i++) {
            System.out.print(distance[i] + " ");
        }
    }
}

```

#### 运行结果

```java
6
0 1 12 -1 -1 -1
-1 0 9 3 -1 -1
-1 -1 0 -1 5 -1
-1 -1 4 0 13 15
-1 -1 -1 -1 0 4
-1 -1 -1 -1 -1 0
0 1 12 2147483647 2147483647 2147483647 
2147483647 0 9 3 2147483647 2147483647 
2147483647 2147483647 0 2147483647 5 2147483647 
2147483647 2147483647 4 0 13 15 
2147483647 2147483647 2147483647 2147483647 0 4 
2147483647 2147483647 2147483647 2147483647 2147483647 0 

0 1 12 2147483647 2147483647 2147483647 
0 1 8 4 13 17 
```

------

