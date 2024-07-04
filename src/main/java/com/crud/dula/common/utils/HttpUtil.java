package com.crud.dula.common.utils;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;

/**
 * @author crud
 * @date 2024/5/20
 */
@Slf4j
public class HttpUtil {
    private static final OkHttpClient CLIENT = new OkHttpClient();
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    /**
     * 发送GET请求
     *
     * @param url          请求地址
     * @param responseType 响应类型
     * @param <T>          泛型类型
     * @return 响应体
     */
    public static <T> T get(String url, Class<T> responseType) throws IOException {
        return get(url, null, responseType);
    }

    /**
     * 发送GET请求
     *
     * @param url          请求地址
     * @param headers      请求头
     * @param responseType 响应类型
     * @param <T>          泛型类型
     * @return 响应体
     */
    public static <T> T get(String url, Map<String, String> headers, Class<T> responseType) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .headers(toHeaders(headers))
                .build();
        return call(request, responseType);
    }

    /**
     * 发送POST请求 - JSON格式
     *
     * @param url          请求地址
     * @param body         JSON对象
     * @param responseType 响应类型
     * @param <T>          泛型类型
     * @return 响应体
     */
    public static <T> T post(String url, Object body, Class<T> responseType) throws IOException {
        return post(url, body, null, responseType);
    }

    /**
     * 发送POST请求 - JSON格式
     *
     * @param url          请求地址
     * @param body         JSON对象
     * @param headers      请求头
     * @param responseType 响应类型
     * @param <T>          泛型类型
     * @return 响应体
     */
    public static <T> T post(String url, Object body, Map<String, String> headers, Class<T> responseType) throws IOException {
        RequestBody requestBody = RequestBody.create(JsonUtil.toJson(body), JSON);
        Request request = new Request.Builder()
                .url(url)
                .headers(toHeaders(headers))
                .post(requestBody)
                .build();
        return call(request, responseType);
    }

    /**
     * 发送请求
     *
     * @param request      请求
     * @param responseType 响应类型
     * @param <T>          泛型类型
     * @return 响应体
     */
    public static <T> T call(Request request, Class<T> responseType) throws IOException {
        try (Response response = CLIENT.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return JsonUtil.toBean(response.body(), responseType).orElse(null);
            } else {
                throw new IOException("Unexpected code " + response);
            }
        }
    }

    /**
     * 将Map转换为Headers
     *
     * @param headersMap 请求头Map
     * @return Headers
     */
    private static Headers toHeaders(Map<String, String> headersMap) {
        Headers.Builder builder = new Headers.Builder();
        if (headersMap != null) {
            for (Map.Entry<String, String> entry : headersMap.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
        }
        return builder.build();
    }

}
