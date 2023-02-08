package com.unicard.rabbit;


import org.springframework.amqp.core.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig2 {

    @Value("${rabbitmq.queue3.name}")
    private String queue3;

    @Value("${rabbitmq.exchange3.name}")
    private String exchange3;

    @Value("${rabbitmq.routing3.key}")
    private String routingKey3;
    
    // spring bean for rabbitmq queue
    @Bean
    public Queue queue3(){
        return new Queue(queue3);
    }

    // spring bean for rabbitmq exchange
    @Bean
    public TopicExchange exchange3(){
        return new TopicExchange(exchange3);
    }

    // binding between queue and exchange using routing key
    @Bean
    public Binding binding3(){
        return BindingBuilder
                .bind(queue3())
                .to(exchange3())
                .with(routingKey3);
    }

// Spring boot autoconfiguration provides following beans
    // ConnectionFactory
    // RabbitTemplate
    // RabbitAdmin
}