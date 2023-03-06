package com.example.blog218.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.blog218.dao.mapper.SysUserMapper;
import com.example.blog218.dao.pojo.SysUser;
import com.example.blog218.service.LoginService;
import com.example.blog218.service.SysUserService;
import com.example.blog218.vo.ErrorCode;
import com.example.blog218.vo.LoginUserVo;
import com.example.blog218.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private LoginService loginService;

    @Override
    public SysUser findUserById(Long id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        if(sysUser == null){
            sysUser = new SysUser();
            sysUser.setNickname("情谊如烟");
        }
        return sysUserMapper.selectById(id);
    }

    @Override
    public SysUser findUser(String account,String password)
    {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount,account);
        queryWrapper.eq(SysUser::getPassword,password);
        queryWrapper.select(SysUser::getAccount,SysUser::getId,SysUser::getAvatar,SysUser::getNickname);
        queryWrapper.last("limit 1");

        return sysUserMapper.selectOne(queryWrapper);
    }

    @Override
    public Result findUserByToken(String token) {

        /**
         * 1.token合法性校验
         *   是否为空，解析是否成功，redis是否存在
         * 2.如果校验失败，返回错误（登陆失败？）
         * 3.如果成功，返回对应的结果 LoginUserVo
         */

        // SysUser sysUser =  loginService.checkLogin(token);

        SysUser sysUser =  loginService.checkToken(token);
        if(sysUser == null)
        {
            Result.fail(ErrorCode.TOKEN_ERROR.getCode(),ErrorCode.TOKEN_ERROR.getMsg());
        }

        LoginUserVo loginUserVo = new LoginUserVo();
        loginUserVo.setId(sysUser.getId());
        loginUserVo.setNickname(sysUser.getNickname());
        loginUserVo.setAvatar(sysUser.getAvatar());
        loginUserVo.setAccount(sysUser.getAccount());

        //SysUser sysUser = loginService.checkToken(token);
        return Result.success(loginUserVo);
    }

}

