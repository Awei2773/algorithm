package waigo.zuoshen.advance.MyCode;

/**
 * author waigo
 * create 2021-03-18 11:13
 */

/*
给定一个单链表，随机选择链表的一个节点，并返回相应的节点值。保证每个节点被选的概率一样。
进阶:
如果链表十分大且长度未知，如何解决这个问题？你能否使用常数级空间复杂度实现？
示例:
// 初始化一个单链表 [1,2,3].
ListNode head = new ListNode(1);
head.next = new ListNode(2);
head.next.next = new ListNode(3);
Solution solution = new Solution(head);
// getRandom()方法应随机返回1,2,3中的一个，保证每个元素被返回的概率相等。
solution.getRandom();

解题思路：
对于这道题目来说，需要我们返回随机一个位置节点的值，
那么好嘛，可以定义一个袋子，假如这个袋子容量就是1，
那么每次从袋子中淘汰的概率都是1，因为就一个元素，
所以必定淘汰它。只要将所有节点都判断一遍，最后袋子里剩下的不就是随机节点的值吗。
 */
public class Code02_RandomListNode {

    //Definition for singly-linked list.
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

    public static class Solution {
        private ListNode head;

        /**
         * @param head The linked list's head.
         *             Note that the head is guaranteed to be not null, so it contains at least one node.
         */
        public Solution(ListNode head) {
            this.head = head;
        }

        /**
         * Returns a random node's value.
         */
        public int getRandom() {
            int[] bag = new int[10];
            int count = 0;//取出来的节点序号
            ListNode temp = head;
            while (temp != null) {
                count++;//取出一个节点
                if (count <= 10) {//前10个直接入袋
                    bag[count - 1] = temp.val;//count是序号，count-1是index
                } else {
                    //超过10个，按照10/count的概率入袋
                    if (rand(count) <= 10) {
                        //1/10概率随机淘汰一个下标的元素
                        bag[rand(10) - 1] = temp.val;
                    }
                }
                temp = temp.next;
            }
            return count > 10 ? bag[(int) (Math.random() * 10)] : bag[(int) (Math.random() * count)];
        }

        //return rand 1~max
        public int rand(int max) {
            return (int) (Math.random() * max) + 1;
        }

        public int getRandom1() {
            //直接假设袋子容量为1
            int count = 0;
            int res = 0;
            ListNode temp = head;
            while (temp != null) {
                count++;
                if (count <= 1) {
                    res = temp.val;
                } else {
                    if (rand(count) <= 1) {
                        res = temp.val;
                    }
                }
                temp = temp.next;
            }
            return res;
        }

    }

    public static void main(String[] args) {
        // 初始化一个单链表 [1,2,3].
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        Solution solution = new Solution(head);

        System.out.println(solution.getRandom());

    }

}
