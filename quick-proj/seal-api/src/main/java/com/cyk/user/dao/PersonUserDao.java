package com.cyk.user.dao;


import com.cyk.user.entity.PsUser;
import com.cyk.user.entity.PsUserAccount;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface PersonUserDao {


    /**
     * 无差别查询所有 -
     */
    @Select("SELECT ID, UID, PIN, NICK_NAME, REAL_NAME, RD_TIME FROM PS_USER ORDER BY ID DESC")
    @Results(id="person", value = {
            @Result(property = "id" , column = "ID"),
            @Result(property = "uid" , column = "UID"),
            @Result(property = "pin" , column = "PIN"),
            @Result(property = "nickName" , column = "NICK_NAME"),
            @Result(property = "realName" , column = "REAL_NAME"),
            @Result(property = "rdTime" , column = "RD_TIME"),

    })
    List<PsUser> queryPUser();

    @Insert("INSERT INTO PS_USER(UID, PIN, NICK_NAME, REAL_NAME,RD_TIME) " +
            " VALUES(#{uid}, #{pin}, #{nickName}, #{realName}, #{rdTime})")
    int addOnePsUser(PsUser user);

    @Update("UPDATE PS_USER SET PIN=#{pin}, NICK_NAME=#{nickName}, REAL_NAME=#{realName}, RD_TIME=#{rdTime}" +
            " WHERE UID=#{uid}")
    int uptOnePsUser(PsUser user);

    @Select("SELECT ID, UID, PIN, NICK_NAME, REAL_NAME, RD_TIME FROM PS_USER WHERE UID=#{uid}")
    @ResultMap("person")
    PsUser findPsUserByUid(String uid);

    // for login

    @Select("SELECT ID, UID, ACCOUNT, ACCOUNT_TYPE, RD_TIME, INFO, PASS_APPR, APPR_INFO, STATUS  " +
            " FROM PS_USER_ACCOUNT " +
            " WHERE ACCOUNT=#{account} AND ACCOUNT_TYPE=#{accountType} AND STATUS = 1 " +
            " ORDER BY ID DESC")
    @Results(id="psUserAccount", value = {
            @Result(property = "id", column = "ID"),
            @Result(property = "uid", column = "UID"),
            @Result(property = "account", column = "ACCOUNT"),
            @Result(property = "accountTypeCode", column = "ACCOUNT_TYPE"),
            @Result(property = "rdTime", column = "RD_TIME"),
            @Result(property = "info", column = "INFO"),
            @Result(property = "passApprCode", column = "PASS_APPR"),
            @Result(property = "apprInfo", column = "APPR_INFO"),
            @Result(property = "statusCode", column = "STATUS"),

    })
    List<PsUserAccount> getLoginPsAccount(@Param("account")String account, @Param("accountType")int accountType);




    // 查询条件待定

/*
    @Select("SELECT ID, NAME, CODE, CRT_TIME, UPT_TIME, VP FROM PS_USER" +
            " WHERE NAME=#{name} AND CODE=#{code}")
    @ResultMap("person")
    PsUser findOneByNameCode(@Param("name") String name, @Param("code") String code);

    @Select("SELECT ID, NAME, CODE, CRT_TIME, UPT_TIME, VP FROM PS_USER" +
            " WHERE ID=#{id}")
    @ResultMap("person")
    PsUser findOneById(int id);
*/

}
