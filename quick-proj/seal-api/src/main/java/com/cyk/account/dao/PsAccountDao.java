package com.cyk.account.dao;


import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface PsAccountDao {


    @Select("SELECT ")
    @Results(id="psAccount", value = {
            @Result(property = "id", column = "ID"),
            @Result(property = "uid", column = "UID"),
            @Result(property = "account", column = "ACCOUNT"),
            @Result(property = "accountType", column = "ACCOUNT_TYPE"),
            @Result(property = "oldNo", column = "RD_TIME"),
            @Result(property = "sealName", column = "INFO"),
            @Result(property = "sealCode", column = "PASS_APPR"),
            @Result(property = "sealType", column = "APPR_INFO"),
            @Result(property = "sealType", column = "STATUS"),
    })
    void findAccountOnLogin(@Param("account")String account, @Param("accountType")String accountType);






}
