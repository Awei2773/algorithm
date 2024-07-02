package waigo.zuoshen.basic.MyCode;

/**
 * author waigo
 * create 2021-02-23 9:31
 */
public class Code08_PaperFolding {
    public static void printPaperFoldingMark(int N){
        ins(N,1,true);
    }

    private static void ins(int n, int floor, boolean down) {
        if(floor>n) return;
        ins(n,floor+1,true);
        System.out.print((down?"down":" up ")+"\t");
        ins(n,floor+1,false);
    }

    public static void main(String[] args) {
        printPaperFoldingMark(4);
    }
}
