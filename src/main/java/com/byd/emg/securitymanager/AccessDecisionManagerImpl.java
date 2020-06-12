package com.byd.emg.securitymanager;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

@Component("accessDecisionManager")
//Security需要用到一个实现了AccessDecisionManager接口的类
//类功能：根据当前用户的信息，和目标url涉及到的权限，判断用户是否可以访问
//判断规则：用户只要匹配到目标url权限中的一个role就可以访问
public class AccessDecisionManagerImpl implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        HttpServletRequest request=((FilterInvocation) object).getRequest();
        HttpServletResponse response=((FilterInvocation) object).getResponse();
        //迭代器遍历目标url的权限列表
        Iterator<ConfigAttribute> iterator = configAttributes.iterator();
        if (authentication instanceof AnonymousAuthenticationToken) {
            try {
                request.getRequestDispatcher("/user/nologin").forward(request, response);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        while (iterator.hasNext()) {
            ConfigAttribute ca = iterator.next();
            String needRole = ca.getAttribute();
            if ("ROLE_LOGIN".equals(needRole)) {
                if (authentication instanceof AnonymousAuthenticationToken) {
                    System.out.println("未登录");
                }else{
                    return;
                }
            }
            //遍历当前用户所具有的权限
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                if (authority.getAuthority().equals(needRole)) {
                    return;
                }
            }
        }
        String roleName=(String) request.getSession().getAttribute("roleName");
        if(!StringUtils.isEmpty(roleName)&&roleName.equals("管理员")){
            return;
        }else {
            //执行到这里说明没有匹配到应有权限,只允许系统管理员访问所有的资源
            throw new AccessDeniedException("权限不足!");
        }

    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
