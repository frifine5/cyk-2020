package com.cyk.user.controller;

import com.cyk.common.ParamsUtil;
import com.cyk.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


@RestController
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @RequestMapping(value = "/user/query", method = {RequestMethod.GET, RequestMethod.POST})
    public Object queryUserList(HttpServletRequest request) {
        String name = request.getParameter("msg");
        String dept = request.getParameter("msg");
        String pageNoStr = request.getParameter("pageNo");
        String pageSizeStr = request.getParameter("pageSize");

        logger.info("param: name={}, dept={}, pageNo={}, pageSize={}", name, dept, pageNoStr, pageSizeStr);

        int pageNo = 1, pageSize = 10;
        try{
            pageNo = ParamsUtil.checkNullSymbal(pageNoStr)? 1: Integer.parseInt(pageNoStr);
        }catch (Exception e){};
        try{
            pageSize = ParamsUtil.checkNullSymbal(pageSizeStr)? 10: Integer.parseInt(pageSizeStr);
        }catch (Exception e){};

        return userService.queryUserCondition(name, dept, pageNo, pageSize);
    }



}
