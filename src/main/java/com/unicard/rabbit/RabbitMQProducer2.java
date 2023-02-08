package com.unicard.rabbit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducer2 {

	   @Value("${rabbitmq.exchange3.name}")
	    private String exchange3;

	    @Value("${rabbitmq.routing3.key}")
	    private String routingKey3;
	    

   private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQProducer2.class);

   private RabbitTemplate rabbitTemplate;

   public RabbitMQProducer2(RabbitTemplate rabbitTemplate) {
       this.rabbitTemplate = rabbitTemplate;
   }

   public void sendMessage(String message){
       LOGGER.info(String.format("Message sent -> %s", message));
       rabbitTemplate.convertAndSend(exchange3, routingKey3, message);
   }

}
