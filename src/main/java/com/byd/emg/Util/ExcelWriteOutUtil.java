package com.byd.emg.Util;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;

//将转换好后的Excel写入服务器的工具类
public class ExcelWriteOutUtil {
    //xls格式的文件写入流
    public static void excelToOutputstrame(HSSFWorkbook wb, String outFile){
            //将excel写入流
        FileOutputStream out=null;
        try{
            String filePath="D:/dist/export/excel";
            //String filePath="D:/dist/cabio/export/excel";
            File fileTemp=new File(filePath);
            //文件夹不存在就创建
            if(!fileTemp.exists()){
                fileTemp.mkdirs();
            }
            File file=new File(filePath+"/"+outFile);
            //文件不存在就创建
            if(!file.exists()) {
                file.createNewFile();
            }
            out=new FileOutputStream(file);
            wb.write(out);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(out!=null) out.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    //xlsx格式的文件写入流
    public static void excelToOutputstrame(XSSFWorkbook wb, String outFile){
        //将excel写入流
        FileOutputStream out=null;
        try{
            String filePath="D:/dist/export/excel";
            //String filePath="D:/dist/cabio/export/excel";
            File fileTemp=new File(filePath);
            //文件夹不存在就创建
            if(!fileTemp.exists()){
                fileTemp.mkdirs();
            }
            File file=new File(filePath+"/"+outFile);
            //文件不存在就创建
            if(!file.exists()) {
                file.createNewFile();
            }
            out=new FileOutputStream(file);
            wb.write(out);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(out!=null) out.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }



}
