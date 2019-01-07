package com.tlibrary.util;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author：wcc
 * @Description：
 * @Date：Create in: 2018/12/27--15:24
 */
@Service
public class ImportText {


//    void  test(){
//        String filePath="D:\\testdate\\wuli.txt";
//        File file = new File(filePath);
//        String lineTxt =null;
//        StringBuilder sb = null;
//        String sbt=null;
//        if (file.isFile()&&file.exists()){
//            try {
//                InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
//                BufferedReader bufferedReader = new BufferedReader(reader);
//
//                while (null != (lineTxt=bufferedReader.readLine())){
//                    // System.out.println(lineTxt);
//                    sbt+=lineTxt;
//                }
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            String text = sbt;
//            List<Map<String, String>> maps = AllMultipleChoice(sbt);
//            System.out.println(maps);
//            List<String> tfng = getTFNG(sbt);
//            System.out.println(tfng);
//            List<String> fillins = getFillins(sbt);
//            System.out.println(fillins);
//            List<String> getquestion = getquestion(sbt);
//            System.out.println(getquestion);
//        }else{
//            System.out.println("文件不存在！");
//        }
//    }


    public String toString(MultipartFile file){
        String lineTxt =null;
        String sbt="";
        InputStream input = null;
        try {
            input = file.getInputStream();
            InputStreamReader reader = new InputStreamReader(input);
            BufferedReader bufferedReader = new BufferedReader(reader);
            while (null != (lineTxt=bufferedReader.readLine())){
                sbt+=lineTxt;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "文件读取出错";
        } catch (IOException e) {
            e.printStackTrace();
            return "文件内部出现错误";
        }
            return sbt;
    }
    /*
        获取选择题
     */

    /**
     * 输入字符串的到选择题的list<map>
     * @param text
     * @return
     */

    public List<Map<String,String>> getAllMultipleChoice(String text){
        String allMultipleChoice = GetAllMultipleChoice(text);
        int numMultipleChoice = GetNumMultipleChoice(allMultipleChoice);
        return GetMultipleChoice(allMultipleChoice,numMultipleChoice);
    }

    /**
     * 获得选择题全部
     * @param text
     * @return string
     */
    private String GetAllMultipleChoice(String text){
        return getContent(text,"选择题");
    }

    /**
     * 获得全部选择题的数目
     * @param allMultipleChoice
     * @return
     */
    private int GetNumMultipleChoice(String allMultipleChoice){
        return getCount(allMultipleChoice, "题");
    }

    /**
     * 得到完整的选择题返回list
     * @param text
     * @param count
     * @return
     */
    private List<Map<String,String>> GetMultipleChoice(String text,int count){
        List<Map<String,String>> multipleChoicesList= new ArrayList<Map<String, String>>();
        Map<String,String> multipleChoic=new HashMap<String, String>();
        for (int i = 1; i <=count ; i++) {
            String msg = getContent(text, "题" + i);
            multipleChoic=getAmultipleChoic(msg);
            multipleChoicesList.add(multipleChoic);
        }
        return multipleChoicesList;
    }

    /**
     * 获得一道完整的选择题
     * @param msg
     * @return
     */
    private Map<String,String> getAmultipleChoic(String msg) {
        Map<String,String> map=new HashMap<String, String>();
        String title = getTitle(msg);
        map.put("title",title);
        int count = getCount(msg, "选项");
        for (int i = 1; i <=count ; i++) {
            String con=getContent(msg,"选项"+i);
            String t=getContent(con,"内容");
            map.put("con"+i,t);
        }
        return  map;
    }

    /**
     * 获得选择的题目
     * @param text
     * @return
     */
    private String getTitle(String text){
        return  getContent(text,"名称");
    }

    /*
        获取判断题
    }
     */
    public List<String> getTFNG(String text){
        String allTFNG = getContent(text,"判断题");
        int i = getCount(allTFNG, "题");
        return getquestion(allTFNG,i);
    }

    /**
     * 获取填空题
     * @param text
     * @return
     */
    public List<String> getFillins(String text){
        String content = getContent(text, "填空题");
        int i = getCount(content, "题");
        return getquestion(content,i);
    }

    public List<String> getquestion(String text){
        String content = getContent(text, "问答题");
        int i = getCount(content, "题");
        return getquestion(content,i);
    }

    /**
     * 获得非判断题的内容
     * @param allTFNG
     * @param count
     * @return
     */

    private List<String> getquestion(String allTFNG, int count) {
        List<String> list=new ArrayList<String>();
        for (int i = 1; i <=count ; i++) {
            String content = getContent(allTFNG, "题" + i);
            String  tf=getContent(content,"名称");
            list.add(tf);
        }
        return  list;
    }


    /**
     * 获取标签中的内容
     * @param text
     * @param tag
     * @return
     */
    private String getContent(String text,String tag) {
        String bt=null;
        int  btIndex =text.indexOf("<"+tag+">");
        int  btLastIndex=text.indexOf("</"+tag+">");
        if(btIndex!=-1&&btLastIndex!=-1){
            bt = text.substring(btIndex+("<"+tag+">").length(), btLastIndex);
        }
        return bt;
    }

    /**
     * 获得结尾标签出现次数
     * @param text
     * @param tag
     * @return
     */

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

}
