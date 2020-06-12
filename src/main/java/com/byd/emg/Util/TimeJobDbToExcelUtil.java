package com.byd.emg.Util;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;

import java.util.List;
import java.util.Map;

public class TimeJobDbToExcelUtil {

    //导出的日期格式样式
    private static final String OUT_DATE_FORMART="yyyy-MM-dd";

    public static HSSFWorkbook timeJobDbToExcel(List<String> cabinet_numberList,
                                                             Map<String, Object> resultMap,
                                                             String sheetName) {
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
        //设置数据格式
        HSSFDataFormat df = wb.createDataFormat();
        textStyleLeft.setDataFormat(df.getBuiltinFormat("0.00"));//保留两位小数点
        HSSFCellStyle textRedStyleLeft = textStyleLeft(wb,curFontRedText);
        //设置正文内容右对齐的样式
        HSSFCellStyle textStyleRight = textStyleRight(wb,curFontText);
        HSSFCellStyle textRedStyleRight = textStyleRight(wb,curFontRedText);
        //设置正文内容居中对齐的样式
        HSSFCellStyle textStyleCenter = textStyleCenter(wb,curFontText);
        //设置日期格式的样式
        CreationHelper creationHelper = wb.getCreationHelper();
        HSSFCellStyle curStyleDate=createDateStyle(wb,creationHelper,curFontText,OUT_DATE_FORMART);

        //创建Sampling_period_5min的sheet
        HSSFSheet sampling_period_5minSheet = wb.createSheet(sheetName);
        convertExcel(cabinet_numberList,resultMap,sampling_period_5minSheet,curStyleTitle,textStyleCenter,curFontTitle);
        setSampling_period_5minContent(textStyleLeft,textStyleCenter,sampling_period_5minSheet,cabinet_numberList,resultMap);
        columnWidth(cabinet_numberList,sampling_period_5minSheet);
        return wb;
    }

    //设置ampling_period_5min内容的方法
    public static void setSampling_period_5minContent(HSSFCellStyle textStyleLeft,
                                                      HSSFCellStyle textStyleCenter,
                                                      HSSFSheet sheet,
                                                      List<String> cabinet_numberList,
                                                      Map<String, Object> resultMap){
        //控制行号、列号
        int rowNo = 1;  //第一行已设置了标题内容,从第二行开始
        int colNo = 0;
        //遍历并且创建行和列
        HSSFRow nRow = null;
        HSSFCell nCell = null;
        List<String[]> historyData=(List<String[]>)resultMap.get("historyData");
        for(String[] array:historyData){
            //每行从第一列写入
            colNo = 0;
            //每遍历一次创建一行
            nRow = sheet.createRow(rowNo++);
            //序号
            nCell = nRow.createCell(colNo++);
            nCell.setCellValue(rowNo - 1);
            nCell.setCellStyle(textStyleCenter);
            //时间
            nCell = nRow.createCell(colNo++);
            nCell.setCellValue(array[0]);
            nCell.setCellStyle(textStyleCenter);
            for(int index=1;index<array.length;index++){
                nCell = nRow.createCell(colNo++);
                nCell.setCellStyle(textStyleLeft);
                if(!StringUtils.isEmpty(array[index])) nCell.setCellValue(Double.valueOf(array[index]));

            }
        }
    }

    //设置Excel表格标题内容的方法
    public static  void convertExcel(List<String> cabinet_numberList,
                                     Map<String, Object> resultMap,
                                     HSSFSheet sheet,
                                     HSSFCellStyle curStyleTitle,
                                     HSSFCellStyle catalogTextStyleCenter,
                                     HSSFFont curFontTitle){
        //创建行列
        HSSFRow nRow = sheet.createRow(0);
        HSSFCell nCell = nRow.createCell(0);
        //设置表格标题样式的方法
        HSSFCellStyle curStyleTitle_1 = mainTitleStyle(curStyleTitle,curFontTitle);
        //柜体编号和柜体名称映射关系的集合
        Map<String,String> cabinetNameMap=(Map<String, String>) resultMap.get("cabinetNameMap");
        //设置标题到第一行
        nRow = sheet.createRow(0);
        //第一列标题
        nCell = nRow.createCell(0);
        nCell.setCellValue("序号");
        nCell.setCellStyle(curStyleTitle_1);
        //第二列标题
        nCell = nRow.createCell(1);
        nCell.setCellValue("时间");
        nCell.setCellStyle(curStyleTitle_1);
        for(int i = 0;i<cabinet_numberList.size();i++) {
            String  cabinet_number=cabinet_numberList.get(i);
            if(!StringUtils.isEmpty(cabinet_number)){
                nCell = nRow.createCell(i+2);
                nCell.setCellValue(cabinetNameMap.get(cabinet_number));
                //设置第一行标题的样式
                nCell.setCellStyle(curStyleTitle_1);
            }
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
    public static HSSFCellStyle createDateStyle(HSSFWorkbook wb, CreationHelper creationHelper, HSSFFont curFontText,String dataFormat){
        HSSFCellStyle curStyleDate=textStyleCommon(wb,curFontText);
        curStyleDate.setDataFormat(creationHelper.createDataFormat().getFormat(dataFormat));
        curStyleDate.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        return curStyleDate;
    }

    //设置列宽自适应
    public static void columnWidth(List<String> cabinet_numberList, HSSFSheet sheet){
        for(int i=1;i<cabinet_numberList.size()+2;i++){
            sheet.autoSizeColumn(i,true);
            //设置文本内容后边距
            sheet.setColumnWidth(i,(sheet.getColumnWidth(i)+5*256+184));
        }
    }

}
