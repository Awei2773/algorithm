package waigo.zuoshen.basic.MyCode;

/**
 * author waigo
 * create 2021-02-27 11:40
 */
//"abc"的全排列 abc,acb,bac,bca,cab,cba，这个的尝试是每个位置上的元素
public class Code03_PrintAllPermutations {
    public static void printAllPermutations(String str){
        if(str==null) return;
        process1(str.toCharArray(),0,"");
    }

    private static void process1(char[] strChars, int index, String path) {
        if(index==strChars.length){
            System.out.println(path);
            return;
        }
        for(int j = index;j < strChars.length;j++){
            swap(strChars,j,index);
            process1(strChars,index+1,path+strChars[index]);
            swap(strChars,j,index);
        }
    }

    private static void swap(char[] strChars,int aIdx,int bIdx){
        char temp = strChars[aIdx];
        strChars[aIdx] = strChars[bIdx];
        strChars[bIdx] = temp;
    }
    //去重思路，记住每个位置已经放过什么，放过的字母就不要放了，比起使用HashSet的方式，这种是
    //直接杀死会重复的路(不会走，不是放出来再用什么杀死)，效率更高，这种方式叫做"边界限定"
    public static void printAllPermutationsNoRepeat(String str){
        if(str==null) return;
        process2(str.toCharArray(),0,"");
    }
    //全排列tips:[0...index-1]表示已经选好了，现在判断index可以有哪些选择，index位置可以放的就是index到strChars.length-1的任意一个
    //所以遍历可以放的，放到index位置来，然后开始下一层
    //比如说abc,一开始看看第一个位有几种选择，a,b,c三种，所以开了三条路
    //然后到了第二位，如果一开始选了b,那么第二位就有a和c两种，开两条路
    private static void process2(char[] strChars, int index, String path) {
        if(index==strChars.length){
            System.out.println(path);
            return;
        }
        boolean[] isVisit = new boolean[26];//表示这条路26个英文字母哪些已经有路了
        for(int j = index;j < strChars.length;j++){
            if(!isVisit[strChars[j]-'a']){//这个下标是a则去0位置，b去1位置...
                isVisit[strChars[j]-'a'] = true;
                swap(strChars,index,j);
                process2(strChars,index+1,path+strChars[index]);
                swap(strChars,index,j);
            }
        }
    }

    public static void main(String[] args) {
//        printAllPermutations("aac");
        printAllPermutationsNoRepeat("aac");
    }
}
