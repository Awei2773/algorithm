package com.waigo.nowcoder.MyCode.Code15_tx;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

/**
 * author waigo
 * create 2021-09-26 20:15
 */
public class Main2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String exp = scanner.nextLine();
        System.out.println(calc(exp));
    }

    private static final HashMap<Character, Integer> CALCLEVEL = new HashMap<>();

    static{
        CALCLEVEL.put('@', 3);
        CALCLEVEL.put('x', 2);
        CALCLEVEL.put('+', 1);
    }

    private static long calc(String exp) {
        if (exp == null || exp.length() == 0) return 0;
        int start = 0;
        while(!Character.isDigit(exp.charAt(start))){
            start++;
        }
        //两个栈，一个数栈一个符号栈
        Stack<Integer> numStack = new Stack<>();
        Stack<Character> sympolStack = new Stack<>();
        char[] expChars = exp.toCharArray();
        int N = expChars.length;
        int num = 0;
        for (int i = start; i < N; i++) {
            char curSympol = expChars[i];
            if (Character.isDigit(curSympol)) {
                num = num * 10 + (curSympol - '0');
            } else {
                numStack.push(num);
                num = 0;
                //将操作数栈中比当前运算符要优先的全弹出来进行运算
                while (!sympolStack.isEmpty() && (CALCLEVEL.get(curSympol) <= CALCLEVEL.get(sympolStack.peek()))) {
                    int right = numStack.pop();
                    int left = numStack.pop();
                    numStack.push(calcTwo(sympolStack.pop(), left, right));
                }
                sympolStack.push(curSympol);
            }
        }
        numStack.push(num);
        //将栈中的全运算完成
        while(!sympolStack.isEmpty()){
            int right = numStack.pop();
            int left = numStack.pop();
            numStack.push(calcTwo(sympolStack.pop(), left, right));
        }
        return numStack.isEmpty()?0:numStack.pop();
    }

    private static Integer calcTwo(char sympol, int left, int right) {
        switch (sympol) {
            case '@':
                return left | (left + right);
            case '+':
                return left + right;
            default:
                return left * right;
        }
    }
}
