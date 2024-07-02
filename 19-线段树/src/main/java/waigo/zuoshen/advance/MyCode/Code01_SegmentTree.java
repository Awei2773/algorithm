package waigo.zuoshen.advance.MyCode;

//线段树解决问题：线段树按照功能来说应该叫区间修改树，就是在一个区间中进行整体加一个数，整体更新成某个数，查询这个区间的元素和


@SuppressWarnings("Duplicates")
public class Code01_SegmentTree {
    public static class SegmentTree {//为了能够使用位运算加速求左右孩子，所有数据的下标0位置都弃之不用，从1开始，这样左孩子就是i*2,右孩子就是i*2+1
        int[] lazy;//核心点：懒更新机制，有任务下发不会全部更新而是会懒住
        int[] change;//更新任务的值，就是更新成多少
        boolean[] update;//某个位置是否有更新，可能之前有个更新任务懒住了之后还没下发又来了一些增加任务，得区分这两种，
        int[] sum;//区间和，这个可以拓展成一些其他区间整合的值
        int[] arr;//原先的数组数据
        int MAXN;

        public SegmentTree(int[] arr) {
            this.arr = arr;//将数组中的元素按照序号完成1~N的线段树初始化
            MAXN = arr.length << 2;//4N绝不会溢出

            lazy = new int[MAXN];
            change = new int[MAXN];
            update = new boolean[MAXN];
            sum = new int[MAXN];

            build(0, arr.length - 1, 1);
        }

        //完成[l...r]数据填入sum，l~r的sum填入ti
        private void build(int l, int r, int ti) {
            if (l == r) {//直接填，base case
                sum[ti] = arr[l];
                return;
            }
            int mid = ((r - l) >> 1) + l;//防止加法溢出，表达的还是(l+r)/2,要是奇数个就中点，偶数个就上中点
            //左子树生成
            build(l, mid, ti << 1);//0位置留空，所以左孩子为2*i
            //右子树生成
            build(mid + 1, r, ti << 1 | 1);
            pushUp(ti, ti << 1, ti << 1 | 1);
        }

        //lc:左孩子的值，rc:右孩子的值，ti父节点的值
        //从左右两个区间合成整个大区间的值
        private void pushUp(int ti, int lc, int rc) {
            sum[ti] = sum[lc] + sum[rc];
        }

        //[l...r]内所有元素都加C
        //[L...R]当前来到的区间,ti表示这个LR区间的值存储下标
        private void add(int l, int r, int C, int L, int R, int ti) {
            if (l <= L && r >= R) {//可以被当前区间完全懒住，就不下发了
                lazy[ti] += C;
                sum[ti] += (R - L + 1) * C;//每个元素都加C，整体就是数量*C的增幅
                return;
            }
            //懒不住就下发,除了现在的这个添加任务可能之前还懒着一些任务，一并更新到下级
            int mid = L + ((R - L) >> 1);
            pushDown(L, R, ti, mid);
            if (l <= mid) {//有分在左边的
                add(l, r, C, L, mid, ti << 1);
            }
            if (r > mid) {//有分在右边的
                add(l, r, C, mid + 1, R, ti << 1 | 1);
            }
            pushUp(ti, ti << 1, ti << 1 | 1);
        }

        public int query(int l, int r) {
            if (l < 1 || r > arr.length || l > r) return Integer.MIN_VALUE;
            return query(l - 1, r - 1, 0, arr.length - 1, 1);
        }

        //返回l...r上的元素和，现在到了L...R区间，这个区间的值去ti找
        private int query(int l, int r, int L, int R, int ti) {
            if (l <= L && r >= R) {
                return sum[ti];
            }
            //当前懒不住了，将任务下发
            int mid = L + ((R - L) >> 1);
            pushDown(L, R, ti, mid);//任务下发
            int ans = 0;
            if (l <= mid) {//下发到左边
                ans += query(l, r, L, mid, ti << 1);
            }
            if (r > mid) {//下发到右边
                ans += query(l, r, mid + 1, R, ti << 1 | 1);
            }
            return ans;
        }

        //l...r上的数都变成C
        private void update(int l, int r, int C, int L, int R, int ti) {
            if (l <= L && r >= R) {//懒住了
                update[ti] = true;
                change[ti] = C;//存下更新操作的值
                lazy[ti] = 0;//更新操作优先级最高，前面的添加操作全部清空了
                sum[ti] = (R - L + 1) * C;
                return;
            }
            //懒不住了，下发
            int mid = L + ((R - L) >> 1);
            pushDown(L, R, ti, mid);
            if (l <= mid) {
                update(l, r, C, L, mid, ti << 1);
            }
            if (r > mid) {
                update(l, r, C, mid + 1, R, ti << 1 | 1);
            }
            pushUp(ti, ti << 1, ti << 1 | 1);
        }
        public void update(int l,int r,int C){
            if (l < 1 || r > arr.length || l > r) return;
            update(l-1,r-1,C,0,arr.length-1,1);
        }
        private void pushDown(int L, int R, int ti, int mid) {
            int leftChild = ti << 1;
            int rightChild = leftChild | 1;
            if (update[ti]) {//如果之前有更新操作,此时lazy里存的肯定是更新操作之后的了
                //更新change
                change[rightChild] = change[ti];
                change[leftChild] = change[ti];
                change[ti] = 0;//下发了就不用懒着了
                //更新lazy
                lazy[leftChild] = 0;
                lazy[rightChild] = 0;
                //更新sum
                sum[leftChild] = change[leftChild] * (mid - L + 1);
                sum[rightChild] = change[rightChild] * (R - mid);
                //下级给我自己懒住
                update[ti] = false;
                update[leftChild] = true;
                update[rightChild] = true;
            }
            //添加任务下发
            if (lazy[ti] != 0) {
                //更新lazy
                lazy[leftChild] += lazy[ti];
                lazy[rightChild] += lazy[ti];
                //更新sum,这里这里一定增加的幅度是lazy[ti]的，因为此前懒住的已经添加过了，盯着debugger一晚上才发现的，哭了
                sum[leftChild] += lazy[ti] * (mid - L + 1);
                sum[rightChild] += lazy[ti] * (R - mid);
                lazy[ti] = 0;//清除懒住的任务，因为已经下发了
            }
        }

        //l..r表示的是序号，要转成下标
        public void add(int l, int r, int C) {
            if (l < 1 || r > arr.length || l > r) return;
            add(l - 1, r - 1, C, 0, arr.length - 1, 1);
        }

    }
    public static class Right{
        int[] arr;
        public Right(int[] arr){
            this.arr = arr;
        }
        public void add(int l,int r,int C){
            if (l < 1 || r > arr.length || l > r) return;
            for(int i = l-1;i<r;i++){
                arr[i]+=C;
            }
        }
        public void update(int l,int r,int C){
            if (l < 1 || r > arr.length || l > r) return;
            for(int i = l-1;i<r;i++){
                arr[i] = C;
            }
        }
        public int query(int l,int r){
            if (l < 1 || r > arr.length || l > r) return Integer.MIN_VALUE;
            int ans = 0;
            for(int i = l-1;i<r;i++)
                ans+=arr[i];
            return ans;
        }
    }
    public static int[][] generalRandomArr(int maxLen,int maxVal){
        int len = (int) (Math.random()*maxLen)+1;
        int[] res1 = new int[len];
        int[] res2 = new int[len];
        for(int i = 0;i<len;i++){
            res1[i] = (int) (Math.random()*maxVal);
            res2[i] = res1[i];
        }
        return new int[][]{res1,res2};
    }
    public static int[] generalRandomSection(int arrLen){
        int l = (int) (Math.random()*arrLen)+1;
        int r = (int) (Math.random()*arrLen)+1;
        if(l>r){
            l^=r;
            r^=l;
            l^=r;
        }
        return new int[]{l,r};
    }
    public static void main(String[] args) {
        int maxLen = 4,maxVal = 15,testTimes = 1000,oprTimes = 10000;
        int[][] res = generalRandomArr(maxLen, maxVal);
        int i = 0;
        for(;i<testTimes;i++){
            int[] rArr1 = res[0];
            int[] rArr2 = res[1];
//            System.out.println(Arrays.toString(rArr1));
            Right right = new Right(rArr1);
            SegmentTree segmentTree = new SegmentTree(rArr2);
            for(int j = 0;j<oprTimes;j++){
                int[] section = generalRandomSection(rArr1.length);
                int C = (int) (Math.random()*maxVal);
                double dict = Math.random();
                if(dict<0.5){
                    right.update(section[0],section[1],C);
                    segmentTree.update(section[0],section[1],C);
//                    System.out.println("update:["+section[0]+","+section[1]+","+C+"]");
                }else{
                    right.add(section[0],section[1],C);
                    segmentTree.add(section[0],section[1],C);
//                    System.out.println("add:["+section[0]+","+section[1]+","+C+"]");
                }
            }
            boolean isOop = false;
            for(int j = 0;j<oprTimes;j++){
                int[] section = generalRandomSection(rArr1.length);
                int rightRes = right.query(section[0], section[1]);
                int treeRes = segmentTree.query(section[0], section[1]);
                if(rightRes!=treeRes) {
                    System.out.println("oops!!!");
                    isOop = true;
                    break;
                }
            }
            if(isOop) break;
        }
        System.out.println(i==testTimes?"finish!!!":"fucking!!!");
        /*int[] arr = new int[]{5,4};
        int[] arr1 = new int[]{5,4};
        SegmentTree tree = new SegmentTree(arr);
        Right right = new Right(arr);
        tree.update(2,2,1);
        tree.update(1,2,0);
        tree.add(1,2,8);
        tree.update(2,2,3);
        tree.update(2,2,9);
        tree.update(1,2,2);
        tree.add(1,2,4);
        tree.add(1,1,10);
        tree.add(1,2,12);
        tree.update(2,2,2);*/

    }
}
