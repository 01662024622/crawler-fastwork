package com.fastwok.crawler.services;

import com.mashape.unirest.http.exceptions.UnirestException;

import javax.mail.MessagingException;

public interface BaseService {
    public void getCustomers() throws UnirestException, MessagingException;
}
