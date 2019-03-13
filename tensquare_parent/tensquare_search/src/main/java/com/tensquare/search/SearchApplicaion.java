package com.tensquare.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import util.IdWorker;

@SpringBootApplication
public class SearchApplicaion {
    public static void main(String[] args) {
        SpringApplication.run(SpringApplication.class,args);
    }
    @Bean
    public IdWorker idWorker(){
        return  new IdWorker();
    }
}
