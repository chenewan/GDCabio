package com.byd.emg.mapper;

import com.byd.emg.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserMapper {

    //根据用户名查询用户信息
    public User findByUserName(@Param("username") String username);

    public List<User> selectAll(@Param("keyWord") String keyWord,@Param("userIds") List<Integer> userIds);

    public int insertUser(User user);

    public User selectByUser(User user);

    public int updateUser(User user);

    public int deleteUserById(@Param("id") Integer id);

    public String getPasswordById(@Param("id") Integer id);

    public User selectUserByUsername(@Param("username") String username,@Param("saltPassword") String saltPassword);
}
