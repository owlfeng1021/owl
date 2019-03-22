package com.tensquare.friend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import util.JwtUtil;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
public class FriendApplicaiton {
    public static void main(String[] args) {
        SpringApplication.run(FriendApplicaiton.class,args);
    }
    @Bean
    public JwtUtil jwtUtil(){
        return new JwtUtil();
    }
}
