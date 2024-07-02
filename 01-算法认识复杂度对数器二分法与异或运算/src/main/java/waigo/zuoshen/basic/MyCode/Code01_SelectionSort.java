package waigo.zuoshen.basic.MyCode;

/**
 * author waigo
 * create 2021-02-09 11:11
 */

import java.util.Arrays;

/**
 * 选择排序
 */
public class Code01_SelectionSort {
    public static void selectSort(int[] arr){
        for(int i = arr.length-1;i>=0;i--){
            int max = arr[0];
            int maxIdx = 0;
            for(int j = 1;j<=i;j++){
                if(arr[j]>max){
                    max = arr[j];
                    maxIdx = j;
                }
            }
            swap(arr,i,maxIdx);
        }
    }

    private static void swap(int[] arr, int a, int b) {
        if(a!=b){
            arr[a]^=arr[b];
            arr[b]^=arr[a];
            arr[a]^=arr[b];
        }
    }
    public static void main(String[] args) {
        int[] arr = new int[]{1,5,2,3423,1,35};
        selectSort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
