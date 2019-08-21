package com.zhuofengyuan.wechat.shop.util;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.Executors;

public class HttpUtil {

    private static HttpUtil util = null;
    private HttpClient client = null;

    public static HttpUtil getInstance(){
        if(util == null){
            util = new HttpUtil();
        }
        return util;
    }

    public HttpUtil(){
        init();
    }

    private void init(){
        //创建 builder
        HttpClient.Builder builder = HttpClient.newBuilder();

        //链式调用
        client = builder
                //http 协议版本 1.1 或者 2
                .version(HttpClient.Version.HTTP_2) //.version(HttpClient.Version.HTTP_1_1)
                //连接超时时间，单位为毫秒
                .connectTimeout(Duration.ofMillis(5000)) //.connectTimeout(Duration.ofMinutes(1))
                //连接完成之后的转发策略
                .followRedirects(HttpClient.Redirect.NEVER) //.followRedirects(HttpClient.Redirect.ALWAYS)
                //指定线程池
                .executor(Executors.newFixedThreadPool(5))
                //认证，默认情况下 Authenticator.getDefault() 是 null 值，会报错
                //.authenticator(Authenticator.getDefault())
                //代理地址
                //.proxy(ProxySelector.of(new InetSocketAddress("http://www.baidu.com", 8080)))
                //缓存，默认情况下 CookieHandler.getDefault() 是 null 值，会报错
                //.cookieHandler(CookieHandler.getDefault())
                //创建完成
                .build();
    }

    public HttpResponse<String> get(String url) {
        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .version(HttpClient.Version.HTTP_2)
                .uri(URI.create(url))
                .timeout(Duration.ofMillis(5000))
                .GET().build();

        HttpResponse<String> resp = null;
        try {
            resp = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return resp;
    }

}
