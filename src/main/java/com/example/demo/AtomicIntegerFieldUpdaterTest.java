package com.example.demo;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @Filename: AtomicIntegerFieldUpdaterTest
 * @Description:
 * @Version: 1.0
 * @Author: wangchao
 * @Email: wangchao@hellogeek.com
 * @date: 2019/7/5 ;
 */
public class AtomicIntegerFieldUpdaterTest {

    public static class Saffer {
        String name;
        volatile int score;

    }


    private final static AtomicIntegerFieldUpdater<Saffer> updaterSaffer =
            AtomicIntegerFieldUpdater.newUpdater(Saffer.class, "score");
    private static AtomicInteger numb = new AtomicInteger(0);

    public static void main(String[] args) {

       final Saffer saffer = new Saffer();
        Thread[] threads = new Thread[10000];
        for (int i = 0; i < 10000; i++) {
            threads[i] = new Thread(() -> {

                if (Math.random() > 0.4) {
                    updaterSaffer.incrementAndGet(saffer);
                    numb.incrementAndGet();
                }


            });
            threads[i].start();
        }

        try {
            for (int i = 0; i < 10000; i++) {
                threads[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(saffer.score);
        System.out.println(numb);

    }

}
