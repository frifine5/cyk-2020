package com.cyk.t.protocol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = {"com.cyk"})
public class MmsAppStarter {
    static Logger logger = LoggerFactory.getLogger(MmsAppStarter.class);

    public static void main(String[] args) {
        String versionNumber = "wsk-mms-2020051801T";
        System.out.println(">>>>>>>>>>> 版本号: " + versionNumber + " >>>>>>>>>>>");
        logger.info("版本号：" + versionNumber);
        SpringApplication.run(MmsAppStarter.class, args);
    }

}
