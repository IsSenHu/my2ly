package com.husen.base;

import com.husen.config.security.WebSecurityConstant;
import com.husen.dao.po.UserPo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Map;

/**
 * Created by HuSen on 2018/7/5 13:18.
 */
@SuppressWarnings("WeakerAccess")
public class Base {

    public static <T> CommonResponse<T> commonResponse(T data, String result) {
        CommonResponse<T> commonResponse = new CommonResponse<>();
        switch (result){
            case Constant.SUCCESS : return commonResponse.success(data);
            case Constant.FAIL : return commonResponse.fail(data);
            case Constant.FORBIDDEN : return commonResponse.forbidden(data);
            case Constant.ERROR : return commonResponse.error(data);
            case Constant.USERNAME_EXISTED : return commonResponse.usernameExisted(data);
            case Constant.NAME_EXISTED : return commonResponse.nameExisted(data);
            case Constant.PARAM_EXCEPTION : return commonResponse.paramException(data);
            case Constant.NOT_EXISTED : return commonResponse.notExisted(data);
            case Constant.EXISTED : return commonResponse.existed(data);
            default : return commonResponse.def(data);
        }
    }

    public final static class Constant{
        public static final String SUCCESS = "success";
        public static final String FAIL = "fail";
        public static final String FORBIDDEN = "forbidden";
        public static final String ERROR = "error";
        public static final String USERNAME_EXISTED = "usernameExisted";
        public static final String PARAM_EXCEPTION = "paramException";
        public static final String NAME_EXISTED = "nameExisted";
        public static final String NOT_EXISTED = "notExisted";
        public static final String EXISTED = "existed";

        public static final String DATE_PATTERN_ONE = "yyyy-MM-dd";
    }

    public static LocalDate getLocalDate(String pattern, String string) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.parse(string).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * 获取已认证的用户信息
     * @return 认证的用户信息
     */
    public static UserPo getUser(){
        SecurityContext securityContext;
        Authentication authentication;
        Object principal;
        if((securityContext = SecurityContextHolder.getContext()) == null
                || (authentication = securityContext.getAuthentication()) == null || (principal = authentication.getPrincipal()) == null) {
            return null;
        }else {
            return (UserPo) principal;
        }
    }

    public static void AllUsersPermissionChanged() {
        Map<Long, Boolean> ifModifiedMap = WebSecurityConstant.USER_PERMISSION_IF_MODIFIED_MAP;
        ifModifiedMap.keySet().forEach(userId -> ifModifiedMap.put(userId, Boolean.TRUE));
    }
}
