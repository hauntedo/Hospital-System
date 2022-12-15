package ru.itis.hauntedo.simbirtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.itis.hauntedo.simbirtest.security.config.SecurityConfig;

@SpringBootApplication
public class SimbirTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimbirTestApplication.class, args);
    }

}
