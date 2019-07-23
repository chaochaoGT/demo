package com.example.demo.mode.future;

/**
 * @Filename: FutureTest
 * @Description:
 * @Version: 1.0
 * @Author: wangchao
 * @Email: wangchao@hellogeek.com
 * @date: 2019/7/23 ;
 */
public class FutureTest {

    public static void main(String[] args) {
        try {
            ClintFuture clintFuture=new ClintFuture();
            Data tmd = clintFuture.request("tmd");

            System.out.println(tmd.getData());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
