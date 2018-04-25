package com.maywidehb.qrpush.utils;



import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.Key;


/**
 * 3DES加密工具类 
 */  
public class Des3 {  
    // 密钥  
    private final static String secretKey = "maywide1234@lx100$#365#$";  
    // 向量  
    private final static String iv = "01234567";  
    // 加解密统一使用的编码方式  
    private final static String encoding = "utf-8";  
  
    /** 
     * 3DES加密
     * @param plainText 普通文本 
     * @return 
     * @throws Exception  
     */  
    public static String encode(String plainText) throws Exception {  
        Key deskey = null;  
        DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());  
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");  
        deskey = keyfactory.generateSecret(spec);  
  
        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");  
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());  
        cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);  
        byte[] encryptData = cipher.doFinal(plainText.getBytes(encoding));  
        return Base64.encode(encryptData);  
    }  
  
    /** 
     * 3DES解密 
     *  
     * @param encryptText 加密文本 
     * @return 
     * @throws Exception 
     */  
    public static String decode(String encryptText) throws Exception { 
    	if(encryptText == null || encryptText.length() == 0){
        	throw new Exception("解密失败:加密字符串为空");
    	}
        Key deskey = null;  
        DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());  
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");  
        deskey = keyfactory.generateSecret(spec);  
        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");  
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());  
        cipher.init(Cipher.DECRYPT_MODE, deskey, ips);  
        byte[] decryptData =null;
        try{
            decryptData = cipher.doFinal(Base64.decode(encryptText));  
        }catch (Exception e){
        	e.printStackTrace();
        	throw new Exception("解密失败:"+e.getMessage());
        }
  
        return new String(decryptData, encoding);  
    }  
    
    /** 
     * 3DES加密
     * @return
     * @throws Exception  
     */  
    public static byte[] encodeByte(byte[] data) throws Exception {  
        Key deskey = null;  
        DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());  
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");  
        deskey = keyfactory.generateSecret(spec);  
  
        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");  
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());  
        cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);  
        byte[] encryptData = cipher.doFinal(data);  
        return encryptData;  
    }
    /** 
     * 3DES解密 
     * @return
     * @throws Exception 
     */  
    public static byte[] decodeByte(byte[] encryptdata) throws Exception {  
        Key deskey = null;  
        DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());  
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");  
        deskey = keyfactory.generateSecret(spec);  
        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");  
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());  
        cipher.init(Cipher.DECRYPT_MODE, deskey, ips);  
        byte[] decryptData =null;
        try{
            decryptData = cipher.doFinal(encryptdata);  
        }catch (Exception e){
        	throw new Exception("解密失败:"+e.getMessage());
        }
  
        return decryptData;
    }   
    /**
     * DES解密算法 hex值只有数字和子母
     * @param data
     * @return
     * @throws Exception
     */
    public static String hexdecrypt(String data) throws Exception {
        return new String(decodeByte(hex2byte(data.getBytes())));
    }

    /**
     * DES加密算法
     * @param data
     * @return
     * @throws Exception
     */
    public  static String hexencrypt(String data)
            throws Exception {
        return byte2hex(encodeByte(data.getBytes(encoding)));
    }
    
    private static byte[] hex2byte(byte[] b) {
        if ((b.length % 2) != 0)
            throw new IllegalArgumentException("长度不是偶数");
        byte[] b2 = new byte[b.length / 2];
        for (int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2);
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }

    private static String byte2hex(byte[] b) {
        StringBuffer hs = new StringBuffer();
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
//                hs = hs + "0" + stmp;
                  hs.append("0").append(stmp);
            else
//                hs = hs + stmp;
                hs.append(stmp);

        }
        return hs.toString().toUpperCase();
    }

} 