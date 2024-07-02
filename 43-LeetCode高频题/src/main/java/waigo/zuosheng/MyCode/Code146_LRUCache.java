package waigo.zuosheng.MyCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * author waigo
 * create 2021-08-12 12:04
 */
public class Code146_LRUCache {
    public int[] LRU (int[][] operators, int k) {
        ArrayList<Integer> res = new ArrayList<>();
        if(operators==null||operators.length==0
                ||operators[0].length==0) return new int[]{};
        int rows = operators.length;
        int cols = operators[0].length;
        LRUCache cache = new LRUCache(k);
        for(int opr = 0;opr<rows;opr++){
            int[] opration = operators[opr];
            if(opration[0]==1){
                //set操作
                cache.set(opration[1],opration[2]);
            }else if(opration[0]==2){
                //get操作
                res.add(cache.get(opration[1]));
            }
        }
        //接收答案
        int[] resArr = new int[res.size()];
        for(int i = 0;i<res.size();i++){
            resArr[i] = res.get(i);
        }
        return resArr;
    }
    public static class LRUCache extends LinkedHashMap<Integer,Integer>{
        int cap;
        public LRUCache(int k){
            super(k,0.75f,true);//需要注意，只有accessOrder为ture才是LRU，否则是按插入顺序，不会根据访问进行调整

            this.cap = k;
        }
        public void set(int key,int value){
            this.put(key,value);
        }

        @Override
        public Integer get(Object key) {
            if(!this.containsKey(key)) return -1;
            return super.get(key);
        }

        //增强方法，在节点插入后回调，如果返回true就将最老那个节点删除
        @Override
        protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
            return this.size()>cap;
        }
    }

}
