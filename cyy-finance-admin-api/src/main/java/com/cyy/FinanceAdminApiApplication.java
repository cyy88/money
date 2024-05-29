package com.cyy;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class}) //先排除安全框架，否则会报错
public class FinanceAdminApiApplication {
    public static void main(String[] args) {
       SpringApplication.run(FinanceAdminApiApplication.class, args);
    }
}
