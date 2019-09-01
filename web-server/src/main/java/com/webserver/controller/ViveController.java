package com.webserver.controller;

import com.webserver.controller.webUiJsonParameterObject.UserLogin;
import com.webserver.model.viveUserModel.Result;
import com.webserver.pojo.ViveUser;
import com.webserver.service.viveService.ViveUserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class ViveController {

    private ViveUserService userService;

    @Autowired
    public void setUserService(ViveUserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "获取用户列表", notes = "获取所有用户")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<ViveUser> index() throws Exception {
        return userService.getUsers();
    }

    @ApiOperation(value = "删除用户", notes = "根据用户id删除用户")
    @ApiImplicitParam(name = "loginId", value = "用户登录ID", required = true, dataType = "string", example = "abc123")
    @RequestMapping(path = "/delete", method = RequestMethod.DELETE)
    public Result delete(@RequestParam(value = "loginId") String loginId) throws Exception {
        return userService.deleteUser(loginId);
    }

    @ApiOperation(value = "新增用户", notes = "新增用户")
    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public Result addUser(@RequestBody ViveUser viveUser) throws Exception {
        return userService.addUser(viveUser);
    }

    @ApiOperation(value = "获取用户详细信息", notes = "根据user的id来获取用户详细信息")
    @ApiImplicitParam(name = "loginId", value = "用户登录ID", required = true, dataType = "string", example = "123")
    @RequestMapping(path = "/{loginId}", method = RequestMethod.GET)
    public Result queryUser(@PathVariable(value = "loginId") String loginId) throws Exception {
        return userService.queryUser(loginId);
    }

    @ApiOperation(value = "修改用户信息", notes = "根据user的id来修改用户详细信息")
    @RequestMapping(path = "/update", method = RequestMethod.POST)
    public Result updateUser(@RequestBody ViveUser viveUser) throws Exception {
        return userService.updateUser(viveUser);
    }

    @ApiOperation(value = "用户登录", notes = "根据用户账户及密码登录")
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public Result login(@RequestBody @Valid UserLogin userLogin) throws Exception {
        return userService.login(userLogin.getName(), userLogin.getPassword());
    }

    @ApiOperation(value = "修改用户登录密码", notes = "根据user的id来修改用户登录密码")
    @RequestMapping(path = "/updatePwd", method = RequestMethod.POST)
    public Result updateUserPwd(@RequestParam(value = "loginid") String loginId,
                                @RequestParam(value = "password") String password,
                                @RequestParam(value = "newPwd") String newPwd) throws Exception {
        return userService.updatePassword(loginId, password, newPwd);
    }

    @ApiOperation(value = "test", notes = "test interface")
    @RequestMapping(path = "/test", method = RequestMethod.POST)
    public Result test() {
        return new Result("test", true, "test", null);
    }
}
