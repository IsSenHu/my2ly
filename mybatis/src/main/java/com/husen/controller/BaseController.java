package com.husen.controller;

import com.husen.base.Base;
import com.husen.base.CommonResponse;
import com.husen.exception.ParamException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by HuSen on 2018/6/29 11:16.
 */
@RestController
public class BaseController extends Base {
    private static final Logger log = LoggerFactory.getLogger(BaseController.class);

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
