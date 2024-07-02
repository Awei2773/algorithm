package waigo.zuoshen.advanced.MyCode;

/**
 * author waigo
 * create 2021-05-13 7:54
 */

import java.util.Stack;

/**
 * 给你一个栈，请你逆序这个栈，
 * 不能申请额外的数据结构，
 * 只能使用递归函数。 如何实现?
 */
public class Code02_ReverseStack {
    public static void reverseStack(Stack<Integer> stack) {
        if (stack.size() <= 1) return;
        Integer bottom = getStackBottom(stack);
        reverseStack(stack);
        stack.push(bottom);
    }

    private static Integer getStackBottom(Stack<Integer> stack) {
        if (stack.size() == 1) return stack.pop();
        Integer cur = stack.pop();
        Integer stackBottom = getStackBottom(stack);
        stack.push(cur);
        return stackBottom;
    }

    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);

        reverseStack(stack);
        while (!stack.isEmpty()) System.out.println(stack.pop());
    }
}
