package com.testall.demo.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.StyleSet;
import com.testall.demo.service.TestService;
import com.testall.demo.entity.Test;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

@RestController
public class TestDBController {
    private final TestService testDBDao;

    public TestDBController(TestService testDBDao) {
        this.testDBDao = testDBDao;
    }

    //连接数据库测试
    @RequestMapping("/test")
    public List<Test> test() {
        return testDBDao.test();
    }

    //batis-plus测试
    @RequestMapping("/testPlus")
    public List<Test> testPlus() {
        return testDBDao.testPlus();
    }

    //测试
    public static void main(String[] args) {


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
        try (FileOutputStream out = new FileOutputStream(filepath + file.getOriginalFilename());) {
            out.write(file.getBytes());
            //InputStream inputStream = file.getInputStream();
            //ExcelReader reader = ExcelUtil.getReader(inputStream);
            //List<Map<String, Object>> maps = reader.readAll();
            //List<Map<String, Object>> read = reader.read(0, 1, 2);
            //for(Map<String, Object> map:read){
            //    System.out.print("11");
            //}


        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("文件上传失败!");
            return "uploading failure";
        }
        System.out.print("文件上传成功!");

        return "uploading success";
    }

    /**
     * 处理文件下载
     */
    @RequestMapping("/download")
    public void downLoad(HttpServletResponse response) throws UnsupportedEncodingException {
        String filename = "账号密码.txt";
        String filePath = "D:/file";
        File file = new File(filePath + "/" + filename);
        if (file.exists()) {
            response.setContentType("application/octet-stream");
            response.setHeader("content-type", "application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(filename, "utf8"));
            byte[] buffer = new byte[1024];
            //输出流
            OutputStream os = null;
            try (FileInputStream fis = new FileInputStream(file);
                 BufferedInputStream bis = new BufferedInputStream(fis)) {
                os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 处理excel下载
     */
    @RequestMapping("/downLoadExcel")
    public void downLoadExcel(HttpServletResponse response) throws UnsupportedEncodingException {
        response.setContentType("application/octet-stream");
        response.setHeader("content-type", "application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode("filename.xlsx", "utf8"));
        byte[] buffer = new byte[1024];
        //输出流
        OutputStream os = null;
        try {
            os = response.getOutputStream();


            ExcelWriter writer = ExcelUtil.getWriter();
            int i = 0;
            String[] split={"年龄","性别","身高"};
            for (String exportRow : split) {
                writer.addHeaderAlias(exportRow, exportRow).autoSizeColumn(i);
                i += 1;
            }
            List<Map<String,String>> copyMap=new ArrayList<>();
            Map<String,String> map1=new HashMap<>();
            map1.put("年龄","刘佳宝");
            map1.put("性别","12");
            map1.put("身高","33");
            copyMap.add(map1);
            Map<String,String> map2=new HashMap<>();
            map2.put("年龄","刘佳宝1");
            map2.put("性别","12213");
            map2.put("身高","3313");
            copyMap.add(map2);
            List<Map<String,String>> newMaps = new ArrayList<>();
            for (Map<String,String> map : copyMap) {
                Map<String,String> m = new LinkedHashMap<>();
                for (String exportRow : split) {
                    m.put(exportRow, map.get(exportRow));
                }
                newMaps.add(m);
            }
            writer.write(newMaps);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
            writer.flush(byteArrayOutputStream);
            // 关闭writer，释放内存
            writer.close();
            byte[] bytes = byteArrayOutputStream.toByteArray();




            os.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 异步处理
     */
    @RequestMapping("/async")
    public String threadExecAsync() {
        ThreadUtil.execAsync(() -> {
            for (int i = 0; i < 1000000; i++) {
                System.out.println(i);
            }
        });
        return "成功";
    }


}
