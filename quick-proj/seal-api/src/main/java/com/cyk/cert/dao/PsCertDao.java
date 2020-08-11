package com.cyk.cert.dao;


import com.cyk.cert.entity.PsSealCert;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface PsCertDao {

    /**
     * 获得用户最近的证书
     */
    @Select("SELECT ID, UID, UUID, ALG, SKID, PK, VALID_ST, VALID_END, VALID_INFO, " +
            " ISSUER_ID, OWNER_DN, RD_TIME, STATUS, CERT_DATA FROM PS_SEAL_CER " +
            " WHERE UID=#{uid} AND ALG=#{alg} ORDER BY ID DESC LIMIT 1")
    @Results(id = "psSealCert",value = {
            @Result(property = "id", column = "ID"),
            @Result(property = "uid", column = "UID"),
            @Result(property = "uuid", column = "UUID"),
            @Result(property = "alg", column = "ALG"),
            @Result(property = "skid", column = "SKID"),
            @Result(property = "pk", column = "PK"),
            @Result(property = "validSt", column = "VALID_ST"),
            @Result(property = "validEnd", column = "VALID_END"),
            @Result(property = "validInfo", column = "VALID_INFO"),
            @Result(property = "issuerId", column = "ISSUER_ID"),
            @Result(property = "ownerDn", column = "OWNER_DN"),
            @Result(property = "rdTime", column = "RD_TIME"),
            @Result(property = "statusCode", column = "STATUS"),
            @Result(property = "certData", column = "CERT_DATA"),
    })
    PsSealCert findByUidAlg(@Param("uid")String uid, @Param("alg") String alg);


    @Select("SELECT ID, UID, UUID, ALG, SKID, PK, VALID_ST, VALID_END, VALID_INFO, " +
            " ISSUER_ID, OWNER_DN, RD_TIME, STATUS, CERT_DATA FROM PS_SEAL_CER " +
            " WHERE UUID=#{uuid} ")
    @ResultMap("psSealCert")
    PsSealCert findByUuid(@Param("uuid")String uuid);

    @Insert("INSERT INTO PS_SEAL_CER(UID, UUID, ALG, SKID, PK, VALID_ST, VALID_END, VALID_INFO," +
            " ISSUER_ID, OWNER_DN, RD_TIME, STATUS, CERT_DATA) " +
            " VALUES(#{uid}, #{uuid}, #{alg}, #{skid}, #{pk}, #{validSt}, #{validEnd}, " +
            " #{validInfo}, #{issuerId}, #{ownerDn}, #{rdTime}, #{statusCode}, #{certData}) ")
    int addCertRnd(PsSealCert rnd);

    @Update("UPDATE PS_SEAL_CER SET STATUS=#{statusCode} WHERE UUID=#{uuid}")
    int uptCertStatusByUUId(@Param("uuid")String uuid, @Param("statusCode")int status);

    @Update("UPDATE PS_SEAL_CER SET UID=#{uid}, ALG=#{alg}, SKID=#{skid}, PK=#{pk}, " +
            " VALID_ST=#{validSt}, VALID_END=#{validEnd}, VALID_INFO=#{validInfo}," +
            " ISSUER_ID=#{issuerId}, OWNER_DN=#{ownerDn}, RD_TIME=#{rdTime}, " +
            " STATUS=#{statusCode}, CERT_DATA=#{certData} " +
            " WHERE UUID=#{uuid}")
    int uptCertRnd(PsSealCert rnd);


    @Select("SELECT MAX(SKID) FROM PS_SEAL_CER")
    int getnowmax();







}
