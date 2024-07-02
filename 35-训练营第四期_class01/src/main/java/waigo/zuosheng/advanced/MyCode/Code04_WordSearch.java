package waigo.zuosheng.advanced.MyCode;

/**
 * author waigo
 * create 2021-06-02 11:29
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 给定一个字符类型的二维数组board，和一个字符串组成的列表words。
 * 可以从board任何位置出发，每一步可以走向上、下、左、右，四个方向，
 * 但是一条路径已经走过的位置，不能重复走。
 * 返回words哪些单词可以被走出来。
 * 例子
 * board = [
 *   ['o','a','a','n'],
 *   ['e','t','a','e'],
 *   ['i','h','k','r'],
 *   ['i','f','l','v']
 * ]
 * words = ["oath","pea","eat","rain"]
 * 输出：["eat","oath"]
 */
public class Code04_WordSearch {
    //建前缀树
    public static class TireTreeNode{
        String endStr;
        boolean isAdded;//加过了就不让加了
        TireTreeNode[] paths = new TireTreeNode[26];//小写字母
    }
    public static class TireTree{
        TireTreeNode root = new TireTreeNode();
        private int rows;
        private int cols;

        public void buildTree(String[] words){
            for (String word : words) {
                char[] curWord = word.toCharArray();
                TireTreeNode temp = root;
                for (int i = 0; i < curWord.length; i++) {
                    int next = curWord[i] - 'a';
                    if(temp.paths[next]==null){
                        temp.paths[next] = new TireTreeNode();
                    }
                    temp = temp.paths[next];
                }
                temp.endStr = word;
            }
        }

        public void search(char[][] board, List<String> res) {
            this.rows = board.length;
            this.cols = board[0].length;
            boolean[][] history = new boolean[rows][cols];
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    //(row,col)选定一个点开始
                    collectRes(root.paths[board[row][col] - 'a'],row,col,res,board,history);
                }
            }
        }
        int[][] dirs = {{-1,0},{0,-1},{1,0},{0,1}};//up left down right
        private void collectRes(TireTreeNode cur, int row, int col,
                                List<String> res, char[][] board,
                                boolean[][] history) {
            //看看这个点是否之前走对了，如果为空，说明之前的路压根走不到这里，直接返回
            if(cur ==null) return;
            if(cur.endStr!=null&&(!cur.isAdded)){//want才是当前走到的点
                res.add(cur.endStr);
                cur.isAdded=true;
            }
            history[row][col] = true;
            for (int i = 0; i < dirs.length; i++) {
                //这个方向是否可以走？
                int newRow = row+dirs[i][0];
                int newCol = col+dirs[i][1];
                if(newRow>=0&&newRow<rows&&newCol>=0&&newCol<cols&&
                        (!history[newRow][newCol])){
                    collectRes(cur.paths[board[newRow][newCol] - 'a'],newRow,newCol,res,board,history);
                }
            }
            history[row][col] = false;
        }
    }
    public static List<String> findWords(char[][] board, String[] words) {
        List<String> res = new ArrayList<>();
        if(board==null||board.length==0||board[0]==null||board[0].length==0
        ||words==null||words[0].length()==0)
            return res;
        TireTree tireTree = new TireTree();
        tireTree.buildTree(words);
        tireTree.search(board,res);
        return res;
    }

    public static void main(String[] args) {
        char[][] board = {{'o','a','a','n'},
                {'e','t','a','e'},{'i','h','k','r'},{'i','f','l','v'}};
        String[] words = {"oath","pea","eat","rain"};
        /*char[][] board ={{'a'}};
        String[] words = {"a"};*/
        List<String> words1 = findWords(board, words);
        System.out.println(Arrays.toString(words1.toArray()));

    }
}
