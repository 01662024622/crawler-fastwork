package com.fastwok.crawler;

import com.fastwok.crawler.services.isservice.CustomerService;
import com.fastwok.crawler.services.impl.MailSenderServiceImpl;
import com.fastwok.crawler.services.isservice.MailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CrawlerFastworkApplication implements CommandLineRunner {
    @Autowired
    CustomerService customerService;
    @Autowired
    MailSenderService senderService;

    public static void main(String[] args) {
        SpringApplication.run(CrawlerFastworkApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        customerService.getCustomers();
    }
}
