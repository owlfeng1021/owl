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
import util.JwtUtil;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UsersService usersService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private JwtUtil jwtUtil;

    @PutMapping("/{userid}/{friendid}/{x}")
    public void updatefanscountandfollowcount(@PathVariable String userid, @PathVariable String friendid,@PathVariable int x){
        usersService.updatefanscountandfollowcount(x,userid,friendid);
    }
    @PostMapping("/login")
    public Result login(@RequestBody User user){
        user=  usersService.login(user.getMobile(),user.getPassword());
        if (user==null){
            return new Result(false, StatusCode.LOGINERROR, "登录失败" );
        }
        String token = jwtUtil.createJWT(user.getId(), user.getMobile(), "user");
        Map<String,String> map=new HashMap<>();
        map.put("token",token);
        map.put("roles","user");
        return new Result(true, StatusCode.OK, "登录成功" ,map);

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
    @DeleteMapping("/{adminId}")
    public Result delete(@PathVariable String adminId){
        usersService.delete(adminId);
        return new Result(true, StatusCode.OK, "删除成功" );
    }
}
