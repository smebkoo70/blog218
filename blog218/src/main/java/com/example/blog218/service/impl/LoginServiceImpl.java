package com.example.blog218.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.blog218.dao.pojo.SysUser;
import com.example.blog218.service.LoginService;
import com.example.blog218.service.SysUserService;
import com.example.blog218.utils.JWTUtils;
import com.example.blog218.vo.ErrorCode;
import com.example.blog218.vo.Result;
import com.example.blog218.vo.params.LoginParam;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.apache.commons.codec.digest.DigestUtils.md5Hex;
import static org.springframework.util.DigestUtils.*;

@Service
@Transactional
public class LoginServiceImpl implements LoginService {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private RedisTemplate<String,String> redisTemplate ;

    private static final String slat = "mszlu!@#";

    @Override
    public Result login(LoginParam loginParam) {

        /**
         * 1.检查参数是否合法
         * 2.根据用户名密码去user表查询，是否存在
         * 3.如果不存在，登陆失败
         * 4.如果存在，使用JWT生成token 返回给前端
         * 5.token放入redis redis token:user信息 设置过期时间 登录认证先验证token字符串是否合法,然后去redis验证是否存在
         *
         */
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        if(StringUtils.isBlank(account) || StringUtils.isBlank(password))
        {
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }

        //加密盐，做测试的时候最好注释掉，不然密码是真的麻烦。


        //password = md5Hex(password + slat);

        SysUser sysUser =  sysUserService.findUser(account, password);
        if(sysUser == null)
        {
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(), ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }

        String token = JWTUtils.createToken(sysUser.getId());
        redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);
        //sysUser.setToken(token);
        System.out.println("登录成功");
        return Result.success(token);
    }

    @Override
    public SysUser checkToken(String token) {
        if(StringUtils.isBlank(token))
        {
            //return Result.fail(ErrorCode.TOKEN_ERROR.getCode(),ErrorCode.TOKEN_ERROR.getMsg());
            return null;
        }
        Map<String,Object> stringObjectMap = JWTUtils.checkToken(token);
         if(stringObjectMap == null)
         {
             return null;
         }
         String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);
         if(StringUtils.isBlank(userJson))
         {
             return null;
         }
        SysUser sysUser = JSON.parseObject(userJson, SysUser.class);
        return sysUser;
    }

    @Override
    public SysUser checkLogin(String token) {

        return null;
    }


    /**
     * 退出登录
     * @param token
     * @return
     */
    @Override
    public Result logout(String token) {
        redisTemplate.delete("TOKEN_"+token);
        return Result.success(null);
    }

    @Override
    public Result register(LoginParam loginParam) {
        /**
         * 1.判断参数是否合法
         * 2.判断账户是否存在，返回账户已经被注册（存在的话）
         * 3.如果账户不存在，注册用户
         * 4.生成token
         * 5.存入redis，并返回
         * 6.注意：加上事务，一旦中间任何过程出现问题，注册的用户就需要回滚
         */
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        String nickname = loginParam.getNickname();
        if (StringUtils.isBlank(account)
                || StringUtils.isBlank(password)
                || StringUtils.isBlank(nickname)
        ){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMsg());
        }
        SysUser sysUser = this.sysUserService.findUserByAccount(account);
        if (sysUser != null){
            return Result.fail(ErrorCode.ACCOUNT_EXIST.getCode(),ErrorCode.ACCOUNT_EXIST.getMsg());
        }
        sysUser = new SysUser();
        sysUser.setNickname(nickname);
        sysUser.setAccount(account);
        /**
         * md5加盐
         */
        //sysUser.setPassword(DigestUtils.md5Hex(password+slat));
        //sysUser.setPassword(md5Hex(password+slat));
        sysUser.setPassword(password);
        sysUser.setCreateDate(System.currentTimeMillis());
        sysUser.setLastLogin(System.currentTimeMillis());
        sysUser.setAvatar("/static/img/logo.b3a48c0.png");
        sysUser.setAdmin(1); //1 为true
        sysUser.setDeleted(0); // 0 为false
        sysUser.setSalt("");
        sysUser.setStatus("");
        sysUser.setEmail("");
        this.sysUserService.save(sysUser);

        //token
        String token = JWTUtils.createToken(sysUser.getId());

        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);
        return Result.success(token);
    }
}
