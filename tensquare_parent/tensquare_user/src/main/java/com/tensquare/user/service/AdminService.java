package com.tensquare.user.service;

import com.tensquare.user.dao.AdminDao;
import com.tensquare.user.pojo.Admin;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import java.beans.Encoder;
import java.util.List;

@Service
@Transactional
public class AdminService {
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private AdminDao adminDao;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void save(Admin  admin){
        admin.setId( idWorker.nextId()+"");
        //密码加密
        admin.setPassword(bCryptPasswordEncoder.encode(admin.getPassword()));
        adminDao.save(admin);
    }

    public Admin login(Admin admin) {
        Admin byLoginname = adminDao.findByLoginname(admin.getLoginname());
        //先是使用loginname 查出 基本的用户信息 然后把admin的密码和数据库里面的密码进行比对
        if (byLoginname!=null && bCryptPasswordEncoder.matches(admin.getPassword(),byLoginname.getPassword())){
            return  byLoginname;
        }
        return null;
    }


}
