package com.cj.task.controller;

import com.cj.task.entity.Result;
import com.cj.task.service.UserService;
import com.cj.task.utils.RegexUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

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
    @GetMapping("userinfo")
    public Result getUsers(HttpServletRequest request) {
        String token = request.getHeader("token");
        return userService.findUserByToken(token);
    }
}

