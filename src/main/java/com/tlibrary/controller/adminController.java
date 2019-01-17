package com.tlibrary.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.tlibrary.model.User;
import com.tlibrary.service.AdminService;
import com.tlibrary.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author：wcc
 * @Description：
 * @Date：Create in: 2019/1/9--17:18
 */
@Controller
@RequestMapping("/admin")
public class adminController {

    @Autowired
    private importController importController;
    @Autowired
    private AdminService as;
    @RequestMapping("/find")
    public void finduser(HttpServletRequest request, HttpServletResponse response){
        Integer pageNum = Integer.valueOf(request.getParameter("page"));
        Integer pageSize = Integer.valueOf(request.getParameter("rows"));

        Map returnMap =as.finduser(pageNum, pageSize);
        Gson gson = new Gson();
        String json = gson.toJson(returnMap);
        //将数据发送到前端
        System.out.println(json);
        ResponseUtil.toHtml(response, json);
    }

    @RequestMapping("/saveAdduser")
    public @ResponseBody Object saveAdduser(HttpServletRequest request,HttpServletResponse response){
        String dlzh = request.getParameter("dlzh");
        String userName = request.getParameter("userName");
        String dept = request.getParameter("dept");
        String major = request.getParameter("major");
        String type = request.getParameter("type");
        User user=new User();
        user.setAccountid(dlzh);
        user.setUsername(userName);
        user.setDept(dept);
        user.setMajor(major);
        user.setType(type);
        String msg=as.saveAdduser(user);
//        Map returnMap=new HashMap();
//        returnMap.put("data",msg);
//        Gson gson = new Gson();
//        String json = gson.toJson(gson);
//        //将数据发送到前端
//        System.out.println(json);
//        ResponseUtil.toHtml(response, json);
        return  msg;
    }
    @RequestMapping("/delUser")
    public @ResponseBody String delUser(HttpServletRequest request){
        String datas = request.getParameter("datas");
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        JSONArray array = JSONObject.parseArray(datas);
        int i = as.delUser(array);
        if (i==0){
            return "删除失败";
        }else {
            return "删除成功";
        }

    }

    /**
     * 批量新增用户
     * @param file
     * @param response
     */
    @RequestMapping("/addeUsers")
    public @ResponseBody
    void addeUsers(@RequestParam(value="exceladd") MultipartFile file, HttpServletResponse response,HttpServletRequest request){

        Map map=new HashMap<>();

        Gson gson = new Gson();
        String json = gson.toJson(map);
        ResponseUtil.toHtml(response, json);
    }

}
