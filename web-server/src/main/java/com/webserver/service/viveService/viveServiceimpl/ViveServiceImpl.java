package com.webserver.service.viveService.viveServiceimpl;

import com.webserver.dao.UserLoginTokenMapper;
import com.webserver.dao.ViveUserMapper;
import com.webserver.model.viveUserModel.Result;
import com.webserver.pojo.UserLoginToken;
import com.webserver.pojo.ViveUser;
import com.webserver.service.viveService.ViveUserService;
import com.webserver.utils.AcountFormat;
import com.webserver.utils.CreatToken;
import com.webserver.utils.PwdEncryption;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ViveServiceImpl implements ViveUserService {
    private ViveUserMapper userMapper;
    private PwdEncryption pwdEncryption;
    private AcountFormat format;
    private UserLoginTokenMapper userLoginTokenMapper;

    @Autowired
    public void setViveUserMapper(ViveUserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setPwdEncryption(PwdEncryption pwdEncryption) {
        this.pwdEncryption = pwdEncryption;
    }

    @Autowired
    public void setFormat(AcountFormat format) {
        this.format = format;
    }

    @Autowired
    public void setUserLoginTokenMapper(UserLoginTokenMapper userLoginTokenMapper) {
        this.userLoginTokenMapper = userLoginTokenMapper;
    }

    //查询全部用户
    @Override
    public List<ViveUser> getUsers() throws Exception {
        return userMapper.getUsers();
    }

    //根据id删除用户
    @Override
    public Result deleteUser(String loginId) throws Exception {
        if (StringUtils.isEmpty(loginId)) {
            return new Result("账号不能为空", false, null, null);
        }
        ViveUser viveData = userMapper.querySingle(loginId);
        if (StringUtils.isEmpty(viveData.getName())) {
            return new Result("没有此用户", false, null, null);
        }
        int row = userMapper.deleteUser(loginId);
        if (row > 0) {
            return new Result("删除成功", true, null, null);
        } else {
            return new Result("删除失败", false, null, null);
        }
    }

    //新增用户
    @Override
    public Result addUser(ViveUser user) throws Exception {
        if (StringUtils.isEmpty(user.getName()) || StringUtils.isEmpty(user.getPassword())) {
            return new Result("账号不能为空", false, null, null);
        }
        if (!format.userAccountFormat(user.getName()) || !format.checkPassword(user.getPassword())) {
            return new Result("用户账号或密码格式不正确", false, null, null);
        }
        ViveUser viveData = userMapper.querySingle(user.getName());
        if (null != viveData && StringUtils.isNotEmpty(viveData.getName())) {
            return new Result("该账号已被注册", false, null, null);
        }
        String salt = UUID.randomUUID().toString();
        String encodePassword = pwdEncryption.encode(user.getPassword(), salt);
        user.setPassword(encodePassword);
        user.setPasswordsalt(salt);
        user.setPwdlastmodifytime(new Date());
        int row = userMapper.addUser(user);
        if (row > 0) {
            return new Result("添加成功", true, null, null);
        } else {
            return new Result("添加失败", false, null, null);
        }
    }

    //查询单个用户
    @Override
    public Result queryUser(String loginId) throws Exception {
        if (StringUtils.isEmpty(loginId)) {
            return new Result("用户登录名不能为空", false, null, null);
        }
        ViveUser viveData = userMapper.querySingle(loginId);
        if (StringUtils.isEmpty(viveData.getName())) {
            return new Result("没有此用户", false, null, null);
        } else {
            return new Result("查询成功", true, null, viveData);
        }
    }

    //根据id修改用户信息
    @Override
    public Result updateUser(ViveUser viveUser) throws Exception {
        if (StringUtils.isEmpty(viveUser.getName())) {
            return new Result("用户登录名不能为空", false, null, null);
        }
        ViveUser userData = userMapper.querySingle(viveUser.getName());
        if (StringUtils.isEmpty(userData.getName())) {
            return new Result("没有此用户信息", false, null, null);
        }
        int row = userMapper.updateUser(viveUser);
        if (row > 0) {
            return new Result("修改成功", true, null, null);
        }
        return new Result("未知错误，修改失败", false, null, null);
    }

    //用户登录
    @Override
    public Result login(String username, String nowPwd) throws Exception {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(nowPwd)) {
            return new Result("用户名或密码不能为空", false, null, null);
        }
        if (!format.userAccountFormat(username) || !format.checkPassword(nowPwd)) {
            return new Result("用户名或密码格式不正确", false, null, null);
        }
        ViveUser viveData = userMapper.querySingle(username);
        if (StringUtils.isEmpty(viveData.getName())) {
            return new Result("没有此账号", false, null, null);
        }
        //TODO 判断用户是否已经在别的地方登陆
        //判断是否在三十分钟内输错密码三次
        long now = new Date(System.currentTimeMillis()).getTime();
        if (viveData.getPwddefaulterrornum() > 3 && viveData.getPwdlastmodifytime().getTime() - now >= 30 * 60000) {
            viveData.setPwddefaulterrornum(0);
            viveData.setPwdlastmodifytime(new Date());
            userMapper.updateUserErrorCount(viveData);
        }
        if (viveData.getPwddefaulterrornum() > 3) {
            return new Result("你已输入密码错误超过三次了！请30分钟后再登录", false, null, null);
        }
        if (!pwdEncryption.verify(viveData.getPassword(), nowPwd, viveData.getPasswordsalt())) {
            viveData.setPwddefaulterrornum(viveData.getPwddefaulterrornum() + 1);
            userMapper.updateUserErrorCount(viveData);
            return new Result("密码输入错误!", false, null, null);
        }
        //根据数据库的用户信息查询Token
        UserLoginToken token = userLoginTokenMapper.findTokenByUserId(viveData.getId());
        //为生成Token准备
        String tokenStr;
        Date date = new Date();
        //int nowtime = (int) (date.getTime() / 1000);
        //生成Token
        tokenStr = CreatToken.creatToken(viveData, date);
        if (null == token) {
            //第一次登陆
            token = new UserLoginToken();
            token.setToken(tokenStr);
            //token.setBuildtime(nowtime);
            token.setBuildtime(date);
            token.setViveuserid(viveData.getId());
            userLoginTokenMapper.addToken(token);
        } else {
            //登陆就更新Token信息
            tokenStr = CreatToken.creatToken(viveData, date);
            token.setToken(tokenStr);
            token.setBuildtime(date);
            userLoginTokenMapper.updataToken(token);
        }
        //返回Token信息给客户端
        return new Result("登录成功", true, tokenStr, null);
    }

    //修改密码
    @Override
    public Result updatePassword(String loginId, String password, String newPwd) throws Exception {
        if (StringUtils.isEmpty(loginId) || StringUtils.isEmpty(password) || StringUtils.isEmpty(newPwd)) {
            return new Result("用户名或密码不能为空", false, null, null);
        }
        if (!format.checkPassword(newPwd)) {
            return new Result("新密码格式不正确", false, null, null);
        }
        ViveUser viveData = userMapper.querySingle(loginId);
        if (pwdEncryption.verify(viveData.getPassword(), password, viveData.getPasswordsalt())) {
            viveData.setPassword(pwdEncryption.encode(newPwd, viveData.getPasswordsalt()));
            int row = userMapper.updatePassword(viveData);
            if (row > 0) {
                return new Result("密码修改成功", true, null, null);
            } else {
                return new Result("密码修改失败", false, null, null);
            }
        }
        return new Result("密码不正确", false, null, null);
    }

    //通过token获取user id
    @Override
    public int getUserIdByToken(String token) throws Exception {
        return userMapper.getUserIdByToken(token);
    }

    //通过user id 获取学校 id
    @Override
    public int getSchoolByUserId(int id) throws Exception {
        return userMapper.getSchoolByUserId(id);
    }
}


