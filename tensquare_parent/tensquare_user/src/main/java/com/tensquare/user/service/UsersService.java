package com.tensquare.user.service;

import com.tensquare.user.dao.UserDao;
import com.tensquare.user.pojo.User;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class UsersService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    private JwtUtil jwtUtil;

    public void sendSms(String mobile) {
        String checkcode = RandomStringUtils.randomNumeric(6);
        //把手机号作为key 随机码作为value
        redisTemplate.opsForValue().set("mobile_" + mobile, checkcode, 6, TimeUnit.HOURS);
        Map<String, String> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("checkcode", checkcode);
        // 发送一份到消息队列里面 然后由消息队列发送短信 验证码
//        rabbitTemplate.convertAndSend("sms",map);


        System.out.println(map.get("mobile") + " " + map.get("checkcode"));


    }

    public void add(User user) {
        user.setId(idWorker.nextId() + "");
        user.setPassword(encoder.encode(user.getPassword()));
        user.setFollowcount(0);//关注数
        user.setFanscount(0);//粉丝数
        user.setOnline(0L);//在线时长
        user.setRegdate(new Date());//注册日期
        user.setUpdatedate(new Date());//更新日期
        user.setLastdate(new Date());//最后登陆日期
        userDao.save(user);
    }

    public User login(String mobile, String password) {
        User byMobile = userDao.findByMobile(mobile);
        if (byMobile != null && encoder.matches(password, byMobile.getPassword())) {
            return byMobile;

        }
        return null;
    }


}
