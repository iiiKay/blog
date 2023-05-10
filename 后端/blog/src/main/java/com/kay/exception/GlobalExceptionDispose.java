package com.kay.exception;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.kay.common.Result;
import com.kay.common.StatusCode;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

@RestController
@ControllerAdvice
public class GlobalExceptionDispose {

    @ExceptionHandler(IncorrectCredentialsException.class)
    public Result incorrectCredentialsException() {
        return new Result(StatusCode.fail, "密码错误!");
    }

    @ExceptionHandler(UnknownAccountException.class)
    public Result unknownAccountException(){
        return new Result(StatusCode.fail, "用户名不存在!");
    }

    @ExceptionHandler(TokenIsNullException.class)
    public Result tokenIsNullException(){
        return new Result(StatusCode.fail,"请先登录！");
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Result SQLIntegrityConstraintViolationException(){
        return new Result(StatusCode.fail,"用户名已占用");
    }

    @ExceptionHandler(TokenExpiredException.class)
    public Result TokenExpiredException(){
        return new Result(StatusCode.fail,"登录过期,请重新登录！");
    }

    @ExceptionHandler(JWTDecodeException.class)
    public Result JWTDecodeException(){
        return new Result(StatusCode.fail,"token不存在!");
    }
}
