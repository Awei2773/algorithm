package waigo.zuosheng.advanced.MyCode;

/**
 * author waigo
 * create 2021-07-04 8:57
 */

import java.util.*;

/**
 * 项目有四个信息:
 * 1)哪个项目经理提的
 * 2)被项目经理润色出来的时间点
 * 3)项目优先级
 * 4)项目花费的时间
 * 项目经理们可以提交项目给程序员们，程序员可以做这些项目。
 * 比如长度为4的数组[1, 3, 2, 2]，表示1号项目经理提的，被项目经理润色出来的时间点是3，
 * 优先级2，花 费程序员2个时间。
 * 所以给一个N*4的矩阵，就可以代表N个项目。 给定一个正数pm，表示项目经理的数量，
 * 每个项目经理只负责自己的那些项目，并且一次只能提交一个项目 给程序员们，这个提交的项目做完了，
 * 才能再次提交。 经理对项目越喜欢，就会越早提交。
 *          一个项目优先级越高越被喜欢;
 *          如果优先级一样，花费时间越少越喜欢;
 *          如果还一样，被项目经理润色出来的时间点越早越喜欢。
 * 给定一个正数sde表示程序员的数量，所有经理提交了的项目，程序员会选自己喜欢的项目做，
 * 每个人做 完了一个项目，
 * 然后才会再来挑选。 当程序员在挑选项目时，有自己的喜欢标准。
 *          一个项目花费时间越少越被喜欢;
 *          如果花费时间一样，该项目 的负责人编号越小越被喜欢。
 * 返回一个长度为N的数组，表示N个项目的结束时间。
 * 比如:
 * int pms = 2;
 * int sde = 2;
 * int[][] programs = {
 *                      { 1, 1, 1, 2 },优先级越小，表示这个项目越优先
 *                      { 1, 2, 1, 1 },
 *                      { 1, 3, 2, 2 },
 *                      { 2, 1, 1, 2 },
 *                      { 2, 3, 5, 5 }
*                      };
 * 任务不能一次性全部进入任务大厅，等按照时间线来走，等到任务润色出来了才能入任务大厅，
 * 这就涉及到任务榜单动态变化的问题了，得手写改堆，项目经理发布的任务会修改，项目经理的任务在取走那一刻就可以将
 * 自己新的任务放进去了
 * 返回:{3, 4, 5, 3, 9}
 */
public class Code03_SDEandPM {
    public static class Task {
        int index;//这是第几个任务，方便收答案
        //1)哪个项目经理提的
        int manager;
        //2)被项目经理润色出来的时间点
        int pushTime;
        //3)项目优先级
        int priority;
        //4)项目花费的时间
        int cost;

        public Task(int manager, int pushTime, int priority, int cost,int index) {
            this.index = index;
            this.manager = manager;
            this.pushTime = pushTime;
            this.priority = priority;
            this.cost = cost;
        }
    }
    public static class ManagerLoveRule implements Comparator<Task>{
        @Override
        public int compare(Task o1, Task o2) {
            if(o1.priority!=o2.priority){
                return o1.priority - o2.priority;
            }else if(o1.cost!=o2.cost){
                return o1.cost - o2.cost;
            }else{
                return o1.pushTime - o2.pushTime;
            }
        }
    }
    public static class CoderLoveRule implements Comparator<Task>{

        @Override
        public int compare(Task o1, Task o2) {
            if(o1.cost!=o2.cost){
                return o1.cost - o2.cost;
            }else{
                return o1.manager - o2.manager;
            }
        }
    }
    public static int[] getTaskEndTimes(int[][] tasks,int pms,int sde){
        //生成任务,startQueue,到时间点就把符合的任务丢进去
        PriorityQueue<Task> startQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o.pushTime));
        int N = tasks.length;
        for(int i = 0; i< N; i++){
            startQueue.add(new Task(tasks[i][0],tasks[i][1],tasks[i][2],tasks[i][3],i));
        }
        int[] ans = new int[N];
        PriorityQueue<Integer> coderQueue = new PriorityQueue<>(Comparator.comparingInt(o->o));
        for(int i = 0;i<sde;i++){
            coderQueue.add(1);//都在1时间醒来
        }
        TaskCentral taskCentral = new TaskCentral(pms);
        while (!startQueue.isEmpty()||!taskCentral.isEmpty()){
            //第一个醒来的程序员是当前时间线
            assert coderQueue.size()!=0;
            int startTime = coderQueue.poll();
            //将当前时间点下润色好的项目给下发
            while (!startQueue.isEmpty()&&startQueue.peek().pushTime<=startTime){
                taskCentral.pushTask(startQueue.poll());
            }
            if(taskCentral.isEmpty()){
                //醒来的程序员没有项目可做
                assert startQueue.peek() != null;
                coderQueue.add(startQueue.peek().pushTime);//在肯定有项目可做的时候再醒来
            }else{
                Task task = taskCentral.pollTask();
                ans[task.index] = startTime+task.cost;
                coderQueue.add(ans[task.index]);
            }
        }
        return ans;
    }
    //职能:是一个大机构，里面各个项目经理有一个自己的任务池，还有一个出口是程序员接收任务的池子
    //1.需要实现的功能是，在每个时间点，我都想要弹出当前我想要做的任务 pollTask
    //2.在某个时间点，我会将当前解锁的任务丢进去
    public static class TaskCentral{
        List<PriorityQueue<Task>> pmPool;//项目经理的池子
        TaskPool taskPool;

        public TaskCentral(int pms) {
            //初始化经理的项目池
            pmPool = new ArrayList<>(pms+1);
            for(int i = 0;i<pms+1;i++){
                pmPool.add(new PriorityQueue<>(new ManagerLoveRule()));
            }
            taskPool = new TaskPool(pms);

        }

        public boolean isEmpty() {
            return taskPool.isEmpty();
        }

        public void pushTask(Task task) {
            PriorityQueue<Task> speManager = pmPool.get(task.manager);
            speManager.add(task);
            taskPool.addOrUpdateOrIgnore(speManager.peek());
        }

        public Task pollTask() {
            //这个任务对应的经理得进新项目
            Task res = taskPool.poll();
            PriorityQueue<Task> manager = pmPool.get(res.manager);
            manager.poll();
            if(!manager.isEmpty()){
                taskPool.addOrUpdateOrIgnore(manager.peek());
            }
            return res;
        }

        public static class TaskPool{//存任务的池子
            Task[] tasks;
            int[] indexs;
            int size = 0;//size是当前最后一个元素位置，下一个填入到size+1
            ManagerLoveRule managerLoveRule = new ManagerLoveRule();
            CoderLoveRule coderLoveRule = new CoderLoveRule();
            public TaskPool(int pms) {
                tasks = new Task[pms+1];
                indexs = new int[pms+1];
                //index[i]表示i号项目经理的项目存在tasks的index[i]位置
                //如果>=size+1或者0位置，说明这个项目经理的项目不在里面
                //堆数据从1，开始有效，左孩子为2*i,右孩子为2*i+1
            }

            public boolean isEmpty() {
                return size==0;
            }
            public boolean isTaskIn(int idx){
                return idx>0&&idx<=size;
            }
            public void addOrUpdateOrIgnore(Task task) {
                int speIdx = indexs[task.manager];
                if(!isTaskIn(speIdx)){
                    //不在里面，那就做一个添加操作
                    tasks[size++ + 1] = task;//size = 0,插入1位置，size = 3,插入4位置
                    indexs[task.manager] = size;
                    heapInsert(size);
                }else if(managerLoveRule.compare(task,tasks[speIdx])<0){
                    tasks[speIdx] = task;
                    heapInsert(speIdx);
                }
            }
            //向上
            private void heapInsert(int speIdx) {
                int father;
                while ((father = (speIdx>>1))>=1&&coderLoveRule.compare(tasks[speIdx],tasks[father])<0){
                    swap(speIdx,father);
                    speIdx = father;
                }
            }
            //向下
            private void heapify(int speIdx) {
                int leftSon;
                int rightSon;
                while ((leftSon = speIdx<<1)<=size){
                    rightSon = leftSon | 1;
                    int smallOne = rightSon<=size&&coderLoveRule.compare(tasks[rightSon],tasks[leftSon])<0?rightSon:leftSon;
                    if(coderLoveRule.compare(tasks[speIdx],tasks[smallOne])<0) return;
                    swap(speIdx,smallOne);
                    speIdx = smallOne;
                }
            }

            //堆的最后一个位置下标为size
            public Task poll() {
                //要弹出的任务
                Task task = tasks[1];
                swap(1,size--);
                indexs[task.manager] = 0;
                heapify(1);
                tasks[size+1] = null;
                return task;
            }

            private void swap(int a, int b) {
                indexs[tasks[b].manager] = a;
                indexs[tasks[a].manager] = b;
                Task temp = tasks[a];
                tasks[a] = tasks[b];
                tasks[b] = temp;
            }
        }
    }
    public static long fast(long n,long m){
        long res = 1;
        long temp = n;
        do{
            if((m&1)!=0){
                res *= temp;
            }
            temp *= temp;
        }while((m>>>=1)!=0);
        return res;
    }
    public static void main(String[] args) {
        int pms = 2;
        int sde = 2;
        int[][] programs = {
                { 1, 1, 1, 2 }, { 1, 2, 1, 1 }, { 1, 3, 2, 2 },
                { 2, 1, 1, 2 }, { 2, 3, 5, 5 },{1,4,3,3},{2,5,3,1},
                {2,4,5,7},{2,1,3,5}
        };
        int[] ans = getTaskEndTimes(programs,pms, sde);
        System.out.println(Arrays.toString(ans));
    }
}