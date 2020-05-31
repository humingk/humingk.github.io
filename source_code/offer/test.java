/**
 * @author humingk
 */
public class test {
    public static void main(String[] args) {
        // 背包的最大承受重量->容量
        int total = 100;
        // 共有N件物品
        int N = 6;
        // 第i件物品的重量
        int[] weight = new int[]{0, 10, 20, 30, 40, 10, 50};
        // 第i件物品的价值
        int[] worth = new int[]{0, 200, 400, 500, 100, 300, 600};


        /**
         * 前i(i<=N)件物品放入一个容量为j(j<=total)的包中，可以获得的最大价值
         *
         * 此时01背包分两种情况：
         *  1. 如果第i件物品不放入背包，则表示前i-1件物品放入容量为 total 背包，即：
         *      dp[i][j]=dp[i-1][j]
         *  2. 如果第i件物品放入背包，则表示前i-1件物品放入容量为 total-weight[i] 背包，再放入第i件物品,即：
         *      dp[i][j]=dp[i-1][total-weight[i]]+worth[i]
         *
         * 即：
         *  dp[i][j] = max{dp[i-1][j],dp[i-1][j-weight[i]]+worth[i]}
         *
         */
        int[][] dp = new int[N + 1][total + 1];
        // 初始化
        dp[0][0] = 0;
        for (int j = 1; j < total + 1; j++) {
            dp[0][j] = Integer.MIN_VALUE;
        }

        // 二维数组 01背包
        for (int i = 1; i < N + 1; i++) {
            for (int j = 1; j < total + 1; j++) {
                dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - weight[i]] + worth[i]);
            }
        }





    }
}
