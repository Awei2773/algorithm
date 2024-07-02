package waigo.zuoshen.advance.MyCode;

/**
 * author waigo
 * create 2021-03-19 7:55
 */
/*
给定一个字符串 s，你可以通过在字符串前面添加字符将其转换为回文串。找到并返回可以用这种方式转换的最短回文串。
输入：s = "aacecaaa"
输出："aaacecaaa"

解题思路：
找到左边界为0的尽可能右的右边界
*/
public class Code02_AddShortestEnd {


    public static String shortestPalindrome(String s) {
        if (s == null || s.length() < 2) return s;
        char[] mArr = strToManacherArr(s);
        int C = mArr.length;
        int L = mArr.length;//左边界下一个位置
        int[] pRadArr = new int[mArr.length];//回文半径数组
        int mostContainPal = 0;//以0位置为开始的最长回文半径
        //将Manacher算法修改，变成从右向左进行处理，找到第一个使得左边界达到0的点
        for (int i = pRadArr.length - 1; i >= 0; i--) {
            pRadArr[i] = L<i ? Math.min(pRadArr[2 * C - i], i-L) : 1;//边界和i的距离需要注意一下，如果是右边界那就是R-i，左边界是i-L
            while (i - pRadArr[i] >= 0 && i + pRadArr[i] < mArr.length && mArr[i - pRadArr[i]] == mArr[i + pRadArr[i]]) {
                pRadArr[i]++;
            }
            if (i-pRadArr[i] < L) {
                L = i-pRadArr[i];
                C = i;
            }
            if (L == -1) {
                mostContainPal = i;//观察法，实轴虚轴都是i正好代表不用逆序的长度
                break;
            }
        }
        char[] res = new char[s.length() - mostContainPal];
        for (int i = 0; i < res.length; i++) {
            res[i] = mArr[mArr.length - 2 - i * 2];
        }
        return String.valueOf(res) + s;
    }

    public static char[] strToManacherArr(String str) {
        char[] res = new char[2 * str.length() + 1];
        char[] strChars = str.toCharArray();
        int j = 0;
        for (int i = 0; i < res.length; i++) {
            res[i] = (i & 1) == 0 ? '#' : strChars[j++];
        }
        return res;
    }

    public static void main(String[] args) {
//        System.out.println(String.valueOf(strToManacherArr("123", "123".toCharArray())));
        System.out.println(shortestPalindrome("ababbbabbaba"));//#a#a#c#e#c#a、
        //"abbacd"->#a#b#b#a#c#d
    }
}
