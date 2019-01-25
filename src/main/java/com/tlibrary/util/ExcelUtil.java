package com.tlibrary.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author：wcc
 * @Description：Excel工具类
 * @Date：Create in: 2019/1/17--10:53
 */
@Service
public class ExcelUtil {
    public static final String OFFICE_EXCEL_2003_POSTFIX = "xls";
    public static final String OFFICE_EXCEL_2010_POSTFIX = "xlsx";
    public static final String EMPTY = "";
    public static final String POINT = ".";
    public static SimpleDateFormat sdf =   new SimpleDateFormat("yyyy/MM/dd");

    /**
     * 导出excel公共方法
     */
    public void exportExcelCommon(
            HttpServletRequest request,
            HttpServletResponse response,List list,String[] title,String[] fields,String filename
    ) throws Exception {
        //生成excel文件
        //创建excel工作簿
        HSSFWorkbook workbook=new HSSFWorkbook();
        //创建工作表sheet
        HSSFSheet sheet=workbook.createSheet();
        for(int i=0;i<title.length;i++){
            sheet.setColumnWidth(i, 7000);
        }
        //创建第一行
        HSSFRow row=sheet.createRow(0);
        HSSFCell cell=null;


        HSSFCellStyle style = (HSSFCellStyle) workbook.createCellStyle();
        HSSFCellStyle style2 = (HSSFCellStyle) workbook.createCellStyle();
        //CellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        style.setFont(font);


        CellStyle css = workbook.createCellStyle();
        DataFormat  format = workbook.createDataFormat();
        css.setDataFormat(format.getFormat("@"));
        HSSFRow row1=sheet.createRow(0);
        for(int i=0;i<title.length;i++){
            row1.createCell(i);
            row1.getCell(i).setCellValue(title[i]);
            sheet.setDefaultColumnStyle(i, css);
        }



        // CellStyle style = wb.createCellStyle();
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        //   cell.setCellStyle(style);

        style2.setBorderBottom(CellStyle.BORDER_THIN);
        style2.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style2.setBorderLeft(CellStyle.BORDER_THIN);
        style2.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style2.setBorderRight(CellStyle.BORDER_THIN);
        style2.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style2.setBorderTop(CellStyle.BORDER_THIN);
        style2.setTopBorderColor(IndexedColors.BLACK.getIndex());


        //粗体显示style.setFont(font);
        //单元格样式cell1.setCellStyle(style);
        //给cell1这个单元格设置样式




        //插入第一行数据的表头
        for(int i=0;i<title.length;i++){
            cell=row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }
        if (list!=null){
            //写入数据
            for(int i=1;i<=list.size();i++){
                HSSFRow dataRow=sheet.createRow(i);
                Map tmap = (Map) list.get(i-1);
                for(int j=0;j<fields.length;j++){
                    String cellData = (String) tmap.get(fields[j]);
                    HSSFCell hcell=dataRow.createCell(j);
                    hcell.setCellValue(cellData);
                    hcell.setCellStyle(style2);
                }
            }
        }

        //创建excel文件
        String path = System.getProperty("user.dir");
        path += "\\"+filename;
        System.out.println(path);
        File file=new File(path);
        try {
            file.createNewFile();
            //将excel写入
            FileOutputStream stream= new FileOutputStream(file);
            workbook.write(stream);
            String newFileName = new String((filename).getBytes("GBK"),
                    "ISO8859_1");
            response.setHeader("Content-Disposition", "attachment;filename="
                    + newFileName);
            workbook.write(response.getOutputStream());
            stream.close();
            // downLoad(path,response,false);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * 获得path的后缀名
     * @param path
     * @return
     */
    public static String getPostfix(String path){
        if(path==null || EMPTY.equals(path.trim())){
            return EMPTY;
        }
        if(path.contains(POINT)){
            return path.substring(path.lastIndexOf(POINT)+1,path.length());
        }
        return EMPTY;
    }
    /**
     * 单元格格式
     * @param hssfCell
     * @return
     */
    @SuppressWarnings({ "static-access" })
    public static String getHValue(HSSFCell hssfCell){
        if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(hssfCell.getBooleanCellValue());
        } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
            String cellValue = "";
            if(HSSFDateUtil.isCellDateFormatted(hssfCell)){
                Date date = HSSFDateUtil.getJavaDate(hssfCell.getNumericCellValue());
                cellValue = sdf.format(date);
            }else{
                DecimalFormat df = new DecimalFormat("#.##");
                cellValue = df.format(hssfCell.getNumericCellValue());
                String strArr = cellValue.substring(cellValue.lastIndexOf(POINT)+1,cellValue.length());
                if(strArr.equals("00")){
                    cellValue = cellValue.substring(0, cellValue.lastIndexOf(POINT));
                }
            }
            return cellValue;
        } else {
            return String.valueOf(hssfCell.getStringCellValue());
        }
    }
    /**
     * 单元格格式
     * @param xssfCell
     * @return
     */
    public static String getXValue(XSSFCell xssfCell){
        if (xssfCell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(xssfCell.getBooleanCellValue());
        } else if (xssfCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            String cellValue = "";
            if(XSSFDateUtil.isCellDateFormatted(xssfCell)){
                Date date = XSSFDateUtil.getJavaDate(xssfCell.getNumericCellValue());
                cellValue = sdf.format(date);
            }else{
                DecimalFormat df = new DecimalFormat("#.##");
                cellValue = df.format(xssfCell.getNumericCellValue());
                String strArr = cellValue.substring(cellValue.lastIndexOf(POINT)+1,cellValue.length());
                if(strArr.equals("00")){
                    cellValue = cellValue.substring(0, cellValue.lastIndexOf(POINT));
                }
            }
            return cellValue;
        } else {
            return String.valueOf(xssfCell.getStringCellValue());
        }
    }

    public void readExcel(@RequestParam(value="excel") MultipartFile file,
                          HttpServletRequest request,
                          HttpServletResponse response,
                          String[] parameter,String sql,Map map){
        String postfix = ExcelUtil.getPostfix(file.getOriginalFilename());
        if(!ExcelUtil.EMPTY.equals(postfix)){
            if(ExcelUtil.OFFICE_EXCEL_2003_POSTFIX.equals(postfix)){
//                try {
//                    importBizc.readXls(file,parameter,sql,map);
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
            }

        }

    }

    /**
     * read the Excel 2003-2007 .xls
     * @param file

     * @return
     * @throws IOException
     */
    public List readXls(MultipartFile file,String[] parameter) throws SQLException{
        // IO流读取文件
        ArrayList list = new ArrayList();
        InputStream input = null;
        HSSFWorkbook wb = null;
        try {
            input = file.getInputStream();
            // 创建文档
            wb = new HSSFWorkbook(input);
            //读取sheet(页)
            HSSFSheet hssfSheet = wb.getSheetAt(0);
            int totalRows = hssfSheet.getLastRowNum();
            //读取Row,从第二行开始
            for(int rowNum = 1;rowNum <= totalRows;rowNum++){
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                if(hssfRow!=null){
                    Map parMap = new HashMap();
                    for(int i=0;i<parameter.length;i++){

                        String ss = parameter[i];
                        String cells = hssfRow.getCell(i).toString();
                        parMap.put(ss, cells);

                    }
                    list.add(parMap);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }


}

class XSSFDateUtil extends DateUtil{
    protected static int absoluteDay(Calendar cal, boolean use1904windowing) {
        return DateUtil.absoluteDay(cal, use1904windowing);
    }

}
