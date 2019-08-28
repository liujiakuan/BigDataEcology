package com.webserver.model.viveUserModel;

import com.webserver.pojo.ViveUser;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Result {
    //请求返回信息
    private String message;
    //请求返回状态
    private boolean status;
    //请求返回的token
    private String token;
    //请求返回的用户信息
    private ViveUser viveUser;
}
