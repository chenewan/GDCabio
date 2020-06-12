package com.byd.emg.controller;

import com.byd.emg.Util.DateTimeUtil;
import com.byd.emg.common.ServerResponse;
import com.byd.emg.pojo.Contractual_capacity;
import com.byd.emg.pojo.SectionAnalysis;
import com.byd.emg.pojo.Tax;
import com.byd.emg.pojo.TotalElectricityAddition;
import com.byd.emg.service.ReportFormService;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/ReportForm")
public class ReportFormController {
    @Autowired
    private ReportFormService reportFormService;

    //新增电单价配置
    @RequestMapping("/Electricity_fees")
    @ResponseBody
    public ServerResponse<Integer> Electricity_fees(
            @RequestParam("contractual_capacity") String contractual_capacity){
        List<Contractual_capacity> contractual_capacityList=new ArrayList<>();
        if(!StringUtils.isEmpty(contractual_capacity)) {
            JSONArray json = JSONArray.fromObject(contractual_capacity);
            if (json != null) {
                for (int index = 0; index < json.size(); index++) {
                    String Name = json.getJSONObject(index).get("name").toString();
                    String Contractual_capacity = json.getJSONObject(index).get("Contractual_capacity").toString();
                    String Price = json.getJSONObject(index).get("Price").toString();
                    String Applicable_time = json.getJSONObject(index).get("Applicable_time").toString();
                    contractual_capacityList.add(new Contractual_capacity(Name,Contractual_capacity,Price,Applicable_time));
                }
            }
        }
        String time=DateTimeUtil.dateToStr(new Date(),"HH:mm:ss");
       int result = reportFormService.insert_Contractual_capacity(contractual_capacityList,time);
        if (result> 0) {
            return ServerResponse.createBySuccess("添加电单价配置成功",result);
        }
        return ServerResponse.createByErrorMessage("添加电单价配置失败");
    }



    //新增或修改电单价立约容量
    @RequestMapping("/electricityFeesUpdate")
    @ResponseBody
    public ServerResponse<Integer> electricityFeesUpdate(Contractual_capacity contractualCapacity ){
        if(contractualCapacity.getId()!=null){
            int updateCount= reportFormService.electricityFeesUpdate(contractualCapacity);
            if(updateCount>0){
                return ServerResponse.createBySuccessMessage("修改立约容量成功");
            }
            return ServerResponse.createByErrorMessage("修改立约容量失败");
        }else{
            int insertCount= reportFormService.addElectricityFees(contractualCapacity);
            if(insertCount>0){
                return ServerResponse.createBySuccessMessage("新增立约容量成功");
            }
            return ServerResponse.createByErrorMessage("新增立约容量失败");
        }


    }

    //查询电单价立约容量录入记录
    @RequestMapping("/electricityFeesSelect")
    @ResponseBody
    public ServerResponse electricityFeesSelect(){
        List<Contractual_capacity> contractual_capacity= reportFormService.electricityFeesSelect();
        if(contractual_capacity.size()>0){
            return ServerResponse.createBySuccess("查询电单价配置成功",contractual_capacity);
        }
        return ServerResponse.createByErrorMessage("查询电单价配置失败");
    }

    //删除电单价配置
    @RequestMapping("/electricityFeesDelete")
    @ResponseBody
    public ServerResponse electricityFeesDelete(Long id){
        int electricityFeesDelete= reportFormService.electricityFeesDelete(id);
        if(electricityFeesDelete==1){
            return ServerResponse.createBySuccessMessage("删除电单价配置成功");
        }
        return ServerResponse.createByErrorMessage("删除电单价配置失败");

    }

    //新增及更新电税率
    @RequestMapping("/Electricity_tax")
    @ResponseBody
    public ServerResponse<Integer> tax(Tax tax){
        if(tax.getId()==null){//新增
            int result = reportFormService.tax(tax);
            if (result> 0) {
                return ServerResponse.createBySuccess("添加电单价成功",result);
            }
            return ServerResponse.createByErrorMessage("添加电单价失败");
        }else{//编辑
            int updateCount=reportFormService.updateTax(tax);
            if(updateCount>0){
                return ServerResponse.createBySuccessMessage("修改电单价成功");
            }
            return ServerResponse.createByErrorMessage("修改电单价失败");
        }
    }

    //电税率的历史记录(默认显示最新的记录)
    @RequestMapping("/taxRecords")
    @ResponseBody
    public ServerResponse taxRecords(String start_time){
        List<Tax> taxList=reportFormService.taxRecords(start_time);
        if(taxList.size()>0){
            return ServerResponse.createBySuccess("查询成功",taxList);
        }
        return ServerResponse.createByErrorMessage("删除失败");
    }

    //删除电税率(根据id)
    @RequestMapping("/delTaxById")
    @ResponseBody
    public ServerResponse<Integer> delTaxById(Long id){
        int delCount=reportFormService.delTaxById(id);
        if(delCount>0){
            return ServerResponse.createBySuccessMessage("删除成功");
        }
        return ServerResponse.createByErrorMessage("删除失败");
    }

    //新增平目录贷征、工业贷征电单价
   @RequestMapping("/Electricity_Addition")
    @ResponseBody
    public ServerResponse<Integer> Total_Electricity_Addition(@RequestParam("Addition") String Addition,String type){
        List<TotalElectricityAddition> additionList=new ArrayList<>();
        if(!StringUtils.isEmpty(Addition)) {
            JSONArray json = JSONArray.fromObject(Addition);
            if (json != null) {
                for (int index = 0; index < json.size(); index++) {
                    String Name = json.getJSONObject(index).get("name").toString();
                    String Unit_Price = json.getJSONObject(index).get("Contractual_capacity").toString();
                    String proportion=json.getJSONObject(index).get("Price").toString();
                    String Start_time = json.getJSONObject(index).get("Applicable_time").toString();
                    String number = json.getJSONObject(index).get("number").toString();
                    additionList.add(new TotalElectricityAddition(Name,Unit_Price,proportion,Start_time,Integer.valueOf(number)));
            }
            }
        }
       int result  = reportFormService.insertAddition(additionList,type);
       if (result> 0) {
            return ServerResponse.createBySuccess("添加成功",result);
        }
        return ServerResponse.createByErrorMessage("添加失败");
    }

    //修改平目录贷征、工业贷征电单价
    @RequestMapping("/Electricity_Update")
    @ResponseBody
    public ServerResponse<Integer> Total_Electricity_Update(TotalElectricityAddition totalElectricityAddition ){
        int updateCount= reportFormService.Total_Electricity_Update(totalElectricityAddition);
        if(updateCount==1){
            return ServerResponse.createBySuccessMessage("修改水单价成功");
        }
            return ServerResponse.createByErrorMessage("修改水单价失败");
    }

    //查询平目录贷征、工业贷征电单价
    @RequestMapping("/Electricity_Select")
    @ResponseBody
    public ServerResponse Total_Electricity_Select(String searchTime,String type){

        List<TotalElectricityAddition> totalElectricityAdditionList= reportFormService.Total_Electricity_Select(searchTime,type);
        if(totalElectricityAdditionList.size()>0){
            return ServerResponse.createBySuccess("查询成功",totalElectricityAdditionList);
        }
        return ServerResponse.createByErrorMessage("查询失败");

    }

    //删除平目录贷征、工业贷征电单价
    @RequestMapping("/Electricity_Delete")
    @ResponseBody
    public ServerResponse Total_Electricity_Delete(Long id){
        int total_Electricity_Delete= reportFormService.Total_Electricity_Delete(id);
        if(total_Electricity_Delete==1){
            return ServerResponse.createBySuccessMessage("删除平目录贷征、工业贷征电单价成功");
        }
        return ServerResponse.createByErrorMessage("删除平目录贷征、工业贷征电单价失败");

    }

    //葛店工厂变动电价
    @RequestMapping("/Total_Electricity_ReportForm")
    @ResponseBody
    public ServerResponse Total_Electricity_ReportForm(@RequestParam("Applicable_time") String Applicable_time){
        String year= DateTimeUtil.dateToStr(DateTimeUtil.strToDate(Applicable_time,"yyyy-MM"),"yyyy");
        if(Integer.parseInt(year)<2019){
            return ServerResponse.createByErrorMessage("报表生成年限仅限于2019年以后");
        }
        ServerResponse response = reportFormService.power_price(Applicable_time,"");
        String msg="报表生成成功";
        if(response.getStatus().equals("SUCCESS")){
            if(!StringUtils.isEmpty(response.getMsg())) msg=response.getMsg();
            return ServerResponse.createBySuccess(msg,response.getData());
        }else{
            return ServerResponse.createByErrorMessage(response.getMsg());
        }
    }

    //葛店工厂变动电价预览
    @RequestMapping("/Total_Electricity_ReportForm_preview")
    @ResponseBody
    public ServerResponse Total_Electricity_ReportForm_preview(@RequestParam("Applicable_time") String Applicable_time){
        String year= DateTimeUtil.dateToStr(DateTimeUtil.strToDate(Applicable_time,"yyyy-MM"),"yyyy");
        if(Integer.parseInt(year)<2019){
            return ServerResponse.createByErrorMessage("报表生成年限仅限于2019年以后");
        }
        ServerResponse response = reportFormService.power_price_pdf(Applicable_time,"pdf");
        if(response.getStatus().equals("SUCCESS")){
            return ServerResponse.createBySuccess(response.getMsg(),response.getData());
        }else{
            return ServerResponse.createByErrorMessage(response.getMsg());
        }
    }

    //葛店工段能耗报表
    @RequestMapping("/energy_consumption")
    @ResponseBody
    public ServerResponse energy_consumption(@RequestParam("Start_t") String Start_t,
                                              @RequestParam("End_t") String End_t ){
        String year= DateTimeUtil.dateToStr(DateTimeUtil.strToDate(Start_t,"yyyy-MM-dd"),"yyyy");
        if(Integer.parseInt(year)<2019){
            return ServerResponse.createByErrorMessage("报表生成年限仅限于2019年以后");
        }
        ServerResponse response = reportFormService.energy_consumption(Start_t,End_t,"");
        if(response.getStatus().equals("SUCCESS")){
            return ServerResponse.createBySuccess("报表生成成功",response.getData());
        }else{
            return ServerResponse.createByErrorMessage(response.getMsg());
        }
    }

    //葛店工段能耗报表预览
    @RequestMapping("/energy_consumption_preview")
    @ResponseBody
    public ServerResponse energy_consumption_preview(@RequestParam("Start_t") String Start_t,
                                                     @RequestParam("End_t") String End_t ){
        String year= DateTimeUtil.dateToStr(DateTimeUtil.strToDate(Start_t,"yyyy-MM-dd"),"yyyy");
        if(Integer.parseInt(year)<2019){
            return ServerResponse.createByErrorMessage("报表生成年限仅限于2019年以后");
        }
        ServerResponse response = reportFormService.energy_consumption_pdf(Start_t,End_t,"pdf");
        if(response.getStatus().equals("SUCCESS")){
            return ServerResponse.createBySuccess(response.getMsg(),response.getData());
        }else{
            return ServerResponse.createByErrorMessage(response.getMsg());
        }
    }

    //工厂能耗报表
    @RequestMapping("/finally_report")
    @ResponseBody
    public ServerResponse  finally_report(@RequestParam("Start_t") String Start_t,
                                                  @RequestParam("End_t") String End_t,
                                                  @RequestParam("type") String type){
        String year= DateTimeUtil.dateToStr(DateTimeUtil.strToDate(Start_t,"yyyy-MM-dd"),"yyyy");
        if(Integer.parseInt(year)<2019){
            return ServerResponse.createByErrorMessage("报表生成年限仅限于2019年以后");
        }
        ServerResponse response= reportFormService.finally_report(Start_t,End_t,type,"");
        String msg="报表生成成功";
        if(response.getStatus().equals("SUCCESS")){
            if(!StringUtils.isEmpty(response.getMsg())) msg=response.getMsg();
            return ServerResponse.createBySuccess(response.getMsg(),response.getData());
        }else{
            return ServerResponse.createByErrorMessage(response.getMsg());
        }
    }

    //工厂能耗报表预览
    @RequestMapping("/finally_report_preview")
    @ResponseBody
    public ServerResponse  finally_report_preview(@RequestParam("Start_t") String Start_t,
                                                  @RequestParam("End_t") String End_t,
                                                  @RequestParam("type") String type){
        String year= DateTimeUtil.dateToStr(DateTimeUtil.strToDate(Start_t,"yyyy-MM-dd"),"yyyy");
        if(Integer.parseInt(year)<2019){
            return ServerResponse.createByErrorMessage("报表生成年限仅限于2019年以后");
        }
        ServerResponse response = reportFormService.finally_report_pdf(Start_t,End_t,type,"pdf");
        if(response.getStatus().equals("SUCCESS")){
            return ServerResponse.createBySuccess(response.getMsg(),response.getData());
        }else{
            return ServerResponse.createByErrorMessage(response.getMsg());
        }
    }

    //动力成本
    @RequestMapping("/powerCost")
    @ResponseBody
    public ServerResponse powerCost(@RequestParam("Start_t") String Start_t,
                                     @RequestParam("End_t") String End_t){
        String year= DateTimeUtil.dateToStr(DateTimeUtil.strToDate(Start_t,"yyyy-MM-dd"),"yyyy");
        if(Integer.parseInt(year)<2019){
            return ServerResponse.createByErrorMessage("报表生成年限仅限于2019年以后");
        }
        ServerResponse response = reportFormService.powerCost(Start_t,End_t,"");
        if(response.getStatus().equals("SUCCESS")){
            return ServerResponse.createBySuccess(response.getMsg(),response.getData());
        }else{
            return ServerResponse.createByErrorMessage(response.getMsg());
        }
    }

    //动力成本预览
    @RequestMapping("/powerCost_preview")
    @ResponseBody
    public ServerResponse powerCost_preview(@RequestParam("Start_t") String Start_t,
                                            @RequestParam("End_t") String End_t){
        String year= DateTimeUtil.dateToStr(DateTimeUtil.strToDate(Start_t,"yyyy-MM-dd"),"yyyy");
        if(Integer.parseInt(year)<2019){
            return ServerResponse.createByErrorMessage("报表生成年限仅限于2019年以后");
        }
        ServerResponse response = reportFormService.powerCost_pdf(Start_t,End_t);
        if(response.getStatus().equals("SUCCESS")){
            return ServerResponse.createBySuccess(response.getMsg(),response.getData());
        }else{
            return ServerResponse.createByErrorMessage(response.getMsg());
        }
    }

    //动力总费用报表
    @RequestMapping("/totalCost")
    @ResponseBody
    public ServerResponse totalCost(@RequestParam("Start_t") String Start_t,
                                            @RequestParam("End_t") String End_t){
        String year= DateTimeUtil.dateToStr(DateTimeUtil.strToDate(Start_t,"yyyy-MM-dd"),"yyyy");
        if(Integer.parseInt(year)<2019){
            return ServerResponse.createByErrorMessage("报表生成年限仅限于2019年以后");
        }
        ServerResponse response = reportFormService.totalCost(Start_t,End_t);
        if(response.getStatus().equals("SUCCESS")){
            return ServerResponse.createBySuccess("报表生成成功",response.getData());
        }else{
            return ServerResponse.createByErrorMessage(response.getMsg());
        }
    }

    //动力总费用报表预览
    @RequestMapping("/totalCost_preview")
    @ResponseBody
    public ServerResponse totalCost_preview(@RequestParam("Start_t") String Start_t,
                                                    @RequestParam("End_t") String End_t){
        String year= DateTimeUtil.dateToStr(DateTimeUtil.strToDate(Start_t,"yyyy-MM-dd"),"yyyy");
        if(Integer.parseInt(year)<2019){
            return ServerResponse.createByErrorMessage("报表生成年限仅限于2019年以后");
        }
        ServerResponse response = reportFormService.totalCost_pdf(Start_t,End_t);
        if(response.getStatus().equals("SUCCESS")){
            return ServerResponse.createBySuccess("预览成功",response.getData());
        }else{
            return ServerResponse.createByErrorMessage(response.getMsg());
        }
    }

    //蒸汽单位成本报表
    @RequestMapping("/unitCostSteam")
    @ResponseBody
    public ServerResponse unitCostSteam(@RequestParam("Start_t") String Start_t,
                                            @RequestParam("End_t") String End_t){
        String year= DateTimeUtil.dateToStr(DateTimeUtil.strToDate(Start_t,"yyyy-MM-dd"),"yyyy");
        if(Integer.parseInt(year)<2019){
            return ServerResponse.createByErrorMessage("报表生成年限仅限于2019年以后");
        }
        ServerResponse response = reportFormService.unitCostSteam(Start_t,End_t);
        if(response.getStatus().equals("SUCCESS")){
            return ServerResponse.createBySuccess("报表生成成功",response.getData());
        }else{
            return ServerResponse.createByErrorMessage(response.getMsg());
        }
    }

    //蒸汽单位成本报表预览
    @RequestMapping("/unitCostSteam_preview")
    @ResponseBody
    public ServerResponse unitCostSteam_preview(@RequestParam("Start_t") String Start_t,
                                                    @RequestParam("End_t") String End_t){
        String year= DateTimeUtil.dateToStr(DateTimeUtil.strToDate(Start_t,"yyyy-MM-dd"),"yyyy");
        if(Integer.parseInt(year)<2019){
            return ServerResponse.createByErrorMessage("报表生成年限仅限于2019年以后");
        }
        ServerResponse response = reportFormService.unitCostSteam_pdf(Start_t,End_t);
        if(response.getStatus().equals("SUCCESS")){
            return ServerResponse.createBySuccess("预览成功",response.getData());
        }else{
            return ServerResponse.createByErrorMessage(response.getMsg());
        }
    }

}