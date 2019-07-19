package com.example.demo.locks;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Filename: ReentrantLockTest
 * @Description:
 * @Version: 1.0
 * @Author: wangchao
 * @Email: wangchao@hellogeek.com
 * @date: 2019/7/11 ;
 */
public class ReentrantLockTest implements  Runnable{
    private static ReentrantLock lock=new ReentrantLock();

    public  static   int numb;

    @Override
    public void run() {

        lock.lock();
        lock.lock();
        try {
            for (int i = 0; i < 1000000; i++) {
                numb++;
            }
        } finally {
            lock.unlock();
            lock.unlock();
        }

    }


    public static void main(String[] args) {

        ReentrantLockTest reentrantLockTest = new ReentrantLockTest();
        Thread t1 = new Thread(reentrantLockTest);
        Thread t2 = new Thread(reentrantLockTest);

        t1.start();t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(numb);
    }
}
