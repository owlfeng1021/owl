package com.tensquare.test;

import com.tensquare.rabbitmq.RabbitApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RabbitApplication.class)
public class ProductTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Test
    public  void test1(){ //生产者
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("mobile","123123");
        stringStringHashMap.put("checkcode","123123");
        rabbitTemplate.convertAndSend("sms" ,stringStringHashMap);
    }

//    @Test
//    public  void test2(){ //分裂模式
//        rabbitTemplate.convertAndSend("ownHome","","分裂模式测试");
//    }

//    @Test
//    public  void test3(){ //主题模式
//        rabbitTemplate.convertAndSend("topic1","good.txt.log","主题模式测试");
//    }

}
