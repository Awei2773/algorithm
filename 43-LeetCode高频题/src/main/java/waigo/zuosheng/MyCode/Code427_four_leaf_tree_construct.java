package waigo.zuosheng.MyCode;

import java.util.*;

/**
 * author waigo
 * create 2024-07-23 22:32
 * https://leetcode.cn/problems/construct-quad-tree/
 */
public class Code427_four_leaf_tree_construct {
    public static class Node {
        public boolean val;
        public boolean isLeaf;
        public Node topLeft;
        public Node topRight;
        public Node bottomLeft;
        public Node bottomRight;


        public Node() {
            this.val = false;
            this.isLeaf = false;
            this.topLeft = null;
            this.topRight = null;
            this.bottomLeft = null;
            this.bottomRight = null;
        }

        public Node(boolean val, boolean isLeaf) {
            this.val = val;
            this.isLeaf = isLeaf;
            this.topLeft = null;
            this.topRight = null;
            this.bottomLeft = null;
            this.bottomRight = null;
        }

        public Node(boolean val, boolean isLeaf, Node topLeft, Node topRight, Node bottomLeft, Node bottomRight) {
            this.val = val;
            this.isLeaf = isLeaf;
            this.topLeft = topLeft;
            this.topRight = topRight;
            this.bottomLeft = bottomLeft;
            this.bottomRight = bottomRight;
        }

        public String toBfsString() {
            StringBuilder sb = new StringBuilder();
            //处理头节点
            if (this.isLeaf) {
                sb.append("[[1,").append(getVal()).append("]]");
                return sb.toString();
            }

            sb.append("[");

            //注意：ArrayDeque不许加入null元素
            Deque<Node> bfsCurFloorQueue = new LinkedList<>();
            bfsCurFloorQueue.add(this);

            while (!bfsCurFloorQueue.isEmpty()) {
                Node head = bfsCurFloorQueue.pollFirst();
                if (head == null) {
                    sb.append("null,");
                    continue;
                }

                //叶子节点:[1, val],
                //非叶子节点:[0, 1]
                sb.append("[").append(head.isLeaf ? 1 : 0).append(",").append(head.getVal()).append("],");

                bfsCurFloorQueue.addLast(head.topLeft);
                bfsCurFloorQueue.addLast(head.topRight);
                bfsCurFloorQueue.addLast(head.bottomLeft);
                bfsCurFloorQueue.addLast(head.bottomRight);
            }

            sb.setCharAt(sb.length() - 1, ']');

            //清除掉尾部多余的[null,null]
            String s = sb.toString();
            String suffix = "null]";
            while (s.endsWith(suffix)) {
                s = s.substring(0, s.length() - suffix.length() - 1) + ']';
            }

            return s;
        }

        public char getVal() {
            return this.val ? '1' : '0';
        }
    }

    public Node construct(int[][] grid) {
        if (grid == null || grid.length < 0 || grid[0].length < 0 || grid.length != grid[0].length) {
            return null;
        }

        return construct(0, grid.length, 0, grid[0].length, grid);
    }

    //基于rowStart, rowEnd, colStart, colEnd划定的n x n的矩阵构建四叉树，其中n可以整除2
    private Node construct(int rowStart, int rowEnd, int colStart, int colEnd, int[][] grid) {
        //1. 基本情况: 最后一个格子
        if (rowStart == rowEnd - 1) {
            return new Node(grid[rowStart][colStart] == 1, true);
        }

        //2. 收集当前节点的四个子节点
        //注意加上偏移量rowStart, colStart
        int bottomRowStart = rowStart + ((rowEnd - rowStart) >> 1);
        int rightColStart = colStart + ((colEnd - colStart) >> 1);

        Node topLeft = construct(rowStart, bottomRowStart, colStart, rightColStart, grid);
        //topRight
        Node topRight = construct(rowStart, bottomRowStart, rightColStart, colEnd, grid);

        //bottomLeft
        Node bottomLeft = construct(bottomRowStart, rowEnd, colStart, rightColStart, grid);

        //bottomRight
        Node bottomRight = construct(bottomRowStart, rowEnd, rightColStart, colEnd, grid);

        //3.子有非叶，当前节点必定非叶
        boolean isLeaf = topLeft.isLeaf && topRight.isLeaf && bottomLeft.isLeaf && bottomRight.isLeaf;

        //4. 子值有不同值，当前节点必定非叶
        boolean topLeftVal = topLeft.val;
        if (topRight.val != topLeftVal || bottomLeft.val != topLeftVal || bottomRight.val != topLeftVal) {
            isLeaf = false;
        }

        boolean currentVal = !isLeaf || topLeftVal;

        //5. 当前节点非叶
        if (!isLeaf) {
            return new Node(currentVal, isLeaf, topLeft, topRight, bottomLeft, bottomRight);
        }
        //6. 当前节点为叶
        else {
            return new Node(currentVal, isLeaf);
        }
    }
}
