package com.tensquare.friend.controller;

import com.tensquare.friend.client.UserClient;
import com.tensquare.friend.service.FriendService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/friend")
public class FriendController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private FriendService friendService;
    @Autowired
    private UserClient userClient;

    @PutMapping("/like/{friendid}/{type}")
    public Result addFirend(@PathVariable String friendid, @PathVariable String type) {
        //验证是否登录 并且拿到当前登录的用户id
        Claims claims = (Claims) request.getAttribute("claims_user");
        String id = claims.getId();
        if (claims == null) {
            return new Result(false, StatusCode.ACCESSERROR, "权限不足 ！");
        }
        //判断是好友还是非好友
        if (type != null) {
            if (type.equals("1")) { //添加好友
                int flag = friendService.addFriend(id, friendid);
                if (flag == 1) {
                    userClient.updatefanscountandfollowcount(id, friendid, 1);
                    return new Result(true, StatusCode.OK, "添加成功");
                }
                if (flag == 0) {
                    return new Result(false, StatusCode.ERROR, "不能重复添加好友 ");
                }
            } else if (type.equals("2")) {                 //添加非好友
                int flag = friendService.addNoFriend(id, friendid);
                if (flag == 1) {
                    return new Result(true, StatusCode.OK, "添加非好友成功");
                }
                if (flag == 0) {
                    return new Result(false, StatusCode.ERROR, "已经移除过了 ");
                }
            } else {
                return new Result(true, StatusCode.OK, "添加成功");
            }
        }

        return new Result(false, StatusCode.ERROR, "不能重复添加好友 ");
    }

    @DeleteMapping("/{friendid}")
    public Result deleteFriend(@PathVariable String friendid) {
        //验证是否登录 并且拿到当前登录的用户id
        Claims claims = (Claims) request.getAttribute("claims_user");
        String id = claims.getId();
        if (claims == null) {
            return new Result(false, StatusCode.ACCESSERROR, "权限不足 ！");
        }
        String userid = claims.getId();
        friendService.deleteFriend(userid,friendid);
        userClient.updatefanscountandfollowcount(userid,friendid,-1)
        ;


        return new Result(false, StatusCode.ERROR, "已经删除了 ");
    }
}
