package com.cyk.user.service;

import com.cyk.common.ParamsUtil;
import com.cyk.common.ex.NullResultException;
import com.cyk.user.dao.PersonUserDao;
import com.cyk.user.entity.PsUser;
import com.cyk.user.entity.PsUserAccount;
import com.cyk.user.rr.PersonUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PsUserService {

    @Autowired
    PersonUserDao personUserDao;


    public PersonUserInfo getLoginInfo(String account, int accoutType, String pin) throws NullResultException{

        List<PsUserAccount> loginPsAccount = personUserDao.getLoginPsAccount(account, accoutType);
        if(ParamsUtil.checkListNull(loginPsAccount)){
            throw new NullResultException("账号不存在");
        }
        PsUser psUser = personUserDao.findPsUserByUid(loginPsAccount.get(0).getUid());
        if( null == psUser || ParamsUtil.checkNull(psUser.getPin())){
            throw new NullResultException("账号已冻结");
        }
        if(!psUser.getPin().equals(pin)){
            throw new NullResultException("密码口令错误");
        }
        PersonUserInfo userInfo = new PersonUserInfo(loginPsAccount.get(0),
                psUser.getNickName(), psUser.getRealName());

        return userInfo;
    }



}
