package com.example.projectlist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
public class ProjectListApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectListApplication.class, args);
    }

}
