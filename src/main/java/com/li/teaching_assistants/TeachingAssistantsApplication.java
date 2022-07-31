package com.li.teaching_assistants;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync
public class TeachingAssistantsApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeachingAssistantsApplication.class, args);
    }

}
