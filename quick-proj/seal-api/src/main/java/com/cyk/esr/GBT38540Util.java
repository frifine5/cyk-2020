package com.cyk.esr;


import com.cyk.esr.entity.GbtESignSeal;
import com.cyk.esr.entity.GbtEseal;
import org.bouncycastle.asn1.*;
import org.bouncycastle.asn1.gm.GMObjectIdentifiers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

/**
 * ruler of GBT38540Util ESEAL
 *
 * @author WangChengyu
 * 2019/12/9 16:35
 */
public class GBT38540Util {

    private static Logger logger = LoggerFactory.getLogger(GBT38540Util.class);


    /**
     * 电子印章信息组成GBT 38540 ：电子印章待签数据
     *
     * @param eSeal 电子印章数据
     * @return 待签数据byte[]
     */
    public static byte[] prepareCombineESeal(GbtEseal eSeal) {
        // 验证参数是否存在空值和越界值
        GbtEseal.verifyESealNec(eSeal);

        // 参数组ASN1格式
        DERIA5String id = new DERIA5String(eSeal.getID());
        ASN1Integer version = new ASN1Integer(eSeal.getVersion());
        DERIA5String vid = new DERIA5String(eSeal.getVid());
        ASN1Encodable[] headerArr = {id, version, vid};
        DERSequence header = new DERSequence(headerArr);

        DERIA5String esId = new DERIA5String(eSeal.getEsID());

        ASN1Integer type = new ASN1Integer(eSeal.getType());
        DERUTF8String name = new DERUTF8String(eSeal.getName());
        ASN1Integer certListType = new ASN1Integer(eSeal.getCertListType());
        List<String> certList = eSeal.getCertList();
        ASN1EncodableVector certVector = new ASN1EncodableVector();
        for (String certBase64 : certList) {
            byte[] cert;
            try {
                cert = Base64.getDecoder().decode(certBase64);
            } catch (Exception e) {
                logger.error("证书Base64解码失败");
                continue;
            }
            DEROctetString octCert = new DEROctetString(cert);
            certVector.add(octCert);
        }
        DERSequence asnCertList = new DERSequence(certVector);
        DERUTCTime createTime = new DERUTCTime(eSeal.getCreateDate());
        DERUTCTime validStart = new DERUTCTime(eSeal.getValidStart());
        DERUTCTime validEnd = new DERUTCTime(eSeal.getValidEnd());
        ASN1Encodable[] propertyArr = {type, name, certListType, asnCertList, createTime, validStart, validEnd};
        DERSequence property = new DERSequence(propertyArr);

        DERIA5String imgType = new DERIA5String(eSeal.getImageType());
        DEROctetString imgData = new DEROctetString(Base64.getDecoder().decode(eSeal.getImageData()));
        ASN1Integer imgWidth = new ASN1Integer(eSeal.getImageWidth());
        ASN1Integer imgHeight = new ASN1Integer(eSeal.getImageHeight());
        ASN1Encodable[] pictureArr = {imgType, imgData, imgWidth, imgHeight};
        DERSequence picture = new DERSequence(pictureArr);

        ASN1EncodableVector esealInfoVector = new ASN1EncodableVector();
        esealInfoVector.add(header);
        esealInfoVector.add(esId);
        esealInfoVector.add(property);
        esealInfoVector.add(picture);

        if (null != eSeal.getExDates()) {
            ASN1EncodableVector exDataVect = new ASN1EncodableVector();
            for (String exData : eSeal.getExDates()) {
                byte[] data;
                try {
                    data = Base64.getDecoder().decode(exData);
                    ASN1Sequence ext = (ASN1Sequence) ASN1Sequence.fromByteArray(data);
                    exDataVect.add(ext);
                } catch (Exception e) {
                    logger.error("证书Base64解码失败");
                }
            }
            if (exDataVect.size() > 0) {
                DERSequence extDatas = new DERSequence(exDataVect);
                DERTaggedObject extTagObj = new DERTaggedObject(0, extDatas);
                esealInfoVector.add(extTagObj);
            }
        }
        DERSequence esealInfo = new DERSequence(esealInfoVector);
        try {
            return esealInfo.getEncoded();
        } catch (IOException e) {
            logger.error("esealInfo ASN.1 编码失败", e);
            return null;
        }
    }


    /**
     * 组装电子印章
     */
    public static byte[] combineESeal(byte[] body, byte[] mkCert, byte[] signedData) {

        try {
            ASN1EncodableVector esealVect = new ASN1EncodableVector();
            ASN1Sequence eSealInfo = (ASN1Sequence) ASN1Sequence.fromByteArray(body);
            DEROctetString cert = new DEROctetString(mkCert);
            ASN1ObjectIdentifier signAlgID = GMObjectIdentifiers.sm2sign_with_sm3;
            DERBitString signedValue = new DERBitString(signedData);

            esealVect.add(eSealInfo);
            esealVect.add(cert);
            esealVect.add(signAlgID);
            esealVect.add(signedValue);

            DERSequence eSeal = new DERSequence(esealVect);
            return eSeal.getEncoded();
        } catch (IOException e) {
            logger.error("ESeal ASN.1 编码失败", e);
        }
        return null;
    }


    /**
     * 电子印章信息组成GBT 38540 ：电子签章待签数据
     *
     * @param eSignSeal 电子签章数据
     * @return 待签数据byte[]
     */
    public static byte[] prepareCombineESign(GbtESignSeal eSignSeal) {
        // 验证参数是否存在空值和越界值
        GbtESignSeal.verifyESignNec(eSignSeal);

        try {
            // 参数组ASN1格式
            ASN1Integer version = new ASN1Integer(eSignSeal.getVersion());
            ASN1Sequence eseal = (ASN1Sequence) ASN1Sequence.fromByteArray(eSignSeal.getEsealData());
            DERBitString dataHash = new DERBitString(Base64.getDecoder().decode(eSignSeal.getDataHash()));
            DERIA5String propertyInfo = new DERIA5String(eSignSeal.getPropertyInfo());

            ASN1EncodableVector toSignVector = new ASN1EncodableVector();
            toSignVector.add(version);
            toSignVector.add(eseal);
            toSignVector.add(dataHash);
            toSignVector.add(propertyInfo);

            // extDatas
            List<String> extList = eSignSeal.getExtDatas();
            if (null != extList && extList.size() > 0) {
                ASN1EncodableVector exDataVect = new ASN1EncodableVector();
                for (String exData : extList) {
                    byte[] data;
                    try {
                        data = Base64.getDecoder().decode(exData);
                        ASN1Sequence ext = (ASN1Sequence) ASN1Sequence.fromByteArray(data);
                        exDataVect.add(ext);
                    } catch (Exception e) {
                        logger.error("证书Base64解码失败");
                    }
                }
                if (exDataVect.size() > 0) {
                    DERSequence extDatas = new DERSequence(exDataVect);
                    DERTaggedObject extTagObj = new DERTaggedObject(0, extDatas);
                    toSignVector.add(extTagObj);
                }
            }
            DERSequence toSign = new DERSequence(toSignVector);
            return toSign.getEncoded();
        } catch (IOException e) {
            logger.error("esealInfo ASN.1 编码失败", e);
        }
        return null;
    }


    /**
     * 组装签章结构
     */
    public static byte[] combineESignSeal(byte[] toSignData, byte[] signerCert, byte[] signedData, byte[] tsByts) {

        try {
            ASN1EncodableVector esealVect = new ASN1EncodableVector();
            ASN1Sequence toSign = (ASN1Sequence) ASN1Sequence.fromByteArray(toSignData);
            DEROctetString cert = new DEROctetString(signerCert);
            ASN1ObjectIdentifier signatureAlgID = GMObjectIdentifiers.sm2sign_with_sm3;
            DERBitString signature = new DERBitString(signedData);

            esealVect.add(toSign);
            esealVect.add(cert);
            esealVect.add(signatureAlgID);
            esealVect.add(signature);
            if (null != tsByts && tsByts.length > 2) {
                esealVect.add((ASN1Sequence) ASN1Sequence.fromByteArray(tsByts));
            }

            DERSequence eSignSeal = new DERSequence(esealVect);
            return eSignSeal.getEncoded();
        } catch (IOException e) {
            logger.error("ESignSeal ASN.1 编码失败", e);
        }
        return null;
    }


}
