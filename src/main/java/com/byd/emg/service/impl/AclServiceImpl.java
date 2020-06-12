package com.byd.emg.service.impl;

import com.byd.emg.mapper.AclMapper;
import com.byd.emg.pojo.Acl;
import com.byd.emg.service.AclService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("aclService")
public class AclServiceImpl implements AclService {

    @Autowired
    private AclMapper aclMapper;

    @Override
    //根据url查询对应的权限信息
    public Acl selectAclByUrl(String requestUrl) {
        return aclMapper.selectAclByUrl(requestUrl);
    }
}
