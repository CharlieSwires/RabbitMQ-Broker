package com.unicard.rabbit;


import org.springframework.amqp.core.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfigBack {

    @Value("${rabbitmq.queue1.name}")
    private String queue1;

    @Value("${rabbitmq.exchange1.name}")
    private String exchange1;

    @Value("${rabbitmq.routing1.key}")
    private String routingKey1;
    
    @Value("${rabbitmq.queue2.name}")
    private String queue2;

    @Value("${rabbitmq.exchange2.name}")
    private String exchange2;

    @Value("${rabbitmq.routing2.key}")
    private String routingKey2;

    // spring bean for rabbitmq queue
    @Bean
    public Queue queue2(){
        return new Queue(queue2);
    }

    // spring bean for rabbitmq exchange
    @Bean
    public TopicExchange exchange2(){
        return new TopicExchange(exchange2);
    }

    // binding between queue and exchange using routing key
    @Bean
    public Binding binding2(){
        return BindingBuilder
                .bind(queue2())
                .to(exchange2())
                .with(routingKey2);
    }

// Spring boot autoconfiguration provides following beans
    // ConnectionFactory
    // RabbitTemplate
    // RabbitAdmin
}