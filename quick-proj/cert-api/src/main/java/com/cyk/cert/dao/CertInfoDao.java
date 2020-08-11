package com.cyk.cert.dao;


import com.cyk.cert.entity.CertRndInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CertInfoDao {


    /**
     * 通过序列号查询证书信息
     */
    @Select("SELECT ID, SERIAL_NUMBER, CERT_TYPE, ALG_TYPE, DN, VALID_ST, VALID_END, " +
            " CER_DATA, RD_TIME, STATUS, PRE_SERIAL " +
            " FROM CERT_RND WHERE SERIAL_NUMBER=#{serial}")
    @Results(id="certInfo", value = {
            @Result(property = "id", column = "ID"),
            @Result(property = "serial", column = "SERIAL_NUMBER"),
            @Result(property = "certTypeCode", column = "CERT_TYPE"),
            @Result(property = "algType", column = "ALG_TYPE"),
            @Result(property = "dn", column = "DN"),
            @Result(property = "validSt", column = "VALID_ST"),
            @Result(property = "validEnd", column = "VALID_END"),
            @Result(property = "cerData", column = "CER_DATA"),
            @Result(property = "rdTime", column = "RD_TIME"),
            @Result(property = "statusCode", column = "STATUS"),
            @Result(property = "preSerial", column = "PRE_SERIAL"),
    })
    CertRndInfo findCertBySerial(@Param("serial") String serial);


    /**
     * 插入证书信息记录
     */
    @Insert("INSERT INTO CERT_RND(SERIAL_NUMBER, CERT_TYPE, ALG_TYPE, DN, " +
            " VALID_ST, VALID_END, CER_DATA, RD_TIME, STATUS, PRE_SERIAL)" +
            " VALUES(#{serial}, #{certTypeCode}, #{algType}, #{dn}, " +
            " #{validSt}, #{validEnd}, #{cerData}, #{rdTime}, #{statusCode}, #{preSerial} ) ")
    int addCertRnd(CertRndInfo rnd);


    /**
     * 更新证书状态
     */
    @Update("UPDATE CERT_RND SET STATUS=#{serial} WHERE STATUS=#{status}")
    int uptCertStatus(@Param("serial") String serial, @Param("status") int statusCode);


    /*
    ID,SERIAL_NUMBER,CERT_TYPE,ALG_TYPE,DN,VALID_ST,VALID_END,CER_DATA,RD_TIME,STATUS,PRE_SERIAL

     */


}

