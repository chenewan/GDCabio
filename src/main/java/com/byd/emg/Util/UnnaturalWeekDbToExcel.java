package com.byd.emg.Util;

import com.byd.emg.pojo.UnnaturalWeek;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;

import java.util.List;

public class UnnaturalWeekDbToExcel {

    public static HSSFWorkbook bugrecordsToExcel(String[] titleArray, List<UnnaturalWeek> unnaturalWeekList) {
        //创建工作薄
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFCellStyle curStyleTitle = wb.createCellStyle();
        //设置标题行的背景色
        curStyleTitle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        curStyleTitle.setFillForegroundColor(HSSFColor.TURQUOISE.index);
        HSSFFont curFontTitle = wb.createFont();
        HSSFFont curFontText = wb.createFont();
        //设置红色字体的样式
        HSSFFont curFontRedText = wb.createFont();
        curFontRedText.setColor(Font.COLOR_RED);
        //设置正文内容左对齐的样式
        HSSFCellStyle textStyleLeft = textStyleLeft(wb,curFontText);
        HSSFCellStyle textRedStyleLeft = textStyleLeft(wb,curFontRedText);
        //设置正文内容右对齐的样式
        HSSFCellStyle textStyleRight = textStyleRight(wb,curFontText);
        HSSFCellStyle textRedStyleRight = textStyleRight(wb,curFontRedText);
        //设置正文内容居中对齐的样式
        HSSFCellStyle textStyleCenter = textStyleCenter(wb,curFontText);
        //设置日期格式的样式
        CreationHelper creationHelper = wb.getCreationHelper();
        HSSFCellStyle curStyleDate=createDateStyle(wb,creationHelper,curFontText);

        //创建收款单的sheet
        HSSFSheet bugrecordsSheet = wb.createSheet();
        convertExcel(titleArray,bugrecordsSheet,curStyleTitle,textStyleCenter,curFontTitle);
        setbugrecordsListContent(textStyleLeft,textStyleCenter,curStyleDate,bugrecordsSheet,unnaturalWeekList);
        columnWidth(titleArray,bugrecordsSheet);
        return wb;
    }


    public static void setbugrecordsListContent(HSSFCellStyle textStyleLeft,
                                                 HSSFCellStyle textStyleCenter,
                                                 HSSFCellStyle curStyleDate,
                                                 HSSFSheet sheet,
                                                List<UnnaturalWeek> unnaturalWeekList){
        //控制行号、列号
        int rowNo = 1;  //第一行已设置了标题内容,从第二行开始
        int colNo = 0;
        //遍历并且创建行和列
        HSSFRow nRow = null;
        HSSFCell nCell = null;
        for(UnnaturalWeek unnaturalWeek:unnaturalWeekList) {
            //每行从第一列写入
            colNo = 0;
            //每遍历一次创建一行
            nRow = sheet.createRow(rowNo++);
            //序号
            nCell = nRow.createCell(colNo++);
            nCell.setCellValue(rowNo - 1);
            nCell.setCellStyle(textStyleCenter);

            //产品类型
            nCell = nRow.createCell(colNo++);
            nCell.setCellValue(unnaturalWeek.getProductType());
            nCell.setCellStyle(textStyleLeft);

            //用水量
            nCell = nRow.createCell(colNo++);
            nCell.setCellValue(unnaturalWeek.getWater());
            nCell.setCellStyle(textStyleLeft);
            //用电量
            nCell = nRow.createCell(colNo++);
            nCell.setCellValue(unnaturalWeek.getElectricity());
            nCell.setCellStyle(textStyleLeft);
            //用气
            nCell = nRow.createCell(colNo++);
            nCell.setCellValue(unnaturalWeek.getSteam());
            nCell.setCellStyle(textStyleLeft);

            //时间
            nCell = nRow.createCell(colNo++);
            nCell.setCellValue(unnaturalWeek.getStartDate());
            nCell.setCellStyle(textStyleLeft);
        }
    }

    //设置Excel表格标题内容的方法
    public static  void convertExcel(String [] columnTitle,
                                     HSSFSheet sheet,
                                     HSSFCellStyle curStyleTitle,
                                     HSSFCellStyle catalogTextStyleCenter,
                                     HSSFFont curFontTitle){
        //创建行列
        HSSFRow nRow = sheet.createRow(0);
        HSSFCell nCell = nRow.createCell(0);
        //设置表格标题样式的方法
        HSSFCellStyle curStyleTitle_1 = mainTitleStyle(curStyleTitle,curFontTitle);
        //设置标题到第一行
        nRow = sheet.createRow(0);
        for(int i = 0;i<columnTitle.length;i++) {
            nCell = nRow.createCell(i);
            nCell.setCellValue(columnTitle[i]);
            //设置第一行标题的样式
            nCell.setCellStyle(curStyleTitle_1);
        }
    }

    //设置表格标题样式的方法
    public static HSSFCellStyle mainTitleStyle(HSSFCellStyle curStyleTitle, HSSFFont curFontTitle) {
        //设置列的样式
        curStyleTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);	//水平居中
        curStyleTitle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
        curStyleTitle.setWrapText(true); // 自动换行
        curFontTitle.setFontName("等线");	//字体
        curFontTitle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 加粗
        curFontTitle.setFontHeightInPoints((short) 11);//字体大小
        curStyleTitle.setFont(curFontTitle); // 绑定关系
        return curStyleTitle;
    }

    //设置表格内容居中对齐样式的方法
    public static HSSFCellStyle textStyleCenter(HSSFWorkbook wb, HSSFFont curFontText){
        HSSFCellStyle cellStyleCenter=textStyleCommon(wb,curFontText);
        cellStyleCenter.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        return cellStyleCenter;
    }

    //设置表格内容左对齐样式的方法
    public static HSSFCellStyle textStyleLeft(HSSFWorkbook wb,HSSFFont curFontText){
        HSSFCellStyle cellStyleLeft=textStyleCommon(wb,curFontText);
        cellStyleLeft.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        return cellStyleLeft;
    }

    //设置表格内容右对齐样式的方法
    public static HSSFCellStyle textStyleRight(HSSFWorkbook wb,HSSFFont curFontText){
        HSSFCellStyle cellStyleRight=textStyleCommon(wb,curFontText);
        cellStyleRight.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        return cellStyleRight;
    }

    //表格内容样式公共的方法
    public static HSSFCellStyle textStyleCommon(HSSFWorkbook wb,HSSFFont curFontText) {
        //创建正文部分的样式和字体
        HSSFCellStyle curStyleText = wb.createCellStyle();
        curStyleText.setAlignment(HSSFCellStyle.ALIGN_LEFT);//水平居中
        curStyleText.setWrapText(true); // 自动换行
        curStyleText.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
        curFontText.setFontName("等线");//字体
        curFontText.setFontHeightInPoints((short) 11);//字体大小
        curStyleText.setFont(curFontText); // 绑定关系
        return curStyleText;
    }

    //设置日期格式
    public static HSSFCellStyle createDateStyle(HSSFWorkbook wb, CreationHelper creationHelper, HSSFFont curFontText){
        HSSFCellStyle curStyleDate=textStyleCommon(wb,curFontText);
        curStyleDate.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy/MM/dd"));
        curStyleDate.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        return curStyleDate;
    }

    //设置列宽自适应
    public static void columnWidth(String [] titleArr, HSSFSheet sheet){
        for(int i=1;i<titleArr.length;i++){
            sheet.autoSizeColumn(i,true);
            //设置文本内容后边距
            sheet.setColumnWidth(i,(sheet.getColumnWidth(i)+5*256+184));
        }
    }


}
