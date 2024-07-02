package waigo.zuoshen.basic.MyCode;

import java.util.logging.Logger;

/**
 * author waigo
 * create 2021-02-07 22:04
 */
public class Pratice {
    /**
     * 如何不用额外变量交换两个数(这两个数必须指向不同的内存空间)
     */
    public static void title1(int a, int b) {
        a ^= b;//a=a^b
        b ^= a;//b=b^a^b-->偶数b异或为0，异或a等于a,此时b=a已经交换
        a ^= b;//a=a^b-->a^b^a-->b，将a变成b
        System.out.println("a:" + a);
        System.out.println("b:" + b);
    }

    /**
     * 一个数组中有一种数出现了奇数次，其他数都出现了偶数次，怎么找到并打印这种数
     * 思路：偶数个相同的数异或为0，奇数个相同的数异或等于这个数，所以全部异或一遍就能得到这个奇数个的数
     * 异或的结果和计算的顺序无关，满足交换律
     */
    public static void title2(int a[]) {
        int eor = a[0];
        for (int i = 1; i < a.length; i++) {
            eor ^= a[i];
        }
        System.out.println(eor);
    }

    /**
     * 怎么把一个int类型的数，提取出最右侧的1来
     * 5-->101-->输出001
     * 6-->110-->输出010
     *          其实是相反数
     * @param num 注意这里：原码和原码取反加一(还不能说是补码，因为正数的补码就等于原码)就只是最后一个1之前的部分不同，而且
     *            不同的部分正好是互为相反的，所以它们按位与一下就能得到最后一位1
     *            101-->取反加一等于-->011
     *            101
     *            &011
     *            ----
     *            001-->两个数不同肯定与为0，相同但是都为0，所以也为0.
     */
    public static void title3(int num) {
        System.out.println(Integer.toBinaryString(num) + "--->" + Integer.toBinaryString(num & ((~num) + 1)));
    }

    /**
     * 一个数组中有两种数出现了奇数次，其他数都出现了偶数次，怎么找到并打印这两种数
     * 思路：偶数次的异或为0，所以如果全部异或起来结果等于这两个奇数次的数的异或
     * 找到这个a^b的一个1，由于是异或这一位肯定是0^1=1,所以通过这一位是0是1可以将原来的数组分成两块
     * 这两块中各有一个出现了奇数次的和若干个偶数次的
     * [3,4,4,3,3,5,4,5]-->全部异或结果：3^4=0011^0100=0111
     * 最后一位是1的：3,5
     * 最后一位是0的：4
     * 这样将它们分开了就能找出来了
     */
    public static void title4(int[] nums) {
        int eor = nums[0];
        for (int i = 1; i < nums.length; i++)
            eor ^= nums[i];
        //eor = a^b,抓个1出来
        int lastOne = eor & ((~eor) + 1);
        int eor1 = 0;
        for (int i = 0; i < nums.length; i++)
            if ((nums[i] & lastOne) != 0) eor1 ^= nums[i];
        //eor1=a或者eor1=b
        Logger.getGlobal().info("这两个数a,b分别为：a:" + eor1 + ",b:" + (eor ^ eor1));
    }

    /**
     * 1) 在一个有序数组中，找某个数是否存在
     *
     * @param nums
     * @param e    需要找的数
     *             二分法初级使用
     */
    public static void title5(int[] nums, int e) {
        int l = 0, r = nums.length - 1;
        int index = -1;
        while (l <= r) {
            ///int mid = (l+r)/2;//这样可能会溢出，重新思考计策：mid=l+(r-l)/2==>l+((r-l)<<1)
            int mid = l + ((r - l) >> 1);
            if (nums[mid] == e) {
                index = mid;
                break;
            } else if (nums[mid] > e) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        Logger.getGlobal().info(index == -1 ? "不存在" : ("下标为：" + index));
    }


}
