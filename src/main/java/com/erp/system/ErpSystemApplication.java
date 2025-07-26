package com.erp.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaAuditing
@EnableTransactionManagement
public class ErpSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ErpSystemApplication.class, args);
    }

}