package com.info.demo.controller;


import com.info.demo.config.kafka.KafkaConsumerConfig;
import com.info.demo.constant.AppConstants;
import com.info.demo.model.User;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/kafka/notify")
public class KafkaController {

    private static final Logger LOGGER = Logger.getLogger(KafkaController.class.getName());
    private KafkaTemplate<String, String> kafkaTemplate;
    private KafkaTemplate<String, User> userKafkaTemplate;

    @GetMapping(value = "/message")
    public void notify(@RequestParam String message) {
        kafkaTemplate.send(AppConstants.NOTIFY_TOPIC, message);
    }

    @PostMapping(value = "/user")
    public void notifyUser(@RequestBody User user) {
        userKafkaTemplate.send(AppConstants.NOTIFY_USER_TOPIC, user);
    }


    @KafkaListener(topics = AppConstants.NOTIFY_TOPIC, groupId = AppConstants.CONSUMER_GROUP_ID, containerFactory = KafkaConsumerConfig.KAFKA_LISTENER_CONTAINER_FACTORY)
    public void receiveNotification(String message) {
        LOGGER.info("Notification Received: " + message);
        System.out.println("Notification Received: " + message);
    }

    @KafkaListener(topics = AppConstants.NOTIFY_USER_TOPIC, groupId = AppConstants.USER_CONSUMER_GROUP_ID, containerFactory = KafkaConsumerConfig.KAFKA_USER_LISTENER_CONTAINER_FACTORY)
    public void receiveUserNotification(User user) {
        LOGGER.info("User Notification Received: " + user);
        System.out.println("User Notification Received: " + user);
    }

}
