package com.tensquare.friend.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient("tensquare-user")
public interface UserClient {
    @PutMapping("/user/{userid}/{friendid}/{x}")
    public void updatefanscountandfollowcount(@PathVariable("userid") String userid, @PathVariable("friendid") String friendid, @PathVariable("x") int x);

}
