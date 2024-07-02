package com.waigo.newcoder;

/**
 * author waigo
 * create 2021-08-23 16:54
 */
public class NC49_longestValidParentheses {
    /*public int longestValidParentheses (String s) {
        //用一个变量记一下左括号的个数，一旦变成负数就说明出现一个答案
        //用一个变量记一下答案，也就是匹配的对数，每到一个)如果此时左括号不为0就匹配一对
        //res的变化 1.在出现右括号且左括号为0的时候抓一次:表示截止到这个右括号为止已经抓到了最大的匹配长度
        //         2.在left小于0的时候将res清0
        //需要思考res是否能够如预期一样维持住意义，是否有特殊情况，从res的置0时机来考虑
        //res仅在left小于0的时候置0，也就是说如果出现这种情况:(()()(((就抓不到答案了,还会抓到错误的答案
        //解决方法就是逆着再来一遍
        //)触发收集抓不到的答案(原因：'('太多了,导致left一直不为0)
        //(触发肯定能抓到
        if(s==null||s.length()<2) return 0;
        int n = s.length();
        int left = 0;
        int res = 0;
        int max = 0;//抓到出现的答案中最多的对数
        char[] chars = s.toCharArray();
        for(char c:chars){
            if(c=='('){
                left++;
            }else if(c==')'){
                left--;
                res++;
                if(left<0){
                    res = 0;
                    left = 0;
                }else if(left==0){
                    max = Math.max(max,res);//只在匹配完全，也就是left==0的时候抓一次答案
                }
            }
        }
        int right = 0;
        res = 0;
        for(int i = n-1;i>=0;i--){
            char c = chars[i];
            if(c==')'){
                right++;
            }else if(c=='('){
                right--;
                res++;
                if(right<0){
                    res = 0;
                    right = 0;
                }else if(right==0){
                    max = Math.max(max,res);//只在匹配完全，也就是left==0的时候抓一次答案
                }
            }
        }

        return max<<1;//对数要乘2
    }*/

    //2.动态规划法
    //dp[i]表示必须以i位置截止的最长正确子串长度
    //1.i为0或者i位置的字符是(，很明显是0
    //2.两种情况
    //2.1 i-1是(
        //匹配一对，dp[i] = 2 + dp[i-2]
    //2.2 i-1是)
        //看一下i-dp[i-1]-1是否是(,不是为0
        //是的话:dp[i] = 2 + dp[i-1] + dp[i-dp[i-1]-2]
    public int longestValidParentheses (String s) {
        if(s==null||s.length()<2) return 0;
        int n = s.length();
        char[] chars = s.toCharArray();
        int max = 0;
        int[] dp = new int[n];
        for(int i = 1;i<n;i++){
            if(chars[i]==')'){
                if(chars[i-1]=='('){
                    dp[i] = 2 + (i-2>=0?dp[i-2]:0);
                }else{
                    int last = i - dp[i-1]-1;
                    if(last>=0&&chars[last]=='('){
                        dp[i] = 2 +dp[i-1]+(last-1>=0?dp[last-1]:0);
                    }
                }
                max = Math.max(max,dp[i]);
            }
        }
        return max;
    }

    public static void main(String[] args) {
        System.out.println(new NC49_longestValidParentheses().longestValidParentheses(")()(()()((((((())("));
    }
}
