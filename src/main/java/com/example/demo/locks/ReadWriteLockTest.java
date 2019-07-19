package com.example.demo.locks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Filename: ReadWriteLockTest
 * @Description:
 * @Version: 1.0
 * @Author: wangchao
 * @Email: wangchao@hellogeek.com
 * @date: 2019/7/15 ;
 */
public class ReadWriteLockTest implements Runnable {


    private static ReentrantReadWriteLock readWriteLock=new ReentrantReadWriteLock();
    private static Lock readLock=readWriteLock.readLock();
    private  static Lock writeLock=readWriteLock.writeLock();



    @Override
    public void run() {
//        readLock
        for (;;){
            System.out.println(" for (;;) ");
        }
    }


    public static void main(String[] args) {
        ReadWriteLockTest t = new ReadWriteLockTest();
        t.run();
    }

}
