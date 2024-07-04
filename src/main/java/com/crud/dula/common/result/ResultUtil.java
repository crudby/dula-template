package com.crud.dula.common.result;

import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author crud
 * @date 2023/9/15
 */
public class ResultUtil {

    /**
     * 将HttpResult结果写入HTTP响应中。
     *
     * @param response   HTTP响应对象，用于向客户端发送响应。
     * @param httpResult 包含响应数据和状态码的HttpResult对象。
     * @throws IOException ioe
     */
    public static void writeResponseResult(HttpServletResponse response, HttpResult<String> httpResult) throws IOException {
        response.setStatus(200);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(httpResult));
        writer.flush();
        writer.close();
    }
}
