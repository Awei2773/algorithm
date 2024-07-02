package waigo.zuosheng.advanced.MyCode;

/**
 * author waigo
 * create 2021-05-30 11:15
 */

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * 给定两个数组arrx和arry，长度都为N。代表二维平面上有N个点，第i个点的x 坐标和y坐标分别为arrx[i]和arry[i]，
 * 返回求一条直线最多能穿过多少个点?
 * <p>
 * 思路：枚举所有直线的可能，把每条线的答案都抓出来最后答案必在其中
 * 一条线的决定因素就是它的斜率，而枚举这条线上穿过的点则是
 * 1.两点所连直线的斜率和这条线的斜率相同
 * 2.两点重合且为这条线上的某个点
 * <p>
 * 将二元问题降元考虑，肯定先是定住一个点，只要这个点定住了，那么穿过它的所有直线的答案必能抓全
 * 斜率如何做哈希表的key？除法肯定会出现精度问题，所以需要避免除法，最好的方法就是斜率只存最简分数
 * 为了算最简分数，那么分子和分母得同时除以gcd(分子,分母)
 * <p>
 * 注意：只依靠斜率无法固定一条直线，所以每个点都要求一遍它伸出去的线，不用往前看，如果是一条线那么前面
 * 肯定收集过答案了
 * 如果是一个重合的点就不用求它的答案了，在前面和它重合的那个点肯定求过了
 */
public class Code07_MaxThroughPointsNum {
    public static class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static int getMaxThroughPointsNum(int[] arrx, int[] arry) {
        if (arrx == null || arry == null || arrx.length != arry.length || arrx.length < 1) return 0;
        int N = arrx.length;
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            points[i] = new Point(arrx[i], arry[i]);
        }
        return process(points);
    }
    public static int maxPoints(int[][] points) {
        if(points==null||points.length<1) return 0;
        int N = points.length;
        Point[] pointsAry = new Point[N];
        for (int i = 0; i < N; i++) {
            pointsAry[i] = new Point(points[i][0], points[i][1]);
        }
        return process(pointsAry);
    }
    private static int process(Point[] points) {
        int res = 0;
        Map<String, Integer> lineThrough = new HashMap<>();//注意：这里存的都是线上不同点，没有重合的
        int N = points.length;
        for (int point = 0; point < N; point++) {
            lineThrough.clear();//上一个点搞定了，现在看下一个点了
            int samePoint = 1;//不用重合，就看自己就有一个点了
            int maxLineThrough = 0;
            for (int next = point+1; next < N; next++) {
                int y = points[next].y-points[point].y;
                int x = points[next].x-points[point].x;
                if(y==0&&x==0){
                    samePoint++;
                }else{
                    String theLine = formatFraction(y, x);
                    lineThrough.put(theLine,lineThrough.getOrDefault(theLine,0)+1);
                    maxLineThrough = Math.max(maxLineThrough,lineThrough.get(theLine));
                }
            }
            res = Math.max(res,samePoint+maxLineThrough);
        }
        return res;
    }

    //进来的时候son和mum不能同时为0，那时重合的答案，自己外面处理去
    private static String formatFraction(int son, int mum) {
        if (son == 0) {
            return "0";
        }
        if (mum == 0) {
            return "~";
        }
        int theyGcd = gcd(son, mum);
        son /= theyGcd;
        mum /= theyGcd;
        if (son < 0 && mum < 0) {
            son = -son;
            mum = -mum;
        }
        if (mum < 0) {//能进这里说明son肯定大于0
            son = -son;
            mum = -mum;
        }
        return son +"_"+mum;
    }

    public static int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }

    public static void main(String[] args) {
        int[] arrx = {1,3,5,4,2,1};
        int[] arry = {1,2,3,1,3,4};
        int[][] points = {{1,1},{3,2},{5,3},{4,1},{2,3},{1,4}};
//        System.out.println(getMaxThroughPointsNum(arrx,arry));
        System.out.println(maxPoints(points));
    }
}
