package com.tritonsfs.cac.sso.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @Time 2018/4/10
 * @Author zlian
 */
public class MD5Encoder {
    private static final String HEX_NUMS_STR="0123456789ABCDEF";

    /**
     * 获得加密后的16进制形式口令(加密)
     * @description getEncryptedPwd
     * @author 佛祖保佑后的最
     * @param [password]
     * @return java.lang.String
     * @time 2018/4/10
     */
    public static String getEncryptedPwd(String password,String date) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        //盐数组变量
        byte[] salt = date.getBytes();
        //消息摘要对象
        MessageDigest md = MessageDigest.getInstance("MD5");
        //将数据传入消息摘要对象
        md.update(salt);
        md.update(password.getBytes("UTF-8"));
        //获得加密后的字节数组
        byte[] digest = md.digest();
        //将字节数组格式加密后的口令转化为16进制字符串格式的口令
        return byteToHexString(digest);
    }

    /**
     * 验证口令是否合法(验证)
     * @description validPassword
     * @author 佛祖保佑后的最
     * @param [password, passwordInDb]
     * @return boolean
     * @time 2018/4/10
     */
    public static boolean validPassword(String password, String passwordInDb,String date)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //盐变量
        byte[] salt = date.getBytes();
        //消息摘要对象
        MessageDigest md = MessageDigest.getInstance("MD5");
        //将数据传入消息摘要对象
        md.update(salt);
        md.update(password.getBytes("UTF-8"));
        //获得加密后的字节数组
        byte[] digest = md.digest();
        //将字节数组格式加密后的口令转化为16进制字符串格式的口令
        String passwordToCheck = byteToHexString(digest);
        //判断是否相同
        if (passwordInDb.equals(passwordToCheck)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 将指定byte数组转换成16进制字符串
     * @description byteToHexString
     * @author 佛祖保佑后的最
     * @param [b]
     * @return java.lang.String
     * @time 2018/4/10
     */
    private static String byteToHexString(byte[] b) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            hexString.append(hex.toUpperCase());
        }
        return hexString.toString();
    }


    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException, ParseException {
        Date date = new Date();
        SimpleDateFormat formatter5 = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = formatter5.format(date);
        //2018-04-10
        String p = getEncryptedPwd("a123456","2018-04-11");
        System.out.println(p);
        //C43AF4591CD40F28D9AD68E8B62EBE2E
        boolean result = validPassword("a123456",p,
                "2018-04-11");
        System.out.println(result);
    }

}
