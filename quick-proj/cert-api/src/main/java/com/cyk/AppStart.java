package com.cyk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan(basePackages = {"com.cyk.*"})
public class AppStart {

    public static void main(String[] args) {
        SpringApplication.run(AppStart.class, args);
    }


}
