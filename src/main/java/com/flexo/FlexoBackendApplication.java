package com.flexo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.flexo")
public class FlexoBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlexoBackendApplication.class, args);
    }
}
