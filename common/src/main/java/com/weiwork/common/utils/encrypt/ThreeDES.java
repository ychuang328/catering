package com.weiwork.common.utils.encrypt;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

/**
 * 3Des加密
 * 
 * @author 杨闯
 *
 */
public class ThreeDES {

private static final String Algorithm = "DESede"; //定义 加密算法,可用 DES,DESede,Blowfish
    
    //keybyte为加密密钥，长度为24字节
    //src为被加密的数据缓冲区（源）
	//	返回加密后的二进制数组
    public static byte[] encrypt(byte[] keybyte, byte[] src) {
       try {
            //生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);

            //加密
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (java.lang.Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }
    
    //key为加密密钥，长度为24字节
    //src为被加密的数据缓冲区（源）,普通字符串
    // 返回加密后的二进制数组
    public static byte[] encrypt(String key, String src) {
    	return encrypt(key.getBytes(), src.getBytes());
    }
    
    //key为加密密钥，长度为24字节
    //src为被加密的数据缓冲区（源）,普通字符串
    // 返回加密后的16进制的字符串
    public static String encrypt2str(String key, String src) {
    	return Hex.encodeHexString(encrypt(key.getBytes(), src.getBytes())) ;
    }

    //keybyte为加密密钥，长度为24字节
    //src为加密后的缓冲区
    // 返回解密后的二进制数组
    public static byte[] decrypt(byte[] keybyte, byte[] src) {      
    try {
            //生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);

            //解密
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.DECRYPT_MODE, deskey);
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (java.lang.Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }
    
    //key为加密密钥，长度为24字节
    //src为加密后的16进制字符串
    // 返回解密后的二进制数组
    public static byte[] decrypt(String key, String src) throws DecoderException {      
    	return decrypt(key.getBytes(), Hex.decodeHex(src.toCharArray()));
    }
    
    //key为加密密钥，长度为24字节
    //src为加密后的16进制字符串
    // 返回解密后（Utf8编码）字符串
    public static String decrypt2str(String key, String src) throws DecoderException {      
    	return StringUtils.toEncodedString(decrypt(key.getBytes(), Hex.decodeHex(src.toCharArray())), Charsets.UTF_8);
    }

    //将二进制数组转换成十六进制字符串
    public static String byte2hex(byte[] b) {
        String hs="";
        String stmp="";

        for (int n=0;n<b.length;n++) {
            stmp=(java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length()==1) hs=hs+"0"+stmp;
            else hs=hs+stmp;
            if (n<b.length-1)  hs=hs;
        }
        return hs;
    }
    
    //将二进制数组转换成十六进制字符串
    public static String byte2hexOfApache(byte[] b) {
        return Hex.encodeHexString(b);
    }
    
    //将十六进制字符串转换成二进制
    public static byte[] hex2byteOfApache(String strHex) {
        try {
			return Hex.decodeHex(strHex.toCharArray());
		} catch (DecoderException e) {
			e.printStackTrace();
		}
        return null;
    }
    
    public static void main(String[] args)
    {
        //添加新安全算法,如果用JCE就要把它添加进去
//        Security.addProvider(new com.sun.crypto.provider.SunJCE());

        final String key = "vko2015partner0000000000";    //24字节的密钥
        System.out.println(key+" 24字节的密钥 "+key.getBytes().length);
//        String szSrc = "{\"oId\":\"20151119-4405-100000046\",\"schoolId\":\"2000000015000001032\",\"account\":\"compAccount\",\"amt\":\"100.00\"}";
        String szSrc = "{\"schoolId\":\"2300000001000357975\",\"userId\":\"2000000056000000272\",\"loginName\":\"yanke001\",\"token\":\"NWZiYmQ0YTliMDUzOGM3MWE2OTU5NjNjOGJmMzhkMTM\" }";
//        String szSrc = "{\"resultCode\":\"0\",\"msg\":\"成功\",\"endDate\":\"20151220\"}";
        
        System.out.println("加密前的字符串:" + szSrc);
        
        byte[] encoded = encrypt(key.getBytes(), szSrc.getBytes());        
//        System.out.println("加密后的字符串:" + new String(encoded)); //乱码, 用Base64转化
//        String cipherString=Base64.encode(encoded);
        String cipherString=Hex.encodeHexString(encoded);
        System.out.println("加密后的字符串:" + cipherString);
        System.out.println("加密后的16进制字符串:" + byte2hex(encoded));
        System.out.println("加密后的16进制字符串:" + byte2hexOfApache(encoded));
        System.out.println("加密后的16进制字符串:" + encrypt2str(key, szSrc));
        
//        byte[] reqPassword = Base64.decode(cipherString);  
        byte[] reqPassword = null;
		try {
			reqPassword = Hex.decodeHex(cipherString.toCharArray());
		} catch (DecoderException e) {
			e.printStackTrace();
		}
        
		byte[] srcBytes = decrypt(key.getBytes(), reqPassword);
		
		try {
			System.out.println("解密后的字符串:" + (new String(srcBytes)));
	        System.out.println("解密后的16进制字符串:" + (byte2hexOfApache(srcBytes)));
        
	        System.out.println("解密后的字符串:" + (decrypt2str(key, cipherString)));
	        System.out.println("解密后的字符串:" + (decrypt2str(key, encrypt2str(key, szSrc))));
			System.out.println("解密后的字符串:" + (decrypt2str(key, "ca3e98490f14fd1d068d2c3ff4b62fae523015519ddd4c3820d5063f086e430a0865c335b88a39c98af00912446cf52ff28de5266aad73af46a1e51b9fe12005ae8ab77349568911ad2e630b9398c438986616f5e5e0b8256fd817b07618c156dcfa5b0b0c59d2b62e981c05eb8edaf9a3f05d00e1ea5c9499804c9934a63d03e2f60c219331f409")));
			
		} catch (DecoderException e) {
			e.printStackTrace();
		}
    }

}