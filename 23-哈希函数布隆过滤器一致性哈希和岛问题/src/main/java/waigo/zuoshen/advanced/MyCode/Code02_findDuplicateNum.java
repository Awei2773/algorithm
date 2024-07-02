package waigo.zuoshen.advanced.MyCode;

/**
 * author waigo
 * create 2021-04-11 23:07
 */
/*
给定一个包含 n + 1 个整数的数组 nums ，其数字都在 1 到 n 之间（包括 1 和 n），可知至少存在一个重复的整数。
假设 nums 只有 一个重复的整数 ，找出 这个重复的数 。
示例 1：
输入：nums = [1,3,4,2,2]
输出：2
示例 2：
输入：nums = [3,1,3,4,2]
输出：3
示例 3：
输入：nums = [1,1]
输出：1
示例 4：
输入：nums = [1,1,2]
输出：1
提示：
2 <= n <= 3 * 10^4
nums.length == n + 1
1 <= nums[i] <= n
nums 中 只有一个整数 出现 两次或多次 ，其余整数均只出现 一次
进阶：
如何证明 nums 中至少存在一个重复的数字?
你可以在不修改数组 nums 的情况下解决这个问题吗？
你可以只用常量级 O(1) 的额外空间解决这个问题吗？
你可以设计一个时间复杂度小于 O(n2) 的解决方案吗
*/
public class Code02_findDuplicateNum {
    //使用位图,时间复杂度O(N),空间复杂度O(N)
    public static int find_1_useSpace(int[] nums) {
        int N = nums.length - 1;
        //位图容量确定，不会超过Integer.length的，就存0~n这些数，所以需要(n+1)bit
        int[] bitmap = new int[N / 32 + 1];
        //找到某一位数对应的bit，比如现在要找500对应的bit
        //bitmap[500/32]>>>(500%32)，此时最后一位就是我们要找的bit
        int ans = 0;
        for (int i = 0; i < nums.length; i++) {
            int speNum = bitmap[nums[i] / 32];
            int specifyBit = speNum >>> (nums[i] % 32);
            if ((specifyBit & 1) == 0) {
                //没有加过
                bitmap[nums[i] / 32] |= 1 << (nums[i] % 32);
            } else {
                //加过了，就是重复了
                ans = nums[i];
                break;
            }
        }
        return ans;
    }

    //用二分法，时间复杂度O(logN),空间复杂度O(1)
    //左边是包括了M的值的，如果左边小于等于[L...M]个数，那么左边必不可能出现重叠，因为只有一个数发生了重叠，而此时右边的个数大于[M+1...R]，必重叠
    public static int find_2_use3Val(int[] nums) {
        //注意n = nums.length - 1
        int LSum;
        int L = 1, R = nums.length - 1;
        int M;
        while (L < R) {
            //M是此时值域的中点，M=(L+R)/2=L+(R-L)/2
            M = L + ((R - L) >>> 1);
            LSum = 0;
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] <= M&&nums[i]>=L) LSum++;//落在左边区，LSum++,只关注[L...M]的数
            }
            //二分,左边区域[L...M],右边区域[M+1....R]
            if (LSum > (M-L+1)) {
                //肯定是左边发生了重复，去左边继续
                R = M;
            } else {
                //肯定是右边发生了重复，去右边继续
                L = M+1;
            }
        }
        return L;
    }
    //快慢指针
    public static int findDuplicate(int[] nums) {
        int slow = 0, fast = 0;
        do{
            slow = nums[slow];//index->nums[index]组成的图中下一跳；
            fast = nums[nums[fast]];//两跳
        }while (slow!=fast);
        fast = 0;
        while (slow!=fast){
            slow = nums[slow];
            fast = nums[fast];
        }
        return slow;
    }
    //方法四，由于出现重复，如果以数组的值作为跳转，那么这个重复的点会去到多次，所以第一次去的时候将它的值设置为相反数，作为表示已经有人到过了
    //再次到达的时候就返回
    public static int find_useAbs(int[] nums){
        int point = 0;
        int ans;
        while (true){
            int next = nums[nums[point]];
            if(nums[nums[point]]==-nums[point]){
                ans =  nums[point];
                break;
            }else{
                nums[nums[point]]=-nums[point];
            }
            point = next;
        }
        return ans;
    }
    public static void main(String[] args) {
//        int[] a = {3,1,3,4,3};
//        System.out.println(findDuplicate(a));
//        int[][] b = new int[3][0xffffffff];
        System.out.println(Integer.toBinaryString(0xffffffff).length());
    }
}
