package com.testall.demo.controller;

import okhttp3.OkHttp;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.web.bind.annotation.RestController;
import sun.net.www.http.HttpClient;

import javax.servlet.http.HttpServletRequest;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.SimpleTimeZone;

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
        //4.
        //问题一：HTTP-Verb（请求方法），ResourcePath（请求路径），CanonicalizedHeaders（请求头）指的是调用机构外联数据传输平台API中的？
        String json = "{\"f1\":\"v1\",\"f2\":\"v2\"}";//问题二：指是调用API中请求体的参数？如果是get请求的参数是否拼接在请求路径中？
        String contentHash = DigestUtils.sha256Hex(json);
        String StringToSign = Scheme + Date + "post"+ "/path" + "CanonicalizedHeaders" + "CanonicalizedQueryString" + contentHash;
        String Signature = HmacUtils.hmacSha256Hex("999EAFC267D94D15AE587AA44B6D9B045C7A1CBF", StringToSign);
        //签名
        String authorization = Scheme + " " + AccessKey + ":" + Date + ":" + Signature;
        System.out.println(authorization);

    }

}

