package waigo.zuoshen.advanced.MyCode;

/**
 * author waigo
 * create 2021-06-18 9:49
 */
//https://www.nowcoder.com/practice/41c399fdb6004b31a6cbb047c641ed8a?tpId=188&&tqId=38612&rp=1&ru=/activity/oj&qru=/ta/job-code-high-week/question-ranking
public class Code05_YSF {
    //升级版，每一次杀人的步数不一致，给你一个数组，循环取数进行杀人，这个和约瑟夫一样，就是直接将每一个m都
    //变换一下就好了
    //     public int ysf (int n, int m) {
//         // write code here
//         if(n==1) return 1;
//         return (ysf(n-1,m)+m-1)%n+1;
//     }
    //改递归成迭代
    public int ysf(int n, int m) {
        //从1求到n
        int res = 1;
        for (int i = 2; i <= n; i++) {
            res = (res + m - 1) % i + 1;
        }
        return res;
    }
    //在n个人中，按m步进行杀人，返回最后幸存者编号

    //f(n-1,m) <= n
    //n个人的时候杀掉的人位置在m%n,在n-1个人的时候将m%n位置看做0，确保和n位置一样都是从0开始编号的
    //(m%n + f(n-1,m))%n，这里由于是在n个人的情况下进行编号推演的，所以得求余n以致循环
    //1<=f(n-1,m)<m%n,总共就这些剩下的编号了，是不可能有其他取值的
    public static int ysf1(int n, int m) {
        //从1求到n
        if (n == 1) return 1;
        return (ysf1(n - 1, m) + m - 1) % n + 1;//因为是序号，所以需要-1，+1
    }

    public static void main(String[] args) {
        System.out.println(isPalindrome(121));
    }

    public static boolean isPalindrome(int x) {
        if (x < 0) return false;
        String xstr = Integer.toString(x);
        int left = 0;
        int right = xstr.length() - 1;
        while (left < right && xstr.charAt(left) == xstr.charAt(right)) {
            left++;
            right--;
        }
        return left >= right;
    }
}
