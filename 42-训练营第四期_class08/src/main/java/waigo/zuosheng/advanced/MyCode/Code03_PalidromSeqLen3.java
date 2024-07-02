package waigo.zuosheng.advanced.MyCode;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * author waigo
 * create 2021-07-11 19:57
 */
public class Code03_PalidromSeqLen3 {
    public static int countPalindromicSubsequence(String s) {
        int[] left = new int[s.length()];
        int[] right = new int[s.length()];
        int count = 0;
        for (int i = 1; i < s.length(); i++) {
            left[i] = left[i - 1] | 1 << s.charAt(i - 1) - 'a';
        }
        for (int i = s.length() - 2; i >= 0; i--) {
            right[i] = right[i + 1] | 1 << s.charAt(i + 1) - 'a';
        }
        boolean[][] visited = new boolean[26][26];
        for (int i = 1; i < s.length() - 1; i++) {
            for (int j = 0; j < 26; j++) {
                if (!visited[s.charAt(i) - 'a'][j] && (left[i] & 1 << j) > 0 && (right[i] & 1 << j) > 0) {
                    visited[s.charAt(i) - 'a'][j] = true;
                    count++;
                }
            }
        }
        return count;
    }

    public static void main(String[] args) {
        String a = "bbcbaba";
        System.out.println(countPalindromicSubsequence(a));
        System.out.println(Integer.MIN_VALUE);

        ArrayList<Integer> arrayList = new ArrayList<Integer>() {{
            add(1);
            add(2);
            add(3);
        }};

        System.out.println(arrayList);
    }
}
