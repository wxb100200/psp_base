package com.base.am.util;

import cn.hillwind.common.util.BASE64Codec;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密工具类
 */
public class EncryptUtil {

    /**
     * md5加密
     */
     public static byte[] md5(byte[] bytes) throws NoSuchAlgorithmException, UnsupportedEncodingException {
         return hash(bytes,"MD5");
     }
    /**
     * md5加密
     */
     public static byte[] md5(InputStream stream) throws NoSuchAlgorithmException, IOException {
         return hash(stream,"MD5");
     }
    /**
     * md5加密
     */
     public static String md5(String str) throws UnsupportedEncodingException, NoSuchAlgorithmException {
         byte[] bytes=str.getBytes("utf-8");
         return new String(hash(bytes,"MD5"));
     }
    /**
     * SHA1加密
     */
     public static byte[] sha1(byte[] bytes) throws NoSuchAlgorithmException, UnsupportedEncodingException {
         return hash(bytes,"SHA1");
     }
    /**
     * SHA1加密
     */
     public static byte[] sha1(InputStream stream) throws NoSuchAlgorithmException, IOException {
         return hash(stream,"SHA1");
     }
    /**
     * SHA1加密
     */
     public static String sha1(String str) throws UnsupportedEncodingException, NoSuchAlgorithmException {
         byte[] bytes=str.getBytes("utf-8");
         return new String(hash(bytes,"SHA1"));
     }

    private static byte[] hash(byte[] bytes,String type) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(type);
        digest.update(bytes);
        byte[] md5 = digest.digest();
        return BASE64Codec.encode(md5 );
    }
    private static byte[] hash(InputStream stream,String type) throws NoSuchAlgorithmException, IOException {
        MessageDigest digest =MessageDigest.getInstance(type);
        BufferedInputStream bis=new BufferedInputStream(stream,1024000);
        byte[] buffer=new byte[1024000];
        while (true){
            int size=bis.read(buffer);
            if(size==-1){
                break;
            }else if(size>0){
                digest.update(buffer,0,size);
            }
        }
        return BASE64Codec.encode(digest.digest());
    }

    public static void main(String[] args){
         String key="j1ElAVbxzNr73XL3CbKCH8==";
         String password="111111";
         String password2="dDarD1wy8Xk=";
         String data=key+password;
         System.out.println(data);

    }
}
