package waigo.zuoshen.basic.MyCode;

import java.util.Arrays;

/**
 * author waigo
 * create 2021-02-09 11:12
 */
public class Code05_BSNearRight {
    //在一个有序数组中，找<=某个数最右侧的位置
    private static int nearestIndex(int[] arr, int value) {
        int index = -1;
        if (arr != null){
            int left = 0, right = arr.length - 1;
            int mid;
            while (left <= right) {
                mid = left + ((right - left) >> 1);
                if (arr[mid] <= value) {
                    index = mid;
                    left = mid + 1;//往右边继续二分
                } else {
                    right = mid - 1;//往左边继续二分
                }
            }
        }
        return index;
    }

    // for test
    public static int test(int[] arr, int value) {
        for (int i = arr.length - 1; i >= 0; i--) {
            if (arr[i] <= value) {
                return i;
            }
        }
        return -1;
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return arr;
    }

    // for test
    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 10;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            Arrays.sort(arr);
            int value = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
            if (test(arr, value) != nearestIndex(arr, value)) {
                printArray(arr);
                System.out.println(value);
                System.out.println(test(arr, value));
                System.out.println(nearestIndex(arr, value));
                succeed = false;
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
    }


}
