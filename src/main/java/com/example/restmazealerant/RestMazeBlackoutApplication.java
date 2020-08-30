package com.example.restmazealerant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class RestMazeBlackoutApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestMazeBlackoutApplication.class, args);
    }

}
