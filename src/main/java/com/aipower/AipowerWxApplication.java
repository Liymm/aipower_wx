package com.aipower;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class AipowerWxApplication {

    public static void main(String[] args) {
        SpringApplication.run(AipowerWxApplication.class, args);
    }

}
