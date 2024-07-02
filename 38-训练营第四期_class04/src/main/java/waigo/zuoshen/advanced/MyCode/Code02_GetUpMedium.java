package waigo.zuoshen.advanced.MyCode;

/**
 * author waigo
 * create 2021-06-16 10:37
 */
public class Code02_GetUpMedium {
    //1.找到两个等长正序数组合并之后的上中位数
    /**
     * 思路：
     * 方法一：直接用双指针计数的方式或者merge两个数组然后通过len/2直接拿到中位数
     *        但是这个时间复杂度是比较高的，为O(m)
     * 方法二：如果能够直接利用两个区间的中位数给算出merge后的中位数或者说能够将下一次
     *        的数据集给二分，那就能将时间复杂度变成Log级别
     *        1.如果区间a1和区间b1的中位数相同，那说明这就是结果了直接返回
     *        2.如果a1.medium>b1.medium,
     *        3.如果a1.medium < b1.medium
     *
     *        如果是偶数就直接递归就好了，因为偶数下分出来的可能的区间是相等的，
     *        但是如果是奇数就不行了，就得进行一下处理， 将medium位置手动判断一下
     *        确保下一个流程进入的时候区间是等长的
     *
     *        反正就是大的那边最后会剩下一个偶数，因为没包medium，
     *        小的那边最后会剩下一个奇数，因为包了medium
     */
    @SuppressWarnings("Duplicates")
    public static int getUpMedium(int[] arr1,int l1,int r1,int[] arr2,int l2,int r2){
        //潜台词：r1-l1==r2-l2
        //两种可能，1.区间长度为奇数2.区间长度为偶数
        while (l1<r1){
            int mid1 = (l1+r1)>>1;
            int mid2 = (l2+r2)>>1;
            if(arr1[mid1]==arr2[mid2]) return arr1[mid1];
            if(arr1[mid1]>arr2[mid2]){
                //a1.medium{包括}之后的都不可能正确，b1.medium之前的都不可能正确
                r1 = mid1-1;
                if(((r1-l1+1)&1)==1&&arr2[mid2]>arr1[r1]){
                    return arr2[mid2];
                }
                l2 = mid2+1;
            }else{
                r2 = mid2-1;
                if(((r1-l1+1)&1)==1&&arr1[mid1]>arr2[r2]){
                    return arr1[mid1];
                }
                l1 = mid1+1;
            }
        }
        return Math.min(arr1[r1],arr2[r2]);
    }
    //还可以这么来处理出现奇数的情况，那就是让大的那边多补上mid这个不可能出现答案的情况
    //这还挺尴尬的，在只剩下两个数的时候可能就压不出答案来了，甚至会死循环，因为每次mid都是第一个
    //然后又包回去，然后又Mid又是第一个，就没完了，所以还是得用上面那种
    public static int getUpMedium1(int[] arr1,int l1,int r1,int[] arr2,int l2,int r2){
        while (l1<r1){
            int mid1 = l1+((r1-l1)>>1);
            int mid2 = l2+((r2-l2)>>1);
            if(arr1[mid1]==arr2[mid2]) return arr1[mid1];
            if(arr1[mid1]>arr2[mid2]){
                r1 = mid1;
                l2 = mid2;
            }else{
                l1 = mid1;
                r2 = mid2;
            }
        }
        return Math.min(arr1[r1],arr2[r2]);
    }
    public static void main(String[] args) {
        int[] a = {1,3,5,7,7,11,13,13,14};
        int[] b = {2,4,6,8,9,9,10,10,12};
        System.out.println(getUpMedium(a,0,a.length-1,b,0,b.length-1));
        System.out.println(getUpMedium1(a,0,a.length-1,b,0,b.length-1));
    }
}
