package waigo.zuosheng.MyCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Code408_SlidingWindowMedium {
    //使用堆+延时删除策设计快速查询中位数的结构
    //中位数的关键在于中间的一个或者两个数
    //所以使用两个堆，左边部分大根堆，右边部分小根堆
    //堆的特点是无法删除非堆顶的元素，所以需要使用延时删除的策略
    //延时删除策略设计：使用哈希表，快速知道某个数字是否需要删除
    //(key,value)->key:删除的数 value:删除几次(因为数字可能重复)
    //每次只有等需要删除的元素来到堆顶才能进行删除，而每次操作过后都需要检查一下堆顶是否需要删除
    //延时删除策略方法
    //1.purge(),检查两个堆的堆顶，while将需要删除的数字全删了
    //结构需要实现这些方法
    //1.insert(num):往结构中加入一个数字
    //2.erase(num):结构删除一个数字
    //3.getMedium():返回当前结构中的中位数
    //由于延时删除，所以使用堆的size()方法获取的size不再准确，需要两个变量
    //smallSize,largeSize
    //每次插入和删除一个元素都可能导致结构不再平衡，有以下两种情况
    //1.smallSize==largeSize+2
    //2.largeSize==smallSize+1
    //需要一个makeBalance方法将结构进行调平衡，数据流进行流转一下，由于每次操作后都进行了堆顶的
    //处理，所以堆顶目前都是合法的，可以进行数据流的流转
    public static class WindowMediumHolder {
        //左边大根堆
        PriorityQueue<Integer> small = new PriorityQueue<>((o1, o2) -> o2>o1?1:-1);//细节：这里也要防止溢出,不能用减法
        //右边小根堆
        PriorityQueue<Integer> large = new PriorityQueue<>();
        //延时删除的哈希表
        HashMap<Integer, Integer> delayed = new HashMap<>();
        //左右的size
        int smallSize, largeSize;

        public void insert(int num) {
            //看一下插入哪边
            if (small.isEmpty() || num <= small.peek()) {
                //插入左边
                smallSize++;
                small.add(num);
            } else {
                //插入右边                
                largeSize++;
                large.add(num);
            }
            makeBalance();
        }

        public void erase(int num) {
            //先查一下这个元素应该放在左边还是右边
            if (num <= small.peek()) {
                //左边
                smallSize--;
            } else {
                //右边
                largeSize--;
            }
            delayed.put(num, delayed.getOrDefault(num, 0) + 1);
            //删掉后就要进行平衡调整
            makeBalance();
        }
        public double getMedium() {
            int n = smallSize + largeSize;
            if (n <= 0) return -1;//越界了
            double result;
            if ((n & 1) == 0) {//偶数
                result = ((double)small.peek()+large.peek())/2;//要敏感加法，乘法可能溢出的情况,将一个提升等级，后一个会隐式升级
            } else {
                result = small.peek();
            }
            return result;
//            return (n & 1) == 1 ? small.peek() : ((double) small.peek() + large.peek()) / 2;
        }

        public void makeBalance() {
            if (smallSize + 1 == largeSize) {
                //右边来个来左边
                smallSize++;
                largeSize--;
                small.add(large.poll());
            } else if (smallSize == largeSize + 2) {
                //左边来个去右边
                largeSize++;
                smallSize--;
                large.add(small.poll());
            }
            purge();//清理一下看是否堆顶有删除的
        }

        public void purge() {
            //检查左边的
            int top;
            while (delayed.getOrDefault(small.peek(), 0) > 0) {
                //延时删除,别忘记修改延时表
                top = small.poll();
                delayed.put(top, delayed.get(top) - 1);
            }
            while (delayed.getOrDefault(large.peek(), 0) > 0) {
                top = large.poll();//延时删除
                delayed.put(top, delayed.get(top) - 1);
            }
        }
    }

    public double[] medianSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length < k) return new double[]{};
        int N = nums.length;
        //滑动窗口法
        int L = 0, R = 0;//[)
        ArrayList<Double> res = new ArrayList<>();
        WindowMediumHolder holder = new WindowMediumHolder();
        for (int i = 0; i < N; i++) {//拢共就是N个元素进滑动窗口
            if (R - L < k) {
                holder.insert(nums[R++]);//加入
            } else {
                res.add(holder.getMedium());
                //开始右移
                holder.insert(nums[R++]);
                holder.erase(nums[L++]);
            }
        }
        //收集最后一次
        res.add(holder.getMedium());
        double[] r = new double[res.size()];
        for (int i = 0; i < res.size(); i++) {
            r[i] = res.get(i);
        }
        return r;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(new Code408_SlidingWindowMedium().medianSlidingWindow(new int[]{-2147483648,-2147483648,2147483647,-2147483648,-2147483648,-2147483648,2147483647,2147483647,2147483647,2147483647,-2147483648,2147483647,-2147483648
        }, 3)));
    }
}
//[-2147483648.0,-2147483648.0,-2147483648.0,-2147483648.0,-2147483648.0,-2147483648.0,2147483647.0,2147483647.0,-2147483648.0,-2147483648.0,-2147483648.0]
//[-2147483648.0,-2147483648.0,-2147483648.0,-2147483648.0,-2147483648.0,2147483647.0,2147483647.0,2147483647.0,2147483647.0,2147483647.0,-2147483648.0]