package com.example.blog218;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.blog218.dao.mapper")
public class Blog218Application {

    public static void main(String[] args) {
        SpringApplication.run(Blog218Application.class, args);
    }

}
