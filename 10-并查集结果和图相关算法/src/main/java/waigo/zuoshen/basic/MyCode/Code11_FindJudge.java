package waigo.zuoshen.basic.MyCode;

import java.util.HashMap;
import java.util.HashSet;

/**
 * author waigo
 * create 2021-03-23 13:29
 */
/*
在一个小镇里，按从 1 到 N 标记了 N 个人。传言称，这些人中有一个是小镇上的秘密法官。

如果小镇的法官真的存在，那么：

小镇的法官不相信任何人。
每个人（除了小镇法官外）都信任小镇的法官。
只有一个人同时满足属性 1 和属性 2 。
给定数组 trust，该数组由信任对 trust[i] = [a, b] 组成，表示标记为 a 的人信任标记为 b 的人。

如果小镇存在秘密法官并且可以确定他的身份，请返回该法官的标记。否则，返回 -1。

思路：就是在一个有向图中找到一个入度为N-1，出度为0的点如果有返回它编号，没有返回-1
*/
public class Code11_FindJudge {
    public static class Node{
        private int val;
        private int in;
        private int out;

        public Node(int val) {
            this.val = val;
        }
    }
    public static int findJudge(int N, int[][] trust) {
        if(N==1) return 1;
        Node[] nodes = new Node[N];//0位置存着1号人节点
        for(int i = 0;i < trust.length;i++){
            int from = trust[i][0];//1号去0下标找节点
            int to = trust[i][1];
            if(nodes[from-1]==null){
                nodes[from-1] = new Node(from);
            }
            if(nodes[to-1]==null){
                nodes[to-1]=new Node(to);
            }
            nodes[from-1].out++;
            nodes[to-1].in++;
        }
        int res = -1;
        for(int i = 0;i<nodes.length;i++){
            if(nodes[i]!=null&&nodes[i].in==N-1&&nodes[i].out==0){
                res = nodes[i].val;
                break;
            }
        }
        return res;
    }
    public static int findJudge1(int N, int[][] trust) {
        if(N==1) return 1;
        int res = -1;
        int[] inDegree = new int[N];//0下标存的是1号人的入度
        int[] outDegree = new int[N];//0下标存的是1号人的出度
        for(int i = 0;i<trust.length;i++){
            int from = trust[i][0];
            int to = trust[i][1];
            outDegree[from-1]++;
            inDegree[to-1]++;
        }
        for(int i = 0;i<N;i++){
            if(outDegree[i]==0&&inDegree[i]==N-1){
                res = i+1;
                break;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        //N = 4, trust = [[],[],[],[],[]]
        int N = 3;
        int[][] trust = {{1,3},{2,3}};
        System.out.println(findJudge1(N,trust));
    }
}
