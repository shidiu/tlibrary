package com.tlibrary.controller;

import com.google.gson.Gson;
import com.tlibrary.service.InsertService;
import com.tlibrary.util.DocToString;
import com.tlibrary.util.ImportText;
import com.tlibrary.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author：wcc
 * @Description：
 * @Date：Create in: 2019/1/2--16:28
 */
@Controller
@RequestMapping("upload")
public class ImportController {
    @Autowired
    private ImportText it;
    @Autowired
    private InsertService is;
    @Autowired
    private DocToString docToString;

    @RequestMapping("/txt")
    public @ResponseBody
    void getTxt(@RequestParam(value="txt") MultipartFile file,HttpServletResponse response){
        String s = it.toString(file);
        List<Map<String, String>> allMultipleChoice = it.getAllMultipleChoice(s);
        List<String> fillins = it.getFillins(s);
        List<String> question = it.getquestion(s);
        List<String> tfng = it.getTFNG(s);
        int i = is.insertMultiplechoice(allMultipleChoice);
        Map map=new HashMap<>();
        map.put("allMultipleChoice",allMultipleChoice);
        map.put("question",question);
        map.put("tfng",tfng);
        map.put("fillins",fillins);
        if (i==0){
            map.put("msg", "上传失败");
        }else {
            map.put("msg", "上传成功");
        }
        Gson gson = new Gson();
        String json = gson.toJson(map);
        ResponseUtil.toHtml(response, json);
    }


    @RequestMapping("/doc")
    public void   getdoc(@RequestParam(value="doc") MultipartFile file,HttpServletResponse response){
        Map doctomap = docToString.docxtomap(file);
        String getall= (String) doctomap.get("word");
        Map<String,byte[]> pics= (Map<String, byte[]>) doctomap.get("picmap");
        int i=0;
        i=is.insertDoc(getall,pics);
        Map map=new HashMap<>();
        if (i==0){
            map.put("msg", "上传失败");
        }else {
            map.put("msg", "上传成功");
        }
        Gson gson = new Gson();
        String json = gson.toJson(map);
        ResponseUtil.toHtml(response, json);
    }
}
