package com.zhuofengyuan.wechat.shop.util;

import com.zhuofengyuan.wechat.shop.prop.PbiSettings;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Pbi工具类
 */
public class PbiUtil {

    public static String generatedFormParam2String(PbiSettings settings){
        // 遍历属性类、属性值
        Field[] fields = settings.getClass().getDeclaredFields();

        StringBuilder requestURL = new StringBuilder();
        try {
            boolean flag = true;
            String property, value;
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                // 允许访问私有变量
                field.setAccessible(true);

                // 属性名
                property = field.getName();
                // 属性值
                value = field.get(settings).toString();

                String params = property + "=" + value;
                if (flag) {
                    requestURL.append(params);
                    flag = false;
                } else {
                    requestURL.append("&" + params);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return requestURL.toString();
    }

    public static String doGetAzureToken(PbiSettings settings) {
        HttpRequest request = HttpRequest.newBuilder(URI.create(settings.getAADToken()))
                .header("Content-Type","application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(generatedFormParam2String(settings)))
                .build();

        HttpResponse<String> result = null;
        try {
            result = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return result.body();
    }

    public static void main(String[] args) {
        // 1.获取cookie
        HttpRequest request = HttpRequest.newBuilder(URI.create("http://mlsoa.zsmls.com/logincheck.php?UNAME=1000779&PASSWORD=YS4xMjM0NTY=&encode_type=1"))
                .header("Content-Type","application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString("1"))
                .build();

        HttpResponse<String> result = null;
        try {
            result = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        var cookieo = result.headers().allValues("set-cookie").stream().filter(s -> s.contains("PHPSESSID")).collect(Collectors.toList()).get(0);
        var oauid = result.headers().allValues("set-cookie").stream().filter(s -> s.contains("OA_USER_ID")).collect(Collectors.toList()).get(0);
//        System.out.println(result.headers().allValues("set-cookie").toString().substring(1, result.headers().allValues("set-cookie").toString().length() - 1));
        var cookie = cookieo.substring(0, cookieo.indexOf(";"));

        //2.获取ticket用来登陆SSO
        var url = "https://mlsoa.zsmls.com/general/sso.php?appName=officeapp&goto=https://phpapp.zsmls.com/oa/module/ssologin/?referer=http://phpapp.zsmls.com/oa/module/attendance";
        HttpRequest request2 = HttpRequest.newBuilder(URI.create(url))
                .header("Cookie", result.headers().allValues("set-cookie").toString().substring(1, result.headers().allValues("set-cookie").toString().length() - 1))
                .POST(HttpRequest.BodyPublishers.ofString("1"))
                .build();
        HttpResponse<String> result2 = null;
        try {
            result2 = HttpClient.newHttpClient().send(request2, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println(result2.body());
        var url2 = result2.headers().allValues("location").get(0);
        System.out.println(url2);

        //3.通过SSO登陆系统
        var request3 = HttpRequest.newBuilder(URI.create(url2))
//                .header("Cookie", result.headers().allValues("set-cookie").toString().substring(1, result.headers().allValues("set-cookie").toString().length() - 1))
                .GET()
                .build();
        HttpResponse<String> result3 = null;
        try {
            result3 = HttpClient.newHttpClient().send(request3, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println(result3.headers().map());

        //4.获取最终考勤数据
        var url3 = result3.headers().allValues("location").get(0);
        System.out.println(url3);
        var cookie2 = result3.headers().allValues("set-cookie").get(0);
        System.out.println(cookie2.substring(0, cookie2.indexOf(";")));
        var request4 = HttpRequest.newBuilder(URI.create("https://phpapp.zsmls.com/oa/module/attendance.html"))
                .header("Cookie", cookie2.substring(0, cookie2.indexOf(";")))
                .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .POST(HttpRequest.BodyPublishers.ofString("bd=2020-04-23&ed=2020-04-23&page=1&is_count=1"))
                .build();
        HttpResponse<String> result4 = null;
        try {
            result4 = HttpClient.newHttpClient().send(request4, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(result4.body());
    }
}
