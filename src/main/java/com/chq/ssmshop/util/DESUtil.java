package com.chq.ssmshop.util;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import org.springframework.util.Base64Utils;

public class DESUtil {
	private static Key key;
	private static final String KEY_STR = "mykey";
	private static final String CHARSET = "UTF-8";
	private static final String ALGORITHM = "DES";

	static {
		try {
			// 对称加密的秘钥生成器
			KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
			// 随机数发生器，产生随机数的算法是SHA1PRNG
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			// 为随机数发生器设置种子数
			secureRandom.setSeed(KEY_STR.getBytes());
			// 初始化秘钥生成器
			keyGenerator.init(secureRandom);
			// 生成秘钥
			key = keyGenerator.generateKey();
			// 销毁秘钥生成器
			keyGenerator = null;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 传入需要加密的字符串，返回DES加密后的字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String getEncryptString(String str) {
		try {
			// 将需要加密的字符串按照指定的字符集转换为byte数组
			byte[] bytes = str.getBytes(CHARSET);
			// 获取DES算法的加密的加密对象，它承担了真正加密任务
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			// 初始化密码信息
			cipher.init(Cipher.ENCRYPT_MODE, key);
			// 进行加密
			byte[] doFinal = cipher.doFinal(bytes);
			// 把加密后的byte数组再通过Base64加密为字符串
			return Base64Utils.encodeToString(doFinal);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 解密部分
	 * 
	 * @param str
	 * @return
	 */
	public static String getDecryptString(String str) {
		try {
			// 先将Base64加密的字符串解密为byte数组
			byte[] bytes = Base64Utils.decodeFromString(str);
			// 创建解码器
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, key);
			// 进行DES解密
			byte[] doFinal = cipher.doFinal(bytes);
			// 返回解密后的字符串
			return new String(doFinal, CHARSET);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public static void main(String[] args) {
//		String encryptStr = DESUtil.getEncryptString("work");
//		System.out.println(encryptStr);
//		String decryptStr = DESUtil.getDecryptString(encryptStr);
//		System.out.println(decryptStr);
//		String encryptStr = DESUtil.getEncryptString("root");
//		System.out.println(encryptStr);
//		String decryptStr = DESUtil.getDecryptString(encryptStr);
//		System.out.println(decryptStr);
		String encryptStr = DESUtil.getEncryptString("Cheng950112!");
		System.out.println(encryptStr);
		String decryptStr = DESUtil.getDecryptString(encryptStr);
		System.out.println(decryptStr);
	}

}
