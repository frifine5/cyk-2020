package com.cyk.file.dao;


import com.cyk.file.entity.EntFileEntity;
import com.cyk.file.entity.EntFileEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 企业文件dao
 */
@Mapper
@Repository
public interface EntFileDao {

    @Select("SELECT ID, UUID, OWNER_ID, FILE_NAME, FILE_PATH, RD_TIME, SIGNED " +
            " FROM ENT_FILE_RD WHERE SIGNED = 0 AND OWNER_ID = #{uid}" +
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
    List<EntFileEntity> getOnePreFiles(String uid);


    @Insert("INSERT INTO ENT_FILE_RD(UUID, OWNER_ID, FILE_NAME, FILE_PATH, RD_TIME, SIGNED )" +
            " VALUES(#{uuid}, #{ownerId}, #{fileName}, #{filePath}, #{rdTime}, #{signedCode} )")
    int addFileRnd(EntFileEntity rnd);

    @Select("SELECT ID, UUID, OWNER_ID, FILE_NAME, FILE_PATH, RD_TIME, SIGNED " +
            " FROM ENT_FILE_RD WHERE UUID=#{uuid} ")
    EntFileEntity findOneByid(String uuid);

    @Delete("DELETE FROM ENT_FILE_RD WHERE SIGNED = 0 AND UUID=#{uuid} ")
    int deletePreFileById(String uuid);



    @Select("SELECT ID, UUID, OWNER_ID, FILE_NAME, FILE_PATH, RD_TIME, SIGNED " +
            " FROM ENT_FILE_RD WHERE SIGNED = 0 AND OWNER_ID = #{uid}" +
            " ORDER BY ID DESC LIMIT #{index}, 5")
    @ResultMap("psFiles")
    List<EntFileEntity> getOneMorePreFiles(String uid, int index);

    @Select("SELECT ID, UUID, OWNER_ID, FILE_NAME, FILE_PATH, RD_TIME, SIGNED " +
            " FROM ENT_FILE_RD WHERE SIGNED > 0 AND OWNER_ID = #{uid}" +
            " ORDER BY ID DESC LIMIT #{index}, 5")
    @ResultMap("psFiles")
    List<EntFileEntity> getOneMoreSignFiles(String uid, int index);

    @Select("SELECT ID, UUID, OWNER_ID, FILE_NAME, FILE_PATH, RD_TIME, SIGNED " +
            " FROM ENT_FILE_RD WHERE SIGNED > 0 AND OWNER_ID = #{uid}" +
            " ORDER BY ID DESC")
    @ResultMap("psFiles")
    List<EntFileEntity> getOneAllSignFiles(String uid, int index);


}
