package com.testall.demo.controller;

import cn.hutool.http.HttpUtil;
import okhttp3.OkHttp;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.web.bind.annotation.RestController;
import sun.net.www.http.HttpClient;


import javax.servlet.http.HttpServletRequest;
import java.net.URL;
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

    public static void main(String[] args) {
        //1.签名协议名称和签名算法名称
        String Scheme = "CMB1-HMAC-SHA256";
        //2.
        String AccessKey = "874F2D1471DE41BA96A0";
        //3.当前时间
        String Date = formatISO8601(new Date());
        System.out.println(Date);
        //4.
        String json = "{\"limit\":10,\"autoAck\":false,\"full\":true}";
        System.out.println(json);
        String contentHash = DigestUtils.sha256Hex(json);
        Map<String,String> herd=new HashMap<>();
        herd.put("content-type","application/json;charset=utf-8");
        String StringToSign=buildStringToSign(Date,"POST","/exs/api/messages/v1",herd,null,contentHash);
        String Signature = HmacUtils.hmacSha256Hex("999EAFC267D94D15AE587AA44B6D9B045C7A1CBF", StringToSign);
        //签名
        String authorization = Scheme + " " + AccessKey + ":" + Date + ":" + Signature;
        System.out.println(authorization);

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

