package com.smart.springbootfacerecognition.util;

import org.springframework.util.DigestUtils;

public class StringUtil {
    public static String passwordToMd5(String password) {
        if ("".equals(password) || password == null) {
            return "";
        }
        return DigestUtils.md5DigestAsHex(DigestUtils.md5DigestAsHex(password.getBytes()).getBytes());
    }
}
