package waigo.zuoshen.advanced.MyCode;

import java.util.Stack;

/**
 * author waigo
 * create 2021-05-05 9:56
 */
/*
给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。
有效字符串需满足：
左括号必须用相同类型的右括号闭合。
左括号必须以正确的顺序闭合。
示例 5：
输入：s = "{[]}"
输出：true
提示：
1 <= s.length <= 104
s 仅由括号 '()[]{}' 组成


括号匹配指的是能够让每种右括号都找到自己可以配对的左括号

如果说此题只有一种括号'('或')'那么好说，定义一个变量count记录此时出现了几个'(',只要每次出现一个')'就让count--就好了，
*/
public class Code02_IsValidBrackets {
    public static boolean isLeft(char c){
        return (c=='(')||(c=='[')||(c=='{');
    }
    public static boolean isMatch(char a,char b){
        return (a=='('&&b==')')||(a=='['&&b==']')||(a=='{'&&b=='}');
    }
    public static boolean isValid(String s) {
        char[] expStr = s.toCharArray();
        Stack<Character> stack = new Stack<>();
        for(int i = 0;i<expStr.length;i++){
            char curStr = expStr[i];
            if(isLeft(curStr)){
                stack.push(curStr);
            }else{
                if(stack.isEmpty()||!isMatch(stack.pop(),curStr)){
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        System.out.println(isValid("()[]{}"));
    }
}
