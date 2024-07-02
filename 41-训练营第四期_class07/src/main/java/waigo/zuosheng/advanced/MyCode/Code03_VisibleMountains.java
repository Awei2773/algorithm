package waigo.zuosheng.advanced.MyCode;


/**
 * author waigo
 * create 2021-07-06 21:07
 */

import java.util.Stack;

/**
 * 一个不含有负数的数组可以代表一圈环形山，每个位置的值代表山的高度。
 * 比如， {3,1,2,4,5}、{4,5,3,1,2}或{1,2,4,5,3}都代表同样结构的环形山。
 * 山峰A和山峰B能够相互看见的条件为:
 * 1.如果A和B是同一座山，认为不能相互看见。
 * 2.如果A和B是不同的山，并且在环中相邻，认为可以相互看见。
 * 3.如果A和B是不同的山，并且在环中不相邻，假设两座山高度的最小值为min。
 *
 * 1)如果A通过顺时针方向到B的途中没有高度比min大的山峰，认为A和B可以相互 看见
 * 2)如果A通过逆时针方向到B的途中没有高度比min大的山峰，认为A和B可以相互 看见
 * 3)两个方向只要有一个能看见，就算A和B可以相互看见 给定一个不含有负数且没有重复值的数组 arr，
 * 请返回有多少对山峰能够相互看见。
 *
 * 进阶: 给定一个不含有负数但可能含有重复值的数组arr，返回有多少对山峰能够相互看见。
 */
public class Code03_VisibleMountains {
    public static class Moutain{
        int height;
        int size;//有几个相同值

        public Moutain(int height, int size) {
            this.height = height;
            this.size = size;
        }
    }
    public static int getVisibleNum(int[] arr) {
        if(arr==null||arr.length<2) return 0;
        //1.如果没有重复值
        //找到山峰中的最高峰和次高峰
        //剩下的山峰都作为min去顺时针，逆时针找大的
        //那么剩下所有的山峰必能在顺时针和逆时针都能看到一个山峰，那么这里的山峰对就有(N-2)*2
        //剩下的次高峰顺时针，逆时针肯定只能看到最高峰，这里只有一对，所以可见山峰对数量为2N-3
        //2.如果有重复值
        //如果重复值中间没有能挡住它们的高峰，那么这对重复值是可见的，所以第一种方法失效
        //所以就得一次结算一批相同值的位置，要快速知道这批位置左边最近比它大和右边最近比它大的信息，想到单调栈
        //一批位置结算方式1：左右存在不同的比它大的值，numRes = C(n,2)+2*n,n为相同值的个数
        //一批位置结算方式2：找到比它大的值是同一个，numRes = n+C(n,2)
        //一批位置结算方式3：最大的那个数，只有自己内部可见，numRes = C(n,2)
        Stack<Moutain> stack = new Stack<>();
        //先找到最大值的下标，并且将它作为栈底，整一个从底到顶，从大到小的单调栈
        int max = arr[0];
        int maxIdx = 0;
        int N = arr.length;
        for(int i = 1; i< N; i++){
            if(arr[i]>max){
                max = arr[i];
                maxIdx = i;
            }
        }
        //将最大值压栈
        stack.push(new Moutain(max,1));
        int res = 0;
        int curMountain = nextIdx(maxIdx, N);
        while (curMountain!=maxIdx){
            int curHeight = arr[curMountain];
            while(curHeight>stack.peek().height){
                Moutain top = stack.pop();
                res += (getInternal(top))+(top.size<<1);
            }
            if(curHeight==stack.peek().height){
                stack.peek().size+=1;
            }else{
                stack.push(new Moutain(curHeight,1));
            }
            curMountain = nextIdx(curMountain,N);
        }
        while(stack.size()>2){
            Moutain top = stack.pop();
            res+= getInternal(top) +(top.size<<1);
        }
        //右边没有的结算
        if(stack.size()==2){
            Moutain top = stack.pop();
            res+=getInternal(top)+
                    (stack.peek().size==1?top.size:(top.size<<1));
        }
        //最大那个的结算
        Moutain top = stack.pop();
        res+=getInternal(top);

        return res;
    }

    private static int getInternal(Moutain top) {
        return (top.size * (top.size - 1)) >> 1;
    }

    public static int nextIdx(int idx,int N){
        return (idx+1)%N;
    }

    public static void main(String[] args) {
        System.out.println(getVisibleNum(new int[]{8,2,4,5,3,3,3 }));
    }
}
