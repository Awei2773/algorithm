package waigo.zuoshen.advance;

/**
 * author waigo
 * create 2021-07-13 23:07
 */
public class TestFib {
    public int fib(int n) {
        if(n<2) return n;
        //|F(n),F(n-1)| = |F(2),F(1)|*{1,1}^(N-2)
        //                            {1,0}
        int[][] fastRes = fast(new int[][]{{1,1},{1,0}},n-2);
        return (fastRes[0][0]+fastRes[1][0])%mod;
    }
    //矩阵快速幂算法
    public int[][] fast(int[][] base,int pow){
        int[][] res = {{1,0},{0,1}};
        do{
            if((pow&1)!=0){
                res = mulTwo(res,base);
            }
            base = mulTwo(base,base);
        }while((pow>>>=1)!=0);
        return res;
    }
    int mod = 1000000007;
    //矩阵乘法 二维*二维 a*b
    public int[][] mulTwo(int[][] a,int[][] b){
        int rows = a.length;
        int cols = a[0].length;
        int[][] res = new int[rows][cols];
        for(int row = 0;row<rows;row++){
            for(int col = 0;col<cols;col++){
                //a的row行去乘以b的col列
                for(int i = 0;i<rows;i++){
                    res[row][col] = (res[row][col]+(a[row][i] * b[i][col]))%mod;
                }

            }
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(new TestFib().fib(150));
    }
}
