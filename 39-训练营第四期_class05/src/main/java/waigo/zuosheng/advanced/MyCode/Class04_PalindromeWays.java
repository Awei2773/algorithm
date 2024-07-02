package waigo.zuosheng.advanced.MyCode;

/**
 * author waigo
 * create 2021-06-26 8:39
 */

import java.util.ArrayList;

/**
 * 对于一个字符串, 从前开始读和从后开始读是一样的, 我们就称这个字符串是回文串。例如"ABCBA","AA", "A" 是回文串,
 * 而"ABCD", "AAB"不是回文串。牛牛特别喜欢回文串, 他手中有一个字符串s, 牛牛在思考能否从字 符串中移除部分
 * (0个或多个)字符使其变为回文串，并且牛牛认为空串不是回文串。牛牛发现移除的方案可能有 很多种, 希望你来帮他
 * 计算一下一共有多少种移除方案可以使s变为回文串。对于两种移除方案, 如果移除的字 符依次构成的序列不一样就是
 * 不同的方案。
 * 例如，XXY 4种 ABA 5种
 * 【说明】 这是今年的原题，提供的说明和例子都很让人费解。现在根据当时题目的所有测试用例，重新解释当时的题目
 * 含义: 1)"1AB23CD21"，你可以选择删除A、B、C、D，然后剩下子序列{1,2,3,2,1}，只要剩下的子序列是同一个，
 * 那 么就只算1种方法，和A、B、C、D选择什么样的删除顺序没有关系。 2)"121A1"，其中有两个{1,2,1}的子序列，
 * 第一个{1,2,1}是由{位置0，位置1，位置2}构成，第二个{1,2,1} 是由{位置0，位置1，位置4}构成。这两个子序列
 * 被认为是不同的子序列。也就是说在本题中，认为字面值一样 但是位置不同的字符就是不同的。 3)
 * 其实这道题是想求，str中有多少个不同的子序列，每一种子序列只对应一种删除方法，那就是把多余的东 西去掉，
 * 而和去掉的顺序无关。
 * 4)也许你觉得我的解释很荒谬，但真的是这样，不然解释不了为什么，XXY 4种 ABA 5种，而且其他的测 试用例
 * 都印证了这一点。
 */
@SuppressWarnings("Duplicates")
public class Class04_PalindromeWays {
    public static int getWays(String str){
        if(str==null||str.length()==0) return 0;
        char[] strChars = str.toCharArray();
        //范围尝试模型
        //1.L...R,如果只有一个字符，那么就一种
        //2.普通位置
        //可能性分析,这里打上对号表示在回文中必须有这个字符
        //1.L(x)....R(√)     a       dp[row][col] = a+b+c+d
        //2.L(√)....R(x)     b       dp[row+1][col] = a+c;
        //3.L(x)....R(x)     c       dp[row][col-1] = b+c;
        //                           dp[row+1][col-1] = c;
        //                           d = 1+c(这个1是只有str[L,R]中间的全删了)
        //4.L(√)....R(√) 这是在str[L]等于str[R]下才有的  d
        int N = strChars.length;
        int[][] dp = new int[N][N];
        //1.先填对角线，左下角全部无效
        //2.dp[row+1][col-1]在第二斜线不适用，是无效位置，所以第二斜线得特殊处理
        int col = 0;
        while (col<N){
            //定下一条斜线的开始
            int tRow = 0;
            int tCol = col;
            while (tRow<N&&tCol<N){
                if(tRow==tCol)
                    dp[tRow][tCol] = 1;    //第一斜线
                else if(tCol-tRow==1)
                    dp[tRow][tCol] = strChars[tCol]==strChars[tRow]?3:2;//第二斜线
                else{
                    //普通位置 返回a+b+c+d
                    dp[tRow][tCol] =
                            dp[tRow+1][tCol] + dp[tRow][tCol-1] - dp[tRow+1][tCol-1];//这里是a+b+c
                    //看看有没有d
                    if(strChars[tCol]==strChars[tRow]){
                        dp[tRow][tCol]+=dp[tRow+1][tCol-1]+1;
                    }
                }
                tRow++;
                tCol++;
            }
            col++;
        }
        return dp[0][N-1];
    }

    public static void main(String[] args) {
        System.out.println(getWays("41532431AB34234232432SDABA234234232432SDFSD"));
    }
}
