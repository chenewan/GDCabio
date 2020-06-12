package com.byd.emg.service.impl;

import com.byd.emg.mapper.RoleUserMapper;
import com.byd.emg.pojo.RoleUser;
import com.byd.emg.service.RoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("iRoleUser")
public class IRoleUser implements RoleUserService {

    @Autowired
    private RoleUserMapper roleUserMapper;


    @Override
    public Map<Integer, Integer> selectRoleUser() {
        Map<Integer, Integer> resultMap=new HashMap<>();
        List<RoleUser>  roleUserList=roleUserMapper.selectAll();
        for(RoleUser roleUser:roleUserList){
            resultMap.put(roleUser.getUserid(),roleUser.getRoleid());
        }
        return resultMap;
    }

    @Override
    public int insertRoleUser(RoleUser roleUser) {
        return roleUserMapper.insertRoleUser(roleUser);
    }

    @Override
    public int updateRoleUser(RoleUser roleUser) {
        return roleUserMapper.updateRoleUser(roleUser);
    }

    @Override
    public int deleteRoleUserByUserId(Integer userId) {
        return roleUserMapper.deleteRoleUserByUserId(userId);
    }

    @Override
    public List<Integer> selectUserIdsByKey(String keyWord) {
        return roleUserMapper.selectUserIdsByKey(keyWord);
    }
}
