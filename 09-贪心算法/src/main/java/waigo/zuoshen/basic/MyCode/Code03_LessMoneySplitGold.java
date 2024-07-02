package waigo.zuoshen.basic.MyCode;


import java.util.PriorityQueue;

/**
 * author waigo
 * create 2021-03-02 13:45
 */
/*
一块金条切成两半，是需要花费和长度数值一样的铜板的。
比如长度为20的金条，不管怎么切，都要花费20个铜板。 一群人想整分整块金条，怎么分最省铜板?

例如,给定数组{10,20,30}，代表一共三个人，整块金条长度为60，金条要分成10，20，30三个部分。

如果先把长度60的金条分成10和50，花费60; 再把长度50的金条分成20和30，花费50;一共花费110铜板。
但如果先把长度60的金条分成30和30，花费60;再把长度30金条分成10和20， 花费30;一共花费90铜板。
输入一个数组，返回分割的最小代价。
*/
public class Code03_LessMoneySplitGold {
    //暴力递归：将所有的数组元素进行一个排列组合，就是穷举出所有分割的可能，然后从中挑出最小代价的
    //分割的可能就是合并的可能
    public static int getLessMoneySplitGold(int[] golds) {
        if (golds == null || golds.length <= 1) return 0;
        return process1(golds, 0);
    }

    //golds:需要合并的金子，pre：已经花费的钱,返回合并所有金子最少的花费
    //每一轮都穷举合并两个金子，所以整体穷举了所有合并的情况
    private static int process1(int[] golds, int pre) {
        if (golds.length == 1) return pre;
        int res = Integer.MAX_VALUE;
        for (int i = 0; i < golds.length; i++) {
            for (int j = i + 1; j < golds.length; j++) {
                res = Math.min(res, process1(copyAndMerge(golds, i, j), pre + golds[i] + golds[j]));
            }
        }
        return res;
    }

    private static int[] copyAndMerge(int[] golds, int i, int j) {
        int[] ans = new int[golds.length - 1];
        int originIdx = 0;
        for (int index = 0; index < ans.length - 1; ) {
            if (originIdx != i && originIdx != j) {
                ans[index++] = golds[originIdx];
            }
            originIdx++;
        }
        ans[ans.length - 1] = golds[i] + golds[j];
        return ans;
    }

    private static int getMinGoldByHuffmanTree(int[] golds) {
        if (golds == null || golds.length <= 1) return 0;
        //贪心策略，每次分割的时候都是最少金子，也就是说每次都拿最少的金子进行合并，使用小根堆
        PriorityQueue<Integer> smallHeap = new PriorityQueue<>();
        for (int i = 0; i < golds.length; i++) {
            smallHeap.add(golds[i]);
        }
        int res = 0;
        while (smallHeap.size() > 1) {
            int newOne = smallHeap.poll() + smallHeap.poll();
            res += newOne;
            smallHeap.add(newOne);
        }

        return res;
    }

    public static int[] generalRandomGolds(int maxSize, int maxVal) {
        int size = (int) (Math.random() * maxSize) + 2;
        int[] arr = new int[size];
        for(int i = 0;i < size;i++)
            arr[i] = (int)(Math.random()*maxVal)+5;
        return arr;
    }

    public static int[] copyArrDeep(int[] origin){
        int[] arr = new int[origin.length];
        System.arraycopy(origin, 0, arr, 0, origin.length);
        return arr;
    }
    public static void main(String[] args) {
        int maxSize = 5,maxVal = 20,testTimes = 10000;
        int i = 0;
        for(;i < testTimes;i++) {
            int[] ints = generalRandomGolds(maxSize, maxVal);
            if(getMinGoldByHuffmanTree(ints)!=getLessMoneySplitGold(ints)) break;
        }
        System.out.println(i==testTimes?"finish!!!":"fucking!!!");
    }
}
