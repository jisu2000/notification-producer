package org.smart_hire.notification_producer.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${sasl.username}")
    private String saslUsername;

    @Value("${sasl.password}")
    private String saslPassword;

    @Value("${truststore.password}")
    private String truststorePassword;

    @Value("${truststore.path}")
    private String truststorePath;

    @Bean
    public ProducerFactory<String, Map<String, Object>> producerFactory() {
        Map<String, Object> config = new HashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_SSL");
        config.put(SaslConfigs.SASL_MECHANISM, "SCRAM-SHA-256");
        config.put(SaslConfigs.SASL_JAAS_CONFIG,
                String.format("org.apache.kafka.common.security.scram.ScramLoginModule required username=\"%s\" password=\"%s\";",
                        saslUsername, saslPassword));

        config.put("ssl.truststore.location", truststorePath);
        config.put("ssl.truststore.password", truststorePassword);
        config.put("ssl.truststore.type", "JKS");
        config.put("ssl.endpoint.identification.algorithm", "");

        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, Map<String, Object>> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
