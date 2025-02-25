package com.cnco.campusflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CampusflowApplication {

    public static void main(String[] args) {
        SpringApplication.run(CampusflowApplication.class, args);
    }

}
