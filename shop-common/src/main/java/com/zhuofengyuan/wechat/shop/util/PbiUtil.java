package com.zhuofengyuan.wechat.shop.util;

import com.zhuofengyuan.wechat.shop.prop.PbiSettings;

import java.lang.reflect.Field;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

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
}
