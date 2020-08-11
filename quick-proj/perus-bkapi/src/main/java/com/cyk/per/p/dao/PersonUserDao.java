package com.cyk.per.p.dao;


import com.cyk.per.p.entity.PersonUser;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface PersonUserDao {

    @Select("SELECT ID, NAME, CODE, CRT_TIME, UPT_TIME, VP FROM PS_USER")
    @Results(id="person", value = {
            @Result(property = "id" , column = "ID"),
            @Result(property = "name" , column = "NAME"),
            @Result(property = "code" , column = "CODE"),
            @Result(property = "crtTime" , column = "CRT_TIME"),
            @Result(property = "uptTime" , column = "UPT_TIME"),
            @Result(property = "vp" , column = "VP"),

    })
    List<PersonUser> queryPUser();

    @Insert("INSERT INTO PS_USER(NAME, CODE, CRT_TIME, UPT_TIME, VP) " +
            " VALUES(#{name}, #{code}, #{crtTime}, #{uptTime}, #{vp})")
    int addOne(PersonUser user);

    @Update("UPDATE PS_USER SET NAME=#{name}, CODE=#{code}, UPT_TIME=#{crtTime}, VP=#{vp}" +
            " WHERE ID=#{id}")
    int uptOne(PersonUser user);

    @Update("UPDATE PS_USER SET NAME=#{name}, CODE=#{code}, UPT_TIME=#{crtTime}, VP=#{vp}" +
            " WHERE NAME=#{name} AND CODE=#{code}")
    int uptOneByNameCode(PersonUser user);

    @Select("SELECT ID, NAME, CODE, CRT_TIME, UPT_TIME, VP FROM PS_USER" +
            " WHERE NAME=#{name} AND CODE=#{code}")
    @ResultMap("person")
    PersonUser findOneByNameCode(@Param("name")String name, @Param("code")String code);

    @Select("SELECT ID, NAME, CODE, CRT_TIME, UPT_TIME, VP FROM PS_USER" +
            " WHERE ID=#{id}")
    @ResultMap("person")
    PersonUser findOneById(int id);

}
