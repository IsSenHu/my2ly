package com.husen.jian.exception;

import org.springframework.validation.FieldError;

import java.util.List;

/**
 * 参数异常
 * Created by HuSen on 2018/7/3 17:01.
 */
public class ParamException extends Exception {
    private List<FieldError> errors;

    public ParamException(List<FieldError> errors) {
        this.errors = errors;
    }

    public ParamException(String message, List<FieldError> errors) {
        super(message);
        this.errors = errors;
    }

    public List<FieldError> getErrors() {
        return errors;
    }
}
