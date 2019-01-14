package com.tlibrary.service;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.tlibrary.dao.UserMapper;
import com.tlibrary.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Author：wcc
 * @Description：
 * @Date：Create in: 2019/1/9--17:24
 */
@Service
public class AdminService {
    @Autowired
    private UserMapper userMapper;
    public Map finduser(Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        List<Map<String,String>> users=userMapper.getAllUser();
        Map returnMap = new HashMap();
        returnMap.put("total", "100");
        returnMap.put("rows", users);
        return returnMap;
    }

    public String saveAdduser(User user) {
        user.setId(UUID.randomUUID().toString());
        user.setPassword("00000000");
        int i=0;
        try {
             i = userMapper.insertSelective(user);
        }catch (Exception e){
            e.printStackTrace();
            return "添加失败";
        }
        if (i==1){
            return "添加成功";
        }else {
            return "添加失败";
        }
    }

    public int delUser(JSONArray array) {
        int msg=0;
        for (int i = 0; i <array.size() ; i++) {
            Map<String,String> o = (Map) array.get(i);
            String id=o.get("id");
            msg+=userMapper.deleteByPrimaryKey(id);
        }
        return msg;
    }
}
