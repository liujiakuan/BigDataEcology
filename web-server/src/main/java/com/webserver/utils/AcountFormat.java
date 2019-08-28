package com.webserver.utils;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * create by: wangjunchang
 * description: Verify account and password format
 * create time: 15:09 2019/7/8
 */
@Component
public class AcountFormat {

    public boolean userAccountFormat(String loginName) {
        Pattern Account_Pattern = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,12}$");
        Matcher matcher = Account_Pattern.matcher(loginName);
        return matcher.matches();
    }

    public boolean checkPassword(String password) {
        Pattern Password_Pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-zA-Z])(.{8,20})$");
        Matcher matcher = Password_Pattern.matcher(password);
        return matcher.matches();
    }
}
