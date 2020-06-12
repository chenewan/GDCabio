package com.byd.emg.service.impl;

import com.byd.emg.Util.DateTimeUtil;
import com.byd.emg.common.ServerResponse;
import com.byd.emg.resultData.ReportHistoryData;
import com.byd.emg.service.ShowReportService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;

@Service("iShowReport")
public class IShowReport implements ShowReportService {

    private final String PLAY_FILE_URL="D:/dist/export/excel/";

    @Override
    public List<ReportHistoryData> getReportHistory(String types,String start_time,String end_time) {
        File file = new File(PLAY_FILE_URL+types);
        List<ReportHistoryData> resultList=new ArrayList<ReportHistoryData>();
        // 获得该文件夹内的所有文件
        File[] array = file.listFiles();
        for(int i=0;i<array.length;i++) {
            if (array[i].isFile()) {//如果是文件
                //将文件名添加到集合中
                String fileName=array[i].getName();
                ReportHistoryData reportHistoryData=new ReportHistoryData();
                reportHistoryData.setReport(fileName);
                reportHistoryData.setUrl("/export/excel/"+types+"/"+fileName);
                int startIndex=fileName.lastIndexOf("_")+1;
                int endIndex=fileName.indexOf(".");
                String dateTime=fileName.substring(startIndex,endIndex);
                String createTime= DateTimeUtil.strToStr(dateTime,"yyMMddHHmmss","yyyy-MM-dd HH:mm:ss");
                reportHistoryData.setCreated(createTime);
                if(StringUtils.isEmpty(start_time)&&StringUtils.isEmpty(end_time)){
                    resultList.add(reportHistoryData);
                }else if(this.compareToDate(createTime,start_time)&&this.compareToDate(end_time,createTime)){
                    resultList.add(reportHistoryData);
                }

            }
        }
        return resultList;
    }

    //比较两个时间的前后(queryDate比dateDb小，就返回true)
    public boolean compareToDate(String dateDb, String queryDate){
        Date date_Db=null;
        Date paraDate=null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date_Db = sdf.parse(dateDb);
            paraDate = sdf.parse(queryDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(paraDate.compareTo(date_Db)<=0){
            return true;
        }else{
            return false;
        }
    }
}
