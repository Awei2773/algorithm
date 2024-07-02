package com.waigo.nowcoder.MyCode.Code03_alibaba;


import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

/**
 * author waigo
 * create 2021-08-16 18:48
 */
public class Main2 {
    //n支部队 编号：1,2,3,4,5..n
    //要结盟
    //并查集
    //任何一个联盟中部队编号都能找到联盟头，联盟头是编号最小的那个
    //两支联盟 i,j 各有x,y名人员，需要交纳(i+j)异或|x-y|枚金币
    public static class Node{
        int nodeNum;//编号

        public Node(int nodeNum) {
            this.nodeNum = nodeNum;
        }
    }
    public static class UnionSet{
        HashMap<Node,Node> parents = new HashMap<>();//找到父
        HashMap<Node,Integer> sizeMap = new HashMap<>();//头对应集合大小
        HashMap<Integer,Node> seqToNode = new HashMap<>();//编号找到node
        public UnionSet(int n, String[] originNums){
            for(int i = 1;i<=n;i++){
                Node node = new Node(i);
                parents.put(node,node);
                sizeMap.put(node,Integer.valueOf(originNums[i-1]));
                seqToNode.put(i,node);
            }
        }
        //方法一：是否是一个联盟
        public boolean isSameSet(int a,int b){
            Node node1 = seqToNode.get(a);
            Node node2 = seqToNode.get(b);
            return findParent(node1)==findParent(node2);
        }

        private Node findParent(Node node) {
            Node parent;
            Stack<Node> nodes = new Stack<>();
            while((parent = parents.get(node))!=node){
                nodes.push(node);
                node = parent;
            }
            while(!nodes.isEmpty()){//扁平化
                parents.put(nodes.pop(),parent);
            }
            return parent;
        }

        //方法二：将两个集合合并
        public void union(int a,int b){
            if(isSameSet(a,b)) return;
            Node node1 = findParent(seqToNode.get(a));
            Node node2 = findParent(seqToNode.get(b));
            //大挂小
            Node head = node1.nodeNum<node2.nodeNum?node1:node2;
            Node other = head==node1?node2:node1;
            Integer p1 = sizeMap.get(head);
            Integer p2 = sizeMap.get(other);
            System.out.println(calc(head.nodeNum,p1,other.nodeNum,p2));
            parents.put(other,head);
            sizeMap.put(head, p1 + p2);//将人数合并
            sizeMap.remove(other);//将被合并的另一边给删去
        }
        public int calc(int i, Integer x, int j, Integer y){
            return (i+j)^Math.abs(x-y);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] s = sc.nextLine().split(" ");
        int n = Integer.valueOf(s[0]);
        int m = Integer.valueOf(s[1]);
        String[] originNums = sc.nextLine().split(" ");
        UnionSet set = new UnionSet(n,originNums);
        for(int i = 0;i<m;i++){
            //一条指令
            String[] s1 = sc.nextLine().split(" ");
            int type = Integer.valueOf(s1[0]);
            int a = Integer.valueOf(s1[1]);
            int b = Integer.valueOf(s1[2]);
            if(a==b) continue;
            if(type==1){
                //请求合并
                set.union(a,b);
            }else if(set.isSameSet(a,b)){
                //询问是否同盟
                System.out.println("YES");
            }else{
                System.out.println("NO");
            }
        }
    }
}


/*
6 9
1 2 3 3 2 1
2 6 3
1 1 1
1 6 1
2 3 6
2 1 6
1 6 3
1 1 3
2 1 3
2 6 3

*/
