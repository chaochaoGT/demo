package com.example.demo;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Filename: AtomicIntegerTest
 * @Description:
 * @Version: 1.0
 * @Author: wangchao
 * @Email: wangchao@hellogeek.com
 * @date: 2019/7/2 ;
 */
public class AtomicIntegerTest {

    private static AtomicInteger numb=new AtomicInteger();


    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new AtomThread().run();
        }
        System.out.println(numb);

    }
    public static class AtomThread implements Runnable{

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                numb.getAndIncrement();
            }

            System.out.println(Thread.currentThread().getName()+"==="+numb);
        }
    }
}
