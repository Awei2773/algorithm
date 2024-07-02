package com.waigo.nowcoder;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.Arrays;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    public static void main(String[] args) {
        int[] arr = {4,5,1,0,6,2,7,3,8,9,8,2,7};
        int k = 4;
        new AppTest().getMinKth(arr,k);
    }
    public void getMinKth(int[] arr,int k){
        int kth = getKth(arr,0,arr.length-1,k);//拿到有序下的第K个
        int[] result = new int[k];
        for(int i = 0;i<k;i++){
            result[i] = arr[i];
        }
        Arrays.sort(result);
        System.out.println(Arrays.toString(result));
    }
    public int getKth(int[] arr,int l,int r,int k){
        if(l==r){
            return arr[l];
        }
        //随机取一个pivot
        swap(arr,r,l+(int)(Math.random()*(r-l+1)));
        int[] flags = partition(arr,l,r);//flags=[minTop+1,bigLow-1];
        if(k<flags[0]){
            return getKth(arr,l,flags[0]-1,k);
        }else if(k>flags[0]){
            return getKth(arr,flags[1]+1,r,k);
        }
        return arr[k];
    }
    public int[] partition(int[] arr,int l,int r){
        int minTop = l-1;
        int bigLow = r;
        int pivot = arr[r];
        for(int j = l;j<bigLow;j++){
            if(arr[j]<pivot){
                swap(arr,++minTop,j);
            }else if(arr[j]>pivot){
                swap(arr,--bigLow,j--);//从高处下来的还没看，留在原地
            }
        }
        swap(arr,bigLow++,r);//将pivot送回等区
        return new int[]{minTop+1,bigLow-1};
    }
    public void swap(int[] arr,int a,int b){
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }
}
