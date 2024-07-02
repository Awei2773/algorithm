package waigo.zuoshen.advance.MyCode;

import java.util.Arrays;

/**
 * author waigo
 * create 2021-06-22 14:04
 */
@SuppressWarnings("Duplicates")
public class Code03_FindMinKth {
    public int findKth(int[] a, int n, int K) {
        return process(a,0,a.length-1,K-1);
    }
    //潜台词K肯定是在l...r中
    public int process(int[] a,int l,int r,int K){
        if(l==r) return a[l];
        //1.随机pivot
        swap(a,l+(int)(Math.random()*(r-l+1)),r);
        //2.flags = [smallTop,hignLow]
        int[] flags = partition(a,l,r);
        if(K<=flags[0]){
            return process(a,l,flags[0],K);
        }
        if(K>=flags[1]){
            return process(a,flags[1],r,K);
        }
        return a[flags[0]+1];
    }
    public void swap(int[] a,int aIdx,int bIdx){
        int temp = a[aIdx];
        a[aIdx] = a[bIdx];
        a[bIdx] = temp;
    }
    public int[] partition(int[] a,int l,int r){
        int smallTop = l-1;
        int highLow = r;
        int pivot = a[r];
        for(int j = l;j<highLow;j++){
            if(a[j]<pivot){
                swap(a,++smallTop,j);
            }else if(a[j]>pivot){
                swap(a,--highLow,j--);
            }
        }
        swap(a,r,highLow++);
        return new int[]{smallTop,highLow};
    }

    public static void main(String[] args) {
        int[] a= {1332802,1177178,1514891,871248,753214,123866,1615405,328656,1540395,968891,1884022,252932,1034406,1455178,821713,486232,860175,1896237,852300,566715,1285209,1845742,883142,259266,520911,1844960,218188,1528217,332380,261485,1111670,16920,1249664,1199799,1959818,1546744,1904944,51047,1176397,190970,48715,349690,673887,1648782,1010556,1165786,937247,986578,798663};
        int n = 49;
        int K = 24;
        Arrays.sort(a);
        System.out.println(Arrays.toString(a));
        System.out.println(a[25]);
    }
}
