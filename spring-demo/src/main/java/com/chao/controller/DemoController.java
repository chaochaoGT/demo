package com.chao.controller;

import com.chao.annotation.Autowired;
import com.chao.annotation.Controller;
import com.chao.annotation.UrlMapping;
import com.chao.service.DemoService;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Filename: DemoController
 * @Description:
 * @Version: 1.0
 * @Author: wangchao
 * @Email: wangchao@hellogeek.com
 * @date: 2019/7/27 ;
 */
@Controller
@UrlMapping(value = "/demo")
public class DemoController  {

    @Autowired(value = "aDemo")
    private DemoService ademoService;
    @Autowired(value = "bDemo")
    private DemoService bdemoService;

    @UrlMapping("/getName")
    public String getName(@RequestParam(value = "name") String name){

        String a =ademoService.getName(name);
        String b =bdemoService.getName(name);

        return String.format("%s ***********A service =%s  |||||| B  service =%s ","chao",a,b);
    }

}
