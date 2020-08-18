package com.fastwok.crawler.services;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;

public interface BaseService {
    public String getCustomers() throws UnirestException;
}
