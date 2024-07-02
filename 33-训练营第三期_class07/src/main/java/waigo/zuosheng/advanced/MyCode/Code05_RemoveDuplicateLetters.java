package waigo.zuosheng.advanced.MyCode;

/**
 * author waigo
 * create 2021-05-30 9:05
 */

/**
 * 给定一个全是小写字母的字符串str，删除多余字符，使得每种字符只保留一个，并让 最终结果字符串的字典序最小
 * 【举例】
 * str = "acbc"，删掉第一个'c'，得到"abc"，是所有结果字符串中字典序最小的。
 * str = "dbcacbca"，删掉第一个'b'、第一个'c'、第二个'c'、第二个'a'，
 * 得到"dabc"， 是所有结 果字符串中字典序最小的。
 */
//dabdcacbca

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 贪心策略：
 * dabdcacbca,第一次我只看dabd，因为超出了这个范围d就没有了，也就是说这四个字符中必然有一个是最终答案里的开头
 * 字符，那么应该选择哪个呢？
 * 很明显，在这个时候肯定是越小越好，只要第一位小了，那答案必然就是最小的，所以选a
 * 下一次从哪里开始看起呢？
 * 很明显，下次应该只关心bdcacbca这一段，这一段中bd必然出第二个字符，因为超出这个范围d就无了，按照选最小的这种
 * 贪心策略，这次选了b
 * 再下次肯定只能看d了，所以选d
 * ...
 * 答案必然贪心到，比较好理解的贪心
 */
public class Code05_RemoveDuplicateLetters {
    public static String removeDuplicateLetters1(String str) {
        if(str==null||str.length()<2) return str;
        int N = str.length();
        char[] strChars = str.toCharArray();
        Map<Character,Integer> wordFreq = new HashMap<>();//ASCII编码共有8位，本来只用了7位，有128个，后来IBM扩展成了256个
        for (int i = 0; i < N; i++) {
            wordFreq.put(strChars[i],wordFreq.getOrDefault(strChars[i],0)+1);
        }
        char[] res = new char[wordFreq.size()];
        process(strChars,0, wordFreq,res,0);
        return new String(res,0,res.length);
    }

    //从begin~end，将字符词频map给你，将挑出来的字符填在res中，现在应该填write位置
    private static void process(char[] strChars, int begin,
                                  Map<Character,Integer> map,char[] res,int write) {
        if(write==res.length) return;
        int minASCAII = Integer.MAX_VALUE;
        int minIdx = 0;
        for (; begin < strChars.length; begin++) {
            if(map.containsKey(strChars[begin])){
                if(strChars[begin]<minASCAII){
                    minASCAII = strChars[begin];
                    minIdx = begin;
                }
                map.put(strChars[begin],map.get(strChars[begin])-1);
                if(map.get(strChars[begin])==0) break;
            }
        }
        res[write++] = (char) minASCAII;
        map.remove(strChars[minIdx]);//存过的就不要让它存在词频里了
        for(int i = minIdx+1;i<=begin;i++){
            if(map.containsKey(strChars[i])){
                map.put(strChars[i],map.get(strChars[i])+1);//minIdx到begin这一段的词频下一次还要用到
            }
        }
        process(strChars,minIdx+1,map,res,write);
    }

    public static void main(String[] args) {
        System.out.println(removeDuplicateLetters1("dabdcacbca"));
    }
}
