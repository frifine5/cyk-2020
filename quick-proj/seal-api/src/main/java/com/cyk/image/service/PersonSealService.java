package com.cyk.image.service;


import com.cyk.common.ParamsUtil;
import com.cyk.image.dao.PersonImageDao;
import com.cyk.image.entity.PsSealImage;
import com.cyk.image.rr.SealImg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * 个人印章操作
 */
@Service
public class PersonSealService {


    @Autowired
    PersonImageDao personImageDao;

    /**
     * 查询个人所属有效且可用的印章
     */

    public List<SealImg> getAvaliable(String uid){
        List<PsSealImage> psSealImages = personImageDao.queryByUid(uid);
        List<SealImg> sealImgs = new ArrayList<>();
        for (PsSealImage seal: psSealImages ) {
            if(1 !=seal.getStatusCode() || null == seal.getSealData() || null == seal.getPicData()){
                // 非正常状态、无印模数据、无印章数据的，都跳过
                continue;
            }
            sealImgs.add(new SealImg(seal.getUuid(), seal.getSealCode(), seal.getSealName(),
                    seal.getPicType(), seal.getWidth(), seal.getHeight(), seal.getPicData()));
        }
        return ParamsUtil.checkListNull(sealImgs)?null: sealImgs;
    }





}
