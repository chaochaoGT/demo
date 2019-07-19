package com.example.demo.pool;

import java.util.List;
import java.util.Vector;

/**
 * @Filename: ThreadPool
 * @Description:
 * @Version: 1.0
 * @Author: wangchao
 * @Email: wangchao@hellogeek.com
 * @date: 2019/7/17 ;
 */
public class ThreadPool {
    private List<Woker> pools;
    private int poolNumb;
    private boolean isShutDown;
    private static ThreadPool instance=null;
    private ThreadPool() {
        this.pools = new Vector<>(5);
        this.poolNumb = 0;
    }


    public static ThreadPool getInstance(){
         if (instance==null){
             return instance=new ThreadPool();
         }
         return instance;
    }

    //将现场收集
    protected   synchronized void   repool(Woker woker){
            if (!isShutDown){
                pools.add(woker);
            }else {
                woker.shutDowm();
            }
    }


    public  synchronized  void shutDown(){
        isShutDown=true;

        for (int poolIndex=0; poolIndex < pools.size(); poolIndex++){
            Woker runnable = pools.get(poolIndex);
            runnable.shutDowm();
        }
    }
    public  synchronized  void start(Runnable runnable) throws InterruptedException {
        Woker woker=null;

        if (pools.size()>0){
            woker=pools.get(pools.size()-1);
            pools.remove(woker);
            woker.setTarget(runnable);
        }else {
            poolNumb++;
            woker=new Woker(runnable,String.format("pools %s",poolNumb),this);
            woker.start();
        }
    }


    public static void main(String[] args) {
        ThreadPool instance = ThreadPool.getInstance();
        for (int i = 0; i < 5; i++) {
            try {
                instance.start(new TestPool());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static class TestPool implements Runnable {

        @Override
        public void run() {
            System.out.println("I`m running.....");
        }
    }
}
