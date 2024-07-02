package waigo.zuoshen.advance.MyCode;

/**
 * author waigo
 * create 2021-03-25 13:22
 */
/*
矩形以列表 [x1, y1, x2, y2] 的形式表示，其中 (x1, y1) 为左下角的坐标，(x2, y2) 是右上角的坐标。矩形的上下边平行于 x 轴，
左右边平行于 y 轴。如果相交的面积为 正 ，则称两矩形重叠。需要明确的是，只在角或边接触的两个矩形不构成重叠。
给出两个矩形 rec1 和 rec2 。如果它们重叠，返回 true；否则，返回 false 。
*/
public class Code04_RectangleOverLap {
    public static boolean isRectangleOverlap(int[] rec1, int[] rec2) {
        //没注意题给条件，这矩形可能面积为0的
        if(Math.abs((rec1[2]-rec1[0])*(rec1[3]-rec1[1]))==0||Math.abs((rec2[2]-rec2[0])*(rec2[3]-rec2[1]))==0) return false;
        if(rec1[1]>=rec2[3]||rec2[1]>=rec1[3]) return false;//底比人家的顶还要高，不可能重叠了
        if(rec1[0]>=rec2[2]||rec2[0]>=rec1[2]) return false;//左边比人家的右边还要远，不可能重叠了
        return true;
    }


    public static void main(String[] args) {
        System.out.println(isRectangleOverlap(new int[]{0,0,1,1},new int[]{1,0,2,1}));
    }
}
