package com.byd.emg.service;

import com.byd.emg.pojo.PowerCompensation;

import java.util.List;

public interface IPowerCompensation {

    public void batchIsert(List<PowerCompensation> powerCompensationList,String his_date,String his_time);


}
