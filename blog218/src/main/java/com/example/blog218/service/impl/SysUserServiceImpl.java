package com.example.blog218.service.impl;

import com.example.blog218.dao.mapper.SysUserMapper;
import com.example.blog218.dao.pojo.SysUser;
import com.example.blog218.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser findUserById(Long id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        if(sysUser == null){
            sysUser = new SysUser();
            sysUser.setNickname("情谊如烟");
        }
        return sysUserMapper.selectById(id);
    }
}
