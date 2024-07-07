package com.itheima.exception;

import com.itheima.pojo.Result;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理类，用于捕获并处理应用程序中抛出的异常。
 * 使用@RestControllerAdvice注解，将其作为一个全局的异常处理器，应用于所有@RestController注解的控制器。
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 捕获所有类型的异常。
     * 当控制器或其他处理方法抛出异常时，此方法将被调用，以便统一处理异常情况。
     *
     * @param e 抛出的异常对象，用于获取异常信息。
     * @return 返回一个Result对象，其中包含错误信息或操作失败的消息。
     */
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e){
        // 打印堆栈跟踪，以便于问题排查。
        e.printStackTrace();
        // 根据异常信息是否有内容，返回具体的错误信息或通用的“操作失败”信息。
        return Result.error(StringUtils.hasLength(e.getMessage()) ? e.getMessage() : "操作失败");
    }
}
