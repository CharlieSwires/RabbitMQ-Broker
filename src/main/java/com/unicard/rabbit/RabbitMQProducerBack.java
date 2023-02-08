package com.unicard.rabbit;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducerBack {

	    @Value("${rabbitmq.exchange2.name}")
	    private String exchange2;

	    @Value("${rabbitmq.routing2.key}")
	    private String routingKey2;

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQProducerBack.class);

    private RabbitTemplate rabbitTemplate;

    public RabbitMQProducerBack(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(Long message){
        LOGGER.info(String.format("Message sent -> %s", message));
        rabbitTemplate.convertAndSend(exchange2, routingKey2, message);
    }
}