package com.byd.emg.service;

import com.byd.emg.pojo.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

//框架需要使用到一个实现了UserDetailsService接口的类
public interface UserService extends UserDetailsService {

    public List<User> selectUserAll(String keyWord);

    public int insertUser(User user);

    public User selectByUser(User user);

    public int updateUser(User user);

    public int deleteUserById(Integer id);

    public User checkPassword(String password,String username);
}
