package com.byd.emg.controller;

import com.byd.emg.Util.DateTimeUtil;
import com.byd.emg.common.ServerResponse;
import com.byd.emg.pojo.Instrument;
import com.byd.emg.service.InstrumentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.util.Calendar.MONTH;

@RestController
@RequestMapping("instrument")
public class InstrumentController {

    @Autowired
    private InstrumentService iInstrument;

    @RequestMapping("instrumentAll")
    public ServerResponse instrumentAll(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize",defaultValue = "20") int pageSize,
                                        String key,String way) {
        List<Instrument> instrumentList=new ArrayList<>();
        PageHelper.startPage(pageNum,pageSize);
        instrumentList=iInstrument.instrumentAll(key,way);
        if(instrumentList.size()>0){
            PageInfo<Instrument>  pageInfo=new PageInfo<Instrument>(instrumentList);
            return  ServerResponse.createBySuccess("查询成功",pageInfo);
        }
        return  ServerResponse.createBySuccess("查询失败",instrumentList);
    }

    //下拉选
    @RequestMapping("/getTagnameOrDescribe")
    public ServerResponse getTagnameOrDescribe(String key) {
        List<Instrument> instrumentList=new ArrayList<Instrument>();
        instrumentList=iInstrument.getTagnameOrDescribe(key);
        if(instrumentList.size()>0){
            return  ServerResponse.createBySuccess("查询成功",instrumentList);
        }
        return  ServerResponse.createBySuccess("查询失败",instrumentList);
    }

    @RequestMapping("addOrUpdateInstrument")
    public ServerResponse addOrUpdateInstrument(Instrument instrument) {
        String updatetime= DateTimeUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss");
        String endDate=this.addMonth(instrument.getStartDate());
        endDate=this.subDay(endDate);
        instrument.setEndDate(endDate);
        instrument.setUpdatetime(updatetime);
        Instrument instrumentDb=iInstrument.selectByInstrument(instrument);
        if(instrumentDb!=null) instrument.setId(instrumentDb.getId());
       if(instrument.getId()!=null){//编辑
           int updateCount=0;
           updateCount=iInstrument.updateInstrumentById(instrument);
           if(updateCount>0){
               return  ServerResponse.createBySuccess("修改成功");
           }
           return  ServerResponse.createByErrorMessage("修改失败");
       }else{//新增
           int insertCount=0;
           insertCount=iInstrument.insertInstrument(instrument);
           if(insertCount>0){
               return  ServerResponse.createBySuccess("新增成功");
           }
           return  ServerResponse.createByErrorMessage("新增失败");
       }
    }

    //删除手输记录
    @RequestMapping("/deleteById")
    public ServerResponse deleteById(int id) {
        int deleteCount=0;
        deleteCount=iInstrument.deleteById(id);
        if(deleteCount>0){
            return  ServerResponse.createBySuccessMessage("删除成功");
        }
        return  ServerResponse.createByErrorMessage("查询失败");
    }

    //将传入的日期加上一个月
    public String addMonth(String dateDb){
        Date dateParameter=null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            dateParameter = sdf.parse(dateDb);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dateParameter);
        rightNow.add(MONTH,1);
        return sdf.format(rightNow.getTime());
    }

    //将传入的日期减去一天
    public String subDay(String dateDb){
        Date dateParameter=null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            dateParameter = sdf.parse(dateDb);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dateParameter);
        rightNow.add(Calendar.DAY_OF_MONTH,-1);
        return sdf.format(rightNow.getTime());
    }







}
