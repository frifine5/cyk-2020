package com.cyk.user.dao;


import com.cyk.user.bean.OuExtendInfo;
import com.cyk.user.bean.OuVo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface OuDao {



    @Select("SELECT OUGUID, OUCODE, OUNAME, OUSHORTNAME, POSTALCODE, UPDATETIME " +
            "FROM XJ_TYSF_OU WHERE OUGUID=#{ouguid}")
    @Results(id="xjOuSlp", value = {
            @Result(property = "ouguid", column = "OUGUID"),
            @Result(property = "oucode", column = "OUCODE"),
            @Result(property = "ouname", column = "OUNAME"),
            @Result(property = "oushortname", column = "OUSHORTNAME"),
            @Result(property = "postalcode", column = "POSTALCODE"),
            @Result(property = "updatetime", column = "UPDATETIME"),

    })
    OuVo findOuById(String ouguid);



    // 机构同步的 增删改
    @Insert("Insert ignore into xj_tysf_ou(ouguid, oucode, ouname, oushortname, ordernumber, " +
            "description, address, postalcode, tel, baseouguid, " +
            "issubwebflow, parentouguid, oucodelevel, haschildou, haschilduser, " +
            "updatetime, ordernumberfull, oulevel, imported, issbgj) " +
            "values( #{ouguid}, #{oucode}, #{ouname}, #{oushortname}, #{ordernumber}, " +
            "#{description}, #{address}, #{postalcode}, #{tel}, #{baseouguid}, " +
            "#{issubwebflow}, #{parentouguid}, #{oucodelevel}, #{haschildou}, #{haschilduser}, " +
            "#{updatetime}, #{ordernumberfull}, #{oulevel}, NULL, #{issbgj} )")
    int addSynOu(OuVo rnd);

    @Delete("delete from xj_tysf_ou where ouguid=#{ouguid}")
    int delSynOu(String ouguid);

    @Update("update xj_tysf_ou set oucode=#{oucode}, ouname=#{ouname}, oushortname=#{oushortname}, ordernumber=#{ordernumber}," +
            " description=#{description}, address=#{address}, postalcode=#{postalcode}, tel=#{tel}, baseouguid=#{baseouguid}," +
            " issubwebflow=#{issubwebflow}, parentouguid=#{parentouguid}, oucodelevel=#{oucodelevel}, haschildou=#{haschildou}, haschilduser=#{haschilduser}, " +
            " updatetime=#{updatetime}, ordernumberfull=#{ordernumberfull}, oulevel=#{oulevel}, issbgj=#{issbgj} " +
            " where ouguid=#{ouguid}")
    int uptSynOu(OuVo rnd);


    // 机构扩展 同步的 增删改
    @Insert("Insert ignore into xj_tysf_ou_extend(ouguid, isindependence, oufax, oucertguid, oucertcontent, oucertname, individuationimgpath) " +
            " values(#{ouguid}, #{isindependence}, #{oufax}, #{oucertguid}, #{oucertcontent}, #{oucertname}, #{individuationimgpath})")
    int addSynOuExt(OuExtendInfo rnd);

    @Delete("delete from xj_tysf_ou_extend where ouguid=#{ouguid}")
    int delSynOuExt(String ouguid);

    @Update("update xj_tysf_ou_extend set isindependence=#{isindependence}, oufax=#{oufax}, oucertguid=#{oucertguid}, " +
            " oucertcontent=#{oucertcontent}, oucertname=#{oucertname}, individuationimgpath=#{individuationimgpath}" +
            " where ouguid=#{ouguid}")
    int uptSynOuExt(OuExtendInfo rnd);




}
