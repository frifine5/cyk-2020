package com.cyk.image.dao;

import com.cyk.image.entity.PsSealImage;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@Mapper
public interface PersonImageDao {


    /**
     * 查个人的所有
     */
    @Select("SELECT ID, UUID, UID, SEAL_NAME, SEAL_TYPE, SEAL_WIDTH, SEAL_HEIGHT, " +
            " SEAL_PIC_TYPE, SEAL_PIC_DATA, RD_TIME, STATUS, CERT_ID, SEAL_CODE, SEAL_DATA " +
            " FROM PS_SEAL_IMAGE WHERE UID=#{uid} ")
    @Results(id="psImage", value = {
            @Result(property = "id" , column = "ID"),
            @Result(property = "uuid" , column = "UUID"),
            @Result(property = "uid" , column = "UID"),
            @Result(property = "sealName" , column = "SEAL_NAME"),
            @Result(property = "sealTypeCode" , column = "SEAL_TYPE"),
            @Result(property = "width" , column = "SEAL_WIDTH"),
            @Result(property = "height" , column = "SEAL_HEIGHT"),
            @Result(property = "picType" , column = "SEAL_PIC_TYPE"),
            @Result(property = "picData" , column = "SEAL_PIC_DATA"),
            @Result(property = "rdTime" , column = "RD_TIME"),
            @Result(property = "statusCode" , column = "STATUS"),
            @Result(property = "certId" , column = "CERT_ID"),
            @Result(property = "sealCode" , column = "SEAL_CODE"),
            @Result(property = "sealData" , column = "SEAL_DATA"),

    })
    List<PsSealImage> queryByUid(@Param("uid") String uid);


    @Insert("INSERT INTO PS_SEAL_IMAGE(UUID, UID, SEAL_NAME, SEAL_TYPE, SEAL_WIDTH, SEAL_HEIGHT, " +
            " SEAL_PIC_TYPE, SEAL_PIC_DATA, RD_TIME, STATUS) " +
            " VALUES(#{uuid}, #{uid}, #{sealName}, #{sealTypeCode}, #{width}, #{height}, " +
            " #{picType}, #{picData}, #{rdTime}, #{statusCode}) ")
    int addPsImageRd(PsSealImage rnd);

    @Update("UPDATE PS_SEAL_IMAGE SET STATUS=#{status} WHERE UUID=#{uuid}")
    int uptPsImageStatus(@Param("uuid") String uuid, @Param("status") int statusCode);

    @Update("UPDATE PS_SEAL_IMAGE SET SEAL_CODE=#{sealCode}, SEAL_DATA=#{sealData}, STATUS=#{statusCode} WHERE UUID=#{uuid}")
    int uptPsImageSeal(PsSealImage rnd);


    @Update("UPDATE PS_SEAL_IMAGE SET CERT_ID=#{certId} WHERE UUID=#{uuid}")
    int uptPsImageCertId(@Param("uuid") String uuid, @Param("certId") String certId);


    @Delete("DELETE FROM PS_SEAL_IMAGE WHERE UUID=#{uuid}")
    int delPsImageStatus(@Param("uuid") String uuid);

    @Select("SELECT ID, UUID, UID, SEAL_NAME, SEAL_TYPE, SEAL_WIDTH, SEAL_HEIGHT, " +
            " SEAL_PIC_TYPE, SEAL_PIC_DATA, RD_TIME, STATUS, CERT_ID, SEAL_CODE, SEAL_DATA " +
            " FROM PS_SEAL_IMAGE WHERE UID=#{uid} AND SEAL_TYPE=0 ")
    @ResultMap("psImage")
    PsSealImage findModeSealImage(@Param("uid") String uid);


    @Select("SELECT ID, UUID, UID, SEAL_NAME, SEAL_TYPE, SEAL_WIDTH, SEAL_HEIGHT, " +
            " SEAL_PIC_TYPE, SEAL_PIC_DATA, RD_TIME, STATUS, CERT_ID, SEAL_CODE, SEAL_DATA " +
            " FROM PS_SEAL_IMAGE WHERE UUID=#{uuid}")
    @ResultMap("psImage")
    PsSealImage findSealImageByImageuuid(@Param("uuid") String uuid);

}
