package com.cyk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
@MapperScan(basePackages = {"com.cyk.*"})
public class SealAppStarter {

    public static void main(String[] args) {
        SpringApplication.run(SealAppStarter.class, args);
    }


}
