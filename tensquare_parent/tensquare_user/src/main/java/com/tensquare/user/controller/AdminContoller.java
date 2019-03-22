package com.tensquare.user.controller;

import com.tensquare.user.pojo.Admin;
import com.tensquare.user.service.AdminService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import util.JwtUtil;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminContoller {
    @Autowired
    private AdminService adminService;
    @Autowired
    private JwtUtil jwtUtil;
    @PostMapping("/login")
    public Result login(@RequestBody Admin admin){

       Admin loginCheck =adminService.login(admin);
        if (loginCheck==null){
            return new Result(false, StatusCode.LOGINERROR, "登录失败" );

        }
        //使得前后端可以通话的 操作 使用 jwt 来实现

        String token = jwtUtil.createJWT(loginCheck.getId(), loginCheck.getLoginname(), "admin");
        Map<String,String> map =new HashMap<>();
        map.put("token",token);
        map.put("roles","admin");
        return new Result(true, StatusCode.OK, "登录成功",map );

    }
    @PostMapping("")
    public Result addAdmin(@RequestBody Admin admin){
        adminService.save(admin);
        return new Result(true, StatusCode.OK, "添加用户成功" );
    }
//    @DeleteMapping("/{adminId}")
//    public Result delete(@PathVariable String adminId){
//        adminService.delete(adminId);
//        return new Result(true, StatusCode.OK, "删除成功" );
//    }
}
