package com.byd.emg.mapper;

import com.byd.emg.pojo.RoleUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleUserMapper {

    public List<RoleUser> selectAll();

    public int insertRoleUser(RoleUser roleUser);

    public int updateRoleUser(RoleUser roleUser);

    public int deleteRoleUserByUserId(@Param("userId") Integer userId);

    public List<Integer> selectUserIdsByKey(@Param("keyWord") String keyWord);
}
