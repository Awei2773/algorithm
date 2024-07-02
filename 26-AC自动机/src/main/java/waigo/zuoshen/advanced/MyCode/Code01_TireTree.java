package waigo.zuoshen.advanced.MyCode;

/**
 * author waigo
 * create 2021-05-03 17:57
 */
/*
A trie (pronounced as "try") or prefix tree is a tree data structure used to efficiently store and retrieve keys
in a dataset of strings. There are various applications of this data structure, such as autocomplete and spellchecker.

Implement the Trie class:

Trie() Initializes the trie object.
void insert(String word) Inserts the string word into the trie.
boolean search(String word) Returns true if the string word is in the trie (i.e., was inserted before), and false otherwise.
boolean startsWith(String prefix) Returns true if there is a previously inserted string word that has the prefix prefix, and false otherwise.
Example 1:
Input
["Trie", "insert", "search", "search", "startsWith", "insert", "search"]
[[], ["apple"], ["apple"], ["app"], ["app"], ["app"], ["app"]]
Output
[null, null, true, false, true, null, true]
Explanation
Trie trie = new Trie();
trie.insert("apple");
trie.search("apple");   // return True
trie.search("app");     // return False
trie.startsWith("app"); // return True
trie.insert("app");
trie.search("app");     // return True
*/
public class Code01_TireTree {
    private TreeNode root;

    private static class TreeNode {
        TreeNode[] nexts = new TreeNode[26];
        int end;
        int pass;
    }

    /**
     * Initialize your data structure here.
     */
    public Code01_TireTree() {
        root = new TreeNode();
    }

    /**
     * Inserts a word into the trie.
     */
    public void insert(String word) {
        TreeNode cur = root;
        char[] strs = word.toCharArray();
        for(int i = 0;i<strs.length;i++){
            cur.pass++;
            int path = strs[i]-'a';
            if(cur.nexts[path]==null){
                cur.nexts[path] = new TreeNode();
            }
            cur = cur.nexts[path];
        }
        cur.pass++;
        cur.end++;
    }

    /**
     * Returns if the word is in the trie.
     */
    public boolean search(String word) {
        char[] strs = word.toCharArray();
        TreeNode cur = root;
        for(int i = 0;i<strs.length;i++){
            int path = strs[i]-'a';
            if(cur.nexts[path]==null) return false;
            cur = cur.nexts[path];
        }
        return cur.end>0;
    }

    /**
     * Returns if there is any word in the trie that starts with the given prefix.
     */
    public boolean startsWith(String prefix) {
        char[] strs = prefix.toCharArray();
        TreeNode cur = root;
        for(int i = 0;i<strs.length;i++){
            int path = strs[i]-'a';
            if(cur.nexts[path]==null) return false;
            cur = cur.nexts[path];
        }
        return true;
    }

    public static void main(String[] args) {
        Code01_TireTree trie = new Code01_TireTree();
        trie.insert("apple");
        System.out.println(trie.search("apple"));   // return True
        System.out.println(trie.search("app"));     // return False
        System.out.println(trie.startsWith("app")); // return True
        trie.insert("app");
        System.out.println( trie.search("app"));
    }
}
