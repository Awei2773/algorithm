package waigo.zuoshen.basic.MyCode;

/**
 * author waigo
 * create 2021-02-27 12:30
 */

/**
 * 规定1和A对应、2和B对应、3和C对应...
 * 那么一个数字字符串比如"111”就可以转化为:
 * "AAA"、"KA"和"AK"
 * 给定一个只有数字字符组成的字符串str，返回有多少种转化结果
 * 注意：这里说的是"1"-->"A","2"-->"B"不是说的字符串值为1对应"A"
 * 所以"01"这种是没有对应的，不然就得记录路径，每次判断它的值是否超过26了，做法不同
 * <p>
 * 通过注意点，我们可以得到一个base case，如果当前处理到的数字字符是'0'
 * 那么它只有可能是一个数字字符串的最后一个字符，不能作为开头
 * <p>
 * 使用的是从左到右的尝试模型
 */
public class Code06_ConvertToLetterString {
    public static int getTransNum(String str) {
        if (str == null || str.length() == 0) return 0;
        return process(str.toCharArray(), 0);
    }

    //从index开始到结束的字符串有多少种转换结果，index之前的都已经处理过了
    //每次进函数都是以index为头进行的尝试
    private static int process(char[] numChars, int index) {
        if (index >= numChars.length) {//base case1
            return 1;
        }
        if (numChars[index] == '0') {//base case2
            return 0;//0不能为头
        }
        //只有1和2为头才有两位，所以单独处理
        if (numChars[index] == '1') {//10~19都可以，所以就不用判断下一位的值了
            //自己一位
            int res = 0;
            res += process(numChars, index + 1);
            //两位
            if (index + 1 < numChars.length) res += process(numChars, index + 2);//得有下一位才能两位一起
            return res;
        }
        if (numChars[index] == '2') {//20~25才可以两位
            int res = 0;
            res += process(numChars, index + 1);
            if (index + 1 < numChars.length && numChars[index + 1] < '6') {
                res += process(numChars, index + 2);
            }
            return res;
        }
        return process(numChars, index + 1);//其他的都是自己做一位，从下一位继续去执行
    }

    /**
     * --------------------------------------------------------------------------------------------------
     */
    //dynamic programing动态规划1.0，记忆性搜索，使用一个表，存储已经计算过的子过程
    private static int getTransNum_dp1(String str) {
        if (str == null || str.length() == 0) return 0;
        //可变参数只有一个Index，取值范围为0~numChars.length,共numChars.length+1个
        char[] numChars = str.toCharArray();
        int[] dp_cache = new int[numChars.length + 1];
        for (int i = 0; i < dp_cache.length; i++)
            dp_cache[i] = -1;//-1表示没有求过值
        return process1(numChars, 0, dp_cache);
    }

    private static int process1(char[] numChars, int index, int[] dp_cache) {
        if (dp_cache[index] != -1) return dp_cache[index];

        if (index == numChars.length) {//base case1
            dp_cache[index] = 1;
            return dp_cache[index];
        }
        if (numChars[index] == '0') {//base case2
            dp_cache[index] = 0;
            return dp_cache[index];//0不能为头
        }
        //只有1和2为头才有两位，所以单独处理
        if (numChars[index] == '1') {//10~19都可以，所以就不用判断下一位的值了
            //自己一位
            int res = 0;
            res += process1(numChars, index + 1, dp_cache);
            //两位
            if (index + 1 < numChars.length) res += process1(numChars, index + 2, dp_cache);//得有下一位才能两位一起
            dp_cache[index] = res;
            return dp_cache[index];
        }
        if (numChars[index] == '2') {//20~26才可以两位
            int res = 0;
            res += process(numChars, index + 1);
            if (index + 1 < numChars.length && numChars[index + 1] >= '0' && numChars[index + 1] <= '6') {
                res += process(numChars, index + 2);
            }
            dp_cache[index] = res;
            return dp_cache[index];
        }
        dp_cache[index] = process(numChars, index + 1);
        return dp_cache[index];//其他的都是自己做一位，从下一位继续去执行
    }

    /**
     * --------------------------------------------------------------------------------------------------
     */
    //dynamic programing动态规划2.0，细粒度，结构化，先填好一张表，然后直接返回答案，实际上这个和上面记忆搜索
    //的傻缓存方式时间复杂度相同，那个自顶向下，这个自顶向上，从简单到复杂
    //这种方式就是利用暴力递归，暴力递归是一直找到最小case然后回溯，这个是直接将所有可能的样本从小case到大case
    //按照暴力递归的处理方式进行一个求值
    private static int getTransNum_dp2(String str) {
        if (str == null || str.length() == 0) return 0;
        char[] numChars = str.toCharArray();
        int[] dp = new int[numChars.length + 1];
        dp[dp.length - 1] = 1;
        for (int i = numChars.length - 1; i >= 0; i--) {
            if (numChars[i] == '0') {
                dp[i] = 0;
            } else if (numChars[i] == '1') {
                dp[i] = dp[i + 1];
                if (i + 1 < numChars.length) dp[i] += dp[i + 2];
            } else if (numChars[i] == '2') {
                dp[i] = dp[i + 1];
                if (i + 1 < numChars.length && numChars[i + 1] >= '0' && numChars[i + 1] <= '6') {
                    dp[i] += dp[i + 2];
                }
            } else {
                dp[i] = dp[i + 1];
            }
        }
        return dp[0];
    }

    public static void main(String[] args) {
//        System.out.println(numStrToStr("1"));
        System.out.println(getTransNum("7210231234234234"));
        System.out.println(getTransNum_dp1("7210231234234234"));
        System.out.println(getTransNum_dp2("7210231234234234"));
    }
}
/*
    总结：这道题关键点是区别和那种问你字符串有多少种序列子串的题目
    这个不用进行沿途路径的记录，因为不是一段的，而是一小段一小段的，而且一个小段最长为2，
    所以应该将之前的处理之后，就是选好到底是自己一段还是自己和index+1一段后不用管之前的了，
    每次都是选择一个不同小段。后面如果因为前面的小段导致'0'作为了开头则返回0，表示这条路走不通。
    每次我们都是以一种合法的方式进行小段的划分，如果直到最后都没有出现'0'开头的段的话，那么说明这条
    路就走通了，会产生一种转化结果，则返回1。
*/