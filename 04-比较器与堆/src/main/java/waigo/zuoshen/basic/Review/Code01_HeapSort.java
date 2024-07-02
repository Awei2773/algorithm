package waigo.zuoshen.basic.Review;

import waigo.zuoshen.basic.MyCode.utils.MathUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.logging.Logger;

/**
 * author waigo
 * create 2021-02-25 11:49
 */
public class Code01_HeapSort {
    public static class Heap<T> {
        private int size;
        private ArrayList<T> elementData = new ArrayList<>();
        private Comparator<? super T> comparator;

        public Heap(int initialSize) {
            elementData = new ArrayList<>(initialSize);
        }

        public Heap() {
        }

        public Heap(Comparator<? super T> comparator) {
            this.comparator = comparator;
        }

        private void swap(int aIdx, int bIdx) {
            T temp = elementData.get(aIdx);
            elementData.set(aIdx, elementData.get(bIdx));
            elementData.set(bIdx, temp);
        }

        @SuppressWarnings("unchecked")
        private int compare(T a, T b) {
            int result;
            if (comparator != null) {
                result = comparator.compare(a, b);
            } else {
                result = ((Comparable<T>) a).compareTo(b);
            }
            return result;
        }

        private void heapInsert(int index) {
            int fatherIdx;
            //找到父亲，如果自己优先则交换
            while (compare(elementData.get(index), elementData.get(fatherIdx = (index - 1) / 2)) < 0) {
                swap(index, fatherIdx);
                index = fatherIdx;
            }
        }

        private void heapify(int index) {
            if (index < 0) return;
            //首先得有左孩子，一个孩子都没有不用往下找了
            int leftChild;
            while ((leftChild = (index << 1) | 1) < size) {//index*2+1
                int largest = leftChild + 1 < size && compare(elementData.get(leftChild + 1), elementData.get(leftChild)) < 0 ? leftChild + 1 : leftChild;
                largest = compare(elementData.get(index), elementData.get(largest)) < 0 ? index : largest;
                if (largest == index) {
                    break;
                }
                swap(index, largest);
                index = largest;
            }

        }

        public void add(T value) {
            elementData.add(value);
            heapInsert(++size - 1);
        }

        public T poll() {
            if (size <= 0) throw new RuntimeException("no more ele...");
            final T result = elementData.get(0);
            swap(0, size - 1);
            elementData.set(size-- - 1, null);//let't GC to work
            heapify(0);
            return result;
        }

        public boolean isEmpty() {
            return size == 0;
        }
    }

    public static void heapSort(int[] ary) {
        //变成堆
        int heapSize = ary.length;
        //1.法一：全部heapInsert，时间复杂度O(N*logN)
        for (int i = 0; i < ary.length; i++) {
            heapInsert(ary, i);
        }
        //2.法二：从后面开始heapify,时间复杂度O(N)
//        for(int i = heapSize;i >= 0;i--)
//            heapify(ary,i,heapSize);
        //大根堆，对顶元素最大
        while (heapSize>0) {
            swap(ary, 0, --heapSize);
            heapify(ary, 0, heapSize);
        }
    }

    public static void heapify(int[] ary, int index, int heapSize) {
        int leftChild;
        while ((leftChild = (index << 1) | 1) < heapSize) {
            int largest = leftChild + 1 < heapSize && ary[leftChild + 1] > ary[leftChild] ? leftChild + 1 : leftChild;
            largest = ary[largest] > ary[index] ? largest : index;
            if (index == largest) break;
            swap(ary, largest, index);
            index = largest;
        }
    }

    public static void heapInsert(int[] ary, int index) {
        int fatherIdx;
        while (ary[index] > ary[(fatherIdx = (index - 1) / 2)]) {
            swap(ary, index, fatherIdx);
            index = fatherIdx;
        }

    }

    public static void swap(int[] ary, int aIdx, int bIdx) {
        int temp = ary[aIdx];
        ary[aIdx] = ary[bIdx];
        ary[bIdx] = temp;
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

    public static void main(String[] args) {
        int maxSize = 10000000, maxValue = 100, times = 1;
        int i = 0;
        for (; i < times; i++) {
            int[] nums = MathUtil.generalRandomArray(maxSize, maxValue);
            int[] cpyNums = copyArray(nums);
            Date a = new Date();
            heapSort(nums);
            System.out.println("时间："+(new Date().getTime()-a.getTime()));
            Arrays.sort(cpyNums);
            if (!isEqlArray(nums, cpyNums)) {
                Logger.getGlobal().info(Arrays.toString(nums));
                Logger.getGlobal().info(Arrays.toString(cpyNums));
                break;
            }
        }
        Logger.getGlobal().info(i == times ? "finish" : "fucking");
        Heap<Integer> heap = new Heap<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2-o1;
            }
        });
//        heap.add(3);
//        heap.add(4);
//        heap.add(2);
//        heap.add(1);
//        System.out.println(heap.poll());
//        System.out.println(heap.poll());
//        System.out.println(heap.poll());
//        System.out.println(heap.poll());
    }
}
