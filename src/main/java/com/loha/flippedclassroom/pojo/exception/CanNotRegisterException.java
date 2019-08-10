package com.loha.flippedclassroom.pojo.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * 报名讨论课时某次序已经被报名
 *
 * @author zhoujian
 * @date 2018/12/16
 */
@Getter
@Setter
public class CanNotRegisterException extends Exception{
    private String message;

    public CanNotRegisterException(){
        message="该次序已经被其他小组报名";
    }
}
