package com.example.demo.mode.future;

/**
 * @Filename: FutureData
 * @Description:
 * @Version: 1.0
 * @Author: wangchao
 * @Email: wangchao@hellogeek.com
 * @date: 2019/7/23 ;
 */
public class FutureData implements Data {
    protected RealData realData=null;
    private boolean isSuccess = false;

    public synchronized void setRealData(RealData realData) {
        if (isSuccess) {
            return;
        }
        this.realData = realData;
        this.isSuccess = true;
        notifyAll();
    }

    @Override
    public synchronized String getData() {
        while (!isSuccess) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return realData.getData();
    }
}
