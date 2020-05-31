class Solution {
    public String longestPalindrome(String s) {
        int l = s.length();
        if (l < 2) {
            return s;
        }
        int start = 0, end = -1;
        // dp[i][j] 表示 s的第i-j子字符串 是否为回文串
        boolean[][] dp = new boolean[l][l];
        for (int i = l - 1; i >= 0; i--) {
            for (int j = i; j < l; j++) {
                // 一个字符 || 两个字符相等 || 三个及以上字符 dp[i][j]=dp[i+1][j-1] && s[i]==s[j]
                if (i == j || ((dp[i + 1][j - 1] || i + 1 == j) && s.charAt(i) == s.charAt(j))) {
                    dp[i][j] = true;
                    if (j - i + 1 > end - start + 1) {
                        start = i;
                        end = j;
                    }
                }
            }
        }
        return s.substring(start, end + 1);
    }
}