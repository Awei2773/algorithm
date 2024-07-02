package com.waigo.nowcoder;

import java.net.URLClassLoader;
import java.util.concurrent.TimeUnit;

/**
 * author waigo
 * create 2021-08-04 19:42
 */
public class Test2 {
    /**
     * 3.有一个API网关，出于对API接口的保护，需要建立一个流控功能，
     * 根据API名称，每分钟最多只能请求指定的次数（如1000次），
     * 超过限制则这分钟内返回错误，但下一分钟又可以正常请求。
     */
//    public static void main(String[] args) {
        /*long l = System.currentTimeMillis();
        long r = l+60000;
        System.out.println(l);
        System.out.println(r);
        new Integer(3);*/

//    }

}
class Base
{
    private String baseName = "base";
    public Base()
    {
        callName();
    }

    public void callName()
    {
        System. out. println(baseName);
    }

    static class Sub extends Base
    {
        private String baseName = "sub";
        public void callName()
        {
            System. out. println (baseName) ;
        }
    }
    public static void main(String[] args)
    {
        Base b = new Sub();
    }
}
class MyClassLoader extends ClassLoader{

}

