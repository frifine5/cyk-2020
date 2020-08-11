package com.cyk.t.protocol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class AppStarter {
    static Logger logger = LoggerFactory.getLogger(AppStarter.class);

    public static void main(String[] args) {
        String versionNumber = "crabKing-2020030201T";
        System.out.println(">>>>>>>>>>> 版本号: " + versionNumber + " >>>>>>>>>>>");
        logger.info("版本号：" + versionNumber);
        SpringApplication.run(AppStarter.class, args);
    }

}
