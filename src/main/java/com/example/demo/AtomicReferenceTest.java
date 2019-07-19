package com.example.demo;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @Filename: AtomicReferenceTest
 * @Description:
 * @Version: 1.0
 * @Author: wangchao
 * @Email: wangchao@hellogeek.com
 * @date: 2019/7/3 ;
 */
public class AtomicReferenceTest {

    private static AtomicReference<String> atomicReference=new AtomicReference<String>("aaa");


    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(){
                @Override
                public void run() {

                    try {
                        Thread.sleep(100L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (atomicReference.compareAndSet("aaa","bbb")){
                        System.out.println(Thread.currentThread().getName()+"===="+atomicReference.get()+"成功啦");
                    }else {
                        System.out.println(Thread.currentThread().getName()+"===="+atomicReference.get()+"Fail");
                    }

                }
            }.start();
        }
    }
}
