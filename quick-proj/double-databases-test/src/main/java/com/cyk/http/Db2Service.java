package com.cyk.http;

import com.cyk.config.ds.DS;
import com.cyk.http.dao.Db2Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Db2Service {

    @Autowired
    Db2Dao db2Dao;

    @DS("db2")
    public List<String> getNames(){

       return db2Dao.getNames();
    }





}
