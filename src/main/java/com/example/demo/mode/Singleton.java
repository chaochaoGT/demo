package com.example.demo.mode;

/**
 * @Filename: Singleton
 * @Description:终极单例模式
 * @Version: 1.0
 * @Author: wangchao
 * @Email: wangchao@hellogeek.com
 * @date: 2019/7/23 ;
 */
public class Singleton {
    private Singleton() {
        System.out.println("单例模式");
    }

    private static class GetSingleton {
        private static Singleton instance = new Singleton();
    }

    public static Singleton getInstance() {
        return GetSingleton.instance;
    }
}

/**
 * 处理并发模式下的单例 ， 但加同步锁后性能降低
 * 懒加载单例模式
 */
class SycSingleton {
    private SycSingleton() {
        System.out.println("单例模式");
    }

    private static SycSingleton instance = null;

    public static synchronized SycSingleton getInstance() {
        if (null==instance){
            instance=new SycSingleton();
        }
        return instance;
    }
}


/**
 * 并发情况下，可能会创建多个对象
 */
class OpenSingleton {
    private OpenSingleton() {
        System.out.println("单例模式");
    }

    private static OpenSingleton instance = new OpenSingleton();

    public static  OpenSingleton getInstance() {
        return instance;
    }
}