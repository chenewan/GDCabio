package com.byd.emg.service.impl;

import com.byd.emg.mapper.ProductInfoMapper;
import com.byd.emg.pojo.ProductInfo;
import com.byd.emg.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("iProductInfo")
public class IProductInfo implements ProductInfoService {

    @Autowired
    private ProductInfoMapper productInfoMapper;

    @Override
    public Map<String, String> productInfoMap() {
        Map<String, String> resultMap=new HashMap<>();
        List<ProductInfo> productInfoList=productInfoMapper.selectAllProductInfo();
        for(ProductInfo product:productInfoList){
            resultMap.put(product.getCode(),product.getProduct());
        }
        return resultMap;
    }
}
