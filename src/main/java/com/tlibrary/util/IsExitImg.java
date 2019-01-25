package com.tlibrary.util;

import org.springframework.stereotype.Service;

/**
 * @Author：wcc
 * @Description：
 * @Date：Create in: 2019/1/24--16:38
 */
@Service
public class IsExitImg {

    public boolean isExit(String string){
        int img=0;
         img = getCount(string, "img");
        if (0==img){
            return false;
        }
        else {
            return true;
        }

    }

    private int getCount(String text,String tag){
        int count=0;
        String aim="</"+tag;
        char[] texts = text.toCharArray();
        char[] aims = aim.toCharArray();
        for (int i = 0; i <texts.length ; i++) {
            if (texts[i]==aims[0]){
                for (int j = 1; j <aims.length ; j++) {
                    if (texts[i+j]!=aims[j]){
                        break;
                    }else if (j==(aims.length-1)){
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public String getTab(String s) {
        int i = s.indexOf("src=");
        int star=s.indexOf("'",i);
        int end=s.indexOf("'",star+1);
        String substring = s.substring(star+1, end);
        return substring;
    }
}
