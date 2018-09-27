package com.husen.base;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;

/**
 * Created by HuSen on 2018/7/5 13:18.
 */
@SuppressWarnings("ALL")
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
            case Constant.AUTH_FAIL : return commonResponse.authFail(data);
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
        public static final String AUTH_FAIL = "authFail";
        public static final String DATE_PATTERN_ONE = "yyyy-MM-dd";
    }

    public static LocalDate getLocalDate(String pattern, String string) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.parse(string).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
