package waigo.zuoshen.advance.MyCode;

/**
 * author waigo
 * create 2021-03-20 15:52
 */
/*
Morris遍历解决的问题：
无论是递归方式遍历二叉树还是我们自己压栈遍历二叉树，空间复杂度都是O(logN)的，也就是说要栈中要压入树高的节点
而Morris遍历能够将时间复杂度给降低到O(1),这其中的原理就是利用了树中的节点的指针

Morris遍历思路：
1.如果一个节点(N)有左孩子，找到左孩子中最右一个节点，遍历左树时最后一个遍历的肯定是这个最右节点，所以如果这个节点能够往上指
回到节点N，再通过某种机制告诉节点N它的左树遍历完了，可以遍历右树了，那么就能将整个流程串起来达到遍历的效果
2.如何告诉某种机制告诉节点N它的左树是否遍历完成了呢？
这很明显是一个状态值只有两种取值(true)or(false)的判断，如何创建出遍历前后产生两种结果？

2.1首先，从N的左树的最右一个节点(mostRight)要想回到N，那它肯定得知道N在哪，mostRight既然是最右一个节点，那它的right指针默认肯定指向null
所以可以将mostRight.right在某个时刻指向N,然后要在某个时刻再指回null，不能遍历了一遍树，树结构就变了嘛。

2.2很明显，现在出现遍历N的左子树前后结果不同的东西了，就是mostRight.right
2.2.1如果它想知道N在哪，那么工作指针在N的时候是最好的机会，这个时候还没开始遍历N的左子树mostRight.right=null，所以可以将mostRight.right = N
2.2.2依靠mostRight.right回到N的时候表示mostRight已经遍历过了，就证明N的左子树遍历结束，得将mostRight.right置回null

you can see that,N第一次见到mostRight的时候它的right是指向null的，然后就将它指向N开始遍历左子树，第二次见到mostRight的时候它
的right是指向N的，这表示已经遍历过N的左子树了

代码流程：cur一开始指向head，作为遍历的工作指针
1.到了最后一个节点的时候肯定是整棵树最右一个节点(mostTreeRight)，那个节点不会有头结点N找到它让它的right指向N，
所以等到cur指向null的时候表示cur已经到了这个最右节点(mostTreeRight)了
2.如果cur的左孩子直接就是null，那还遍历啥，直接遍历右孩子去

3.如果cur的左孩子不为null,那么分情况
3.1cur左子树的最右一个节点mostRight.right==null,还没遍历完左子树
mostRight.right=cur,然后继续往左走cur = cur.left

3.2cur左子树的最右一个节点mostRight.right==cur,左子树遍历完毕了
mostRight.right=null(复原),cur = cur.right,去遍历右子树去

now,show you the code ^_^
*/
@SuppressWarnings("Duplicates")
public class Code01_MorrisTraversal {
    public static class Node {
        int val;
        Node left;
        Node right;

        public Node(int val) {
            this.val = val;
        }
    }

    //morris序
    /*
        时间复杂度
            1.每个节点必到至少一次嘛，我也不管它是否有左子树了，那就是N
            2.某个节点最多趟两次左子树找最右节点，一棵树可以由很多右斜线(就是去找左子树最右节点那条线)分解
            所以所有节点N，趟两遍顶多多了也就2N，所以所有节点访问的次数往多多了数也就去了3N次，那时间复杂度肯定还是O(N)

        空间复杂度
            只用了两个指针，O(1)
    */
    public static void morrisSerial(Node head) {
        if (head == null) return;
        Node cur = head;
        Node mostRight;
        while (cur != null) {
            System.out.print(cur.val + "\t");
            if (cur.left != null) {
                mostRight = cur.left;
                while (mostRight.right != null && mostRight.right != cur) {//根据来到cur的先后mostRight.right的值会变
                    mostRight = mostRight.right;//右走
                }
                if (mostRight.right == null) {
                    //第一次来
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                }
                mostRight.right = null;
            }
            cur = cur.right;
        }
    }

    //通过morris序加工出先序，能够到两次的在第一次打印，到一次的直接打印就好
    public static void pre(Node head) {
        Node cur = head;
        Node mostRight;
        while (cur != null) {
            if (cur.left != null) {
                mostRight = cur.left;
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) {
                    System.out.print(cur.val + "\t");
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                }
                mostRight.right = null;
            } else {//有左子树的才会两次，没有左子树只会来一次
                System.out.print(cur.val + "\t");
            }
            cur = cur.right;
        }
    }

    //通过morris序加工出中序，能够到两次的在第一次打印，到一次的直接打印就好
    //通过morris序加工出后序，后序和前中序不同，利用的是树拆成很多右斜线，可以在能够回到它两次的节点第二次回到的时候逆序打印它的左边斜线
    public static void pos(Node head) {
        Node cur = head;
        Node mostRight;
        while (cur != null) {
            if (cur.left != null) {
                mostRight = cur.left;
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) {
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                }
                mostRight.right = null;//一定先置空，不然打印的时候会跑到cur然后把cur的右孩子那一串一起打印了
                printSlashRevOrd(cur.left);//等有左子树的节点回来第二次就逆序打印一下它的左边那个右斜线
            }
            cur = cur.right;
        }
        //最后头结点领衔的那个右斜线没有节点打印它，需要单独打印一次
        printSlashRevOrd(head);
    }

    public static Node reverseTreeSlash(Node head) {
        Node pre = null, cur = head, next = null;
        while (cur != null) {
            next = cur.right;
            cur.right = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }

    public static void printSlashRevOrd(Node head) {
        Node rHead = reverseTreeSlash(head);
        Node cur = rHead;
        while (cur != null) {
            System.out.print(cur.val + "\t");
            cur = cur.right;
        }
        reverseTreeSlash(rHead);//返回去
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(2);
        head.left.left = new Node(4);
        head.left.right = new Node(5);
        head.right = new Node(3);
        head.right.left = new Node(6);
        head.right.right = new Node(7);
        morrisSerial(head);
        System.out.println();
        printSlashRevOrd(head);
        System.out.println();
        pre(head);
        System.out.println();
        pos(head);
    }

}
