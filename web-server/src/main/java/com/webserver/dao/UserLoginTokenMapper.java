package com.webserver.dao;

import com.webserver.pojo.UserLoginToken;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserLoginTokenMapper {
    //添加token
    void addToken(UserLoginToken token);

    //更新token
    void updataToken(UserLoginToken token);

    //查询token
    UserLoginToken findTokenByUserId(int userid);
}
