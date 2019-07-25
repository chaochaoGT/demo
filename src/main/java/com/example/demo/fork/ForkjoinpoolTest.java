package com.example.demo.fork;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @Filename: ForkjoinpoolTest
 * @Description:
 * @Version: 1.0
 * @Author: wangchao
 * @Email: wangchao@hellogeek.com
 * @date: 2019/7/19 ;
 */
public class ForkjoinpoolTest extends RecursiveTask<Long> {

    private static final int LIMIT_NUMB=10000;
    private long star;
    private long end;
    private static int ONE_SIZE=809;
    public ForkjoinpoolTest(long star, long end) {
        this.star = star;
        this.end = end;
    }



    @Override
    protected Long compute() {
        long sum=0L;
        boolean ableCount = (end-star)<LIMIT_NUMB;
        if (ableCount){
            for (long i = star; i <end ; i++) {
                sum+=1;
            }
        }else {
            long slipt=(end-star)/ONE_SIZE;
            List<ForkjoinpoolTest> lists=new ArrayList<ForkjoinpoolTest>();

            long userStar=star;

            for (int i = 0; i < ONE_SIZE; i++) {

//                long lastOne=userStar+ slipt;
//                System.out.println("userStar="+userStar+"********lastOne="+lastOne);
//
//                if (lastOne>end){
//                    lastOne=end;
//                }

//                ForkjoinpoolTest forkjoinpoolTest=new ForkjoinpoolTest(userStar,lastOne);
                ForkjoinpoolTest forkjoinpoolTest=new ForkjoinpoolTest(userStar+i*slipt,userStar+(i+1)*slipt);
//                userStar+=slipt+1;

                lists.add(forkjoinpoolTest);

                forkjoinpoolTest.fork();
            }

            for (ForkjoinpoolTest f:lists){
                System.out.println("================"+f.join());
                sum+= f.join();
            }
        }
        return sum;
    }


    public static void main(String[] args) {
        ForkJoinPool fp=new ForkJoinPool();
        //本次测试没有累加 ， 且超过20000000出现内存溢出：
        //原因：end增大 ， ONE_SIZE增大 ，for循环越多线程越多 ，内存溢出
        ForkjoinpoolTest forkjoinpoolTest=new ForkjoinpoolTest(0,100000000L);
        ForkJoinTask<Long> f=fp.submit(forkjoinpoolTest);

        try {
            System.out.println("sum="+f.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
