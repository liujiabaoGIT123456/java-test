package com.testall.demo.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.StyleSet;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.testall.demo.entity.EnumAll;
import com.testall.demo.service.TestService;
import com.testall.demo.entity.Test;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
public class TestDBController {
    private final TestService testDBDao;

    public TestDBController(TestService testDBDao) {
        this.testDBDao = testDBDao;
    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i < 30; i++) {
            list.add(i);
        }
        List<Integer> collect = list.stream().skip(10).limit(10).collect(Collectors.toList());
        collect.forEach(System.out::println);

    }

    //连接数据库测试
    @RequestMapping("/test")
    public List<Test> test() {
        List<Test> test = testDBDao.test();
        System.out.println(test);
        return testDBDao.test();
    }

    //batis-plus测试
    @RequestMapping("/testPlus")
    public List<Test> testPlus() {
        return testDBDao.testPlus();
    }

    private static final Algorithm ALGORITHM = Algorithm.HMAC256("d84b297d50f3c726563372e5c3f5c66a");

    public static String encode() {
        //通过秘钥生成一个算法
        String token = JWT.create()
                //设置签发者
                //.withIssuer("test")
                //设置过期时间为一个小时
                .withExpiresAt(new Date(System.currentTimeMillis() + 3 * 60 * 60 * 1000))
                //设置用户信息
                .withClaim("name", "liqixiu")
                .withClaim("email", "liqixiu@cmschina.com.cn")
                .withClaim("iat", new Date())
                //.withClaim("return_to","www.baidu.com")
                .sign(ALGORITHM);
        return token;
    }

    //测试


    //list分页
    public static void main3(String[] args) {
        List<Map<Integer, Integer>> list = new ArrayList();
        for (int i = 0; i < 10; i++) {
            Map map = new HashMap<>();
            map.put(i, i);
            list.add(map);
        }
        List<Map<Integer, Integer>> collect = list.stream().skip(5 * (2 - 1)).limit(5).collect(Collectors.toList());
        int i = 0;
        for (Map<Integer, Integer> test : collect) {
            System.out.println(test.get(i));
            i++;
        }

    }

    //json互转
    public static void main2(String[] args) {
        //String jsonStr = JSONUtil.parse(brand).toString();
        //PmsBrand brandBean = JSONUtil.toBean(jsonStr, PmsBrand.class);
        List<Map<String, String>> lis = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("1", "2");
        lis.add(map);
        String s = JSONUtil.parse(lis).toString();
        System.out.println(s);
        List<Map> maps = JSONUtil.toList(new JSONArray(s), Map.class);
        for (Map map1 : maps) {
            map1.keySet().forEach(System.out::println);

        }


    }

    //正则分割和获取数据
    public static void main1(String[] args) {
        String a = "a2019.01 - 2019.01b2c2019.01 - 2019.01d5asdfa2019.01 - asf";
        String[] split = a.split("[0-9]{4}\\.[0-9]{2}\\s-\\s([0-9]{4}\\.[0-9]{2})?");
        Arrays.stream(split).forEach(System.out::println);

        Pattern p = Pattern.compile("[0-9]{4}\\.[0-9]{2}\\s-\\s([0-9]{4}\\.[0-9]{2})?");
        Matcher m = p.matcher("a2019.01 - 2019.01b2c2019.01 - 2019.01d5asdfa2019.01 - asf");
        while (m.find()) {
            System.out.println(m.group());
        }

    }

    @Value("${filepath}")
    private String filepath;

    /**
     * 处理文件上传
     */
    @PostMapping(value = "/upload")
    public String uploading(@RequestParam("file") MultipartFile file) throws IOException {
        File targetFile = new File(filepath);
        if (!targetFile.exists()) {
            boolean mkdirs = targetFile.mkdirs();
        }
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<List<Object>> read = reader.read();
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

            List<Map<String, String>> newMaps = new ArrayList<>();
            Map<String, String> map1 = new LinkedHashMap<>();
            String[] split = {"债券预警项", "判断条件", "是否达到预警条件", "具体说明", "详情备注"};
            int i = 0;
            for (String exportRow : split) {
                writer.addHeaderAlias(exportRow, exportRow).autoSizeColumn(i);
                i += 1;
                map1.put(exportRow, i + "");
            }
            newMaps.add(map1);
            writer.merge(split.length - 1, "债券违约预警详情展示", true);
            List<String> a = new ArrayList<>();
            a.add("说明：发债主体为中信证券。本管理人共有xx只产品持有该主题的债券，产品名分别为xxx、xxx…");
            a.add("数据更新日期：20200817");
            writer.writeRow(a);
            Font font = writer.createFont();
            font.setColor(Font.COLOR_RED);
            font.setFontName("宋体"); // 设置字体
            font.setFontHeightInPoints((short) 10); // 设置字号
            //第二个参数表示是否忽略头部样式
            writer.getStyleSet().setFont(font, true);
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


    /**
     * 标签转换
     *
     * @param file
     * @throws IOException
     */
    @RequestMapping("/labelChange")
    public void labelChange(@RequestParam("file") MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<Map<String, Object>> maps = reader.readAll();

        List<Map<String, Object>> values = new ArrayList<>();
        for (Map<String, Object> map : maps) {
            List<String> collect = map.keySet().stream().map(a -> a + "").collect(Collectors.toList());
            collect.remove("L_ID");
            List<String> other = collect.stream().filter(a -> a.contains("其他")).collect(Collectors.toList());
            List<String> notOther = collect.stream().filter(a -> !a.contains("其他")).collect(Collectors.toList());
            for (String item : notOther) {
                Object item1 = map.get(item);
                Object item2 = map.get(item + "（其他）");
                if ((item1 == null || StringUtils.isBlank(item1 + "")) && (item2 == null || StringUtils.isBlank(item2 + ""))) {
                    continue;
                }
                if (StringUtils.isBlank(item1 + "")) {
                    item1 = null;
                }
                if (StringUtils.isBlank(item2 + "")) {
                    item2 = null;
                }
                Map<String, Object> label = new HashMap<>();
                label.put("L_ID", map.get("L_ID"));
                label.put("LABEL", item);
                label.put("LABEL_VALUE", item1 == null ? "" : item1);
                label.put("OTHER_VALUE", item2 == null ? "" : item2);
                label.put("LABEL_GROUP", EnumAll.LabelEnum.getType(item));
                label.put("ID", UUID.randomUUID().toString());
                values.add(label);
            }
        }
        String s = JSONUtil.parse(values).toString();
        Test test = new Test();
        test.setValue(s);
        test.setId(UUID.randomUUID().toString());
        testDBDao.insert(test);
        List<Map> maps1 = JSONUtil.toList(new JSONArray(s), Map.class);
        System.out.println(values.size());

    }

    @RequestMapping("/downLoadLabel")
    public void downLoadLabel(HttpServletResponse response) throws UnsupportedEncodingException {
        response.setContentType("application/octet-stream");
        response.setHeader("content-type", "application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode("标签.xlsx", "utf8"));
        byte[] buffer = new byte[1024];
        //输出流
        OutputStream os = null;
        try {
            List<Test> test = test();
            String value = test.get(0).getValue();
            List<Map> maps1 = JSONUtil.toList(new JSONArray(value), Map.class);
            os = response.getOutputStream();
            ExcelWriter writer = ExcelUtil.getWriter();
            writer.write(maps1);
            for (int i = 0; i < 5; i++) {
                writer.autoSizeColumn(i, false);
            }

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
     * 访问静态资源
     *
     * @param response
     * @throws Exception
     */
    @GetMapping("/downloadTemplateForUserTest")
    @ResponseBody
    public void downloadLocal(HttpServletResponse response) throws Exception {
        /** 获取静态文件的路径 .*/
        String path = ResourceUtils.getURL("src/main/resources/static/labelValue.xls").getPath();


        /** 获取文件的名称 . */
        File file = new File(path);
        if (!file.exists()) {
            System.out.println("路径错误");

        }

        String fileName = path.substring(path.lastIndexOf("/") +1);
        /** 将文件名称进行编码 */
        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        response.setContentType("content-type:octet-stream");
        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));

        OutputStream outputStream = response.getOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inputStream.read(buffer)) != -1) { /** 将流中内容写出去 .*/
            outputStream.write(buffer, 0, len);

        }
        inputStream.close();
        outputStream.close();
    }

}
