package waigo.zuoshen.basic.MyCode;

/**
 * author waigo
 * create 2021-02-09 11:13
 */
public class Code06_BSAwesome {
    //局部最小值问题，求一个局部最小的位置下标，这个数组不要求是有序的，这个题目也能用二分

    /**
     * 首先局部最小值指的是，一个位置的值比它左边和右边的位置的值都要小于或等于(3,2,2,4这种2也是局部最小的)，
     * 数学上有个定理，就是如果左边是降序的趋势，右边是升序的趋势，
     * 则中间一定存在一个凹下去的点，这就是我们要的局部最小位置
     * <p>
     * 如果一个数组只有一个元素，那么这个元素就是局部最小的
     */
    public static int getLessIndex(int[] arr) {
        if (arr == null || arr.length == 0) return -1;//no exist
        if (arr.length == 1 || arr[0] <= arr[1]) return 0;
        //arr.length>=2
        if (arr[arr.length - 2] >= arr[arr.length - 1]) return arr.length - 1;
        //此时左边开头处处于降序，结尾处处于升序
        int left = 1, right = arr.length - 2, mid = -1;
        while (left < right) {
            mid = left + ((right - left) >> 1);
            if (arr[mid] > arr[mid - 1]) {
                //向左
                right = mid - 1;
            } else if (arr[mid] > arr[mid + 1]) {
                //向右
                left = mid + 1;
            } else {
                return mid;
            }
        }
        //假设此时left=mid-1,而且left恰好就是一个局部最小的点，然后向左二分，然后right = mid - 1，此时left==right会
        //跳出循环，所以返回left或者right,因为每次只会left或者right移动一步，所以不会出现left>right的情况
        return left;
        //由我们的思想来说，既然左右开头处分别为左边降序，右边升序，那么中间必有一个以上的局部最小点，既然left都等于right
        //了还没找到，那必然就是这个left处
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return arr;
    }

    public static void main(String[] args) {
        int maxSize = 10, maxValue = 100, times = 100000;
        //
        for (int i = 0; i < times; i++) {
            int[] nums = generateRandomArray(maxSize, maxValue);
            int lessIndex = getLessIndex(nums);
            if (lessIndex != 0 && lessIndex != nums.length - 1) {
                if (nums[lessIndex - 1] < nums[lessIndex] || nums[lessIndex] > nums[lessIndex + 1])
                    System.out.println("59--oops!!!");
            }
        }
        System.out.println("finish!!!");
    }

}
