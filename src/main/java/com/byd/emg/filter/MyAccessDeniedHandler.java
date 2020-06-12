package com.byd.emg.filter;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("myAccessDeniedHandler")
//自定义403响应内容
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        request.getRequestDispatcher("/error/noauthorityError").forward(request, response);
       /* PrintWriter out = response.getWriter();
        Map resultMap=new HashMap();
        resultMap.put("success","error");
        resultMap.put("msg","没有访问权限，如需要访问，请联系管理员");
        resultMap.put("status", ResponseCode.NEED_ACCESS.getCode());
        out.write(JsonUtil.obj2String(resultMap));
        out.flush();
        out.close();//geelynote 这里要关闭*/

    }
}
