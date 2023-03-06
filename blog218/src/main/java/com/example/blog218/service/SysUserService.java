package com.example.blog218.service;

import com.example.blog218.dao.pojo.SysUser;
import com.example.blog218.vo.Result;

public interface SysUserService {

    SysUser findUserById(Long id);

    SysUser findUser(String account, String password);

    /**
     * 根据token查询用户信息
     * @param token
     * @return
     */
    Result findUserByToken(String token);
}
