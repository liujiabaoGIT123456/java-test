package com.testall.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.testall.demo.entity.Test;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TestDao extends BaseMapper<Test> {
    List<Test> test();
}
