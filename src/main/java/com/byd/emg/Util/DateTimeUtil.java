package com.byd.emg.Util;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//时间管理的工具类
public class DateTimeUtil {

    public static String dateToStr(Date date, String formatStr){
        if(date == null){
            return StringUtils.EMPTY;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(formatStr);
    }

    public static Date strToDate(String dateTimeStr,String formatStr){
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(formatStr);
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateTimeStr);
        return dateTime.toDate();
    }

    public static String strToStr(String date,String dateTimeStr,String formatStr){
        Date date_2=strToDate(date,dateTimeStr);
        String now=dateToStr(date_2,formatStr);
        return now;
    }

    //根据cell类型设置数据(poi包下的)
    public static String getCellFormatValue(Cell cell) {
        String cellvalue = "";
        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC:// 如果当前Cell的Type为NUMERIC
                case Cell.CELL_TYPE_FORMULA: {
                    // 判断当前的cell是否为Date
                    if (DateUtil.isCellDateFormatted(cell)) {
                        // 如果是Date类型则，转化为Data格式
                        // data格式是带时分秒的：2013-7-10 0:00:00
                        // cellvalue = cell.getDateCellValue().toLocaleString();
                        // data格式是不带带时分秒的：2013-7-10
                        Date date = cell.getDateCellValue();
                        DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
                        cellvalue = formater.format(date);
                    } else {// 如果是纯数字
                        // 取得当前Cell的数值
                        DecimalFormat df = new DecimalFormat("0.000");
                        cellvalue = String.valueOf(df.format(cell.getNumericCellValue()));
                    }
                    break;
                }
                case Cell.CELL_TYPE_STRING:// 如果当前Cell的Type为STRING
                    // 取得当前的Cell字符串
                    cellvalue = cell.getRichStringCellValue().getString();
                    if(cellvalue.contains("/")){
                        //将"yyyy/MM/dd"转换为"yyyy-MM-dd"
                        String dateTimeStr="yyyy/MM/dd";
                        try{
                            String date=strToStr(cellvalue,dateTimeStr,"yyyy-MM-dd");
                            cellvalue=date;
                        }catch (Exception e){
                            break;
                        }
                    }
                    break;
                default:// 默认的Cell值
                    cellvalue = "";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;
    }

    //获取指定年指定月的最后一天的日期
    /**
     * 获取某月的最后一天
     * @Title:getLastDayOfMonth
     * @Description:
     * @param:@param year
     * @param:@param month
     * @param:@return
     * @return:String
     * @throws
     */
    public static String getLastDayOfMonth(int year,int month)
    {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR,year);
        //设置月份
        cal.set(Calendar.MONTH, month-1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String lastDayOfMonth = sdf.format(cal.getTime());
        return lastDayOfMonth;
    }

    /**
     * 秒转化为天小时分秒字符串
     *
     * @param minute
     * @return String
     */
    public static String formatMinute(String minute) {
        Long longMinute= Long.valueOf(minute) ;
        String timeStr = longMinute + "分";
        if (longMinute > 60) {
            long min = (longMinute ) % 60;
            long hour = (longMinute ) / 60;
            timeStr = hour + "小时" + min + "分" ;
            if (hour > 24) {
                hour = (longMinute  / 60) % 24;
                long day = ((longMinute  / 60) / 24);
                timeStr = day + "天" + hour + "小时" + min + "分" ;
            }
        }
        return timeStr;
    }
}
