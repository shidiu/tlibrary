package com.tlibrary.service;

import com.tlibrary.dao.MultiplechoiceMapper;
import com.tlibrary.model.Multiplechoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Author：wcc
 * @Description：
 * @Date：Create in: 2019/1/3--9:35
 */
@Service
public class InsertService {

    @Autowired
    private MultiplechoiceMapper mm;

    public int insertMultiplechoice(List<Map<String,String>> allMultipleChoice) {
        int num=0;
        Multiplechoice mc=new Multiplechoice();
        for (int i = 0; i < allMultipleChoice.size() ; i++) {
            Map<String, String> map = allMultipleChoice.get(i);
              mc.setTitle (map.get("title"));
              int j=1;
              while (j<=6){
                  switch (j){
                      case 1 :
                          mc.setA(map.get("con"+j));
                          break;
                      case 2 :
                          mc.setB(map.get("con"+j));
                          break;
                      case 3 :
                          mc.setC(map.get("con"+j));
                          break;
                      case 4 :
                          mc.setD(map.get("con"+j));
                          break;
                      case 5 :
                          mc.setE(map.get("con"+j));
                          break;
                      case 6 :
                          mc.setF(map.get("con"+j));
                          break;
                  }
                  j++;
              }
              mc.setId(UUID.randomUUID().toString());
              try {
                  num += mm.insert(mc);
              }catch (Exception e){
                  e.printStackTrace();
              }


        }
        return num;
    }

}
