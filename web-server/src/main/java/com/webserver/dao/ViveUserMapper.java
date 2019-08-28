package com.webserver.dao;

import java.util.List;

import com.webserver.pojo.ViveUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ViveUserMapper {

    //获取用户名单
    List<ViveUser> getUsers() throws Exception;

    //根据id删除用户
    int deleteUser(String loginId) throws Exception;

    //新增用户
    int addUser(ViveUser user) throws Exception;

    //根据id查询单个用户
    ViveUser querySingle(String loginId) throws Exception;

    //根据id修改用户信息
    int updateUser(ViveUser user) throws Exception;

    //修改用户密码错误次数
    void updateUserErrorCount(ViveUser user) throws Exception;

    //修改用户密码
    int updatePassword(ViveUser viveUser) throws Exception;

    //通过token获取user id
    int getUserIdByToken(String token) throws Exception;

    //通过user id 获取学校 id
    int getSchoolByUserId(int id) throws Exception;
}
