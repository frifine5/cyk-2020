package com.cyk.user;


import com.cyk.common.ParamsUtil;
import com.cyk.common.ex.NullResultException;
import com.cyk.user.rr.PersonUserInfo;
import com.cyk.user.service.PsUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    PsUserService psUserService;


    @RequestMapping(value = "/user/login", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Object login(HttpServletRequest request){
        // 表单参数
        String account = request.getParameter("account");
        String accountTypeStr = request.getParameter("accountType");
        String pin = request.getParameter("pin");
        logger.info("请求参数: {}, {}, {}", account, accountTypeStr, pin);

        Map<String, Object> rsMap = new HashMap<>();
        // sql检查
        if(ParamsUtil.chkSqlKeys(account)){
            rsMap.put("code", -1);
            rsMap.put("msg", "非法参数:sql illegal");
            return rsMap;
        }

        try {
            int accountType = 1;
            try {
                accountType = Integer.parseInt(accountTypeStr);
            } catch (NumberFormatException pe) {
                // ignore it
                logger.error("入参账号类型不是指定的数字类型: accountType = " + accountTypeStr);
            }
            PersonUserInfo loginInfo = psUserService.getLoginInfo(account, accountType, pin);
            rsMap.put("code", 0);
            rsMap.put("msg", "SUCCESS");
            rsMap.put("data", loginInfo);

        }catch (NullResultException ne){
            logger.error("登录失败", ne);
            rsMap.put("code", -1);
            rsMap.put("msg", "登录失败: "+ ne.getMessage());
        }catch (Exception e){
            logger.error("登录失败", e);
            rsMap.put("code", -1);
            rsMap.put("msg", "登录失败");
        }
        return rsMap;
    }



}
