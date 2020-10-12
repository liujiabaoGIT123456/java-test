package com.testall.demo.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.testall.demo.service.TestService;
import com.testall.demo.entity.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
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
        ExcelReader reader = ExcelUtil.getReader(FileUtil.file("test.xlsx"));

    }

    @Value("${filepath}")
    private String filepath;

    /**
     * 处理文件上传
     */
    @PostMapping(value = "/upload")
    public String uploading(@RequestParam("file") MultipartFile file) {
        File targetFile = new File(filepath);
        if (!targetFile.exists()) {
            boolean mkdirs = targetFile.mkdirs();
        }
        try (FileOutputStream out = new FileOutputStream(filepath + file.getOriginalFilename());){
            out.write(file.getBytes());
            //InputStream inputStream = file.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("文件上传失败!");
            return "uploading failure";
        }
        System.out.print("文件上传成功!");
        return "uploading success";
    }
    
}
