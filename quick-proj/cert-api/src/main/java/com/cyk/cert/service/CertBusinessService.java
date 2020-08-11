package com.cyk.cert.service;


import com.cyk.apira.dao.RaReqDao;
import com.cyk.apira.entity.RequestInfo;
import com.cyk.apiuser.dao.RentInfoDao;
import com.cyk.apiuser.entity.RentProjInfo;
import com.cyk.apiuser.entity.RentUser;
import com.cyk.cert.SM2CaCert;
import com.cyk.cert.SM2CertGenUtil;
import com.cyk.cert.dao.CertInfoDao;
import com.cyk.cert.entity.CertRndInfo;
import com.cyk.common.ParamsUtil;
import com.cyk.common.ex.NullResultException;
import com.cyk.common.ex.OptFailException;
import com.cyk.common.global.LocalCacheRepo;
import com.cyk.ioclient.wskt.WebSocketClient;
import com.cyk.number.GenNumberService;
import net.sf.json.JSONObject;
import org.bouncycastle.asn1.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.util.*;

@Service
public class CertBusinessService {
    Logger logger = LoggerFactory.getLogger(CertBusinessService.class);

    @Autowired
    GenNumberService genNumberService;

    @Autowired
    RentInfoDao rentInfoDao;

    @Autowired
    CertInfoDao certInfoDao;

    @Autowired
    RaReqDao raReqDao;


    @Value("${issueCert:}")
    String issueCert;


    /**
     * ra业务: api 入口
     */
    public Object vsAndBizDivide(String token, String data, String signValue)
            throws OptFailException {
        RequestInfo reqInfo = new RequestInfo();
        reqInfo.setReqId(ParamsUtil.getUUIDStr());

        // 验证token
        Object cache = LocalCacheRepo.getCache(token);
        boolean vs = false;
        try {
            RentProjInfo projInfo = (RentProjInfo) cache;
            String authType = projInfo.getAuthType();
            String authPuk = projInfo.getAuthPuk();
            reqInfo.setUid(projInfo.getUid());
            reqInfo.setReqData(Base64.getDecoder().decode(data));


            switch (authType.toLowerCase()) {
                case "sm2_sign":
                    vs = verifyBySm2(data, signValue, authPuk);
                    break;

                default:
                    vs = false;
                    break;
            }
        } catch (Exception e) {
            throw new OptFailException("验签失败：" + e.getMessage());
        }

        if (!vs) throw new OptFailException("token验证失败");

        // 业务
        Object rtn = null;
        try {
            String bizData = new String(reqInfo.getReqData(), "UTF-8");
            JSONObject bodyJson = JSONObject.fromObject(bizData);
            String taskCode = bodyJson.getString("taskCode");
            reqInfo.setCertType("" + bodyJson.getInt("certType"));
            reqInfo.setRdTime(new Date());

            switch (taskCode) {
                case "applyCert": // 注册证书
                    rtn = regCert(bodyJson);
                    break;
                case "modifyCert": // 变更证书
                    rtn = modifyCert(bodyJson);
                    break;
                case "delayCert": // 延期证书
                    rtn = delayCert(bodyJson);
                    break;
                case "revokeCert": // 注销证书
                    rtn = revokeCert(bodyJson);
                    break;
                default:
                    rtn = null;
                    break;
            }
            if (rtn == null) {
                reqInfo.setCode("1");
                reqInfo.setErrMsg("无效业务类型参数");
            } else {
                reqInfo.setCode("0");
            }
            try {
                raReqDao.addReqRnd(reqInfo);//忽略
            } catch (Exception e) {
                logger.error("请求参数的记录保存失败", e);
            }

        } catch (OptFailException oe) {
            throw oe;
        } catch (Exception e) {
            throw new OptFailException(e);
        }
        return rtn;
    }


    /**
     * 证书注册
     */
    Map<String, String> regCert(JSONObject bodyJson) throws OptFailException {
        try {

            int reqType = bodyJson.getInt("reqType");
            int certType = bodyJson.getInt("certType");
            String body = bodyJson.getString("body");

            CertRndInfo certInfo = new CertRndInfo();
            long serial = genNumberService.getNumber();
            certInfo.setSerial("" + serial);
            certInfo.setCertTypeCode(certType);
            certInfo.setAlgType("sm2");
            Date st = new Date();
            Calendar cld = Calendar.getInstance();
            cld.setTime(st);
            cld.set(Calendar.YEAR, cld.get(Calendar.YEAR) + 1);
            Date et = cld.getTime();

            certInfo.setValidSt(st);
            certInfo.setValidEnd(et);

            // 组包
            byte[] cerTbs = null;
            ASN1Sequence issue = (ASN1Sequence) ((ASN1Sequence) ((ASN1Sequence) ASN1Sequence.fromByteArray(
                    Base64.getDecoder().decode(issueCert))).getObjectAt(0)).getObjectAt(5);
            if (reqType == 0) {
                byte[] csr = Base64.getDecoder().decode(body);
                certInfo.setDn(transfDn(csr));
                cerTbs = SM2CertGenUtil.generateCertTBSCert(serial, issue, st, et, csr);
            } else {
                // dn 解析
                JSONObject dnpkJson = JSONObject.fromObject(body);
                String dnBody = dnpkJson.getString("dn");
                String pkBody = dnpkJson.getString("puk");
                certInfo.setDn(dnBody);
                String[] dnArr = dnBody.split("\\|");
                ASN1Sequence subject = transFromDn(dnArr);
                cerTbs = SM2CertGenUtil.generateCertTBSCert(serial, issue, subject, st, et, Base64.getDecoder().decode(pkBody));
            }

            // 签名
            String ctx = Base64.getEncoder().encodeToString(cerTbs);
            String func = "eccSign";
            JSONObject reqJson = new JSONObject();
            reqJson.put("function", func);
            reqJson.put("index", 1);
            reqJson.put("data", ctx);
            reqJson.put("pkPre", true);

            String result = WebSocketClient.sendMsg(reqJson.toString());

            // 组包
            JSONObject rtnJson = JSONObject.fromObject(result);
            if (0 != rtnJson.getInt("code"))
                throw new OptFailException("生成证书失败：" + rtnJson.getString("msg"));

            String svStr = rtnJson.getString("data");
            byte[] sv = Base64.getDecoder().decode(svStr);
            byte[] cerByt = SM2CertGenUtil.makeSM2Cert(cerTbs, (ASN1Sequence) ASN1Sequence.fromByteArray(sv));

            // 记录
            certInfo.setCerData(cerByt);
            certInfo.setRdTime(st);

            int ra = certInfoDao.addCertRnd(certInfo);
            if (ra < 1)
                throw new OptFailException("生成证书失败：存储失败");


            Map<String, String> rtnMap = new HashMap<>();
            rtnMap.put("signCert", Base64.getEncoder().encodeToString(cerByt));
            return rtnMap;
        } catch (OptFailException oe) {
            throw oe;
        } catch (Exception e) {
            throw new OptFailException(e);
        }
    }


    /**
     * 证书变更
     */
    String modifyCert(JSONObject bodyJson) throws OptFailException, NullResultException {
        String msg = "";
        try {
            int certType = bodyJson.getInt("certType");
            long serialOld = bodyJson.getLong("serialNumber");
            String dnBodyBase64 = bodyJson.getString("dn");
            // 查询旧记录
            CertRndInfo oldCert = certInfoDao.findCertBySerial("" + serialOld);
            if (null == oldCert || null == oldCert.getCerData()) {
                logger.info(msg = "未查找到序列号为[" + serialOld + "]的证书");
                throw new NullResultException(msg);
            }
            if(2 == oldCert.getStatusCode()){
                logger.info(msg = "序列号为[" + serialOld + "]的证书已注销，禁止执行变更操作");
                throw new OptFailException(msg);
            }
            byte[] oldCertByt = oldCert.getCerData();
            ASN1Sequence pkSeq = SM2CaCert.getSm2PkOnX509Encode(oldCertByt);// 公钥不变

            // 新证书
            CertRndInfo certInfo = new CertRndInfo();
            long serial = genNumberService.getNumber();// 新证书序列号
            certInfo.setSerial("" + serial);
            certInfo.setCertTypeCode(certType);
            certInfo.setAlgType("sm2");
            Date st = new Date(); // 取当前时间为新有效期开始时间
            Date et = oldCert.getValidSt();
            certInfo.setValidSt(st);
            certInfo.setValidEnd(et);

            // 组包
            byte[] cerTbs = null;
            ASN1Sequence issue = (ASN1Sequence) ASN1Sequence.getInstance(
                    ASN1Sequence.getInstance(Base64.getDecoder().decode(issueCert)).getObjectAt(0)
            ).getObjectAt(5);

            // dn 解析
            String dnBody = new String(Base64.getDecoder().decode(dnBodyBase64), "UTF-8");

            certInfo.setDn(dnBody);
            String[] dnArr = dnBody.split("\\|");
            ASN1Sequence subject = transFromDn(dnArr);
            cerTbs = SM2CertGenUtil.generateCertTBSCert(serial, issue, subject, st, et, pkSeq.getEncoded());

            // 签名
            String ctx = Base64.getEncoder().encodeToString(cerTbs);
            String func = "eccSign";
            JSONObject reqJson = new JSONObject();
            reqJson.put("function", func);
            reqJson.put("index", 1);
            reqJson.put("data", ctx);
            reqJson.put("pkPre", true);

            String result = WebSocketClient.sendMsg(reqJson.toString());

            // 组包
            JSONObject rtnJson = JSONObject.fromObject(result);
            if (0 != rtnJson.getInt("code"))
                throw new OptFailException("生成证书失败：" + rtnJson.getString("msg"));

            String svStr = rtnJson.getString("data");
            byte[] sv = Base64.getDecoder().decode(svStr);
            byte[] cerByt = SM2CertGenUtil.makeSM2Cert(cerTbs, (ASN1Sequence) ASN1Sequence.fromByteArray(sv));

            // 记录
            certInfo.setCerData(cerByt);
            certInfo.setRdTime(st);
            certInfo.setPreSerial(serialOld + "");

            // 先生成新证书记录
            int ra = certInfoDao.addCertRnd(certInfo);
            if (ra < 1)
                throw new OptFailException("生成证书失败：存储失败");
            // 再更新旧证书的状态 | 注销旧证书
            int uor = certInfoDao.uptCertStatus(serialOld + "", 2);
            logger.info("更新证书状态结果: ret = " + uor);

            return Base64.getEncoder().encodeToString(cerByt);
        } catch (OptFailException | NullResultException oe) {
            throw oe;
        } catch (Exception e) {
            throw new OptFailException(e);
        }
    }


    /**
     * 证书延期
     * */
    String delayCert(JSONObject bodyJson) throws OptFailException, NullResultException {
        String msg = "";
        try {
            int certType = bodyJson.getInt("certType");
            long serialOld = bodyJson.getLong("serialNumber");
            // 查询旧记录
            CertRndInfo oldCert = certInfoDao.findCertBySerial("" + serialOld);
            if (null == oldCert || null == oldCert.getCerData()) {
                logger.info(msg = "未查找到序列号为[" + serialOld + "]的证书");
                throw new NullResultException(msg);
            }
            if(2 == oldCert.getStatusCode()){
                logger.info(msg = "序列号为[" + serialOld + "]的证书已注销，禁止执行延期操作");
                throw new OptFailException(msg);
            }
            byte[] oldCertByt = oldCert.getCerData();


            // 新证书
            CertRndInfo certInfo = new CertRndInfo();
            long serial = genNumberService.getNumber();// 新证书序列号
            certInfo.setSerial("" + serial);
            certInfo.setCertTypeCode(certType);
            certInfo.setAlgType("sm2");

            Date st = new Date(); // 取当前时间为新有效期开始时间
            Calendar cld = Calendar.getInstance();
            cld.setTime(st);
            cld.set(Calendar.YEAR, cld.get(Calendar.YEAR) + 1);
            Date et = cld.getTime();
            certInfo.setValidSt(st);
            certInfo.setValidEnd(et);

            // 组包
            byte[] cerTbs = null;
            ASN1Sequence issue = SM2CaCert.getSm2Issuer(Base64.getDecoder().decode(issueCert));

            // 从原证书中解析获得主题和公钥
            ASN1Sequence subject = SM2CaCert.getSm2Subject(oldCertByt);
            ASN1Sequence pkSeq = SM2CaCert.getSm2PkOnX509Encode(oldCertByt);

            cerTbs = SM2CertGenUtil.generateCertTBSCert(serial, issue, subject, st, et, pkSeq.getEncoded());

            // 签名
            String ctx = Base64.getEncoder().encodeToString(cerTbs);
            String func = "eccSign";
            JSONObject reqJson = new JSONObject();
            reqJson.put("function", func);
            reqJson.put("index", 1);
            reqJson.put("data", ctx);
            reqJson.put("pkPre", true);

            String result = WebSocketClient.sendMsg(reqJson.toString());
            // 组包
            JSONObject rtnJson = JSONObject.fromObject(result);
            if (0 != rtnJson.getInt("code"))
                throw new OptFailException("生成证书失败：" + rtnJson.getString("msg"));

            String svStr = rtnJson.getString("data");
            byte[] sv = Base64.getDecoder().decode(svStr);
            byte[] cerByt = SM2CertGenUtil.makeSM2Cert(cerTbs, (ASN1Sequence) ASN1Sequence.fromByteArray(sv));

            // 记录
            certInfo.setCerData(cerByt);
            certInfo.setRdTime(st);
            certInfo.setPreSerial(serialOld + "");

            // 先生成新证书记录
            int ra = certInfoDao.addCertRnd(certInfo);
            if (ra < 1)
                throw new OptFailException("生成证书失败：存储失败");
            // 再更新旧证书的状态 | 注销旧证书
            int uor = certInfoDao.uptCertStatus(serialOld + "", 2);
            logger.info("延期证书状态结果: ret = " + uor);

            return Base64.getEncoder().encodeToString(cerByt);
        } catch (OptFailException | NullResultException oe) {
            throw oe;
        } catch (Exception e) {
            throw new OptFailException(e);
        }
    }

    /**
     * 证书注销
     * */
    boolean revokeCert(JSONObject bodyJson) throws OptFailException, NullResultException {
        String msg = "";
        try {
            int certType = bodyJson.getInt("certType");

            long serialNumber = bodyJson.getLong("serialNumber");
            // 查询证书
            CertRndInfo ndCert = certInfoDao.findCertBySerial("" + serialNumber);
            if (null == ndCert || null == ndCert.getCerData()) {
                logger.info(msg = "未查找到序列号为[" + serialNumber + "]的证书");
                throw new NullResultException(msg);
            }
            if(2 == ndCert.getStatusCode()){
                logger.info(msg = "序列号为[" + serialNumber + "]的证书已注销，禁止重复操作");
                throw new OptFailException(msg);
            }
            // 再更新旧证书的状态 | 注销旧证书
            int uor = certInfoDao.uptCertStatus(serialNumber + "", 2);
            logger.info("延期证书状态结果: ret = " + uor);
            return true;
        } catch (OptFailException | NullResultException oe) {
            throw oe;
        } catch (Exception e) {
            throw new OptFailException(e);
        }
    }



    /**
     * 请求token
     */
    public String chkRentAndGenToken(String rentId, String projId, String rndA)
            throws NullResultException, OptFailException {
        // 查询租户
        RentUser rentUser = rentInfoDao.findUserById(rentId);
        if (null == rentUser) throw new NullResultException("无效租户");
        // 查询项目编号
        RentProjInfo projInfo = rentInfoDao.findProjInfo(rentId, projId);
        if (null == projInfo) throw new NullResultException("租户项目不匹配");
        String authType = projInfo.getAuthType();
        String authPuk = projInfo.getAuthPuk();
        if (ParamsUtil.checkNullSymbal(authType, authPuk))
            throw new NullResultException("租户项目信息错误，请重新注册项目");

        // 生成rndB 和 token 并缓存
        String rndB = ParamsUtil.genRandom(16);
        String ctx = rndA + rndB + rentUser.getPin(); // 同一租户的并发请求，因rndA和rndB的不同也不会相互干扰
        // enc
        String token = "";
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(ctx.getBytes("UTF-8"));
            byte[] tokenByt = messageDigest.digest();
            token = Base64.getEncoder().encodeToString(tokenByt);
        } catch (Exception e) {
            throw new OptFailException("token生成失败：" + e.getMessage());
        }
        LocalCacheRepo.putCache(token, projInfo, 30);
        return rndB;
    }


    /**
     * 验证用户请求的签名
     */
    public boolean verifyRequestSign(String token, String data, String signValue)
            throws NullResultException, OptFailException {
        Object cache = LocalCacheRepo.getCache(token);

        try {
            RentProjInfo projInfo = (RentProjInfo) cache;
            String authType = projInfo.getAuthType();
            String authPuk = projInfo.getAuthPuk();
            switch (authType.toLowerCase()) {
                case "sm2_sign":
                    return verifyBySm2(data, signValue, authPuk);

                default:
                    return false;
            }
        } catch (Exception e) {
            throw new OptFailException("验签失败：" + e.getMessage());
        }

    }


    boolean verifyBySm2(String data, String sv, String puk)
            throws OptFailException {

        JSONObject reqJson = new JSONObject();
        reqJson.put("function", "eccVerifyOut");
        reqJson.put("puk", puk);
        reqJson.put("data", data);
        reqJson.put("sv", sv);
        reqJson.put("pkPre", false);
        String msg = reqJson.toString();
        try {
            String rs = WebSocketClient.sendMsg(msg);
            JSONObject svRtnJson = JSONObject.fromObject(rs);
            int code = svRtnJson.getInt("code");
            if (0 != code) throw new OptFailException(svRtnJson.getString("msg"));
            JSONObject rtData = svRtnJson.getJSONObject("data");
            boolean pass = rtData.getBoolean("pass");
            if (pass) return pass;
            throw new OptFailException(rtData.getString("info"));
        } catch (OptFailException oe) {
            throw oe;
        } catch (Exception e) {
            throw new OptFailException(e);
        }
    }


    String transfDn(byte[] csr) throws OptFailException {
        try {
            StringBuffer sbf = new StringBuffer();
            ASN1Sequence subject = (ASN1Sequence) ((ASN1Sequence) ((ASN1Sequence)
                    ASN1Sequence.fromByteArray(csr)).getObjectAt(0)).getObjectAt(1);
            int len = subject.size();
            for (int i = 0; i < len; i++) {
                ASN1Sequence atIdx = (ASN1Sequence) ((ASN1Set) subject.getObjectAt(i)).getObjectAt(0);
                ASN1ObjectIdentifier dnOid = (ASN1ObjectIdentifier) atIdx.getObjectAt(0);
                switch (dnOid.toString()) {
                    case "2.5.4.3": // cn
                        sbf.append("cn=");
                        sbf.append(atIdx.getObjectAt(1)).append("|");
                        break;
                    case "2.5.4.10": //o
                        sbf.append("o=");
                        sbf.append(atIdx.getObjectAt(1)).append("|");
                        break;
                    case "2.5.4.7": // l
                        sbf.append("l=");
                        sbf.append(atIdx.getObjectAt(1)).append("|");
                        break;
                    case "2.5.4.8": // st
                        sbf.append("st=");
                        sbf.append(atIdx.getObjectAt(1)).append("|");
                        break;
                    case "2.5.4.11": //ou
                        sbf.append("ou=");
                        sbf.append(atIdx.getObjectAt(1)).append("|");
                        break;
                    case "2.5.4.6": //c
                        sbf.append("c=");
                        sbf.append(atIdx.getObjectAt(1)).append("|");
                        break;
                    default:
                        sbf.append(dnOid.toString()).append("=");
                        sbf.append(atIdx.getObjectAt(1)).append("|");
                        break;
                }
            }
            String rt = sbf.toString();
            if (rt.endsWith("|")) {
                rt = rt.substring(0, rt.length() - 1);
            }
            return rt;
        } catch (Exception e) {
            throw new OptFailException("p10解析失败：" + e.getMessage());
        }
    }

    ASN1Sequence transFromDn(String[] dnArr) {
        Map<String, String> map = new HashMap<>();
        for (String a : dnArr) {
            String[] kv = a.split("=");
            if (kv.length != 2) continue;
            map.put(kv[0].toLowerCase(), kv[1]);  // 简易操作
        }

        if (map.size() < 1) throw new IllegalArgumentException("主题参数不符合格式要求");

        // 主题序列
        ASN1EncodableVector issueVector = new ASN1EncodableVector();

        // 国家
        if (map.containsKey("c")) {
            if (!"cn".equalsIgnoreCase(map.get("c"))) {
                throw new IllegalArgumentException("暂不支持中国以外地区");
            }
        }
        ASN1Encodable[] issueCountry = {new ASN1ObjectIdentifier("2.5.4.6"), new DERPrintableString("CN")};
        issueVector.add(new DERSet(new DERSequence(issueCountry)));

        // 城市、 地区、 组织单元、 组织、 单位
        if (map.containsKey("st")) {
            ASN1Encodable[] issueCity = {new ASN1ObjectIdentifier("2.5.4.8"), new DERUTF8String(map.get("st"))};
            issueVector.add(new DERSet(new DERSequence(issueCity)));
        }
        if (map.containsKey("l")) {
            ASN1Encodable[] issueDN = {new ASN1ObjectIdentifier("2.5.4.7"), new DERUTF8String(map.get("l"))};
            issueVector.add(new DERSet(new DERSequence(issueDN)));
        }
        if (map.containsKey("ou")) {
            ASN1Encodable[] issueOrgUnit = {new ASN1ObjectIdentifier("2.5.4.11"), new DERUTF8String(map.get("ou"))};
            issueVector.add(new DERSet(new DERSequence(issueOrgUnit)));
        }
        if (map.containsKey("o")) {
            ASN1Encodable[] issueOrg = {new ASN1ObjectIdentifier("2.5.4.10"), new DERUTF8String(map.get("o"))};
            issueVector.add(new DERSet(new DERSequence(issueOrg)));
        }
        if (map.containsKey("cn")) {
            ASN1Encodable[] issueName = {new ASN1ObjectIdentifier("2.5.4.3"), new DERUTF8String(map.get("cn"))};
            issueVector.add(new DERSet(new DERSequence(issueName)));
        }
        // 暂忽略其它项目到主题中

        return new DERSequence(issueVector);
    }


}
