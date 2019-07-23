package com.example.demo.mode.future.call;

import java.util.concurrent.*;

/**
 * @Filename: FutureCallTest
 * @Description:
 * @Version: 1.0
 * @Author: wangchao
 * @Email: wangchao@hellogeek.com
 * @date: 2019/7/23 ;
 */
public class FutureCallTest {

    public static void main(String[] args) {
        FutureTask<String> futureTask = new FutureTask<>(new RealData("ab="));

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<?> submit = executorService.submit(futureTask);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {

        }

        try {
            System.out.println("submit="+futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }
}
