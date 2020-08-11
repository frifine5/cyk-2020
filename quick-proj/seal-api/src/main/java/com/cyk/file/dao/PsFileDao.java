package com.cyk.file.dao;


import com.cyk.file.entity.PsFileEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 个人文件dao
 */
@Mapper
@Repository
public interface PsFileDao {

    @Select("SELECT ID, UUID, OWNER_ID, FILE_NAME, FILE_PATH, RD_TIME, SIGNED " +
            " FROM PS_FILE_RD WHERE SIGNED = 0 AND OWNER_ID = #{uid}" +
            " ORDER BY ID DESC")
    @Results(id = "psFiles", value = {
            @Result(property = "id", column = "ID"),
            @Result(property = "uuid", column = "UUID"),
            @Result(property = "ownerId", column = "OWNER_ID"),
            @Result(property = "fileName", column = "FILE_NAME"),
            @Result(property = "filePath", column = "FILE_PATH"),
            @Result(property = "rdTime", column = "RD_TIME"),
            @Result(property = "signedCode", column = "SIGNED"),

    })
    List<PsFileEntity> getOnePreFiles(String uid);


    @Insert("INSERT INTO PS_FILE_RD(UUID, OWNER_ID, FILE_NAME, FILE_PATH, RD_TIME, SIGNED )" +
            " VALUES(#{uuid}, #{ownerId}, #{fileName}, #{filePath}, #{rdTime}, #{signedCode} )")
    int addFileRnd(PsFileEntity rnd);

    @Select("SELECT ID, UUID, OWNER_ID, FILE_NAME, FILE_PATH, RD_TIME, SIGNED " +
            " FROM PS_FILE_RD WHERE UUID=#{uuid} ")
    @ResultMap("psFiles")
    PsFileEntity findOneByid(String uuid);


    @Delete("DELETE FROM PS_FILE_RD WHERE SIGNED = 0 AND UUID=#{uuid} ")
    int deletePreFileById(String uuid);


    @Select("SELECT ID, UUID, OWNER_ID, FILE_NAME, FILE_PATH, RD_TIME, SIGNED " +
            " FROM PS_FILE_RD WHERE SIGNED = 0 AND OWNER_ID = #{uid}" +
            " ORDER BY ID DESC LIMIT #{index}, 5")
    @ResultMap("psFiles")
    List<PsFileEntity> getOneMorePreFiles(String uid, int index);

    @Select("SELECT ID, UUID, OWNER_ID, FILE_NAME, FILE_PATH, RD_TIME, SIGNED " +
            " FROM PS_FILE_RD WHERE SIGNED > 0 AND OWNER_ID = #{uid}" +
            " ORDER BY ID DESC LIMIT #{index}, 5")
    @ResultMap("psFiles")
    List<PsFileEntity> getOneMoreSignFiles(@Param("uid") String uid, @Param("index") int index);

    @Select("SELECT ID, UUID, OWNER_ID, FILE_NAME, FILE_PATH, RD_TIME, SIGNED " +
            " FROM PS_FILE_RD WHERE SIGNED > 0 AND OWNER_ID = #{uid}" +
            " ORDER BY ID DESC")
    @ResultMap("psFiles")
    List<PsFileEntity> getOneAllSignFiles(String uid);


}
