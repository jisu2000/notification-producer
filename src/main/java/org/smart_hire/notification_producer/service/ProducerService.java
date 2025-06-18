package org.smart_hire.notification_producer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProducerService {

    private final KafkaTemplate<String, Map<String, Object>> kafkaTemplate;

    public boolean sendMessage(Map<String , Object> msg, String topic) throws Exception {
        boolean success = false;
        try {
            msg.put("key_id", UUID.randomUUID().toString());
            msg.put("kakfa_topic", topic);
            kafkaTemplate.send(topic, msg);
            success = !success;
            log.info("Message Published to topic {}", topic);
        }catch (Exception e) {
            log.error("Error while publishing message to topic {}", topic, e);
        }
        return success;
    }
}
