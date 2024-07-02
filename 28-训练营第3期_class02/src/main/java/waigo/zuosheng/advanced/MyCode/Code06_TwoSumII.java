package waigo.zuosheng.advanced.MyCode;

import java.util.*;

/**
 * author waigo
 * create 2021-05-10 11:18
 */

/*
输入一个递增排序的数组和一个数字s，在数组中查找两个数，使得它们的和正好是s。如果有多对数字的和等于s，则输出任意一对即可。

示例 1：

输入：nums = [2,7,11,15], target = 9
输出：[2,7] 或者 [7,2]
示例 2：

输入：nums = [10,26,30,31,47,60], target = 40
输出：[10,30] 或者 [30,10]
 
限制：
1 <= nums.length <= 10^5
1 <= nums[i] <= 10^6

思路：
先看限制，数据量为10^5，所以O(N^2)必超时
先用暴力法更加理解题意，然后看是否能优化
一:暴力法
那就是枚举所有的二元组了，这里说这是个递增的数组，所以就没有去重的顾虑了，那就定住一个i，然后枚举i+1~N-1(N为数组长度)
这种方法O(N^2)没的说，必过不了

优化思考:
我i位置一定要把i+1~N-1全看一遍才知道有没有合法元组嘛，是否之前求过的位置能给我加加速？
这是一个递增的数组，思考是否有单调性可以维持？
好像是有的，比如说i-1位置配对的位置j在大于target的情况下一直来到的最右边了，
也就是说
arr[i-1]+arr[j]<=target
arr[i-1]+arr[j+1]>target,已经最右了，再右就超了
这时候就得分情况讨论了
1.arr[i-1]+arr[j-1]=target,直接返回
2.arr[i-1]+arr[j-1]<target,OK，i-1位置不可能有答案了，淘汰，head++

那么arr[i]>arr[i-1]的情况下，arr[i]+arr[j+1]必定也大于target了，j不可能会往后走，假如说i位置有答案，
j也只会是小于等于之前j的位置

OK，维持好这个单调性，使用双指针，一前一后，所有位置只看一遍，O(N)拿下。

 */
public class Code06_TwoSumII {
    public static int[] twoSum(int[] nums, int target) {
        int head = 0;
        int tail = nums.length-1;
        int[] res = new int[2];
        while (head<tail){
            while (nums[head]+nums[tail]>target)
                tail--;
            if(head>=tail) break;//错误了，直接跳出没得答案了
            if(nums[head]+nums[tail]==target){
                res[0] = nums[head];
                res[1] = nums[tail];
                break;
            }
            head++;
        }
        return res;
    }
    //2.给定一个有序数组arr，给定一个正数aim
    //
    //1）返回累加和为aim的，所有不同二元组
    public static List<int[]> getAllTwoSumPair(int[] sortedArr,int aim){
        List<int[]> res=  new ArrayList<>();
        if(sortedArr==null||sortedArr.length<2) return res;
        int head = 0;
        int tail = sortedArr.length-1;
        while (head<tail){
            int twoSum = aim-1;
            while (head<tail&&(twoSum = sortedArr[head]+sortedArr[tail])>aim)
                tail--;
            if(head>=tail) break;
            if(twoSum==aim&&(head-1<0||sortedArr[head]!=sortedArr[head-1])){
                res.add(new int[]{sortedArr[head],sortedArr[tail]});
            }
            head++;
        }
        return res;
    }
    public static class Pair{
        int sum1;
        int sum2;

        public Pair(int sum1, int sum2) {
            this.sum1 = sum1;
            this.sum2 = sum2;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair pair = (Pair) o;
            return sum1 == pair.sum1 && sum2 == pair.sum2;
        }

        @Override
        public int hashCode() {
            return Objects.hash(sum1, sum2);
        }
    }
    //for test
    public static List<int[]> getAllTwoSumPair2(int[] sortedArr,int aim){
        List<int[]> res=  new ArrayList<>();
        if(sortedArr==null||sortedArr.length<2) return res;
        Set<Pair> pairFound = new HashSet<>();
        Set<Integer> preFind = new HashSet<>();
        for(int i = 0;i<sortedArr.length;i++){
            if(preFind.contains(aim-sortedArr[i])){
                pairFound.add(new Pair(aim-sortedArr[i],sortedArr[i]));
            }else{
                preFind.add(sortedArr[i]);
            }
        }
        Iterator<Pair> iterator = pairFound.iterator();
        while (iterator.hasNext()){
            Pair next = iterator.next();
            res.add(new int[]{next.sum1,next.sum2});
        }
        return res;
    }
    //For test
    public static int[] generalRandomAry(int maxLen,int maxVal){
        int size = (int) (Math.random()*maxLen)+1;
        int[] res = new int[size];
        for (int i = 0; i < size; i++) {
            res[i] = (int) (Math.random()*maxVal)-(int) (Math.random()*maxVal);
        }
        Arrays.sort(res);
        return res;
    }
    public static void main(String[] args) {
        int maxVal = 300;
        int maxLen = 10000;
        int testTimes = 1000000;
        int i = 0;
        for(;i<testTimes;i++){
            int[] nums = generalRandomAry(maxLen,maxVal);
            int aim = (int) (Math.random()*maxVal);
            if(!resIsEql(getAllTwoSumPair2(nums, aim),getAllTwoSumPair(nums, aim))) {
                System.out.println("aim:"+aim);
                System.out.println("ary:"+Arrays.toString(nums));
                toListString(getAllTwoSumPair2(nums, aim));
                toListString(getAllTwoSumPair(nums, aim));
                break;
            }
        }
        System.out.println(i==testTimes?"finish!!!":"fucking!!!");
    }

    private static void toListString(List<int[]> allTwoSumPair2) {
        System.out.println("result:----------------------------------");
        for (int[] ary : allTwoSumPair2) {
            System.out.println(Arrays.toString(ary));
        }
        System.out.println("end:----------------------------------");
    }
    public static boolean resIsEql(List<int[]> res1,List<int[]> res2){
        if(res1==null&&res2==null) return true;
        if(res1==null||res2==null||res1.size()!=res2.size()) return false;
        return true;
    }
}
