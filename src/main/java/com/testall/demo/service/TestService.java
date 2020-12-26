package com.testall.demo.service;

import com.testall.demo.dao.TestDao;
import com.testall.demo.entity.Test;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TestService {
    private  final TestDao testDao;

    public TestService(TestDao testDao) {
        this.testDao = testDao;
    }

    public  List<Test> test() {
        return testDao.test();
    }

    public List<Test> testPlus() {
        List<Test> tests = testDao.selectList(null);
        return tests;
    }

    public void insert(Test test) {
        testDao.insert(test);
    }
}
