package waigo.zuoshen.advanced.MyCode;

/**
 * author waigo
 * create 2021-05-13 8:10
 */

import java.util.*;

/**
 * 给你一个整数数组 nums 和一个整数 k ，请你返回其中出现频率前 k 高的元素。
 * 你可以按 任意顺序 返回答案。
 * <p>
 * 示例 1:
 * <p>
 * 输入: nums = [1,1,1,2,2,3], k = 2
 * 输出: [1,2]
 * 示例 2:
 * <p>
 * 输入: nums = [1], k = 1
 * 输出: [1]
 *  
 * 提示：
 * <p>
 * 1 <= nums.length <= 10^5
 * k 的取值范围是 [1, 数组中不相同的元素的个数]
 * 题目数据保证答案唯一，换句话说，数组中前 k 个高频元素的集合是唯一的
 *  
 * 进阶：你所设计算法的时间复杂度 必须 优于 O(n log n) ，其中 n 是数组大小。
 */
public class Code03_TopKQuestion {
    public static class Node implements Comparable<Node> {
        int num;
        int times;

        public Node(int num, int times) {
            this.num = num;
            this.times = times;
        }

        @Override
        public int compareTo(Node o) {
            return this.times - o.times;
        }
    }

    public static int[] topKFrequent(int[] nums, int k) {
        if (nums == null || nums.length < k||k<=0) return new int[]{};
        PriorityQueue<Node> minEdge = new PriorityQueue<>(k);
        HashMap<Integer, Integer> wordMapTimes = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            //词频统计
            if (!wordMapTimes.containsKey(nums[i])) wordMapTimes.put(nums[i], 1);
            else wordMapTimes.put(nums[i], wordMapTimes.get(nums[i]) + 1);
        }
        //选K大
        Set<Map.Entry<Integer, Integer>> entrySet = wordMapTimes.entrySet();
        for (Map.Entry<Integer, Integer> e : entrySet) {
            if(minEdge.size()<k){
                minEdge.add(new Node(e.getKey(), e.getValue()));
            }else if (e.getValue() > minEdge.peek().times) {
                minEdge.poll();
                minEdge.add(new Node(e.getKey(), e.getValue()));
            }
        }
        int[] res = new int[k];
        int count = 0;
        while (!minEdge.isEmpty()){
            res[count++] =minEdge.poll().num;
        }
        return res;
    }


    //基于快排找Kth问题解决
    public static int[] topKFrequent2(int[] nums, int k) {
        if (nums == null || nums.length < k || k <= 0) return new int[]{};
        HashMap<Integer, Integer> wordMapTimes = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            //词频统计
            if (!wordMapTimes.containsKey(nums[i])) wordMapTimes.put(nums[i], 1);
            else wordMapTimes.put(nums[i], wordMapTimes.get(nums[i]) + 1);
        }
        //准备数组来找Kth
        Node[] nodes = new Node[wordMapTimes.size()];
        Set<Map.Entry<Integer, Integer>> entries = wordMapTimes.entrySet();
        int count = 0;
        for (Map.Entry<Integer, Integer> entry:entries){
            nodes[count++] = new Node(entry.getKey(),entry.getValue());
        }
        //找到第k大
        Node node = findKth(nodes,0,nodes.length-1,k-1,new NodeComparator());
        int[] res = new int[k];
        res[k-1] = node.num;
        for(int i = 0;i<k-1;i++){
            res[i] = nodes[i].num;
        }
        return res;
    }
    //辅助数组进行降序排序
    public static class NodeComparator implements Comparator<Node>{
        @Override
        public int compare(Node o1, Node o2) {
            return o2.times-o1.times;
        }

    }
    private static Node findKth(Node[] nodes, int l, int r, int k,Comparator<Node> comparator) {
        int pivot = (int) (Math.random()*(r-l+1)+l);
        swap(nodes,pivot,r);
        int[] flags = partition(nodes,l,r,comparator);//[lowTop+1,highLow-1],相等区的两个边界
        if(k<flags[0]){
            findKth(nodes,0,flags[0]-1,k,comparator);
        }else if(k>flags[1]){
            findKth(nodes,flags[1]+1,r,k,comparator);
        }
        return nodes[k];
    }

    private static int[] partition(Node[] arr, int l, int r,Comparator<Node> comparator) {
        int lowTop = l-1;
        int highLow = r;
        for(int j = l;j<highLow;j++){
            int judge = comparator.compare(arr[j],arr[r]);
            if(judge <0){
                swap(arr,++lowTop,j);
            }else if(judge>0){
                swap(arr,--highLow,j--);//停在原地，上面来的还没看
            }
        }
        swap(arr,r,highLow++);
        return new int[]{lowTop+1,highLow-1};
    }

    private static void swap(Node[] arr, int aIdx,int bIdx) {
        Node temp = arr[aIdx];
        arr[aIdx] = arr[bIdx];
        arr[bIdx] = temp;
    }

    public static void main(String[] args) {
        int[] a = {1,1,1,2,2,2,3,3,3,3,3};
        System.out.println(Arrays.toString(topKFrequent2(a, 2)));
    }
}
