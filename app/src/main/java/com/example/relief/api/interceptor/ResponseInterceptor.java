package com.example.relief.api.interceptor;

import androidx.annotation.NonNull;
import com.example.relief.network.http.Request;
import com.example.relief.network.http.Result;
import com.example.relief.network.http.interceptor.Interceptor;

import java.io.IOException;

/**
 * 头部信息拦截器，负责在请求头部加入固定的参数
 */
public class ResponseInterceptor implements Interceptor {

    @NonNull
    @Override
    public Result intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        Result result = null;
        try {
            result = chain.proceed(request);
        } catch (Exception e) {
            e.getStackTrace();
        }
        return result;
    }
}
