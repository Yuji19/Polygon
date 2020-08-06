package com.yuji.polygon.entity;/**
 * Created by Enzo Cotter on 2020/8/5.
 */

/**
 * @program: polygon
 * @description:
 * @author: yuji
 * @create: 2020-08-05 22:54
 **/

public class ResultVO<T> {

    private int code;

    private String msg;

    private T data;

    public ResultVO(T data){
        this.code = ResultCode.SUCCESS.getCode();
        this.msg = ResultCode.SUCCESS.getMsg();
        this.data = data;
    }

    public ResultVO(ResultCode resultCode, T data){
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }
}
