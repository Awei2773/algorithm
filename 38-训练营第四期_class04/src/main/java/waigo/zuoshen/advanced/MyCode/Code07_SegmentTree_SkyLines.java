package waigo.zuoshen.advanced.MyCode;


import java.util.*;

/**
 * author waigo
 * create 2021-06-21 10:12
 */
//将所有building当做之前的矩形falling下来，存每个地域的最高点，矩形也像之前一样处理，存左边不存右边，
//这样右边就能模拟一个down的样子，高度为0，这个线段树只有更新和查找行为,而天际线需要的是单点最大值，只需要
//懒住更新操作就好了，也就是说只有change和update表以及区域的max值
//注意这里的更新它不是一般的更新，它是下发一个数字，对区间的max进行更新
//1.下标离散化，只需要存开始和结束坐标，所以先对buildings进行处理，两边x坐标入treeSet，然后出来创建truthToVirtual映射
//2.所有buildings falling down结束之后遍历所有点的max抓天际线就完事了
public class Code07_SegmentTree_SkyLines {
    public static class SegmentTree{
        //buildings 房子数据
        int[][] buildings;
        //更新
        int[] change;
        boolean[] update;
        //max
        int[] max;
        private TreeMap<Integer, Integer> truthToVirtual;
        private int N;//数组长度，有几个不同的坐标

        public SegmentTree(int[][] buildings){
            this.buildings = buildings;
            //下标离散化
            TreeSet<Integer> treeSet = new TreeSet<>();
            for (int[] building : buildings) {
                treeSet.add(building[0]);//left
                treeSet.add(building[1]);//right
            }
            //key=truth(like 10,20000)-->value=virtual(like 0,1)
            N = 0;
            truthToVirtual = new TreeMap<>();
            while (!treeSet.isEmpty()){
                truthToVirtual.put(treeSet.pollFirst(), N++);
            }

            int MAXN = N <<2;
            change = new int[MAXN];
            update = new boolean[MAXN];
            max = new int[MAXN];
        }

        public List<List<Integer>> getSkyline() {
            List<List<Integer>> res = new ArrayList<>();
            this.fallingAll();
            //抓天际线
            int last = 0;
            while (!truthToVirtual.isEmpty()){
                Map.Entry<Integer, Integer> entry = truthToVirtual.pollFirstEntry();
                int idx = entry.getValue();
                int queryMax = query(idx, idx,0,N-1,1);
                if(queryMax!=last){
                    res.add(Arrays.asList(entry.getKey(),queryMax));
                    last = queryMax;
                }
            }
            return res;
        }

        private int query(int l, int r, int L, int R, int it) {
            if(l<=L&&r>=R){
                return max[it];
            }
            //任务下发
            pushDown(it);
            int mid = ((R-L)>>1)+L;
            int leftChild = it<<1;
            int maxQuery = Integer.MIN_VALUE;
            int rightChild = leftChild | 1;
            if(l<=mid){
                maxQuery = Math.max(query(l,r,L,mid,leftChild),maxQuery);
            }
            if(r>mid){
                maxQuery = Math.max(query(l,r,mid+1,R, rightChild),maxQuery);
            }
            pushUp(it,leftChild,rightChild);
            return maxQuery;
        }

        private void fallingAll() {
            for (int[] building : buildings) {
                int left = truthToVirtual.get(building[0]);
                int right = truthToVirtual.get(building[1]);
                int height = building[2];
                //不包尾，全更新成height,和原来上面的值进行权衡，留大的那个
                this.update(left, Math.max(left,right-1), height,0,N-1,1);
            }
        }
        //l...r更新为c，此时在L..R范围上,L...R的值存在it上
        private void update(int l,int r,int c, int L, int R,int it) {
            if(l<=L&&r>=R){
                //可以懒住
                update[it] = true;
                change[it] = Math.max(change[it],c);
                max[it] = Math.max(max[it],c);
                return;
            }
            //懒不住，任务下发
            int mid = ((R-L)>>1)+L;
            pushDown(it);
            int leftChild = it << 1;
            int rightChild = leftChild | 1;
            if(l<=mid){
                update(l,r,c,L,mid, leftChild);
            }
            if(r>mid){
                update(l,r,c,mid+1,R, rightChild);
            }
            pushUp(it,leftChild,rightChild);
        }


        private void pushUp(int it,int left,int right) {
            max[it] = Math.max(max[left],max[right]);
        }

        //将任务下发到左右分区
        private void pushDown(int it) {
            int leftChild = it<<1;//规定0下标不存东西，这样左孩子就是2*i,
            int rightChild = leftChild|1;
            if(update[it]){
                max[leftChild]=Math.max(max[leftChild],change[it]);
                max[rightChild]=Math.max(max[rightChild],change[it]);
                update[it]=false;
                update[leftChild]=true;
                update[rightChild]=true;
                change[leftChild] = Math.max(change[leftChild],change[it]);
                change[rightChild] = Math.max(change[rightChild],change[it]);
                change[it] = 0;
            }
        }
    }
    public static List<List<Integer>> getSkyline(int[][] buildings) {
        if(buildings==null||buildings.length==0) return new ArrayList<>();
        return new SegmentTree(buildings).getSkyline();
    }

    public static void main(String[] args) {
        int[][] buildings =  {{2,9,10},{3,7,15},{5,12,12},{15,20,10},{19,24,8}};
        System.out.println(getSkyline(buildings));
        List<Integer> list = new ArrayList<>();
        list.clear();
    }

}
