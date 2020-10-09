package com.cyk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class
})
@MapperScan(basePackages = {"com.cyk.*"})
public class DdsTestAppStarter {

    public static void main(String[] args) {
        SpringApplication.run(DdsTestAppStarter.class, args);
    }


}
