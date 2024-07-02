package waigo.zuosheng.advanced.MyCode;

/**
 * author waigo
 * create 2021-05-31 11:34
 */

import java.util.HashMap;
import java.util.Map;

/**
 * 一种消息接收并打印的结构设计
 * 已知一个消息流会不断地吐出整数 1~N，但不一定按照顺序吐出。如果上次打印的数为 i，
 * 那么当 i+1 出现时，请打印 i+1 及其之后接收过的并且连续的所有数，直到 1~N 全部接收 并打印完，
 * 请设计这种接收并打印的结构。
 * 初始时默认i==0
 *
 * 我的思路:
 * 既然i+1及其之后接收过连续的所有数，那么i+1肯定是连成的一条链，由于1~N是随机出现的，
 * 所以一开始无法确定i+1出来的时候会串多长，所以用链表来存i+1连出来的数。
 * 当前吐出来的数是j，如果j-1还没有出现过，那还得等着，所以一条链得记住它的开头和结尾都是啥。
 * 这样j一出来就查一下j-1作为结尾出现过没有，有就和他串起来，去掉j-1这个尾。看一下有没有j+1开头的，
 * 有就连起来，去掉j+1这个开头。
 *
 * 实现思路：
 * 1.将消息封装成Node，里面存着消息的序号和消息体，之后要用这个Node串链表
 * 2.整两个map，一个存消息链条的头，一个存消息链条的尾，headMap< seq,Node>，
 * 3.肯定需要存一下每个时间点在等的是啥消息，不然咋知道啥时候进行打印
 */
public class Code01_ReceiveAndPrintOrderLine {
    public static class Node{
        int seq;
        String info;
        Node next;

        @Override
        public String toString() {
            return "Node{" + "seq=" + seq + ", info='" + info + '\'' + '}';
        }

        public Node(int seq, String info) {
            this.seq = seq;
            this.info = info;
        }
    }
    public static class MessageBox{
        Map<Integer,Node> headMap = new HashMap<>();
        Map<Integer,Node> tailMap = new HashMap<>();
        int wishSeq = 1;

        public void receiveMsg(int seq,String info){
            if(seq<wishSeq) return;//之前打印过这个消息了，不能用这个序号了
            Node msg = new Node(seq,info);
            //如果seq==wishSeq，那么它之前的消息必然打印完毕，就只用看尾巴了，所以我们先看尾巴
            //所谓的尾巴是指能够接在当前这个消息后面的，所以查头表
            boolean getNewTail = false;
            if(headMap.containsKey(seq+1)){
                msg.next = headMap.get(seq+1);
                //连上了，那个之前的头可以删掉了
                headMap.remove(seq+1);
                getNewTail = true;
                //但是我们这个消息先不存为头，看看它是不是可以打印的那个数，不然进了待会还得删
            }
            if(wishSeq==seq){
                printMsg(msg);
                //直接return，这个消息连带着它的那条链都莫用了
                return;
            }
            //不是的话就得看看是不是能够做别人的尾巴了
            if(tailMap.containsKey(seq-1)){
                tailMap.get(seq-1).next=msg;
                tailMap.remove(seq-1);//将之前的尾巴断掉，现在这个做尾巴
                if(!getNewTail) tailMap.put(seq,msg);
                //也不用存头了，头肯定是人家这个原来尾巴的头，所以直接返回
                return;
            }
            //到了这里说明这个消息可能只有自己，也可能还有后面的
            headMap.put(seq,msg);
            if(!getNewTail) tailMap.put(seq,msg);
        }
        public void receiveMsgAwesome(int seq,String info){
            if(seq<wishSeq) return;
            //1.二话不说，先把这个当做孤儿自己先成条链
            Node msg = new Node(seq,info);
            headMap.put(seq,msg);
            tailMap.put(seq,msg);
            //连头
            if(tailMap.containsKey(seq-1)){
                tailMap.get(seq-1).next = msg;
                tailMap.remove(seq-1);
                headMap.remove(seq);
                //这个结束之后，头成了连上的链的头，尾变成seq
            }
            //连尾
            if(headMap.containsKey(seq+1)){
                msg.next = headMap.get(seq+1);
                tailMap.remove(seq);//不用自己做尾巴了，尾巴是连上的链条的尾巴
                headMap.remove(seq+1);//旧链不用留头了
            }
            if(seq==wishSeq)
                printMsg(msg);
        }
        //遍历打印整个链条，记得把尾巴从尾表中删去
        private void printMsg(Node msg) {
            while(msg!=null){
                System.out.println(msg);
                if(msg.next==null){
                    //来到尾巴了，去掉尾巴，现在该等尾巴后那个数了
                    tailMap.remove(msg.seq);
                    wishSeq = msg.seq+1;
                }
                msg = msg.next;
            }
        }

        public static void main(String[] args) {
            // MessageBox only receive 1~N
            MessageBox box = new MessageBox();

            box.receiveMsgAwesome(2,"B"); // - 2"
            box.receiveMsgAwesome(1,"A"); // 1 2 -> print, trigger is 1

            box.receiveMsgAwesome(4,"D"); // - 4
            box.receiveMsgAwesome(5,"E"); // - 4 5
            box.receiveMsgAwesome(7,"G"); // - 4 5 - 7
            box.receiveMsgAwesome(8,"H"); // - 4 5 - 7 8
            box.receiveMsgAwesome(6,"F"); // - 4 5 6 7 8
            box.receiveMsgAwesome(3,"C"); // 3 4 5 6 7 8 -> print, trigger is 3

            box.receiveMsgAwesome(9,"I"); // 9 -> print, trigger is 9

            box.receiveMsgAwesome(10,"J"); // 10 -> print, trigger is 10

            box.receiveMsgAwesome(12,"L"); // - 12
            box.receiveMsgAwesome(13,"M"); // - 12 13
            box.receiveMsgAwesome(11,"K"); // 11 12 13 -> print, trigger is 11

            box.receiveMsgAwesome(18,"P");
            box.receiveMsgAwesome(17,"Q");
            box.receiveMsgAwesome(16,"E");
            box.receiveMsgAwesome(15,"I");
            box.receiveMsgAwesome(14,"O");
            box.receiveMsgAwesome(19,"R");
            box.receiveMsgAwesome(21,"B");
            box.receiveMsgAwesome(20,"N");
            box.receiveMsgAwesome(23,"X");
            box.receiveMsgAwesome(22,"C");
        }
    }
}
