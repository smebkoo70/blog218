package com.example.blog218.service;

import com.example.blog218.dao.pojo.SysUser;
import com.example.blog218.vo.Result;
import com.example.blog218.vo.params.LoginParam;

public interface LoginService {

    /**
     * 登录功能
     * @param loginParam
     * @return
     */
    Result login(LoginParam loginParam);

    SysUser checkToken(String token);

    SysUser checkLogin(String token);

    Result logout(String token);

    Result register(LoginParam loginParam);
}
