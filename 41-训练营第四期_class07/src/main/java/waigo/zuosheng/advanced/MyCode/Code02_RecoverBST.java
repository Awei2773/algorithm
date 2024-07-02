package waigo.zuosheng.advanced.MyCode;

/**
 * author waigo
 * create 2021-07-03 22:23
 */


import java.util.Stack;

/**
 * 一棵二叉树原本是搜索二叉树，但是其中有两个节点调换了位置，使得这棵二叉树不再 是搜索二叉树，
 * 请找到这两个错误节点并返回。已知二叉树中所有节点的值都不一样，给定二叉树的头节点 head，
 * 返回一个长度为2的 二叉树节点类型的数组errs，errs[0]表示一个错误节点， errs[1]表示另一个错误节 点。
 * 进阶: 如果在原问题中得到了这两个错误节点，我们当然可以通过交换两个节点的节点值的方 式
 * 让整棵二叉树重新成为搜索二叉树。 但现在要求你不能这么做，而是在结构上完全交换两个节点的位置，
 * 请实现调整的函数
 */
@SuppressWarnings("Duplicates")
public class Code02_RecoverBST {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public static void recoverTree(TreeNode root) {
        //调换后高位的去到低位置，低位出现高位
        //1.出现两对错误位置
        //2.出现一堆错误位置
        //第一个错误位置在第一对的第一个
        //第二个错误位置在第二对的第二个
        //用一个栈中序遍历
        Stack<TreeNode> stack = new Stack<>();
        //一路向左下走
        TreeNode cur = root;
        TreeNode pre = null;
        TreeNode[][] faultPair = new TreeNode[2][2];//最多两对
        int w = 0;
        while (!stack.isEmpty() || cur != null) {
            if (cur != null) {
                stack.push(cur);
                cur = cur.left;
            } else {
                cur = stack.pop();//处理一个
                if (pre != null && cur.val < pre.val) {
                    faultPair[w++] = new TreeNode[]{pre, cur};
                }
                pre = cur;
                cur = cur.right;
            }
        }
        if (faultPair[1] == null) {
            int temp = faultPair[0][0].val;
            faultPair[0][0].val = faultPair[0][1].val;
            faultPair[0][1].val = temp;
        } else {
            int temp = faultPair[0][0].val;
            faultPair[0][0].val = faultPair[1][1].val;
            faultPair[1][1].val = temp;
        }
    }

    //采用调结构的方式将树recover
    public static TreeNode recoverTree1(TreeNode root) {
        //1. e1                e2
        //      e2          e1
        TreeNode[] faliures = getFaliures(root);//{e1,e2}
        if (faliures[0] == null) return root;//已经是BST
        TreeNode e1 = faliures[0];
        TreeNode e2 = faliures[1];
        TreeNode[] faliureFathers = getFaliureFather(root, e1, e2);//{e1.father,e2.father}
        //1.可能为头
        TreeNode e2F = faliureFathers[1];
        TreeNode e1F = faliureFathers[0];
        if (e1 == root) {//3种
            //e1,e2挨着
            if (e2 == e1.right) {
                changeRightCloseEnv(e1, e2);
            } else if (e2 == e2F.left) {//不挨着
                e2F.left = e1;
                changeTwoNoCloseEnv(e1, e2);
            } else {
                e2F.right = e1;
                changeTwoNoCloseEnv(e1, e2);
            }
            root = e2;
        } else if (e2 == root) {//3种
            //e1,e2挨着
            if (e1 == e2.left) {
                changeLeftCloseEnv(e1, e2);
            } else if (e1 == e1F.left) {//不挨着
                e1F.left = e2;
                changeTwoNoCloseEnv(e1, e2);
            } else {
                e1F.right = e2;
                changeTwoNoCloseEnv(e1, e2);
            }
            root = e1;
        } else {//8种
            //挨着
            if (e1.right == e2) {
                if (e1 == e1F.left) {
                    e1F.left = e2;
                } else {
                    e1F.right = e2;
                }
                changeRightCloseEnv(e1, e2);
            } else if (e2.left == e1) {
                if (e2 == e2F.left) {
                    e2F.left = e1;
                } else {
                    e2F.right = e1;
                }
                changeLeftCloseEnv(e1, e2);
            } else {
                //不挨着
                if (e1 == e1F.left) {
                    e1F.left = e2;
                } else {
                    e1F.right = e2;
                }
                if (e2 == e2F.left) {
                    e2F.left = e1;
                } else {
                    e2F.right = e1;
                }
                changeTwoNoCloseEnv(e1, e2);
            }

        }
        return root;
    }

    public static TreeNode recoverTreeAwesome(TreeNode root) {
        //1. e1                e2
        //      e2          e1
        TreeNode[] faliures = getFaliures(root);//{e1,e2}
        if (faliures[0] == null) return root;//已经是BST
        TreeNode e1 = faliures[0];
        TreeNode e2 = faliures[1];
        //使用哑节点
        TreeNode res = new TreeNode(0);
        res.left = root;
        TreeNode[] faliureFathers = getFaliureFather(res, e1, e2);//{e1.father,e2.father}
        TreeNode e2F = faliureFathers[1];
        TreeNode e1F = faliureFathers[0];
        //挨着
        if (e1.right == e2) {
            if (e1 == e1F.left) {
                e1F.left = e2;
            } else {
                e1F.right = e2;
            }
            changeRightCloseEnv(e1, e2);
        } else if (e2.left == e1) {
            if (e2 == e2F.left) {
                e2F.left = e1;
            } else {
                e2F.right = e1;
            }
            changeLeftCloseEnv(e1, e2);
        } else {
            //不挨着
            if (e1 == e1F.left) {
                e1F.left = e2;
            } else {
                e1F.right = e2;
            }
            if (e2 == e2F.left) {
                e2F.left = e1;
            } else {
                e2F.right = e1;
            }
            changeTwoNoCloseEnv(e1, e2);
        }
        return res.left;
    }

    private static TreeNode[] getFaliureFather(TreeNode cur, TreeNode e1, TreeNode e2) {
        TreeNode[] parents = new TreeNode[2];
        if (cur == null) {
            return parents;
        }
        Stack<TreeNode> stack = new Stack<TreeNode>();
        while (!stack.isEmpty() || cur != null) {
            if (cur != null) {
                stack.push(cur);
                cur = cur.left;
            } else {
                cur = stack.pop();
                if (cur.left == e1 || cur.right == e1) {
                    parents[0] = cur;
                }
                if (cur.left == e2 || cur.right == e2) {
                    parents[1] = cur;
                }
                cur = cur.right;
            }
        }
        return parents;
    }

    private static TreeNode[] getFaliures(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        TreeNode pre = null;
        TreeNode[][] faliurePair = new TreeNode[2][2];//最多两对
        int w = 0;
        while (!stack.isEmpty() || cur != null) {
            if (cur != null) {
                stack.push(cur);
                cur = cur.left;
            } else {
                cur = stack.pop();//处理一个
                if (pre != null && cur.val < pre.val) {
                    faliurePair[w++] = new TreeNode[]{pre, cur};
                }
                pre = cur;
                cur = cur.right;
            }
        }
        if (faliurePair[0][0] == null) {
            return new TreeNode[2];
        }
        if (faliurePair[1][0] == null) {
            return new TreeNode[]{faliurePair[0][0], faliurePair[0][1]};
        } else {
            return new TreeNode[]{faliurePair[0][0], faliurePair[1][1]};
        }
    }

    private static void changeRightCloseEnv(TreeNode e1, TreeNode e2) {
        TreeNode temp;
        temp = e1.left;
        e1.left = e2.left;
        e2.left = temp;
        e1.right = e2.right;
        e2.right = e1;
    }

    private static void changeLeftCloseEnv(TreeNode e1, TreeNode e2) {
        TreeNode temp;
        temp = e2.right;
        e2.right = e1.right;
        e1.right = temp;
        e2.left = e1.left;
        e1.left = e2;
    }

    private static void changeTwoNoCloseEnv(TreeNode e1, TreeNode e2) {
        TreeNode temp;
        temp = e1.left;
        e1.left = e2.left;
        e2.left = temp;
        temp = e1.right;
        e1.right = e2.right;
        e2.right = temp;
    }

    /*public static void main(String[] args) {
     *//*TreeNode treeNode = new TreeNode(3);
        treeNode.left = new TreeNode(1);
        treeNode.right = new TreeNode(4);
        treeNode.right.left = new TreeNode(2);
        recoverTree(treeNode);*//*
        TreeNode[][] treeNodes = new TreeNode[2][2];
        System.out.println(treeNodes[1] == null);
        System.out.println(treeNodes[1][0] == null);
        //数组一旦new出来就已经不是null了，有初始值,数组空间开辟好了，如果是对象，里面默认是null
    }*/
    // for test -- print tree
    public static void printTree(TreeNode head) {
        System.out.println("Binary Tree:");
        printInOrder(head, 0, "H", 17);
        System.out.println();
    }

    public static void printInOrder(TreeNode head, int height, String to, int len) {
        if (head == null) {
            return;
        }
        printInOrder(head.right, height + 1, "v", len);
        String val = to + head.val + to;
        int lenM = val.length();
        int lenL = (len - lenM) / 2;
        int lenR = len - lenM - lenL;
        val = getSpace(lenL) + val + getSpace(lenR);
        System.out.println(getSpace(height * len) + val);
        printInOrder(head.left, height + 1, "^", len);
    }

    public static String getSpace(int num) {
        String space = " ";
        StringBuffer buf = new StringBuffer("");
        for (int i = 0; i < num; i++) {
            buf.append(space);
        }
        return buf.toString();
    }

    // for test
    public static boolean isBST(TreeNode head) {
        if (head == null) {
            return false;
        }
        Stack<TreeNode> stack = new Stack<TreeNode>();
        TreeNode pre = null;
        while (!stack.isEmpty() || head != null) {
            if (head != null) {
                stack.push(head);
                head = head.left;
            } else {
                head = stack.pop();
                if (pre != null && pre.val > head.val) {
                    return false;
                }
                pre = head;
                head = head.right;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        TreeNode head = new TreeNode(5);
        head.left = new TreeNode(3);
        head.right = new TreeNode(7);
        head.left.left = new TreeNode(2);
        head.left.right = new TreeNode(4);
        head.right.left = new TreeNode(6);
        head.right.right = new TreeNode(8);
        head.left.left.left = new TreeNode(1);
        printTree(head);
        System.out.println("origin" + isBST(head));

        // ���1, 7 -> e1, 5 -> e2
        System.out.println("situation 1");
        TreeNode head1 = new TreeNode(7);
        head1.left = new TreeNode(3);
        head1.right = new TreeNode(5);
        head1.left.left = new TreeNode(2);
        head1.left.right = new TreeNode(4);
        head1.right.left = new TreeNode(6);
        head1.right.right = new TreeNode(8);
        head1.left.left.left = new TreeNode(1);
        printTree(head1);
        System.out.println("origin" + isBST(head1));
        TreeNode res1 = recoverTreeAwesome(head1);
        printTree(res1);
        System.out.println("recover" + isBST(res1));

        // ���2, 6 -> e1, 5 -> e2
        System.out.println("situation 2");
        TreeNode head2 = new TreeNode(6);
        head2.left = new TreeNode(3);
        head2.right = new TreeNode(7);
        head2.left.left = new TreeNode(2);
        head2.left.right = new TreeNode(4);
        head2.right.left = new TreeNode(5);
        head2.right.right = new TreeNode(8);
        head2.left.left.left = new TreeNode(1);
        printTree(head2);
        System.out.println("origin" + isBST(head2));
        TreeNode res2 = recoverTreeAwesome(head2);
        printTree(res2);
        System.out.println("recover" + isBST(res2));

        // ���3, 8 -> e1, 5 -> e2
        System.out.println("situation 3");
        TreeNode head3 = new TreeNode(8);
        head3.left = new TreeNode(3);
        head3.right = new TreeNode(7);
        head3.left.left = new TreeNode(2);
        head3.left.right = new TreeNode(4);
        head3.right.left = new TreeNode(6);
        head3.right.right = new TreeNode(5);
        head3.left.left.left = new TreeNode(1);
        printTree(head3);
        System.out.println("origin" + isBST(head3));
        TreeNode res3 = recoverTreeAwesome(head3);
        printTree(res3);
        System.out.println("recover" + isBST(res3));

        // ���4, 5 -> e1, 3 -> e2
        System.out.println("situation 4");
        TreeNode head4 = new TreeNode(3);
        head4.left = new TreeNode(5);
        head4.right = new TreeNode(7);
        head4.left.left = new TreeNode(2);
        head4.left.right = new TreeNode(4);
        head4.right.left = new TreeNode(6);
        head4.right.right = new TreeNode(8);
        head4.left.left.left = new TreeNode(1);
        printTree(head4);
        System.out.println("origin" + isBST(head4));
        TreeNode res4 = recoverTreeAwesome(head4);
        printTree(res4);
        System.out.println("recover" + isBST(res4));

        // ���5, 5 -> e1, 2 -> e2
        System.out.println("situation 5");
        TreeNode head5 = new TreeNode(2);
        head5.left = new TreeNode(3);
        head5.right = new TreeNode(7);
        head5.left.left = new TreeNode(5);
        head5.left.right = new TreeNode(4);
        head5.right.left = new TreeNode(6);
        head5.right.right = new TreeNode(8);
        head5.left.left.left = new TreeNode(1);
        printTree(head5);
        System.out.println("origin" + isBST(head5));
        TreeNode res5 = recoverTreeAwesome(head5);
        printTree(res5);
        System.out.println("recover" + isBST(res5));

        // ���6, 5 -> e1, 4 -> e2
        System.out.println("situation 6");
        TreeNode head6 = new TreeNode(4);
        head6.left = new TreeNode(3);
        head6.right = new TreeNode(7);
        head6.left.left = new TreeNode(2);
        head6.left.right = new TreeNode(5);
        head6.right.left = new TreeNode(6);
        head6.right.right = new TreeNode(8);
        head6.left.left.left = new TreeNode(1);
        printTree(head6);
        System.out.println("origin" + isBST(head6));
        TreeNode res6 = recoverTreeAwesome(head6);
        printTree(res6);
        System.out.println("recover" + isBST(res6));

        // ���7, 4 -> e1, 3 -> e2
        System.out.println("situation 7");
        TreeNode head7 = new TreeNode(5);
        head7.left = new TreeNode(4);
        head7.right = new TreeNode(7);
        head7.left.left = new TreeNode(2);
        head7.left.right = new TreeNode(3);
        head7.right.left = new TreeNode(6);
        head7.right.right = new TreeNode(8);
        head7.left.left.left = new TreeNode(1);
        printTree(head7);
        System.out.println("origin" + isBST(head7));
        TreeNode res7 = recoverTreeAwesome(head7);
        printTree(res7);
        System.out.println("recover" + isBST(res7));

        // ���8, 8 -> e1, 7 -> e2
        System.out.println("situation 8");
        TreeNode head8 = new TreeNode(5);
        head8.left = new TreeNode(3);
        head8.right = new TreeNode(8);
        head8.left.left = new TreeNode(2);
        head8.left.right = new TreeNode(4);
        head8.right.left = new TreeNode(6);
        head8.right.right = new TreeNode(7);
        head8.left.left.left = new TreeNode(1);
        printTree(head8);
        System.out.println("origin" + isBST(head8));
        TreeNode res8 = recoverTreeAwesome(head8);
        printTree(res8);
        System.out.println("recover" + isBST(res8));

        // ���9, 3 -> e1, 2 -> e2
        System.out.println("situation 9");
        TreeNode head9 = new TreeNode(5);
        head9.left = new TreeNode(2);
        head9.right = new TreeNode(7);
        head9.left.left = new TreeNode(3);
        head9.left.right = new TreeNode(4);
        head9.right.left = new TreeNode(6);
        head9.right.right = new TreeNode(8);
        head9.left.left.left = new TreeNode(1);
        printTree(head9);
        System.out.println("origin" + isBST(head9));
        TreeNode res9 = recoverTreeAwesome(head9);
        printTree(res9);
        System.out.println("recover" + isBST(res9));

        // ���10, 7 -> e1, 6 -> e2
        System.out.println("situation 10");
        TreeNode head10 = new TreeNode(5);
        head10.left = new TreeNode(3);
        head10.right = new TreeNode(6);
        head10.left.left = new TreeNode(2);
        head10.left.right = new TreeNode(4);
        head10.right.left = new TreeNode(7);
        head10.right.right = new TreeNode(8);
        head10.left.left.left = new TreeNode(1);
        printTree(head10);
        System.out.println("origin" + isBST(head10));
        TreeNode res10 = recoverTreeAwesome(head10);
        printTree(res10);
        System.out.println("recover" + isBST(res10));

        // ���11, 6 -> e1, 2 -> e2
        System.out.println("situation 11");
        TreeNode head11 = new TreeNode(5);
        head11.left = new TreeNode(3);
        head11.right = new TreeNode(7);
        head11.left.left = new TreeNode(6);
        head11.left.right = new TreeNode(4);
        head11.right.left = new TreeNode(2);
        head11.right.right = new TreeNode(8);
        head11.left.left.left = new TreeNode(1);
        printTree(head11);
        System.out.println("origin" + isBST(head11));
        TreeNode res11 = recoverTreeAwesome(head11);
        printTree(res11);
        System.out.println("recover" + isBST(res11));

        // ���12, 8 -> e1, 2 -> e2
        System.out.println("situation 12");
        TreeNode head12 = new TreeNode(5);
        head12.left = new TreeNode(3);
        head12.right = new TreeNode(7);
        head12.left.left = new TreeNode(8);
        head12.left.right = new TreeNode(4);
        head12.right.left = new TreeNode(6);
        head12.right.right = new TreeNode(2);
        head12.left.left.left = new TreeNode(1);
        printTree(head12);
        System.out.println("origin" + isBST(head12));
        TreeNode res12 = recoverTreeAwesome(head12);
        printTree(res12);
        System.out.println("recover" + isBST(res12));

        // ���13, 6 -> e1, 4 -> e2
        System.out.println("situation 13");
        TreeNode head13 = new TreeNode(5);
        head13.left = new TreeNode(3);
        head13.right = new TreeNode(7);
        head13.left.left = new TreeNode(2);
        head13.left.right = new TreeNode(6);
        head13.right.left = new TreeNode(4);
        head13.right.right = new TreeNode(8);
        head13.left.left.left = new TreeNode(1);
        printTree(head13);
        System.out.println("origin" + isBST(head13));
        TreeNode res13 = recoverTreeAwesome(head13);
        printTree(res13);
        System.out.println("recover" + isBST(res13));

        // ���14, 8 -> e1, 4 -> e2
        System.out.println("situation 14");
        TreeNode head14 = new TreeNode(5);
        head14.left = new TreeNode(3);
        head14.right = new TreeNode(7);
        head14.left.left = new TreeNode(2);
        head14.left.right = new TreeNode(8);
        head14.right.left = new TreeNode(6);
        head14.right.right = new TreeNode(4);
        head14.left.left.left = new TreeNode(1);
        printTree(head14);
        System.out.println("origin" + isBST(head14));
        TreeNode res14 = recoverTreeAwesome(head14);
        printTree(res14);
        System.out.println("recover" + isBST(res14));

    }
}
