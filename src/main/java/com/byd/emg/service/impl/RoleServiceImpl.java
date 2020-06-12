package com.byd.emg.service.impl;

import com.byd.emg.mapper.RoleAclMapper;
import com.byd.emg.mapper.RoleMapper;
import com.byd.emg.pojo.Role;
import com.byd.emg.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleAclMapper roleAclMapper;

    @Override
    //根据用户的Id查询该用户所拥有的角色的集合
    public List<Role> selectByUserId(Integer userid) {
        return roleMapper.selectByUserId(userid);
    }

    @Override
    //根据权限id查询角色的集合
    public List<Role> getRoleListByAclId(Integer aclId) {
        List<Integer> roleIdList = roleAclMapper.getRoleIdListByAclId(aclId);
        if (CollectionUtils.isEmpty(roleIdList)) {
            return new ArrayList<Role>();
        }
        return roleMapper.getByIdList(roleIdList);
    }

    @Override
    public List<String> selectByUsername(String username) {
        return roleMapper.selectByUsername(username);
    }

    @Override
    public Map<Integer, String> selectAll() {
        Map<Integer, String> resultMap=new HashMap<>();
        List<Role> roleList=roleMapper.selectAll();
        for(Role role:roleList){
            resultMap.put(role.getId(),role.getRolename());
        }
        return resultMap;
    }

    @Override
    public List<String> selectAllRoleName() {
        return roleMapper.selectAllRoleName();
    }

    @Override
    public Integer selectIdByRoleName(String rolename) {
        return roleMapper.selectIdByRoleName(rolename);
    }
}
