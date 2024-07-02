package waigo.zuoshen.advanced;

/**
 * author waigo
 * create 2021-07-19 10:00
 */

/**
 * 给定一个有序数组arr（有正有负），从左到右依次表示X轴上从左往右点的位置
 * 给定一个正整数K，返回如果有一根长度为K的绳子，最多能盖住几个点
 * 绳子的边缘点碰到X轴上的点，也算盖住，所以一条长度为2的绳子能够cover住[1,2,3]
 */
public class Code01_CordCoverMaxPoint {
    //1.枚举法，绳子以某个位置结尾的情况下最多盖住多少个点，那就是找mostLeft(>=(i-k))的最左侧位置，然后i-mostLeft+1就是盖住的点数
    public static int maxCover(int[] arr,int K){
        if(arr==null||arr.length==0) return 0;
        int max = 0;
        for(int i = 0;i<arr.length;i++){
            int mostLIdx = mostLeft(arr,arr[i]-K);
            max = Math.max(max,i-mostLIdx+1);
        }
        return max;
    }
    //找不到返回0
    private static int mostLeft(int[] arr, int num) {
        int res = 0;
        int L = 0,R = arr.length-1;
        while(L<=R){
            int mid = (L+R)>>1;
            if(arr[mid]>=num){
                R = mid-1;
                res = mid;
            }else{
                L = mid+1;
            }
        }
        return res;
    }

}
