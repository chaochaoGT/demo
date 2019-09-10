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
        int a=-6;
        for (int i = 0; i < 32; i++) {
            int x=(a& 0x80000000>>>i)>>>(31-i);
            System.out.print(x);
        }
        System.out.println();
    }
}
