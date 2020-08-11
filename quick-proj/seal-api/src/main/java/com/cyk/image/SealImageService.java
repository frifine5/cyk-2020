package com.cyk.image;

import com.cyk.cert.SM2CaCert;
import com.cyk.cert.dao.PsCertDao;
import com.cyk.cert.entity.PsSealCert;
import com.cyk.cert.service.CaSerRaClient;
import com.cyk.common.ParamsUtil;
import com.cyk.common.ex.NullResultException;
import com.cyk.common.ex.OptFailException;
import com.cyk.esr.GBT38540Util;
import com.cyk.esr.entity.GbtEseal;
import com.cyk.image.dao.PersonImageDao;
import com.cyk.image.entity.PsSealImage;
import com.cyk.image.service.PersonImageService;
import com.cyk.ioclient.wskt.WebSocketClient;
import com.cyk.number.GenNumberService;
import com.cyk.user.dao.PersonUserDao;
import com.cyk.user.entity.PsUser;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

/**
 * 印章（制式）图片生成服务
 *
 * @author WangChengyu
 * 2020/5/25 14:49
 */
@Service
public class SealImageService {

    Logger logger = LoggerFactory.getLogger(SealImageService.class);


    @Autowired
    PersonImageService personImageService;

    @Autowired
    PersonUserDao personUserDao;

    @Autowired
    PersonImageDao personImageDao;

    @Autowired
    PsCertDao psCertDao;

    @Autowired
    CaSerRaClient raServer;

    @Autowired
    GenNumberService genNumberService;

    /**
     * 删除个人印图
     *
     * @throws NullResultException
     * @throws OptFailException
     */
    public void delPsImage(String uuid) {
        int rd = personImageDao.delPsImageStatus(uuid);
        logger.info("删除记录结果ret= " + rd);
    }


    /**
     * 更改个人印图的状态
     *
     * @throws NullResultException
     * @throws OptFailException
     */
    public void optPsImage(String uuid, int status) throws NullResultException, OptFailException,IOException,ParseException  {
        String msg = "";
        // status : 0-未启用； 1-启用； 2-冻结
        PsSealImage psSeal = personImageDao.findSealImageByImageuuid(uuid);
        if (null == psSeal || ParamsUtil.checkNull(psSeal.getUid()) || null == psSeal.getPicData()) {
            logger.info(msg = "未查询到用户印章图片数据");
            throw new NullResultException(msg);
        }

        switch (status) {
            case 1: {   // 制作印章
                mkOrRdoSeal(psSeal);
            }
            break;
            case 2:

                break;


            default:// 无事无非
                return;
        }
        if (null == psSeal.getSealData()) {// 无印章
            // todo check seal

        }


        int ru = personImageDao.uptPsImageStatus(uuid, status);
        logger.info("更新记录结果ret= " + ru);
    }


    @Value("${maker.cert:}")
    String makerCertStr;
    @Value("${maker.index:10}")
    int makerIdx;


    // 制作或重制印章
    void mkOrRdoSeal(PsSealImage psSeal) throws OptFailException, IOException,ParseException {
        // 获取证书
        PsSealCert psSealCert = psCertDao.findByUuid(psSeal.getCertId());

        String uid = psSeal.getUid();
        String alg = "ecc256";
        String name = psSeal.getSealName();
        int type = psSeal.getSealTypeCode();
        String mid = "CYK-laboratory";    // 印章厂商
        int index = psCertDao.getnowmax() + 1;
        StringBuffer dnbf = new StringBuffer();
        dnbf.append("c=CN").append("|").append("o=");
        if(type == 0 ){
            dnbf.append("个人名（制式）");
        }else if(type==1){
            dnbf.append("个人名（自制）");
        }else{
            dnbf.append("个人手写签");
        }
        dnbf.append("|").append("cn=").append(name);
        String dn = "c=CN|o=电子印章系统(测试)|cn=在线申请平台(测试)";
        dn = dnbf.toString();

        String signCert;
        byte[] cerByt;
        if(null == psSealCert){ //无证书 新申请
            psSealCert = new PsSealCert();
            psSealCert.setAlg(alg);
            psSealCert.setUid(uid);
            String certId = ParamsUtil.getUUIDStr();
            psSealCert.setUuid(certId);
            psSealCert.setSkid(index);
            psSealCert.setIssuerId(mid);
            psSealCert.setOwnerDn(dn);
            try {
                signCert = raServer.cerRegBusiness(index, dn);
            }catch (OptFailException oe){
                throw oe;
            } catch (Exception e) {
                throw new OptFailException(e.getMessage());
            }
            cerByt = Base64.getDecoder().decode(signCert);
            Date[] valid = SM2CaCert.getSM2ValidTime(cerByt);
            psSealCert.setValidSt(valid[0]);
            psSealCert.setValidEnd(valid[1]);
            psSealCert.setValidInfo("1年");
            psSealCert.setCertData(cerByt);
            psSealCert.setStatusCode(1);
            psSealCert.setRdTime(new Date());
            // 新增证书记录
            int ra = psCertDao.addCertRnd(psSealCert);
            logger.info("证书存储结果 ret = " + ra);
            // 更新图片记录
            psSeal.setCertId(certId);
            int ra2 = personImageDao.uptPsImageCertId(psSeal.getUuid(), certId);
            logger.info("证书关联更新结果 ret = " + ra2);
        }else if(null != psSealCert.getCertData()) {
            cerByt = psSealCert.getCertData();
            if (1 == psSealCert.getStatusCode()) {
                // go on
            } else if (0 == psSealCert.getStatusCode()) {
                psCertDao.uptCertStatusByUUId(psSealCert.getUuid(), 1);
            } else {
                throw new OptFailException("证书已冻结，无法重制印章");
            }
        }else{   // 无证书，需更新
            if (1 == psSealCert.getStatusCode()|| 0 == psSealCert.getStatusCode()) {
                psSealCert.setAlg(alg);
                psSealCert.setUid(uid);
                psSealCert.setSkid(index);
                psSealCert.setIssuerId(mid);
                psSealCert.setOwnerDn(dn);
                try {
                    signCert = raServer.cerRegBusiness(index, dn);
                }catch (OptFailException oe){
                    throw oe;
                } catch (Exception e) {
                    throw new OptFailException(e.getMessage());
                }
                cerByt = Base64.getDecoder().decode(signCert);
                Date[] valid = SM2CaCert.getSM2ValidTime(cerByt);
                psSealCert.setValidSt(valid[0]);
                psSealCert.setValidEnd(valid[1]);
                psSealCert.setValidInfo("1年");
                psSealCert.setCertData(cerByt);
                psSealCert.setStatusCode(1);
                psSealCert.setRdTime(new Date());
                // 更新
                int ra = psCertDao.uptCertRnd(psSealCert);
                logger.info("证书更新结果 ret = " + ra);
            } else {
                throw new OptFailException("证书已冻结，无法重制印章");
            }
        }
        if(null == cerByt) throw new OptFailException("签章人证书更新失败，无法重制印章");
        //
        String sealCode = "preson-" + genNumberService.getNumber();
        List<String> certList = new ArrayList<>();
        certList.add(Base64.getEncoder().encodeToString(cerByt));

        GbtEseal gbtEseal = new GbtEseal();
        gbtEseal.setID("ES");
        gbtEseal.setVid(mid);
        gbtEseal.setEsID(sealCode);
        gbtEseal.setImageType(psSeal.getPicType());
        gbtEseal.setName(name);
        gbtEseal.setImageData(Base64.getEncoder().encodeToString(psSeal.getPicData()));
        gbtEseal.setType(0);
        gbtEseal.setImageWidth(psSeal.getWidth());
        gbtEseal.setImageHeight(psSeal.getHeight());
        gbtEseal.setCertListType(1);
        gbtEseal.setCertList(certList);
        Date[] valid = SM2CaCert.getSM2ValidTime(cerByt);
        Date st = new Date();
        if(st.compareTo(valid[1]) > 0){
            throw new OptFailException("签章人证书已过期，无法重制印章");
        }
        gbtEseal.setCreateDate(st);
        gbtEseal.setValidStart(st);
        gbtEseal.setValidEnd(valid[1]);
        byte[] esBody = GBT38540Util.prepareCombineESeal(gbtEseal);

        // 签名
        JSONObject reqJson2 = new JSONObject();
        reqJson2.put("function", "eccSign");
        reqJson2.put("index", makerIdx);
        reqJson2.put("data", Base64.getEncoder().encodeToString(esBody));
        reqJson2.put("pkPre", true);
        String svStr;
        try {
            String result2 = WebSocketClient.sendMsg(reqJson2.toString());
            JSONObject rtnJson2 = JSONObject.fromObject(result2);
            logger.info("eccSign receive >> " + result2);
            svStr = rtnJson2.getString("data");
        } catch (Exception e) {
            throw new OptFailException("调用签名失败：" + e.getMessage());
        }
        // 组成电子印章结构体(完整)
        byte[] eSeal = GBT38540Util.combineESeal(esBody, Base64.getDecoder().decode(makerCertStr),
                Base64.getDecoder().decode(svStr));

        psSeal.setSealCode(sealCode);
        psSeal.setSealData(eSeal);
        psSeal.setStatusCode(1);
        int ru = personImageDao.uptPsImageSeal(psSeal);
        logger.info("更新印章 ret = " + ru);
        if(ru <1){
            throw new OptFailException("印章制作存储失败");
        }

    }



    /**
     * 制式个人章制作
     *
     * @throws NullResultException
     * @throws OptFailException
     */
    public void genPsSealImage(String uid, boolean isSquare, int width, int height, int colorType)
            throws NullResultException, OptFailException {

        PsUser psUser = personUserDao.findPsUserByUid(uid);
        if (null == psUser) throw new NullResultException("不存在的用户uid:" + uid);

        PsSealImage extSealImage = personImageDao.findModeSealImage(uid);
        if (null != extSealImage) {
            String msg = "";
            logger.info(msg = "已经生成了制式印章，无需重复生成");
            throw new OptFailException(msg);
        }

        String name = psUser.getRealName();

        Color fontColor = 0 == colorType ? Color.red : 1 == colorType ? Color.BLUE : Color.BLACK;
        int canvasWidth = width;
        int canvasHeight = height;

        if (isSquare) {
            canvasHeight = canvasWidth; // 正方形以宽为准
        }
        if (ParamsUtil.checkNull(name)) {
            throw new IllegalArgumentException("名字为空");
        }
        float ri = (float) width / 196;

        int nameLen = name.length();
        byte[] imgByts = null;
        if (nameLen < 5) {
            imgByts = personImageService.genPsSquareSeal(name, ri, fontColor);
        } else if (nameLen < 7) {
            imgByts = personImageService.genPsSquareSealSix(name, ri, fontColor);
        } else if (nameLen < 16) {
            float wh = canvasWidth / canvasHeight;
            imgByts = personImageService.genPsRectSealLonger(name, ri, wh, fontColor);
        } else {
            throw new IllegalArgumentException("名字太长");
        }

        String imgUuid = ParamsUtil.getUUIDStr();
        PsSealImage rnd = new PsSealImage();
        rnd.setUuid(imgUuid);
        rnd.setUid(uid);
        rnd.setSealName(name);
        rnd.setWidth(canvasWidth);
        rnd.setHeight(canvasHeight);
        rnd.setPicType("png");
        rnd.setRdTime(new Date());
        rnd.setPicData(imgByts);

        int ra = personImageDao.addPsImageRd(rnd);
        if (ra < 1) {
            throw new OptFailException("图片记录失败");
        }

        // 制式生成电子印章 ？


    }


    /**
     * 查询个人所有印图
     *
     * @throws NullResultException
     */
    public List<PsSealImage> getPsImages(String uid) throws NullResultException {
        List<PsSealImage> psSealImages = personImageDao.queryByUid(uid);
        if (ParamsUtil.checkListNull(psSealImages)) {
            throw new NullResultException("用户未生成/上传个人印图");
        }
        return psSealImages;
    }


    /**
     * 存储个人印图的记录
     *
     * @throws NullResultException
     * @throws OptFailException
     */
    public void uploadSealImage(String uid, byte[] files, String fileName, int sealType)
            throws NullResultException, OptFailException {
        PsUser psUser = personUserDao.findPsUserByUid(uid);
        if (null == psUser) throw new NullResultException("不存在的用户uid:" + uid);

        InputStream ss = new ByteArrayInputStream(files);
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(ss);
        } catch (IOException e) {
            throw new OptFailException("图片解析失败:" + e.getMessage());
        }

        String imgUuid = ParamsUtil.getUUIDStr();
        PsSealImage rnd = new PsSealImage();
        rnd.setUuid(imgUuid);
        rnd.setUid(uid);
        rnd.setSealName(fileName);
        rnd.setSealTypeCode(sealType);
        rnd.setWidth(bi.getWidth());
        rnd.setHeight(bi.getHeight());
        rnd.setPicType("png");
        rnd.setRdTime(new Date());
        rnd.setPicData(files);

        int ra = personImageDao.addPsImageRd(rnd);
        if (ra < 1) {
            throw new OptFailException("图片存储失败");
        }

    }

}
