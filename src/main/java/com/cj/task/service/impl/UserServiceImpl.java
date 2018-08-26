package com.cj.task.service.impl;

import com.cj.task.entity.Result;
import com.cj.task.entity.User;
import com.cj.task.mapper.UserMapper;
import com.cj.task.service.UserService;
import com.cj.task.utils.RegexUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
            return Result.getFail(HttpStatus.BAD_REQUEST.value(), "用户名为空");
        }
        if (StringUtils.isEmpty(pwd)) {
            return Result.getFail(HttpStatus.BAD_REQUEST.value(), "密码为空");
        }
        if (StringUtils.isEmpty(nickName)) {
            return Result.getFail(HttpStatus.BAD_REQUEST.value(), "昵称为空");
        }
        if (!RegexUtils.isAccount(account)) {
            return Result.getFail(HttpStatus.BAD_REQUEST.value(), "您输入的账号不符合规范");
        }
        if (!RegexUtils.isPwd(pwd)) {
            return Result.getFail(HttpStatus.BAD_REQUEST.value(), "您输入的密码不符合规范");
        }
        User userByAccount = userMapper.findUserByAccount(account);
        if (null != userByAccount) {
            return Result.getFail(HttpStatus.BAD_REQUEST.value(), "您输入的用户名已存在");
        }
        User user = new User();
        user.setAccount(account);
        user.setPwd(pwd);
        user.setNickName(nickName);
        userMapper.save(user);
        return Result.getSuccess(HttpStatus.OK.value(), "注册成功", user);
    }
}
