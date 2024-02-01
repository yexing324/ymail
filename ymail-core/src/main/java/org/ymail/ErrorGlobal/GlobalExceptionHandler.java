package org.ymail.ErrorGlobal;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.ymail.utils.Result;


/**
 * 全局异常处理器
 */
@Component
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public Result<String> handleException(Exception e, HttpServletResponse response) {
        log.error("全局异常信息：{}", e.getMessage(), e);
        return Result.failure(e.getMessage());
    }

}