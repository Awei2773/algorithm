package waigo.zuoshen.basic.MyCode;

/**
 * author waigo
 * create 2021-02-13 14:14
 */
public class Code04_HeapSort {
    public static void swap(int[] arr, int aIdx, int bIdx) {
        if (aIdx != bIdx) {
            arr[aIdx] ^= arr[bIdx];
            arr[bIdx] ^= arr[aIdx];
            arr[aIdx] ^= arr[bIdx];
        }
    }

    public static int[] copyArray(int[] origin) {
        if (origin == null) return null;
        int[] newArray = new int[origin.length];
        for (int i = 0; i < origin.length; i++)
            newArray[i] = origin[i];
        return newArray;
    }

    public static boolean isEqlArray(int[] arrA, int[] arrB) {
        if (arrA == arrB) return true;
        if (arrA == null || arrB == null) return false;
        if (arrA.length != arrB.length) return false;
        for (int i = 0; i < arrA.length; i++)
            if (arrA[i] != arrB[i]) return false;
        return true;
    }

    public static void heapInsert(int[] arr, int index) {
        //父节点位置(index-1)/2
        while (arr[index] > arr[(index - 1) / 2]) {
            swap(arr, index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    public static void heapify(int[] arr, int index, int heapSize) {
        //确保至少有一个孩子
        int leftChildIdx;
        while ((leftChildIdx = ((index << 1) | 1)) < heapSize) {
            int largest = (leftChildIdx + 1 < heapSize) && (arr[leftChildIdx] < arr[leftChildIdx + 1]) ? leftChildIdx + 1 : leftChildIdx;
            largest = arr[largest] > arr[index] ? largest : index;
            //说明已经没有比它大的孩子节点了
            if (largest == index) {
                break;
            }
            //说明还有比它大的孩子节点
            swap(arr, index, largest);
            //以孩子节点为开始再次寻找
            index = largest;
        }
    }

    public static void heapSort(int[] arr) {
        //创建成堆
//        for (int i = 0; i < arr.length; i++)//N
//            heapInsert(arr, i);//logN  --> O(N*logN);
        int size = arr.length;
        for (int i = arr.length - 1; i >= 0; i--)
            heapify(arr, i, size);
        while (size > 0) {//N
            //第一个是堆顶，最大，放到最后
            swap(arr, 0, --size);
            heapify(arr, 0, size);//logN -->O(N*logN)
        }

    }
}
