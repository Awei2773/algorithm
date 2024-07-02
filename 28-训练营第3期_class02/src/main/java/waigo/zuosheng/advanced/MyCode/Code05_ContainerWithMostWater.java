package waigo.zuosheng.advanced.MyCode;

/**
 * author waigo
 * create 2021-05-08 13:56
 */
/*
Given n non-negative integers a1, a2, ..., an , where each represents a point
at coordinate (i, ai). n vertical lines are drawn such that the two endpoints
 of the line i is at (i, ai) and (i, 0). Find two lines, which, together with
 the row-axis forms a container, such that the container contains the most water.

Notice that you may not slant the container.

Example 1:

Input: height = [1,8,6,2,5,4,8,3,7]
Output: 49
Explanation: The above vertical lines are represented by array [1,8,6,2,5,4,8,3,7]. In this case, the max area of water (blue section) the container can contain is 49.
Example 2:

Input: height = [1,1]
Output: 1
Example 3:

Input: height = [4,3,2,1,4]
Output: 16
Example 4:

Input: height = [1,2,1]
Output: 2
 

Constraints:

n == height.length
2 <= n <= 10^5
0 <= height[i] <= 10^4


思路分析：
1.暴力法
解读完题目可以很快想到暴力解，就是枚举所有的边的二元组答案必在其中，但是这会是N^2的解
题目的数据量为10^5，一平方就是10^10必定超时，所以想一下是否所有的情况都要考虑到

接水的量等于=两线距离*min(线1，线2),假设此时短线是1，那么线1和线2之间的线x和线1组成的
二元组答案不可能比(线1,线2)还大。因为此时两线距离缩短了，min(线1，线x)不会大于线1，
那么结果肯定就要小了。


2.双指针法
一个指针指向开始，一个指针指向结尾，每次拿到两个指针中短的那个的答案，短指针移动
遍历一遍后所有线的答案都拿到了

为什么只用考虑两个指针中间的区域的最大值，比如说1,8,6,2,5,4,8,3,7
                                         |           |
                                        head        tail
为什么不用考虑1~3这种的了，就是如何保证head不回移还能不丢失答案，如何保证单调性？
*/
public class Code05_ContainerWithMostWater {
    public static int maxArea(int[] height) {
        if(height==null||height.length<2) return 0;
        int head = 0;
        int tail = height.length-1;
        int waterMax = 0;
        while (head<=tail){
            waterMax =Math.max(waterMax,
                    (tail-head)*(
                            height[head]<=height[tail]?height[head++]:height[tail--]
                    ));
        }
        return waterMax;
    }
}
