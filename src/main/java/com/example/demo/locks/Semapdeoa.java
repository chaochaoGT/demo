package com.example.demo.locks;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @Filename: Semapdeoa
 * @Description:
 * @Version: 1.0
 * @Author: wangchao
 * @Email: wangchao@hellogeek.com
 * @date: 2019/7/15 ;
 */
public class Semapdeoa implements Runnable {

    private Semaphore semaphore=new Semaphore(2);



    @Override
    public void run() {
        try {
            semaphore.acquire();
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName()+"semap .....");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            semaphore.release();
        }
    }


    public static void main(String[] args) {
        Semapdeoa semapdeoa=new Semapdeoa();
        ExecutorService exec= Executors.newFixedThreadPool(20);
        for (int i = 0; i < 20; i++) {
            exec.submit(semapdeoa);

        }
            exec.shutdown();
    }
}
