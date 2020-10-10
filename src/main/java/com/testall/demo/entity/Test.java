package com.testall.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("TEST")
@Data
public class Test {
    @TableId("ID")
    private String id;

    @TableField("NAME")
    private String name;
}
