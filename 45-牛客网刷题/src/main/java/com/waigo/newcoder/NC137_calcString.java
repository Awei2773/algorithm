package com.waigo.newcoder;

import java.util.Stack;

/**
 * author waigo
 * create 2021-09-23 10:55
 */
public class NC137_calcString {
    public int solve(String s) {
        if (s == null || s.length() == 0) return 0;
        return calc(s.toCharArray(), 0)[0];
    }

    //计算idx到)或者结尾的表达式的值，返回值中0:表达式的值，1:接着从哪个下标开始
    public int[] calc(char[] strs, int idx) {
        Stack<Integer> stack = new Stack<>();
        //每次处理一对值，+3，-2，这样一对一对的处理，保证栈里只有值，而且之后栈中的值只做加法，妙啊
        int N = strs.length;
        int i = idx;
        int num = 0;
        char preSign = '+';
        //考虑(1+2)，以)结尾这种情况怎么处理？
        //考虑1+5,这最后一个数怎么收集？
        for (; i < N && strs[i] != ')'; i++) {
            if (Character.isDigit(strs[i])) {
                num = num * 10 + (strs[i] - '0');
            } else if (strs[i] == '(') {
                //一对一对，处理，所以，碰到下一个非数字的时候处理前一对，此时num是第二个运算数
                //就算是+3这种情况，也就是此时第一个+进行结算的时候其实前面没有数的，将num=0压入也不会影响结果
                int[] inRes = calc(strs, i + 1);
                i = inRes[1];
                num = inRes[0];
            } else {
                resolvePair(stack, preSign, num);
                num = 0;
                preSign = strs[i];
            }
        }
        resolvePair(stack, preSign, num);//收集最后一个数
        int res = 0;
        while (!stack.isEmpty()) {
            res += stack.pop();
        }
        return new int[]{res, i};
    }

    public void resolvePair(Stack<Integer> stack, char preSign, int num) {
        switch (preSign) {
            case '+':
                stack.push(num);
                break;
            case '-':
                stack.push(-num);
                break;
            default:
                stack.push(stack.pop() * num);
        }
    }

    public static void main(String[] args) {
        int solve = new NC137_calcString().solve("(-3+7)-5*8+15");
        System.out.println(solve);
    }
}
