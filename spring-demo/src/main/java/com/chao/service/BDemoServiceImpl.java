package com.chao.service;

import com.chao.annotation.Service;

/**
 * @Filename: BDemoServiceImpl
 * @Description:
 * @Version: 1.0
 * @Author: wangchao
 * @Email: wangchao@hellogeek.com
 * @date: 2019/7/27 ;
 */

@Service("bDemo")
public class BDemoServiceImpl implements DemoService {
    @Override
    public String getName(String name) {
        return "bDemo";
    }
}
