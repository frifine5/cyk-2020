package com.cyk.http;


import com.cyk.http.dao.Db1Dao;
import com.cyk.http.dao.Db2Dao;
import org.apache.commons.collections.map.LinkedMap;
import org.apache.commons.collections.map.ListOrderedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


@RestController
public class AppTestController {

    Logger logger = LoggerFactory.getLogger(AppTestController.class);



    @RequestMapping(value = "/app/rev", method = {RequestMethod.GET, RequestMethod.POST})
    public Object revMsg4lApp(HttpServletRequest request) {
        String s = "I got you in time: " + new Date();
        String msg = request.getParameter("msg");
        System.out.println("I got msg from app: " + msg);

        return msg;
    }



    @Autowired
    Db1Dao db1Dao;


    @Autowired
    Db2Service db2Service;

    @RequestMapping(value = "/app/testDs", method = {RequestMethod.GET, RequestMethod.POST})
    public Object testDoubleDs1(HttpServletRequest request) {

        logger.info(">> test double databases...");

        List<String> name1s = db1Dao.getNames();

        List<String> name2s = db2Service.getNames();


        Map<String, Object> rtnMap = new TreeMap<>();
        rtnMap.put("db1result", name1s);
        rtnMap.put("db2result", name2s);

        return rtnMap;
    }




}
