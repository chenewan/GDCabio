package com.byd.emg.service.impl;

import com.byd.emg.mapper.RealValueTankBatchNoMapper;
import com.byd.emg.pojo.RealValueTankBatchNo;
import com.byd.emg.service.RealValueTankBatchNoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("realValueTankBatchNoService")
public class IRealValueTankBatchNoService implements RealValueTankBatchNoService {

    @Autowired
    private RealValueTankBatchNoMapper realValueTankBatchNoMapper;

    @Override
    public Map<String, List<RealValueTankBatchNo>> screenRealValue() {
        Map<String, List<RealValueTankBatchNo>> resultMap=new HashMap<String, List<RealValueTankBatchNo>>();
        List<RealValueTankBatchNo> startStateList=new ArrayList<RealValueTankBatchNo>();
        List<RealValueTankBatchNo> endStateList=new ArrayList<RealValueTankBatchNo>();
        List<RealValueTankBatchNo> allBatchNo=realValueTankBatchNoMapper.selectAllRealValue();
        for(RealValueTankBatchNo realValue:allBatchNo){
            if(realValue.getState().equals("开始")){
                startStateList.add(realValue);
            }else if(realValue.getState().equals("结束")){
                endStateList.add(realValue);
            }
        }
        resultMap.put("startState",startStateList);
        resultMap.put("endState",endStateList);
        return resultMap;
    }
}
