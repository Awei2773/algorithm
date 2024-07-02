package waigo.zuoshen.advanced.MyCode;

/**
 * author waigo
 * create 2021-05-19 11:27
 */

/**
 * 一个数组的异或和是指数组中所有的数异或在一起的结果
 * 给定一个数组arr，求最大子数组异或和。数组有正有负
 *
 * 思路：
 * 暴力法：
 * 以每个位置结尾的最大子数组异或和中再挑最大的那个，答案必是
 * 可惜异或没有单调性，把握不住那个子数组的异或和就是最大的，可能一个很长的子数组异或和很小，
 * 而一个很短的子数组反而异或和很大。所以得遍历每个位置结尾下的所有子数组，但是对于每个子数组的
 * 异或和可以用前缀异或和来加速，使得每个子数组的求解都是O(1),这样整个流程能够做到O(N^2)
 *
 * 思考是否具有单调性或者能否贪心贪出最优解？
 * 分析异或的性质
 * 计算机底层数都是以补码的形式存在的，所以-1在运算的时候为111111111，-2在运算的时候为11111110
 * -3在运算的时候为111111101，-4在运算的时候为1111111100
 * 分情况讨论看是否具有某些好东西可以贪心
 *
 * 比如说，以3位置结尾，以2位置开头的子数组的异或和=EOR[0...1]^EOR[0...3]
 * 所以现在问题就变成了某个位置结尾时候应该和哪个前缀异或和结合会出现最大异或和？
 *
 * 1.如果此时结尾位置的前缀为正数
 *   011111010101，如何能够获得最大的异或和？
 *   000000101010，这个和它异或很明显能够得到最大值，贪心就贪心在高位一定多出现1，能够做正数就一定还是正数
 *   贪心策略：
 *   1.符号位有0一定选0，没有也没办法只好选1了
 *   2.结果为正数的时候，高位越多1，结果越大，很明显
 *   3.通过上面对于负数补码的分析，发现负数也是一样，高位1越多就结果越大
 *   nice,两个可以统一处理了，结果就是选结合对象的时候
 *   conclusion:符号位选和自己此时的符号位相同的，数值位尽量和自己相反的。
 *
 *   那么如何去找那个最应该结合的前缀呢？
 *    我们找的那个过程就像是站在一个分岔路口，手里拿着那一位要找对象的bit，犹豫该往哪边走，这很明显得用树了。
 *    前缀树能够完美符合我们的要求。
 */
public class Code01_MaxEOR {
    //O(N^2)，痛点在于不知道以某个位置结尾的子数组中应该选择哪个，需要遍历一遍O(N)才能确定
    public static int maxSubAryEORSum(int[] nums){
        if (nums == null || nums.length == 0) {
            return 0;
        }
        //前缀异或数组 prefixEOR[i]表示[0..i]的异或和
        int N = nums.length;
        int[] prefixEOR = new int[N];
        prefixEOR[0] = nums[0];
        for (int i = 1; i < N; i++) {
            prefixEOR[i] = prefixEOR[i-1]^nums[i];
        }
        //枚举每个位置结尾情况下的子数组最大异或和
        int max = Integer.MIN_VALUE;
        for (int end = 0; end < N; end++) {
            for (int start = 0; start <= end; start++) {
                //[start...end]这一个子数组的异或和=prefixEOR[end]^prefixEOR[start-1]
                max = Math.max(max,prefixEOR[end]^(start-1<0?0:prefixEOR[start-1]));
            }
        }
        return max;
    }

    public static class ZeroOneTireTree{
        Node root = new Node();//下面第一次走是符号位
        public static class Node{
            Node[] nexts = new Node[2];//nexts[0]表示走向0，nexts[1]表示走向1
            Integer end;
        }
        //加入一个数
        public void addNum(int num){
            Node cur = root;
            for (int bit = 31; bit >=0 ; bit--) {
                int path = (num>>>bit)&1;
                if(cur.nexts[path]==null){
                    cur.nexts[path] = new Node();
                }
                cur = cur.nexts[path];
            }
            cur.end = num;//记录这一条路的结尾
        }
        //输入一个数，返回此时前缀树中能够和它结合的最大前缀数
        public int getMaxPrefix(int num){
            Node cur = root;
            for (int bit = 31; bit >=0 ; bit--) {//处理数值位
                int curBit = (num>>>bit)&1;
                int wish = bit==31?curBit:(curBit^1);//理想
                cur = cur.nexts[wish]==null?cur.nexts[wish^1]:cur.nexts[wish];//现实
            }
            return cur.end;
        }
    }
    public static int maxSubAryEORSum2(int[] nums){
        if (nums == null || nums.length == 0) {
            return 0;
        }
        ZeroOneTireTree tireTree = new ZeroOneTireTree();
        tireTree.addNum(0);//为了[0....i]这个答案不被忽略
        int prefixEOR = 0;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            prefixEOR^=nums[i];//[0...i]的EOR
            max = Math.max(prefixEOR^tireTree.getMaxPrefix(prefixEOR),max);
            tireTree.addNum(prefixEOR);
        }
        return max;
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
    // for test
    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 30;
        int maxValue = 50;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int comp = maxSubAryEORSum(arr);
            int res = maxSubAryEORSum2(arr);
            if (res != comp) {
                succeed = false;
                printArray(arr);
                System.out.println(res);
                System.out.println(comp);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
        //
        // // int[] arr = generateRandomArray(6, maxValue);
        // int[] arr = { 3, -28, -29, 2};
        //
        // for (int i = 0; i < arr.length; i++) {
        // System.out.println(arr[i] + " ");
        // }
        // System.out.println("=========");
        // System.out.println(maxXorSubarray(arr));
        // System.out.println((int) (-28 ^ -29));

    }
}
