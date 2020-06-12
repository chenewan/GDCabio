package com.byd.emg.service;

import com.byd.emg.pojo.Role;

import java.util.List;
import java.util.Map;

public interface RoleService {

    public List<Role> selectByUserId(Integer id);

    public List<Role> getRoleListByAclId(Integer aclId);

    public List<String> selectByUsername(String username);

    public Map<Integer, String> selectAll();

    public List<String> selectAllRoleName();

    public Integer selectIdByRoleName(String rolename);
}
