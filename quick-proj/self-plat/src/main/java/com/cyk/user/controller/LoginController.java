package com.cyk.user.controller;

import com.cyk.common.ParamsUtil;
import com.cyk.user.rr.LoginParam;
import com.cyk.user.rr.LoginResp;
import com.cyk.user.service.UserLoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
public class LoginController {

    Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    UserLoginService userLoginService;

    @RequestMapping(value = "/user/login", method = { RequestMethod.POST})
    @ResponseBody
    public Object queryUserList(@RequestBody LoginParam reqParam) {
        Map<String, Object> rtnMap = new HashMap<>();
        String name = reqParam.getName();
        String pwd = reqParam.getPwd();
        if(ParamsUtil.checkNullSymbal(name, pwd)){
            rtnMap.put("code", -1);
            rtnMap.put("msg", "参数错误");
            return rtnMap;
        }
        try{
            LoginResp resp = userLoginService.findUserName(name, pwd);
            rtnMap.put("code", 0);
            rtnMap.put("msg", "SUCCESS");
            rtnMap.put("data", resp);
        }catch (Exception e){
            logger.error("用户"+name+":登录失败", e);
            rtnMap.put("code", -2);
            rtnMap.put("msg", e.getMessage());
        }
        return rtnMap;
    }


}
