package waigo.zuosheng.advanced.MyCode;

/**
 * author waigo
 * create 2021-07-12 23:09
 */
public class Code06_CountPalidromSeq3 {
    public static int countPalindromicSubsequence(String s) {
        int[][] charFinds = new int[26][2];
        for(int i = 0;i<26;i++){
            charFinds[i][0] = -1;
            charFinds[i][1] = -1;
        }
        for(int i = 0;i<s.length();i++){
            int spe = s.charAt(i) - 'a';
            charFinds[spe][0] = charFinds[spe][0]==-1?i:Math.min(charFinds[spe][0],i);
            charFinds[spe][1] = Math.max(charFinds[spe][1],i);
        }
        boolean[][] isActived = new boolean[26][26];//C,L是否用过了
        int res = 0;
        for(int i = 1;i<s.length();i++){
            int cur = s.charAt(i)-'a';
            for(int j = 0;j<26;j++){
                if(charFinds[j][0]!=charFinds[j][1]&&charFinds[j][0]<i&&charFinds[j][1]>i&&(!isActived[cur][j])){
                    isActived[cur][j] = true;
                    res++;
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        
        System.out.println(countPalindromicSubsequence("xcvsdfsdfsdsdfjsdljflsdjfljsdljfldsjfjasdfjasdlfjasdf"));
    }
}
