package com.tlibrary.controller;

import com.tlibrary.model.User;
import com.tlibrary.service.LogInService;
import com.tlibrary.util.ImportText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author：wcc
 * @Description：
 * @Date：Create in: 2019/1/2--14:46
 */
@Controller
public class IndexController {

    @Autowired
    private ImportText it;
    @Autowired
    private LogInService is;


    @RequestMapping("/")
    public String  toIndex(){
        return "inedx/index.html";
    }

    @RequestMapping("/index")
    public String  indextoIndex(){
        return "index.html";
    }
    @RequestMapping("inedx/index.jsp")
    public String  jsptoIndex(){
        return "index.html";
    }
    @RequestMapping("inedx/index.html")
    public String  htmljsptoIndex(){
        return "index.html";
    }
    @RequestMapping("/importTxt")
    public String  toTxt(){
        return "txtimport/txtimport.html";
    }
    @RequestMapping("/login")
    public String  login(HttpServletRequest request){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = new User();
        user.setAccountid(username);
        user.setPassword(password);
        String msg=null;
        try {
             msg = is.login(user);
        }catch (Exception e){

            request.setAttribute("loginmsg","未找到登陆信息");
            e.printStackTrace();
        }
        if (msg==null){return "inedx/index.html";}
        else if (msg.equals("0")){return "administrators/administrators.html";}
        else if (msg.equals("1")){return "teacher/teacher.html";}
        else if (msg.equals("2")){return "student/student.html";}
        else {
            return "err/err.html";
        }
    }
}
