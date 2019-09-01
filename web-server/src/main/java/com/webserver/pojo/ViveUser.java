package com.webserver.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class ViveUser {
    //用户id
    private int id;
    //用户姓名
    private String name;
    //用户密码
    private String password;
    //默认错误次数
    private int pwddefaulterrornum;
    //最后修改时间(判断过期时间)
    private Date pwdlastmodifytime;
    //用户账号是否被锁住
    private char islock;
    //用户账号是否有效
    private char effective;
    //用户账号密码盐值
    private String passwordsalt;
    //一个用户有一个token信息
    UserLoginToken userLoginToken;
    //一个用户属于一个学校
//    private School school;
}
