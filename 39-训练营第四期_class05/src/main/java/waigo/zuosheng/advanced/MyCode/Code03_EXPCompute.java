package waigo.zuosheng.advanced.MyCode;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Objects;

/**
 * author waigo
 * create 2021-08-15 0:20
 */
public class Code03_EXPCompute {
    public HashMap<String,Integer> priority = new HashMap<>();
    {
        priority.put("+",1);
        priority.put("-",1);
        priority.put("*",1);
    }
    public int solve (String s) {
        if(s==null||s.length()==0) return 0;
        return calc(s.toCharArray(),0)[0];
    }
    //calc返回一个数组，这一段表达式计算结果多少:0，计算到第几位了:1
    //idx计算到)或者到结尾
    public int[] calc(char[] strs,int idx){
        ArrayDeque<String> stack = new ArrayDeque<>();
        int cur = 0;
        int[] bra;//子括号的运算结果
        while(idx<strs.length&&strs[idx]!=')'){
            if(isNumber(strs[idx])){
                cur = cur*10+(strs[idx++]-'0');
            }else if(strs[idx]!='('){
                addNum(cur,stack);
                cur = 0;
                stack.addLast(String.valueOf(strs[idx++]));
            }else{
                //是左括号
                bra = calc(strs,idx+1);
                idx = bra[1];
                cur = bra[0];
            }
        }
        addNum(cur,stack);
        return new int[]{getNum(stack),idx+1};
    }
    public boolean isNumber(char c){
        return c>='0'&&c<='9';
    }
    public void addNum(int num,Deque<String> stack){
        if(!stack.isEmpty()){
            String top = stack.peekLast();
            if(priority.get(top)>1){
                top = stack.getLast();
                num = calcTwo(Integer.valueOf(stack.getLast()),num,top);
            }
        }
        stack.addLast(String.valueOf(num));//有个好处，避免了出现-号开头的情况，这样(-3)就变成了(0-3)方便处理
    }
    public int calcTwo(int first,int next,String opr){
        if("-".equals(opr)){
            return first - next;
        }else if("+".equals(opr)){
            return first + next;
        }else if("*".equals(opr)){
            return first * next;
        }
        return first / next;
    }
    public int getNum(Deque<String> stack){
        int res = 0;
        boolean opt = true;//表示加法，每个开头都不会是字符
        while(!stack.isEmpty()){
            String opr = stack.pollFirst();
            if("-".equals(opr)){
                opt = false;
            }else if("+".equals(opr)){
                opt = true;
            }else{
                Integer num = Integer.valueOf(opr);
                res += opt?num:-num;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        new Code03_EXPCompute().solve("(-3+7)-5*8+15");
    }
}
