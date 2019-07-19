package com.example.demo;

import java.util.concurrent.atomic.AtomicReferenceArray;

/**
 * @Filename: AtomicIntegerTest
 * @Description:
 * @Version: 1.0
 * @Author: wangchao
 * @Email: wangchao@hellogeek.com
 * @date: 2019/7/2 ;
 */
public class AtomicArrayTest<E> implements java.io.Serializable {


    public static void main(String[] args) {
        System.out.println( new AtomicReferenceArray<String>(10));
    }
}
