package com.husen.jian.controller;

import com.husen.base.Base;
import com.husen.base.CommonResponse;
import com.husen.jian.exception.ParamException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by HuSen on 2018/9/18 9:37.
 */
@RestController
@Slf4j
public class BasicController extends Base {
    @ExceptionHandler
    public CommonResponse<Object> exp(HttpServletRequest request, Exception e){
        e.printStackTrace();
        log.info("requestURI:[{}]", request.getRequestURI());
        if(e instanceof ParamException){
            ParamException paramException = (ParamException) e;
            return commonResponse(paramException.getErrors(), Constant.PARAM_EXCEPTION);
        }else {
            return commonResponse(e.getMessage(), Constant.ERROR);
        }
    }
}
