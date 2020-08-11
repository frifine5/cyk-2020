package com.cyk.t.protocol.mws;

import com.cyk.t.protocol.mws.service.MmsFunService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.stereotype.Service;

import java.util.Base64;

/**
 * 标记并且前置参数处理类
 * @author WangChengyu
 * 2020/5/18 16:38
 */
@Service
public class MmsServiceNavigate {

    Logger log = LoggerFactory.getLogger(MmsServiceNavigate.class);

    public MmsServiceNavigate(){
        super();
        if(mmsFunService == null){
            mmsFunService = new MmsFunService();
        }
    }

    @Autowired
    MmsFunService mmsFunService;


    @MmsFunc
    public String genRandom(String param){

        log.info("param >>> " + param);
        try{
            JSONObject reqJson = JSONObject.fromObject(param);
            int len = reqJson.getInt("len");
            if(len < 1){
                throw new IllegalArgumentException("length is out of scope :len = " + len);
            }else{
                byte[] rnd = mmsFunService.genRandom(len);
                return Hex.toHexString(rnd);
            }
        }catch (Exception e){
            throw new IllegalArgumentException("参数错误", e);
        }
    }

    @MmsFunc
    public String eccSign(String param){
        log.info("param >>> " + param);
        try{
            JSONObject reqJson = JSONObject.fromObject(param);
            int index = reqJson.getInt("index");
            String data = reqJson.getString("data");
            boolean isPre = reqJson.getBoolean("pkPre");
            byte[] ctx = Base64.getDecoder().decode(data);
            byte[] sv = mmsFunService.eccSign(ctx, index, isPre);
            return Base64.getEncoder().encodeToString(sv);
        }catch (Exception e){
            throw new IllegalArgumentException("参数错误", e);
        }
    }

    @MmsFunc
    public Object eccVerify(String param){
        log.info("param >>> " + param);
        try{
            JSONObject reqJson = JSONObject.fromObject(param);
            int index = reqJson.getInt("index");
            String data = reqJson.getString("data");
            String svData = reqJson.getString("sv");
            boolean isPre = reqJson.getBoolean("pkPre");
            byte[] ctx = Base64.getDecoder().decode(data);
            byte[] sv = Base64.getDecoder().decode(svData);
            boolean vs = mmsFunService.eccVerify(ctx, sv, index, isPre);
            JSONObject rtnJson = new JSONObject();
            rtnJson.put("pass", vs);
            rtnJson.put("info", vs?"验证通过": "验证失败");
            return rtnJson;
        }catch (Exception e){
            throw new IllegalArgumentException("参数错误", e);
        }
    }

    @MmsFunc
    public Object eccVerifyOut(String param){
        log.info("param >>> " + param);
        try{
            JSONObject reqJson = JSONObject.fromObject(param);
            String data = reqJson.getString("data");
            String puk = reqJson.getString("puk");
            String svData = reqJson.getString("sv");
            boolean isPre = reqJson.getBoolean("pkPre");
            byte[] ctx = Base64.getDecoder().decode(data);
            byte[] pk = Base64.getDecoder().decode(puk);
            byte[] sv = Base64.getDecoder().decode(svData);
            boolean vs = mmsFunService.eccVerifyOut(ctx, sv, pk, isPre);
            JSONObject rtnJson = new JSONObject();
            rtnJson.put("pass", vs);
            rtnJson.put("info", vs?"验证通过": "验证失败");
            return rtnJson;
        }catch (Exception e){
            throw new IllegalArgumentException("参数错误", e);
        }
    }

    @MmsFunc
    public String exportEccPubkey(String param){
        log.info("param >>> " + param);
        try{
            JSONObject reqJson = JSONObject.fromObject(param);
            int index = reqJson.getInt("index");
            boolean encode = reqJson.getBoolean("encode");
            byte[] data = mmsFunService.exportEccPubkey(index, encode);
            return Base64.getEncoder().encodeToString(data);
        }catch (Exception e){
            throw new IllegalArgumentException("参数错误", e);
        }
    }


}
