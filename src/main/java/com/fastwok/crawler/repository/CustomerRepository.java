package com.fastwok.crawler.repository;

import com.fastwok.crawler.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT cus FROM Customer cus WHERE cus.code=?1")
    List<Customer> getByIdCode( String code);
}
