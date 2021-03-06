package com.fastwok.crawler.job;

import com.fastwok.crawler.services.isservice.CustomerService;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;

@Component
@Slf4j
public class CrawlerFwSchedule {
    @Autowired
    CustomerService customerService;

    @Scheduled(fixedRateString = "${crawler.cron.delay}")
    public void importData() throws MessagingException, UnirestException {
        customerService.getCustomers();
    }
}
