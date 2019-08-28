package com.webserver.utils;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;

/**
 * create by: wangjunchang
 * description: Password encryption
 * create time: 10:02 2019/7/18
 *
 * @Param: null
 * @return
 */
@Component
public class PwdEncryption {


    public String encode(String password, String salt) {
        String merge = password + salt;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(merge.getBytes("GBK"));
            return bytes2Hex(md.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String bytes2Hex(byte[] bts) {
        StringBuilder des = new StringBuilder();
        String tmp;
        for (byte bt : bts) {
            tmp = (Integer.toHexString(bt & 0xFF));
            if (tmp.length() == 1) {
                des.append("0");
            }
            des.append(tmp);
        }
        return des.toString();
    }

    //判断密码是否一致
    public boolean verify(String password, String nowPwd, String salt) {
        if (StringUtils.isEmpty(password) || StringUtils.isEmpty(salt) || StringUtils.isEmpty(nowPwd)) {
            return false;
        }
        String encode = encode(nowPwd, salt);
        if (StringUtils.isEmpty(encode)) {
            return false;
        }
        return password.equals(encode);
    }
}
