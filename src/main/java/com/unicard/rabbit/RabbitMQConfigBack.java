package com.unicard.rabbit;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfigBack {
  
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