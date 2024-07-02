package waigo.zuoshen.basic.MyTest;


import org.junit.Test;
import waigo.zuoshen.basic.MyCode.Code01_ReverseList;
import waigo.zuoshen.basic.MyCode.utils.LoggerUtil;

import java.util.LinkedList;

/**
 * author waigo
 * create 2021-02-09 21:09
 */
public class TestCode01_ReverseList {
    @Test
    public void testSimpleList(){
        Code01_ReverseList.SimpleLinkedList<Integer> linkedList = new Code01_ReverseList().new SimpleLinkedList<>();
        linkedList.add(13);
        linkedList.add(13);
        linkedList.add(1);
        linkedList.add(13);
        linkedList.add(2);
        linkedList.add(13);
        linkedList.printLinkedList();
        LoggerUtil.log(linkedList.size()+"");
        linkedList.reverseLinkedList();
        linkedList.printLinkedList();
        linkedList.removeAll(13);
        linkedList.printLinkedList();
    }
    @Test
    public void testDoubleLinkedList(){
        Code01_ReverseList.DoubleLinkedList<Integer> linkedList = new Code01_ReverseList().new DoubleLinkedList<>();
        linkedList.add(13);
        linkedList.add(13);
        linkedList.add(1);
        linkedList.add(13);
        linkedList.add(2);
        linkedList.add(13);
        linkedList.printLinkedList();
        linkedList.reverseLinkedList();
        linkedList.printLinkedList();
        linkedList.removeAll(13);
        linkedList.printLinkedList();
    }
}
