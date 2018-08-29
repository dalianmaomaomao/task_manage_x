package com.cj.task.controller;

import com.cj.task.annotation.TokenValid;
import com.cj.task.entity.Result;
import com.cj.task.entity.User;
import com.cj.task.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by cj on 2018/8/25.
 */
@RestController
@RequestMapping("/")
public class UserController {
    @Autowired
    UserService userService;

    //用户注册
    @PostMapping("register")
    public Result register(@RequestParam String account, @RequestParam String pwd, @RequestParam String nickName) {
        System.out.println("account " + account);
        return userService.registerUser(account, pwd, nickName);
    }

    //用户登录
    @PostMapping("login")
    public Result login(@RequestParam String account, @RequestParam String pwd) {
        return userService.login(account, pwd);
    }

    //获取用户信息
    @TokenValid
    @GetMapping("userinfo")
    public Result getUsers(User user) {
        return Result.getSuccess(HttpStatus.OK.value(), "获取用户信息成功", user);
    }
}

