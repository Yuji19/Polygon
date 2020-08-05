package com.yuji.polygon.entity;/**
 * Created by Enzo Cotter on 2020/8/5.
 */

/**
 * @program: polygon
 * @description:
 * @author: yuji
 * @create: 2020-08-05 22:50
 **/

public class APIException extends RuntimeException {

    private int code;

    private String msg;

    public APIException(int code, String msg){
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
