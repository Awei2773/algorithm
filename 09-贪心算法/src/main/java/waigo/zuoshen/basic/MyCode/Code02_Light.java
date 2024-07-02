package waigo.zuoshen.basic.MyCode;

import java.util.HashSet;

/**
 * author waigo
 * create 2021-03-02 10:49
 */
/*
给定一个字符串str，只由‘X’和‘.’两种字符构成。
‘X’表示墙，不能放灯，也不需要点亮
‘.’表示居民点，可以放灯，需要点亮
如果灯放在i位置，可以让i-1，i和i+1三个位置被点亮
返回如果点亮str中所有需要点亮的位置，至少需要几盏灯

 */
public class Code02_Light {
    //for test暴力递归
    public static int getLessLightNum_fr(String str) {
        if (str == null) return 0;
        HashSet<Integer> lightIdxSet = new HashSet<>();
        return process(str.toCharArray(), 0, lightIdxSet);
    }

    //返回从index开始到数组结束位置最少需要多少栈灯，isLight表示这个位置是否被点亮
    //[0...index-1]已经放好灯了，穷举每个位置两种可能，放灯和不放灯，最后将所有放灯的位置存储起来
    //到了每种方法的最后一个点时测试这些方法是否合法，然后不合法的筛选掉，从剩下的中找出最小的
    private static int process(char[] strChars, int index, HashSet<Integer> lightIdxSet) {
        //暴力递归不是最后的解，我们需要的是拿到最优解作为对数器，所以有多暴力就多暴力，以最快的速度写出来就好了
        if (index == strChars.length) {
            //合法性校验
            for (int i = 0; i < strChars.length; i++) {
                if (strChars[i] == 'X') continue;
                //'.'位置只要前后或者自己这里有一盏灯就可以了
                if (!lightIdxSet.contains(i - 1) && !lightIdxSet.contains(i + 1) && !lightIdxSet.contains(i))
                    return Integer.MAX_VALUE;
            }
            return lightIdxSet.size();
        }
        if (strChars[index] == '.') {
            //放灯
            lightIdxSet.add(index);
            int res1 = process(strChars, index + 1, lightIdxSet);
            lightIdxSet.remove(index);
            //不放灯
            int res2 = process(strChars, index + 1, lightIdxSet);
            return Math.min(res1, res2);//返回最小灯
        }
        //'x'直接返回下一个位置开始的最小灯
        return process(strChars, index + 1, lightIdxSet);
    }


    //贪心策略：每次尽可能一盏灯照亮更多地方，
    //一个递归返回[index...strChars.length)最少需要多少灯，每次递归开始时[0...index-1)位置都已经处理好了，不用再管
    public static int getLessLightNum_tx(String str) {
        if (str == null) return 0;
        return process_tx(str.toCharArray(), 0);
    }

    //返回index到最后最少需要多少栈灯
    private static int process_tx(char[] strChars, int index) {
        if (index >= strChars.length) return 0;
        if (strChars[index] == 'X') return process_tx(strChars, index + 1);//当前位置不用放灯
        //index位置必是'.'
        if (index + 1 < strChars.length) {
            if (strChars[index + 1] == '.') {
                return 1 + process_tx(strChars, index + 3);
            } else {
                return 1 + process_tx(strChars, index + 2);
            }
        }
        //如果没有下一个位置了，这个位置得放灯
        return 1 + process_tx(strChars, index + 1);
    }

    //改非递归，贪心一般都不用递归
    //从左到右，只处理index处是'.'的情况，index前的都已经照亮了
    //就看这个index如何贪心处理最优然后将整个循环连起来
    //index+1处如果是'.'，那么肯定放在index+1处，这样可以照亮更多范围
    //index+1处如果溢出了，或者是'x'那么就得放在index处
    public static int getLessLightNum_tx_noneRecursive(String str) {
        if (str == null || str.length() == 0) return 0;
        int light = 0;
        char[] strChars = str.toCharArray();
        for(int index = 0;index<strChars.length;){
            if(strChars[index]=='X') {
                index++;
                continue;
            }
            //index必为'.'
            light++;
            index = index+1<strChars.length&&strChars[index+1]=='.'?index+3:index+2;
        }
        return light;
    }

    private static String generalRandomStr(int maxLen) {
        int len = (int) (Math.random() * maxLen) + 1;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < len; i++) {
            builder.append(Math.random() < 0.3 ? "X" : ".");
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        int maxLen = 10, testTimes = 100000;
        int i = 0;
        for (; i < testTimes; i++) {
            String s = generalRandomStr(maxLen);
            if (getLessLightNum_fr(s) != getLessLightNum_tx_noneRecursive(s)) {
                System.out.println(s);
                break;
            }
        }
        System.out.println(i == testTimes ? "finish!!!" : "fucking");
    }
}
