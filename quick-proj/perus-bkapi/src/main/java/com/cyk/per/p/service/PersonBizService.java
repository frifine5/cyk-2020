package com.cyk.per.p.service;


import com.cyk.common.ParamsUtil;
import com.cyk.per.p.dao.PersonImageDao;
import com.cyk.per.p.dao.PersonUserDao;
import com.cyk.per.p.entity.PersonUser;
import com.cyk.per.p.entity.PsImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;


/**
 * 个人业务服务
 * @author WangChengyu
 * 2020/4/14 14:59
 */
@Service
public class PersonBizService {

    // 生成个人名章：2-16字名章
    @Autowired
    PersonImageService psImgService;


    @Autowired
    PersonUserDao personUserDao;

    @Autowired
    PersonImageDao personImageDao;


    public void genPsImageAndSave(int id){
        PersonUser pUser = personUserDao.findOneById(id);
        if(null == pUser || ParamsUtil.checkNull(pUser.getName())){
            throw new RuntimeException("用户不存在");
        }

        PsImage psImageRd = personImageDao.findExist(id);

        if(null == psImageRd ||ParamsUtil.checkNull(psImageRd.getImgPath()) ){
            byte[] pImgData = genPsImage(pUser.getName());
            PsImage psImage = new PsImage();
            psImage.setUid(id);
            psImage.setType(1);
            psImage.setCrtTime(ParamsUtil.formatTime19(new Date()));
            psImage.setImgPath(Base64.getEncoder().encodeToString(pImgData));

            int ra = personImageDao.addOne(psImage);
            if(ra <1) throw new RuntimeException("个人名章生成失败");
        }

    }

    public void upPSignAndSave(int id, byte[] imgData ){
        PersonUser pUser = personUserDao.findOneById(id);
        if(null == pUser || ParamsUtil.checkNull(pUser.getName())){
            throw new RuntimeException("用户不存在");
        }
        PsImage psImage = new PsImage();
        psImage.setUid(id);
        psImage.setType(2);
        psImage.setCrtTime(ParamsUtil.formatTime19(new Date()));
        psImage.setImgPath(Base64.getEncoder().encodeToString(imgData));
        int ra = personImageDao.addOne(psImage);
        if(ra <1) throw new RuntimeException("个人签名保存失败");
    }

    public byte[] genPsImage(String name){
        if(ParamsUtil.checkNull(name)){
            throw new IllegalArgumentException("name is empty/参数name为空值");
        }
        byte[] imgData = null;
        if(name.length()<5)
           imgData = psImgService.genPsSquareSeal(name, 1.0f, null);
        else if(name.length()<7)
            imgData = psImgService.genPsSquareSealSix(name, 1.0f);
        else if(name.length()< 17)
            imgData = psImgService.genPsRectSealLonger(name, 1, 1);
        else
            throw new IllegalArgumentException("name is too long/参数name太长");

        return imgData;
    }






}
