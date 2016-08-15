package com.weiwork.common.utils.encrypt;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.weiwork.common.exception.ServiceException;
import com.weiwork.common.utils.Util;

/**
 * 加解密工具类
 * <p>
 * DES算法、md5
 */
public class EncryptUtil {
	public static final String PASSWORD_CRYPT_KEY = "abcdefgh";
	private static final Logger logger = LoggerFactory.getLogger(EncryptUtil.class);

	/**
	 * DES加密
	 * @param message 待加密的数据
	 * @param password 密钥
	 * @return 密文
	 */
	public static String encrypt(final String message, final String password) {
		try {
			if (Util.isEmpty(message) || Util.isEmpty(password)) {
				return "";
			}
			SecureRandom random = new SecureRandom();
			DESKeySpec desKey = new DESKeySpec(password.getBytes("UTF-8"));
			//创建一个密匙工厂，然后用它把DESKeySpec转换成  
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey securekey = keyFactory.generateSecret(desKey);
			//Cipher对象实际完成加密操作  
			Cipher cipher = Cipher.getInstance("DES");
			//用密匙初始化Cipher对象  
			cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
			//现在，获取数据并加密  
			//正式执行加密操作  
			return new String(Base64.encodeBase64(cipher.doFinal(message.getBytes("UTF-8")), false));
		} catch (Exception e) {
			throw new ServiceException(1,"DES加密失败");
		}
	}

	/**
	 * 使用默认密钥的加密
	 * @param message 待加密的数据
	 * @return 密文
	 */
	public static String encrypt(final String message) {
		return encrypt(message, PASSWORD_CRYPT_KEY);
	}

	/**
	 * DES解密
	 * @param message 待解密的数据
	 * @param password 密钥
	 * @return 解密文
	 */
	public static String decrypt(final String message, final String password) {
		try {
			if (Util.isEmpty(message) || Util.isEmpty(password)) {
				return "";
			}
			byte[] decodeValue = Base64.decodeBase64(message.getBytes("UTF-8"));
			// DES算法要求有一个可信任的随机数源  
			SecureRandom random = new SecureRandom();
			// 创建一个DESKeySpec对象  
			DESKeySpec desKey = new DESKeySpec(password.getBytes("UTF-8"));
			// 创建一个密匙工厂  
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			// 将DESKeySpec对象转换成SecretKey对象  
			SecretKey securekey = keyFactory.generateSecret(desKey);
			// Cipher对象实际完成解密操作  
			Cipher cipher = Cipher.getInstance("DES");
			// 用密匙初始化Cipher对象  
			cipher.init(Cipher.DECRYPT_MODE, securekey, random);
			// 真正开始解密操作  
			return new String(cipher.doFinal(decodeValue), "UTF-8");
		} catch (Exception e) {
			throw new ServiceException(1, "DES解密失败");
		}
	}

	/**
	 * 使用默认密钥的DES解密
	 * @param message 待解密的数据
	 * @return 解密文
	 */
	public static String decrypt(final String message) {
		return decrypt(message, PASSWORD_CRYPT_KEY);
	}

	/**
	 * Md5加密
	 * @param message 待加密的数据
	 * @return 加密过后的数据
	 */
	public static String md5(final String message) {
		if (Util.isEmpty(message)) {
			return "";
		}
		return DigestUtils.md5Hex(message);
	}

	/**
	 * 计算文件的md5
	 *
	 * @param file 文件
	 * @return md5
	 */
	public static String getFileMd5(final File file) {
		if (file == null || !file.exists() || !file.isFile()) {
			return "";
		}
		FileInputStream in = null;
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			in = new FileInputStream(file);
			int len;
			byte buffer[] = new byte[1024];
			while ((len = in.read(buffer, 0, 1024)) != -1) {
				digest.update(buffer, 0, len);
			}
			BigInteger bigInt = new BigInteger(1, digest.digest());
			return bigInt.toString(16);
		} catch (Exception e) {
			logger.error("计算文件md5出错", e);
			return "";
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					logger.error("计算文件md5出错。关闭文件流失败", e);
				}
			}
		}
	}

	public static String encode(final String message) {
		if (Util.isEmpty(message)) {
			return "";
		}

		try {
			return Base64.encodeBase64String(message.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	public static String decode(final String message) {
		if (Util.isEmpty(message)) {
			return "";
		}
		byte[] b = Base64.decodeBase64(message);
		return new String(b);
	}

	public static void main(String[] args) {
		System.out.println(EncryptUtil
				.decode("ZmFsc2V2a29wbGF5ZXJodHRwOi8vbWVkaWEudmtvLmNuL2NvbnRlbnQvdGVhY2hlci92aWRlby9UMDA2Lm1wNA=="));
	}
}
