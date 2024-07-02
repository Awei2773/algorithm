package waigo.zuoshen.advance.MyCode;

/**
 * author waigo
 * create 2021-06-14 11:20
 */

import java.util.HashMap;
import java.util.Map;

/**
 * 给定一个字符串，编写一个函数判定其是否为某个回文串的排列之一。
 *
 * 回文串是指正反两个方向都一样的单词或短语。排列是指字母的重新排列。
 *
 * 回文串不一定是字典当中的单词。
 *
 *  
 *
 * 示例1：
 *
 * 输入："tactcoa"
 * 输出：true（排列有"tacocat"、"atcocta"，等等）
 */
public class Code03_PalindromePermutationLCCI {
    //1.最多只能有一种字符出现奇数次
    public static boolean canPermutePalindrome(String s) {
        if(s==null||s.length()<2) return true;
        char[] chars = s.toCharArray();
        int count = 0;
        HashMap<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < chars.length; i++) {
            map.put(chars[i],map.getOrDefault(chars[i],0)+1);
        }
        //遍历map
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            if((entry.getValue()&1)!=0){
                count++;
            }
        }
        return count<2;
    }

    public static void main(String[] args) {
        System.out.println(canPermutePalindrome("tactcoa"));
    }
}
