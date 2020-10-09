package com.cyk.http.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface Db2Dao {

    @Select("select name from t_client_cert")
    List<String> getNames();

}
