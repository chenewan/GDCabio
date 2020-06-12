package com.byd.emg.mapper;

import com.byd.emg.pojo.Acl;
import org.apache.ibatis.annotations.Param;

public interface AclMapper {

    public Acl selectAclByUrl(@Param("requestUrl") String requestUrl);
}
