package com.fastwok.crawler.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name="customer_fastwork")
public class CustomerFastwork {
    @Id
    private Long id;
    private String fw_id;
    private String customer;
}
