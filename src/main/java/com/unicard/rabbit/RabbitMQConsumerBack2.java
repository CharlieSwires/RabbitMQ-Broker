package com.unicard.rabbit;

import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RabbitMQConsumerBack2 {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQConsumerBack2.class);
    @Autowired
    private RabbitService service;
   
    @RabbitListener(queues = {"${rabbitmq.queue4.name}"})
    public void consume(String message){

    	if (message != null) {
    		ObjectMapper mapper = new ObjectMapper();

    		RequestBean bean = null;
    		try {
    			bean = mapper.readValue(message, RequestBean.class);
    		} catch (JsonProcessingException e) {
    			e.printStackTrace();
    		}
    		service.response = bean;
    		service.messageId= new AtomicLong(bean.getMessageId());

    		LOGGER.info(String.format("Received message -> %s", message));
    	}else {
            LOGGER.info(String.format("Received message -> null"));
    	}
    }
}