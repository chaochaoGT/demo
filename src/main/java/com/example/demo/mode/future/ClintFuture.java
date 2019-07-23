package com.example.demo.mode.future;

/**
 * @Filename: FutureDemo
 * @Description:
 * @Version: 1.0
 * @Author: wangchao
 * @Email: wangchao@hellogeek.com
 * @date: 2019/7/23 ;
 */
public class ClintFuture {

    public   Data request(String result) {
      final    FutureData futureData = new FutureData();

        new Thread() {
            @Override
            public void run() {
                RealData r = new RealData(result);
                futureData.setRealData(r);
            }
        }.start();
        return futureData;
    }
}
