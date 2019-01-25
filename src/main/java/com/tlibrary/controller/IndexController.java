package com.tlibrary.controller;

import com.tlibrary.model.User;
import com.tlibrary.service.LogInService;
import com.tlibrary.util.ImportText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    public String toIndex() {
        return "inedx/index.html";
    }

    @RequestMapping("/index")
    public String indextoIndex() {
        return "index.html";
    }

    @RequestMapping("inedx/index.jsp")
    public String jsptoIndex() {
        return "index.html";
    }

    @RequestMapping("inedx/index.html")
    public String htmljsptoIndex() {
        return "index.html";
    }

    @RequestMapping("/importTxt")
    public String toTxt() {
        return "txtimport/txtimport.html";
    }

    @RequestMapping("/importDoc")
    public String importDoc() {
        return "docImport/DocImport.html";
    }
    @RequestMapping("/login")
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = new User();
        user.setAccountid(username);
        user.setPassword(password);
        String msg = null;
        try {
            msg = is.login(user);
        } catch (Exception e) {
            request.setAttribute("loginmsg", "未找到登陆信息");
            e.printStackTrace();
        }
        if (msg == null) {
            modelAndView.setViewName("inedx/index.html");
        } else if (msg.equals("0")) {
                modelAndView.setViewName("redirect:/toAdministrators");
        } else if (msg.equals("1")) {
            modelAndView.setViewName("redirect:toTeacher");
        } else if (msg.equals("2")) {
            modelAndView.setViewName("redirect:toStudent");
        } else {
            modelAndView.setViewName("redirect:/err/err.html");
        }

        return  modelAndView;
    }

    @RequestMapping("/toTeacher")
    public String toTeacher(){
        return "/teacher/teacher.html";
    }
    @RequestMapping("/toStudent")
    public String toStudent(){
        return "/student/student.html";
    }
    @RequestMapping("/toAdministrators")
    public String toAdministrators(){
        return "/administrators/administrators.html";
    }
}


