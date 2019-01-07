package com.tlibrary.service;

import com.tlibrary.dao.UserMapper;
import com.tlibrary.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author：wcc
 * @Description：
 * @Date：Create in: 2019/1/7--11:17
 */
@Service
public class LogInService {

   @Autowired
   private UserMapper um;

    public String login(User user) {
        User msg = um.getUser(user);
        System.out.println(msg);
        return msg.getType();
    }
}
