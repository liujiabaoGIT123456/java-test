package com.testall.demo.controller;

import com.testall.demo.service.TestService;
import com.testall.demo.entity.Test;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestDBController {
    private final TestService testDBDao;

    public TestDBController(TestService testDBDao) {
        this.testDBDao = testDBDao;
    }

    //连接数据库测试
    @RequestMapping("/test")
    public List<Test> test(){
        return testDBDao.test();
    }

    //batis-plus测试
    @RequestMapping("/testPlus")
    public List<Test> testPlus(){
        return testDBDao.testPlus();
    }

    //测试
    public static void main(String[] args){

    }
    
}
