package com.lowi.authdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lowi.authdemo.mapper")
public class AuthdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthdemoApplication.class, args);
    }

}
