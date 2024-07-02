package waigo.zuoshen.advanced;

import org.junit.Test;
import waigo.zuoshen.advanced.MyCode.Code05_WordMinPaths;

import java.util.*;

/**
 * author waigo
 * create 2021-05-17 10:44
 */
public class TestCode05 {
    String start = "abc";
    List<String> list = new ArrayList<>(Arrays.asList("cab","acc","cbc","ccc","cac","cbb","aab","abb"));
    {
        list.add(start);
    }
    @Test
    public void testGetNextList(){
        Map<String, List<String>> nextList1 = Code05_WordMinPaths.getNextList1(list);
        Map<String, List<String>> nextList2 = Code05_WordMinPaths.getNextList2(list);
        Set<Map.Entry<String, List<String>>> entries = nextList1.entrySet();
        for (Map.Entry<String, List<String>> entry : entries) {
            System.out.println(entry.getKey()+":"+entry.getValue());
        }
        System.out.println("---------------------------------------------");
        Set<Map.Entry<String, List<String>>> entries1 = nextList2.entrySet();
        for (Map.Entry<String, List<String>> entry : entries1) {
            System.out.println(entry.getKey()+":"+entry.getValue());
        }
    }
    @Test
    public void testGetDistance(){
        Map<String, Integer> distance1 = Code05_WordMinPaths.getDistance1(start, Code05_WordMinPaths.getNextList1(list));
        Map<String, Integer> distance2 = Code05_WordMinPaths.getDistance2(start, Code05_WordMinPaths.getNextList1(list));
        for (Map.Entry<String, Integer> entry:distance1.entrySet()){
            System.out.println(entry.getKey()+":"+entry.getValue());
        }
        System.out.println("-------------------------------------");
        for (Map.Entry<String, Integer> entry:distance2.entrySet()){
            System.out.println(entry.getKey()+":"+entry.getValue());
        }
    }
    @Test
    public void testDfsGetResult(){
        List<List<String>> minPaths = Code05_WordMinPaths.findMinPaths("abc", "cab", list);
        for (List<String> path : minPaths) {
            for (String subPath : path) {
                System.out.print(subPath+"->");
            }
            System.out.println();
        }

    }
}
