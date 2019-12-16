package com.example.demo.result;

/**
 * @author 阳光
 * @version v1.0
 */

public class Result {
    //响应码
    //实际上由于响应码是固定的，code 属性应该是一个枚举值，这里作了一些简化。
    private int code;
    private String msg;


    public Result(int code) {
        this.code = code;
    }

    public Result(int code,String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(String msg){
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}

