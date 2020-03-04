package com.zhuofengyuan.wechat.shop.util;

import com.alibaba.fastjson.JSONObject;
import com.zhuofengyuan.wechat.shop.exception.FengtoosException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class FengtoosUtil {

    public static void null2Entity(Object entity, String msg){
        if(entity == null){
            throw new FengtoosException(500, msg);
        }
    }

    public static void null2Entity(Object entity){
        null2Entity(entity, "对象不能为空");
    }

    public static void main(String[] args) throws Exception {

        var formbody = "resource=https://analysis.windows.net/powerbi/api&client_id=c3b1069c-50b9-4bf8-b52e-201a843eeea2&grant_type=password&password=Yzf19950420&username=fengtoos.yuan@zsmls.cn&client_secret=P/eeqJ:Y2.wAhcdcLgCZ?9IEb23Upm@l";
        HttpRequest request = HttpRequest.newBuilder(URI.create("https://login.microsoftonline.com/common/oauth2/token"))
                .header("Content-Type","application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(formbody))
                .build();

        var result = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject Object = JSONObject.parseObject(result.body());
        System.out.println(Object);
    }
}
