package com.unicard.rabbit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.UniCardMongoBeanRepository;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses= {UniCardMongoBeanRepository.class})
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}