package com.waigo.nowcoder.MyCode;

/**
 * author waigo
 * create 2021-09-08 16:16
 */
public class DCLTest {

}
class SingletonObject{
    private SingletonObject(int age){
        this.age = age;
    }
    public int age;
    public volatile static SingletonObject INSTANCE;
    public static SingletonObject getInstance(){
        if(INSTANCE==null){
            synchronized (SingletonObject.class){
                if(INSTANCE==null){
                    INSTANCE = new SingletonObject(3);
                    //A线程，负责创建对象
                    //1.开辟内存空间，属性默认值
                    //2.属性初始化
                    //3.返回句柄
                }
            }
        }
        return INSTANCE;
    }
}
