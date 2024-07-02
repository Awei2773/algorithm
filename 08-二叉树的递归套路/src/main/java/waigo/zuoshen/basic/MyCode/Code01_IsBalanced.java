package waigo.zuoshen.basic.MyCode;

/**
 * author waigo
 * create 2021-02-23 19:22
 */
public class Code01_IsBalanced {
    //给定一棵二叉树的头节点head，返回这颗二叉树是不是平衡二叉树
    //平衡二叉树：1）左树平衡2）右树平衡3）左右树高度差绝对值小于2(<=1)
    //向子树要树高，和树是否平衡的信息
    public static class Info {
        private int height;
        private boolean isBalanced;

        public Info(int height, boolean isBalanced) {
            this.height = height;
            this.isBalanced = isBalanced;
        }
    }

    public static class Node {
        private int value;
        private Node left;
        private Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    public static boolean isBalanced1(Node head) {
        return recurCheck(head).isBalanced;
    }

    public static Info recurCheck(Node head) {
        if (head == null) {
            return new Info(0, true);
        }
        //左树，右树要信息
        Info leftInfo = recurCheck(head.left);
        Info rightInfo = recurCheck(head.right);
        //加工我的信息返回
        return new Info(
                Math.max(leftInfo.height, rightInfo.height) + 1,
                leftInfo.isBalanced
                        &&
                        rightInfo.isBalanced
                        &&
                        Math.abs(leftInfo.height - rightInfo.height) < 2);
    }
    public static boolean isBalanced2(Node head){
        boolean[] ans = new boolean[1];
        ans[0] = true;
        process2(head,ans);
        return ans[0];
    }
    //浓缩，同理的，就是不用创建新类
    public static int process2(Node head,boolean[] ans){
        if(head==null) return 0;
        int leftHeight = process2(head.left,ans);
        int rightHeight = process2(head.right,ans);
        if(Math.abs(leftHeight-rightHeight)>1)
            ans[0] = false;

        return Math.max(leftHeight,rightHeight)+1;
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
            if (isBalanced1(head) != isBalanced2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }

}
