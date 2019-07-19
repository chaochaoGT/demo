package com.example.demo.locks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Filename: TimerLockTest
 * @Description:
 * @Version: 1.0
 * @Author: wangchao
 * @Email: wangchao@hellogeek.com
 * @date: 2019/7/11 ;
 */
public class TimerLockTest implements Runnable {

    private ReentrantLock lock=new ReentrantLock();


    @Override
    public void run() {

        try {
            if (lock.tryLock(5, TimeUnit.MILLISECONDS)){
                lock.lockInterruptibly();
                System.out.println(Thread.currentThread().getName()+"get  lock !!!");
            }
        } catch (InterruptedException e) {
                e.printStackTrace();
        } finally {
            if (lock.isHeldByCurrentThread()){
                System.out.println(Thread.currentThread().getName()+"gave  lock !!!");
                lock.unlock();
            }
        }
    }


    public static void main(String[] args) {
        TimerLockTest lockTest=new TimerLockTest();
        Thread t1 = new Thread(lockTest);
        Thread t2 = new Thread(lockTest);

        t1.start();t2.start();
        try {
            t2.join();
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
