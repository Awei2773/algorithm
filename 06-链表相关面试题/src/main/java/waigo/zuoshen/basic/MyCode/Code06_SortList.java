package waigo.zuoshen.basic.MyCode;

import java.util.ArrayList;

/**
 * author waigo
 * create 2021-04-02 23:28
 */
/*
给你链表的头结点 head ，请将其按 升序 排列并返回 排序后的链表 。
进阶：
你可以在 O(n log n) 时间复杂度和常数级空间复杂度下，对链表进行排序吗？
*/
@SuppressWarnings("Duplicates")
public class Code06_SortList {
    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    //链表上玩不了快排，就算能玩也是只能选定一个基准，比如每次都是用第一个作为基准，这样最差情况下就是人家链表有序了，你每次分边的时候都是
    //左边0个，右边N-1个，这时间复杂度是O(N^2),所以你想玩快排只能够将链表拷贝一遍进数组然后在数组中玩快排后再串起链表
    //快排：空间复杂度O(N),这个是过不了大用例的，时间复杂度O(N*logN)
    public static ListNode sortList(ListNode head) {
        ArrayList<ListNode> ary = new ArrayList<>();
        ListNode cur = head;
        while (cur != null) {
            ary.add(cur);
            cur = cur.next;
        }
        //进行快排排序
        quickSort(ary, 0, ary.size() - 1);
        //整出新链
        ListNode newHead = ary.get(0);
        ListNode tail = ary.get(0);
        for (int i = 1; i < ary.size(); i++) {
            tail.next = ary.get(i);
            tail = tail.next;
            tail.next = null;//防止出现回环
        }
        return newHead;
    }

    //完成[l...r]上的快排
    private static void quickSort(ArrayList<ListNode> ary, int l, int r) {
        if (l < r) {
            //随机选一个作为pivot,经典快排
            int ri = (int) (Math.random() * (r - l + 1)) + l;
            swap(ary, ri, r);
            int[] flags = partition(ary, l, r);//0:小区的右边界，1:大区左边界
            //左边递归去
            quickSort(ary, l, flags[0]);
            //右边递归去
            quickSort(ary, flags[1], r);
        }
    }

    //在l...r上玩荷兰国旗
    private static int[] partition(ArrayList<ListNode> ary, int l, int r) {
        int sTop = l - 1, bLow = r;//sTop指向小区的右边界，bLow指向大区的左边界
        int pivot = ary.get(r).val;
        int curVal;
        for (int j = l; j < bLow; j++) {
            curVal = ary.get(j).val;
            if (curVal < pivot) {//小区
                swap(ary, j, ++sTop);
            } else if (curVal > pivot) {//大区，得停在这里，从大区下来的是还没看过的
                swap(ary, j, --bLow);
                j--;
            }
        }
        swap(ary, r, bLow++);
        return new int[]{sTop, bLow};
    }

    private static void swap(ArrayList<ListNode> ary, int aIdx, int bIdx) {
        ListNode temp = ary.get(aIdx);
        ary.set(aIdx, ary.get(bIdx));
        ary.set(bIdx, temp);
    }

    public static ListNode generalRandomList(int maxLen, int maxVal) {
        int len = (int) (Math.random() * maxLen) + 1;
        ListNode head = new ListNode((int) (Math.random() * maxVal) - (int) (Math.random() * maxVal));
        for (int i = 0; i < len; i++) {
            ListNode newNode = new ListNode((int) (Math.random() * maxVal) - (int) (Math.random() * maxVal));
            newNode.next = head.next;
            head.next = newNode;
        }
        return head;
    }

    public static boolean isEqlList(ListNode node1, ListNode node2) {
        while (node1 != null && node2 != null) {
            if (node1.val != node2.val) return false;
            node1 = node1.next;
            node2 = node2.next;
        }
        return node1 == node2;
    }
    //链表相比较于数组的优势在于一个节点可以随便插入一个节点后面

    //归并排序最终空间复杂度为O(N)的原因是数组中的值无法随机插入，所以需要一个辅助表先将结果拷贝一遍再刷回原数组，但是链表其实可以做到随机插入的
    //所以链表的归并排序可以做到空间复杂度O(1)，只要不递归压栈，不然因为递归的缘故还是会O(logN)

    //递归版,时间复杂度：使用Master公式进行计算，T(N) = 2*T(N/2)+O(N),这个O(N)=O(N/2:找中间节点)+O(N:merge)
    //log(b,a) = log(2,2)=d=1所以归并排序的时间复杂度还是O(N*logN)
    //空间复杂度，主程序递归压栈和merge递归压栈其实是一个此消彼长的过程，主程序压的越多说明数据量越小，在merge中压的栈就会越小，最大情况下也就是logN
    public static ListNode mergeSortList(ListNode node) {
        if (node == null || node.next == null) return node;//一个节点就是有序的
        //找到中间节点(上中点或者中点)然后将整个链表从中间断开，等他们两边都有序后merge
        ListNode fast = node.next;
        ListNode slow = node;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        //slow来到了上中点，它后面那个节点串起来右半段链表
        ListNode right = slow.next;
        slow.next = null;//从中间断开,此时node串起左边，right串起右边，都递归去有序去
        node = mergeSortList(node);
        right = mergeSortList(right);
        //然后合并两条有序的链表
        return merge(node, right);
    }

    //递归版本
    private static ListNode merge(ListNode left, ListNode right) {
        if (left == right) return left;
        if (left == null || right == null) return left == null ? right : left;
        ListNode head;
        if (left.val <= right.val) {//稳定性
            head = left;
            left = left.next;
        } else {
            head = right;
            right = right.next;
        }
        head.next = merge(left, right);
        return head;
    }

    //非递归版本，就是自底向上的方式进行mergeSort
    public static ListNode mergeSortListNoRecur(ListNode node) {
        if (node == null || node.next == null) return node;
        //链表的长度
        int length = 0;
        ListNode tail = node;
        while (tail != null) {
            tail = tail.next;
            length++;
        }
        ListNode newHead = new ListNode(0,node);
        //mergeSize表示左边有序的个数，此值为1表示现在需要进行处理的区间共有2*1=2个值
        //时间复杂度O(N*logN),空间复杂度O(1)
        for (int mergeSize = 1; mergeSize < length; mergeSize <<= 1) {//这里总共会进入循环logN次，因为每次乘以2追赶，最终处理次数就是树高
            ListNode L = newHead.next;//L来到此时需要处理的链表的头
            tail = newHead;//准备尾插法刷新新的链表
            while (L != null) {//这里时间复杂度是O(2N)因为找M、R的时候会趟过原链表一遍，tail又会趟一遍
                ListNode M = L;//小分区的左边界
                for (int i = 1; i < mergeSize && M.next != null; i++) {
                    M = M.next;
                }
                if (M.next == null) {//右半区没有,就不需要做合并操作
                    tail.next = L;
                    break;
                }
                ListNode R = M;//小分区的右边界
                for (int i = 0; i < mergeSize && R.next != null; i++) {
                    R = R.next;
                }
                ListNode next = R.next;//下一个分区的开始
                R.next = null;//和下一个分区断开，这样就是从链表中取前一段出来进行merge操作
                R = M.next;//用R找到右分区
                M.next = null;//将需要merge的两个链表拆开
                tail.next = mergeNoRecur(L, R);
                while (tail.next != null) {//去新链表的尾巴
                    tail = tail.next;
                }
                L = next;
            }
            if (mergeSize > (length>>1)) break;//防止溢出
        }
        return newHead.next;
    }

    //非递归方式将两个有序链表merge,返回链表的头和尾
    public static ListNode mergeNoRecur(ListNode left, ListNode right) {
        //反正只要最后能够找到新的链表就好了，为了判断逻辑不出现多次，直接创建一个新头，让它的next指向合并后的链表
        ListNode newHead = new ListNode(0);
        ListNode tail = newHead;//尾巴，要尾插法保证顺序
        while (left != null && right != null) {
            if (left.val <= right.val) {
                tail.next = left;
                left = left.next;
            } else {
                tail.next = right;
                right = right.next;
            }
            tail = tail.next;//移动到末尾
        }
        if (left != null) {//这里和数组不同，直接可以将剩余的链表串在新链表上就好了
            tail.next = left;
        }
        if (right != null) {
            tail.next = right;
        }
        return newHead.next;
    }

    public static void main(String[] args) {
        ListNode listNode = generalRandomList(2, 10);
        ListNode cur = listNode;
        while (cur != null) {
            System.out.print(cur.val + " ");
            cur = cur.next;
        }
        System.out.println();
        ListNode listNode1 = mergeSortListNoRecur(listNode);
        while (listNode1 != null) {
            System.out.print(listNode1.val + " ");
            listNode1 = listNode1.next;
        }

    }
}
