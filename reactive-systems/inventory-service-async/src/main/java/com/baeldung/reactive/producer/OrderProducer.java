package com.baeldung.reactive.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.baeldung.reactive.domain.Order;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderProducer {

    @Autowired
    private KafkaTemplate<String, Order> kafkaTemplate;

    public void sendMessage(Order order) {
        log.info(String.format("#### -> Producing message -> %s", order));
        this.kafkaTemplate.send("orders", order);
    }

}
