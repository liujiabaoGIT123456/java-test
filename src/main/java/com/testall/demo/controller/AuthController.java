package com.testall.demo.controller;


import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.HmacUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class AuthController {


    private static final String ISO8601_FORMAT = "yyyyMMdd'T'HHmmss'Z'";

    public static String formatISO8601(Date date) {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat(ISO8601_FORMAT);
        dateTimeFormat.setTimeZone(new SimpleTimeZone(0, "UTC"));
        return dateTimeFormat.format(date);
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
//////消息列表
        Map<String, Object> map1 = new LinkedHashMap<>();
        map1.put("limit",10);
        map1.put("autoAck",false);
        map1.put("full",true);
        String param=JSONUtil.parse(map1).toString();
        Map<String, String> head = new LinkedHashMap<>();
        head.put("content-type","application/json;charset=utf-8");
        String authorization=getAuthorization("874F2D1471DE41BA96A0","999EAFC267D94D15AE587AA44B6D9B045C7A1CBF","/exs/api/messages/v1","POST",param,head,null);
        System.out.println(authorization);


//确认消息
//        Map<String, Object> map1 = new LinkedHashMap<>();
//        map1.put("id","35833");
//        String param=JSONUtil.parse(map1).toString();
//        Map<String, String> head = new LinkedHashMap<>();
//        head.put("content-type","application/json;charset=utf-8");
//        String authorization=getAuthorization("874F2D1471DE41BA96A0","999EAFC267D94D15AE587AA44B6D9B045C7A1CBF","/exs/api/messages/ack/v1","POST",param,head,null);
//        System.out.println(authorization);

//读取消息
//        byte[] emptyBody = new byte[0];
//        String s = new String(emptyBody);
//
//        String authorization =
//                getAuthorization("874F2D1471DE41BA96A0", "999EAFC267D94D15AE587AA44B6D9B045C7A1CBF", "/exs/api/messages/19098/v1", "GET", s, new HashMap<>(), null);
//        System.out.println(authorization);
//        final HttpGet httpGet = new HttpGet("https://exchange.cs.cmburl.cn/exs/api/messages/19098/v1");
//        httpGet.setHeader("authorization", authorization);
//
//        try (CloseableHttpClient httpClient = HttpClients.createDefault(); CloseableHttpResponse response = httpClient.execute(httpGet)) {
//            System.out.println(response.getStatusLine());
//            String s1 = EntityUtils.toString(response.getEntity(), "UTF-8");
//            Map map = JSONUtil.toBean(s1, Map.class);
//            Map infbdy = JSONUtil.toBean(map.get("INFBDY") + "", Map.class);
//            List<Map> maps1 = JSONUtil.toList(new JSONArray(infbdy.get("PBDSOHLDX1")), Map.class);
//            for(Map item:maps1){
//                List<String> maps2 = JSONUtil.toList(new JSONArray(item.get("securIdList")), String.class);
//                System.out.println(maps2);
//            }
//            System.out.println(map);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        //文件上传
//        String json = "我";
//        System.out.println(json.getBytes(StandardCharsets.UTF_8).length);
//        String Date = formatISO8601(new Date());
//        //请求体参数加密
//        String contentHash = DigestUtils.sha256Hex(json);
//        String ak = "874F2D1471DE41BA96A0";
//        String sk = "999EAFC267D94D15AE587AA44B6D9B045C7A1CBF";
//
//        Map<String, String> head = new HashMap<>();
//        head.put("content-type", "application/octet-stream");
//        head.put("x-cmb-repo", "lu67_pbds_files");
//        head.put("x-cmb-meta-file-name", URLEncoder.encode("test.txt", "UTF-8"));
//
//        Map<String, String> objectObjectHashMap = new HashMap<>();
//        objectObjectHashMap.put("storage", "fs");
//
//        String StringToSign = buildStringToSign(Date, "PUT", "/upload", head, objectObjectHashMap, contentHash);
//        String Signature = HmacUtils.hmacSha256Hex(sk, StringToSign);
//        //签名
//        String auth = "CMB1-HMAC-SHA256" + " " + ak + ":" + Date + ":" + Signature;
//        System.out.println(auth);
//
//        final HttpPut httpPut = new HttpPut("https://exchange.cs.cmburl.cn/upload?storage=fs");
//        httpPut.setHeader("content-type", "application/octet-stream");
//        httpPut.setHeader("x-cmb-repo", "lu67_pbds_files");
//        httpPut.setHeader("x-cmb-meta-file-name", URLEncoder.encode("test.txt", "UTF-8"));
//        //httpPut.setHeader("x-cmb-meta-last-modified", time);
//        httpPut.setHeader("authorization", auth);
//        InputStream file = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
//        httpPut.setEntity(new InputStreamEntity(file, json.length(), ContentType.APPLICATION_OCTET_STREAM));
//        try (CloseableHttpClient httpClient = HttpClients.createDefault(); CloseableHttpResponse response = httpClient.execute(httpPut)) {
//            System.out.println(response.getStatusLine());
//            String s = EntityUtils.toString(response.getEntity(), "UTF-8");
//            Map map = JSONUtil.toBean(s, Map.class);
//            System.out.println(map);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


        //发送消息
//        String json = "123";
//        String Date = formatISO8601(new Date());
//        //请求体参数加密
//        String contentHash = DigestUtils.sha256Hex(json);
//        String ak = "874F2D1471DE41BA96A0";
//        String sk = "999EAFC267D94D15AE587AA44B6D9B045C7A1CBF";
//
//        Map<String, String> head = new HashMap<>();
//        head.put("content-type", "application/json;charset=UTF-8");
//        head.put("x-cmb-exs-type", "PBDSFLID");
//        head.put("x-cmb-exs-to", "PBDS");
//
//        String StringToSign = buildStringToSign(Date, "PUT", "/exs/api/messages/v1", head, null, contentHash);
//        String Signature = HmacUtils.hmacSha256Hex(sk, StringToSign);
//        //签名
//        String auth = "CMB1-HMAC-SHA256" + " " + ak + ":" + Date + ":" + Signature;
//        System.out.println(auth);
//
//
//        // 创建Post请求
//        HttpPut httpPost = new HttpPut("https://exchange.cs.cmburl.cn/exs/api/messages/v1");
//        httpPost.setHeader("authorization", auth);
//        httpPost.setHeader("content-type","application/json;charset=UTF-8");
//        httpPost.setHeader("x-cmb-exs-type", "PBDSFLID");
//        httpPost.setHeader("x-cmb-exs-to","PBDS");
//        StringEntity stringEntity = new StringEntity("123", ContentType.APPLICATION_JSON);
//        httpPost.setEntity(stringEntity);
//        try (CloseableHttpClient httpClient = HttpClients.createDefault(); CloseableHttpResponse response = httpClient.execute(httpPost)) {
//            System.out.println(response.getStatusLine());
//            String s = EntityUtils.toString(response.getEntity(), "UTF-8");
//            Map map = JSONUtil.toBean(s, Map.class);
//            Object resultData = map.get("resultData");
//            List<Map> maps = JSONUtil.toList(new JSONArray(resultData), Map.class);
//            System.out.println(s+"+++++");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }




    }

    //ak,sk,请求类型，POST=JSON参数,请求头,GET=参数
    public static String getAuthorization(String ak, String sk, String url, String httpType, String postParam, Map<String, String> headers, Map<String, String> parameters) {
        //签名协议名称和签名算法名称
        String Scheme = "CMB1-HMAC-SHA256";
        //当前时间
        String Date = formatISO8601(new Date());
        //请求体参数加密
        String contentHash = DigestUtils.sha256Hex(postParam);


        String StringToSign = buildStringToSign(Date, httpType, url, headers, parameters, contentHash);
        String Signature = HmacUtils.hmacSha256Hex(sk, StringToSign);
        //签名
        return Scheme + " " + ak + ":" + Date + ":" + Signature;
    }


    public static String buildStringToSign(String dateTime, String httpMethod, String resourcePath, Map<String, String> headers, Map<String, String> parameters, String contentHash) {
        return "CMB1-HMAC-SHA256\n" + dateTime + "\n" + httpMethod + "\n" + resourcePath + "\n" + getCanonicalizedHeaderString(headers) + "\n" + getCanonicalizedQueryString(parameters) + "\n" + contentHash;
    }

    public static String getCanonicalizedHeaderString(Map<String, String> headers) {
        if (headers == null || headers.isEmpty()) {
            return "";
        }
        return headers.entrySet().stream().map(entry -> new AbstractMap.SimpleEntry<>(entry.getKey().toLowerCase(), entry.getValue())).filter(entry -> entry.getKey().equalsIgnoreCase("content-type") || entry.getKey().startsWith("x-cmb-")).sorted(Map.Entry.comparingByKey()).map(entry -> entry.getKey().replaceAll("\\s+", " ") + ":" + entry.getValue().replaceAll("\\s+", " ") + "\n").collect(Collectors.joining(""));
    }

    public static String getCanonicalizedQueryString(Map<String, String> parameters) {
        if (parameters == null || parameters.isEmpty()) {
            return "";
        }
        return parameters.entrySet().stream().sorted(Map.Entry.comparingByKey()).map(entry -> entry.getKey() + "=" + entry.getValue()).collect(Collectors.joining("&"));
    }


}

