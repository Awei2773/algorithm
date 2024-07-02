package waigo.zuoshen.advanced.MyCode;

/**
 * author waigo
 * create 2021-05-15 9:11
 */

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * 给你一个字符串类型的数组arr，譬如:
 * String[] arr = { "b\st", "d\", "a\d\e", "a\b\c" };
 * 把这些路径中蕴含的目录结构给打印出来，子目录直接列在父目录下面，并比父目录向右进两格，就像这样:
 * a
 *   b
 *     c
 *   d
 *     e
 * b
 *   st
 * d
 * 同一级的需要按字母顺序排列不能乱。
 */
public class Code01_GetFolderTree {
    public static void main(String[] args) {
        String[] arr = {"b\\st", "d\\", "a\\d\\e", "a\\b\\c","a\\c\\d\\e"};
        List<String[]> folders = new ArrayList<>(arr.length);
        for (String str : arr) {
            folders.add(str.split("\\\\"));
        }
        TireTree tireTree = new TireTree(folders);
        tireTree.printTireTree();
    }

    private static class TireTree {
        Node root = new Node("");
        private static class Node{
            String fileName;
            TreeMap<String,Node> nexts;

            public Node(String fileName) {
                this.fileName = fileName;
                nexts = new TreeMap<>(String::compareTo);
            }
        }
        public TireTree(List<String[]> folders) {
            buildTireTree(folders);
        }

        private void buildTireTree(List<String[]> folders) {
            for (String[] folder:folders) {
                Node curNode = root;
                for (String file : folder) {
                    if(!curNode.nexts.containsKey(file)){
                        curNode.nexts.put(file,new Node(file));
                    }
                    curNode = curNode.nexts.get(file);
                }
            }
        }
        private void printTireTree(){
            print(root,0);
        }

        private void print(Node tireTree, int curFloor) {
            if(curFloor!=0){
                System.out.println(getSpace(curFloor)+tireTree.fileName);
            }
            while (!tireTree.nexts.isEmpty()){
                print(tireTree.nexts.pollFirstEntry().getValue(),curFloor+1);
            }
        }

        private String getSpace(int curFloor) {
            StringBuilder builder = new StringBuilder();
            for (int i = 1; i < curFloor; i++) {
                builder.append("  ");
            }
            return builder.toString();
        }
    }
}
