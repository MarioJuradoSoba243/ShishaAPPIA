package com.shisha.appia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Shisha APPIA backend application.
 */
@SpringBootApplication
public class ShishaAppiaApplication {

    /**
     * Bootstraps the Spring Boot context.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(ShishaAppiaApplication.class, args);
    }
}
