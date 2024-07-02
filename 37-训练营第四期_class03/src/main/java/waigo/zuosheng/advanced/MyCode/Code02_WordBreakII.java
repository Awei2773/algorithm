package waigo.zuosheng.advanced.MyCode;

/**
 * author waigo
 * create 2021-06-13 12:35
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Given a string s and a dictionary of strings wordDict, add spaces in s to construct
 * a sentence where each word is a valid dictionary word. Return all such possible sentences
 * in any order.
 * <p>
 * Note that the same word in the dictionary may be reused multiple times in the segmentation.
 * <p>
 * Example 1:
 * <p>
 * Input: s = "catsanddog", wordDict = ["cat","cats","and","sand","dog"]
 * Output: ["cats and dog","cat sand dog"]
 * Example 2:
 * <p>
 * Input: s = "pineapplepenapple", wordDict = ["apple","pen","applepen","pine","pineapple"]
 * Output: ["pine apple pen apple","pineapple pen apple","pine applepen apple"]
 * Explanation: Note that you are allowed to reuse a dictionary word.
 * Example 3:
 * <p>
 * Input: s = "catsandog", wordDict = ["cats","dog","sand","and","cat"]
 * Output: []
 * <p>
 * Constraints:
 * <p>
 * 1 <= s.length <= 20
 * 1 <= wordDict.length <= 1000
 * 1 <= wordDict[i].length <= 10
 * s and wordDict[i] consist of only lowercase English letters.
 * All the strings of wordDict are unique.
 */
public class Code02_WordBreakII {
    //wordDict建前缀树，流程如下
    //从头开始往下走，一直走到如果为end位置那么说明这是一种拆的可能，然后拆分可能性
    //1.返回头重新匹配
    //2.继续向下走看看前缀还有么有结尾的
    //如果走到一条不能往下走的路，那很明显是没有可能性了，直接return
    public static class TireTree {
        Node root = new Node();

        public TireTree build(List<String> wordDict) {
            for (int i = 0; i < wordDict.size(); i++) {
                char[] chars = wordDict.get(i).toCharArray();
                Node cur = root;
                int idx;
                for (char path : chars) {
                    idx = path - 'a';
                    if (cur.paths[idx] == null) {
                        cur.paths[idx] = new Node();
                    }
                    cur = cur.paths[idx];
                }
                cur.end = i;
            }
            return this;
        }

        public List<String> wordBreak(String s, List<String> wordDict) {
            char[] chars = s.toCharArray();
            List<String> res = new ArrayList<>();
            awesome(root, chars, 0, res, new ArrayList<>(), wordDict);
            return res;
        }

        //idx前的char都看过了，idx还没看，目前来到了cur节点，走过的路径封在path中，存的是wordDict的下标，将搜集到的答案存在res中
        private void process(Node cur, char[] chars, int idx, List<String> res, List<Integer> path, List<String> wordDict) {
            if (idx == chars.length) {
                StringBuilder str = new StringBuilder();
                for (int i = 0; i < path.size(); i++) {
                    str.append(wordDict.get(path.get(i))).append(" ");
                }
                str.delete(str.length() - 1, str.length());
                res.add(str.toString());
                return;
            }
            if (cur.paths[chars[idx] - 'a'] != null) {
                if (idx + 1 < chars.length) {
                    process(cur.paths[chars[idx] - 'a'], chars, idx + 1, res, path, wordDict);
                }
                if (cur.paths[chars[idx] - 'a'].end != null) {
                    path.add(cur.paths[chars[idx] - 'a'].end);
                    process(root, chars, idx + 1, res, path, wordDict);
                    path.remove(path.size() - 1);//轨迹擦除
                }
            }
        }
        private void awesome(Node cur, char[] chars, int idx, List<String> res, List<Integer> path, List<String> wordDict) {
            if (idx == chars.length) {
                StringBuilder str = new StringBuilder();
                for (int i = 0; i < path.size(); i++) {
                    str.append(wordDict.get(path.get(i))).append(" ");
                }
                str.delete(str.length() - 1, str.length());
                res.add(str.toString());
                return;
            }
            int pathIdx;
            for (int end = idx; end < chars.length; end++) {
                pathIdx = chars[end]-'a';
                if(cur.paths[pathIdx]==null){
                    break;
                }
                cur = cur.paths[pathIdx];
                if(cur.end!=null){
                    path.add(cur.end);
                    awesome(root,chars,end+1,res,path,wordDict);
                    path.remove(path.size()-1);
                }
            }
        }
    }

    static class Node {
        Integer end;//存在wordDict中出现的下标，如果为null表示在wordDict中不存在
        Node[] paths = new Node[26];
    }


    public static void main(String[] args) {
        List<String> wordDict = new ArrayList<>();
        wordDict.addAll(Arrays.asList("apple","pen","applepen","pine","pineapple"));
        TireTree tireTree = new TireTree().build(wordDict);
        List<String> catsanddog = tireTree.wordBreak("pineapplepenapple", wordDict);
        System.out.println(catsanddog);
    }
}
