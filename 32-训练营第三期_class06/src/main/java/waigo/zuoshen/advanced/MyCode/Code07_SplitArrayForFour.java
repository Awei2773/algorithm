package waigo.zuoshen.advanced.MyCode;

/**
 * author waigo
 * create 2021-05-24 10:45
 */

/**
 * 给定一个正数数组arr，返回该数组能不能分成4个部分，并且每个部分的累加和相等，切分位置的数不要。
 * 例如:
 * arr=[3, 2, 4, 1, 4, 9, 5, 10, 1, 2, 2] 返回true
 * 三个切割点下标为2, 5, 7. 切出的四个子数组为[3,2], [1,4], [5], [1,2,2]，
 * 累加和都是5
 */

/**
 * 解题思路：
 * 切四个部分得三刀，第一刀不能切在第一个数那里，最后一刀不能切在最后那个数那里，两刀之间不能挨着
 * 四个数直接返回true，过滤一下
 * 小于7个数直接返回false
 * 第一刀可以切的位置为1...N-6,这里是索引，假设第一刀切在了s1位置
 * 第二刀可以切的位置为s1+2...N-4,假设切在了s2位置
 * 第三刀可以切的位置为s2+2...N-2
 * 其实不用枚举每刀切在哪里，因为这是一个正数数组，累加和只会变大，所以第一刀切下去后得到了第一块的累加和
 * 然后会将第二刀推到第二块等于第一块的位置
 * 1.第二块推不到等于第一块的位置，但是没有越界
 * 这个第一刀位置不行，换
 * 2.越界了
 * 已经不可能再找到了，返回false
 * 3.推到了等于第一块的位置，第三刀开始推，重复第二块的逻辑看看能不能推到等于位置
 *
 * 为了快速求出第一块的累加和，肯定需要一个前缀和数组
 */
public class Code07_SplitArrayForFour {
    public static boolean isSplitFour(int[] arr){
        if(arr==null||arr.length<7) return false;
        //前缀和数组
        int N = arr.length;
        int[] prefix = new int[N];
        prefix[0] = arr[0];
        for (int i = 1; i < N; i++) {
            prefix[i] = arr[i]+prefix[i-1];
        }
        int firSum;
        int s2=0,s3=0;
        for (int s1 = 1; s1 <= N - 6; s1++) {
            //第一刀切下去,得到第一块的和
             firSum= prefix[s1-1];
             s2= Math.max(s2,s1+2);//初始化
             //推第二刀
             while (s2<=N-4&&prefix[s2-1]-prefix[s1]<firSum){
                 s2++;
             }
             //1.越界了
            if(s2>N-4) return false;
            //2.推不出来，但是没有越界，换第一刀
            if(prefix[s2-1]-prefix[s1]>firSum) continue;
            //3.推出来了，那就推最后一刀
            s3 = Math.max(s2+2,s3);
            while (s3<=N-2&&prefix[s3-1]-prefix[s2]<firSum){
                s3++;
            }
            if(s3>N-2) return false;
            if(prefix[s3-1]-prefix[s2]>firSum) continue;
            //看推出来的最后一块是否也相等
            if(prefix[N-1]-prefix[s3]==firSum) return true;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(isSplitFour(new int[]{3, 2, 4, 1, 4, 9, 5, 10, 1, 2, 2}));
    }
}
