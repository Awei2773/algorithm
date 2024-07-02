package waigo.zuoshen.advanced.MyCode;

/**
 * author waigo
 * create 2021-05-05 11:30
 */
/*
Given a string containing just the characters '(' and ')', find the length of the longest valid (well-formed) parentheses substring.

Example 1:

Input: s = "(()"
Output: 2
Explanation: The longest valid parentheses substring is "()".
Example 2:

Input: s = ")()())"
Output: 4
Explanation: The longest valid parentheses substring is "()()".
Example 3:

Input: s = ""
Output: 0
 

Constraints:

0 <= s.length <= 3 * 104
s[i] is '(', or ')'.
*/
public class Code04_LongestValidParentheses {

    public static int longestValidParentheses(String s) {
        char[] str = s.toCharArray();
        int N = str.length;
        int[] dp = new int[N];
        int ans = 0;
        for(int i = 1;i<N;i++){//0位置不管是啥，以它结尾肯定长度为0
            //枚举每个位置，以i位置结尾的字符串的最长有效长度为多长
            if(str[i]==')'){//以(结尾的有效长度肯定为0
                int last = i - dp[i - 1] - 1;
                dp[i] = last <0?0:
                        (str[last]=='('?dp[i-1]+2+(last-1<0?0:dp[last-1]):0);
                ans = Math.max(ans,dp[i]);
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(longestValidParentheses("((()()()()))))()())))()()()()()()()()()()((((((((())))))"));
        System.out.println(longestValid("((()()()()))))()())))()()()()()()()()()()((((((((())))))"));
    }
    /*************************************************************************************/
    public static int longestValid(String exps){
        if(exps==null||exps.length()==0) return 0;
        int N = exps.length();
        int[] dp = new int[N];
        int res = 0;
        for(int i = 1;i<N;i++){
            if(exps.charAt(i)==')'){
                int last = i - dp[i - 1] - 1;
                dp[i] = last>=0&&exps.charAt(last)=='('?(dp[i-1]+2+(last-1>=0?dp[last-1]:0)):0;
                res = Math.max(res,dp[i]);
            }
        }
        return res;
    }
}
