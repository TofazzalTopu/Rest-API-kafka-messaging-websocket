package com.info.demo.service.kafka;

import com.info.demo.config.kafka.KafkaConsumerConfig;
import com.info.demo.constant.AppConstants;
import com.info.demo.model.User;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class KafkaNotificationService {

    @KafkaListener(topics = AppConstants.MESSAGE_TOPIC, groupId = AppConstants.CONSUMER_GROUP_ID, containerFactory = KafkaConsumerConfig.KAFKA_LISTENER_CONTAINER_FACTORY)
    public void receiveNotification(String message) {
        System.out.println("Notification Received: " + message);
    }

    @KafkaListener(topics = AppConstants.NOTIFY_USER_TOPIC, groupId = AppConstants.CONSUMER_GROUP_ID, containerFactory = KafkaConsumerConfig.KAFKA_USER_LISTENER_CONTAINER_FACTORY)
    public void receiveUserNotification(User user) {
        System.out.println("User Notification Received: " + user);
    }

}
