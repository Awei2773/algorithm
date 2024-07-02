package waigo.zuoshen.basic.MyCode;


import java.util.PriorityQueue;

/**
 * author waigo
 * create 2021-02-12 22:24
 */
public class Code02_Heap01 {

    //这个堆是以0开始存储元素的，所以父节点位置为(i-1)/2,左孩子位置i*2+1,右孩子位置i*2+2
    //如果是以1开始存储元素的，父节点位置为i/2，左孩子位置i*2,右孩子位置i*2+1
    //直接简单画一个完全二叉树就可以看出来了，堆是完全二叉树的一种，完全二叉树就是每一层都排满了才去排下一层
    //处于这个状态下都是完全二叉树
    public static class MyHeap {
        private int limit;
        private int size;//既是当前堆中数量，也是指向当前存储元素的堆位置
        private int[] elementData;

        public MyHeap(int limit) {
            this.limit = limit;
            elementData = new int[limit];
        }

        public void add(int value) {
            if (size >= limit) throw new RuntimeException("HeapOverFlow...");
            elementData[size] = value;
            heapInsert(size++);
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public void heapInsert(int index) {
            //找到不比父节点大的位置,-1右移还是-1，因为是循环右移的
            while (elementData[index] > elementData[(index - 1) / 2]) {
                swap(index, (index - 1) / 2);
                //index = (index - 1)/2;
                index = (index - 1) / 2;
            }

        }

        public int pop() {
            if (size <= 0) throw new RuntimeException("the heap is null...");
            int heapTop = elementData[0];
            swap(0, --size);
            heapify(0);
            return heapTop;
        }

        public void heapify(int index) {
            //找到较大的孩子，如果比自己还要大则交换，直到一个比左右孩子都大的节点或者没有孩子的节点
            int leftChild = (index << 1) | 1;//index*2+1
            while (leftChild < size) {
                //只有这样才能保证至少有一个孩子节点
                int largest = (leftChild + 1) < size && elementData[leftChild] < elementData[leftChild + 1] ? leftChild + 1 : leftChild;
                largest = elementData[largest] > elementData[index] ? largest : index;
                if (largest == index) break;
                swap(largest, index);
                index = largest;
                leftChild = (index << 1) | 1;
            }

        }

        public void swap(int aIdx, int bIdx) {
            int temp = elementData[aIdx];
            elementData[aIdx] = elementData[bIdx];
            elementData[bIdx] = temp;
        }
    }

    //for test
    public static class TestHeap {
        private int[] elementData;
        private int limit;
        private int size;

        public TestHeap(int limit) {
            this.limit = limit;
            elementData = new int[limit];
        }

        public void add(int value) {
            if(isFull())
                throw new RuntimeException("HeapOverFlow...");
            elementData[size++] = value;
        }

        public void swap(int aIdx, int bIdx) {
            if (aIdx != bIdx) {
                elementData[aIdx] ^= elementData[bIdx];
                elementData[bIdx] ^= elementData[aIdx];
                elementData[aIdx] ^= elementData[bIdx];
            }
        }

        public int pop() {
            int maxIdx = 0;
            for (int i = 0; i < size; i++) {
                if (elementData[i] > elementData[maxIdx]) maxIdx = i;
            }
            if (maxIdx != 0) swap(0, maxIdx);
            int max = elementData[0];
            swap(0, --size);
            return max;
        }
        public boolean isEmpty(){
            return size == 0;
        }
        public boolean isFull(){
            return size == limit;
        }
    }
}
