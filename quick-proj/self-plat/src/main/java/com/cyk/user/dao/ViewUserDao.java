package com.cyk.user.dao;



import com.cyk.user.bean.ViewUser;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ViewUserDao {

    // plat 条件查询
    @Select("<script>" +
            "select u.userguid ID, u.displayname NAME, u.updatetime CRT_DATE, u.sex SEX, ou.ouname DEPT, ou.ouguid OUID, ou.oucode OU_CODE " +
            " from xj_tysf_user u left join xj_tysf_ou ou on u.ouguid = ou.ouguid " +
            "where 1=1 " +
            "<if test='name!=null and name != \"\" '>"+
            " AND u.displayname LIKE concat('%', #{name}, '%') "+
            "</if> " +
            "<if test='dept!=null and dept != \"\" '>"+
            " AND ou.ouname LIKE concat('%', #{dept}, '%') "+
            "</if> " +
            " limit #{index}, #{mount}" +
            "</script>")
    @Results(id="userList", value = {
            @Result(property = "id", column = "ID"),
            @Result(property = "name", column = "NAME"),
            @Result(property = "dept", column = "DEPT"),
            @Result(property = "crtDate", column = "CRT_DATE"),
            @Result(property = "sex", column = "SEX"),

    })
    List<ViewUser> queryUListCondition(@Param("name")String name, @Param("dept")String dept, @Param("index")int index, @Param("mount")int pageSize);


    // plat 条件查询
    @Select("<script>" +
            "select count(u.userguid)" +
            " from xj_tysf_user u left join xj_tysf_ou ou on u.ouguid = ou.ouguid " +
            "where 1=1 " +
            "<if test='name!=null and name != \"\" '>"+
            " AND u.displayname LIKE concat('%', #{name}, '%') "+
            "</if> " +
            "<if test='dept!=null and dept != \"\" '>"+
            " AND ou.ouname LIKE concat('%', #{dept}, '%') "+
            "</if> " +
            "</script>")
    int queryCountCondition(@Param("name")String name, @Param("dept")String dept);




}
