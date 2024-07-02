package waigo.zuoshen.advance.MyCode;

import java.util.*;

/**
 * author waigo
 * create 2021-03-25 11:17
 */
@SuppressWarnings("Duplicates")
public class Code02_FallingSquares_m2 {
    public static class SegmentTree {
        private final int[][] positions;
        private HashMap<Integer, Integer> truthToVirtual;//对应下标
        private int MAXN;
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
            max = new int[MAXN];
            change = new int[MAXN];
        }

        private List<Integer> fallingAll() {
            List<Integer> ans = new ArrayList<>();
            int l,r;
            int max = 0;
            for (int[] square : positions) {
                l = truthToVirtual.get(square[0]);
                r = truthToVirtual.get(square[0] + square[1] - 1);
                int height = query(l, r, 0, N - 1, 1)+square[1];
                ans.add(max = Math.max(height ,max));
                update(l,r,height,0,N-1,1);
            }
            return ans;
        }


        //从L...R区间下发任务
        private void pushDown(int rt) {
            int leftChild = rt << 1;
            if (change[rt]!=0) {//下发更新任务
                change[leftChild] = change[rt];
                change[leftChild | 1] = change[rt];
                max[leftChild] = change[rt];
                max[leftChild | 1] = change[rt];
                change[rt] = 0;
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
