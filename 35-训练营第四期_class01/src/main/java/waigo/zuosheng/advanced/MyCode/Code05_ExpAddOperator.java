package waigo.zuosheng.advanced.MyCode;

/**
 * author waigo
 * create 2021-06-04 9:57
 */

import java.util.*;

/**
 * 给定一个只由'0'~'9'字符组成的字符串num，和整数target。可以用+、-和*连接，返回num得到target的所有不同方法。
 * Example 1:
 * Input: num = "123", target = 6 Output: ["1+2+3", "1*2*3"]
 * Example 2:
 * Input: num = "232", target = 8 Output: ["2*3+2", "2+3*2"]
 * Example 3:
 * Input: num = "105", target = 5 Output: ["1*0+5","10-5"]
 * Example 4:
 * Input: num = "00", target = 0 Output: ["0+0", "0-0", "0*0"]
 * Example 5:
 * Input: num = "3456237490", target = 9191 Output: []
 */
public class Code05_ExpAddOperator {
    public static String[] collectAllTargetConsist(String num, int target) {
        String[] res = {};
        if (num == null || num.length() == 0) return res;
        char[] numChars = num.toCharArray();
        if (!validCheck(numChars)) {
            return res;
        }
        List<String> targetExp = new ArrayList<>();
        process(numChars, 0, "", targetExp, target);
        return targetExp.toArray(res);
    }

    //当前在cur，之前全看过了，结果来到了preRes，组出来的表达式为preExp,将答案收在targetExp中
    private static void process(char[] numChars, int curIdx, String path, List<String> targetExp, int target) {
        if (curIdx == numChars.length) {
            if (caculate(path) == target) {
                targetExp.add(path);//收答案
            }
            return;
        }
        //枚举当前位置开始要几位，最少一位，最多cur~len-1
        int curNum;
        int maxNumLen = numChars[curIdx]=='0'?1:numChars.length - curIdx;
        for (int len = 1; len <= maxNumLen; len++) {
            curNum = Integer.valueOf(String.valueOf(numChars, curIdx, len));
            //如果是第一位就直接下传，如果不是第一位得枚举计算可能性
            if (curIdx == 0) {
                process(numChars, curIdx + len, curNum + "", targetExp, target);
            } else {
                //枚举可能性
                int curStr = path.length();
                path += "+" + curNum;
                process(numChars, curIdx + len, path, targetExp, target);

                path = path.substring(0, curStr);
                path += "-" + curNum;
                process(numChars, curIdx + len, path, targetExp, target);

                path = path.substring(0, curStr);
                path += "*" + curNum;
                process(numChars, curIdx + len, path, targetExp, target);
            }
        }
    }
    private static boolean validCheck(char[] numChars) {
        for (int i = 0; i < numChars.length; i++) {
            if (!isNum(numChars[i])) return false;
        }
        return true;
    }

    private static boolean isNum(char numChar) {
        return numChar >= '0' && numChar <= '9';
    }

    //计算1+2*3*3-4*5这样的式子
    public static int caculate(String exp) {
        LinkedList<String> doubleQueue = new LinkedList<>();
        //一开始从右进，右出，将*给计算完毕，碰到一个*就弹出一个来计算，计算完再压回去
        char[] expChars = exp.toCharArray();
        int N = expChars.length;
        for (int start = 0; start < N; start++) {
            if (!isNum(expChars[start])) {
                doubleQueue.addLast(expChars[start] + "");
                continue;
            }
            //如果是数字就把它整个取完,取完之后start就不能还是start了，得从end开始
            int end = start + 1;
            while (end< N &&isNum(expChars[end])) end++;
            String num = String.valueOf(expChars, start, end - start);
            if (!doubleQueue.isEmpty() && "*".equals(doubleQueue.peekLast())) {
                doubleQueue.pollLast();
                num = (Integer.valueOf(Objects.requireNonNull(doubleQueue.pollLast()))
                        * Integer.valueOf(num)) + "";
            }
            doubleQueue.addLast(num);
            start=end-1;//后面自己还有个++
        }
        //接下来算+、-,从左出
        while (doubleQueue.size()>1){
            int firNum = Integer.valueOf(Objects.requireNonNull(doubleQueue.pollFirst()));
            String opr = doubleQueue.pollFirst();
            int secNum = Integer.valueOf(Objects.requireNonNull(doubleQueue.pollFirst()));
            doubleQueue.addFirst((Objects.equals(opr,"+")?firNum+secNum:firNum-secNum)+"");
        }
        return doubleQueue.size()==1?Integer.valueOf(Objects.requireNonNull(doubleQueue.pollFirst())):0;
    }

    public static void main(String[] args) {
//        System.out.println(caculate("1+2*3*3-4*5"));
        System.out.println(Arrays.toString(collectAllTargetConsist("232",8)));
    }
}
