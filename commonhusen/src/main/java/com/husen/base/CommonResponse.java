package com.husen.base;

import java.io.Serializable;

/**
 * 通用响应
 * Created by HuSen on 2018/6/29 11:18.
 */
@SuppressWarnings("ALL")
public class CommonResponse<T> implements Serializable {
    private Integer code;
    private String msg;
    private T data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    CommonResponse<T> success(T data) {
        CommonResponse<T> commonResponse = new CommonResponse<>();
        commonResponse.setCode(200);
        commonResponse.setMsg("success");
        commonResponse.setData(data);
        return commonResponse;
    }

    CommonResponse<T> fail(T data) {
        CommonResponse<T> commonResponse = new CommonResponse<>();
        commonResponse.setCode(400);
        commonResponse.setMsg("fail");
        commonResponse.setData(data);
        return commonResponse;
    }

    CommonResponse<T> forbidden(T data) {
        CommonResponse<T> commonResponse = new CommonResponse<>();
        commonResponse.setCode(403);
        commonResponse.setMsg("forbidden");
        commonResponse.setData(data);
        return commonResponse;
    }

    CommonResponse<T> error(T data) {
        CommonResponse<T> commonResponse = new CommonResponse<>();
        commonResponse.setCode(500);
        commonResponse.setMsg("error");
        commonResponse.setData(data);
        return commonResponse;
    }

    CommonResponse<T> usernameExisted(T data){
        CommonResponse<T> commonResponse = new CommonResponse<>();
        commonResponse.setCode(401);
        commonResponse.setMsg("用户名已存在");
        commonResponse.setData(data);
        return commonResponse;
    }

    CommonResponse<T> paramException(T data){
        CommonResponse<T> commonResponse = new CommonResponse<>();
        commonResponse.setCode(402);
        commonResponse.setMsg("参数异常");
        commonResponse.setData(data);
        return commonResponse;
    }

    CommonResponse<T> nameExisted(T data){
        CommonResponse<T> commonResponse = new CommonResponse<>();
        commonResponse.setCode(401);
        commonResponse.setMsg("该名称已存在");
        commonResponse.setData(data);
        return commonResponse;
    }

    CommonResponse<T> notExisted(T data){
        CommonResponse<T> commonResponse = new CommonResponse<>();
        commonResponse.setCode(405);
        commonResponse.setMsg("相关数据不存在");
        commonResponse.setData(data);
        return commonResponse;
    }

    CommonResponse<T> existed(T data){
        CommonResponse<T> commonResponse = new CommonResponse<>();
        commonResponse.setCode(406);
        commonResponse.setMsg("相关数据已存在");
        commonResponse.setData(data);
        return commonResponse;
    }

    CommonResponse<T> def(T data) {
        CommonResponse<T> commonResponse = new CommonResponse<>();
        commonResponse.setCode(600);
        commonResponse.setMsg("未知类型");
        commonResponse.setData(data);
        return commonResponse;
    }

    public CommonResponse<T> authFail(T data) {
        CommonResponse<T> commonResponse = new CommonResponse<>();
        commonResponse.setCode(409);
        commonResponse.setMsg("认证失败");
        commonResponse.setData(data);
        return commonResponse;
    }

    @Override
    public String toString() {
        return "CommonResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
