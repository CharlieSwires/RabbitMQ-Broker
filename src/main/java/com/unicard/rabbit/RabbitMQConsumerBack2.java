package com.unicard.rabbit;

import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumerBack2 {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQConsumerBack2.class);
    @Autowired
    private RController control;
   
    @RabbitListener(queues = {"${rabbitmq.queue4.name}"})
    public void consume(Long message){
    	if (message != null) {
    		control.messageId= new AtomicLong(message);
    		LOGGER.info(String.format("Received message -> %s", message));
    	}else {
            LOGGER.info(String.format("Received message -> null"));
    	}
    }
}