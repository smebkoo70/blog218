package com.example.blog218.Controller;


import com.example.blog218.service.LoginService;
import com.example.blog218.vo.Result;
import com.example.blog218.vo.params.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public Result login(@RequestBody LoginParam loginParam){

        //登录 验证用户 访问用户表，但是
        return loginService.login(loginParam);
    }
}
