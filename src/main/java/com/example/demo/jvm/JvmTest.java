package com.example.demo.jvm;

/**
 * @Filename: JvmTest
 * @Description:
 * @Version: 1.0
 * @Author: wangchao
 * @Email: wangchao@hellogeek.com
 * @date: 2019/9/10 ;
 */
public class JvmTest {

    public static void main(String[] args) {
     //testJVM_1();
        //
        testStack();

    }

    private static void testStack() {
        long timeMillis = System.currentTimeMillis();
        for (int i = 0; i < 100000000; i++) {
            alloc();        
        }
        long timeMillis1 = System.currentTimeMillis();
        System.out.println(timeMillis1-timeMillis);
    }

    private static void alloc() {
        byte[] bytes=new byte[2];
        bytes[0]=1;
    }

    //-6的二进制
    private static void testJVM_1() {
        int a=-6;
        for (int i = 0; i < 32; i++) {
            int x=(a& 0x80000000>>>i)>>>(31-i);
            System.out.print(x);
        }
        System.out.println();
    }
}
