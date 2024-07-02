package waigo.zuosheng.MyCode;

/**
 * author waigo
 * create 2021-07-18 11:41
 */
public class Code36_IsValidSuDo {
    public static boolean isValidSudoku(char[][] board) {
        int[][] colums = new int[9][9];//colums[row][col]表示第col列是否出现过row这个数
        int[][] rows = new int[9][9];//rows[row][col]表示第row行是否出现过col这个数
        //为1表示出现过
        int rowBegin = 0;
        int colBegin = 0;
        while(rowBegin<=6){
            //rowBegin和colBegin框住一个9宫格
            int[] ninesFind = new int[9];
            for(int tempRow = rowBegin;tempRow<rowBegin+3;tempRow++){
                for(int tempCol = colBegin;tempCol<colBegin+3;tempCol++){
                    if(board[tempRow][tempCol]=='.') continue;
                    int cur = board[tempRow][tempCol]-'1';
                    if(colums[cur][tempCol]==1||rows[tempRow][cur]==1||ninesFind[cur]==1){
                        return false;
                    }
                    colums[cur][tempCol]=1;
                    rows[tempRow][cur]=1;
                    ninesFind[cur]=1;
                }
            }
            if(colBegin>=6){
                rowBegin+=3;
                colBegin=0;
            }else{
                colBegin+=3;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        char[][] board = {{'5','3','.','.','7','.','.','.','.'},{'6','.','.','1','9','5','.','.','.'},{'.','9','8','.','.','.','.','6','.'},{'8','.','.','.','6','.','.','.','3'},{'4','.','.','8','.','3','.','.','1'},{'7','.','.','.','2','.','.','.','6'},{'.','6','.','.','.','.','2','8','.'},{'.','.','.','4','1','9','.','.','5'},{'.','.','.','.','8','.','.','7','9'}};
        isValidSudoku(board);
    }

}
