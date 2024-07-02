package waigo.zuosheng.advanced.MyCode;

import java.util.HashMap;

/**
 * author waigo
 * create 2021-07-05 23:06
 */

public class Code04_ExpMatch {
    public static boolean isMatch(String s, String p) {
        if(s==null||p==null) return false;
        char[] sChars = s.toCharArray();
        char[] pChars = p.toCharArray();
        if(!isValid(pChars)) return false;

        return process(sChars,pChars,0,0,new HashMap<>());
    }
    //流程保证ei绝对不是*
    public static boolean process(char[] sChars,char[] pChars,int si,int ei,HashMap<String,Boolean> map){
        String key = si+"_"+ei;
        if(map.containsKey(key)) return map.get(key);
        if(ei==pChars.length){
            map.put(key,si == sChars.length);
            return si == sChars.length;
        }

        //如果后面不是*
        if(ei+1>=pChars.length||pChars[ei+1]!='*'){
            boolean res = (si<sChars.length)&&(sChars[si] == pChars[ei]||
                    pChars[ei] == '.') && process(sChars,pChars,si+1,ei+1,map);
            map.put(key,res);
            return res;
        }
        //此时后面是*,枚举0个重复，1个重复...相同的字符长度，直到3个a都试一遍
        //aaabc    a*
        while(si<sChars.length&&(sChars[si]==pChars[ei]||pChars[ei]=='.')){
            if(process(sChars,pChars,si,ei+2,map)) {
                map.put(key,true);
                return true;
            }
            si++;
        }
        boolean res = process(sChars,pChars,si,ei+2,map);
        map.put(key,res);
        return res;
    }
    //两个*挨着或者*为开头，则为False
    public static boolean isValid(char[] exps){
        for(int i = 0;i<exps.length;i++){
            if(exps[i]=='*'&&(i-1<0||exps[i-1]=='*')) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(isMatch("a","a*aa*a*a*a*a*a*"));
    }
}
