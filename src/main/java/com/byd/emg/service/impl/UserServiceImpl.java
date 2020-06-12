package com.byd.emg.service.impl;

import com.byd.emg.Util.MD5Util;
import com.byd.emg.mapper.UserMapper;
import com.byd.emg.pojo.Role;
import com.byd.emg.pojo.User;
import com.byd.emg.security.UserDetailsImpl;
import com.byd.emg.service.RoleService;
import com.byd.emg.service.RoleUserService;
import com.byd.emg.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
   private UserMapper userMapper;

   @Autowired
   private RoleService roleService;

    @Autowired
    private RoleUserService roleUserService;

    @Override
    //重写UserDetailsService接口里面的抽象方法
    //根据用户名 返回一个UserDetails的实现类的实例
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userMapper.findByUserName(username);
        if(user == null){
            throw new UsernameNotFoundException("没有该用户");
        }
        List<Role> roleList=roleService.selectByUserId(user.getId());
        //查到User后将其封装为UserDetails的实现类的实例供程序调用
        //用该User和它对应的Role实体们构造UserDetails的实现类
        return new UserDetailsImpl(user, roleList);
    }

    @Override
    public List<User> selectUserAll(String keyWord) {
        //用户id和角色id的映射关系
        Map<Integer,Integer> roleUserMap=roleUserService.selectRoleUser();
        //角色id和角色名的映射关系
        Map<Integer,String> roleMap=roleService.selectAll();
        List<Integer> userIds=new ArrayList<Integer>();
        if(!StringUtils.isEmpty(keyWord)) userIds=roleUserService.selectUserIdsByKey(keyWord);
        List<User> userList=userMapper.selectAll(keyWord,userIds);
        for(User user:userList){
            String roleName="";
            Integer roleId=roleUserMap.get(user.getId());
            if(roleId!=null) roleName=roleMap.get(roleId);
            user.setRolename(roleName);
        }
        return userList;
    }

    @Override
    public int insertUser(User user) {
        return userMapper.insertUser(user);
    }

    @Override
    public User selectByUser(User user) {
        return userMapper.selectByUser(user);
    }

    @Override
    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }

    @Override
    public int deleteUserById(Integer id) {
        return userMapper.deleteUserById(id);
    }

    @Override
    public User checkPassword(String password,String username) {
        //根据用户名和原密码查询对应的用户信息
        String saltPassword= MD5Util.MD5EncodeUtf8(password);
        User user=userMapper.selectUserByUsername(username,saltPassword);
        return user;
    }
}
