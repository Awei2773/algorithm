package waigo.zuosheng.advanced.MyCode;

import java.util.ArrayList;
/**
 * author waigo
 * create 2021-05-02 9:01
 */
/*
Given an integer array nums and two integers lower and upper, return the number of range sums that lie in [lower, upper] inclusive.
Range sum S(i, j) is defined as the sum of the elements in nums between indices i and j inclusive, where i <= j.
Example 1:
Input: nums = [-2,5,-1], lower = -2, upper = 2
Output: 3
Explanation: The three ranges are: [0,0], [2,2], and [0,2] and their respective sums are: -2, -1, 2.
Example 2:
Input: nums = [0], lower = 0, upper = 0
Output: 1

思路：
1.这种题优化的关键就在于减少子数组和的求解，之前求过的和就不要再求一遍了，可以利用前缀和来生成当前和。
2.如果idx=3位置的curSum=5,lower=-2,upper=2,那么只要看idx=0,1,2位置的sum有几个满足lower<=curSum-sum<=upper的，
也就是curSum-upper<=sum<=curSum-lower 的有几个位置这不就能解题了？
3.curSum-upper<=sum<=curSum-lower可以转变为count(<curSum-lower+1)-count(<curSum-upper)
4.这个api(countOfLessKey)由有序表来实现是最好的，能够做到查询的时间复杂度O(logN),所以前面位置的和要放入到有序表中
为了减少扰动，性能更好，这里采用SB树，比较好实现和改写
可能会有重复值，所以节点添加一个all字段表示这个节点上有几个重复值，size字段维护SB树自己的平衡性
5.countOfLessKey(int key)编写思路：

*/
public class Code01_CountOfRangeSum {
    public static class SBTreeSet<K extends Comparable<K>> {
        SBTreeNode root;

        public class SBTreeNode {
            K key;
            int all = 1;//子树里存过的所有东西，有重复值的，业务相关
            int size = 1;//子树不同节点的个数，用来维护SB树的，这个非常重要，必须是不同节点的个数，别和业务需要给混了，
            // 不然调平衡的时候栈溢出了都找不到问题出在哪，因为都和SB树定义背离了
            SBTreeNode left, right;

            public SBTreeNode(K key) {
                this.key = key;
            }
        }

        public void add(K key) {
            root = add(root, key, contains(key));
        }

        public boolean contains(K key) {
            SBTreeNode cur = root;
            while (cur != null) {
                int judge = cur.key.compareTo(key);
                if (judge == 0) {
                    break;
                } else if (judge > 0) {
                    cur = cur.left;
                } else {
                    cur = cur.right;
                }
            }
            return cur != null;
        }

        //将key插入到cur这棵树下，返回插入结束且经过平衡调整后的树头
        private SBTreeNode add(SBTreeNode cur, K key, boolean contains) {
            if (cur == null) return new SBTreeNode(key);
            if (!contains) cur.size++;
            cur.all++;//只要是有节点进来all就++

            int judge = cur.key.compareTo(key);
            if (judge == 0) {
                return cur;
            } else if (judge > 0) {
                cur.left = add(cur.left, key, contains);//插入左子树
            } else {
                cur.right = add(cur.right, key, contains);//插入右子树
            }
            return maintain(cur);
        }

        private SBTreeNode maintain(SBTreeNode head) {//注意调平衡的时候，所有size发生了变化的子树都要调平衡，而且从下到上
            if (head == null) return null;
            int rSize = head.right == null ? 0 : head.right.size;
            int lSize = head.left == null ? 0 : head.left.size;
            //LL型
            if (head.left != null && head.left.left != null && head.left.left.size > rSize) {
                head = rightRotate(head);
                head.right = maintain(head.right);
                head = maintain(head);
            }
            //LR型
            if (head.left != null && head.left.right != null && head.left.right.size > rSize) {
                head.left = leftRotate(head.left);
                head = rightRotate(head);
                head.right = maintain(head.right);
                head.left = maintain(head.left);
                head = maintain(head);
            }
            //RR型
            if (head.right != null && head.right.right != null && head.right.right.size > lSize) {
                head = leftRotate(head);
                head.left = maintain(head.left);
                head = maintain(head);
            }
            //RL型
            if (head.right != null && head.right.left != null && head.right.left.size > lSize) {
                head.right = rightRotate(head.right);
                head = leftRotate(head);
                head.right = maintain(head.right);
                head.left = maintain(head.left);
                head = maintain(head);
            }
            return head;
        }

        //oldHead.right!=null
        private SBTreeNode leftRotate(SBTreeNode oldHead) {
            SBTreeNode newHead = oldHead.right;
            int same = oldHead.all - (oldHead.left==null?0:oldHead.left.all) -
                    (oldHead.right==null?0:oldHead.right.all);
            oldHead.right = newHead.left;
            newHead.left = oldHead;
            newHead.size = oldHead.size;
            oldHead.size = (oldHead.left == null ? 0 : oldHead.left.size)
                    + (oldHead.right == null ? 0 : oldHead.right.size) + 1;

            newHead.all = oldHead.all;//交换的时候肯定也要维护all
            oldHead.all = (oldHead.left == null ? 0 : oldHead.left.all)
                    + (oldHead.right == null ? 0 : oldHead.right.all) + same;
            return newHead;
        }

        //oldHead.left!=null
        private SBTreeNode rightRotate(SBTreeNode oldHead) {
            SBTreeNode newHead = oldHead.left;
            int same = oldHead.all - (oldHead.left==null?0:oldHead.left.all) - (oldHead.right==null?0:oldHead.right.all);
            oldHead.left = newHead.right;
            newHead.right = oldHead;
            newHead.size = oldHead.size;
            oldHead.size = (oldHead.left == null ? 0 : oldHead.left.size) + (oldHead.right == null ? 0 : oldHead.right.size) + 1;

            newHead.all = oldHead.all;
            oldHead.all = (oldHead.left == null ? 0 : oldHead.left.all)
                    + (oldHead.right == null ? 0 : oldHead.right.all) + same;
            return newHead;
        }

        /**
         * 返回<key的size
         */
        public int countOfLessKey(K key) {
            int res = root.all;
            SBTreeNode cur = root;
            while (cur != null) {
                if (key.compareTo(cur.key) > 0) {//key>cur.key
                    cur = cur.right;//往右滑
                } else {
                    res = res - cur.all;
                    cur = cur.left;//往左滑
                    res += (cur == null ? 0 : cur.all);
                }
            }
            return res;
        }

        public ArrayList<K> getInOrder() {
            ArrayList<K> order = new ArrayList<>();
            getInOrder(root, order);
            return order;
        }

        private void getInOrder(SBTreeNode root, ArrayList<K> order) {
            if (root == null) return;
            getInOrder(root.left, order);
            order.add(root.key);
            getInOrder(root.right, order);
        }
    }

    //for test,为了校验SB树是正确的，需要对数器，add任意树，任意时刻这个SB树的中序序列都是有序的
    public static <K extends Comparable<K>> boolean isSorted(ArrayList<K> list) {
        for (int i = 0; i < list.size(); i++) {
            K iKey = list.get(i);
            for (int j = i + 1; j < list.size(); j++) {
                if (iKey.compareTo(list.get(j)) > 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public  int countRangeSum(int[] nums, int lower, int upper) {
        long prefixSum = 0;
        SBTreeSet<Long> set = new SBTreeSet<>();
        set.add((long) 0);
        int res = 0;
        for(int i = 0;i<nums.length;i++){
            prefixSum+=nums[i];
            //抓一下以这个位置结尾的子数组是否有符合条件的
            res+=set.countOfLessKey(prefixSum-lower+1)-set.countOfLessKey(prefixSum-upper);
            set.add(prefixSum);
        }
        return res;
    }
//      for test
//    public static void main(String[] args) {
//        SBTreeSet<Integer> set = new SBTreeSet<>();
//        int testTimes = 100, value = 10;
//        int i = 0;
//        for (; i < testTimes; i++) {
//            if (Math.random() < 0.5) {
//                set.add((int) (Math.random() * value) - (int) (Math.random() * value));
//            } else {
//                ArrayList<Integer> inOrder = set.getInOrder();
//                System.out.println(inOrder);
//                if (inOrder.size() > 0) {
//                    System.out.println(set.countOfLessKey(inOrder.get(inOrder.size() - 1)));
//                }
//                if (!isSorted(inOrder)) break;
//            }
//        }
//        System.out.println(i == testTimes ? "finish!!!" : "fucking!!!");
//    }

    public static void main(String[] args) {
        int[] nums = new int[]{2147483647,-2147483648,-1,0};
        Code01_CountOfRangeSum sum = new Code01_CountOfRangeSum();
        System.out.println(sum.countRangeSum(nums,-1,0));
    }
}
