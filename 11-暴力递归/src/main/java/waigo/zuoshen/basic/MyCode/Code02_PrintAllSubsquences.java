package waigo.zuoshen.basic.MyCode;

import java.util.HashSet;

/**
 * author waigo
 * create 2021-02-27 10:09
 */
//打印一个字符串的全部子序列
//打印一个字符串的全部子序列，要求不要出现重复字面值的子序列
//子序列和子串的区别:
//子序列:从左往右可以不用连续，比如"abc"的子序列可以有"ac"
//子串：从左往右必须连续，比如"abc"的子串只能是"a","b","c","ab","bc","abc",""
public class Code02_PrintAllSubsquences {
    public static void printAllSubsquences(String str){
        if(str==null) return;
        process(str.toCharArray(),0,"");
    }
    //打印全序列，i之前的已经决断好放在path中
    private static void process(char[] strChars, int i, String path) {
        if(i==strChars.length){
            System.out.println(path);
            return;
        }
        process(strChars,i+1,path);//no choose i char
        process(strChars,i+1,path+strChars[i]);//choose i char
    }
    //去重思路一：将所有的子序列都存在一个HashSet中，会自动去重
    public static void printAllDistinctSubsquences1(String str){
        if(str==null) return;
        HashSet<String> subSequence = new HashSet<>();
        process1(str.toCharArray(),0,"",subSequence);
        for(String subSequenceStr:subSequence){
            System.out.println(subSequenceStr);
        }
    }
    private static void process1(char[] chars, int i, String path, HashSet<String> subSequence){
        if(i==chars.length) {
            subSequence.add(path);
            return;
        }
        process1(chars,i+1,path,subSequence);
        process1(chars,i+1,path+chars[i],subSequence);
    }
    public static void main(String[] args) {
        printAllSubsquences("aaa");
//        printAllDistinctSubsquences1("aaa");
    }

}
