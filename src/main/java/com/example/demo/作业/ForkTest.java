package com.example.demo.作业;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Filename: ForkTest
 * @Description:
 * @Version: 1.0
 * @Author: wangchao
 * @Email: wangchao@hellogeek.com
 * @date: 2019/7/25 ;
 */
public class ForkTest {

    public static class CountAaremd extends RecursiveTask<Float> {
        private static final long serialVersionUID = -8437583391117009271L;
        private static float THRESHOLD = 0.01f;
        private static int ONE_SIZE = 10;
        private static AtomicInteger atomicInteger = new AtomicInteger(0);

        private float start=0.0f;
        private float end=0.0f;

        public CountAaremd(float start, float end) {
            this.start = start;
            this.end = end;
        }


        @Override
        protected Float compute() {
            float result = 0.0f;
            boolean v = (end - start) <= THRESHOLD;
            if (v) {
                result = sumArea(start, end);
                atomicInteger.incrementAndGet();
                System.out.println(atomicInteger + "--> calc result between " + (start  ) + " -- " + (end));
            }

            else {
                float slipt = (end - start) / ONE_SIZE;


                for (int i = 0; i < ONE_SIZE; i++) {

                    CountAaremd countAaremd = new CountAaremd(start + i * slipt, start + (i+1) * slipt);

                    countAaremd.fork();
                    result+=countAaremd.join();
                }


            }


            return result;
        }

        private float sumArea(float startXLoc, float endXLoc) {
            float rectArea = 1 / (endXLoc) * THRESHOLD;
            float trigleArea = 0.5f * (1 / startXLoc - 1 / endXLoc) * 0.01f;
            return rectArea + trigleArea;
        }
    }
    public static void main(String[] args) {

        CountAaremd nodeA1 = new CountAaremd(1, 100);
        ForkJoinPool pool = new ForkJoinPool();

        Future<Float> result = pool.submit(nodeA1);
        long startPoint = System.currentTimeMillis();
        try {
            System.out.println("Final Area:" + result.get());
            System.out.println("The whole process consumes " + (System.currentTimeMillis() - startPoint) + " ms");
        } catch (InterruptedException | ExecutionException e) {
            //  Auto-generated catch block
            e.printStackTrace();
        }
    }
}
