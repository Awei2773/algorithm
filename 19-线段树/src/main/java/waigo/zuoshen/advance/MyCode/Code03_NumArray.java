package waigo.zuoshen.advance.MyCode;

/**
 * author waigo
 * create 2021-03-24 23:20
 */
//https://leetcode-cn.com/problems/range-sum-query-mutable/
/*给你一个数组 nums ，请你完成两类查询，其中一类查询要求更新数组下标对应的值，另一类查询要求返回数组中某个范围内元素的总和。
实现 NumArray 类：
NumArray(int[] nums) 用整数数组 nums 初始化对象
void update(int index, int val) 将 nums[index] 的值更新为 val
int sumRange(int left, int right) 返回子数组 nums[left, right] 的总和（即，nums[left] + nums[left + 1], ..., nums[right]）*/
public class Code03_NumArray {
    public static class NumArray {
        int MAXN;
        int[] sum;
        int[] arr;

        public NumArray(int[] nums) {
            this.arr = nums;
            MAXN = nums.length << 2;
            sum = new int[MAXN];
            build(0, arr.length - 1, 1);
        }

        private void build(int l, int r, int rt) {
            if (l == r) {
                sum[rt] = arr[l];
                return;
            }
            int mid = l + ((r - l) >> 1);
            build(l, mid, rt << 1);
            build(mid + 1, r, rt << 1 | 1);
            pushUp(rt, rt << 1, rt << 1 | 1);
        }

        private void pushUp(int rt, int lc, int rc) {
            sum[rt] = sum[lc] + sum[rc];
        }

        //nums[index] to val,本题只有单个位置的更新，所以不用维护更新表
        public void update(int index, int val) {
            update(index, index, val, 0, arr.length - 1, 1);
        }

        private void update(int l, int r, int C, int L, int R, int rt) {
            if (l <= L && r >= R) {//必定是l==L==R==r
                sum[rt] = C;
                return;
            }
            //下发任务
            int mid = L + ((R - L) >> 1);
            if (l <= mid) {
                update(l, r, C, L, mid, rt << 1);
            }
            if (r > mid) {
                update(l, r, C, mid + 1, R, rt << 1 | 1);
            }
            pushUp(rt, rt << 1, rt << 1 | 1);
        }


        public int sumRange(int left, int right) {
            return query(left, right, 0, arr.length - 1, 1);
        }

        private int query(int l, int r, int L, int R, int rt) {
            if (l <= L && r >= R) {
                return sum[rt];
            }
            //任务下发
            int mid = L + ((R - L) >> 1);
            int ans = 0;
            if (l <= mid) {
                ans += query(l, r, L, mid, rt << 1);
            }
            if (r > mid) {
                ans += query(l, r, mid + 1, R, rt << 1 | 1);
            }
//            pushUp(rt,rt<<1,rt<<1|1);沿途都没懒任务，不用进行更新操作
            return ans;
        }
    }

    public static void main(String[] args) {
        NumArray numArray = new NumArray(new int[]{1, 2, 3});
        numArray.update(0, 3);
        numArray.update(1, 3);
        numArray.update(0, 4);
        numArray.update(2, 3);
        numArray.update(1, 2);
        System.out.println(numArray.sumRange(1,2));
    }
}
