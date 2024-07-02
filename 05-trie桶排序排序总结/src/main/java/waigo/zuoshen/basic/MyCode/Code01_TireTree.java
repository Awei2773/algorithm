package waigo.zuoshen.basic.MyCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

/**
 * author waigo
 * create 2021-02-16 9:44
 */
public class Code01_TireTree {

    //使用固定26长度的数组来记录路,只能记录英文字符串
    public static class TireTree1 {
        private Node head = new Node();

        private static class Node {
            private Node[] paths;//路，到下一节点，按照字母求出下标找到那条路，路是否为null表示这个路是否已经存在
            private int pass;
            private int end;

            public Node() {
                paths = new Node[26];//a~z
            }
        }

        public void insert(String str) {
            char[] strChars = str.toCharArray();
            Node cur = head;
            int idx;
            for (char strChar : strChars) {
                //表示没有路
                if (cur.paths[(idx = strChar - 'a')] == null) {
                    cur.paths[idx] = new Node();//建一条路
                }
                cur.pass++;//当前位置的pass++
                cur = cur.paths[idx];//走到下一路
            }
            //当前cur走到了最后一个节点位置
            cur.end++;
            cur.pass++;
        }

        /**
         * 查询某个字符串在结构中还有几个
         *
         * @param str
         * @return
         */
        public int search(String str) {
            char[] strChars = str.toCharArray();
            Node cur = head;
            int idx;
            for (char strChar : strChars) {
                if (cur.paths[(idx = strChar - 'a')] == null) {
                    return 0;
                }
                cur = cur.paths[idx];
            }
            //移动到了最后一个，返回end数就是有几个
            return cur.end;
        }

        /**
         * 删除某个字符串，可以重复删除，每次算一个
         *
         * @param str
         */
        public void delete(String str) {
            //先找一下看看有没有存过这个字符串
            if (str != null && search(str) != 0) {
                char[] strChars = str.toCharArray();
                Node cur = head;
                int idx;
                //因为查询过已经确定存在了，所以直接删除就可以了，前面到过的节点pass--,终点end--
                for (char strChar : strChars) {
                    idx = strChar - 'a';
                    //当前节点pass--
                    cur.pass--;
                    //cur move
                    cur = cur.paths[idx];
                }
                //移动到最后节点
                cur.pass--;
                cur.end--;
            }
        }

        /**
         * 查询有多少个字符串是以str做前缀的
         *
         * @param str
         * @return 找到str的最后一个节点的pass，这个值表示有几个字符串是从这里过的，那么就表示有多少字符串以str做前缀
         */
        public int prefixNumber(String str) {
            char[] strChars = str.toCharArray();
            Node cur = head;
            int idx;
            for (char strChar : strChars) {
                if (cur.paths[(idx = strChar - 'a')] == null) {
                    return 0;//连str都没有一条直达的路，怎么可能还有以str为前缀的字符串，直接返回
                }
                cur = cur.paths[idx];
            }
            return cur.pass;
        }
    }

    //升级版，使用HashMap来记录路，就能够记录所有的字符串
    public static class TireTree2 {
        private Node head = new Node();

        public static class Node {
            private HashMap<Character, Node> paths = new HashMap<>();
            private int pass;
            private int end;
        }

        //添加某个字符串，可以重复添加，每次算1个
        public void insert(String str) {
            if (str == null) return;
            char[] strChars = str.toCharArray();
            Node cur = head;
            for (char curChar : strChars) {
                //看一下有没有这条路
                if (!cur.paths.containsKey(curChar)) {
                    cur.paths.put(curChar, new Node());
                }
                //当前节点pass++
                cur.pass++;
                //cur move
                cur = cur.paths.get(curChar);
            }
            //来到最后一个节点
            cur.pass++;
            cur.end++;
        }

        //查询某个字符串在结构中还有几个
        public int search(String str) {
            if (str == null) return 0;
            char[] strChars = str.toCharArray();
            Node cur = head;
            for (char strChar : strChars) {
                if (!cur.paths.containsKey(strChar)) {
                    return 0;//不存在一条直达str的路，说明没有存过这个字符串
                }
                cur = cur.paths.get(strChar);//cur move
            }
            return cur.end;
        }

        //删掉某个字符串，可以重复删除，每次算1个
        public void delete(String str) {
            if (search(str) != 0) {
                char[] strChars = str.toCharArray();
                Node cur = head;
                for (char strChar : strChars) {
                    //已经证明有至少一个字符串了，直接删就完事了
                    cur.pass--;
                    cur = cur.paths.get(strChar);
                }
                //来到最后一个节点
                cur.pass--;
                cur.end--;
            }
        }

        //查询有多少个字符串，是以str做前缀的
        public int prefixNumber(String str) {
            if (str == null) return 0;
            char[] strChars = str.toCharArray();
            Node cur = head;
            for (char strChar : strChars) {
                if (!cur.paths.containsKey(strChar)) {
                    return 0;//找不到这条路证明都没有str,更别提str做前缀的字符串了
                }
                cur = cur.paths.get(strChar);
            }
            return cur.pass;//返回最后一个节点的pass，表示这个节点有这么多经过了
        }

    }

    //对数器
    public static class Right {
        private ArrayList<String> strs = new ArrayList<>();

        //添加某个字符串，可以重复添加，每次算1个
        void insert(String str) {
            strs.add(str);
        }

        //查询某个字符串在结构中还有几个
        int search(String str) {
            int size = 0;
            for (String curStr : strs) {
                if (curStr.equals(str)) size++;
            }
            return size;
        }

        //删掉某个字符串，可以重复删除，每次算1个
        void delete(String str) {
            for (String curStr : strs) {
                if (curStr.equals(str)) {
                    strs.remove(curStr);
                    break;
                }
            }
        }

        //查询有多少个字符串，是以str做前缀的
        int prefixNumber(String str) {
            int num = 0;
            for (String curStr : strs) {
                if(curStr.length()<str.length()) continue;
                String substring = curStr.substring(0, str.length());
                if (substring.equals(str)) num++;
            }
            return num;
        }

    }

    public static String generalRandomString(int maxLength) {
        char[] strChars = new char[(int) (Math.random() * maxLength) + 1];//[1...maxLength]
        for (int i = 0; i < strChars.length; i++) {
            int random = (int) (Math.random() * 6);
            strChars[i] = (char) ('a' + random);
        }
        return String.valueOf(strChars);
    }

    public static String[] generalRandomStringArray(int maxSize, int maxLength) {
        String[] strAry = new String[(int) (Math.random() * maxSize) + 1];
        for (int i = 0; i < strAry.length; i++) {
            strAry[i] = generalRandomString(maxLength);
        }
        return strAry;
    }

    public static void main(String[] args) {
        int maxSize = 100, maxLength = 20, times = 1000001;
        int i = 0;
        for (; i < times; i++) {
            TireTree1 tree1 = new TireTree1();
            TireTree2 tree2 = new TireTree2();
            Right right = new Right();
            String[] randomStrings = generalRandomStringArray(maxSize, maxLength);
            for (String randomString : randomStrings) {
                double decide = Math.random();//概率，使得四个方法都能均匀的进行测试
                if (decide < 0.25) {
                    int tree1Search = tree1.search(randomString);
                    int tree2Search = tree2.search(randomString);
                    int rightSearch = right.search(randomString);
                    if(rightSearch!=tree1Search||rightSearch!=tree2Search){
                        System.out.println("tree1Search="+tree1Search);
                        System.out.println("tree2Search="+tree2Search);
                        System.out.println("rightSearch="+tree2Search);
                        System.out.println("262--oops");
                    }
                } else if (decide < 0.5) {
                    tree1.delete(randomString);
                    tree2.delete(randomString);
                    right.delete(randomString);
                } else if (decide < 0.75) {
                    tree1.insert(randomString);
                    tree2.insert(randomString);
                    right.insert(randomString);
                } else {
                    int tree1PrefixNum = tree1.prefixNumber(randomString);
                    int tree2PrefixNum = tree2.prefixNumber(randomString);
                    int rightPrefixNum = right.prefixNumber(randomString);
                    if(rightPrefixNum!=tree1PrefixNum||rightPrefixNum!=tree2PrefixNum){
                        System.out.println("tree1PrefixNum="+tree1PrefixNum);
                        System.out.println("tree2PrefixNum="+tree2PrefixNum);
                        System.out.println("rightPrefixNum="+rightPrefixNum);
                        System.out.println("274--oops");
                    }
                }
            }
        }
        System.out.println("finish");
    }

}
