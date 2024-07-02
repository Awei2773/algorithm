package waigo.zuoshen.advanced.MyCode;

/**
 * author waigo
 * create 2021-05-15 12:10
 */

/**
 * 面试题 01.05. 一次编辑
 * 字符串有三种编辑操作:插入一个字符、删除一个字符或者替换一个字符。
 * 给定两个字符串，编写一个函数判定它们是否只需要一次(或者零次)编辑。
 *
 * 示例 1:
 *
 * 输入:
 * first = "pale"
 * second = "ple"
 * 输出: True
 *
 *
 * 示例 2:
 *
 * 输入:
 * first = "pales"
 * second = "pal"
 * 输出: False
 *
 * 就是问最小编辑距离是否小于等于1
 * 思路1:
 * 1.过筛，长度相差超过1直接返回false
 * 2.建一张二维表dp[][]，纵轴是first，横轴是second
 * dp[y][x]表示first[0...y]最小编辑成second[0...x]需要多少次
 * 第一列：
 * y>1直接最大，不可能1次编辑好，设置为最长，这里用second的长度表示正无穷
 * y=x=0的时候看两个字符是否相等，相等为0，不等为1
 * y=1,x=0的时候看末尾是否相等，相等为1，不等则值为dp[0][0]
 *
 */
public class Code01_OnlyOneEdit {
    //second.len>=first.len
    //str.len>=1
    public static boolean process(char[] first, char[] second) {
        int cols = second.length;
        int rows = first.length;
        int[][] dp = new int[rows][cols];
        dp[0][0] = first[0]==second[0]?0:1;
        //第一行
        if(cols>1){
            dp[0][1] = first[0]==second[1]?1:1+dp[0][0];
        }
        //第一列
        if(rows>1){
            dp[1][0] = first[1]==second[0]?1:1+dp[0][0];
        }
        for(int col=1;col<cols;col++){
            for(int row=Math.max(col-1,1);row<rows;row++){
                if(Math.abs(row-col)>1){
                    break;
                }else{
                    if(first[row]==second[col]){
                        dp[row][col] = dp[row-1][col-1];
                    }else{
                        int p1 = Math.abs(row-1-col)>1?cols:dp[row-1][col];
                        int p2 = Math.abs(row-col+1)>1?cols:dp[row][col-1];
                        int p3 = dp[row-1][col-1];
                        dp[row][col] = 1+Math.min(Math.min(p1,p2),p3);
                    }
                }
            }
        }
        return dp[rows-1][cols-1]<=1;
    }
    public static boolean oneEditAway(String first, String second) {
        if(first.equals(second)) return true;
        int judge =  first.length() - second.length();
        if(Math.abs(judge)>1) return false;
        if(first.length()==0||second.length()==0) return true;//滤掉可能某个字符为空的情况
        //到了这里first和second长度相差最多为1，长度相同的时候不可能值相同
        return judge<=0?process(first.toCharArray(),second.toCharArray()):
                process(second.toCharArray(),first.toCharArray());
    }

    //方法二:其实这题比起编辑距离来说还要简单一点，不需要计算具体多少编辑距离
    //过好筛之后first.len==second.len或者first.len+1==second.len
    //也就是只有两种情况是正确的
    //一:first长度和second不同时候，first是second的一个长度为second.len-1的子序列
    //二:first和second长度相等，只有一个字符不同
    //我们要做的就是把这两种情况cover到，不属于这两种的通通返回false
    public static boolean oneEditAway2(String first, String second) {
        if(first.equals(second)) return true;
        int judge =  first.length() - second.length();
        if(Math.abs(judge)>1) return false;
        if(first.length()==0||second.length()==0) return true;//滤掉可能某个字符为空的情况
        //到了这里first和second长度相差最多为1，长度相同的时候不可能值相同
        return judge<=0?doublePointWay(first.toCharArray(),second.toCharArray()):
                doublePointWay(second.toCharArray(),first.toCharArray());
    }
    private static boolean doublePointWay(char[] first, char[] second) {
        int pFir = 0,pSec=0;//两个指针分别指向第一个和第二个字符串，准备匹配
        //具体两种成功方式的统一处理点在于出现两指针指向位置不同的时候的处理
        //1.相同长度的时候直接两个后移
        //2.不同长度的时候，pSec后移，pFir不动，因为我们要抓的是first是否能够成为一个长度为second.len-1的子序列
        int diffCount = 0;
        int firLen = first.length;
        int secLen = second.length;
        while (pFir< firLen &&pSec< secLen){
            if(first[pFir]==second[pSec]){
                pFir++;
                pSec++;
                continue;//匹配成功一个字符，直接跳过
            }
            //出现不同
            diffCount++;
            if(diffCount>1) break;//剪枝，已经不可能成功了
            if(secLen==firLen){
                pFir++;
                pSec++;
            }else{
                pSec++;
            }
        }
        return diffCount<=1;
    }
    public static void main(String[] args) {
        /*"teacher"
        "teached"*/
        System.out.println(oneEditAway2("teacer","teached"));
    }
}
