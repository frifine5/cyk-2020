package com.cyk.cert;

import com.cyk.common.ex.OptFailException;
import org.bouncycastle.asn1.*;
import org.bouncycastle.asn1.gm.GMObjectIdentifiers;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;

public class SM2CertGenUtil {



    /**
     * 根据主题组装证书待签名数据tbs
     */
    public static byte[] generateCertTBSCert(long serial, ASN1Sequence certIssue,
           ASN1Sequence certSubject, Date st, Date et, byte[] pk) throws OptFailException{
        try {
            // 1) version 证书版本
            DERTaggedObject certVersion = new DERTaggedObject(true, 0, new ASN1Integer(3));
            // 2) serial 证书序列号
            ASN1Integer certSerial = new ASN1Integer(serial);
            // 3) algorithm oid
            // default sm2 算法OID
            ASN1Encodable[] smAlg = {GMObjectIdentifiers.sm2sign_with_sm3, DERNull.INSTANCE};
            DERSequence certAlg = new DERSequence(smAlg);
            // 4) issue 颁发者

            // 5) valid start add age to end 证书有效期（从--止）
            ASN1EncodableVector validVector = new ASN1EncodableVector();
            validVector.add(new ASN1UTCTime(st));
            validVector.add(new ASN1UTCTime(et));
            DERSequence certValid = new DERSequence(validVector);
            // 6) subject 证书的申请者/持有者/主体

            // 7) publicKey 主体的公钥
            ASN1Sequence certPk;
            if(pk.length == 65){
                ASN1Encodable[] pkAlg = {new ASN1ObjectIdentifier(CertOidEnum.SM2_ECPK.oid), new ASN1ObjectIdentifier(CertOidEnum.SM2_ECPK_EX.oid)};
                DERSequence pkOid = new DERSequence(pkAlg);
                DERBitString pkString = new DERBitString(pk);
                ASN1Encodable[] pkOidAsn = {pkOid, pkString};
                certPk = new DERSequence(pkOidAsn);
            }else{
                certPk = (ASN1Sequence)ASN1Sequence.fromByteArray(pk);
            }
            // 8)扩展项

            // tbsc 证书主体
            ASN1Encodable[] tbsCertAsn = {certVersion, certSerial, certAlg, certIssue, certValid, certSubject, certPk};
            DERSequence certTBSCert = new DERSequence(tbsCertAsn);
            return certTBSCert.getEncoded();
        }catch (Exception e){
            throw new OptFailException(e);
        }
    }


    /**
     * 根据csr生成tbs
     * */
    public static byte[] generateCertTBSCert(long serial, ASN1Sequence certIssue,
           Date st, Date et, byte[] csr) throws OptFailException {
        try {
            // 1) version 证书版本
            DERTaggedObject certVersion = new DERTaggedObject(true, 0, new ASN1Integer(3));
            // 2) serial 证书序列号
            ASN1Integer certSerial = new ASN1Integer(serial);
            // 3) algorithm oid
            // default sm2 算法OID
            ASN1Encodable[] smAlg = {GMObjectIdentifiers.sm2sign_with_sm3, DERNull.INSTANCE};
            DERSequence certAlg = new DERSequence(smAlg);
            // 4) issue 颁发者

            // 5) valid start to end 证书有效期（从--止）
            ASN1EncodableVector validVector = new ASN1EncodableVector();
            validVector.add(new ASN1UTCTime(st));
            validVector.add(new ASN1UTCTime(et));
            DERSequence certValid = new DERSequence(validVector);

            // 6) subject 证书的申请者/持有者/主体
            ASN1Sequence csrAsn = (ASN1Sequence) ((ASN1Sequence) ASN1Sequence.fromByteArray(csr)).getObjectAt(0);
            ASN1Sequence certSubject = (ASN1Sequence)csrAsn.getObjectAt(1);
            // 7) publicKey 主体的公钥
            ASN1Sequence certPk = (ASN1Sequence)csrAsn.getObjectAt(2);

            // tbsc 证书主体
            ASN1Encodable[] tbsCertAsn = {certVersion, certSerial, certAlg, certIssue, certValid, certSubject, certPk};
            DERSequence certTBSCert = new DERSequence(tbsCertAsn);

            return certTBSCert.getEncoded();
        }catch (Exception e){
            throw new RuntimeException("生产证书主体失败", e);
        }
    }




    /**
     * 证书主体， 签名值 ==》 证书结构体 | 指定为SM2算法oid
     */
    public static byte[] makeSM2Cert(byte[] bytTbsc, byte[] signValue){
        try {
            ASN1Encodable[] oidArr = {GMObjectIdentifiers.sm2sign_with_sm3, DERNull.INSTANCE};
            DERSequence derOid = new DERSequence(oidArr);// 算法域

            byte[] x = new byte[32];
            byte[] y = new byte[32];
            System.arraycopy(signValue, 0, x, 0, 32);
            System.arraycopy(signValue, 32, y, 0, 32);
            ASN1Integer r = new ASN1Integer(new BigInteger(1, x));
            ASN1Integer s = new ASN1Integer(new BigInteger(1, y));
            ASN1Encodable[] rsArr = {r, s};
            DERSequence derBitStringSV = new DERSequence(rsArr);
            DERBitString derSV = new DERBitString(derBitStringSV.getEncoded());// 签名值域
            ASN1Sequence tbsc = (ASN1Sequence) ASN1Sequence.fromByteArray(bytTbsc);
            ASN1Encodable[] asnCertArr = {tbsc, derOid, derSV};
            return new DERSequence(asnCertArr).getEncoded();
        }catch (Exception e){
            throw new RuntimeException("组装证书失败", e);
        }
    }


    /**
     * 证书主体， 签名值 ==》 证书结构体 | 指定为SM2算法oid
     */
    public static byte[] makeSM2Cert(byte[] bytTbsc, ASN1Sequence p1Sv)throws OptFailException{
        try {
            ASN1Encodable[] oidArr = {GMObjectIdentifiers.sm2sign_with_sm3, DERNull.INSTANCE};
            DERSequence derOid = new DERSequence(oidArr);// 算法域

            DERBitString derSV = new DERBitString(p1Sv.getEncoded());   // 签名值域
            ASN1Sequence tbsc = (ASN1Sequence) ASN1Sequence.fromByteArray(bytTbsc);
            ASN1Encodable[] asnCertArr = {tbsc, derOid, derSV};
            return new DERSequence(asnCertArr).getEncoded();
        }catch (Exception e){
            throw new OptFailException( e);
        }
    }




}
