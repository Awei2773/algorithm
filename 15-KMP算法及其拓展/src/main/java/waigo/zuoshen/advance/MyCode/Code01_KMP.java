package waigo.zuoshen.advance.MyCode;

/**
 * author waigo
 * create 2021-03-13 9:51
 */
public class Code01_KMP {
    public static int getIndexOf(String s, String m) {
        if (s == null || m == null || s.length() < m.length()) return -1;
        if (s.length() == 0 || m.length() == 0) return 0;
        //N>=M&&N!=0&&M!=0才走下面的
        char[] str = s.toCharArray();
        char[] match = m.toCharArray();
        //maxPrePosLen信息就存在这个数组中i位置的值就对应nexts下标i的值
        int[] nexts = getNextArray(match);
        int x = 0, y = 0;//x是str的工作指针，y是match的
        //为什么循环条件这么写，因为x,y都是从0位置往后匹配的，匹配不上则y回跳
        //匹配上则x前走，不管匹配得上还是匹配不上，最终总有至少有一个会到length
        while (x < str.length && y < match.length) {
            if (str[x] == match[y]) {
                x++;
                y++;//匹配上了，都后移，非常合理
            } else if (nexts[y] >= 0) {
                //x不动，y移动到最长前缀的后一个位置，假如最长前缀长度为j，那么0~j-1就是最长前缀，所以j就是现在y应该去的地方，这也是为什么maxPrePosLen数组叫做next数组，代表了y下一跳去的地方
                y = nexts[y];
            } else {
                //说明str指针从x-j位置到x位置开始一个都匹配不上，那么x以及x前的位置淘汰，x++，很合理
                x++;
                Object o = new Object();
            }
        }
        /*
        分析：
        1.x==str.length&&y==match.length,那么说明x从str.length-match.length位置开始匹配上了match
        2.x==str.length&&y!=match.length,那么说明str都走完了，所有位置都淘汰了还是匹配不上match，返回-1
        3.x!=str.length&&y==match.length,说明str中间某个串就匹配上了match，这个开始位置是x-match.length
         */
        return y == match.length ? x - match.length : -1;//结合所有情况，合理
    }

    public static int[] getNextArray(char[] match) {
        if (match.length == 1) return new int[]{-1};
        int[] nexts = new int[match.length];
        nexts[0] = -1;
        nexts[1] = 0;
        int cur = 2;
        //这代表的是cur前一个位置的next的值也代表需要和cur比对的match中的下标,理解这个很重要
        int preNext = 0;
        while (cur < match.length) {
            if (match[cur - 1] == match[preNext]) {
                //每个cur位置都是要看上一个位置的next的，上个位置设置的时候next = preNext+1,所以就干脆直接修改preNext为preNext+1了
                nexts[cur++] = ++preNext;
            } else if (preNext>0) {//nexts[preNext]>=0同于preNext>0,因为只有0位置的next为-1
                //不符合还能往前跳就继续往前跳
                preNext = nexts[preNext];
            } else {
                nexts[cur++] = 0;
            }
        }
        return nexts;
    }

    public static String[] getRandomStr(int maxLen) {
        int len = (int) (Math.random() * maxLen);
        char[] chars = new char[len];
        for (int i = 0; i < len; i++) {
            chars[i] = (char) ((int) (Math.random() * 26) + 'a');
        }
        String str = String.valueOf(chars);
        String match;
        double decide = Math.random();
        if (decide < 0.5) {
            int i1 = (int) (Math.random() * len);
            int i2 = (int) (Math.random() * len);
            match = str.substring(Math.min(i1, i2), Math.max(i1, i2));
        } else {
            match = "1234".substring(0, Math.min(4, len));
        }
        return new String[]{str, match};
    }

    public static void main(String[] args) {
        /*int maxLen = 10,testTimes = 5000000;
        int i = 0;
        for(;i < testTimes;i++){
            String[] randomStr = getRandomStr(maxLen);
            int truth = randomStr[0].indexOf(randomStr[1]);
            int myRes = getIndexOf(randomStr[0],randomStr[1]);
            if(truth!=myRes){
                System.out.println("myRes:"+myRes);
                System.out.println("truth:"+truth);
                System.out.println("str:"+randomStr[0]+"|match:"+randomStr[1]);
                break;
            }
        }
        System.out.println(i==testTimes?"finish!!!":"fucking!!!");*/
        ClassLoader classLoader = Code01_KMP.class.getClassLoader();
    }
}
