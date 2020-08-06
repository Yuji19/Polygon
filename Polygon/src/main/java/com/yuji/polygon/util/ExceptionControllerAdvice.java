package com.yuji.polygon.util;/**
 * Created by Enzo Cotter on 2020/8/5.
 */

import com.yuji.polygon.entity.APIException;
import com.yuji.polygon.entity.ResultCode;
import com.yuji.polygon.entity.ResultVO;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @program: polygon
 * @description:
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
}
