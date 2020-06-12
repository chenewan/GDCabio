package com.byd.emg.mapper;

import com.byd.emg.pojo.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {

    public List<Role> selectByUserId(@Param("userid") Integer userid);

    public List<Role> getByIdList(@Param("roleIdList") List<Integer> roleIdList);

    public List<String> selectByUsername(@Param("username") String username);

    public List<Role> selectAll();

    public List<String> selectAllRoleName();

    public Integer selectIdByRoleName(@Param("rolename") String rolename);
}
