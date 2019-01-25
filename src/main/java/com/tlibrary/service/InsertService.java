package com.tlibrary.service;

import com.tlibrary.dao.MultiplechoiceMapper;
import com.tlibrary.model.MultiplechoiceWithBLOBs;
import com.tlibrary.util.GetQuestion;
import com.tlibrary.util.IsExitImg;
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
    @Autowired
    private GetQuestion getQuestion;
    @Autowired
    private IsExitImg isExitImg;

    public int insertMultiplechoice(List<Map<String,String>> allMultipleChoice) {
        int num=0;
        MultiplechoiceWithBLOBs mc = new MultiplechoiceWithBLOBs();
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




    public int insertDoc(String getall, Map<String,byte[]> pics) {
        int num=0;
        MultiplechoiceWithBLOBs mc = new MultiplechoiceWithBLOBs();
        List<Map<String,String>> allMultipleChoice=getQuestion.getAllMultipleChoice(getall);
        for (int i = 0; i <allMultipleChoice.size() ; i++) {
            Map<String, String> map = allMultipleChoice.get(i);
            mc.setTitle (map.get("title"));
            int j=1;
            while (j<=6){
                switch (j){
                    case 1 :
                        mc.setA(map.get("con"+j));
                        String s1 = map.get("con" + j);
                        if (s1==null){
                            break;
                        }
                        if(isExitImg.isExit(map.get("con"+j))){
                            mc.setPica(pics.get(isExitImg.getTab(map.get("con"+j))));
                        }
                        break;
                    case 2 :
                        mc.setB(map.get("con"+j));
                        String s2 = map.get("con" + j);
                        if (s2==null){
                            break;
                        }
                        if(isExitImg.isExit(map.get("con"+j))){
                            mc.setPica(pics.get(isExitImg.getTab(map.get("con"+j))));
                        }
                        break;
                    case 3 :
                        mc.setC(map.get("con"+j));

                        String s3 = map.get("con" + j);
                        if (s3==null){
                            break;
                        }
                        if(isExitImg.isExit(map.get("con"+j))){
                            mc.setPica(pics.get(isExitImg.getTab(map.get("con"+j))));
                        }
                        break;
                    case 4 :
                        mc.setD(map.get("con"+j));
                        String s4 = map.get("con" + j);
                        if (s4==null){
                            break;
                        }
                        if(isExitImg.isExit(map.get("con"+j))){
                            mc.setPica(pics.get(isExitImg.getTab(map.get("con"+j))));
                        }
                        break;
                    case 5 :
                        mc.setE(map.get("con"+j));
                        String s5 = map.get("con" + j);
                        if (s5==null){
                            break;
                        }
                        if(isExitImg.isExit(map.get("con"+j))){
                            mc.setPica(pics.get(isExitImg.getTab(map.get("con"+j))));
                        }
                        break;
                    case 6 :
                        mc.setF(map.get("con"+j));
                        String s6 = map.get("con" + j);
                        if (s6==null){
                            break;
                        }
                        if(isExitImg.isExit(map.get("con"+j))){
                            mc.setPica(pics.get(isExitImg.getTab(map.get("con"+j))));
                        }
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
