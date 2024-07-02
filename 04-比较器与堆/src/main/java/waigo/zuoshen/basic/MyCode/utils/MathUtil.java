package waigo.zuoshen.basic.MyCode.utils;

/**
 * author waigo
 * create 2021-02-13 13:52
 */
public class MathUtil {
    public static int[] generalRandomArray(int maxSize,int maxValue){
        int size = (int)(Math.random()*maxSize)+1;//1~maxSize
        int[] newArray = new int[size];
        for(int i = 0;i < size;i++){
            newArray[i] = (int)(Math.random()*maxValue)-(int)(Math.random()*maxValue);
        }
        return newArray;
    }
}
