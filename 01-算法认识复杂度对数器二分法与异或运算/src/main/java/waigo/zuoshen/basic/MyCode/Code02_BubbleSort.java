package waigo.zuoshen.basic.MyCode;

import java.util.Arrays;

/**
 * author waigo
 * create 2021-02-09 11:11
 */
public class Code02_BubbleSort {
    public static void bubbleSort(int[] arr){
        for(int i = arr.length-1;i>=0;i--){
            for(int j = 0;j<i;j++){
                if(arr[j]>arr[j+1]){
                    swap(arr,i,j);
                }
            }
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
        bubbleSort(arr);
        System.out.println(Arrays.toString(arr));
    }

}
