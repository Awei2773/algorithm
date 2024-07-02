package waigo.zuoshen.basic.MyCode;


/**
 * author waigo
 * create 2021-02-23 20:03
 */
//给定一棵二叉树的头节点head，返回这颗二叉树是不是满二叉树
//思路一：左子树是满的，右子树也是满的，他们高度相同那这棵树就是满的
//思路二：左树右树高度相同，然后整棵树节点数为2^n-1，那么也是满的
public class Code02_IsFull {
    public static class Node{
        private int value;
        private Node left;
        private Node right;

        public Node(int value) {
            this.value = value;
        }
    }
    //思路一：左子树是满的，右子树也是满的，他们高度相同那这棵树就是满的
    public static class Info1{
        private boolean isFull;
        private int height;

        public Info1(boolean isFull, int height) {
            this.isFull = isFull;
            
            this.height = height;
        }
    }
    public static boolean isFull1(Node head){
        return process1(head).isFull;
    }

    private static Info1 process1(Node head) {
        if(head==null){
            return new Info1(true,0);
        }
        //左右树拿消息
        Info1 leftInfo = process1(head.left);
        Info1 rightInfo = process1(head.right);
        //加工
        return new Info1(
                leftInfo.isFull&&rightInfo.isFull&&leftInfo.height==rightInfo.height,
                Math.max(leftInfo.height,rightInfo.height)+1
        );
    }
    //思路二：左树右树高度相同，然后整棵树节点数为2^n-1，n是树高，那么也是满的
    public static class Info2{
        private int height;
        private int nodes;//节点数

        public Info2(int height, int nodes) {
            this.height = height;
            this.nodes = nodes;
        }
    }
    public static boolean isFull2(Node head){
        Info2 info2 = process2(head);
        return info2.nodes == ((1<<info2.height)-1);//2^n - 1
    }

    private static Info2 process2(Node head) {
        if(head == null){
            return new Info2(0,0);
        }
        Info2 leftInfo = process2(head.left);
        Info2 rightInfo = process2(head.right);
        //加工
        return new Info2(Math.max(leftInfo.height,rightInfo.height)+1,
                leftInfo.nodes+rightInfo.nodes+1
                );
    }
    // for test
    public static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    // for test
    public static Node generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        Node head = new Node((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    public static void main(String[] args) {
        int maxLevel = 5;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (isFull1(head) != isFull2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }

}
/*
* 套路是一种思考的方式，你可以向左右两棵树要任何信息，因为base case，对于一棵空树来说所有的信息都是非常简单的
* ，我们给计算机一种处理问题的逻辑，然后繁杂的细节让计算机去处理。
* 所以一道题思考的方向不同，做法可能不同，但是由于递归序，每个节点到三次，那么时间复杂度都是O(N),然后空间复杂度
* 假如栈帧是O(1)的话，那么最多只会将一个树的最高那条路径全部压栈，所以是O(树高)
* */
