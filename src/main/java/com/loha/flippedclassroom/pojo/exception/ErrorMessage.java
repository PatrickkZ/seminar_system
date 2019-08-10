package com.loha.flippedclassroom.pojo.exception;

/**
 * 当发生异常时向前端传递信息
 *
 * @author zhoujian
 * @date 2018/12/16
 */
public class ErrorMessage {
    private String code;
    private String message;

    public ErrorMessage(){}

    public ErrorMessage(String code,String message){
        this.code=code;
        this.message=message;
    }
}
