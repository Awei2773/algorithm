package com.waigo.nowcoder.MyCode.Code02_meituan;

import java.util.*;

/**
 * author waigo
 * create 2021-08-15 10:27
 */
public class Main3 {
    public static class Rabot{
        long idx;
        String dir;

        public Rabot(long idx, String dir) {
            this.idx = idx;
            this.dir = dir;
        }
    }
    //只有整数时刻重合才会爆炸，一碰撞，两个一起炸
    //思路：先将所有机器人按照所处位置进行排序，从小到大
    //1.遍历一遍，左边到第一个R为止都不可能重叠
    //2.右边到第一个L为止都不可能重叠
    //3.中间将R的位置进大根堆，每次最右边那个R去碰撞，要往右边的L都撞一遍
    //碰撞逻辑，两个的位置和如果是奇数就碰不到，如果是偶数就是除以2时碰到
    public static void main(String[] args) {
        /*Rabot r = new Rabot(63, "R");
        Rabot l = new Rabot(74, "L");
        System.out.println(crash(r,l));*/
        Scanner sc = new Scanner(System.in);
        int n = Integer.valueOf(sc.nextLine());
        Rabot[] rabots = new Rabot[n];
        for(int i = 0;i<n;i++){
            String[] rabot = sc.nextLine().split(" ");
            rabots[i] = new Rabot(Integer.valueOf(rabot[0]),rabot[1]);
        }
        int[] crashTime = getCrashTime(rabots, n);
        for(int i = 0;i<n;i++){
            System.out.println(crashTime[i]);
        }
    }
    public static int[] getCrashTime(Rabot[] rabots,int n){
        if(rabots==null||rabots.length==0) return new int[]{};
        //需要注意的是通过排序，机器人的顺序乱了，需要建立一个映射
        HashMap<Long, Integer> idxToOld = new HashMap<>();
        for(int i = 0;i<n;i++){
            idxToOld.put(rabots[i].idx,i);
        }
        Arrays.sort(rabots, Comparator.comparingLong(o->o.idx));
        //将左边的剔除
        boolean[] isVisited = new boolean[n];
        int[] res = new int[n];
        int L = 0,R = n-1;
        for(int i = 0;i<n;i++){
            if(!toRight(rabots[i])){
                isVisited[i] = true;
                res[idxToOld.get(rabots[i].idx)] = -1;
            }else{
                L = i;
                break;
            }
        }
        for(int i = n-1;i>=0;i--){
            if(toRight(rabots[i])){
                isVisited[i] = true;
                res[idxToOld.get(rabots[i].idx)] = -1;
            }else{
                R = i;
                break;
            }
        }
        //处理中间部分
        //先遍历一遍，将所有的R收集到
        PriorityQueue<Rabot> toRight = new PriorityQueue<>((o1,o2)->{
            long diff = o2.idx-o1.idx;
            if(diff<0){
                return -1;
            }else{
                return 1;
            }
        });
        HashMap<Long, Integer> idxToIndex = new HashMap<>();//每个R当前的下标
        for(int i = L;i<=R;i++){
            if(toRight(rabots[i])){
                toRight.add(rabots[i]);
                idxToIndex.put(rabots[i].idx,i);
            }
        }
        //让每个R尝试去碰撞右边的所有可能的L
        while(!toRight.isEmpty()){
            Rabot top = toRight.poll();
            int index = idxToIndex.get(top.idx);
            res[idxToOld.get(top.idx)] = -1;
            for(int i = index+1;i<=R;i++){
                if(!toRight(rabots[i])&&!isVisited[i]){
                    int c = crash(top,rabots[i]);
                    if(c!=-1){
                        isVisited[i] = true;
                        res[idxToOld.get(rabots[i].idx)] = c;
                        res[idxToOld.get(top.idx)] = c;
                        break;//找到一对碰撞的了
                    }
                }
            }
            isVisited[index] = true;
        }
        //在扫一遍L...R,所有没有碰到的就设为-1
        for(int i = L;i<=R;i++){
            if(!isVisited[i]){
                res[idxToOld.get(rabots[i].idx)] = -1;
            }
        }
        return res;
    }

    private static boolean toRight(Rabot rabot) {
        return "R".equals(rabot.dir);
    }

    //a在左边，b在右边
    public static int crash(Rabot a,Rabot b){
        long sum = a.idx + b.idx;
        if((sum&1)!=0) return -1;//是奇数
        return (int) ((sum>>1)-a.idx);//在这个地方碰撞

    }

}
