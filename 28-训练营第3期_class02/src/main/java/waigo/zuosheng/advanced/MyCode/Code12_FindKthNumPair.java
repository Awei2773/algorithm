package waigo.zuosheng.advanced.MyCode;

import java.util.Arrays;

/**
 * author waigo
 * create 2021-05-11 8:19
 */
/*
长度为N的数组arr，一定可以组成N^2个数值对。
例如arr = [3,1,2]，
数值对有(3,3) (3,1) (3,2) (1,3) (1,1) (1,2) (2,3) (2,1) (2,2)，
也就是任意两个数都有数值对，而且自己和自己也算数值对。
数值对怎么排序？规定，第一维数据从小到大，第一维数据一样的，第二维数组也从小到大。所以上面的数值对排序的结果为：
(1,1)(1,2)(1,3)(2,1)(2,2)(2,3)(3,1)(3,2)(3,3)

给定一个数组arr，和整数k，返回第k小的数值对。
*/
public class Code12_FindKthNumPair {
    public static int[] findKthNumPair(int[] arr, int k) {
        if(k<=0) return new int[]{};
        //第一个数是排第几的
        int N = arr.length;
        int k1 = k / N + (k % N == 0 ? 0 : 1);
        int k1Num = findKthNum(arr, k1);//找到第一个数
        int lessK1Sum = 0;//小于第一个数的有几个
        int eqlK1Sum = 0;//等于第一个数的有几个
        for (int i = 0; i < arr.length; i++) {
            if(arr[i]<k1Num) lessK1Sum++;
            else if(arr[i]==k1Num) eqlK1Sum++;
        }
        //以k1Num的第一个数值对开始数数到第几个，更新k,淘汰掉小于k1Num组成的数值对
        k-=lessK1Sum*N;
        //第二个数是排第几的
        int k2 = k/eqlK1Sum+(k%eqlK1Sum==0?0:1);
        return new int[]{k1Num,findKthNum(arr,k2)};
    }
    //通过partition来找第kth的值
    public static int findKthNum(int[] arr,int k){
        return process(arr, 0, arr.length - 1, k - 1);
    }

    //返回arr数组l...r区间上排好序之后，idx位置的值
    private static int process(int[] arr, int l, int r, int idx) {
        if(l==r) return arr[l];
        //随机pivot
        int rIdx = ((int) (Math.random()*(r-l+1)))+l;
        swap(arr,r,rIdx);
        int[] flags = partition(arr,l,r);
        if(idx<flags[0]){
            //在小区
            return process(arr,l,flags[0]-1,idx);
        }else if(idx>flags[1]){
            //在大区
            return process(arr,flags[1]+1,r,idx);
        }
        return arr[idx];//在pivot这里
    }

    //在l...r上玩partition，返回lowTop+1和highLow-1,就是等于pivot那个区间的上下界
    private static int[] partition(int[] arr, int l, int r) {
        int pivot = arr[r];
        int lowTop = l-1;
        int highLow = r;
        for(int j = l;j<highLow;j++){
            if(arr[j]<pivot){
                swap(arr,++lowTop,j);//进小区
            }else if(arr[j]>pivot){
                swap(arr,--highLow,j);
                j--;//停在原地，上面换下来的还没看过
            }
        }
        swap(arr,r,highLow++);//pivot换下来
        return new int[]{lowTop+1,highLow-1};
    }

    private static void swap(int[] arr, int aIdx, int bIdx) {
        if(aIdx!=bIdx){
            arr[aIdx]^=arr[bIdx];
            arr[bIdx]^=arr[aIdx];
            arr[aIdx]^=arr[bIdx];
        }
    }


    public static class Pair{
        int sum1;
        int sum2;

        public Pair(int sum1, int sum2) {
            this.sum1 = sum1;
            this.sum2 = sum2;
        }

        @Override
        public String toString() {
            return "["  + sum1 + "," + sum2 + ']';
        }
    }
    //For test
    public static int[] findKthNumPair2(int[] arr, int k) {
        if(k<=0) return new int[]{};
        int N = arr.length;
        Pair[] pairs = new Pair[N*N];
        int count = 0;
        for(int i = 0;i<N;i++){
            for(int j = 0;j<N;j++){
                pairs[count++] = new Pair(arr[i],arr[j]);
            }
        }
        Arrays.sort(pairs,(a,b)->{
            if(a.sum1-b.sum1<0){
                return -1;
            }else if(a.sum1-b.sum1==0){
                return a.sum2-b.sum2;
            }else{
                return 1;
            }
        });
        Pair target = pairs[k-1];
        return new int[]{target.sum1,target.sum2};
    }
    //for test
    public static int[] generalRandomAry(int maxSize,int maxVal){
        int size = (int) (Math.random()*maxSize)+1;
        int[] res = new int[size];
        for (int i = 0; i < size; i++) {
            res[i] = (int) (Math.random()*maxVal)-(int) (Math.random()*maxVal);
        }
        return res;
    }
    public static void main(String[] args) {
        int maxVal = 1000;
        int maxSize = 100;
        int testTimes = 10000;
        int i = 0;
        for (; i <testTimes; i++) {
            int[] nums = generalRandomAry(maxSize, maxVal);
            int[] newNums = Arrays.copyOf(nums, nums.length);
            int k = (int) (Math.random()*(nums.length*nums.length));
            int[] res1 = findKthNumPair(nums, k);
            int[] res2 = findKthNumPair2(newNums, k);
            if(!Arrays.equals(res1,res2)){
                System.out.println("k:"+k);
                System.out.println("res1:"+Arrays.toString(res1));
                System.out.println("res2:"+Arrays.toString(res2));
                System.out.println(Arrays.toString(nums));
                System.out.println(Arrays.toString(newNums));
                break;
            }
        }
        System.out.println(i==testTimes?"finish!!!!":"fucking!!!");


    }
}
