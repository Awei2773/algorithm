package waigo.zuoshen.basic.MyCode;

import java.util.HashMap;

/**
 * author waigo
 * create 2021-03-05 16:34
 */

/*
剑指 Offer 19. 正则表达式匹配
请实现一个函数用来匹配包含'. '和'*'的正则表达式。模式中的字符'.'表示任意一个字符，而'*'表示它前面的字符可以出现任意次（含0次）。
在本题中，匹配是指字符串的所有字符匹配整个模式。
例如，字符串"aaa"与模式"a.a"和"ab*ac*a"匹配，但与"aa.a"和"ab*a"均不匹配。

s 可能为空，且只包含从 a-z 的小写字母。
p 可能为空，且只包含从 a-z 的小写字母以及字符 . 和 *，无连续的 '*'。
*/


/*
  思路分析：
  暴力递归：首先，这道题和求子序列、子字符串的题目不同，这个是每一位都比较，而且最终是否符合的标准就是
  匹配字符串p是否能够将给定s字符串匹配完全，所以没必要先将所有的匹配串都给穷举出来，也不实际太多了。
  只需要每次都处理一位，整两个index(sTemp、pTemp)指向s、p的某一位字符，index前的都已经匹配好了，
  如果碰到无法匹配的就直接返回false;

  实践分析：
  1）pTemp指向一个"."，没的说，什么都匹配，直接两个index后移一位
  2）pTemp指向一个字母，那么返回字母sA==字母sB && process(两个index后移一位)
  3）pTemp指向一个"*",这个就是难点了，因为*可以重复前一位0~n次

  难点分析(思维风暴)：
  1）如何实现*重复前一位0~n次？
       解决方法：
       1.1）不知道n是多少次，而且如果都无法匹配那么重复再多次也没用。
            如果无法利用，直接跳过(sTemp不动，pTemp+1(跳过*))
       1.2）如果当前字母可以利用，若是可以将pTemp往回走一位这样它就能再回到*位置了，
            然后或(||)上一个往前走但是没利用字母的(利用0次)，再或上(||)一个往前走利用字母的(利用1次)
            这样就能够实现利用前一位0~n次了
           1.2.1）注意点：当前已经有利用1次，利用0次了，所以下一次必须有利用两次才能跑起来，所以往回走那个分支
           应该是sTemp+1,pTemp不动
       1.3）如果不可以利用，直接跳过*位置走下面匹配

  2）如果某个字母下一位是*，但是当前位字母不匹配，这不能错吧，因为可以重复0次。
  3）如果*前面都没字符，重复啥？直接false掉，这都不合法
  4）总结*位置处理，这个位置不能单看这个位置的，要连着前一位一起看，所以碰到后一位是*的直接pTemp+1，这是一切的
  前提，只有这个位置后一个位置不是*才能走实践分析中1、2点

  base case分析：
  1）如果sTemp来到了s.length()位置，表示没有字符需要匹配了，那么怎么样算正确？怎么样算错误？
  1.1）如果pTemp此时也来到了p.length()位置那么说明p也匹配完毕了，当然正确
  1.2）如果pTemp此时没有结尾，但是后面的元素都是a*a*这样一对一对的，那么都可以消去，所以也正确
  总结：除了这两种其它情况肯定不正确，因为s都匹配完了，结果p还有的剩而且消不掉，那么肯定是匹配不上的。
  2）如果sTemp没到s.length()位置,而pTemp到了p.length()位置，那么说明p没有字符再去匹配s了，自然错误
  不满足这两个base case，说明两个指针都没有到尽头，那么就继续走程序匹配去嘛
*/
public class Code10_RegExpMatch {
    public static boolean isRegMatchStr(String s, String p) {
        if (p == null || s == null) return false;
        if (p.length() == 0 && s.length() != 0) return false;
        return process(s.toCharArray(), p.toCharArray(), 0, 0);
    }

    private static boolean process(char[] sChars, char[] pChars, int sTemp, int pTemp) {
        if(sTemp==sChars.length) return pTemp==pChars.length||noSeeIsCanSkip(pTemp,pChars);
        if(pTemp==pChars.length) return false;
        if (pTemp + 1 < pChars.length && pChars[pTemp + 1] == '*') {
            //直接跑到*位置
            return process(sChars, pChars, sTemp, pTemp + 1);
        } else {
            if (pChars[pTemp] == '.') {
                return process(sChars, pChars, sTemp + 1, pTemp + 1);
            }
            if (pChars[pTemp] == '*') {
                if (pTemp - 1 >= 0) {//上一个位置合法
                    //利用，可以利用才行
                    char frontStar = pChars[pTemp - 1];
                    if (frontStar == '.' || sChars[sTemp] == frontStar) {
                        return process(sChars, pChars, sTemp + 1, pTemp)//为了利用多次
                                ||process(sChars, pChars, sTemp, pTemp+1)
                                ||process(sChars, pChars, sTemp+1, pTemp+1);//利用一次
                    }
                    //无法利用，直接跳过
                    return process(sChars,pChars,sTemp,pTemp+1);
                }
            }
            //字母
            return sChars[sTemp]==pChars[pTemp]&&process(sChars,pChars,sTemp+1,pTemp+1);
        }
    }

    private static boolean noSeeIsCanSkip(int pTemp, char[] pChars) {
        int temp = pChars.length-1;
        boolean isStar = true;
        while (temp>=pTemp){
            if(isStar&&pChars[temp]!='*') return false;
            else isStar = false;
            temp--;
            if(((pChars.length-1)-temp)%2==0) isStar = true;
        }
        return true;
    }

    //for test
    public static String randomS(int maxLen) {
        int len = (int) (Math.random() * maxLen);//0~maxLen-1
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < len; i++) {
            char r = (char) ((int) (Math.random() * 5) + 'a');
            builder.append(r);
        }
        return builder.toString();
    }

    //p 可能为空，且只包含从 a-z 的小写字母以及字符 . 和 *，无连续的 '*'。
    public static String randomP(int maxLen) {
        int len = (int) (Math.random() * maxLen);//0~maxLen-1
        char[] chars = new char[len];
        for (int i = 0; i < len; i++) {
            double dict = Math.random();
            if (dict < 0.33) {
                chars[i] = (char) ((int) (Math.random() * 5) + 'a');//字母
            } else if (dict < 0.66) {
                chars[i] = '.';
            } else {
                if (i - 1 < 0 || chars[i - 1] == '*') {
                    i--;
                    continue;
                }
                chars[i] = '*';
            }
        }
        return String.valueOf(chars);
    }

    //dp01_傻缓存
    public static boolean dp01(String s, String p){
        if (p == null || s == null) return false;
        if (p.length() == 0 && s.length() != 0) return false;
        HashMap<String,Boolean> dp = new HashMap<>();
        return process1(s.toCharArray(), p.toCharArray(), 0, 0,dp);
    }

    private static boolean process1(char[] sChars, char[] pChars, int sTemp, int pTemp, HashMap<String, Boolean> dp) {
        //dp表-->key:sTemp_pTemp value:结果
        String dpKey = sTemp+"_"+pTemp;
        if(dp.containsKey(dpKey)) return dp.get(dpKey);//取缓存
        if(sTemp==sChars.length) {
            dp.put(dpKey,pTemp==pChars.length||noSeeIsCanSkip(pTemp,pChars));
            return dp.get(dpKey);
        }
        if(pTemp==pChars.length) {
            dp.put(dpKey,false);
            return dp.get(dpKey);
        }
        if (pTemp + 1 < pChars.length && pChars[pTemp + 1] == '*') {
            dp.put(dpKey,process1(sChars, pChars, sTemp, pTemp + 1,dp));
            return dp.get(dpKey);
        } else {
            if (pChars[pTemp] == '.') {
                dp.put(dpKey,process1(sChars, pChars, sTemp + 1, pTemp + 1,dp));
                return dp.get(dpKey);
            }
            if (pChars[pTemp] == '*') {
                if (pTemp - 1 >= 0) {
                    char frontStar = pChars[pTemp - 1];
                    if (frontStar == '.' || sChars[sTemp] == frontStar) {
                        dp.put(dpKey,process1(sChars, pChars, sTemp + 1, pTemp,dp)
                                ||process1(sChars, pChars, sTemp, pTemp+1,dp)
                                ||process1(sChars, pChars, sTemp+1, pTemp+1,dp));
                        return dp.get(dpKey);
                    }
                    dp.put(dpKey,process1(sChars,pChars,sTemp,pTemp+1,dp));
                    return dp.get(dpKey);
                }
            }
            dp.put(dpKey,sChars[sTemp]==pChars[pTemp]&&process1(sChars,pChars,sTemp+1,pTemp+1,dp));
            return dp.get(dpKey);
        }
    }
    //dp02_经典dp
    public static boolean dp02(String s,String p){
        if (p == null || s == null) return false;
        if (p.length() == 0 && s.length() != 0) return false;
        char[] pChars = p.toCharArray();
        char[] sChars = s.toCharArray();

        boolean[][] dp = new boolean[s.length()+1][p.length()+1];
        //base case
        for(int pTemp = 0;pTemp <= pChars.length;pTemp++){
            dp[sChars.length][pTemp] = pTemp==pChars.length||noSeeIsCanSkip(pTemp,pChars);
        }
        //从右到左,从下到上,填表
        for(int pTemp=pChars.length-1;pTemp>=0;pTemp--){
            for(int sTemp = sChars.length-1;sTemp>=0;sTemp--){
                if (pTemp + 1 < pChars.length && pChars[pTemp + 1] == '*') {
                    dp[sTemp][pTemp] = dp[sTemp][pTemp + 1];
                } else {
                    if (pChars[pTemp] == '.') {
                        dp[sTemp][pTemp] = dp[sTemp + 1][pTemp + 1];
                    }else if (pChars[pTemp] == '*'&&pTemp - 1 >= 0) {
                            char frontStar = pChars[pTemp - 1];
                            if (frontStar == '.' || sChars[sTemp] == frontStar) {
                                dp[sTemp][pTemp] = dp[sTemp + 1][pTemp]||dp[sTemp][pTemp+1]||dp[sTemp+1][pTemp+1];
                            }else{
                                dp[sTemp][pTemp] = dp[sTemp][pTemp+1];
                            }
                    }else
                        dp[sTemp][pTemp] = sChars[sTemp]==pChars[pTemp]&&dp[sTemp+1][pTemp+1];
                }
            }
        }
        return dp[0][0];
    }
    public static void main(String[] args) {
        int maxLen = 10,testTimes=1000000;
        int i = 0;
        for(;i<testTimes;i++){
            String s = randomS(maxLen);
            String p = randomP(maxLen);
            boolean regMatchForce = isRegMatchStr(s, p);
            boolean dpRes = dp02(s, p);
            if(regMatchForce != dpRes){
                System.out.println("s:"+s+",p:"+p);
                System.out.println("regMatchForce:"+regMatchForce+",dpRes:"+dpRes);
                break;
            }
        }
        System.out.println(i==testTimes?"finish!!!":"fucking");
    }
}
