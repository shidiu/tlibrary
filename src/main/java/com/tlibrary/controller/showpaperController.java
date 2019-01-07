package com.tlibrary.controller;

import com.tlibrary.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author：wcc
 * @Description：
 * @Date：Create in: 2019/1/3--16:45
 */
@Controller
@RequestMapping("show")
public class showpaperController {

    @Autowired
    private ShowService ss;


    @RequestMapping("/paper")
    public  String showpaper(HttpServletRequest request){
            ss.getMultipleChoice();
        return "multipleChoiceShow/multipleChoiceShow.html";
    }
}
