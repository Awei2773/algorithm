package waigo.zuoshen.advanced.MyCode;

import java.util.*;

/**
 * author waigo
 * create 2021-06-20 10:23
 */
public class Code06_SkyLines {
    //1.关键点在于抓住每个发生转折的时间点，每个时刻的最高点和上一次天际线的高度不同就说明出现了新的天际线
    //2.将每个建筑都拆成两个操作，一个是向上的up操作，一个是向下的down操作，这些操作肯定是依次进行的，所以得排序
    //2.1排序的时候left作为第一层排序，left相同的则up操作在前，避免出现纸片大楼的干扰情况
    public List<List<Integer>> getSkyline(int[][] buildings) {
        List<List<Integer>> res = new ArrayList<>();
        if(buildings==null||buildings.length==0) return res;
        //1.将所有操作进行排序，这些操作也就是每一个有效的点，之后在遍历这些操作的时候进行每个点是否是天际线的判定
        //思考每个operation需要哪些信息，1.出现的x坐标2.这个位置的高度3.能够标识这是个up还是一个down操作
        //将2，3合为一个信息，up为正数，down为负数
        int buildNum = buildings.length;
        int[][] allOprations = new int[buildNum<<1][2];
        for(int i = 0;i<buildNum;i++){
            allOprations[i<<1]=new int[]{buildings[i][0],buildings[i][2]};
            allOprations[(i<<1)|1]=new int[]{buildings[i][1],-buildings[i][2]};
        }
        //2.操作排序
        Arrays.sort(allOprations,(o1, o2)->o1[0]==o2[0]?o2[1]-o1[1]:o1[0]-o2[0]);
        //3.遍历所有操作，用一个last记录一下之前的天际线，每个操作位置都看看能不能获得新的天际线
        int last = 0;
        TreeMap<Integer,Integer> mapHeightTimes = new TreeMap<>();//key=高度，value=出现的times
        for(int i = 0;i<allOprations.length;i++){
            int[] ops = allOprations[i];
            int height = ops[1]<0?-ops[1]:ops[1];
            int times = mapHeightTimes.getOrDefault(height,0);
            if(ops[1]<0){//down操作
                if(times ==1){
                    mapHeightTimes.remove(height);
                }else{
                    mapHeightTimes.put(height,times-1);
                }
            }else{//up操作
                mapHeightTimes.put(height,times+1);
            }
            //看看这个点是不是天际线,想要产生天际线高度必定是当前的高度，否则在之前就已经看出来了
            int curMaxHeight = mapHeightTimes.isEmpty() ? 0 : mapHeightTimes.lastKey();
            if(curMaxHeight !=last){
                res.add(Arrays.asList(ops[0],curMaxHeight));
                last = mapHeightTimes.lastKey();
            }
        }
        return res;
    }
}
