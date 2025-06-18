package org.smart_hire.notification_producer.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    
    @GetMapping("/test")
    public Map<String,String> test(){
        return Map.of("msg","Kafka producer is up");
    }
}
