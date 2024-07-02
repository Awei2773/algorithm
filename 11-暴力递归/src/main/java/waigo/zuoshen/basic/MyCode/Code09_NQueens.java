package waigo.zuoshen.basic.MyCode;

/**
 * author waigo
 * create 2021-03-03 14:29
 */

/**
 * N皇后问题是指在N*N的棋盘上要摆N个皇后，
 * 要求任何两个皇后不同行、不同列， 也不在同一条斜线上
 * 给定一个整数n，返回n皇后的摆法有多少种。  n=1，返回1
 * n=2或3，2皇后和3皇后问题无论怎么摆都不行，返回0
 * n=8，返回92
 */
public class Code09_NQueens {
    public static int getCount(int N) {
        if (N < 3) return 0;
        int[] record = new int[N];
        return process(N, record, 0);
    }

    /**
     * @param N      几皇后问题
     * @param record 已经摆放的皇后位置，下标表示行，值表示列 record[0] = 3表示第0行的皇后放在下标3位置
     * @param curRow 现在摆放那一层
     * @return 返回curLay到N-1层总有多少种皇后的摆法
     */
    private static int process(int N, int[] record, int curRow) {
        if (curRow == N) return 1;//摆放完了，算一种
        int res = 0;
        for (int col = 0; col < N; col++) {
            if (isValid(record, curRow, col)) {
                record[curRow] = col;
                res += process(N, record, curRow + 1);
            }
        }
        return res;
    }

//    private static int[] resetRecord(int[] record, int col) {
//        int[] ary = new int[record.length+1];
//        System.arraycopy(record,0,ary,0,record.length);
//        ary[ary.length-1] = col;
//        return ary;
//    }

    private static boolean isValid(int[] record, int curRow, int col) {
        for (int i = 0; i < curRow; i++) {
            //i是层，record[i]是那一层的皇后摆放的列
            int iQueenCol = record[i];
            //判断左右是否共斜线用这个等腰三角形真的方便，只要行差距和列差距绝对值相同，那么就表示是在斜线上
            if (col == iQueenCol || Math.abs(curRow - i) == Math.abs(iQueenCol - col)) return false;
        }
        return true;
    }

    //使用位运算进行优化，将0|1用来代表一个位置是否可以放皇后，4皇后问题就是最后列限制为1111的时候就表示所有列都放好了，那么就说明这是一个摆法
    public static int getCount_bit(int N) {
        if (N < 4 || N > 32) return 0;
        int limit = N == 32 ? -1 : (1 << N) - 1;
        return process1(limit, 0, 0, 0);
    }

    private static int process1(int limit, int colLimit, int leftLimit, int rightLimit) {
        if (limit == colLimit) return 1;//base case
        //找到所有可以放的位置，将原本0可以放转成1可以放，因为比较习惯找到最右侧的1
        int pos = limit & (~(colLimit | leftLimit | rightLimit));
        int res = 0;
        int mostRightOne;
        while (pos != 0) {
            mostRightOne = pos & ((~pos) + 1);
            pos -= mostRightOne;
            //因为是整合所有限制点，所以使用位或运算，有1为1
            res+=process1(limit,colLimit|mostRightOne,(leftLimit|mostRightOne)<<1,(rightLimit|mostRightOne)>>>1);
        }
        return res;
    }

    public static void main(String[] args) {
//        System.out.println(getCount_bit(15));
        int i = -1;
        System.out.println(i >>> 1);
    }

}
