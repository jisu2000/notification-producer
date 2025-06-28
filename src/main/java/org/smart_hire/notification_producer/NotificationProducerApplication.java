package org.smart_hire.notification_producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class NotificationProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationProducerApplication.class, args);
	}

}
