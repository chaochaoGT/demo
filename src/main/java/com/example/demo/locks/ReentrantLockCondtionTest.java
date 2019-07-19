package com.example.demo.locks;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Filename: ReentrantLockTest
 * @Description:
 * @Version: 1.0
 * @Author: wangchao
 * @Email: wangchao@hellogeek.com
 * @date: 2019/7/11 ;
 */
public class ReentrantLockCondtionTest implements  Runnable{
    private static ReentrantLock lock=new ReentrantLock();
    private static Condition condition=lock.newCondition();
    public  static   int numb;

    @Override
    public void run() {

        try {
//                if(lock.tryLock(5, TimeUnit.SECONDS)){
//                    System.out.println(Thread.currentThread().getName()+"waiting !!!");
                    lock.lock();
                    condition.await();
                    System.out.println(Thread.currentThread().getName()+"runing !!!");
//                }else {
//                    System.out.println("get lock fail");
//                }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }


    public static void main(String[] args) {

        ReentrantLockCondtionTest reentrantLockTest = new ReentrantLockCondtionTest();
        Thread t1 = new Thread(reentrantLockTest);
//        Thread t2 = new Thread(reentrantLockTest);

        t1.start();
//        t2.start();
        try {
            Thread.sleep(2200);
            lock.lock();
//            t1.join();
            condition.signal();
            lock.unlock();
//            t2.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(numb);

    }
}
