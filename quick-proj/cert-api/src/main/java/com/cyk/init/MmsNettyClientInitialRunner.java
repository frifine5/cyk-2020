package com.cyk.init;

import com.cyk.ioclient.wskt.WebSocketClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(99)
public class MmsNettyClientInitialRunner implements CommandLineRunner {

    Logger logger = LoggerFactory.getLogger(MmsNettyClientInitialRunner.class);

    @Value("${mms.ws.ipport:ws://192.168.6.238:11003/mms/}")
    String wsIpUrl;

    @Value("${mms.ws.client.name:sykSelfCA}")
    String cliName;

    @Value("${mms.ws.client.size:5}")
    int cliSize;



    @Override
    public void run(String... args) throws Exception {
        logger.info("======================= 初始化mms的netty客户端 ======================= Load Begin");
        logger.info("=== mms web-socket client [name: {}, size: {} ] ===", cliName, cliSize );
        String uri = wsIpUrl + cliName;
        WebSocketClient.initClientLoop(uri);


        logger.info("======================= 初始化mms的netty客户端 ======================= Load Finish");
    }
}
