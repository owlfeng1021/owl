package com.tensquare.sms.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queues = "sms")
public class SmsListener {
    @RabbitHandler
    public void executeSms(Map<String,String> map){
        if (map!=null)
        {
            System.out.println(map.get("mobile") + " :" + map.get("checkcode"));
        }
        else
        {
        System.out.println("错误");
        }
    }

}
