package waigo.zuosheng.MyCode;

import java.util.*;

/**
 * author waigo
 * create 2021-07-16 23:12
 */
public class Code207_Course {
    public static class Node{
        int in;
        List<Node> next;
        int course;
        public Node(){
            next = new ArrayList<>();
        }
    }
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        if(prerequisites==null||prerequisites.length==0) return true;
        //拓扑排序
        int N = prerequisites.length;
        Node[] nodes = new Node[numCourses];//i-->第i门课
        int needLearn = 0;
        for(int i = 0;i<N;i++){
            int[] course = prerequisites[i];
            int cur = course[0];
            int pre = course[1];
            if(nodes[cur] == null){
                nodes[cur] = new Node();
                needLearn++;
            }
            if(nodes[pre]==null){
                nodes[pre] = new Node();
                needLearn++;
            }


            nodes[cur].in++;
            nodes[pre].next.add(nodes[cur]);
            nodes[cur].course = cur;
        }
        int resolveCourse = 0;
        Queue<Node> zeroInQueue = new LinkedList<>();
        for(int i = 0;i<numCourses;i++){
            if(nodes[i]!=null&&nodes[i].in==0)
                zeroInQueue.add(nodes[i]);
        }
        while(!zeroInQueue.isEmpty()){
            Node curCourse = zeroInQueue.poll();
            resolveCourse++;
            for(Node next:curCourse.next){
                if(--next.in==0){
                    zeroInQueue.add(next);
                }
            }
        }
        return resolveCourse==needLearn;
    }
}
