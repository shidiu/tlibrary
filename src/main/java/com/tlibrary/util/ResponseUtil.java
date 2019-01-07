package com.tlibrary.util;

import com.google.gson.Gson;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * @Author：wcc
 * @Description：
 * @Date：Create in: 2019/1/3--16:01
 */
public class ResponseUtil {

    private ResponseUtil(){}
    protected static final Log logger = LogFactory
            .getLog(ResponseUtil.class);

    public static void toHtml(HttpServletResponse response,String json) {
        response.setCharacterEncoding("utf-8");
        try {
            response.getWriter().print(json);
        }catch (Exception e) {
            logger.error(e);
        }
    }

    @SuppressWarnings("rawtypes")
    public static void writeHtml(HttpServletResponse response, List<Map> dataList) {
        Gson gson = new Gson();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("result", "success");
        resultMap.put("items", dataList);
        String json = gson.toJson(resultMap);
        toHtml(response, json);
    }

    public static void toHtml1(HttpServletResponse response, String json) {
        response.setCharacterEncoding("utf-8");
        try {
            response.getWriter().print("_jqjsp("+json+")");
        }catch (Exception e) {
            logger.error(e);
        }

    }

}
