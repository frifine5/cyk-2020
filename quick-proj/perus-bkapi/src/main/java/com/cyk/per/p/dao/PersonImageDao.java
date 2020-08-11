package com.cyk.per.p.dao;

import com.cyk.per.p.entity.PsImage;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface PersonImageDao {


    // 根据主键查询
    @Select("SELECT ID, UID, TYPE, PATH, CRT_TIME FROM PS_PIC WHERE ID=#{id}")
    @Results(id="psImage", value = {
            @Result(property = "id" , column = "ID"),
            @Result(property = "uid" , column = "UID"),
            @Result(property = "type" , column = "TYPE"),
            @Result(property = "imgPath" , column = "PATH"),
            @Result(property = "crtTime" , column = "CRT_TIME"),
    })
    PsImage findPsImageById(int id);

    // 插入
    @Insert("INSERT INTO PS_PIC(UID, TYPE, PATH, CRT_TIME) " +
            " VALUES(#{uid}, #{type}, #{imgPath}, #{crtTime})")
    int addOne(PsImage nrd);

    // 查询名章是否已经生成
    @Select("SELECT ID, UID, TYPE, PATH, CRT_TIME FROM PS_PIC WHERE UID=#{uid} AND TYPE= 1 LIMIT 1")
    @ResultMap("psImage")
    PsImage findExist(@Param("uid")int uid);

    // 更新记录
    @Update("UPDATE PS_PIC SET PATH=#{imgPath}, CRT_TIME=#{crtTime} WHERE UID=#{uid} AND TYPE=#{type}")
    int uptOneStamp(PsImage nrd);


    // 手写签
    @Select("SELECT ID, UID, TYPE, PATH, CRT_TIME FROM PS_PIC WHERE UID=#{uid} AND TYPE= 2 ORDER BY CRT_TIME DESC")
    @ResultMap("psImage")
    List<PsImage> findHandSign(@Param("uid")int uid );

    // 查询并按类型和时间排序
    @Select("SELECT ID, UID, TYPE, PATH, CRT_TIME FROM PS_PIC WHERE UID=#{uid} ORDER BY TYPE ASC, CRT_TIME DESC")
    @ResultMap("psImage")
    List<PsImage> queryAllOnUid(@Param("uid")int uid);

}
