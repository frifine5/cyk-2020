package com.cyk.apiuser.dao;


import com.cyk.apiuser.entity.RentProjInfo;
import com.cyk.apiuser.entity.RentUser;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RentInfoDao {

    @Select("SELECT ID, UID, UNAME, PIN, RD_TIME, LIM_TIME " +
            " FROM RENT_INFO WHERE UID=#{uid}")
    @Results(id="rentUser", value = {
            @Result(property = "id", column = "ID"),
            @Result(property = "uid", column = "UID"),
            @Result(property = "uname", column = "UNAME"),
            @Result(property = "pin", column = "PIN"),
            @Result(property = "rdTime", column = "RD_TIME"),
            @Result(property = "limTime", column = "LIM_TIME"),
    })
    RentUser findUserById(@Param("uid") String rentId);



    @Select("SELECT ID, UID, PRJ_ID, ALG_TYPE, AUTH_TYPE, AUTH_PUK, RD_TIME " +
            " FROM RENT_PROJ_RD WHERE UID=#{uid} AND PRJ_ID=#{projId}")
    @Results(id="rentProj", value = {
            @Result(property = "id", column = "ID"),
            @Result(property = "uid", column = "UID"),
            @Result(property = "projId", column = "PRJ_ID"),
            @Result(property = "algType", column = "ALG_TYPE"),
            @Result(property = "authType", column = "AUTH_TYPE"),
            @Result(property = "authPuk", column = "AUTH_PUK"),
            @Result(property = "rdTime", column = "RD_TIME"),
    })
    RentProjInfo findProjInfo(@Param("uid") String rentId, @Param("projId") String projId);





}

