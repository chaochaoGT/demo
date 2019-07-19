package com;

public class DemoTest  {

    public static Object u=new Object();
    public static MyThred m1=new MyThred();
    public static MyThred m2=new MyThred();



    public static class MyThred extends Thread {

        @Override
        public  void  run() {
            while (true){
                if (Thread.currentThread().isInterrupted()){
                    break;
                }
                synchronized (u){
                    System.out.println("dddd"+Thread.currentThread().getName());
                    try {
                        Thread.sleep(2000L);
                        Thread.currentThread().suspend();
                    } catch (InterruptedException e) {
                    }
                }

            }
        }
    }


    public static void main(String[] args) throws InterruptedException {
        m1.start();
        m2.start();

        m1.resume();
        m2.resume();

        m1.join();
        m2.join();

    }




}


