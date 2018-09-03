package com.cj.task.service.impl;

import com.cj.task.entity.Result;
import com.cj.task.entity.User;
import com.cj.task.entity.response.UserDetailResponse;
import com.cj.task.entity.response.UserListResponse;
import com.cj.task.entity.response.UserResponse;
import com.cj.task.mapper.UserMapper;
import com.cj.task.service.UserService;
import com.cj.task.utils.RegexUtils;
import com.cj.task.utils.TokenUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;


/**
 * Created by cj on 2018/8/26.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    public Result registerUser(String account, String pwd, String nickName) {
        if (StringUtils.isEmpty(account)) {
            return new Result.ResultBuilder().fail("用户名为空");
            //return Result.getFail(HttpStatus.BAD_REQUEST.value(), "用户名为空");
        }
        if (StringUtils.isEmpty(pwd)) {
            return new Result.ResultBuilder().fail("密码为空");
            //return Result.getFail(HttpStatus.BAD_REQUEST.value(), "密码为空");
        }
        if (StringUtils.isEmpty(nickName)) {
            return new Result.ResultBuilder().fail("昵称为空");
            //return Result.getFail(HttpStatus.BAD_REQUEST.value(), "昵称为空");
        }
        if (!RegexUtils.isAccount(account)) {
            return new Result.ResultBuilder().fail("您输入的账号不符合规范");
            //return Result.getFail(HttpStatus.BAD_REQUEST.value(), "您输入的账号不符合规范");
        }
        if (!RegexUtils.isPwd(pwd)) {
            return new Result.ResultBuilder().fail("您输入的密码不符合规范");
            //return Result.getFail(HttpStatus.BAD_REQUEST.value(), "您输入的密码不符合规范");
        }
        if (nickName.length() > 8) {
            return new Result.ResultBuilder().fail("您输入的昵称长度不符合规范");
        }
        User userByAccount = userMapper.findUserByAccount(account);
        if (null != userByAccount) {
            return new Result.ResultBuilder().fail("您输入的用户名已存在");
            //return Result.getFail(HttpStatus.BAD_REQUEST.value(), "您输入的用户名已存在");
        }
        User user = new User();
        user.setAccount(account);
        user.setPwd(pwd);
        user.setNickName(nickName);
        userMapper.save(user);
        return new Result.ResultBuilder().success("注册成功", UserResponse.wrap(user));
    }

    public Result login(String account, String pwd) {
        if (StringUtils.isEmpty(account)) {
            return new Result.ResultBuilder().fail("账号为空");
            //Result.getFail(HttpStatus.BAD_REQUEST.value(), "账号为空");
        }
        if (StringUtils.isEmpty(pwd)) {
            return new Result.ResultBuilder().fail("密码为空");
            //Result.getFail(HttpStatus.BAD_REQUEST.value(), "密码为空");
        }
//        Map map=new HashMap();
//        map.put("account",account);
//        map.put("pwd",account);
        User user = userMapper.findUserByAccountAndPwd(account, pwd);
        if (null == user) {
            return new Result.ResultBuilder().fail("用户名或密码错误");
            //Result.getFail(HttpStatus.BAD_REQUEST.value(), "用户或密码错误");
        }
        String token = TokenUtils.getToken(account);
        user.setToken(token);
        userMapper.updateUserByToken(user);
        return new Result.ResultBuilder().success("登录成功", UserResponse.wrap(user));
    }

    public Result findUserByToken(String token) {
        User user = userMapper.findUserByToken(token);
        return new Result.ResultBuilder().success("根据token在查找用户成功", UserResponse.wrap(user));
    }

    public Result updateUserinfo(String nickName, User user) {
        if (StringUtils.isEmpty(nickName)) {
            return new Result.ResultBuilder().fail("新昵称为空");
        }
        if (nickName.length() > 8) {
            return new Result.ResultBuilder().fail("新昵称长度不符合规范");
        }
        System.out.println("user.getID" + user.getId());
        userMapper.updateUserinfo(nickName, user.getId());
        return new Result.ResultBuilder().success("修改用户信息成功", UserResponse.wrap(user));
    }

    public Result updatePwd(String oldPwd, String newPwd, User user) {
        if (StringUtils.isEmpty(oldPwd)) {
            return new Result.ResultBuilder().fail("您输入的旧密码为空");
        }
        if (StringUtils.isEmpty(newPwd)) {
            return new Result.ResultBuilder().fail("您输入的新密码为空");
        }
        if (!RegexUtils.isPwd(newPwd)) {
            return new Result.ResultBuilder().fail("您输入的新密码不符合规范");
        }
        if (!oldPwd.equals(user.getPwd())) {
            return new Result.ResultBuilder().fail("您输入的旧密码错误");
        }
        userMapper.updatePwd(newPwd, user.getId());
        return new Result.ResultBuilder().success("修改用户密码成功", UserResponse.wrap(user));
    }

    public Result userList(int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<User> userList = userMapper.userList();
        return new Result.ResultBuilder().success("获取用户列表成功", userList);
    }

    public Result findUserById(int id) {
        User user = userMapper.findUserById(id);
        if (user == null) {
            return new Result.ResultBuilder().fail(HttpStatus.NOT_FOUND.value(), "无该用户");
        }
        return new Result.ResultBuilder().success("获取该用户信息成功", UserDetailResponse.wrap(user));
    }

    public Result deleteUser(int id) {
        userMapper.delete(id);
        return new Result.ResultBuilder().success("删除用户成功");
    }
}
