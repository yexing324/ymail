package org.ymail.ErrorGlobal;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.ymail.util.Result;
import org.springframework.validation.BindException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.stream.Collectors;
/**
 * 全局异常处理器
 */
@Component
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public Result<Void> handleException(Exception e, HttpServletResponse response) {
        e.printStackTrace();
        log.error("全局异常信息：{}", e.getMessage(), e);
        return Result.failure(e.getMessage());
    }

    @ExceptionHandler(BindException.class)
    @ResponseBody
    public Result<Void> BindExceptionHandler(BindException e) {
        String message = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(","));
        return Result.failure(message,"400");
    }

}