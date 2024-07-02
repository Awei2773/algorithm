package waigo.zuosheng.advanced.MyCode;

/**
 * author waigo
 * create 2021-07-10 0:19
 */

/**
 * 汉诺塔游戏的要求把所有的圆盘从左边都移到右边的柱子上，给定一个整型数组arr，
 * 其中只含有1、2和3，代表所有圆盘目前的状态，1代表左柱，2代表中柱，3代表右柱，
 * arr[i]的值代表第i+1个圆盘的位置。 比如，arr=[3,3,2,1]，代表第1个圆盘在右柱上、
 * 第2个圆盘在右柱上、第3个圆盘在中 柱上、第4个圆盘在左柱上 如果arr代表的状态是最优移动轨迹过程
 * 中出现的状态，返回arr这种状态是最优移动轨 迹中的第几个状态;如果arr代表的状态不是最优移动轨迹过程
 * 中出现的状态，则返回- 1。
 */
public class Code02_HanoiProblem {
    //汉诺塔游戏最优解下，N个碟子的游戏要走2^N-1步
    //游戏分为三步
    //f(N-1,from,to,other),第N个圆盘如果在from则表示第一步没走完
    //N-->to
    //f(N-1,other,from,to),第N个圆盘如果在to表示第二步走完了,第三步不知道走了多少
    //第N个圆盘如果出现在other表示当前不是最优移动,返回-1
    public static int perfectStep(int[] arr) {
        if (arr == null || arr.length == 0) return -1;
        return hanoi(arr.length - 1, 1, 2, 3, arr);
    }
    //[0....i]
    //玩N层汉诺塔游戏，当前arr状态是最优走法下的第几步
    public static int hanoi(int i, int from, int other, int to, int[] arr) {
        if(i==-1){
            return 0;
        }
        if (arr[i] == other) return -1;
        if (arr[i] != to) {//第一步没走完
            return hanoi(i - 1,from, to, other, arr);
        }
        int rest = hanoi(i - 1, other, from, to, arr);

        return (rest == -1 ? 0 : (1 << i)) + rest;
    }

    public static void main(String[] args) {
        int[] arr = { 3, 3, 2, 1,2,1 };
        System.out.println(perfectStep(arr));

    }

}
