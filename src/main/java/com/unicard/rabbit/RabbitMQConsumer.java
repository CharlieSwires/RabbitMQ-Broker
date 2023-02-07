package com.unicard.rabbit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RabbitMQConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQConsumer.class);

    @Autowired
    private RabbitMQProducerBack producer;

    @RabbitListener(queues = {"${rabbitmq.queue1.name}"})
    public void consume(String message){
        final String uri = "http://archiver:8080/uni-archiver/api/v1/dataarray";
        String ib = message;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<String>(ib ,headers);

        @SuppressWarnings("unchecked")
		Long result = restTemplate.postForObject( uri, entity, Long.class);
        producer.sendMessage(result);
        
        LOGGER.info(String.format("Received message -> %s", message));
    }
}