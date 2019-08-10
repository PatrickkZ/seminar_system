package com.loha.flippedclassroom.exception;

import com.loha.flippedclassroom.pojo.exception.CanNotRegisterException;
import com.loha.flippedclassroom.pojo.exception.ErrorMessage;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 *
 * @author zhoujian
 * @date 2018/12/22
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CanNotRegisterException.class)
    @ResponseBody
    public ErrorMessage userNotExistExceptionHandler(CanNotRegisterException e){
        return new ErrorMessage("404",e.getMessage());
    }

}
