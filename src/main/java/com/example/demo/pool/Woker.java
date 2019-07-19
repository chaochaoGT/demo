package com.example.demo.pool;

/**
 * @Filename: Woker
 * @Description:
 * @Version: 1.0
 * @Author: wangchao
 * @Email: wangchao@hellogeek.com
 * @date: 2019/7/17 ;
 */
public class Woker extends Thread {

    private ThreadPool threadPool;
    private Runnable target;
    private boolean isRunning=false;
    private boolean isShutDown=false;


    public Woker(Runnable target, String name, ThreadPool threadPool) {
        super(name);
        this.target=target;
        this.threadPool=threadPool;

    }

    public synchronized void shutDowm() {
        isShutDown=true;
        notifyAll();
    }

    public synchronized void setTarget(Runnable woker) throws InterruptedException {
            target=woker;
            notifyAll();
    }

    @Override
    public void run() {
        while (!isShutDown) {
            isRunning = false;
            if (null != target) {
                target.run();
            }

            isRunning = true;

            try {
                threadPool.repool(this);
                synchronized (this){
                    wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            isRunning=false;
        }
    }


}
