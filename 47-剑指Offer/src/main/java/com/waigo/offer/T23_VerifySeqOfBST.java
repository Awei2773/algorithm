package com.waigo.offer;

/**
 * author waigo
 * create 2021-09-17 11:20
 */
public class T23_VerifySeqOfBST {
    public boolean VerifySquenceOfBST(int [] sequence) {
        if(sequence==null||sequence.length==0) return false;
        int N = sequence.length;
        int[] res = process(sequence,0,N-1);
        return res[0] == 1;//0为false,1为true
    }
    //返回结果中0:isBST,1:min,2:max
    //在数组上玩二叉树套路
    //需要左右子树返回三个条件，1.自己是否是BST 2.自己的最小值 3.自己的最大值
    //但是左子树右子树的切分点j需要二分找出来,找到的是左子树的最右点，如果找不到返回left - 1
    //这样左区就变成了[left,j],右区就变成了[j+1,right]
    public int[] process(int[] sequence,int left,int right){
        if(left>right) return null;
        int isBST = 1,min = sequence[right],max = sequence[right];
        //找到比sequence[right]小的最右位置
        int pivot = sequence[right];
        int leftMax = findLeftMax(sequence,left,right - 1,pivot);
        int[] leftRes = process(sequence,left,leftMax);
        int[] rightRes = process(sequence,leftMax+1,right-1);
        if(leftRes!=null){
            isBST = Math.min(isBST,leftRes[0]);
            if(leftRes[2]>pivot){//左边最大值大于pivot
                isBST = 0;
            }
            min = Math.min(min,leftRes[1]);
            max = Math.max(max,leftRes[2]);
        }
        if(rightRes!=null){
            isBST = Math.min(isBST,rightRes[0]);
            if(rightRes[1]<pivot){//右边最小值小于pivot
                isBST = 0;
            }
            min = Math.min(min,rightRes[1]);
            max = Math.max(max,rightRes[2]);
        }
        return new int[]{isBST,min,max};
    }
    public int findLeftMax(int[] sequence,int left,int right,int pivot){
        if(left>right) return left - 1;
        int target = left - 1;
        while(left<=right){
            int mid = ((right-left)>>>1)+left;
            if(sequence[mid]<pivot){
                target = mid;
                left = mid + 1;//向右找
            }else{
                right = mid - 1;//向左找
            }
        }
        return target;
    }

    public static void main(String[] args) {
        T23_VerifySeqOfBST t23_verifySeqOfBST = new T23_VerifySeqOfBST();
        System.out.println(t23_verifySeqOfBST.VerifySquenceOfBST(new int[]{4,8,6,12,9,14,10}));
    }
}
