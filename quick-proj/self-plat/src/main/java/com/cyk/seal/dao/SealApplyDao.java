package com.cyk.seal.dao;


import com.cyk.seal.entity.SealApplyEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SealApplyDao {



    @Select("SELECT ORDER_ID, DEPT_NAME, DEPT_CODE, AREA, ADDRESS, TEL, LINKMAN, REQ_PERSON, SEAL_NAME, SEAL_COLOR, VALID_START, VALID_END, VALID, PIC_DATA, PIC_WIDTH, PIC_HEIGHT, PIC_TYPE, RD_TIME, STATUS " +
            "FROM SEAL_APPLY WHERE DEPT_NAME=#{deptName} AND STATUS = 0")
    @Results(id="sealApply", value = {
            @Result(property = "orderId", column = "ORDER_ID"),
            @Result(property = "deptName", column = "DEPT_NAME"),
            @Result(property = "deptCode", column = "DEPT_CODE"),
            @Result(property = "area", column = "AREA"),
            @Result(property = "address", column = "ADDRESS"),
            @Result(property = "tel", column = "TEL"),
            @Result(property = "linkman", column = "LINKMAN"),
            @Result(property = "reqPerson", column = "REQ_PERSON"),
            @Result(property = "sealName", column = "SEAL_NAME"),
            @Result(property = "sealColor", column = "SEAL_COLOR"),
            @Result(property = "validStart", column = "VALID_START"),
            @Result(property = "validEnd", column = "VALID_END"),
            @Result(property = "valid", column = "VALID"),
            @Result(property = "picData", column = "PIC_DATA"),
            @Result(property = "picWidth", column = "PIC_WIDTH"),
            @Result(property = "picHeight", column = "PIC_HEIGHT"),
            @Result(property = "picType", column = "PIC_TYPE"),
            @Result(property = "rdTime", column = "RD_TIME"),
            @Result(property = "status", column = "STATUS"),

    })
    List<SealApplyEntity> findApplyOnDeptName(String deptName);




    @Insert("INSERT INTO SEAL_APPLY(ORDER_ID, DEPT_NAME, DEPT_CODE, AREA, ADDRESS, TEL, LINKMAN, REQ_PERSON, SEAL_NAME, SEAL_COLOR, VALID_START, VALID_END, VALID, PIC_DATA, PIC_WIDTH, PIC_HEIGHT, PIC_TYPE, RD_TIME, STATUS) " +
            " VALUES(#{orderId}, #{deptName}, #{deptCode}, #{area}, #{address}, #{tel}, #{linkman}, #{reqPerson}, #{sealName}, #{sealColor}, #{validStart}, #{validEnd}, #{valid}, #{picData}, #{picWidth}, #{picHeight}, #{picType}, #{rdTime}, #{status} ) ")
    int addSealApply(SealApplyEntity sealApply);


    @Update("UPDATE SEAL_APPLY SET PIC_DATA = #{picData}, PIC_TYPE=#{picType}, STATUS=1 WHERE ORDER_ID=#{orderId}")
    int uploadApplyPic(@Param("orderId") String orderId, @Param("picData")  byte[] picData, @Param("picType")  String picType);


    /**
     * 印章名集查重
     */
    @Select("<script>" +
            " SELECT COUNT(SEAL_NAME) FROM SEAL_APPLY WHERE SEAL_NAME IN " +
            "<foreach item='item' index='index' collection='list' " +
            " separator=',' open='(' close=')'> " +
            " #{item} " +
            "</foreach>" +
            "</script>")
    int checkSealNameInApply(@Param("list")List<String> sealNames);



}
