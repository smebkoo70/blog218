package com.example.blog218.handler;

import com.alibaba.fastjson.JSON;
import com.example.blog218.dao.pojo.SysUser;
import com.example.blog218.service.LoginService;
import com.example.blog218.utils.UserThreadLocal;
import com.example.blog218.vo.ErrorCode;
import com.example.blog218.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //在执行controller方法（handler）之前执行进行
        /**
         * 1.需要判断，请求的借口路径，是否为handler的方法（controller方法）
         * 2.判断token是否为空，如果为空，未登录
         * 3.如果token不为空，登陆验证  loginservice ckecktoken
         * 4.如果认证成功，就可以执行
         */
        if (!(handler instanceof HandlerMethod)){
            //handler 可能是 requestresourcehandler springboot 程序访问静态资源 默认区classpath下的static目录去查询
            return true;
        }
        String token = request.getHeader("Authorization");
        log.info("=================request start===========================");
        String requestURI = request.getRequestURI();
        log.info("request uri:{}",requestURI);
        log.info("request method:{}",request.getMethod());
        log.info("token:{}", token);
        log.info("=================request end===========================");

        if (token == null){
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), "未登录");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        SysUser sysUser = loginService.checkToken(token);
        if (sysUser == null){
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), "未登录");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        //是登录状态，放行

        //希望在controller中直接获取用户信息怎么获取？
        UserThreadLocal.put(sysUser);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //如果不删除 threadlocal中用完的信息 会有内存泄漏的风险
        UserThreadLocal.remove();
    }

}