package com.fastwok.crawler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
public class CrawlerFastworkApplication {
    public static void main(String[] args) {
        SpringApplication.run(CrawlerFastworkApplication.class, args);
    }
}
