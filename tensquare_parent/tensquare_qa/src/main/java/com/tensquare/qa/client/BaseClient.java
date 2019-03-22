package com.tensquare.qa.client;

import entity.Result;
import entity.StatusCode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
// 熔断器
@FeignClient(value = "tensquare-base",fallback = BaseClientImpl.class)//不能写下划线

public interface BaseClient {

    @GetMapping("/label/{labelId}")
    public Result findById(@PathVariable("labelId") String labelId);

}
