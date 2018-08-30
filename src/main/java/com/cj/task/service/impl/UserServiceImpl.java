package com.cj.task.service.impl;

import com.cj.task.entity.Result;
import com.cj.task.entity.User;
import com.cj.task.mapper.UserMapper;
import com.cj.task.service.UserService;
import com.cj.task.utils.RegexUtils;
import com.cj.task.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


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
        return new Result.ResultBuilder().success("注册成功");
        //return Result.getSuccess(HttpStatus.OK.value(), "注册成功", LoginResponse.wrap(user));
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
            return new Result.ResultBuilder().fail( "用户名或密码错误");
            //Result.getFail(HttpStatus.BAD_REQUEST.value(), "用户或密码错误");
        }
        String token = TokenUtils.getToken(account);
        user.setToken(token);
        userMapper.updateUserByToken(user);
        return new Result.ResultBuilder().success("登录成功", user);
        //return Result.getSuccess(HttpStatus.OK.value(), "登录成功", LoginResponse.wrap(user));
    }

    public Result findUserByToken(String token) {
        User user = userMapper.findUserByToken(token);
        return new Result.ResultBuilder().success("根据token在查找用户成功", user);
    }

}
