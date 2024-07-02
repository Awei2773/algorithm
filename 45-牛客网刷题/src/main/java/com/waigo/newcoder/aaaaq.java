package com.waigo.newcoder;

public class aaaaq {

	public static void main(String[] args) {
		int num=1000;
		process1(num);
		process2(num);
		//int num=234;
		//int g=(num/1)%10;//个位
		//System.out.println(g);
		//int s=(num/10)%10;//十位
		//System.out.println(s);
		//int b=(num/100)%10;//百位
		//System.out.println(b);
		//int b2=(num/1000)%10;//千位
		//System.out.println(b2);
	}
	public static void process1(int n){
		for(int i=2;i<n;i++){
			if(isResult(i)){
				System.out.print(i+" ");
			}
		}
	}
	public static void process2(int n){
		for(int i=2;i<n;i++){
			int g=(i/1)%10;
			int s=(i/10)%10;
			int b=(i/100)%10;
			int q=(i/1000)%10;
			if(isResult(i)&&(g+s+b+q)%2==0){
				System.out.print(i+" ");
			}
		}
	}
	public static boolean isResult(int n){
		for(int i=2;i<n;i++){
			if(n%i==0){
				return false;
			}
		}
		return true;
	}


}