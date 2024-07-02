package waigo.zuoshen.advance.MyCode;

import java.util.*;

/**
 * author waigo
 * create 2021-03-24 11:10
 */
/*
在无限长的数轴（即 x 轴）上，我们根据给定的顺序放置对应的正方形方块。
第 i 个掉落的方块（positions[i] = (left, side_length)）是正方形，其中 left 表示该方块最左边的点位置(positions[i][0])，
side_length 表示该方块的边长(positions[i][1])。每个方块的底部边缘平行于数轴（即 x 轴），并且从一个比目前所有的落地方块更高的
高度掉落而下。在上一个方块结束掉落，并保持静止后，才开始掉落新方块。方块的底边具有非常大的粘性，并将保持固定在它们所接触的任何长度
表面上（无论是数轴还是其他方块）。邻接掉落的边不会过早地粘合在一起，因为只有底边才具有粘性。返回一个堆叠高度列表 ans 。每一个堆叠
高度 ans[i] 表示在通过 positions[0], positions[1], ..., positions[i] 表示的方块掉落结束后，目前所有已经落稳的方块堆叠的最高高度。
*/
/*
1.本题考虑用线段树来解决，因为题目要问的就是每个方块落下去的时候整个区间的最大高度，每个区间的最大高度可以很容易从两个子区间来获得

2.需要用到哪些线段树的操作
2.1方法一：可以照常add改变某个区间的最大高度，这会导致如果此时方块下面不是平整的就会将方块分成多块，因为它们原先的最大高度不同，
然后掉落完了之后，你得query找出此时被抬起来的最高的那部分是多高，然后将之前掉落下去的部分给和这最高的块合并。就是找出现在的最高高度，
将l,r范围全部刷新成这个高度(update)。
2.2方法二：其实可以只用query和update操作就能完成的，因为最大高度我们可以在方块压下去之前就得到，
那就是l,r范围内现在的最高高度(query)加上此时这个方块的长度，然后再一次性的将最大高度刷新就可以了
这两个肯定方法二好了，代码量少了而且内存用少了(只用维护update的表和max表)，虽然时间复杂度相同都是O(N*logN),但是方法二常数项时间少多了

3.边界处理
模拟方块掉落，假设方块[1,2]掉落，那么会引起1,2,3位置高度升高，但是此时如果有一个[2,2]方块掉落，我们希望3位置高度不要升高，
因为之前[1,2]方块不会卡住它，所以我们将方块引起升高的区间设置为一个左闭右开区间[1,2]方块会引起[1,3)区间高度升高
也就是[x,len]方块会引起[x,x+len-1]位置高度升高，也就是add(x,x+len-1,len)
4.坐标离散化
1 <= positions.length <= 1000.
1 <= positions[i][0] <= 10^8.
1 <= positions[i][1] <= 10^6.
考察题目数据状况，将所有位置都在记录下来根本不可能，像是第一个开始坐标为1，第二个开始坐标为10^8,难道1~10^8这里面的坐标都进行记录？
其实我们根本用不到这么多位置，add操作其实就是对x,x+len-1这个区间的值进行增加，所以我们只需要所有方块的x和x+len-1坐标，对这些坐标
进行线段树的维护
4.1这些我们需要维护的坐标肯定得是从左到右按顺序排列的，像是真正的坐标一样，而且得去重，这就可以使用到有序表了TreeSet
4.2每个方块落下来的时候要拿到它的x去对应一个下标才知道到底是要哪个区间进行add，所以得整个Map<truthX,virtualX>

*/
public class Code02_FallingSquares_m1 {
    public static class SegmentTree {
        private final int[][] positions;
        private HashMap<Integer, Integer> truthToVirtual;//对应下标
        private int MAXN;
        private boolean[] update;
        private int[] lazy;
        private int[] change;
        private int[] max;
        private int N;//坐标总数

        public SegmentTree(int[][] positions) {
            this.positions = positions;
            TreeSet<Integer> treeSet = new TreeSet<>();
            for (int[] square : positions) {
                treeSet.add(square[0]);//左边界
                treeSet.add(square[0] + square[1] - 1);//右边界
            }
            //编号
            N = 0;
            truthToVirtual = new HashMap<>();
            while (!treeSet.isEmpty()) {
                truthToVirtual.put(treeSet.pollFirst(), N++);
            }
            /*for(Map.Entry<Integer,Integer> i:truthToVirtual.entrySet()){
                System.out.println("truthIdx:"+i.getKey()+",virtualIdx"+i.getValue());
            }*/
            //初始化各表
            MAXN = N << 2;
            update = new boolean[MAXN];
            max = new int[MAXN];
            lazy = new int[MAXN];
            change = new int[MAXN];
        }

        private List<Integer> fallingAll() {
            List<Integer> ans = new ArrayList<>();
            int l,r;
            for (int[] square : positions) {
                l = truthToVirtual.get(square[0]);
                r = truthToVirtual.get(square[0] + square[1] - 1);
//                方法一：较慢，就是先让方块落下去，这时候方块可能分成了几块，导致高度不一样，被原先最高高度抬高的部分形成了现在的最高高度
                //找出现在的最高高度，将l,r范围全部刷新成这个高度，其实可以只用query和update操作就能完成的，因为最大高度我们可以在方块
                //压下去之前就得到，那就是l,r范围内现在的最高高度(query)加上此时这个方块的长度，然后再一次性的将最大高度刷新就可以了
//                add(l, r, square[1], 0, N - 1, 1);
//                update(l,r,query(l,r,0,N-1,1),0,N-1,1);
                ans.add(max[1]);
            }
            return ans;
        }

        //l...r区间加c，此时在L...R区间，rt指向L...R区间的值
        private void add(int l, int r, int c, int L, int R, int rt) {
            if (l <= L && r >= R) {
                lazy[rt] += c;//懒住
                max[rt] += c;//整个区间升高
                return;
            }
            //懒不住，任务下发
            int mid = L + ((R - L) >> 1);
            pushDown(rt);
            if (l <= mid) {
                add(l, r, c, L, mid, rt << 1);
            }
            if (r > mid) {
                add(l, r, c, mid + 1, R, rt << 1 | 1);
            }
            pushUp(rt, rt << 1, rt << 1 | 1);
        }

        //从L...R区间下发任务
        private void pushDown(int rt) {
            int leftChild = rt << 1;
            if (update[rt]) {//下发更新任务
                update[leftChild] = true;
                update[leftChild | 1] = true;
                max[leftChild] = change[rt];
                max[leftChild | 1] = change[rt];
                lazy[leftChild] = 0;
                lazy[leftChild |1] = 0;
                change[leftChild] = change[rt];
                change[leftChild |1] = change[rt];
                update[rt] = false;
            }
            if(lazy[rt]!=0){//下发add任务
                lazy[leftChild]+=lazy[rt];
                lazy[leftChild|1]+=lazy[rt];
                max[leftChild]+=lazy[rt];
                max[leftChild|1]+=lazy[rt];
                lazy[rt] = 0;
            }
        }

        private void pushUp(int rt, int lc, int rc) {
            //当前区间最高高度肯定是两个子区间中更高那个
            max[rt] = Math.max(max[lc], max[rc]);
        }
        private int query(int l,int r,int L,int R,int rt){
            if (l <= L && r >= R) {
                return max[rt];
            }
            //懒不住，任务下发
            int mid = L + ((R - L) >> 1);
            pushDown(rt);
            int ans = 0;
            if (l <= mid) {
                ans = Math.max(query(l, r, L, mid, rt << 1),ans);
            }
            if (r > mid) {
                ans = Math.max(query(l, r,  mid + 1, R, rt << 1 | 1),ans);
            }
            pushUp(rt, rt << 1, rt << 1 | 1);
            return ans;
        }
        private void update(int l,int r,int c,int L,int R,int rt){
            if (l <= L && r >= R) {
                change[rt] = c;//懒住
                update[rt] = true;
                lazy[rt] = 0;//之前的添加任务全部取消
                max[rt] = c;
                return;
            }
            //懒不住，任务下发
            int mid = L + ((R - L) >> 1);
            pushDown(rt);
            if (l <= mid) {
                update(l, r, c, L, mid, rt << 1);
            }
            if (r > mid) {
                update(l, r, c, mid + 1, R, rt << 1 | 1);
            }
            pushUp(rt, rt << 1, rt << 1 | 1);
        }
    }

    public static void main(String[] args) {
        int[][] pro = {{9, 7}, {1, 9}, {3, 1}};
        SegmentTree segmentTree = new SegmentTree(pro);
        List<Integer> max = segmentTree.fallingAll();
        System.out.println(Arrays.toString(max.toArray()));

    }
}
