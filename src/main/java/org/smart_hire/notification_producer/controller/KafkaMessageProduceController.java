package org.smart_hire.notification_producer.controller;

import lombok.RequiredArgsConstructor;
import org.smart_hire.notification_producer.service.ProducerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class KafkaMessageProduceController {
    private final ProducerService producerService;

    @PostMapping("/publish")
    public ResponseEntity<?> sendMessage(@RequestBody Map<String, Object> req) throws Exception {
        boolean sendSuccess;
        Map<String, Object> msg = (Map<String, Object>) req.get("message");
        String topic = (String) req.get("topic");
            sendSuccess = producerService.sendMessage(msg,topic);
            return ResponseEntity.ok(Map.of("success", sendSuccess));
    }
}
