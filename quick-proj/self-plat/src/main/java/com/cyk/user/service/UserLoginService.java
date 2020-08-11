package com.cyk.user.service;


import com.cyk.common.ParamsUtil;
import com.cyk.user.bean.OuVo;
import com.cyk.user.bean.UserVo;
import com.cyk.user.bean.ViewUser;
import com.cyk.user.dao.OuDao;
import com.cyk.user.dao.UserDao;
import com.cyk.user.dao.ViewUserDao;
import com.cyk.user.rr.LoginResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserLoginService {

    Logger logger = LoggerFactory.getLogger(UserLoginService.class);

    @Autowired
    UserDao userDao;

    @Autowired
    OuDao ouDao;



    public LoginResp findUserName(String name, String pwd){

        UserVo user = userDao.findOuById(name);
        if(user == null || ParamsUtil.checkNullSymbal(user.getOuguid())){
            return null;
        }else{
            checkPwd(pwd, user.getPassword(), name);
            OuVo ou = ouDao.findOuById(user.getOuguid());
            if(null == ou || ParamsUtil.checkNullSymbal(ou.getOuname())){
                return null;
            }
            LoginResp resp = new LoginResp(user.getUserguid(), name, user.getUserguid(), ou.getOuname(), ou.getOuguid(), ou.getOucode() );
            return resp;
        }
    }

    void checkPwd(String pwd, String dbPwd, String name){

        if(pwd.equals(dbPwd)){
            // check 方式

        }else{
            logger.error("用户{}:密码匹配失败", name);
//            throw new RuntimeException("用户名或密码错误，请检查后输入");
        }

    }


}
