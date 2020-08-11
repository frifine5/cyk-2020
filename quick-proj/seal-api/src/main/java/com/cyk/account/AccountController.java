package com.cyk.account;


import com.cyk.common.ParamsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AccountController {

    Logger logger = LoggerFactory.getLogger(AccountController.class);


    @RequestMapping(value = "", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Object login(HttpServletRequest request){
        // 表单参数
        String account = request.getParameter("account");
        String accountType = request.getParameter("accountType");
        String pin = request.getParameter("pin");

        Map<String, Object> errRt = new HashMap<>();
        // sql检查
        if(ParamsUtil.chkSqlKeys(account)){
            errRt.put("code", -1);
            errRt.put("msg", "非法参数:sql illegal");
            return errRt;
        }

        try{

            return null;

        }catch (Exception e){
            logger.error("登录失败", e);
            errRt.put("code", -1);
            errRt.put("msg", "登录失败");
            return errRt;
        }
    }



}
