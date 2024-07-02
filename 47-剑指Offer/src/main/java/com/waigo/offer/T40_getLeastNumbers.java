package com.waigo.offer;

import java.util.Arrays;

/**
 * author waigo
 * create 2021-09-02 1:06
 */
public class T40_getLeastNumbers {
    public int[] getLeastNumbers(int[] arr, int k) {
        if(arr==null||arr.length<=k) return arr;
        if(k==0) return new int[]{};
        bfprt(arr,0,arr.length-1,k-1);
        int[] res = new int[k];
        for(int i = 0;i<k;i++){
            res[i] = arr[i];
        }
        return res;
    }
    //思路：
    //1.将求取范围L...R的元素拆成5个一份求个中位数出来做pivot : getMedium
    //2.根据这个pivot做partition
    //3.idx处于中间区就返回，否则就向左或者向右找
    //潜规则：idx一定在L...R之间
    public int bfprt(int[] arr,int L,int R,int idx){
        if(L==R) return arr[idx];
        int pivot = getPivot(arr,L,R);
        int[] flags = partition(arr,L,R,pivot);//0:小区顶，1:大区底
        if(idx<=flags[0]){
            return bfprt(arr,L,flags[0],idx);
        }else if(idx>=flags[1]){
            return bfprt(arr,flags[1],R,idx);
        }
        return arr[idx];
    }
    //partition相关
    public int[] partition(int[] arr,int L,int R,int pivot){
        int lTop = L - 1;
        int rLow = R + 1;
        for(int j = L;j<rLow;j++){
            if(arr[j]<pivot){
                swap(arr,++lTop,j);
            }else if(arr[j]>pivot){
                swap(arr,--rLow,j--);
            }
        }
        return new int[]{lTop,rLow};
    }
    public void swap(int[] arr,int a,int b){
        if(a!=b){
            arr[a]^=arr[b];
            arr[b]^=arr[a];
            arr[a]^=arr[b];
        }
    }
    //getPivot相关
    private static final int FREGMENT_LEN = 5;//5个一小段
    public int getPivot(int[] arr,int L,int R){
        int N = R - L + 1;
        int mArrSize = N/FREGMENT_LEN + (N%FREGMENT_LEN==0?0:1);
        //第一段：L~L+4
        int[] mArr = new int[mArrSize];
        for(int i = 0;i<mArrSize;i++){
            //获取小段排序后的中点值
            int fregFirst = L + i*FREGMENT_LEN;//0,5,10
            mArr[i] = getFregMedium(arr,fregFirst,Math.min(fregFirst+4,R));
        }
        return bfprt(mArr,0,mArrSize-1,mArrSize>>1);
    }
    public int getFregMedium(int[] arr,int L,int R){
        insertSort(arr,L,R);
        return arr[L+((R-L)>>1)];
    }
    public void insertSort(int[] arr,int L,int R){
        for(int i = L+1;i<=R;i++){
            int ii = i - 1;//尝试插入的地方
            int t = arr[i];
            while(ii>=L&&arr[ii]>t){
                arr[ii+1] = arr[ii--];//后移
            }
            arr[ii+1] = t;
        }
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(new T40_getLeastNumbers().getLeastNumbers(new int[]{3,2,1,5,6,7,-2,0},2)));
    }
}
