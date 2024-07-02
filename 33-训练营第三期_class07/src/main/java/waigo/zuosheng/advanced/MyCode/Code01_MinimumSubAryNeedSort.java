package waigo.zuosheng.advanced.MyCode;

/**
 * author waigo
 * create 2021-05-29 8:24
 */

/**
 * 给定一个无序数组arr，如果只能再一个子数组上排序
 * 返回如果让arr整体有序，需要排序的最短子数组长度
 */
public class Code01_MinimumSubAryNeedSort {
    public static int minimumNeedSort(int[] arr){
        int N = arr.length;
        if(arr==null|| N <2) return 0;
        int up=-1,down=0;
        int leftMax = arr[0];
        for (int i = 1; i < N; i++) {
            if(arr[i]<leftMax){
                up = i;
            }
            leftMax = Math.max(leftMax,arr[i]);
        }
        int rightMin = arr[N-1];
        for (int i = N -2; i >=0 ; i--) {
            if(arr[i]>rightMin){
                down = i;
            }
            rightMin = Math.min(rightMin,arr[i]);
        }
        return up-down+1;
    }

    public static void main(String[] args) {
        System.out.println(minimumNeedSort(new int[]{1,2,5,4,2,3,6,7}));
    }
}
