package com.yuji.polygon.advice;

import com.yuji.polygon.entity.APIException;
import com.yuji.polygon.entity.ResultCode;
import com.yuji.polygon.entity.ResultVO;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

/**
 * @className: ExceptionControllerAdvice
 * @description: 全局异常处理
 * @author: yuji
 * @create: 2020-08-05 22:49
 **/

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(APIException.class)
    public ResultVO<String> APIExceptionHandler(APIException e){
        return new ResultVO<>(ResultCode.FAILED,e.getMsg());
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultVO<String> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e){
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        return new ResultVO<>(ResultCode.VALIDATE_FAILED,objectError.getDefaultMessage());
    }

    //@Valid + @NotEmpty 校验不通过抛出BindException异常
    @ExceptionHandler(BindException.class)
    public ResultVO<String> BindExceptionHandler(BindException e){
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        return new ResultVO<>(ResultCode.VALIDATE_FAILED,objectError.getDefaultMessage());
    }

    @ExceptionHandler(SQLException.class)
    public ResultVO<String> SQLExceptionHandler(SQLException e){
        return new ResultVO<>(ResultCode.ERROR,e.getLocalizedMessage());
    }

    @ExceptionHandler(ArrayIndexOutOfBoundsException.class)
    public ResultVO<String> ArrayIndexOutOfBoundsException(ArrayIndexOutOfBoundsException e){
        return new ResultVO<>(ResultCode.FAILED,e.getLocalizedMessage());
    }

    @ExceptionHandler(ArithmeticException.class)
    public ResultVO<String> ArithmeticException(ArithmeticException e){
        return new ResultVO<>(ResultCode.FAILED,e.getLocalizedMessage());
    }

    @ExceptionHandler(ClassCastException.class)
    public ResultVO<String> ClassCastException(ClassCastException e){
        return new ResultVO<>(ResultCode.FAILED,e.getLocalizedMessage());
    }

}
