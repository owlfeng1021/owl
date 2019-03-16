package com.tensquare.user.controller;

import com.tensquare.user.pojo.User;
import com.tensquare.user.service.UsersService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UsersService usersService;
    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/login")
    public Result login(@RequestBody User user){
        user=  usersService.login(user.getMobile(),user.getPassword());
        if (user==null){
            return new Result(false, StatusCode.LOGINERROR, "登录失败" );
        }
        return new Result(true, StatusCode.OK, "登录成功" );

    }
    @PostMapping("/sendsms/{mobile}")
    public Result sendsms(@PathVariable String mobile){
        usersService.sendSms(mobile);
        return new Result(true, StatusCode.OK ,"发送成功");

    }
    @PostMapping("/register/{code}")
    public Result regist(@PathVariable String code, @RequestBody User user){
        String checkCode=(String)redisTemplate.opsForValue().get("mobile_"+user.getMobile());
        if (checkCode.isEmpty()){
            return new Result(false, StatusCode.ERROR ,"验证码为空");
        }
        if (!checkCode.equals(code)){
            return new Result(false, StatusCode.ERROR ,"验证码错误");
        }
        usersService.add(user);
        return new Result(true, StatusCode.OK ,"注册成功");
    }
}
