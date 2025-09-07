package com.voracity.shushudemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.voracity.shushudemo.mapper")
public class ShushudemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShushudemoApplication.class, args);
    }

}
