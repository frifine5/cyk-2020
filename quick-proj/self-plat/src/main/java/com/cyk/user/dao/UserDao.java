package com.cyk.user.dao;


import com.cyk.user.bean.UserExtendInfo;
import com.cyk.user.bean.UserVo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserDao {


    @Select("SELECT USERGUID, LOGINID, PASSWORD, OUGUID, DISPLAYNAME, ISENABLED, SEX, FIRSTNAME " +
            " FROM XJ_TYSF_USER WHERE LOGINID=#{loginid} LIMIT 1")
    @Results(id="xjUserSlp", value = {
            @Result(property = "userguid", column = "USERGUID"),
            @Result(property = "loginid", column = "LOGINID"),
            @Result(property = "password", column = "PASSWORD"),
            @Result(property = "ouguid", column = "OUGUID"),
            @Result(property = "displayname", column = "DISPLAYNAME"),
            @Result(property = "isenabled", column = "ISENABLED"),
            @Result(property = "sex", column = "SEX"),
            @Result(property = "firstname", column = "FIRSTNAME"),

    })
    UserVo findOuById(String loginid);

    @Select("SELECT UID FROM XJ_TYSF_USER WHERE LOGINID=#{loginid} ")
    long findUserIdByGuid(String loginid);


    // 用户数据 同步的 增删改
    @Insert("Insert ignore into xj_tysf_user(userguid, loginid, password, ouguid, displayname, isenabled, title, leaderguid," +
            " ordernumber, telephoneoffice, mobile, email, description, telephonehome, fax," +
            " allowuseemail, sex, oucodelevel, updatetime, firstname, middlename, timezone, lastname, prelang, adloginid, " +
            " updatepwd, pinyininitials, row_id) " +
            " values(#{userguid}, #{loginid}, #{password}, #{ouguid}, #{displayname}, #{isenabled}, #{title}, #{leaderguid}, " +
            " #{ordernumber}, #{telephoneoffice}, #{mobile}, #{email}, #{description}, #{telephonehome}, #{fax}, " +
            " #{allowuseemail}, #{sex}, #{oucodelevel}, #{updatetime}, #{firstname}, #{middlename}, #{timezone}, #{lastname}, " +
            " #{prelang}, #{adloginid}, #{updatepwd}, #{pinyininitials}, #{row_id})")
    int addSynUser(UserVo rnd);

    @Delete("delete from xj_tysf_user where userguid=#{userguid}")
    int delSynUser(String userguid);

    @Update("update xj_tysf_user set loginid=#{loginid}, password=#{password}, ouguid=#{ouguid}, displayname=#{displayname}, isenabled=#{isenabled}, " +
            " title=#{title}, leaderguid=#{leaderguid}, ordernumber=#{ordernumber}, telephoneoffice=#{telephoneoffice}, mobile=#{mobile}, " +
            " email=#{email}, description=#{description}, telephonehome=#{telephonehome}, fax=#{fax}, allowuseemail=#{allowuseemail}, " +
            " sex=#{sex}, oucodelevel=#{oucodelevel}, updatetime=#{updatetime}, firstname=#{firstname}, middlename=#{middlename}, " +
            " timezone=#{timezone}, lastname=#{lastname}, prelang=#{prelang}, adloginid=#{adloginid}, " +
            " updatepwd=#{updatepwd}, pinyininitials=#{pinyininitials}, row_id=#{row_id}" +
            " where userguid=#{userguid}")
    int uptSynUser(UserVo rnd);



    // 用户扩展数据 同步的 增删改
    @Insert("Insert ignore into xj_tysf_user_extend(userguid, usbkey, birthday, qqnumber, msnnumber, " +
            " piccontent, piccontenttype, postaladdress, postalcode, identitycardnum, " +
            " isdisable, ntx_extnumber, ntx_password, epassrnd, epassserial," +
            " epassguid, epasspwd, ad_account, shortmobile, carnum, row_id) " +
            " values( #{userguid}, #{usbkey}, #{birthday}, #{qqnumber}, #{msnnumber}," +
            " #{piccontent}, #{piccontenttype}, #{postaladdress}, #{postalcode}, #{identitycardnum}," +
            " #{isdisable}, #{ntx_extnumber}, #{ntx_password}, #{epassrnd}, #{epassserial}," +
            " #{epassguid}, #{epasspwd}, #{ad_account}, #{shortmobile}, #{carnum}, #{row_id} )")
    int addSynUserExt(UserExtendInfo rnd);

    @Delete("delete from xj_tysf_user_extend where userguid=#{userguid}")
    int delSynUserExt(String userguid);

    @Update("update xj_tysf_user_extend set usbkey=#{usbkey}, birthday=#{birthday}, qqnumber=#{qqnumber}, msnnumber=#{msnnumber}, " +
            " piccontent=#{piccontent}, piccontenttype=#{piccontenttype}, postaladdress=#{postaladdress}, postalcode=#{postalcode}, identitycardnum=#{identitycardnum}, " +
            " isdisable=#{isdisable}, ntx_extnumber=#{ntx_extnumber}, ntx_password=#{ntx_password}, epassrnd=#{epassrnd}, epassserial=#{epassserial}," +
            " epassguid=#{epassguid}, epasspwd=#{epasspwd}, ad_account=#{ad_account}, shortmobile=#{shortmobile}, carnum=#{carnum}, row_id=#{row_id} " +
            " where userguid=#{userguid}" )
    int uptSynUserExt(UserExtendInfo rnd);



}
