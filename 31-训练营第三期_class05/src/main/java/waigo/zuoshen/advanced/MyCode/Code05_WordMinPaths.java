package waigo.zuoshen.advanced.MyCode;

/**
 * author waigo
 * create 2021-05-16 9:59
 */

import java.util.*;

/**
 *给定两个字符串，记为start和to，再给定一个字符串列表list，list中一定包含to list中没有重复字符串，所有的字符串都是小写的。
 * 规定: start每次只能改变一个字符，最终的目标是彻底变成to，但是每次变成的新字符串必须在list 中存在。
 * 请返回所有最短的变换路径。
 * 【举例】
 * start="abc",end="cab",list={"cab","acc","cbc","ccc","cac","cbb","aab","abb"}
 * 转换路径的方法有很多种，但所有最短的转换路径如下:
 * abc -> abb -> aab -> cab
 * abc -> abb -> cbb -> cab
 * abc -> cbc -> cac -> cab
 * abc -> cbc -> cbb -> cab
 *
 *  思路：
 *  1.分析题目：
 *  出现了start->str只能改变一个字符，最终要start变成to，start每次变化后必须在list中，最终问start到to有多少条最短路径
 *  2.算法选型：
 *  通过分析题目可以发现，这就是问你一张图，从一个节点到另一个节点有多少条最小路径的问题。所以这题将每个字符串看成一个节点，
 *  采用图的方式来做。
 *  3.构图
 *  图的存储方式有很多，这里我们需要知道当走到某个字符串之后下一步能走到哪，所以需要邻接表，邻居可以直达
 *  实现方式：
 *  将start丢进list中，这个list去构出一张邻接表，这个邻接表就是最后画好的图
 *  4.找最小路径
 *  start->to的最小路径必然不止一条，所以需要先计算出start到to的最小路径是多少，这样之后在dfs的时候以这个路径为标准，只要是
 *  路径和这个相等的时候且结尾为to，则表示收到了一条最短路径
 *  两个节点间的路径可能有很多，所以start到每个节点的最小路径都得算出来，为了之后对长路径进行剪枝
 *
 *  实现方式：
 *  1.dijkstra，这个稍显得重了一点，因为点到点的权值都是1，所以已经处理过的节点随便哪个拿出来都是最短路径，不需要再遍历了
 *  2.根据本题两点之间权值为1的特点，其实我们通过一个bfs就能够完成最小路径表的求解了。
 *  从start开始，存入start会唤醒start直接连接的点，start到这些点的距离肯定是最小的，而且都等于1
 *  那么对这些点进行一个宽度优先遍历，肯定能够对第二层的节点完成最小路径的求解，底层思路和dijkstra是一样的
 *
 *  5.dfs收答案
 *  从start开始深度优先遍历整张图，只要是路径等于start到to的最短路径且当前来到了to节点那么说明找到一个答案。
 *
 */
public class Code05_WordMinPaths {
    public static List<List<String>> findMinPaths(String start, String to, List<String> list) {
        list.add(start);
        //1.邻接表,构图
        Map<String,List<String>> nexts =  getNextList1(list);
        //2.最小距离表
        Map<String,Integer> distances = getDistance1(start,nexts);
        //3.dfs收答案
        List<List<String>> res = new ArrayList<>();
        dfsGetResult(start,new ArrayList<>(),to,nexts,distances,res, distances.get(to));
        return res;
    }
    //当前在cur,当前走过的轨迹为path,想要前往to,每个节点的邻居在nexts中,点到点的距离存在distances中
    public static void dfsGetResult(String cur, List<String> path, String to, Map<String,
            List<String>> nexts, Map<String, Integer> distances,
                                    List<List<String>> res, Integer toDistance) {
        //遍历cur的所有邻居
        List<String> neighbours = nexts.get(cur);
        path.add(cur);//走到当前节点
        if(path.size()-1== toDistance &&cur.equals(to)){
            res.add(new ArrayList<>(path));
        }
        //来到当前节点前面已经走过的路程为path.size-1
        if(path.size()-1 < toDistance&&neighbours!=null){//要么是走到当前位置还没有走完路程，要么是收到答案，要么是不可能收到答案了
            for (String neighbour : neighbours) {
                if(path.size()==distances.get(neighbour)){//剪枝，有走的必要才走
                    dfsGetResult(neighbour,path,to,nexts,distances,res,toDistance);
                }
            }
        }
        path.remove(path.size()-1);//轨迹擦除
    }

    //策略1:dijkstra，默认版本需要遍历找到目前距离最小的节点，优化版本使用手写改堆(维护indexMap)，直接弹出最小节点
    public static Map<String, Integer> getDistance1(String start, Map<String,List<String>> nexts) {
        Set<String> visitedStr = new HashSet<>();
        Map<String,Integer> distanceMap = new HashMap<>();
        distanceMap.put(start,0);
        while (true){
            int minimumDistance = Integer.MAX_VALUE;
            String minimumNode = null;
            //找当前最小距离
            Set<Map.Entry<String, Integer>> entries = distanceMap.entrySet();
            for (Map.Entry<String, Integer> entry : entries) {
                if(!visitedStr.contains(entry.getKey())&&entry.getValue()<minimumDistance){
                    minimumDistance= entry.getValue();
                    minimumNode = entry.getKey();
                }
            }
            if(minimumDistance==Integer.MAX_VALUE) break;
            //把最小点逛一遍
            List<String> neighbours = nexts.get(minimumNode);
            for (String next : neighbours) {
                if(!distanceMap.containsKey(next)){
                    distanceMap.put(next,minimumDistance+1);
                }
            }
            //锁起来
            visitedStr.add(minimumNode);
        }
        return distanceMap;
    }


    //策略2:BFS,根据此题特殊性对dijkstra进行了贪心的优化
    public static Map<String, Integer> getDistance2(String start, Map<String,List<String>> nexts) {
        Map<String, Integer> distanceMap = new HashMap<>();
        distanceMap.put(start,0);
        Queue<String> bfsQueue = new LinkedList<>();//BFS
        bfsQueue.add(start);
        while (!bfsQueue.isEmpty()){
            String cur = bfsQueue.poll();//肯定是最小路径，按照此题权值1对dijkstra进行了优化
            for (String next : nexts.get(cur)) {
                if(!distanceMap.containsKey(next)){
                    distanceMap.put(next, distanceMap.get(cur) + 1);
                    bfsQueue.add(next);
                }
            }
        }
        return distanceMap;
    }
    //对list中的字符串进行邻居字符串的求解
    //是否邻居的两种判断策略：
    //1.遍历两个字符串，如果相差字符只有1，那么就是邻居,时间复杂度O(k*N^2),k是字符串的长度,必须得遍历，因为要找两个字符串遍历完才知道是不是邻居
    //2.字符串A进行枚举
    //比如说:A=abc,枚举bbc   aac  aba时间复杂度O(k^2*N)具体选择哪种，看K和N的大小,使用hash表就不用遍历了
    //               cbc   acc  abb
    //               dbc   adc  abd
    //               ...   ...  ...
    //               zbc   azc  abz

    //策略1:N^2*k
    public static Map<String, List<String>> getNextList1(List<String> list) {
        Map<String, List<String>> nexts = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            String curStr = list.get(i);
            nexts.put(curStr,new ArrayList<>());
            for (int j = 0; j < list.size(); j++) {//N^2
                String target = list.get(j);
                int diff = 0;
                for(int z = 0;z<curStr.length();z++){//k
                    if(curStr.charAt(z)!=target.charAt(z)) diff++;
                    if(diff>1) break;
                }
                if(diff==1){
                    nexts.get(curStr).add(target);
                }
            }
        }
        return nexts;
    }
    //策略2:N*K^2
    public static Map<String, List<String>> getNextList2(List<String> list) {
        Map<String, List<String>> nexts = new HashMap<>();
        Set<String> strSet = new HashSet<>(list);
        for (int i = 0; i < list.size(); i++) {//N
            String curStr = list.get(i);
            nexts.put(curStr,new ArrayList<>());
            char[] curChars = curStr.toCharArray();
            for(int j = 0;j<curStr.length();j++){
                for(char c = 'a';c<='z';c++){//25k
                    if(curChars[j]!=c){
                        char temp = curChars[j];
                        curChars[j] = c;
                        String target = String.valueOf(curChars);
                        if(strSet.contains(target)){//计算hashCode k
                            nexts.get(curStr).add(target);
                        }
                        curChars[j] = temp;
                    }
                }
            }
        }
        return nexts;
    }

}
