package com.cmanager.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class AuthModuleApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthModuleApplication.class, args);
    }
}
