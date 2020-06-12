package com.byd.emg.securitymanager;

import com.byd.emg.pojo.Acl;
import com.byd.emg.pojo.Role;
import com.byd.emg.service.AclService;
import com.byd.emg.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component("filterInvocationSecurityMetadataSource")
//接收用户请求的地址，返回访问该地址需要的所有权限
public class FilterInvocationSecurityMetadataSourceImpl implements FilterInvocationSecurityMetadataSource {


    @Autowired
    private AclService aclService;

    @Autowired
    private RoleService roleService;

    @Override
    //接收用户请求的地址，返回访问该地址需要的所有权限
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        //得到用户的请求地址
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        if(requestUrl.contains("?")){
            requestUrl=requestUrl.substring(0,requestUrl.indexOf("?"));
        }

        //如果登录页面就不需要权限
        if ("/user/login".equals(requestUrl)) {
            return null;
        }
        Acl acl = aclService.selectAclByUrl(requestUrl);
        //如果没有匹配的url则说明大家都可以访问
        if(acl==null) {
            return SecurityConfig.createList("ROLE_LOGIN");
        }

        //将resource所需要到的roles按框架要求封装返回（ResourceService里面的getRoles方法是基于RoleRepository实现的）
        List<Role> roles = roleService.getRoleListByAclId(acl.getId());
        int size = roles.size();
        if(roles.size()==0) {
            return SecurityConfig.createList("ROLE_LOGIN");
        }
        String[] values = new String[size];
        for (int i = 0; i < size; i++) {
            values[i] = roles.get(i).getRolename();
        }
        return SecurityConfig.createList(values);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }
}
