package com.byd.emg.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleAclMapper {

    public List<Integer> getRoleIdListByAclId(@Param("aclId") Integer aclId);
}
