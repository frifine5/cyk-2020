package com.cyk.per.p.dao;

import com.cyk.per.p.entity.PsStamp;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface PsStampDao {


    // 根据主键查询
    @Select("SELECT ID, UID, KS_TYPE, KS_PATH, KS_CERT, CRT_TIME, VALID_ST, VALID_END, UPT_TIME, ENT_INFO " +
            " FROM PS_STAMP WHERE ID=#{id}")
    @Results(id="psStamp", value = {
            @Result(property = "id" , column = "ID"),
            @Result(property = "uid" , column = "UID"),
            @Result(property = "ksType" , column = "KS_TYPE"),
            @Result(property = "ksPath" , column = "KS_PATH"),
            @Result(property = "ksCert" , column = "KS_CERT"),
            @Result(property = "crtTime" , column = "CRT_TIME"),
            @Result(property = "validSt" , column = "VALID_ST"),
            @Result(property = "validEnd" , column = "VALID_END"),
            @Result(property = "uptTime" , column = "UPT_TIME"),
            @Result(property = "entInfo" , column = "ENT_INFO"),
    })
    PsStamp findPsStampById(String id);

    // 插入
    @Insert("INSERT INTO PS_STAMP(ID, UID, KS_TYPE, KS_PATH, KS_CERT, CRT_TIME, VALID_ST, VALID_END, UPT_TIME, ENT_INFO) " +
            " VALUES(#{id}, #{uid}, #{ksType}, #{ksPath}, #{ksCert}, #{crtTime}, #{validSt}, #{validEnd}, #{uptTime}, #{entInfo})")
    int addOne(PsStamp nrd);

    @Update("UPDATE PS_STAMP SET KS_TYPE=#{ksType}, KS_PATH=#{ksPath}, KS_CERT=#{ksCert}, CRT_TIME==#{crtTime}," +
            " VALID_ST=#{validSt}, VALID_END=#{validEnd}, UPT_TIME=#{uptTime}, ENT_INFO=#{entInfo}" +
            " WHERE ID=#{id}")
    int uptOne(PsStamp record);

    @Select("SELECT ID, UID, KS_TYPE, KS_PATH, KS_CERT, CRT_TIME, VALID_ST, VALID_END, UPT_TIME, ENT_INFO " +
            " FROM PS_STAMP WHERE UID=#{uid}")
    @ResultMap("psStamp")
    List<PsStamp> findPsStampsByUid(int uid);

}
