package com.example.relief.network.http.interceptor;

import com.example.relief.network.http.BaseConnection;
import com.example.relief.network.http.HttpConnection;
import com.example.relief.network.http.HttpsConnection;
import com.example.relief.network.http.Request;
import com.example.relief.network.http.Result;

import java.io.IOException;

/**
 * 基础的拦截器实现类，最终是由该类来实现请求
 */
public class BaseInterceptor implements Interceptor {

    public static final String HTTPS = "https:";

    @Override
    public Result intercept(Chain chain) throws IOException {
        Request request = chain.request();
        BaseConnection connection;
        String url = request.getPath();
        if (url.startsWith(HTTPS)) {
            connection = new HttpsConnection(url);
        } else {
            connection = new HttpConnection(url);
        }
        return connection.doRequest(request);
    }
}
