package com.tlibrary.util;

import com.tlibrary.model.MultiplechoiceWithBLOBs;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Author：wcc
 * @Description：
 * @Date：Create in: 2019/1/23--15:07
 */
@Service
public class DocToString {


    public Map docxtomap(MultipartFile file){
        return doctomap(file);
    }
    private Map doctomap(MultipartFile file) {
        String lineTxt = null;
        String sbt = "";
        InputStream inputStream = null;
        Map<String,  byte[]> mapb = new HashMap<String,  byte[]>();
        try {

            inputStream = file.getInputStream();
            XWPFDocument xDocument = new XWPFDocument(inputStream);
            List<XWPFParagraph> paragraphs = xDocument.getParagraphs();
            List<XWPFPictureData> pictures = xDocument.getAllPictures();
            Map<String, String> map = new HashMap<String, String>();
            for (XWPFPictureData picture: pictures) {
                String id=picture.getParent().getRelationId(picture);
                String rawName = picture.getFileName();
                String fileExt = rawName.substring(rawName.lastIndexOf("."));
                String newName = System.currentTimeMillis() + UUID.randomUUID().toString() + fileExt;
                byte[] data = picture.getData();
                MultiplechoiceWithBLOBs multiplechoiceWithBLOBs=new MultiplechoiceWithBLOBs();
                multiplechoiceWithBLOBs.setPica(data);
                System.out.println(newName);
                map.put(id,newName);
                mapb.put(newName,data);
            }
            String text = "";
            for (XWPFParagraph paragraph:paragraphs) {
                List<XWPFRun> runs = paragraph.getRuns();
                for(XWPFRun run : runs){
                    if(run.getCTR().xmlText().indexOf("<w:drawing>")!=-1){
                        String runXmlText = run.getCTR().xmlText();
                        int rIdIndex = runXmlText.indexOf("r:embed");
                        int rIdEndIndex = runXmlText.indexOf("/>", rIdIndex);
                        String rIdText = runXmlText.substring(rIdIndex, rIdEndIndex);
                        String id = rIdText.split("\"")[1];
                        text = text +"</img src= '"+map.get(id)+"'/>";
                    }else {
                        text = text + run;
                    }
                }
            }
            lineTxt=text;
        }catch (Exception e){
            e.printStackTrace();
        }
        Map map=new HashMap();
        map.put("picmap",mapb);
        map.put("word",lineTxt);
        return map;
    }


}
