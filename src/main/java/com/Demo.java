package com;

public class Demo {

    /**
     * @param args
     */

    public static void main(String[] args) {

        final Printer2 p = new Printer2();

        new Thread() {

            @Override
            public void run() {

                while (true) {

                    try {

                        p.print1();

                    } catch (InterruptedException e) {


                        e.printStackTrace();

                    }

                }

            }

        }.start();


        new Thread() {

            @Override
            public void run() {

                while (true) {

                    try {

                        p.print2();

                    } catch (InterruptedException e) {


                        e.printStackTrace();

                    }

                }

            }

        }.start();


        new Thread() {

            @Override
            public void run() {

                while (true) {

                    try {

                        p.print3();

                    } catch (InterruptedException e) {


                        e.printStackTrace();

                    }

                }

            }

        }.start();

        new Thread() {

            @Override
            public void run() {

                while (true) {

                    try {
//                        if (Thread.currentThread().isInterrupted()){
//                            break;
//                        }
//                        Thread.currentThread().interrupt();
                        p.print4();
                    } catch (InterruptedException e) {


                        e.printStackTrace();
                        break;
                    }

                }

            }

        }.start();

    }

}

class Printer2 {

    private int flag = 1;

    public synchronized void print1() throws InterruptedException {

        Thread.sleep(1000);

        while (flag != 1) {

            this.wait();

        }

        System.out.println("黑马程序员"+Thread.currentThread().getName());

        flag = 2;

        this.notifyAll();

    }


    public synchronized void print2() throws InterruptedException {

        Thread.sleep(1000);

        while (flag != 2) {

            this.wait();                    //线程2在此等待

        }

        System.out.println("传智播客"+Thread.currentThread().getName());

        flag = 3;

        this.notifyAll();

    }


    public synchronized void print3() throws InterruptedException {

        Thread.sleep(1000);

        while (flag != 3) {

            this.wait();

        }

        System.out.println("itheima"+Thread.currentThread().getName());

        flag = 4;

        this.notifyAll();

    }


    public synchronized void print4() throws InterruptedException {

        Thread.sleep(1000);

        while (flag != 4) {

            this.wait();

        }

        System.out.println("abcdddddd"+Thread.currentThread().getName());

        flag = 1;

        this.notifyAll();




    }




}


