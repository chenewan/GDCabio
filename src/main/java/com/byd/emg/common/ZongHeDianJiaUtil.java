package com.byd.emg.common;

import com.byd.emg.Util.DateTimeUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

public class ZongHeDianJiaUtil {

    public static void main(String[] args) throws Exception {
        //getPricePowerByExcel("D:\\dist\\export\\excel\\bianDongDianJia\\2019年06月葛店工厂变动电价_190719094058.xlsx");
        String a="请输入对应时间段内的单价errcode"+"/export/excel/dongLiChengBen/";
        int index=a.indexOf("errcode");
        System.out.println(a.substring(0,index));
        System.out.println(a.substring(index+7));
    }
    public static String getPricePowerByExcel(String address) {
        String ext = address.substring(address.lastIndexOf("."));
        File insertFile=null;
        String unitPricePower="";
        try {
            Workbook rwb=null;
            insertFile=new File(address);
            InputStream is = new FileInputStream(insertFile);
            if(".xls".equals(ext)){
                rwb = new HSSFWorkbook(is);
            }else if(".xlsx".equals(ext)){
                rwb = new XSSFWorkbook(is);
            }

            Sheet sheet = rwb.getSheetAt(0);
            int clos=sheet.getRow(0).getPhysicalNumberOfCells();//得到所有的列
            int rows=sheet.getPhysicalNumberOfRows();//得到所有的行
            Row row=sheet.getRow(2);
            //第一个是列数，第二个是行数
            unitPricePower= DateTimeUtil.getCellFormatValue(row.getCell(5));//默认最左边编号也算一列 所以这里得j++
//            if(insertFile!=null) insertFile.delete();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return unitPricePower;
    }
}
