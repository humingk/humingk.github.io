package array;

/**
 * 二维数组 字符串路径
 *
 * @author humingk
 */
public class StringPathInMatrix<Value> {
    // ------------------------------------------------------------------------------------------
    // start
    // 1.测试用例方法

    public void test() {
        System.out.println("1. 多行多列存在路径：");
        solution(new char[]{'a', 'b', 't', 'g', 'c', 'f', 'c', 's', 'j', 'd', 'e', 'h'}, 3, 4, new char[]{'b', 'f', 'c', 'e'});
        System.out.println();
        System.out.println("2. 多行多列不存在路径：");
        solution(new char[]{'a', 'b', 't', 'g', 'c', 'f', 'c', 's', 'j', 'd', 'e', 'h'}, 3, 4, new char[]{'b', 'f', 'c', 'd'});
        System.out.println();
        System.out.println("3. 只有一行存在路径：");
        solution(new char[]{'a', 'b', 't', 'g', 'c', 'f', 'c', 's', 'j', 'd', 'e', 'h'}, 1, 12, new char[]{'g', 'c', 'f', 'c'});
        System.out.println();
        System.out.println("4. 只有一行不存在路径：");
        solution(new char[]{'a', 'b', 't', 'g', 'c', 'f', 'c', 's', 'j', 'd', 'e', 'h'}, 1, 12, new char[]{'g', 'c', 'f', 'j'});
        System.out.println();
        System.out.println("5. 矩阵字符串都相同:");
        solution(new char[]{'a', 'a', 'a', 'a'}, 2, 2, new char[]{'a', 'a', 'a'});
        System.out.println();
        System.out.println("6. 输入为空:");
        solution(new char[]{}, 0, 0, new char[]{});
        System.out.println();
        System.out.println("7. 错误测试例子1：(非第一个元素)");
        solution(new char[]{'A','B','C','E','S','F','C','S','A','D','E','E'},3,4,new char[]{'S','E','E'});
        System.out.println();
        System.out.println("8. 错误测试例子2：(下 左 边界出错)");
        solution(new char[]{'A','B','C','E','S','F','C','S','A','D','E','E'},3,4,new char[]{'A','B','C','B'});
        System.out.println("9. 错误例子3：（出错的路径需要重置）");
        solution(new char[]{'A','B','C','E','S','F','C','S','A','D','E','E'},3,4,new char[]{'E','E','D','A','S','A','B','C','E','S'});
    }

    // 2.算法题方法

    public boolean solution(char[] matrix, int rows, int cols, char[] str) {
        if(matrix==null || matrix.length==0){
            return false;
        }
        if(str==null||str.length==0){
            return true;
        }
        // 标记是否走过,默认false未走过
        boolean[] flags = new boolean[rows * cols];
        // 初始化路径的起始元素
        int startRow = -1, startCol = -1;
        for (int i = 0; i < rows * cols; i++) {
            if (matrix[i] == str[0]) {
                startRow = i / cols;
                startCol = i % cols;
                flags[i] = false;
                // 有一项成功，则成功
                if(recursion(matrix, rows, cols, startRow, startCol, str, 0, "start", flags)){
                    System.out.print("true ");
                    return true;
                }else {
                    // 重置flags
                    flags=new boolean[rows*cols];
                    System.out.print("false ");
                    System.out.println();
                }
            }
        }
        return false;
    }

    /**
     * 递归
     *
     * @param matrix
     * @param rows
     * @param cols
     * @param i 当前位置在矩阵中的行数
     * @param j 当前位置在矩阵中的列数
     * @param str
     * @param nowS
     * @param type
     * @param flags
     * @return
     */
    private boolean recursion(char[] matrix, int rows, int cols, int i, int j, char[] str, int nowS, String type, boolean[] flags) {
        int index=i*cols+j;
        // 路径经过标记true
        flags[index] = true;
        // 走完路径
        if (nowS >= str.length - 1) {
            System.out.print(matrix[index]+" -> ");
            return true;
        }
        // 路径回溯到第一个，路径不通
        else if (nowS < 0) {
            return false;
        }
        // 还没有走完路径
        else {
            System.out.print(str[nowS] + " -> ");
            // 左边
            if (j > 0 && cols != 1 && !flags[index - 1] && matrix[index - 1] == str[nowS + 1]) {
                return recursion(matrix, rows, cols, i, j - 1, str, nowS + 1, "left", flags);
            }
            // 右边
            else if (j < cols - 1 && cols != 1 && !flags[index + 1] && matrix[index + 1] == str[nowS + 1]) {
                return recursion(matrix, rows, cols, i, j + 1, str, nowS + 1, "right", flags);
            }
            // 上边
            else if (i > 0 && rows != 1 && !flags[index-cols] && matrix[index-cols] == str[nowS + 1]) {
                return recursion(matrix, rows, cols, i - 1, j, str, nowS + 1, "up", flags);
            }
            // 下边
            else if (i < rows - 1 && rows != 1 && !flags[index+cols] && matrix[index+cols] == str[nowS + 1]) {
                return recursion(matrix, rows, cols, i + 1, j, str, nowS + 1, "down", flags);
            }
            // 回溯
            else {
                System.out.print(str[nowS] + " -> ");
                if ("left".equals(type)) {
                    // 恢复上一个的flags为true
                    flags[index + 1] = false;
                    return recursion(matrix, rows, cols, i, j + 1, str, nowS - 1, "right", flags);
                } else if ("right".equals(type)) {
                    flags[index - 1] = false;
                    return recursion(matrix, rows, cols, i, j - 1, str, nowS - 1, "left", flags);
                } else if ("up".equals(type)) {
                    flags[index+cols] = false;
                    return recursion(matrix, rows, cols, i + 1, j, str, nowS - 1, "down", flags);
                } else if ("down".equals(type)) {
                    flags[index-cols] = false;
                    return recursion(matrix, rows, cols, i - 1, j, str, nowS - 1, "up", flags);
                }
            }
        }
        return false;
    }
    // end
    // ------------------------------------------------------------------------------------------

    public static void main(String[] args) {
        StringPathInMatrix stringBase = new StringPathInMatrix();
        stringBase.test();
    }
}