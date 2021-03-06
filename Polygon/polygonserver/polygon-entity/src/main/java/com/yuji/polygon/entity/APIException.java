package com.yuji.polygon.entity;

/**
 * @className: APIException
 * @description:
 * @author: yuji
 * @create: 2020-08-05 22:50
 **/

public class APIException extends RuntimeException {

    private int code;

    private String msg;

    public APIException(String msg) {
        this.code = 1001;
        this.msg = msg;
    }

    public APIException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
