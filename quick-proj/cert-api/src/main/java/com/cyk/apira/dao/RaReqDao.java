package com.cyk.apira.dao;


import com.cyk.apira.entity.RequestInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RaReqDao {





    // 请求记录
    @Insert("INSERT INTO REQ_DATA_RND(UID, REQ_DATA, BIZ_TYPE, CERT_TYPE, REQ_ID, CODE, ERR_MSG, RD_TIME) " +
            " VALUES(#{uid},#{reqData},#{bizType},#{certTypeCode},#{reqId},#{code},#{errMsg},#{rdTime} )")
    int addReqRnd(RequestInfo rnd);


}

