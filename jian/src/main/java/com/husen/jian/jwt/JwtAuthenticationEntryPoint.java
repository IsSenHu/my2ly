package com.husen.jian.jwt;

import com.alibaba.fastjson.JSONObject;
import com.husen.base.Base;
import com.husen.base.CommonResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * jwt未授权
 * Created by HuSen on 2018/9/21 11:21.
 */
@Component
public class JwtAuthenticationEntryPoint extends Base implements AuthenticationEntryPoint, Serializable {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        CommonResponse<String> result;
        //身份认证未通过
        if(authException instanceof BadCredentialsException){
            result = commonResponse("身份认证未通过", Constant.FORBIDDEN);
        }else{
            result = commonResponse("无效的token", Constant.FORBIDDEN);
        }
        response.getWriter().write(JSONObject.toJSONString(result));
    }
}
