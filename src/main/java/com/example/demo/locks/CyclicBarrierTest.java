package com.example.demo.locks;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @Filename: CyclicBarrierTest
 * @Description:
 * @Version: 1.0
 * @Author: wangchao
 * @Email: wangchao@hellogeek.com
 * @date: 2019/7/16 ;
 */
public class CyclicBarrierTest {
    public static class Solder implements Runnable{
        private  String solder;
        private CyclicBarrier cyclicBarrier;

        public Solder(String solder,CyclicBarrier cyclicBarrier) {
            this.solder=solder;
            this.cyclicBarrier=cyclicBarrier;
        }

        @Override
        public void run() {
            try {
                cyclicBarrier.await();
                dowork();
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }

        private void dowork() {
            System.out.println(Thread.currentThread().getName()+" 正在执行任务。。。");
        }
    }


    public static class Barrier implements Runnable{
        private int N;
        private  boolean falg;

        public Barrier(int N,boolean falg) {
        this.falg=falg;
        this.N=N;
        }

        @Override
        public void run() {
            if (falg) {
                System.out.println("头：\n " + "\t第" + N + "个士兵执行完毕");
            } else {
                System.out.println("头：\n " + "\t第" + N + "个士兵集合完毕");
                falg=true;
            }
        }
    }

    public static void main(String[] args) {
        int N=10;
        boolean falg=false;
        Thread[] threads=new Thread[N];
        CyclicBarrier cyclicBarrier= new CyclicBarrier(N,new Barrier(N,falg));


        for (int i = 0; i < N; i++) {
            Solder solder=new Solder("士兵"+i,cyclicBarrier);
            threads[i]=new Thread(solder,"士兵"+i);
            threads[i].start();
//            if (i==5){
//                threads[i].interrupt();
//            }
        }



    }
}
