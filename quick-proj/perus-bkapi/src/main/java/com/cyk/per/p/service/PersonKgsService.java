package com.cyk.per.p.service;


import com.cyk.common.ParamsUtil;
import com.cyk.common.rsa.PfxUtil;
import com.cyk.per.p.dao.PersonUserDao;
import com.cyk.per.p.dao.PsStampDao;
import com.cyk.per.p.entity.PersonUser;
import com.cyk.per.p.entity.PsStamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Calendar;
import java.util.Date;


@Service
public class PersonKgsService {

    private Logger log = LoggerFactory.getLogger(PersonKgsService.class);

    @Autowired
    PersonUserDao personUserDao;

    @Autowired
    PsStampDao psStampDao;

    // 生成密钥对和.ks in rsa or kp in sm3
    public String genKpOfRsa(int id){
        PersonUser person = personUserDao.findOneById(id);
        String name = person.getName();
        String alias = "person";
        String pwdStr = ParamsUtil.getUUIDStr().substring(0,8);
        String issuer = String.join("," , new String[]{name, name, "北京市" , "北京市" , "CN"});

        byte[] ksBytes = null;
        try {
            ksBytes = PfxUtil.genRsaPfx(alias, pwdStr.toCharArray(), issuer, 365);
        } catch (Exception e) {
            log.error("生成pkcs12文件失败", e);
            throw new RuntimeException("生成pkcs12文件失败", e);
        }
        String crtTime = ParamsUtil.formatTime19(new Date());
        String stampId = ParamsUtil.getUUIDStr();
        PsStamp psStamp = new PsStamp();
        psStamp.setId(stampId);
        psStamp.setUid(id);
        psStamp.setKsType("rsa2048");
        psStamp.setKsPath(Base64.getEncoder().encodeToString(ksBytes));
        psStamp.setEntInfo(alias + "-" + pwdStr);
        psStamp.setCrtTime(crtTime);
        psStamp.setValidSt(crtTime);
        psStamp.setValidEnd(ParamsUtil.formatTime19(
                ParamsUtil.calcDestDate(new Date(), Calendar.YEAR, 1)));

        int ra = psStampDao.addOne(psStamp);
        if(ra <1){
            throw new RuntimeException("保存个人章信息失败");
        }
        return stampId;
    }


    // 申请证书




    //加解密




    //签名验签






}
