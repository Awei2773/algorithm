package waigo.zuosheng.advanced.MyCode;

/**
 * author waigo
 * create 2021-06-06 9:40
 */
public class Code05_DungeonGame {
    /**
     * 这样的问题不是一次两次了，很多时候从前到后觉得很恶心，搞不定需求的时候，记得反过来
     * 从后到前，
     */
    public static class Info implements Comparable<Info> {
        int initialBlood;
        int curBlood;

        public Info() {
        }

        public Info(int initialBlood, int curBlood) {
            this.initialBlood = initialBlood;
            this.curBlood = curBlood;
        }

        @Override
        public int compareTo(Info o) {
            int flag = this.initialBlood - o.initialBlood;
            return flag == 0 ? o.curBlood - this.curBlood : flag;
        }
    }

    public static int calculateMinimumHP(int[][] dungeon) {
        if (dungeon == null || dungeon.length == 0 || dungeon[0].length == 0) return 0;
        int rows = dungeon.length;
        int cols = dungeon[0].length;
        Info[][] dp = new Info[rows][cols];
        dp[0][0] = dungeon[0][0] <= 0 ? new Info(Math.abs(dungeon[0][0]) + 1, 1) : new Info(1, dungeon[0][0] + 1);
        for (int col = 1; col < cols; col++) {
            dp[0][col] = new Info();
            dp[0][col].initialBlood = dp[0][col - 1].initialBlood;
            dp[0][col].curBlood = dp[0][col - 1].curBlood + dungeon[0][col];
            if (dp[0][col].curBlood <= 0) {
                dp[0][col].initialBlood += Math.abs(dp[0][col].curBlood) + 1;
                dp[0][col].curBlood = 1;
            }
        }
        for (int row = 1; row < rows; row++) {
            dp[row][0] = new Info();
            dp[row][0].initialBlood = dp[row - 1][0].initialBlood;
            dp[row][0].curBlood = dp[row - 1][0].curBlood + dungeon[row][0];
            if (dp[row][0].curBlood <= 0) {
                dp[row][0].initialBlood += Math.abs(dp[row][0].curBlood) + 1;
                dp[row][0].curBlood = 1;
            }
        }
        for (int row = 1; row < rows; row++) {
            for (int col = 1; col < cols; col++) {
                //看上面那个
                Info up = new Info();
                up.initialBlood = dp[row - 1][col].initialBlood;
                up.curBlood = dp[row - 1][col].curBlood + dungeon[row][col];
                if (up.curBlood <= 0) {
                    up.initialBlood += Math.abs(up.curBlood) + 1;
                    up.curBlood = 1;
                }
                //看左边那个
                Info left = new Info();
                left.initialBlood = dp[row][col - 1].initialBlood;
                left.curBlood = dp[row][col - 1].curBlood + dungeon[row][col];
                if (left.curBlood <= 0) {
                    left.initialBlood += Math.abs(left.curBlood) + 1;
                    left.curBlood = 1;
                }
                dp[row][col] = up.compareTo(left) < 0 ? up : left;
            }
        }
        return dp[rows - 1][cols - 1].initialBlood;
    }

    /**
     * 上面那个是处刑版，我就没见过dp表建对象的，完了含义是压根就把握不住的，从前到后现在来到了
     * (i,j)位置，确保不了说我从我上或者左选最经济的情况就能够推出我当前位置最经济的情况，这就是
     * 失败的贪心，还有，说了多少次，没想清楚别动代码，写半天一测啪过不了，白给了。
     * <p>
     * 但是从后往前就不同了，你(i,j)位置到右下角的最经济走法那肯定就是能够推出上层的抉择
     */
    public static int calculateMinimumHP1(int[][] dungeon) {
        if (dungeon == null || dungeon.length == 0 || dungeon[0].length == 0) return 0;
        int[][] dp = new int[dungeon.length][dungeon[0].length];
        int rows = dungeon.length;
        int cols = dungeon[0].length;
        //dp[i][j]表示当前来到了i,j位置，但是还没登上去，登上去之后走到右下角的最经济方式初始血量多少
        dp[rows - 1][cols - 1] = dungeon[rows - 1][cols - 1] <= 0 ? Math.abs(dungeon[rows - 1][cols - 1]) + 1 : 1;
        for (int row = rows - 2; row >= 0; row--) {
            //只能往下走，所以下面的期望多少现在就得多少了，不用抉择，不过得加上自己登上去的期望
            //需要注意：dp[row+1][cols-1]能够确保登上下面位置后还能活下来
            //要想往下走，那你登上当前的位置之后还得留足能够往下走的血条
            int curRoom = dungeon[row][cols - 1];
            int nextRoom = dp[row + 1][cols - 1];
            if (curRoom < 0) {
                dp[row][cols - 1] = -curRoom + nextRoom;
            } else if (curRoom+1 >= nextRoom) {
                dp[row][cols - 1] = 1;//跨上了就行
            } else {
                dp[row][cols - 1] = nextRoom - curRoom;
            }
        }
        for (int col = cols - 2; col >= 0; col--) {
            int curRoom = dungeon[rows - 1][col];
            int nextRoom = dp[rows - 1][col + 1];
            if (curRoom < 0) {
                dp[rows - 1][col] = -curRoom + nextRoom;
            } else if (curRoom+1 >= nextRoom) {
                dp[rows - 1][col] = 1;//跨上了就行
            } else {
                dp[rows - 1][col] = nextRoom - curRoom;
            }
        }
        for (int row = rows-2; row >=0 ; row--) {
            for (int col = cols-2; col >=0; col--) {
                int curRoom = dungeon[row][col];
                int nextRoom = Math.min(dp[row+1][col],dp[row][col+1]);//哪条路经济
                if (curRoom < 0) {
                    dp[row][col] = -curRoom + nextRoom;
                } else if (curRoom+1 >= nextRoom) {
                    dp[row][col] = 1;//跨上了就行
                } else {
                    dp[row][col] = nextRoom - curRoom;
                }
            }
        }
        return dp[0][0];
    }

    public static void main(String[] args) {
        int[][] dungeon = {{1,-3,3}, {0,-2,0},{-3,-3,-3}};
        System.out.println(calculateMinimumHP1(dungeon));
    }
}
