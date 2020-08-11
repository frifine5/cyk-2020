package com.cyk.cert;

import org.bouncycastle.asn1.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;


/**
 * 证书解析辅助类
 *
 * @author WangChengyu
 * 2020/6/3 10:04
 */
public class SM2CaCert {

	/**
	 * 输入证书字节，返回主体公钥中的公钥
	 * @return 65字节公钥
	 */
	public static byte[] getSM2PublicKey(byte[] src) throws IOException {
		try {
			ASN1Encodable at0 = ASN1Sequence.getInstance((ASN1Sequence.fromByteArray(src))).getObjectAt(0);
			ASN1Encodable at06 = ASN1Sequence.getInstance(at0).getObjectAt(6);
			ASN1Encodable at061 = ASN1Sequence.getInstance(at06).getObjectAt(1);
			byte[] bitString = DERBitString.getInstance(at061).getBytes();
			byte[] pk = new byte[65];
			pk[0] = 0x04;
			int len = bitString.length;
			System.arraycopy(bitString, len-64, pk, 1, 64);
			return pk;
		} catch (IOException e) {
			throw new IOException("不符合SM2证书的格式，获取主体公钥信息失败", e);
		}
	}
	/**
	 * 从csr中解析sm2证书
	 * @return 65字节公钥
	 */
	public static byte[] getSM2PublicKeyFromCSR(byte[] src) throws IOException {
		try {
			ASN1Encodable at0 = ASN1Sequence.getInstance((ASN1Sequence.fromByteArray(src))).getObjectAt(0);
			ASN1Encodable at02 = ASN1Sequence.getInstance(at0).getObjectAt(2);
			ASN1Encodable at021 = ASN1Sequence.getInstance(at02).getObjectAt(1);
			byte[] bitString = DERBitString.getInstance(at021).getBytes();
			byte[] pk = new byte[65];
			pk[0] = 0x04;
			int len = bitString.length;
			System.arraycopy(bitString, len-64, pk, 1, 64);
			return pk;
		} catch (IOException e) {
			throw new IOException("不符合SM2证书的格式，获取主体公钥信息失败", e);
		}
	}
	
	
	/**
	 * 输入证书字节，返回证书中的待签名主体的数据
	 * @return byte[] 证书主体信息
	 */
	public static byte[] getSM2TBSCertificateDate(byte[] src) throws IOException {
		try {
			ASN1Encodable at0 = ASN1Sequence.getInstance((ASN1Sequence.fromByteArray(src))).getObjectAt(0);
			return ASN1Sequence.getInstance(at0).getEncoded();
		} catch (IOException e) {
			throw new IOException("不符合SM2证书的格式，获取证书主体信息失败", e);
		}
	}
	
	/**
	 * 输入证书字节，返回签名值
	 * @return byte[] 返回SM2证书签名值（64位: x(32) | y(32)）
	 */
	public static byte[] getSM2signatureValue(byte[] src) throws IOException {
		try {
			DERBitString bsv = DERBitString.getInstance(ASN1Sequence.getInstance((ASN1Sequence.fromByteArray(src))).getObjectAt(2));
			byte[] ssv = bsv.getBytes();
			int len = ssv.length;
			byte[] sv = new byte[64];
			byte[] flag = new byte[1];
			flag[0] = ssv[len-33];
			if(0 == flag[0]){
				System.arraycopy(ssv, len-67, sv, 0, 32);
			}else{
				System.arraycopy(ssv, len-66, sv, 0, 32);
			}
			System.arraycopy(ssv, len-32, sv, 32, 32);
			return  sv;
		} catch (IOException e) {
			throw new IOException("不符合SM2证书的格式，获取证书主体信息失败", e);
		} 
	}


	/**
	 * 从证书中读取签名值
	 */
	public static ASN1BitString getSM2SignValueStruct(byte[] src) throws IOException {
		try {
			return  DERBitString.getInstance(
					ASN1Sequence.getInstance(
							ASN1Sequence.fromByteArray(src)
					).getObjectAt(2));
		} catch (IOException e) {
			throw new IOException("不符合SM2证书的格式，获取证书主体信息失败", e);
		}
	}


	/**
	 * 输入证书字节，返回有效期格式
	 */
	public static Date[] getSM2ValidTime(byte[] src) throws IOException, ParseException {
		try {
			ASN1Encodable at0 = ASN1Sequence.getInstance((ASN1Sequence.fromByteArray(src))).getObjectAt(0);
			ASN1Encodable at04 = ASN1Sequence.getInstance(at0).getObjectAt(4);
			ASN1Encodable at040 = ASN1Sequence.getInstance(at04).getObjectAt(0);
			ASN1Encodable at041 = ASN1Sequence.getInstance(at04).getObjectAt(1);
			Date st = null;
			Date et = null;
			if(at040 instanceof ASN1GeneralizedTime){
				st = DERGeneralizedTime.getInstance(at040).getDate();
			}else if(at040 instanceof ASN1UTCTime){
				st= DERUTCTime.getInstance(at040).getDate();
			}
			if(at041 instanceof ASN1GeneralizedTime){
				et = DERGeneralizedTime.getInstance(at041).getDate();
			}else if(at041 instanceof ASN1UTCTime){
				et= DERUTCTime.getInstance(at041).getDate();
			}
			return new Date[]{st, et};
		} catch (IOException e) {
			throw new IOException("不符合SM2证书的格式，获取证书有效期信息失败", e);
		}catch (ParseException e) {
			throw new ParseException("证书有效期转换时间格式失败", e.getErrorOffset());
		}
	}


	/**
	 * 返回证书序列号：
	 * @return ASN1Integer
	 */
	public static ASN1Integer getSm2Serial(byte[] src)throws IOException{
		try {
			ASN1Encodable at0 = ASN1Sequence.getInstance((ASN1Sequence.fromByteArray(src))).getObjectAt(0);
			ASN1Encodable at01 = ASN1Sequence.getInstance(at0).getObjectAt(1);
			return ASN1Integer.getInstance(at01);
		}catch (IOException e){
			throw new IOException("不符合SM2证书的格式，获取证书序列号信息失败", e);
		}
	}

	/**
	 * 获取sm2证书序列号
	 *  @return 输出16进制
	 */
	public static byte[] getSm2HexSerial(byte[] src)throws IOException{
		try {
			ASN1Encodable at0 = ASN1Sequence.getInstance((ASN1Sequence.fromByteArray(src))).getObjectAt(0);
			ASN1Encodable at01 = ASN1Sequence.getInstance(at0).getObjectAt(1);
			ASN1Integer derSerial = ASN1Integer.getInstance(at01);
			return derSerial.getValue().toByteArray();
		}catch (IOException e){
			throw new IOException("不符合SM2证书的格式，获取证书序列号信息失败", e);
		}
	}


	/**
	 * 从证书中读取颁发者
	 */
	public static ASN1Sequence getSm2Issuer(byte[] src)throws IOException{
		try {
			ASN1Encodable at0 = ASN1Sequence.getInstance((ASN1Sequence.fromByteArray(src))).getObjectAt(0);
			ASN1Sequence at03 = (ASN1Sequence)ASN1Sequence.getInstance(at0).getObjectAt(3);
			return at03;
		}catch (IOException e){
			throw new IOException("不符合SM2证书的格式，获取颁发者信息失败", e);
		}
	}

	/**
	 * 从证书读取证书主题subject
	 */
	public static ASN1Sequence getSm2Subject(byte[] src)throws IOException{
		try {
			ASN1Encodable at0 = ASN1Sequence.getInstance((ASN1Sequence.fromByteArray(src))).getObjectAt(0);
			ASN1Sequence at05 = (ASN1Sequence)ASN1Sequence.getInstance(at0).getObjectAt(5);
			return at05;
		}catch (IOException e){
			throw new IOException("不符合SM2证书的格式，获取颁发者信息失败", e);
		}
	}


	/**
	 * 解析得到sm2证书主题公钥结构（含算法和公钥）
	 */
	public static ASN1Sequence getSm2PkOnX509Encode(byte[] src)throws IOException{
		try {
			ASN1Encodable at0 = ASN1Sequence.getInstance((ASN1Sequence.fromByteArray(src))).getObjectAt(0);
			ASN1Sequence at06 = (ASN1Sequence)ASN1Sequence.getInstance(at0).getObjectAt(6);
			return at06;
		}catch (IOException e){
			throw new IOException("不符合SM2证书的格式，获取颁发者信息失败", e);
		}
	}

	

}
