package com.tensquare.jwt;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class CreateJwt {
    public static void main(String[] args) {
          JwtBuilder jwtBuilder=Jwts.builder()
                  .setId("666")
                  .setSubject("owl")
                  .setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256,"Sakura")
                  .setExpiration(new Date(new Date().getTime()+60000))
                  .claim("role" ,"admin");

          System.out.println(jwtBuilder.compact());

    }
}
