package com.webserver.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class UserLoginToken {
    //用户登录时token验证id
    private int id;
    //token
    private String token;
    //token创建时间
    private Date buildtime;
    //用户id
    private int viveuserid;
}
