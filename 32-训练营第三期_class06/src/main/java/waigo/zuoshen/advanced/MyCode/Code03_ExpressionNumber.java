package waigo.zuoshen.advanced.MyCode;

/**
 * author waigo
 * create 2021-05-21 7:47
 */

/**
 * 给定一个只由 0(假)、1(真)、&(逻辑与)、|(逻辑或)和^(异或)五种字符组成 的字符串express，再给定一个布尔值 desired。
 * 返回express能有多少种组合 方式，可以达到desired的结果。
 * 【举例】
 * express="1^0|0|1"，desired=false
 * 只有 1^((0|0)|1)和 1^(0|(0|1))的组合可以得到 false，返回 2。 express="1"，desired=false
 * 无组合则可以得到false，返回0
 */
public class Code03_ExpressionNumber {
    public static int getExpSumForDesired(String expression,boolean desired){
        char[] exps = expression.toCharArray();
        if(!isValid(exps)) return 0;
        return process(exps,0,exps.length-1,desired);
    }
    //返回[l...r]上期望为desired时能够有多少种表达式
    private static int process(char[] exps, int l, int r, boolean desired) {
        if(l==r){
            return (exps[l]=='0'&&(!desired))
                    ||(exps[l]=='1'&&desired)?1:0;
        }
        int sum = 0;
        for(int i = l+1;i<r;i+=2){
            int leftFalse = process(exps,l,i-1,false);
            int leftTrue = process(exps,l,i-1,true);
            int rightFalse = process(exps,i+1,r,false);
            int rightTrue = process(exps,i+1,r,true);
            switch (exps[i]){
                case '|':{
                    sum+=desired?(leftFalse*rightTrue+leftTrue*rightFalse+leftTrue*rightTrue):leftFalse*rightFalse;
                    break;
                }
                case '&':{
                    sum+=desired?(leftTrue*rightTrue):(leftFalse*rightFalse+leftFalse*rightTrue+leftTrue*rightFalse);
                    break;
                }
                case '^':{
                    sum+=desired?(leftFalse*rightTrue+leftTrue*rightFalse):(leftTrue*rightTrue+leftFalse*rightFalse);
                    break;
                }
            }
        }
        return sum;
    }

    public static int dpWay(char[] exps,boolean desired){
        if(!isValid(exps)) return 0;
        int N = exps.length;
        int[][] dpFalse = new int[N][N];
        int[][] dpTrue = new int[N][N];
        //先填对角线,base case
        int row = 0,col = 0;
        while (row<N){
            dpFalse[row][col]=exps[row]=='0'?1:0;
            dpTrue[row][col]=exps[row]=='1'?1:0;
            row++;
            col++;
        }
        row = 0;
        col = 2;
        while (col<N){//一次写一个斜线
            int tRow = row;
            int tCol = col;
            while (tRow<N&&tCol<N){
                //process(tRow,tCol)
                for(int i = tRow+1;i<tCol;i+=2){
                    int leftFalse = dpFalse[tRow][i-1];
                    int leftTrue = dpTrue[tRow][i-1];
                    int rightFalse = dpFalse[i+1][tCol];
                    int rightTrue = dpTrue[i+1][tCol];
                    switch (exps[i]){
                        case '|':{
                            dpTrue[tRow][tCol]+=leftFalse*rightTrue+leftTrue*rightFalse+leftTrue*rightTrue;
                            dpFalse[tRow][tCol]+=leftFalse*rightFalse;
                            break;
                        }
                        case '&':{
                            dpTrue[tRow][tCol]+=leftTrue*rightTrue;
                            dpFalse[tRow][tCol]+=leftFalse*rightFalse+leftFalse*rightTrue+leftTrue*rightFalse;
                            break;
                        }
                        case '^':{
                            dpTrue[tRow][tCol]+=leftFalse*rightTrue+leftTrue*rightFalse;
                            dpFalse[tRow][tCol]+=leftTrue*rightTrue+leftFalse*rightFalse;
                            break;
                        }
                    }

                }
                tCol+=2;
                tRow+=2;
            }
            col+=2;//下一条斜线
        }
        return desired?dpTrue[0][N-1]:dpFalse[0][N-1];

    }
    public static boolean isValid(char[] exps){
        if(exps==null||(exps.length&1)==0) return false;
        for (int i = 0; i < exps.length; i++) {
            if(((i&1)==1)&&(!isOpr(exps[i]))){
                return false;
            }
            if((i&1)==0&&(exps[i]!='0'&&exps[i]!='1')){
                return false;
            }
        }
        return true;
    }

    private static boolean isOpr(char exp) {
        return exp=='|'||exp=='^'||exp=='&';
    }

    public static void main(String[] args) {
        System.out.println(getExpSumForDesired("1^0|0|1",true));
    }
}
