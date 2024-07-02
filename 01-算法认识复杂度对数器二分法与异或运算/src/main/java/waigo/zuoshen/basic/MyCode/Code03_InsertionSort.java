package waigo.zuoshen.basic.MyCode;

import java.util.Arrays;

/**
 * author waigo
 * create 2021-02-09 11:12
 */
public class Code03_InsertionSort {
    public static void insertSort(int[] arr){
        for(int i = 1;i<arr.length;i++){
            int cur = i;
            while(cur-1>=0&&arr[cur-1]>arr[cur]){
                swap(cur-1,cur,arr);
                cur--;
            }
        }
    }

    private static void swap(int a, int b,int[] arr) {
        if(a!=b){
            arr[a]^=arr[b];
            arr[b]^=arr[a];
            arr[a]^=arr[b];
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[]{1,5,2,3423,1,35};
        insertSort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
