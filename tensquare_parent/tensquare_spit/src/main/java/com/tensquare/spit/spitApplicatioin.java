package com.tensquare.spit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import util.IdWorker;

@SpringBootApplication
public class spitApplicatioin {
    public static void main(String[] args) {
        SpringApplication.run(spitApplicatioin.class,args);
    }
    @Bean
    public IdWorker idWorkker(){
        return new IdWorker(1, 1);
    }

}
