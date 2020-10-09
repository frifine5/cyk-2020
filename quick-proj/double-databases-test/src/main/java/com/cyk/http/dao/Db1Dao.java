package com.cyk.http.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface Db1Dao {


    @Select("select menu_name from ps_menu")
    List<String> getNames();

}
