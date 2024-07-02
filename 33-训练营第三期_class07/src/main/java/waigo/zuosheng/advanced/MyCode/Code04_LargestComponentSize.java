package waigo.zuosheng.advanced.MyCode;

/**
 * author waigo
 * create 2021-05-29 11:07
 */


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 一个数组中，如果两个数的最小公共因子大于1，则认为这两个数之间有通路
 * 返回数组中，有多少个独立的域
 */
public class Code04_LargestComponentSize {
    //set中每个集合都是下标，专门为数组上玩并查集量身打造
    public static class UnionSet {
        int[] parents;
        int[] sizes;
        int[] help;//用来存查父的时候走过的路径
        int maxSize = 1;

        public UnionSet(int length) {
            parents = new int[length];
            sizes = new int[length];
            help = new int[length];
            for (int i = 0; i < length; i++) {
                parents[i] = i;
                sizes[i] = 1;
            }
        }

        //离散化，使得平摊O(1)
        public int findFather(int son) {
            int pathLen = 0;
            while (parents[son] != son) {
                help[pathLen++] = son;
                son = parents[son];
            }
            for (int i = 0; i < pathLen; i++) {
                parents[help[i]] = son;
            }
            return son;
        }

        //合并两个集合,小挂大
        public void union(int setA, int setB) {
            int aFather = findFather(setA);
            int bFather = findFather(setB);
            if (aFather == bFather) return;
            if (sizes[aFather] < sizes[bFather]) {
                //小挂大
                sizes[bFather] += sizes[aFather];
                parents[aFather] = bFather;
            } else {
                sizes[aFather] += sizes[bFather];
                parents[bFather] = aFather;
            }
            maxSize = Math.max(Math.max(maxSize, sizes[aFather]), sizes[bFather]);
        }
    }

    //O(N^2)解法
    public static int getZoneNum(int[] arr) {
        if (arr == null || arr.length < 1) return 0;
        UnionSet set = new UnionSet(arr.length);
        int N = arr.length;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (gcd(arr[i], arr[j]) > 1) {
                    set.union(i, j);
                }
            }
        }
        return set.maxSize;
    }

    //gcd(a,b)
    public static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    //O(N^sqrt(M)),M是最大值
    public static int getZoneNum2(int[] nums) {
        if (nums == null || nums.length < 1) return 0;
        UnionSet set = new UnionSet(nums.length);
        Map<Integer, Integer> factorToIdx = new HashMap<>();//因子对应的下标
        int N = nums.length;
        for (int i = 0; i < N; i++) {
            int sqrtM = (int) Math.sqrt(nums[i]);
            for (int factor = 2; factor <= sqrtM; factor++) {
                if (nums[i] % factor == 0) {
                    if (factorToIdx.containsKey(factor)) {
                        set.union(i, factorToIdx.get(factor));
                    } else factorToIdx.put(factor, i);
                    if (factorToIdx.containsKey(nums[i] / factor)) {
                        set.union(i, factorToIdx.get(nums[i] / factor));
                    } else factorToIdx.put(nums[i] / factor, i);
                }
            }
            //自己可能作为因子
            if(factorToIdx.containsKey(nums[i])){
                set.union(i,factorToIdx.get(nums[i]));
            }else
                factorToIdx.put(nums[i], i);
        }
        return set.maxSize;
    }

    //从这题的数据量来看N^2是过不了的，N^2会出现4*10^8的规模，超时了
    public static void main(String[] args) {
        int[] ary = new int[]{83,99,39,11,19,30,31};
        System.out.println(getZoneNum(ary));
        System.out.println(getZoneNum2(ary));
    }
}
