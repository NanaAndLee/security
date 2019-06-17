package com.lee.web.controller;

import com.lee.exception.UserNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(UserNotExistException.class)//凡是UserNotExistException都会进入该方法进行处理
    @ResponseBody//返回值转JSON
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)//这里指定服务器内部错误，对应状态码为 500
    public Map<String, Object> handleUserNotExistException(UserNotExistException ex){
        Map<String, Object> result = new HashMap<>(  );
        result.put( "id", ex.getId() );
        result.put( "message", ex.getMessage() );
        return result;
    }
}
