package waigo.zuoshen.advance.MyCode;

import java.util.Arrays;

/**
 * author waigo
 * create 2021-03-28 13:04
 */
//给定一个区间[L,R]，问这里面的数中奇号位和大于偶号位和的数字个数
    //找不到优解
public class Code10_OddSumEqlEvenSumCount {
    public static int getOddSumEqlEvenSumCount(int L,int R){
        int count = 0;
        for(int curNum = L;curNum<=R;curNum++){
            if(oddSumEqlEvenSum(curNum)){
                count++;
            }
        }
        return count;
    }

    private static boolean oddSumEqlEvenSum(int curNum) {
        return getOddSum(curNum)==getEvenSum(curNum);
    }

    private static int getOddSum(int curNum) {
        String num = curNum+"";
        int sum = 0;
        for(int i = 0;i<num.length();i+=2){
            sum+=Integer.valueOf(num.substring(i,i+1));
        }
        return sum;
    }

    private static int getEvenSum(int curNum) {
        String num = curNum+"";
        int sum = 0;
        for(int i = 1;i<num.length();i+=2){
            sum+=Integer.valueOf(num.substring(i,i+1));
        }
        return sum;
    }
    private static int[] generalRandomLR(int maxVal){
        int num1 = (int) (Math.random()*maxVal);
        int num2 = (int) (Math.random()*maxVal);
        return new int[]{Math.min(num1,num2),Math.max(num1,num2)};
    }
    public static void main(String[] args) {

//        int testTime = 1000,maxVal = 1000;
//        int i = 0;
//        for(;i<testTime;i++){
//            int[] LR = generalRandomLR(maxVal);
//            int res1 = getOddSumEqlEvenSumCount(LR[0], LR[1]);
//            int res2 = getOddSumEqlEvenSumCount1(LR[0], LR[1]);
//            if(res1 != res2){
//                System.out.println(Arrays.toString(LR));
//                System.out.println("res1:"+res1);
//                System.out.println("res2:"+res2);
//                break;
//            }
//        }

//        System.out.println(count);
//        System.out.println(getOddSumEqlEvenSumCount(0,10000000));
//        System.out.println(i==testTime?"finish!!!":"fuck!!!");
        //肯定得整除11
        int pre = 0;
        for(int i = 0;i<10000;i+=11){
            System.out.println("[0,"+i+"]:"+getOddSumEqlEvenSumCount(0,i));
        }
    }
}
