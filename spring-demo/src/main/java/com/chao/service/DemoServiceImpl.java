package com.chao.service;

import com.chao.annotation.Service;

/**
 * @Filename: DemoServiceImpl
 * @Description:
 * @Version: 1.0
 * @Author: wangchao
 * @Email: wangchao@hellogeek.com
 * @date: 2019/7/27 ;
 */
@Service("aDemo")
public class DemoServiceImpl implements DemoService {
    @Override
    public String getName(String name) {
        return "aDemo";
    }
}
