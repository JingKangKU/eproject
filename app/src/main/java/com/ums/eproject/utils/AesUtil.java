package com.ums.eproject.utils;



import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;



/**
 *  ase加密
 * 
 * @author shijianquan
 *
 */
public class AesUtil {

	/**
	 * AES加密
	 * 
	 * @param sSrc   需加密内容
	 * @param keyStr 加密key
	 * @param ivStr  偏移量
	 * @return 加密内容
	 */
	public static String aesEncrypt(String sSrc, String keyStr, String ivStr) {
		if (StrUtil.isEmpty(sSrc)) {
			return null;
		}
		try {
			byte[] raw = keyStr.getBytes("ASCII");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec iv = new IvParameterSpec(ivStr.getBytes());
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			// 先用bAES64解密
			byte[] encrypted1 = sSrc.getBytes("utf-8");
			try {
				byte[] original = cipher.doFinal(encrypted1);
				String originalString = Base64Utils.encode(original);
				return originalString;
			} catch (Exception e) {
				return null;
			}
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}
	/**
	 * 获取加密后的值
	 * <br><b>作者： ryan</b>
	 * <br>创建时间：2023年1月9日 上午12:06:14
	 * @since 1.0
	 * @return
	 */
	public static String encryptValue(String value) {
		
		StringBuffer keySb = new StringBuffer(Constant.publicKey);
		String ivStr = keySb.reverse().toString();
		
		return aesEncrypt(value, Constant.publicKey, ivStr);
	}

}
