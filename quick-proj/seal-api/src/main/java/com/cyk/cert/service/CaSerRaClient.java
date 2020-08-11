package com.cyk.cert.service;

import com.cyk.common.HttpUtils;
import com.cyk.common.ParamsUtil;
import com.cyk.common.ex.OptFailException;
import com.cyk.ioclient.wskt.WebSocketClient;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Base64;

@Service
public class CaSerRaClient {

    Logger logger = LoggerFactory.getLogger(CaSerRaClient.class);


    @Value("${ca.url:http://localhost:11005/}")
    String url;

    @Value("${ca.rentId:1}")
    String rentId;

    @Value("${ca.prjId:1}")
    String prjId;

    @Value("${ca.prjPin:88998899}")
    String prjPin;


    /**
     * 返回证书
     */
    public String cerRegBusiness(int index, String dn) throws OptFailException, InterruptedException, UnsupportedEncodingException {

        String urlToken = url + "/api/certService/token";
        String urlReg = url + "/api/certService/ra";
        // token  - rndB

        String rndA = "12345678aabbccdd";
        String pin = prjPin;
        JSONObject tokenJson = new JSONObject();
        tokenJson.put("rentId", rentId);
        tokenJson.put("projId", prjId);
        tokenJson.put("rndA", rndA);

        String tokenResult = HttpUtils.httpSendAndReceive(tokenJson.toString(), urlToken);

        JSONObject rtnTokenJson = JSONObject.fromObject(tokenResult);
        String rndB = rtnTokenJson.getString("data");
        if ("null".equals(rndB)) throw new OptFailException(tokenResult);

        String ctx = rndA + rndB + pin;

        String token = "";
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(ctx.getBytes("UTF-8"));
            byte[] tokenByt = messageDigest.digest();
            token = Base64.getEncoder().encodeToString(tokenByt);
        } catch (Exception e) {
            throw new RuntimeException("token生成失败：" + e.getMessage());
        }

        // 导出公钥
        JSONObject reqBody1 = new JSONObject();
        reqBody1.put("function", "exportEccPubkey");
        reqBody1.put("index", index);
        reqBody1.put("encode", true);

        String pkbResult = WebSocketClient.sendMsg(reqBody1.toString());
        JSONObject pkrJson = JSONObject.fromObject(pkbResult);
        if (0 != pkrJson.getInt("code")) {
            throw new OptFailException("导出公钥失败：" + pkrJson.get("msg"));
        }
        String pkb = pkrJson.getString("data");
        if (ParamsUtil.checkNullSymbal(pkb)) {
            throw new OptFailException("导出公钥失败：" + index + "号索引位公钥为空");
        }
        JSONObject reqBody = new JSONObject();
        reqBody.put("taskCode", "applyCert");
        reqBody.put("reqType", 1);
        reqBody.put("certType", 1);

        JSONObject dnpk = new JSONObject();
        dnpk.put("dn", dn);
        dnpk.put("puk", pkb);
        reqBody.put("body", dnpk.toString());
        String regBodyStr = Base64.getEncoder().encodeToString(reqBody.toString().getBytes("UTF-8"));


        JSONObject reqJson2 = new JSONObject();
        reqJson2.put("function", "eccSign");
        reqJson2.put("index", 2);
        reqJson2.put("data", regBodyStr);
        reqJson2.put("pkPre", false);

        String result2 = WebSocketClient.sendMsg(reqJson2.toString()); // 返回签名结果
        JSONObject rtnJson2 = JSONObject.fromObject(result2);
        if(0!=rtnJson2.getInt("code"))throw new OptFailException("invoke mms ex: " + rtnJson2.getString("msg"));

        String svStr2 = rtnJson2.getString("data");

        // 组请求包
        JSONObject regJson = new JSONObject();
        regJson.put("token", token);
        regJson.put("data", regBodyStr);
        regJson.put("signValue", svStr2);

        String regResult = HttpUtils.httpSendAndReceive(regJson.toString(), urlReg);
        logger.info("reg result >> " + regResult);
        JSONObject regCaResult = JSONObject.fromObject(regResult);
        if(0!=regCaResult.getInt("code"))throw new OptFailException("申请证书失败: " + regCaResult.getString("msg"));
        String signCert = regCaResult.getJSONObject("data").getString("signCert");
        if(ParamsUtil.checkNullSymbal(signCert)) throw new OptFailException("申请证书失败: CA服务返回的证书数据为空");

        return signCert;
    }







}

