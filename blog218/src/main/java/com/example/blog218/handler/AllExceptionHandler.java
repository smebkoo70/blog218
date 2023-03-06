package com.example.blog218.handler;

import com.example.blog218.vo.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//对家了@controller注解的方法进行拦截处理，AOP的实现
@ControllerAdvice
public class AllExceptionHandler {

    //进行异常处理，处理excepition.class
    @ExceptionHandler(Exception.class)
    @ResponseBody //返回json格式
    public Result doException(Exception ex)
    {
        ex.printStackTrace();
        return Result.fail(-999,"系统异常");
        //return new Result(500,"服务器异常");
    }
}
