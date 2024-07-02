package waigo.zuoshen.advance.MyCode;

/**
 * author waigo
 * create 2021-03-18 16:02
 */
/*
先导知识：
#1#2#1#
回文半径：表示从中心点2开始往左或往右数到边界，这里回文半径是4
回文直径：表示这个回文串的长度
回文长度：表示去掉添加的辅助字符#之后的长度
求法：
1.回文半径减一
2.回文直径除以2

Manacher算法核心
1）理解回文半径数组
2）理解所有中心的回文最右边界R，和取得R时的中心点C
3）理解   L…(i`)…C…(i)…R  的结构，以及根据i’回文长度进行的状况划分
4）每一种情况划分，都可以加速求解i回文半径的过程

解决问题：
假设字符串str长度为N，想返回最长回文子串的长度
时间复杂度O(N)
*/
public class Code01_Manacher {
    public static int forceMatch(String str) {
        //回文子串其实就是用所有的实轴和虚轴去往外扩，扩到不能扩的位置，最终找到最长的回文串
        //为了操作方便，需要将虚轴用字符填充好，这样就能够统一处理了
        char[] resArr = strToResolveArr(str);
        int maxPLen = Integer.MIN_VALUE;
        for (int i = 0; i < resArr.length; i++) {
            //匹配中最后肯定是以#结尾，因为虚轴都是相等的，所以匹配的回文最长肯定长类似这样#1#2#1#
            //如果你记录的是回文直径(7)，那么回文长度就是(7/2=3)
            //如果记录的是回文半径(4),那么回文长度就是(4-1=3)
            int pRad = 1;//回文长度，最少包括自己，所以初始化为1
            int step = 1;
            while (i - step >= 0 && i + step < resArr.length && resArr[i + step] == resArr[i - step]) {
                pRad++;
                step++;
            }
            maxPLen = Math.max(maxPLen, pRad - 1);
        }
        return maxPLen;
        //时间复杂度分析：
        //最差情况：一个整体直接回文的#1#1#1#1#1#1#1#
        //第一个for循环执行2N+1次，第二个while循环执行2N+1次，因为所有位置都扩到了
        //所以时间复杂度O((2N+1)*(2N+1)) = O(N^2),较差
        //差的原因是因为所有的点都是一个孤岛，没有利用前人的经验，全是自己去摸索
        //而Manacher算法就是去解决这个问题，利用已经求过点的答案来加速现在要求的点
    }

    //123->#1#2#3#->长度变成2N+1
    private static char[] strToResolveArr(String str) {
        char[] res = new char[2 * str.length() + 1];
        char[] strs = str.toCharArray();
        for (int i = 0, j = 0; i < res.length; i++) {
            res[i] = (i & 1) == 0 ? '#' : strs[j++];//以后判断是否为偶数可以使用&1来判断，因为偶数的0号位肯定是0
        }
        return res;
    }

    /*
        时间复杂度分析：
        因为i和R都是向右不会停下来的而且最大值都是N
        可以看所有的分支
        如果是需要扩的分支，那么R就会右走，i在R走完之后会++
        如果不需要扩的则R不动，i++
        那么他们最多就是i加到N，然后R也走到N，最多就是2N次嘛，那么时间复杂度是O(N)
    */
    public static int manacher(String str) {
        char[] mArr = strToResolveArr(str);//拿到manacher操作串
        int[] pRadArr = new int[mArr.length];//回文半径数组
        int c = -1;//中心点和右边界，左闭右开，R是）
        int R = -1;
        int max = Integer.MIN_VALUE;
        //manacher算法和KMP类似，就是利用前面位置已经求过的回文的区域来加速当前需要求位置的回文长度
        for (int i = 0; i < pRadArr.length; i++) {
            if (i > R) {
                //如果当前位置不在最大边界里面，没有优化，暴力扩去
                idxFceExpand(mArr, pRadArr, i, 1);
            } else {
                int i1 = 2 * c - i;//i关于c的对称点
                int L = 2 * c - R;
                int i1L = i1 - pRadArr[i1] + 1;
                //1.如果i1的回文区域完全处于c,R产生的边界区域中
                if (i1L > L) {
                    //直接得出答案，因为i1区域左边界再左一个和右边界再右一个不等，而i那边正好和i1这边关于c是对称的，所以i的边界区域等于i1的
                    pRadArr[i] = pRadArr[i1];//最小回文半径为pRadArr[i1]<R-i
                } else if (i1L == L) {
                    //2.如果i1的回文区域左边界压住c,R产生的左边界
                    //最小为pRadArr[i1]，需要继续扩，因为L再左一个(i1L-1)和R再右一个(R+1)不等，而i1的右边界再右一个(i1R+1)和i此时能扩到的最小区域的左边界的再左一个(iL-1)是相同的
                    //所以无法判断(iL+1)和(R+1)是否相等还是不等
                    idxFceExpand(mArr, pRadArr, i, pRadArr[i1]);//最小回文半径为R-i==pRadArr[i1]
                } else {
                    //3.如果i1的回文区域超出了左边界,不用算i直接就为i1到L的长度
                    pRadArr[i] = i1 - L + 1;////最小回文半径为R-i<pRadArr[i1]
                }
            }
            int iR = pRadArr[i] + i - 1;
            if (iR > R) {//边界扩张了就更新
                R = iR;
                c = i;
            }
            max = Math.max(pRadArr[i] - 1, max);
        }
        return max;
    }

    //代码整合优化版本，算法性能方面完全没有优化，只是将向外扩的代码整合到一起，就是全部位置都统一处理
    public static int manacher1(String str) {
        char[] mStr = strToResolveArr(str);
        int C = -1;
        int R = -1;//右边界的下一个位置，为了回文半径等于R-C
        int[] pRadArr = new int[mStr.length];//回文半径数组
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < pRadArr.length; i++) {
            //反正那些加速的位置再比对下一个位置肯定失败，那么就设置他们最小的回文半径，然后就让它们扩去
            //1.如果是暴力扩那种，那就是一个位置不用看
            //2.三个分支集中分析得出Math.min(pRadArr[2*C-i],R-i)
            pRadArr[i] = R > i ? Math.min(pRadArr[2 * C - i], R - i) : 1;
            //开始扩pRadArr[i]不但代表了回文半径，还代表了如果现在想要扩的话，应该i+-的步长
            while (i - pRadArr[i] >= 0 && i + pRadArr[i] < mStr.length && mStr[i - pRadArr[i]] == mStr[i + pRadArr[i]]) {
                pRadArr[i]++;
            }
            //是否更新边界
            if (pRadArr[i] + i > R) {
                R = pRadArr[i] + i;
                C = i;
            }
            max = Math.max(max, pRadArr[i] - 1);
        }
        return max;
    }

    private static void idxFceExpand(char[] mArr, int[] pRadArr, int i, int initialStep) {
        pRadArr[i] = initialStep;//初始肯定包括自己
        while (i - pRadArr[i] >= 0 && i + pRadArr[i] < mArr.length && mArr[i - pRadArr[i]] == mArr[i + pRadArr[i]]) {
            pRadArr[i]++;
        }
    }

    public static String generalRandomStr(int maxLen) {
        int len = (int) (Math.random() * maxLen) + 1;
        char[] chars = new char[len];
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) ((int) (Math.random() * 3) + 'a');
        }
        return String.valueOf(chars);
    }

    public static int getClosestPalindromeNum(int num) {
        int step = 1;
        String leftNum, rightNum;
        boolean left = true;
        while (true) {
            int leftInt = num - step;
            leftNum = leftInt + "";
            int rightInt = num + step;
            rightNum = rightInt + "";
            if (leftInt>=0&&manacher1(leftNum) == leftNum.length()) break;
            if (rightInt>=0&&manacher1(rightNum) == rightNum.length()) {
                left = false;
                break;
            }
            step++;
        }
        return left?Integer.valueOf(leftNum):Integer.valueOf(rightNum);
    }

    public static void main(String[] args) {
        /*int maxLen = 10, testTimes = 1000000;
        int i = 0;
        for (; i < testTimes; i++) {
            String str = generalRandomStr(maxLen);
            int forRes = forceMatch(str);
            int manacherRes = manacher(str);
            if (forRes != manacherRes || forRes != manacher1(str)) break;
        }
        System.out.println(i == testTimes ? "finish!!!" : "fucking");*/
        System.out.println(getClosestPalindromeNum(-10));
    }

}
