package com.example.blog218.Controller;

import com.example.blog218.dao.pojo.SysUser;
import com.example.blog218.utils.UserThreadLocal;
import com.example.blog218.vo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

    @RequestMapping
    public Result test(){

        SysUser sysUser = UserThreadLocal.get();
        System.out.println(sysUser);
        return Result.success(null);
    }


}
