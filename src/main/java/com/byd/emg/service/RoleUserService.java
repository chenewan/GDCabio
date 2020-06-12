package com.byd.emg.service;

import com.byd.emg.pojo.RoleUser;

import java.util.List;
import java.util.Map;

public interface RoleUserService {

    public Map<Integer, Integer> selectRoleUser();

    public int insertRoleUser(RoleUser roleUser);

    public int updateRoleUser(RoleUser roleUser);

    public int deleteRoleUserByUserId(Integer userId);

    public List<Integer> selectUserIdsByKey(String keyWord);
}
