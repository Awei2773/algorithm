package waigo.zuoshen.basic.MyCode;

import java.util.Stack;

/**
 * author waigo
 * create 2021-02-27 12:11
 */
public class Code04_ReverseStackUsingRecursive {
    public static <T> void reverseStackOnlyUsingRecursive(Stack<T> stack){
        if(stack==null) return;
        reverse(stack);
    }

    private static <T> void reverse(Stack<T> stack) {
        //将底拿出来，将上面的翻转压回去，然后压入底
        if(stack.isEmpty()) return;
        T stackBottom = getStackBottom(stack);
        reverse(stack);
        stack.push(stackBottom);
    }

    //拿到底层的一个元素,将上面的压回去
    //  |1|   |1|
    //  |2|-->|2| 返回3
    //  |3|   |_|
    //  |_|
    public static <T> T getStackBottom(Stack<T> stack){
        T res = stack.pop();
        if(!stack.isEmpty()){
            T res1 = getStackBottom(stack);
            stack.push(res);
            return res1;
        }
        return res;
    }

    public static void main(String[] args) {
        //test getStackBottom is true
        /*Stack<Integer> a = new Stack<>();
        a.push(12);
        a.push(13);
        a.push(14);
        System.out.println(getStackBottom(a));
        while (!a.isEmpty()) System.out.println(a.pop());*/
        Stack<Integer> a = new Stack<>();
        a.push(12);
        a.push(13);
        a.push(14);
        reverseStackOnlyUsingRecursive(a);
        while (!a.isEmpty()) System.out.println(a.pop());

    }
}
