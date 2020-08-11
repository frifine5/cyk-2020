package com.cyk.t.protocol.mws.service;


import com.cyk.t.protocol.alg.BcGmSm2Util;
import com.cyk.t.protocol.alg.sm2.GMTSM2;
import com.cyk.t.protocol.util.FileUtil;
import org.bouncycastle.asn1.*;
import org.bouncycastle.asn1.gm.GMNamedCurves;
import org.bouncycastle.asn1.gm.GMObjectIdentifiers;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.bouncycastle.util.encoders.Hex;

@Service
public class MmsFunService {



    @Value("${kp.path:/mnt/ecckey}")
    String keyDir = "/mnt/ecckey/";

    /**
     * byte[] 随机数
     *
     * @param len
     * @return
     */
    public byte[] genRandom(int len) {
        if (len < 1) {
            throw new IllegalArgumentException("illegal length: len < 8");
        }
        byte[] rnd = new byte[len];
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < len; i++) {
            rnd[i] = 0x0;
            int r = random.nextInt(255);
            rnd[i] = (byte) r;
        }
        return rnd;
    }


    public byte[] eccSign(byte[] ctx, int index, boolean isPukPre) {
        Security.addProvider(new BouncyCastleProvider());

        try {
            byte[] prkByt = FileUtil.fromDATfile(keyDir + "pvk-" + index + ".bin");
//            byte[] pukByt = FileUtil.fromDATfile(keyDir + "puk-" + index + ".bin");
            KeyFactory kf = KeyFactory.getInstance("EC", new BouncyCastleProvider());
            PrivateKey privateKey = kf.generatePrivate(new PKCS8EncodedKeySpec(prkByt));
//            PublicKey publicKey = kf.generatePublic(new X509EncodedKeySpec(pukByt));

            byte[] sv = null;
            if(isPukPre){
                sv = BcGmSm2Util.signSm3WithSm2Asn1Rs(ctx, null, privateKey);
            }else{ // 裸签的实现类型
                byte[] prikey = parsePrikey(prkByt);
                sv = GMTSM2.getInstance().sm2Sign(BcGmSm2Util.sm3(ctx), prikey);
            }

            return sv;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }


    private byte[] parsePrikey(byte[] asn){
        try{
            ASN1Sequence root = (ASN1Sequence)ASN1Sequence.fromByteArray(asn);
            ASN1Encodable at2 = root.getObjectAt(2);
            DEROctetString octAt2 = (DEROctetString)at2;
            byte[] oct = octAt2.getOctets();

            ASN1Sequence rtAtOct = (ASN1Sequence)ASN1Sequence.fromByteArray(oct);
            ASN1Encodable at1 = rtAtOct.getObjectAt(1);
            DEROctetString octAt1 = (DEROctetString)at1;
            return octAt1.getOctets();
        }catch (Exception e){
            throw new RuntimeException("签名获取私钥权限失败", e);
        }
    }

    private byte[] parsePubkey(byte[] asn){
        try{
            ASN1Sequence rtAtOct = (ASN1Sequence) ASN1Sequence.fromByteArray(asn);
            ASN1Encodable at1 = rtAtOct.getObjectAt(1);
            DERBitString octAt1 = (DERBitString) at1;
            return octAt1.getBytes();
        }catch (Exception e){
            throw new RuntimeException("获取公钥失败", e);
        }
    }


    public boolean eccVerify(byte[] ctx, byte[] sv, int index, boolean isPukPre) {
        Security.addProvider(new BouncyCastleProvider());

        try {
            byte[] pukByt = FileUtil.fromDATfile(keyDir + "puk-" + index + ".bin");
            KeyFactory kf = KeyFactory.getInstance("EC", new BouncyCastleProvider());
            PublicKey publicKey = kf.generatePublic(new X509EncodedKeySpec(pukByt));

            boolean vs = false;
            if (isPukPre) {
                vs = BcGmSm2Util.verifySm3WithSm2Asn1Rs(ctx, null, sv, publicKey);
            } else {
                ASN1Sequence svAsn = (ASN1Sequence)ASN1Sequence.fromByteArray(sv);
                BigInteger r = ((ASN1Integer) svAsn.getObjectAt(0)).getValue();
                BigInteger s = ((ASN1Integer) svAsn.getObjectAt(1)).getValue();
                vs = GMTSM2.getInstance().sm2Verify(BcGmSm2Util.sm3(ctx), r, s, parsePubkey(pukByt) );
            }
            System.out.println("验签结果：" + vs);
            return vs;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private byte[] sm3PreHandle(byte[] ctx, PublicKey publicKey) throws IOException {
        SM3Digest bcSm3Dg = new SM3Digest();
        ASN1Sequence rtAtOct = (ASN1Sequence) ASN1Sequence.fromByteArray(publicKey.getEncoded());
        ASN1Encodable at1 = rtAtOct.getObjectAt(1);
        DERBitString octAt1 = (DERBitString) at1;
        byte[] pk = octAt1.getBytes();

        byte[] mdPre = new byte[32];
        String hentl = "0080";
        String hid = "31323334353637383132333435363738";
        String ha = "FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00000000FFFFFFFFFFFFFFFC";
        String hb = "28E9FA9E9D9F5E344D5A9E4BCF6509A7F39789F515AB8F92DDBCBD414D940E93";
        String hgx = "32C4AE2C1F1981195F9904466A39C9948FE30BBFF2660BE1715A4589334C74C7";
        String hgy = "BC3736A2F4F6779C59BDCEE36B692153D0A9877CC62A474002DF32E52139F0A0";
        String hpk = Hex.toHexString(pk);
        String pre1 = hentl + hid + ha + hb + hgx + hgy + hpk.substring(2);
        byte[] preByt = Hex.decode(pre1);
        // 预处理摘要
        bcSm3Dg.update(preByt, 0, ctx.length);
        bcSm3Dg.doFinal(mdPre, 0);

        byte[] afPre = new byte[32 + ctx.length];
        System.arraycopy(mdPre, 0, afPre, 0, 32);
        System.arraycopy(ctx, 0, afPre, 32, ctx.length);

        return afPre;
    }

    public boolean eccVerifyOut(byte[] ctx, byte[] sv, byte[] pukByt, boolean isPukPre) {
        Security.addProvider(new BouncyCastleProvider());

        try {
            KeyFactory kf = KeyFactory.getInstance("EC", new BouncyCastleProvider());
            PublicKey publicKey = kf.generatePublic(new X509EncodedKeySpec(pukByt));

            boolean vs = false;
            if (isPukPre) {
                vs = BcGmSm2Util.verifySm3WithSm2Asn1Rs(ctx, null, sv, publicKey);
            } else {
                ASN1Sequence svAsn = (ASN1Sequence)ASN1Sequence.fromByteArray(sv);
                BigInteger r = ((ASN1Integer) svAsn.getObjectAt(0)).getValue();
                BigInteger s = ((ASN1Integer) svAsn.getObjectAt(1)).getValue();
                vs = GMTSM2.getInstance().sm2Verify(BcGmSm2Util.sm3(ctx), r, s, parsePubkey(pukByt) );
            }
            System.out.println("验签结果：" + vs);
            return vs;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public byte[] exportEccPubkey(int index, boolean encode) {
        if (index < 0) {
            throw new IllegalArgumentException("illegal index: " + index);
        }
        try {
            byte[] pukByt = FileUtil.fromDATfile(keyDir + "puk-" + index + ".bin");
            if (encode) {     // 返回原始编码结构,含算法的X509EncodedKeySpec
                return pukByt;
            } else {  // 返回纯公钥byte[]
                return parsePubkey(pukByt);
            }
        } catch (IOException e) {
            throw new RuntimeException("data is un-format in index = " + index);
        }
    }


    public KeyPair genKpFile() throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        X9ECParameters ecParameters = GMNamedCurves.getByName("sm2p256v1");
        // 构造EC 算法参数
        // 设置SM2 算法的 OID,设置曲线方程 , 椭圆曲线G点 ,大整数N ,
        ECNamedCurveParameterSpec sm2Spec = new ECNamedCurveParameterSpec(
                GMObjectIdentifiers.sm2p256v1.toString(),
                ecParameters.getCurve(),
                ecParameters.getG(),
                ecParameters.getN());
        // 创建 密钥对生成器
        KeyPairGenerator gen = KeyPairGenerator.getInstance("EC", new BouncyCastleProvider());
        // 使用SM2的算法区域初始化密钥生成器
        gen.initialize(sm2Spec, new SecureRandom());
        // 获取密钥对
        KeyPair keyPair = gen.generateKeyPair();

        return keyPair;
    }


    public static void main(String[] args) throws Exception {
        MmsFunService service = new MmsFunService();
        String rootFp = "/mnt/ecckey1/";
        for (int i = 0; i < 10; i++) {
//            System.out.println(Hex.toHexString(service.genRandom(8)));
            String pvkFp = rootFp + "pvk-" + (i + 1) + ".bin";
            String pukFp = rootFp + "puk-" + (i + 1) + ".bin";
            KeyPair keyPair = service.genKpFile();
            FileUtil.writeInFiles(pvkFp, keyPair.getPrivate().getEncoded());
            FileUtil.writeInFiles(pukFp, keyPair.getPublic().getEncoded());
        }
        System.out.println("finish done");
    }


}
