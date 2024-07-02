package waigo.zuoshen.basic.MyCode;

/**
 * author waigo
 * create 2021-02-27 16:29
 */

/**
 * 给定一个整型数组arr，代表数值不同的纸牌排成一条线，
 * 玩家A和玩家B依次拿走每张纸牌，
 * 规定玩家A先拿，玩家B后拿，
 * 但是每个玩家每次只能拿走最左或最右的纸牌，
 * 玩家A和玩家B都绝顶聪明。请返回最后获胜者的分数。
 */
public class Code08_CardsInLine {
    public static int getWinnerScore(int[] arr) {
        if (arr == null) return -1;
        return Math.max(first(arr, 0, arr.length - 1), back(arr, 0, arr.length - 1));
    }

    //先手最多分，肯定有多高要多高
    public static int first(int[] arr, int L, int R) {
        if (L == R) return arr[L];
        return Math.max(back(arr, L + 1, R) + arr[L], back(arr, L, R - 1) + arr[R]);
    }

    //后手最少分，人家不会给你高分的
    public static int back(int[] arr, int L, int R) {
        if (L == R) return 0;
        return Math.min(first(arr, L + 1, R), first(arr, L, R - 1));
    }

    /**
     * --------------------------------------动态规划dp2.0--------------------------------------------------
     */
    public static int getWinnerScore_db(int[] arr) {
        if (arr == null) return -1;
        //这里有两张表f表和b表，都是二维表有两个取值，而且都是0~N-1
        int N = arr.length;
        int[][] f = new int[N][N];
        int[][] b = new int[N][N];
        //f初始化
        for (int l = 0; l < N; l++)
            f[l][l] = arr[l];
        for (int i = 1;i < N;i++) {
            int L = 0;//斜线进行填表
            int R = i;
            while (L<N-i) {
                f[L][R] = Math.max(b[L + 1][R] + arr[L], b[L][R - 1] + arr[R]);
                b[L][R] = Math.min(f[L + 1][R], f[L][R - 1]);
                R++;
                L++;
            }
        }
        return Math.max(f[0][arr.length - 1], b[0][arr.length - 1]);
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 18, 5,18,19,100,23,35,34,12};
        System.out.println(getWinnerScore(arr));
        System.out.println(getWinnerScore_db(arr));
    }
}
