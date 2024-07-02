package waigo.zuoshen.advanced.MyCode;

/**
 * author waigo
 * create 2021-05-05 10:49
 */

/*
Given a string S of '(' and ')' parentheses, we add the minimum number of parentheses ( '(' or ')', and in any positions ) so that the resulting parentheses string is valid.
Formally, a parentheses string is valid if and only if:
    It is the empty string, or
    It can be written as AB (A concatenated with B), where A and B are valid strings, or
    It can be written as (A), where A is a valid string.
Given a parentheses string, return the minimum number of parentheses we must add to make the resulting string valid.

Example 4:

Input: "()))(("
Output: 4

Note:

S.length <= 1000
S only consists of '(' and ')' characters.
 */
public class Code03_MakeParenthesesValid {
    public static int minAddToMakeValid(String S) {
        char[] parentheses = S.toCharArray();
        int count= 0;
        int need = 0;
        for(int i = 0;i<parentheses.length;i++){
            if((count-=parentheses[i]=='('?-1:1)==-1){
                need++;
                count=0;
            }
        }
        return need+count;
    }

    public static void main(String[] args) {
        System.out.println(minAddToMakeValid("()))(("));
    }
}
