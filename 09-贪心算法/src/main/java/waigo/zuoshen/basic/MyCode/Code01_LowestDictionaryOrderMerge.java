package waigo.zuoshen.basic.MyCode;

/**
 * author waigo
 * create 2021-02-24 13:20
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * 面试题：
 * 给定一个由字符串组成的数组strs，
 * 必须把所有的字符串拼接起来，
 * 返回所有可能的拼接结果中，字典序最小的结果
 */
public class Code01_LowestDictionaryOrderMerge {
    //对数器：暴力递归
    public static String getLowestMerge1(String[] strs) {
        if (strs == null || strs.length == 0) return null;
        ArrayList<String> resultList = new ArrayList<>();
        //使用List的时候需要注意，千万不要在一个迭代中使用remove，remove后数组会进行一个压缩，即使是
        //你后来又add回去了，元素位置乱了就出问题了
        ArrayList<Integer> useSet = new ArrayList<>();
        process("", strs, resultList, useSet);
        try{
            String lowest = resultList.get(0);
            for (int i = 1; i < resultList.size(); i++)
                if (resultList.get(i).compareTo(lowest) < 0) lowest = resultList.get(i);
            return lowest;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param s          当前拼接到的字符串
     * @param useSet    拼接过的字符串
     * @param resultList 已经尝试过的序列集合
     */
    private static void process(String s, String[] strs, ArrayList<String> resultList, ArrayList<Integer> useSet) {
        if (useSet.size() >= strs.length) {
            resultList.add(s);//这里存储已经使用过的字符串不能存储值，不然出现相同字符串的时候，比如
            //"k","k","k"，一上来选了k，然后发现有了，就无法再次选下一个K，导致字符串数量无法到达strs.length
            return;
        }
        for (int i = 0;i < strs.length;i++) {
            if (!useSet.contains(i)) {
                useSet.add(i);//选了str，存它下标
                process(s + strs[i], strs, resultList, useSet);
                useSet.remove((Integer)i);//恢复现场，不选str,去选其他的
            }
        }
    }

    //贪心算法
    public static String getLowestMerge2(String[] strs) {
        if (strs == null || strs.length == 0) return null;
        Arrays.sort(strs, (o1, o2) -> (o1+o2).compareTo(o2+o1));
        StringBuilder result = new StringBuilder();
        for (String str : strs) {
            result.append(str);
        }

        return result.toString();
    }
    public static String[] generalStringAry(int maxSize, int maxStrLen) {
        if (maxSize < 0) return null;
        int size = (int) (Math.random() * (maxSize + 1));//0~maxSize
        String[] ary = new String[size];
        for (int i = 0; i < size; i++) {
            ary[i] = generalRandomString(maxStrLen);
        }
        return ary;
    }

    public static String generalRandomString(int maxStrLen) {
        char[] strChars = new char[(int) (Math.random() * maxStrLen)+1];//1~maxStrLen-1
        for (int i = 0; i < strChars.length; i++) {
            strChars[i] = (char) ((int) (Math.random() * 26) + 'a');
        }
        return String.valueOf(strChars);
    }

    public static void main(String[] args) {
        int maxSize = 6, maxStrLen = 3, testTimes = 1000000;
        int i = 0;
        String lowestMerge2="";
        String lowestMerge1="";
        String[] strs = null;
        for (; i < testTimes; i++) {
            strs = generalStringAry(maxSize, maxStrLen);
            lowestMerge2 = getLowestMerge2(strs);
            lowestMerge1 = getLowestMerge1(strs);
            if (!Objects.equals(lowestMerge2, lowestMerge1)) break;
        }
        System.out.println(i == testTimes ? "finish!!!" : "oops!!!");

    }
}
