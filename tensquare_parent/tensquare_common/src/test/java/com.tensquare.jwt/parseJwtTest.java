package com.tensquare.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import java.text.SimpleDateFormat;

public class parseJwtTest {
    public static void main(String[] args) {
        Claims sakura = Jwts.parser().setSigningKey("Sakura")
                .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI2NjYiLCJzdWIiOiJvd2wiLCJpYXQiOjE1NTI3MjA1ODEsImV4cCI6MTU1MjcyMDY0MSwicm9sZSI6ImFkbWluIn0.2X_7n6DyGvxeep-I7XM_D53UtyRMsdk-hZK6LUIkP_w")
                .getBody();
        System.out.println("用户id" + sakura.getId());
        System.out.println("用户名" + sakura.getSubject());
        System.out.println("登录时间" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(sakura.getIssuedAt()));
        System.out.println("过期时间" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(sakura.getExpiration()));
        System.out.println("用户角色" + sakura.get("role") );

    }
}
