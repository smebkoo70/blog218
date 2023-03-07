package com.example.blog218.utils;

import com.example.blog218.dao.pojo.SysUser;

public class UserThreadLocal {

    //线程变量隔离
    private UserThreadLocal()
    {

    }

    private static final ThreadLocal<SysUser> LOCAL = new ThreadLocal<>();

    public static void put(SysUser sysUser)
    {
        LOCAL.set(sysUser);
    }

    public static SysUser get()
    {
        return LOCAL.get();
    }

    public static void remove()
    {
        LOCAL.remove();
    }

}