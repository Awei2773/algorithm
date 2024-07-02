package waigo.zuoshen.basic.MyCode;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.logging.Logger;

/**
 * author waigo
 * create 2021-02-17 22:21
 */
@SuppressWarnings("Duplicates")
public class Code03_Heap02 {
    public static class MyHeap02<T> {
        private ArrayList<T> elementData;
        private HashMap<T, Integer> indexMap;//记录一个元素当前在堆中的下标，用于resign
        private int heapSize;
        private Comparator<? super T> comparator;

        {
            elementData = new ArrayList<>();
            indexMap = new HashMap<>();
        }

        public MyHeap02() {

        }

        public MyHeap02(Comparator<? super T> comparator) {
            this.comparator = comparator;
        }

        @SuppressWarnings("unchecked")
        public int compare(T o1, T o2) {
            int result;
            if (comparator != null) {
                result = comparator.compare(o1, o2);
            } else {
                result = ((Comparable<? super T>) o1).compareTo(o2);
            }
            return result;
        }

        public void swap(int aIdx, int bIdx) {
            T elementA = elementData.get(aIdx);
            T elementB = elementData.get(bIdx);
            elementData.set(aIdx, elementB);//aIdx值重置为bIdx的值
            elementData.set(bIdx, elementA);
            //发生交换之后，交换两方都需要改一下Index位置
            indexMap.put(elementA, bIdx);
            indexMap.put(elementB, aIdx);
        }

        public void heapInsert(int index) {
            //看看父节点(index - 1)/2是否大于它，默认的小根堆
            int fatherIdx;
            while (compare(elementData.get(index), elementData.get((fatherIdx = (index - 1) / 2))) < 0) {
                swap(index, fatherIdx);
                index = fatherIdx;
            }
        }

        public void add(T value) {
            if (value != null) {
                elementData.add(heapSize, value);
                indexMap.put(value, heapSize);
                heapInsert(heapSize++);
            }
        }

        public boolean isEmpty() {
            return heapSize == 0;
        }

        public void heapify(int index) {
            //至少有左节点再接着heapify
            int leftChild;
            while ((leftChild = (index << 1) | 1) < heapSize) {
                int largest = leftChild + 1 < heapSize && compare(elementData.get(leftChild + 1), elementData.get(leftChild)) < 0 ? leftChild + 1 : leftChild;
                largest = compare(elementData.get(largest), elementData.get(index)) < 0 ? largest : index;
                if (largest == index) {
                    break;//表示已经没有比它需要排在前面的孩子了
                }
                swap(index, largest);
                index = largest;
            }
        }

        public T poll() {
            if (heapSize <= 0) throw new RuntimeException("the heap is no more ele...");
            final T value = elementData.get(0);
            swap(0, --heapSize);
            heapify(0);//开始位置那个需要进行heapify调整O(logN)
            return value;
        }

        public void resign(T value) {
            Integer index = indexMap.get(value);
            if (index != null) {
                //上下都看看，只会选一边进行改变
                heapify(index);
                heapInsert(index);
            }
        }

    }

    public static class Student {
        private int age;
        private String name;

        public Student(int age, String name) {
            this.age = age;
            this.name = name;
        }

        @Override
        public String toString() {
            return "Student{" + "age=" + age + ", name='" + name + '\'' + '}';
        }
    }

    public static Student[] generalRandomArray(int maxSize, int maxValue) {
        int size = (int) (Math.random() * maxSize) + 1;//1~maxSize
        Student[] ary = new Student[size];
        for (int i = 0; i < size; i++) {
            ary[i] = new Student((int) (Math.random() * maxValue) - (int) (Math.random() * maxValue), "lisi");
        }
        return ary;
    }

    //对数器
    public static void test() {
        int resignTimes = 100, maxAge = 100, maxSize = 100, maxValue = 100, times = 100;
        for (int k = 0; k < times; k++) {
            MyHeap02<Student> heap = new MyHeap02<>(new Comparator<Student>() {
                @Override
                public int compare(Student o1, Student o2) {
                    return o1.age - o2.age;
                }
            });
            ArrayList<Student> list = new ArrayList<>();
            Student[] students = generalRandomArray(maxSize, maxValue);
            for (Student s : students) {
                heap.add(s);
                list.add(s);
            }
            //resign
            for (int i = 0; i < resignTimes; i++) {
                int randomIdx = (int) (Math.random() * students.length);//0~length-1
                Student specifyValue = list.get(randomIdx);
                specifyValue.age = (int) (Math.random() * maxValue) - (int) (Math.random() * maxValue);
                heap.resign(specifyValue);
            }
            //测试是否还是小根堆
            while (!heap.isEmpty()) {
                Student topMin = heap.poll();
                list.remove(topMin);
                //直接遍历一遍list
                for (Student s : list) {
                    if (s.age < topMin.age) Logger.getGlobal().info("OOPS！！！");
                }
            }
        }

        System.out.println("finish!!!");
    }

    public static void main(String[] args) {
        PriorityQueue<Student> studentPriorityQueue = new PriorityQueue<>(new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return o1.age - o2.age;
            }
        });
        Student s1 = new Student(11, "lisi");
        studentPriorityQueue.add(s1);
        studentPriorityQueue.add(new Student(12, "lisi"));
        studentPriorityQueue.add(new Student(13, "lisi"));
        s1.age = 14;
        //while (!studentPriorityQueue.isEmpty()) Logger.getGlobal().info("item:" + studentPriorityQueue.poll());
        /**
         * output:信息: 默认实现的堆结构无法在元素添加进入之后对它进行修改，修改之后不能保证堆正确，你改了
         * 元素而系统的堆结构不会知道，也不会重新处理堆，这种情况下就需要我们自己来定制堆了，某些语言可能支持
         * resign一个已经添加进入的元素的值，但是时间复杂度不会低，最好自己定制。
         *        item:Student{age=14, name='lisi'}
         *        item:Student{age=12, name='lisi'}
         *        item:Student{age=13, name='lisi'}
         */
        MyHeap02<Student> smallRtHeap = new MyHeap02<>(new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return o2.age - o1.age;//年龄从小到大排序，小根堆
            }
        });
        Student s2 = new Student(11, "战三");
        Student s3 = new Student(12, "战四");
        Student s4 = new Student(13, "战无");
        smallRtHeap.add(s2);
        smallRtHeap.add(s3);
        smallRtHeap.add(s4);
        s2.age = 15;
        smallRtHeap.resign(s2);//告诉堆，s2修改了
        while (!smallRtHeap.isEmpty()) Logger.getGlobal().info("item:" + smallRtHeap.poll());
    }
}
