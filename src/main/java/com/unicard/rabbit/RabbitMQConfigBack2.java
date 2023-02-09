package com.unicard.rabbit;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfigBack2 {

    
    @Value("${rabbitmq.queue4.name}")
    private String queue4;

    @Value("${rabbitmq.exchange4.name}")
    private String exchange4;

    @Value("${rabbitmq.routing4.key}")
    private String routingKey4;

    // spring bean for rabbitmq queue
    @Bean
    public Queue queue4(){
        return new Queue(queue4);
    }

    // spring bean for rabbitmq exchange
    @Bean
    public TopicExchange exchange4(){
        return new TopicExchange(exchange4);
    }

    // binding between queue and exchange using routing key
    @Bean
    public Binding binding4(){
        return BindingBuilder
                .bind(queue4())
                .to(exchange4())
                .with(routingKey4);
    }

// Spring boot autoconfiguration provides following beans
    // ConnectionFactory
    // RabbitTemplate
    // RabbitAdmin
}