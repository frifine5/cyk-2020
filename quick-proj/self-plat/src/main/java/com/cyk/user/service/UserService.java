package com.cyk.user.service;


import com.cyk.user.bean.ViewUser;
import com.cyk.user.dao.ViewUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    ViewUserDao viewUserDao;



    public Map<String, Object> queryUserCondition(String name, String dept, int pageNo, int pageSize){
        pageSize = pageSize<1? 10: pageSize;
        int index = pageNo<1 ? 0: (pageNo-1) * pageSize;
        // 真查询： 查总数 >> 分页条件查
        int total = viewUserDao.queryCountCondition(name, dept);
        List<ViewUser> userList = viewUserDao.queryUListCondition(name, dept, index, pageSize);
        Map<String, Object> rtnMap = new HashMap<>();
        rtnMap.put("pageNo", index<1?1:pageNo);
        rtnMap.put("pageSize", pageSize);
        rtnMap.put("total",total);
        rtnMap.put("list",userList);
        return rtnMap;
    }


}
