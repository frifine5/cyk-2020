package com.cyk.seal.service;


import com.cyk.common.ParamsUtil;
import com.cyk.number.GenNumberService;
import com.cyk.seal.dao.SealApplyDao;
import com.cyk.seal.entity.SealApplyEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class SealApplyService {

    @Autowired
    SealApplyDao sealApplyDao;

    @Autowired
    GenNumberService numberService;


    @Transactional(rollbackFor = Exception.class)
    public void recordApplyList(List<SealApplyEntity> applyList) {
        for (SealApplyEntity e : applyList) {
            // 发个orderId号
            String orderId = "apply" + (new SimpleDateFormat("yyyyMMdd").format(new Date())) + numberService.getNumber();
            e.setOrderId(orderId);
            Date st = new Date();
            Calendar cld = Calendar.getInstance();
            cld.setTime(st);
            int dayofYear = cld.get(Calendar.DAY_OF_YEAR);
            if(dayofYear == 1 ){
                cld.set(cld.get(Calendar.YEAR), 12, 31, 23, 59, 59);
            }else{
                cld.set(Calendar.YEAR, cld.get(Calendar.YEAR) + e.getValid());
                cld.set(Calendar.DAY_OF_YEAR, cld.get(Calendar.DAY_OF_YEAR) - 1);
                cld.set(Calendar.HOUR_OF_DAY, 23);
                cld.set(Calendar.MINUTE, 59);
                cld.set(Calendar.SECOND, 59);
            }
            Date et = cld.getTime();
            e.setValidStart(new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(st));
            e.setValidEnd(ParamsUtil.formatTime19(et));
            e.setRdTime(ParamsUtil.formatTime19(new Date()));
            int ra = sealApplyDao.addSealApply(e);
            if(ra < 1) throw new RuntimeException("添加记录失败：印章名=" + e.getSealName());
        }
    }

    public boolean checkSealNameDupt(List<String> sealNameList){
        int count = sealApplyDao.checkSealNameInApply(sealNameList);
        return count > 0 ;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean uptSealPic(String orderId, byte[] picData, String picType) {

        int ru = sealApplyDao.uploadApplyPic(orderId, picData, picType);
        return ru > 1;

    }



}
