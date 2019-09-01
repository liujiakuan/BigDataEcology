package com.webserver.service.viveService;

import com.webserver.model.viveUserModel.Result;
import com.webserver.pojo.ViveUser;

import java.util.List;

public interface ViveUserService {
    //获取用户名单
    List<ViveUser> getUsers() throws Exception;

    //根据id删除用户
    Result deleteUser(String loginId) throws Exception;

    //新增用户
    Result addUser(ViveUser user) throws Exception;

    //根据id获取单个用户
    Result queryUser(String loginId) throws Exception;

    //根据id修改用户信息
    Result updateUser(ViveUser viveUser) throws Exception;

    //用户登录
    Result login(String loginId, String passWord) throws Exception;

    //修改用户密码
    Result updatePassword(String loginId, String password, String newPwd) throws Exception;

    //通过token获取user id
    int getUserIdByToken(String token) throws Exception;

    //通过user id 获取学校 id
    int getSchoolByUserId(int id) throws Exception;
}
