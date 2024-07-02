package waigo.zuosheng.advanced.MyCode;

/**
 * author waigo
 * create 2021-05-31 12:41
 */

/**
 * 现有n1+n2种面值的硬币，其中前n1种为普通币，可以取任意枚，后n2种为纪念币，
 * 每种最多只能取一枚，每种硬币有一个面值，问能用多少种方法拼出m的面值?
 */
public class Code02_MoneyWays {
    //背包解不了，那个普通币的可能性太难列了，把握不住
    public static int moneyWays(int[] arbitrary, int[] onlyone, int money) {
        if (money < 0) {
            return 0;
        }
        if ((arbitrary == null || arbitrary.length == 0) && (onlyone == null || onlyone.length == 0)) {
            return money == 0 ? 1 : 0;
        }
        //dpArb[i][j]表示，0~i这些普通币任意使用能够凑出j元有几种方法
        int[][] dpArb = getArbDp(arbitrary,money);
        //dpOnly[i][j]表示，0~i这些普通币随意使用但是每个最多能用一个能够凑出j元有几种方法
        int[][] dpOnly = getOnlyDp(onlyone,money);
        if(dpArb==null){
            return dpOnly[onlyone.length-1][money];
        }
        if(dpOnly==null){
            return dpArb[arbitrary.length-1][money];
        }
        int res = 0;
        for (int arbMoney = 0; arbMoney <= money; arbMoney++) {
            res+=dpArb[arbitrary.length-1][arbMoney]*dpOnly[onlyone.length-1][money-arbMoney];
        }
        return res;
    }

    private static int[][] getOnlyDp(int[] onlyone, int money) {
        if(onlyone==null||onlyone.length==0) return null;
        int rows = onlyone.length;
        int cols = money + 1;//col=0~money
        int[][] dp = new int[rows][cols];
        //第一行
        for (int wantMoney = 1; wantMoney < cols; wantMoney++) {
            dp[0][wantMoney] = wantMoney==onlyone[0]?1:0;
        }
        for (int row = 0; row < rows; row++) {
            dp[row][0] = 1;
        }
        //从上到下，从左到右
        for (int wantMoney = 1; wantMoney < cols; wantMoney++) {
            for (int row = 1; row < rows; row++) {
                dp[row][wantMoney] = dp[row-1][wantMoney];
                dp[row][wantMoney] += wantMoney-onlyone[row]>=0?dp[row-1][wantMoney-onlyone[row]]:0;
            }
        }
        return dp;
    }

    private static int[][] getArbDp(int[] arbitrary, int money) {
        if(arbitrary==null||arbitrary.length==0) return null;
        int rows = arbitrary.length;
        int cols = money + 1;//col=0~money
        int[][] dp = new int[rows][cols];
        //第一行
        for (int wantMoney = 0; wantMoney < cols; wantMoney++) {
            dp[0][wantMoney] = wantMoney%arbitrary[0]==0?1:0;
        }
        for (int row = 1; row < rows; row++) {
            dp[row][0] = 1;
        }
        //从上到下，从左到右
        for (int wantMoney = 1; wantMoney < cols; wantMoney++) {
            for (int row = 1; row < rows; row++) {
                dp[row][wantMoney] = dp[row-1][wantMoney];
                dp[row][wantMoney] += wantMoney-arbitrary[row]>=0?dp[row][wantMoney-arbitrary[row]]:0;
            }
        }
        return dp;
    }


    public static void main(String[] args) {
        int[] arbitrary = {1,2,5,7,9,8,6,9,100};
        int[] onlyone = {3,3,6,5,9,1,10,53,3,2};
        int money = 3;
        System.out.println(moneyWays(arbitrary, onlyone, money));
    }
}
