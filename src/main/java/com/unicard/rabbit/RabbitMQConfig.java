package com.unicard.rabbit;


import org.springframework.amqp.core.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.queue1.name}")
    private String queue1;

    @Value("${rabbitmq.exchange1.name}")
    private String exchange1;

    @Value("${rabbitmq.routing1.key}")
    private String routingKey1;
    
    // spring bean for rabbitmq queue
    @Bean
    public Queue queue(){
        return new Queue(queue1);
    }

    // spring bean for rabbitmq exchange
    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(exchange1);
    }

    // binding between queue and exchange using routing key
    @Bean
    public Binding binding(){
        return BindingBuilder
                .bind(queue())
                .to(exchange())
                .with(routingKey1);
    }

// Spring boot autoconfiguration provides following beans
    // ConnectionFactory
    // RabbitTemplate
    // RabbitAdmin
}