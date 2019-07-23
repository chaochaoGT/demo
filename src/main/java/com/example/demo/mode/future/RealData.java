package com.example.demo.mode.future;

/**
 * @Filename: RealData
 * @Description:
 * @Version: 1.0
 * @Author: wangchao
 * @Email: wangchao@hellogeek.com
 * @date: 2019/7/23 ;
 */
public class RealData implements Data {
    protected final String result;

    public RealData(String result) {
        StringBuffer sb=new StringBuffer();
        for (int i = 0; i <10;i++) {
            sb.append(result);
//            System.out.println();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        this.result = sb.toString();
    }

    @Override
    public String getData() {
        return result;
    }
}
