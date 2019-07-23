package com.example.demo.mode.future.call;

import java.util.concurrent.Callable;

/**
 * @Filename: RealData
 * @Description:
 * @Version: 1.0
 * @Author: wangchao
 * @Email: wangchao@hellogeek.com
 * @date: 2019/7/23 ;
 */
public class RealData implements Callable<String> {
    protected final String result;

    public RealData(String result) {
        this.result = result;
    }

    @Override
    public String call() throws Exception {
        StringBuffer sb=new StringBuffer();
        for (int i = 0; i <10;i++) {
            sb.append(result);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return   sb.toString();
    }
}
