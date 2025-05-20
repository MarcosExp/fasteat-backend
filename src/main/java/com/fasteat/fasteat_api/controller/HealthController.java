package com.fasteat.fasteat_api.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class HealthController {
    
    private static final Logger logger = LoggerFactory.getLogger(HealthController.class);

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${server.port}")
    private String serverPort;
    
    @GetMapping("/health")
    public String healthCheck() {
        logger.info("DB URL: {}", dbUrl);
        logger.info("DB Username: {}", dbUsername);
        logger.info("Server Port: {}", serverPort);
        return "API is running!";
    }
} 